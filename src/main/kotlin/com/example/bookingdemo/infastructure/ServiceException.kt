package com.example.bookingdemo.infastructure

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.time.Instant
import java.util.*

open class ServiceException(
    private val httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    override val message: String
) : ResponseStatusException(httpStatus, message)

class RoomNotFoundException(
    private val roomId: UUID
) : ServiceException(HttpStatus.NOT_FOUND, "Room with $roomId not found")

class BookingNotFoundException(
    private val bookingId: UUID
) : ServiceException(HttpStatus.NOT_FOUND, "Booking with $bookingId not found")

class RoomConflictException(
    private val roomId: UUID,
    private val start: Instant,
    private val end: Instant
) : ServiceException(HttpStatus.CONFLICT, "Room with id: $roomId is already booked in period: $start - $end")
