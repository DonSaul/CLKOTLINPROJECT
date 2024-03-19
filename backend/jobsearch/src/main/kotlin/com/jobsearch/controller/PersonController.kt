package com.jobsearch.controller

import com.jobsearch.dto.PersonDTO
import com.jobsearch.service.PersonService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/persons")
class PersonController(val personService: PersonService) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createPerson(@RequestBody personDTO: PersonDTO): PersonDTO {
        return personService.createPerson(personDTO)
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun retrievePerson(@PathVariable("id") personId: Int): PersonDTO {
        return personService.retrievePerson(personId)
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    fun retrieveAllPersons(): List<PersonDTO> {
        return personService.retrieveAllPersons()
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updatePerson(@PathVariable("id") personId: Int, @RequestBody personDTO: PersonDTO): PersonDTO {
        return personService.updatePerson(personId, personDTO)
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePerson(@PathVariable("id") personId: Int): String {
        return personService.deletePerson(personId)
    }
}