package com.example.bookingdemo.command

import com.example.bookingdemo.common.infastructure.BookingDateException
import com.example.bookingdemo.common.infastructure.InvalidDateTimeParamsException
import java.time.*

class CreateRoom(
    val number: String,
    val hasWhiteboard: Boolean,
    val hasProjector: Boolean
)

class CreateBooking(
    val roomId: String,
    var start: Instant,
    var end: Instant
) {
    init {
        validateDateTimeParams(start, end)
    }
}

class UpdateBooking(
    val roomId: String,
    val bookingId: String,
    var start: Instant,
    var end: Instant
) {
    init {
        validateDateTimeParams(start, end)
    }
}

class CancelBooking(
    val roomId: String,
    val bookingId: String
)

private fun validateDateTimeParams(start: Instant, end: Instant) {
    if (start.isAfter(end)) throw InvalidDateTimeParamsException()
    val today = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).toInstant(ZoneOffset.UTC)
    if (start.isBefore(today)) throw BookingDateException()
}