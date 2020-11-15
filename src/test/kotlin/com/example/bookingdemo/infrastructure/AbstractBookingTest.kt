package com.example.bookingdemo.infrastructure

import com.example.bookingdemo.query.domain.booking.BookingService
import com.example.bookingdemo.stubs.BookingStub
import io.kotest.core.spec.style.BehaviorSpec

abstract class AbstractBookingTest(private val bookingService: BookingService) : BehaviorSpec({
    beforeSpec {
        bookingService.getAllBetween(fromDate = null, toDate = null)
            .forEach { bookingService.deleteById(it.id!!) }

        bookingService.save(BookingStub.wednesdayNoonBooking)
    }
    afterSpec {
        bookingService.getAllBetween(fromDate = null, toDate = null)
            .forEach { bookingService.deleteById(it.id!!) }
    }
})