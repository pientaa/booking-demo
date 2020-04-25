package com.example.bookingdemo.booking

import org.springframework.stereotype.Service

@Service
class BookingService(
    val bookingRepository: BookingRepository
) {
    fun save(booking: Booking): Booking = bookingRepository.save(booking)
    fun getAll(): List<Booking> = bookingRepository.findAll()
}