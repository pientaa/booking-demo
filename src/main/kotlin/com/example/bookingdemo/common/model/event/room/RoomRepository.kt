package com.example.bookingdemo.common.model.event.room

import org.springframework.data.mongodb.repository.MongoRepository

interface RoomRepository : MongoRepository<Room, String> {
    fun findByNumber(number: String): Room?
}