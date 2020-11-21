package com.example.bookingdemo.command.api

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant

data class BookingDTO(
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    var start: Instant,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    var end: Instant
)