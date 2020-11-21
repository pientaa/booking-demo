package com.example.bookingdemo.command.api.dto

import com.example.bookingdemo.common.model.Booking
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant

data class BookingDTO(
    val id: String? = null,
    val roomId: String? = null,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    var start: Instant,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    var end: Instant
) {
    constructor(booking: Booking) : this(
        id = booking.id,
        roomId = booking.roomId,
        start = booking.start,
        end = booking.end
    )
}