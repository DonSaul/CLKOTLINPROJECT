package com.jobsearch.service

import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.colors.DeviceRgb
import com.itextpdf.kernel.events.IEventHandler
import com.itextpdf.kernel.events.PdfDocumentEvent
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.properties.BorderRadius
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.kernel.pdf.canvas.PdfCanvas
import com.itextpdf.layout.Canvas
import com.itextpdf.layout.element.Div
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.properties.HorizontalAlignment
import com.jobsearch.entity.*
import com.jobsearch.exception.ForbiddenException
import com.jobsearch.exception.NotFoundException
import com.jobsearch.repository.CvRepository
import com.jobsearch.repository.UserRepository
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream

@Service
class GeneratePdfService(
    private val cvRepository: CvRepository,
    private val userRepository: UserRepository,
    private val userService: UserService
) {
    fun getUserCv(userId: Int): ByteArray {
        val user = userRepository.findById(userId).get()
        val authUser = userService.retrieveAuthenticatedUser()
        if (authUser.role!!.id != RoleEnum.MANAGER.id) throw ForbiddenException("Only managers can generate candidates pdfs")
        val cv = cvRepository.findByUser(user).last()
        return generateCV(cv)
    }

    fun getAuthUserCv(): ByteArray {
        val authUser = userService.retrieveAuthenticatedUser()
        val cvList = cvRepository.findByUser(authUser)
        if (cvList.isEmpty()) throw NotFoundException("Cv not found")
        return generateCV(cvList.last())
    }

    private fun generateCV(cv: Cv): ByteArray {
        val outputStream = ByteArrayOutputStream()
        val writer = PdfWriter(outputStream)
        val pdf = PdfDocument(writer)
        val document = Document(pdf)
        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, ImageEventHandler(getLogoImage()))

        val title = Paragraph("${cv.user.firstName} ${cv.user.lastName}").apply {
            setTextAlignment(TextAlignment.CENTER)
            setFontSize(20f)
            setBold()
            setMarginBottom(1f)
        }
        val email = Paragraph(cv.user.email).apply {
            setTextAlignment(TextAlignment.CENTER)
            setFontSize(15f)
            setMarginBottom(10f)
        }
        val education = Paragraph("Education: ${cv.education}").apply {
            setTextAlignment(TextAlignment.LEFT)
            setFontSize(15f)
            setMarginBottom(10f)
        }
        val summary = Paragraph(cv.summary).apply {
            setMarginBottom(10f)
            setTextAlignment(TextAlignment.JUSTIFIED)
        }
        val skillsRow = createSkillsRow(cv.skills!!)
        val jobsCardList = createJobCards(cv.jobs!!.toList().sortedBy { it.startDate }.reversed())
        val projectCardList = createProjectDivs(cv.projects!!.toList().sortedBy { it.id })

        document.add(title)
        document.add(email)
        document.add(summary)
        document.add(education)
        document.add(skillsRow)
        document.add(Paragraph("Job experience:").apply { setFontSize(15f); setMarginTop(15f) })
        for (card in jobsCardList) {
            document.add(card)
        }
        document.add(Paragraph("Projects:").apply { setFontSize(15f); setMarginTop(15f) })
        for (card in projectCardList) {
            document.add(card)
        }
        document.close()
        return outputStream.toByteArray()
    }

    private fun createSkillsRow(skills: MutableSet<Skill>): Paragraph {
        val backgroundColor = DeviceRgb(200, 200, 200)
        val opacity = 0.5f
        val skillsRow = Paragraph().apply {
            setWidth(520f)
        }
        skillsRow.add(Paragraph("Skills:").apply { setFontSize(15f); setMarginTop(15f) })

        for (skill in skills) {
            val skillTag = Paragraph(skill.name).apply {
                setTextAlignment(TextAlignment.CENTER)
                setHorizontalAlignment(HorizontalAlignment.LEFT)
                setBackgroundColor(backgroundColor, opacity)
                setMarginLeft(4f)
                setBorderRadius(BorderRadius(5f))
                setPaddings(3f,5f,3f,5f)
            }
            skillsRow.add(skillTag)
        }
        return skillsRow
    }

    private fun createJobCards(jobs: List<Job>): List<Div> {
        val cardList = MutableList(jobs.size) { Div() }
        val backgroundColor = DeviceRgb(200, 200, 200)
        val opacity = 0.5f

        for (job in jobs) {
            val card = Div().apply {
                setWidth(520f)
                setPadding(5f)
                setMarginBottom(0f)
            }
            val jobDiv = Div().apply {
                setTextAlignment(TextAlignment.JUSTIFIED)
                add(Paragraph("${job.position} at ${job.company}").apply { setFontSize(15f); setMarginBottom(0f)})
                add(Paragraph("${job.startDate} - ${job.endDate}").apply { setMarginBottom(0f)}).apply { setMarginBottom(0f)}
                add(Paragraph("Area: ${job.jobFamily.name}").apply { setMarginBottom(0f)})
                add(Paragraph(job.description).apply { setMarginBottom(0f)})
                setBackgroundColor(backgroundColor, opacity)
                setBorderRadius(BorderRadius(10f))
                setPadding(10f)
            }
            card.add(jobDiv)
            cardList.add(card)
        }
        return cardList
    }

    private fun createProjectDivs(projects: List<Project>): List<Div> {
        val cardList = MutableList(projects.size) { Div() }
        val backgroundColor = DeviceRgb(200, 200, 200)
        val opacity = 0.5f

        for (project in projects) {
            val card = Div().apply {
                setWidth(520f)
                setPadding(5f)
                setMarginBottom(0f)
            }
            val projectDiv = Div().apply {
                setTextAlignment(TextAlignment.JUSTIFIED)
                add(Paragraph(project.name).apply { setFontSize(15f); setMarginBottom(0f)})
                add(Paragraph("Area: ${project.jobFamily.name}").apply { setMarginBottom(0f)})
                add(Paragraph(project.description).apply { setMarginBottom(0f)})
                setBackgroundColor(backgroundColor, opacity)
                setBorderRadius(BorderRadius(10f))
                setPadding(10f)
            }
            card.add(projectDiv)
            cardList.add(card)
        }
        return cardList
    }
    private fun getLogoImage(): Image {
        val imagePath = "images/softserve_logo.png"
        val imageResource = ClassPathResource(imagePath)
        val imageData = ImageDataFactory.create(imageResource.file.absolutePath)
        return Image(imageData)
    }
}

class ImageEventHandler(private val img: Image) : IEventHandler {
    override fun handleEvent(event: com.itextpdf.kernel.events.Event?) {
        val docEvent = event as PdfDocumentEvent
        val pdfDoc = docEvent.document
        val page = docEvent.page
        val aboveCanvas = PdfCanvas(page.newContentStreamAfter(), page.resources, pdfDoc)
        val area = page.pageSize
        val x = area.left + 10f
        val y = area.top - 40f
        Canvas(aboveCanvas, area)
            .add(img.setWidth(80f).setFixedPosition(x, y))
    }
}
