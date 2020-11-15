package com.example.bookingdemo.infrastructure

import com.example.bookingdemo.query.domain.booking.BookingQueryService
import com.example.bookingdemo.stubs.BookingStub
import io.kotest.core.spec.style.BehaviorSpec

abstract class AbstractBookingTest(private val bookingQueryService: BookingQueryService) : BehaviorSpec({
    beforeSpec {
        bookingQueryService.getAllBetween(fromDate = null, toDate = null)
            .forEach { bookingQueryService.deleteById(it.id!!) }

        bookingQueryService.save(BookingStub.wednesdayNoonBooking)
    }
    afterSpec {
        bookingQueryService.getAllBetween(fromDate = null, toDate = null)
            .forEach { bookingQueryService.deleteById(it.id!!) }
    }
})