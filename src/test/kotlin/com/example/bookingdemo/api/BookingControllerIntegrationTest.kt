package com.example.bookingdemo.api

import com.example.bookingdemo.infrastructure.AbstractBookingTest
import com.example.bookingdemo.infrastructure.Mapper
import com.example.bookingdemo.query.domain.booking.Booking
import com.example.bookingdemo.query.domain.booking.BookingService
import com.example.bookingdemo.stubs.BookingStub
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotlintest.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.time.LocalDateTime

@SpringBootTest
final class BookingControllerIntegrationTest(
    applicationContext: WebApplicationContext,
    bookingService: BookingService
) : AbstractBookingTest(bookingService) {

    lateinit var mvc: MockMvc

    init {
        beforeSpec {
            mvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .build()
        }

        Given("parameters as time period from 8 a.m. to 2 p.m.") {
            val url = "http://localhost/bookings"
            val fromDateTime = LocalDateTime.of(2020, 8, 29, 8, 0)
            val toDateTime = LocalDateTime.of(2020, 8, 29, 14, 0)

            When("get bookings between") {
                val result = mvc.perform(
                    MockMvcRequestBuilders.get(url)
                        .param("fromDate", fromDateTime.toString())
                        .param("toDate", toDateTime.toString())
                )
                    .andExpect(MockMvcResultMatchers.status().isOk)
                    .andReturn()

                val actual: List<Booking> = Mapper.readValue(result.response.contentAsString)

                Then("should return one booking at 12 a.m.") {
                    actual.size shouldBe 1
                    actual.first() shouldBe BookingStub.wednesdayNoonBooking.copy(id = actual.first().id)
                }
            }
            And("two non conflicting bookings") {
                val bookings = BookingStub.nonConflictingBookings

                When("add them") {
                    val createdBookings: List<Booking> =
                        bookings.map {
                            mvc.perform(
                                MockMvcRequestBuilders.post(url)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(Mapper.writeValueAsString(it))
                            )
                                .andExpect(MockMvcResultMatchers.status().isCreated)
                                .andReturn()
                                .let { mvcResult -> Mapper.readValue(mvcResult.response.contentAsString) as Booking }
                        }

                    Then("bookings should be added") {
                        createdBookings.first() shouldBe bookings.first().copy(id = createdBookings.first().id)
                        createdBookings.last() shouldBe bookings.last().copy(id = createdBookings.last().id)
                    }
                }
            }
            And("get bookings between") {
                val expected =
                    (BookingStub.nonConflictingBookings + BookingStub.wednesdayNoonBooking).sortedBy { it.start }

                val result = mvc.perform(
                    MockMvcRequestBuilders.get(url)
                        .param("fromDate", fromDateTime.toString())
                        .param("toDate", toDateTime.toString())
                )
                    .andExpect(MockMvcResultMatchers.status().isOk)
                    .andReturn()

                val actual: List<Booking> = Mapper.readValue(result.response.contentAsString)

                Then("should return list of 3 bookings") {
                    actual.size shouldBe 3
                    actual.forEachIndexed { index, booking -> booking shouldBe expected[index].copy(id = booking.id) }
                }
            }
        }
    }
}
