package com.example.bookingdemo.service

import arrow.core.EitherOf
import arrow.core.Try
import com.example.bookingdemo.infastructure.RoomConflictException
import com.example.bookingdemo.model.Booking
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotlintest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.time.ZoneOffset

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookingServiceBehaviorSpec : BehaviorSpec() {

    @Autowired
    lateinit var bookingService: BookingService

    @Autowired
    lateinit var roomService: RoomService

    init {
        afterSpec {
            bookingService.getAllBetween(null, null)
                .forEach { bookingService.deleteById(it.id!!) }
        }

        Given("new booking") {
            val booking = Booking(
                id = null,
                roomId = "5eada3967f3a1726878aead2",
                start = LocalDateTime.of(2020, 5, 13, 12, 0).toInstant(ZoneOffset.UTC),
                end = LocalDateTime.of(2020, 5, 13, 14, 0).toInstant(ZoneOffset.UTC)
            )

            When("save it") {
                val createdBooking = bookingService.save(booking)

                Then("should be saved") {
                    createdBooking shouldBe booking.copy(id = createdBooking.id)
                }
            }
            And("try to save it again") {
                val saveAttempt = Try { bookingService.save(booking) }

                Then("Exception should be thrown") {
                    saveAttempt.isFailure() shouldBe true
                    saveAttempt.shouldBeInstanceOf<EitherOf<RoomConflictException, Booking>>()
                }
            }
        }
    }
}