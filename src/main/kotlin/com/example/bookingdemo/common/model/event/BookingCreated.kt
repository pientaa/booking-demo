package com.example.bookingdemo.common.model.event

import com.example.bookingdemo.common.model.DomainEvent
import com.example.bookingdemo.query.domain.booking.Booking
import java.time.Instant

data class BookingCreated(
    val roomId: String,
    val start: Instant,
    val end: Instant
) : DomainEvent("BookingCreated") {
    constructor(booking: Booking) : this(
        roomId = booking.roomId,
        start = booking.start,
        end = booking.end
    )
}