package com.jobsearch.dto

import jakarta.validation.constraints.NotBlank

class ProfileDTO(
    @get:NotBlank(message = "First Name must not be blank")
    var firstName: String,
    @get:NotBlank(message = "Last Name must not be blank")
    var lastName: String,
    var email: String?,
    var roleId: Int,
    var cv: CvResponseDTO? = null,
)
