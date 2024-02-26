package com.jobsearch.service

import com.jobsearch.dto.JobFamilyDto
import com.jobsearch.entity.JobFamily
import com.jobsearch.repository.JobFamilyRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class JobFamilyService(
    private val jobFamilyRepository: JobFamilyRepository
) {
    fun createJobFamily(jobFamilyDto: JobFamilyDto): JobFamilyDto{
        val jobFamilyEntity = JobFamily(id=null, name = jobFamilyDto.name)
        val newJobFamily = jobFamilyRepository.save(jobFamilyEntity)
        return newJobFamily.let {
            JobFamilyDto(it.id, it.name)
        }
    }

    fun retrieveJobFamily(jobFamilyId: Int): JobFamilyDto {
        val jobFamily = jobFamilyRepository.findById(jobFamilyId)
            .orElseThrow { NoSuchElementException("No job family found with id $jobFamilyId") }

        return jobFamily.let {
            JobFamilyDto(it.id, it.name)
        }
    }

    fun retrieveAllJobFamily(): List<JobFamilyDto> {
        val jobFamilyList = jobFamilyRepository.findAll()

        return jobFamilyList.map {
            JobFamilyDto(it.id, it.name)
        }
    }

    fun updateJobFamily(jobFamilyId: Int, jobFamilyDto: JobFamilyDto): JobFamilyDto {
        val jobFamily = jobFamilyRepository.findById(jobFamilyId)
            .orElseThrow { NoSuchElementException("No job family found with id $jobFamilyId") }

        jobFamily.name = jobFamilyDto.name

        val updatedJobFamily = jobFamilyRepository.save(jobFamily)

        return updatedJobFamily.let {
            JobFamilyDto(it.id, it.name)
        }
    }

    fun deleteJobFamily(jobFamilyId: Int): String {
        val jobFamily = jobFamilyRepository.findById(jobFamilyId)
            .orElseThrow { NoSuchElementException("No job family found with id $jobFamilyId") }

        jobFamilyRepository.delete(jobFamily)

        return "Job family deleted successfully"
    }
    fun findByJobFamilyId(jobFamilyId: Int): Optional<JobFamily> {
        return jobFamilyRepository.findById(jobFamilyId)
    }
}