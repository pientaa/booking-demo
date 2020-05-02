package com.example.bookingdemo.service

import com.example.bookingdemo.infastructure.RoomNotFoundException
import com.example.bookingdemo.model.Room
import com.example.bookingdemo.repository.RoomRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class RoomService(
    val roomRepository: RoomRepository
) {
    fun save(room: Room): Room = roomRepository.save(room)
    fun getAll(): List<Room> = roomRepository.findAll()
    fun getById(id: String): Room = roomRepository.findByIdOrNull(id) ?: throw RoomNotFoundException(id)
}