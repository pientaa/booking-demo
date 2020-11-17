package com.example.bookingdemo.query.api.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant

data class BookingQueryDTO(
    val id: String,
    val roomId: String,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    val start: Instant,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    val end: Instant
)