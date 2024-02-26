package com.jobsearch.service

import com.jobsearch.dto.VacancyDto
import com.jobsearch.entity.Vacancy
import com.jobsearch.repository.VacancyRepository
import jakarta.transaction.Transactional
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
            Vacancy(it.id, it.name, it.companyName, it.salaryExpectation, it.yearsOfExperience, it.description, selectedJobFamily)
        }
        val newVacancy = vacancyRepository.save(vacancyEntity)

        return mapToVacancyDto(newVacancy)
    }

    fun retrieveVacancy(vacancyId: Int): VacancyDto {
        val vacancy = vacancyRepository.findById(vacancyId)
            .orElseThrow { NoSuchElementException("No vacancy found with id $vacancyId") }

        return mapToVacancyDto(vacancy)
    }

    fun retrieveAllVacancy(): List<VacancyDto> {
        val persons = vacancyRepository.findAll()

        return persons.map {
            mapToVacancyDto(it)
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
        return mapToVacancyDto(updatedVacancy)
    }

    fun deleteVacancy(vacancyId: Int): String {
        val vacancy = vacancyRepository.findById(vacancyId)
            .orElseThrow { NoSuchElementException("No vacancy found with id $vacancyId") }

        vacancyRepository.delete(vacancy)
        return "Vacancy deleted successfully"
    }

    @Transactional
    fun findVacanciesByJobFamily(jobFamilyId: Int): List<VacancyDto> {
        val vacancies = vacancyRepository.findByJobFamilyId(jobFamilyId)
        return vacancies.map {
            mapToVacancyDto(it)
        }
    }

    @Transactional
    fun findBySalaryExpectation(salary: Int): List<VacancyDto> {
        val vacancies = vacancyRepository.findBySalaryExpectation(salary)
        return vacancies.map {
            mapToVacancyDto(it)
        }
    }

    @Transactional
    fun findVacanciesByYearsOfExperience(yearsOfExperience: Int): List<VacancyDto> {
        val vacancies = vacancyRepository.findByYearsOfExperience(yearsOfExperience)
        return vacancies.map {
            mapToVacancyDto(it)
        }
    }

    fun mapToVacancyDto(vacancy: Vacancy): VacancyDto {
        return vacancy.let {
            VacancyDto(
                it.id,
                it.name,
                it.companyName,
                it.salaryExpectation,
                it.yearsOfExperience,
                it.description,
                it.jobFamily.id,
                it.jobFamily.name
            )
        }
    }
}