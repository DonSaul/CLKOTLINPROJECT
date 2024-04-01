package com.jobsearch.integration.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.jobsearch.dto.ChatMessageRequestDTO
import com.jobsearch.dto.VacancyRequestDTO
import com.jobsearch.entity.*
import com.jobsearch.repository.ChatMessageRepository
import com.jobsearch.repository.ConversationRepository
import com.jobsearch.repository.UserRepository
import com.jobsearch.repository.VacancyRepository
import com.jobsearch.service.ConversationService
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import org.springframework.test.web.servlet.delete
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.*
import org.hamcrest.Matchers.equalTo

/**
 * Integration test for ConversationController.
 * Uses H2 database for testing with hardcoded base data contained in data.sql file.
 * Base data for testing is created in setUp method.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test") // Use the "test" profile for testing with H2 database
class ConversationControllerIntgTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var conversationRepository: ConversationRepository

    @Autowired
    lateinit var conversationService: ConversationService

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var chatMessageRepository: ChatMessageRepository


    // Will be initialized in setUp
    lateinit var manager1: User
    lateinit var manager2: User
    lateinit var candidate1: User
    lateinit var candidate2: User

    companion object {
        // Mock objects, will be initialized in setUp
        private val candidateRole = Role(1, "candidate")
        private val managerRole = Role(2, "manager")
        val JOB_FAMILY = JobFamily(1, "Information Technology")
        val MANAGER_1 = User(
            id = null,
            firstName = "Mana",
            lastName = "Ger",
            email = "manager1@mail.com",
            password = "test123",
            role = managerRole
        )
        val MANAGER_2 = User(
            id = null,
            firstName = "Mana2",
            lastName = "Ger2",
            email = "manager2@mail.com",
            password = "test123",
            role = managerRole
        )
        val CANDIDATE_1 = User(
            id = null,
            firstName = "Cand",
            lastName = "Ide",
            email = "candidate1@mail.com",
            password = "test123",
            role = candidateRole
        )

        val CANDIDATE_2 = User(
            id = null,
            firstName = "Candolio",
            lastName = "Ditolio",
            email = "candidate2@mail.com",
            password = "test123",
            role = candidateRole
        )

        val messageToSend_1 = ChatMessageRequestDTO(
            receiverUserName = CANDIDATE_2.email,
            message = "Hello this is a test 1"
        )
        val messageToSend_2 = ChatMessageRequestDTO(
            receiverUserName = CANDIDATE_2.email,
            message = "Hello this is a test 2"
        )

        val CONVERSATION_BETWEEN_CANDIDATE_1_AND_MANAGER_1 = Conversation(
            id = null,
            user1 = CANDIDATE_1,
            user2 = MANAGER_1,
        )

        val CONVERSATION_BETWEEN_CANDIDATE_2_AND_MANAGER_1 = Conversation(
            id = null,
            user1 = CANDIDATE_2,
            user2 = MANAGER_1,
        )



        val CHAT_MESSAGES_BETWEEN_CANDIDATE_1_AND_MANAGER_1 = mutableListOf(
            ChatMessage(
                id = null,
                message = "From manager 1 to candidate 1",
                sender = MANAGER_1,
                receiver = CANDIDATE_1,
                date = Date.from(LocalDate.of(2023, 3, 1).atStartOfDay(ZoneOffset.UTC).toInstant()),
                conversation = CONVERSATION_BETWEEN_CANDIDATE_1_AND_MANAGER_1
            ),
            ChatMessage(
                id = null,
                message = "From candidate 1 to manager 1",
                sender = CANDIDATE_1,
                receiver = MANAGER_1,
                date = Date.from(LocalDate.of(2023, 4, 1).atStartOfDay(ZoneOffset.UTC).toInstant()),
                conversation = CONVERSATION_BETWEEN_CANDIDATE_1_AND_MANAGER_1
            )
        )

        val CHAT_MESSAGES_BETWEEN_CANDIDATE_2_AND_MANAGER_1 = mutableListOf(
            ChatMessage(
                id = null,
                message = "From manager 1 to candidate 2",
                sender = MANAGER_1,
                receiver = CANDIDATE_2,
                date = Date.from(LocalDate.of(2023, 3, 1).atStartOfDay(ZoneOffset.UTC).toInstant()),
                conversation = CONVERSATION_BETWEEN_CANDIDATE_1_AND_MANAGER_1
            ),
            ChatMessage(
                id = null,
                message = "From candidate 2 to manager 1",
                sender = CANDIDATE_2,
                receiver = MANAGER_1,
                date = Date.from(LocalDate.of(2023, 4, 1).atStartOfDay(ZoneOffset.UTC).toInstant()),
                conversation = CONVERSATION_BETWEEN_CANDIDATE_1_AND_MANAGER_1
            ),
            ChatMessage(
                id = null,
                message = "From candidate 2 to manager 1",
                sender = CANDIDATE_2,
                receiver = MANAGER_1,
                date = Date.from(LocalDate.of(2023, 5, 1).atStartOfDay(ZoneOffset.UTC).toInstant()),
                conversation = CONVERSATION_BETWEEN_CANDIDATE_1_AND_MANAGER_1
            )
        )


    }

    @BeforeEach
    fun setUp() {


        //userRepository.deleteAll()

        manager1 = userRepository.save(MANAGER_1)
        manager2 = userRepository.save(MANAGER_2)
        candidate1 = userRepository.save(CANDIDATE_1)
        candidate2 = userRepository.save(CANDIDATE_2)

        conversationRepository.save(CONVERSATION_BETWEEN_CANDIDATE_1_AND_MANAGER_1)

        CHAT_MESSAGES_BETWEEN_CANDIDATE_1_AND_MANAGER_1.forEach {
            chatMessageRepository.save(it)
        }

        conversationRepository.save(CONVERSATION_BETWEEN_CANDIDATE_2_AND_MANAGER_1)

        CHAT_MESSAGES_BETWEEN_CANDIDATE_2_AND_MANAGER_1.forEach {
            chatMessageRepository.save(it)
        }

    }

    @Test
    @WithMockUser(username = "candidate1@mail.com", authorities = ["candidate"])
    fun `Should retrieve a single conversation after users has messaged only one user previously and return 200`() {
        val response = mockMvc.get("/api/v1/conversation/user/all")

        response
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                (jsonPath("$", hasSize<Int>(1)))

            }


    }

    @Test
    @WithMockUser(username = "manager1@mail.com", authorities = ["manager"])
    fun `Should retrieve two conversations after users has messaged two other users and return 200`() {
        val response = mockMvc.get("/api/v1/conversation/user/all")

        response
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                (jsonPath("$", hasSize<Int>(2)))

            }


    }
}