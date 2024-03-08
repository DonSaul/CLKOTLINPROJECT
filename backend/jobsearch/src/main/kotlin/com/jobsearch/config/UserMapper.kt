package com.jobsearch.config

import com.jobsearch.dto.UserDTO
import com.jobsearch.entity.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface UserMapper {

    @Mapping(source = "id", target = "id")
    fun toEntity(userDTO: UserDTO): User

    @Mapping(source = "id", target = "id")
    fun toDTO(user: User): UserDTO
}

