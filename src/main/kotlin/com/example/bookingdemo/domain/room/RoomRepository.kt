package com.example.bookingdemo.domain.room

import org.springframework.data.mongodb.repository.MongoRepository

interface RoomRepository : MongoRepository<Room, String>