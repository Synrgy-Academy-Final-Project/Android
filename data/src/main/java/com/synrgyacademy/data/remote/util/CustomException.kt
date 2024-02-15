package com.synrgyacademy.data.remote.util


class UnauthorizedException(message: String) : Exception(message)
class ForbiddenException(message: String) : Exception(message)
class NotFoundException(message: String) : Exception(message)
class InternalServerException(message: String) : Exception(message)
class BadRequestException(message: String) : Exception(message)