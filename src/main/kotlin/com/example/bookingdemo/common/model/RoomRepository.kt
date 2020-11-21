package com.example.bookingdemo.common.model

import org.springframework.data.mongodb.repository.MongoRepository

interface RoomRepository : MongoRepository<Room, String> {
    fun findByNumber(number: String): Room?
}