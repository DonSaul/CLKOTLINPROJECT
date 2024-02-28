package com.jobsearch.service

import com.jobsearch.dto.StatusDTO
import com.jobsearch.entity.Status
import com.jobsearch.repository.StatusRepository
import org.springframework.stereotype.Service

@Service
class StatusService(val statusRepository: StatusRepository) {
    fun createStatus(statusDTO: StatusDTO): StatusDTO {
        val statusEntity = statusDTO.let {
            Status(statusId = null, name = it.name)
        }
        val newStatus = statusRepository.save(statusEntity)

        return newStatus.let {
            StatusDTO(it.statusId, it.name)
        }
    }

    fun retrieveStatus(statusId: Int): StatusDTO {
        val status = statusRepository.findById(statusId)
            .orElseThrow{NoSuchElementException("No status found with id $statusId")}
        return status.let {
            StatusDTO(it.statusId!!, it.name)
        }
    }

    fun retrieveAllStatus(): List<StatusDTO> {
        val status = statusRepository.findAll()
        return status.map {
            StatusDTO(it.statusId!!, it.name)
        }
    }

    fun updateStatus(statusId: Int, statusDTO: StatusDTO): StatusDTO {
        val status = statusRepository.findById(statusId)
            .orElseThrow { NoSuchElementException("No status found with id $statusId") }
        status.name = statusDTO.name
        val updatedStatus = statusRepository.save(status)

        return updatedStatus.let {
            StatusDTO(it.statusId!!, it.name)
        }
    }

    fun deleteStatus(statusId: Int): String {
        val status = statusRepository.findById(statusId)
            .orElseThrow { NoSuchElementException("No status found with id $statusId") }

        statusRepository.delete(status)

        return "Status deleted successfully"
    }
}