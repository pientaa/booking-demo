package com.example.bookingdemo.booking

import com.example.bookingdemo.room.RoomService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bookings")
class BookingController(
    val bookingService: BookingService,
    val roomService: RoomService
) {
    @PostMapping
    fun save(@RequestBody booking: Booking): Booking {
        //TODO: Check if not exists
        return bookingService.save(booking)
    }

    @GetMapping
    fun getAll(): List<Booking> = bookingService.getAll()
}