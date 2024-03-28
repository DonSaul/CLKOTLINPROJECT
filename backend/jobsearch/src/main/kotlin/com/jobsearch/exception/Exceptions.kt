package com.jobsearch.exception

import jakarta.ws.rs.ForbiddenException
import jakarta.ws.rs.NotFoundException

class NotFoundException(message: String) : NotFoundException(message)
class ForbiddenException(message: String) : ForbiddenException(message)
class UserAlreadyExistsException(message: String) : RuntimeException(message)
