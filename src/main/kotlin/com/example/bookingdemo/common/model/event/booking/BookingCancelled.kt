package com.example.bookingdemo.common.model.event.booking

import com.example.bookingdemo.common.model.event.DomainEvent

data class BookingCancelled(
    val bookingId: String,
    val roomId: String
) : DomainEvent("BookingCancelled", aggregateId = roomId)