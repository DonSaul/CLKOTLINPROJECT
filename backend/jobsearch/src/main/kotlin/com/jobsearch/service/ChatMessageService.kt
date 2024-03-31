package com.jobsearch.service

import com.jobsearch.dto.ChatMessageDTO
import com.jobsearch.dto.ChatMessageRequestDTO
import com.jobsearch.entity.ChatMessage
import com.jobsearch.repository.ChatMessageRepository
import com.jobsearch.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*
import kotlin.NoSuchElementException
@Service
class ChatMessageService
(

        private val chatMessageRepository: ChatMessageRepository,
        private val userService: UserService,
        private val userRepository:UserRepository
        )
{


}