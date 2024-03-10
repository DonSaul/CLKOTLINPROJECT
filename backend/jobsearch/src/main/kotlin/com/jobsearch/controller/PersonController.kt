package com.jobsearch.controller

import com.jobsearch.dto.PersonDTO
import com.jobsearch.service.PersonService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/persons")
class PersonController(val personService: PersonService) {
    @PostMapping
    fun createPerson(@RequestBody personDTO: PersonDTO): PersonDTO {
        return personService.createPerson(personDTO)
    }

    @GetMapping("{id}")
    fun retrievePerson(@PathVariable("id") personId: Int): PersonDTO {
        return personService.retrievePerson(personId)
    }

    @GetMapping()
    fun retrieveAllPersons(): List<PersonDTO> {
        return personService.retrieveAllPersons()
    }

    @PutMapping("{id}")
    fun updatePerson(@PathVariable("id") personId: Int, @RequestBody personDTO: PersonDTO): PersonDTO {
        return personService.updatePerson(personId, personDTO)
    }

    @DeleteMapping("{id}")
    fun deletePerson(@PathVariable("id") personId: Int): String {
        return personService.deletePerson(personId)
    }
}