package com.example.bookingdemo.query.domain.booking

import com.example.bookingdemo.command.domain.room.BookingCreated
import com.example.bookingdemo.command.domain.room.BookingUpdated
import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document
data class Booking(
    @Id
    val id: String,
    val roomNumber: String,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    val start: Instant,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    val end: Instant
) {
    constructor(event: BookingCreated) : this(
        id = event.bookingId,
        roomNumber = event.roomNumber,
        start = event.start,
        end = event.end
    )

    constructor(event: BookingUpdated) : this(
        id = event.bookingId,
        roomNumber = event.roomNumber,
        start = event.start,
        end = event.end
    )
}