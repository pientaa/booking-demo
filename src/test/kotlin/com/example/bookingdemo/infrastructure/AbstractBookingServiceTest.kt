package com.example.bookingdemo.infrastructure

import com.example.bookingdemo.service.BookingService
import com.example.bookingdemo.stubs.BookingStub
import io.kotest.core.spec.style.BehaviorSpec

abstract class AbstractBookingServiceTest(private val bookingService: BookingService) : BehaviorSpec({
    beforeSpec {
        bookingService.getAllBetween(null, null)
            .forEach { bookingService.deleteById(it.id!!) }

        bookingService.save(BookingStub.wednesdayNoonBooking)
    }
    afterSpec {
        bookingService.getAllBetween(null, null)
            .forEach { bookingService.deleteById(it.id!!) }
    }
})