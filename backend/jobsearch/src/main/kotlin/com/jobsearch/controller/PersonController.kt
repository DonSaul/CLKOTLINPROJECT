package com.jobsearch.controller

import com.jobsearch.dto.PersonDTO
import com.jobsearch.service.PersonService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/persons")
class PersonController(val personService: PersonService) {
    @PostMapping
    fun createPerson(@RequestBody personDTO: PersonDTO): PersonDTO {
        return personService.createPerson(personDTO)
    }
}