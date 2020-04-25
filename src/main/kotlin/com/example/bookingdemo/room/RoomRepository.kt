package com.example.bookingdemo.room

import org.springframework.data.mongodb.repository.MongoRepository

interface RoomRepository : MongoRepository<Room, String>