package com.example.bookingdemo.infrastructure

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.text.SimpleDateFormat

object Mapper : ObjectMapper() {
    init {
        findAndRegisterModules()
        registerKotlinModule()
        dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    }
}