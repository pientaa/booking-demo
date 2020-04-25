package com.example.bookingdemo.booking

import org.springframework.data.mongodb.repository.MongoRepository

interface BookingRepository : MongoRepository<Booking, String>