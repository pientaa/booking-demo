package com.example.bookingdemo.command

import com.example.bookingdemo.common.infastructure.BookingDateException
import com.example.bookingdemo.common.infastructure.InvalidDateTimeParamsException
import com.example.bookingdemo.common.model.event.BookingCreated
import com.example.bookingdemo.common.model.event.BookingUpdated
import com.example.bookingdemo.common.model.event.RoomCreated
import com.example.bookingdemo.common.model.event.RoomEvent
import java.time.*

class CreateRoom(
    val number: String,
    val hasWhiteboard: Boolean,
    val hasProjector: Boolean
) {
    fun toEvent(number: String): RoomCreated =
        RoomCreated(number, hasWhiteboard, hasProjector)
}

class CreateBooking(
    val number: String,
    var start: Instant,
    var end: Instant
) {
    fun toEvent(bookingId: String): BookingCreated =
        BookingCreated(bookingId, number, start, end)

    init {
        validateDateTimeParams(start, end)
    }
}

class UpdateBooking(
    val number: String,
    val bookingId: String,
    var start: Instant,
    var end: Instant
) {
    fun toEvent(): RoomEvent =
        BookingUpdated(bookingId, number, start, end)

    init {
        validateDateTimeParams(start, end)
    }
}

class CancelBooking(
    val number: String,
    val bookingId: String
)

private fun validateDateTimeParams(start: Instant, end: Instant) {
    if (start.isAfter(end)) throw InvalidDateTimeParamsException()
    val today = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).toInstant(ZoneOffset.UTC)
    if (start.isBefore(today)) throw BookingDateException()
}