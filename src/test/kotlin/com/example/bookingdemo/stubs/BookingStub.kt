package com.example.bookingdemo.stubs

import com.example.bookingdemo.query.domain.booking.Booking
import java.time.LocalDateTime
import java.time.ZoneOffset

object BookingStub {
    val wednesdayNoonBooking
        get() = Booking(
            id = null,
            roomId = "5eada3967f3a1726878aeacf",
            start = LocalDateTime.of(2020, 8, 29, 12, 0).toInstant(ZoneOffset.UTC),
            end = LocalDateTime.of(2020, 8, 29, 13, 0).toInstant(ZoneOffset.UTC)
        )

    val nonConflictingBookings
        get() = listOf(
            Booking(
                id = null,
                roomId = "5eada3967f3a1726878aeacf",
                start = LocalDateTime.of(2020, 8, 29, 11, 0).toInstant(ZoneOffset.UTC),
                end = LocalDateTime.of(2020, 8, 29, 12, 0).toInstant(ZoneOffset.UTC)
            ),
            Booking(
                id = null,
                roomId = "5eada3967f3a1726878aeacf",
                start = LocalDateTime.of(2020, 8, 29, 13, 0).toInstant(ZoneOffset.UTC),
                end = LocalDateTime.of(2020, 8, 29, 14, 0).toInstant(ZoneOffset.UTC)
            )
        )

    val conflictingBookings
        get() = listOf(
            Booking(
                id = null,
                roomId = "5eada3967f3a1726878aeacf",
                start = LocalDateTime.of(2020, 8, 29, 11, 0).toInstant(ZoneOffset.UTC),
                end = LocalDateTime.of(2020, 8, 29, 12, 1).toInstant(ZoneOffset.UTC)
            ),
            Booking(
                id = null,
                roomId = "5eada3967f3a1726878aeacf",
                start = LocalDateTime.of(2020, 8, 29, 12, 59).toInstant(ZoneOffset.UTC),
                end = LocalDateTime.of(2020, 8, 29, 14, 0).toInstant(ZoneOffset.UTC)
            ),
            Booking(
                id = null,
                roomId = "5eada3967f3a1726878aeacf",
                start = LocalDateTime.of(2020, 8, 29, 12, 0).toInstant(ZoneOffset.UTC),
                end = LocalDateTime.of(2020, 8, 29, 13, 0).toInstant(ZoneOffset.UTC)
            ),
            Booking(
                id = null,
                roomId = "5eada3967f3a1726878aeacf",
                start = LocalDateTime.of(2020, 8, 29, 12, 1).toInstant(ZoneOffset.UTC),
                end = LocalDateTime.of(2020, 8, 29, 12, 59).toInstant(ZoneOffset.UTC)
            )
        )
}