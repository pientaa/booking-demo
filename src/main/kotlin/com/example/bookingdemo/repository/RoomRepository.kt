package com.example.bookingdemo.repository

import com.example.bookingdemo.model.Room
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface RoomRepository : MongoRepository<Room, UUID>