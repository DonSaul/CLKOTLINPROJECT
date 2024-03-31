package com.jobsearch.integration.controller

import com.jobsearch.controller.CandidateController
import com.jobsearch.dto.CandidateDTO
import com.jobsearch.service.UserService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.http.HttpStatus

class CandidateControllerTest {

    private val userService = mock(UserService::class.java)
    private val candidateController = CandidateController(userService)

    @Test
    fun `test searchCandidates with valid criteria`() {
        // Mock data
        val candidates = listOf(
            CandidateDTO(
                1,
                "Will",
                "smith",
                "wilSmith@gmail.com",
                123,
                2300,
                null,
                null
            )
        )
        `when`(userService.findCandidatesByFilter(2300, 1, 123)).thenReturn(candidates)
        val responseEntity = candidateController.searchCandidates(2300, 1, 123) // Corrected parameters
        // Assertions
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertEquals(candidates, responseEntity.body?.data)
    }

    @Test
    fun `test searchCandidates with null criteria`() {
        val candidates = emptyList<CandidateDTO>()
        `when`(userService.findCandidatesByFilter(null, null, null)).thenReturn(candidates)
        val responseEntity = candidateController.searchCandidates(null, null, null)

        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertEquals(candidates, responseEntity.body?.data)
    }

    @Test
    fun `test getCandidatesByVacancy with valid vacancyId`() {
        val candidates = listOf(CandidateDTO(
            1,
            "Will",
            "smith",
            "wilSmith@gmail.com",
            123,
            2300
        ))
        `when`(userService.findCandidatesByVacancyApplication(123)).thenReturn(candidates)

        val responseEntity = candidateController.getCandidatesByVacancy(123)
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertEquals(candidates, responseEntity.body?.data)
    }

    @Test
    fun `test getCandidatesByVacancy with invalid vacancyId`() {
        val candidates = emptyList<CandidateDTO>()
        `when`(userService.findCandidatesByVacancyApplication(999)).thenReturn(candidates)
        val responseEntity = candidateController.getCandidatesByVacancy(999)
        // Assertions
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.statusCode)
        assertEquals(candidates, responseEntity.body?.data)
    }
}
