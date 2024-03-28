package com.jobsearch.mapper
import com.jobsearch.dto.CvResponseDTO
import com.jobsearch.entity.Cv
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface CvMapper {

    fun mapToDto(cv: Cv): CvResponseDTO
}