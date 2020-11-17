package com.example.bookingdemo.common.repository

import com.example.bookingdemo.common.model.Room
import org.springframework.data.mongodb.repository.MongoRepository

interface RoomRepository : MongoRepository<Room, String> {
    fun findByNumber(number: String): Room?
}