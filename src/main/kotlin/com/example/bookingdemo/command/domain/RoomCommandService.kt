package com.example.bookingdemo.command.domain

import com.example.bookingdemo.common.infastructure.RoomAlreadyExistsException
import com.example.bookingdemo.common.model.Room
import com.example.bookingdemo.common.model.event.DomainEventPublisher
import com.example.bookingdemo.common.model.event.room.RoomCreated
import com.example.bookingdemo.common.repository.RoomRepository
import org.springframework.stereotype.Service

@Service
class RoomCommandService(
    private val roomRepository: RoomRepository,
    private val domainEventPublisher: DomainEventPublisher
) {

    fun create(room: Room): Room {
        if (roomRepository.findByNumber(room.number) != null)
            throw RoomAlreadyExistsException(room.number)

        return roomRepository.save(room)
            .also { domainEventPublisher.publishEvent(RoomCreated(it)) }
    }
}