package com.example.bookingdemo.repository

import com.example.bookingdemo.infastructure.RoomNotFoundException
import com.example.bookingdemo.model.Room
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.findByIdOrNull

interface RoomRepository : MongoRepository<Room, String> {

    @JvmDefault
    fun findByIdOrThrow(id: String): Room =
        findByIdOrNull(id) ?: throw RoomNotFoundException(id)
}