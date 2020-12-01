package com.example.bookingdemo.common.infastructure

data class RabbitEvent(
    val eventType: String,
    val eventLoad: String
)
