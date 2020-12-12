package com.example.bookingdemo.query.domain.room

import org.springframework.data.mongodb.repository.MongoRepository

interface RoomRepository : MongoRepository<Room, String> {
    fun findByNumber(number: String): Room?
}