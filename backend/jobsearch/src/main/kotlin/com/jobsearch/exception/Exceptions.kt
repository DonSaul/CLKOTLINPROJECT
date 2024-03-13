package com.jobsearch.exception


class NotFoundException(message: String?): RuntimeException(message)
class UserAlreadyExistsException(message: String?): RuntimeException(message)