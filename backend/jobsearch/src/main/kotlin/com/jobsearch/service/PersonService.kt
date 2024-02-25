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
            Person(id = null, firstName = it.firstName, lastName = it.lastName)
        }
        val newPerson = personRepository.save(personEntity)

        return newPerson.let {
            PersonDTO(it.id!!, it.firstName, it.lastName)
        }
    }

    fun retrievePerson(personId: Int): PersonDTO {
        val person = personRepository.findById(personId)
            .orElseThrow { NoSuchElementException("No person found with id $personId") }

        return person.let {
            PersonDTO(it.id!!, it.firstName, it.lastName)
        }
    }

    fun retrieveAllPersons(): List<PersonDTO> {
        val persons = personRepository.findAll()

        return persons.map { person ->
            PersonDTO(person.id!!, person.firstName, person.lastName)
        }
    }

    fun updatePerson(personId: Int, personDTO: PersonDTO): PersonDTO {
        val person = personRepository.findById(personId)
            .orElseThrow { NoSuchElementException("No person found with id $personId") }

        person.firstName = personDTO.firstName
        person.lastName = personDTO.lastName

        val updatedPerson = personRepository.save(person)

        return updatedPerson.let {
            PersonDTO(it.id!!, it.firstName, it.lastName)
        }
    }

    fun deletePerson(personId: Int): String {
        val person = personRepository.findById(personId)
            .orElseThrow { NoSuchElementException("No person found with id $personId") }

        personRepository.delete(person)

        return "Person deleted successfully"
    }
}