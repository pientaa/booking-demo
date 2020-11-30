package com.example.bookingdemo.command.infrastructure

data class RabbitEvent(
    val eventType: String,
    val eventLoad: String
)
