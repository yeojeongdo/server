package com.map.map.exception

import org.springframework.http.HttpStatus
import java.lang.RuntimeException

class CustomHttpException (val status : HttpStatus, override val message : String) : RuntimeException()