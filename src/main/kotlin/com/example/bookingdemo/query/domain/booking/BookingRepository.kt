package com.example.bookingdemo.query.domain.booking

import org.springframework.data.mongodb.repository.MongoRepository
import java.time.Instant

interface BookingRepository : MongoRepository<Booking, String> {
    fun findAllByStartBefore(toDate: Instant): List<Booking>
    fun findAllByEndAfter(fromDate: Instant): List<Booking>
    fun findAllByStartBetween(fromDate: Instant, toDate: Instant): List<Booking>
    fun findAllByStartLessThanEqualAndEndGreaterThan(fromDate: Instant, toDate: Instant): List<Booking>

    fun findAllByRoomNumber(roomNumber: String): List<Booking>
    fun findAllByRoomNumberAndStartBefore(roomNumber: String, toDate: Instant): List<Booking>
    fun findAllByRoomNumberAndEndAfter(roomNumber: String, fromDate: Instant): List<Booking>
    fun findAllByRoomNumberAndStartBetween(roomNumber: String, fromDate: Instant, toDate: Instant): List<Booking>
    fun findAllByRoomNumberAndStartLessThanEqualAndEndGreaterThan(
        roomNumber: String, fromDate: Instant, toDate: Instant
    ): List<Booking>
}