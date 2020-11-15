package com.example.bookingdemo.command.domain

import com.example.bookingdemo.common.model.DomainEventPublisher
import com.example.bookingdemo.common.model.event.BookingCreated
import com.example.bookingdemo.query.domain.booking.Booking
import org.springframework.stereotype.Service

@Service
class BookingCommandService(
    private val domainEventPublisher: DomainEventPublisher
) {

    fun save(booking: Booking) {
        domainEventPublisher.publishEvent(BookingCreated(booking))
    }
}