package com.jobsearch.controller
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jobsearch.entity.*
import com.jobsearch.service.NotificationService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NotificationControllerUnitTest {
    private lateinit var mockMvc: MockMvc

    @Mock
    private lateinit var notificationService: NotificationService

    @InjectMocks
    private lateinit var notificationController: NotificationController

    // Configuring ObjectMapper to handle LocalDateTime
    private val objectMapper = jacksonObjectMapper().apply {
        registerModule(JavaTimeModule())
        registerModule(
            JavaTimeModule().addSerializer(
                LocalDateTime::class.java,
                LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME)
            )
        )
    }


    private val recipient = User(
        id = null,
        firstName = "Cand",
        lastName = "Ide",
        email = "candidate1@mail.com",
        password = "test123",
        role = Role(2, "candidate")
    )

    private val notificationType = NotificationType(1, "vacancies")

    private val notification = Notification(
        id = 1,
        type = notificationType,
        recipient = recipient,
        subject = "subject",
        content = "content",
        sentDateTime = LocalDateTime.now()
    )

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        mockMvc = MockMvcBuilders.standaloneSetup(notificationController)
            .build()

    }

    @Test
    fun `Should retrieve all notifications and return status 200`() {
        `when`(notificationService.retrieveAllNotifications()).thenReturn(listOf(notification))

        // when - action or the behaviour that we are going test
        val result = mockMvc.get("/api/v1/notifications/all")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                content { objectMapper.writeValueAsString(listOf(notification)) }
            }
            .andReturn()

        val contentAsString = result.response.contentAsString
        println("Response Content: $contentAsString")

        verify(notificationService).retrieveAllNotifications()
    }


    @Test
    fun `Should retrieve all notifications by recipient`() {
        `when`(notificationService.getNotificationsByRecipientUsername(recipient.email)).thenReturn(listOf(notification))
        val recipientEmail = recipient.email

        // GET request
        val result = mockMvc.get("/api/v1/notifications/recipient/{email}", recipientEmail)
            // then - verify the output
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                content { objectMapper.writeValueAsString(listOf(notification)) }
            }
            .andReturn()

        val contentAsString = result.response.contentAsString
        println("Response Content: $contentAsString")

        verify(notificationService).getNotificationsByRecipientUsername(recipientEmail)
    }

}