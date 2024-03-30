package com.jobsearch.service

import com.itextpdf.kernel.colors.DeviceRgb
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.*
import com.itextpdf.layout.properties.BorderRadius
import com.itextpdf.layout.properties.HorizontalAlignment
import com.itextpdf.layout.properties.TextAlignment
import com.jobsearch.entity.Job
import com.jobsearch.entity.Project
import com.jobsearch.exception.ForbiddenException
import com.jobsearch.repository.CvRepository
import com.jobsearch.repository.UserRepository
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream

@Service
class GeneratePdfService(
    private val cvRepository: CvRepository,
    private val userRepository: UserRepository,
    private val userService: UserService
) {


    fun generatePdf(userId: Int): ByteArray {
        val outputStream = ByteArrayOutputStream()
        val writer = PdfWriter(outputStream)
        val pdf = PdfDocument(writer)
        val document = Document(pdf)

        val user = userRepository.findById(userId).get()
        val authUser = userService.retrieveAuthenticatedUser()
        if (authUser.role!!.name != "manager") throw ForbiddenException("Only managers can generate candidates pdfs")

        val cv = cvRepository.findByUser(user).last()

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
        val summary = Paragraph(cv.summary).apply {
            setMarginBottom(10f)
        }

        val skillString = cv.skills!!.joinToString { it.name }
        val jobsCardList = createJobsDiv(cv.jobs!!.toList())
        val projectCardList = createProjectsDiv(cv.projects!!.toList())

        document.add(title)
        document.add(email)
        document.add(summary)
        document.add(Paragraph("Skills: $skillString"))
        document.add(Paragraph("Job experience:"))
        for (card in jobsCardList) {
            document.add(card)
        }
        document.add(Paragraph("Projects:"))
        for (card in projectCardList) {
            document.add(card)
        }

        document.close()
        return outputStream.toByteArray()
    }

    private fun createJobsDiv(jobs: List<Job>): List<Div> {
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
                setTextAlignment(TextAlignment.LEFT)
                add(Paragraph(job.position).apply { setFontSize(15f); setMarginBottom(0f)})
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

    private fun createProjectsDiv(projects: List<Project>): List<Div> {
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
                setTextAlignment(TextAlignment.LEFT)
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


//    private fun createProjectsTable(projects: List<Project>): Table {
//        val table = Table(3).apply {
//            setHorizontalAlignment(HorizontalAlignment.CENTER)
//            setWidth(520f)
//            setMarginBottom(10f)
//        }
//        table.addCell(Cell().add(Paragraph("Title").apply {
//            setTextAlignment(TextAlignment.CENTER)
//        }))
//        table.addCell(Cell().add(Paragraph("Area").apply {
//            setTextAlignment(TextAlignment.CENTER)
//        }))
//        table.addCell(Cell().add(Paragraph("Description").apply {
//            setTextAlignment(TextAlignment.CENTER)
//        }))
//
//        for (project in projects) {
//            val titleParagraph = Paragraph(project.name)
//            val areaParagraph = Paragraph(project.jobFamily.name)
//            val descriptionParagraph = Paragraph(project.description)
//
//            table.addCell(Cell().add(titleParagraph))
//            table.addCell(Cell().add(areaParagraph))
//            table.addCell(Cell().add(descriptionParagraph))
//
//        }
//        return table
//    }
//
//    fun createJobsTable(jobs: List<Job>): Table {
//        val table = Table(4).apply {
//            setHorizontalAlignment(HorizontalAlignment.CENTER)
//            setWidth(520f)
//            setMarginBottom(10f)
//        }
//
//        table.addCell(Cell().add(Paragraph("Start Date").apply {
//            setTextAlignment(TextAlignment.CENTER)
//        }))
//        table.addCell(Cell().add(Paragraph("End Date").apply {
//            setTextAlignment(TextAlignment.CENTER)
//        }))
//        table.addCell(Cell().add(Paragraph("Position").apply {
//            setTextAlignment(TextAlignment.CENTER)
//        }))
//        table.addCell(Cell().add(Paragraph("Description").apply {
//            setTextAlignment(TextAlignment.CENTER)
//        }))
//
//        for (job in jobs) {
//            val startDateParagraph = Paragraph(job.startDate.toString()).apply {
//                setTextAlignment(TextAlignment.CENTER)
//            }
//            val endDateParagraph = Paragraph(job.endDate.toString()).apply {
//                setTextAlignment(TextAlignment.CENTER)
//            }
//            val positionParagraph = Paragraph(job.position)
//            val descriptionParagraph = Paragraph(job.description)
//
//
//            table.addCell(Cell().add(startDateParagraph))
//            table.addCell(Cell().add(endDateParagraph))
//            table.addCell(Cell().add(positionParagraph))
//            table.addCell(Cell().add(descriptionParagraph))
//        }
//        return table
//    }
}