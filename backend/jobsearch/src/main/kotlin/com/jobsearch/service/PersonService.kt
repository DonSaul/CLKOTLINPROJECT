package com.jobsearch.service

import com.jobsearch.dto.PersonDTO
import com.jobsearch.entity.Person
import com.jobsearch.repository.PersonRepository
import org.springframework.stereotype.Service

@Service
class PersonService(
    val personRepository: PersonRepository
) {
    fun createPerson(personDTO: PersonDTO): PersonDTO {
        val personEntity = personDTO.let {
            Person(id=null, firstName = it.firstName, lastName = it.lastName)
        }
        val newPerson = personRepository.save(personEntity)

        return newPerson.let {
            PersonDTO(it.id!!, it.firstName, it.lastName)
        }
    }
}