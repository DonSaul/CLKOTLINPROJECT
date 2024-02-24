package com.jobsearch.service

import com.jobsearch.dto.VacancyDto
import com.jobsearch.entity.Vacancy
import com.jobsearch.repository.VacancyRepository
import org.springframework.stereotype.Service

@Service
class VacancyService(
    val vacancyRepository: VacancyRepository,
    val jobFamilyService: JobFamilyService
) {
    fun createVacancy(vacancyDto: VacancyDto): VacancyDto {
        val selectedJobFamily = jobFamilyService.findByJobFamilyId(vacancyDto.jobFamilyId!!)
            .orElseThrow { NoSuchElementException("No vacancy found with id ${vacancyDto.jobFamilyId}") }

        val vacancyEntity = vacancyDto.let {
            Vacancy(it.id, it.name, it.companyName, it.salaryExpectation, it.description, selectedJobFamily)
        }
        val newVacancy = vacancyRepository.save(vacancyEntity)

        return newVacancy.let {
            VacancyDto(
                it.id,
                it.name,
                it.companyName,
                it.salaryExpectation,
                it.description,
                it.jobFamily.id,
                it.jobFamily.name
            )
        }
    }

    fun retrieveVacancy(vacancyId: Int): VacancyDto {
        val vacancy = vacancyRepository.findById(vacancyId)
            .orElseThrow { NoSuchElementException("No vacancy found with id $vacancyId") }

        return vacancy.let {
            VacancyDto(
                it.id,
                it.name,
                it.companyName,
                it.salaryExpectation,
                it.description,
                it.jobFamily.id,
                it.jobFamily.name
            )
        }
    }

    fun retrieveAllVacancy(): List<VacancyDto> {
        val persons = vacancyRepository.findAll()

        return persons.map {
            VacancyDto(
                it.id,
                it.name,
                it.companyName,
                it.salaryExpectation,
                it.description,
                it.jobFamily.id,
                it.jobFamily.name
            )
        }
    }

    fun updateVacancy(vacancyId: Int, vacancyDto: VacancyDto): VacancyDto {
        val vacancy = vacancyRepository.findById(vacancyId)
            .orElseThrow { NoSuchElementException("No vacancy found with id $vacancyId") }

        val selectedJobFamily = jobFamilyService.findByJobFamilyId(vacancyDto.jobFamilyId!!)
            .orElseThrow { NoSuchElementException("No vacancy found with id ${vacancyDto.jobFamilyId}") }

        vacancy.name = vacancyDto.name
        vacancy.companyName = vacancyDto.companyName
        vacancy.salaryExpectation = vacancyDto.salaryExpectation
        vacancy.description = vacancyDto.description
        vacancy.jobFamily = selectedJobFamily

        val updatedVacancy = vacancyRepository.save(vacancy)
        return updatedVacancy.let {
            VacancyDto(
                it.id,
                it.name,
                it.companyName,
                it.salaryExpectation,
                it.description,
                it.jobFamily.id,
                it.jobFamily.name
            )
        }


    }

    fun deleteVacancy(vacancyId: Int): String {
        val vacancy = vacancyRepository.findById(vacancyId)
            .orElseThrow { NoSuchElementException("No vacancy found with id $vacancyId") }

        vacancyRepository.delete(vacancy)
        return "Vacancy deleted successfully"
    }

}