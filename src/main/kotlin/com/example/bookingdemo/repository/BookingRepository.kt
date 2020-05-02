package com.example.bookingdemo.repository

import com.example.bookingdemo.model.Booking
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.Instant
import java.util.*

interface BookingRepository : MongoRepository<Booking, UUID> {
    fun findAllByStartBefore(toDate: Instant): List<Booking>
    fun findAllByEndAfter(fromDate: Instant): List<Booking>
    fun findAllByStartBetween(fromDate: Instant, toDate: Instant): List<Booking>
    fun findAllByStartLessThanEqualAndEndGreaterThan(fromDate: Instant, toDate: Instant): List<Booking>

    fun findAllByRoomId(roomId: UUID): List<Booking>
    fun findAllByRoomIdAndStartBefore(roomId: UUID, toDate: Instant): List<Booking>
    fun findAllByRoomIdAndEndAfter(roomId: UUID, fromDate: Instant): List<Booking>
    fun findAllByRoomIdAndStartBetween(roomId: UUID, fromDate: Instant, toDate: Instant): List<Booking>
    fun findAllByRoomIdAndStartLessThanEqualAndEndGreaterThan(
        roomId: UUID, fromDate: Instant, toDate: Instant
    ): List<Booking>
}