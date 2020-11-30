package com.example.bookingdemo.command.infrastructure

import com.example.bookingdemo.common.model.DomainEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class EventPublisher(
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    fun publish(event: DomainEvent) {
        applicationEventPublisher.publishEvent(event)
    }
}