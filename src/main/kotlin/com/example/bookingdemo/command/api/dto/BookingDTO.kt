package com.example.bookingdemo.command.api.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant

data class BookingDTO(
    val id: String? = null,
    val roomId: String? = null,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    var start: Instant,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    var end: Instant
)