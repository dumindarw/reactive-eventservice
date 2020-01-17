package com.calamitaid.events.eventsservice.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.calamitaid.events.eventsservice.model.Event;

public interface EventRepository extends ReactiveMongoRepository<Event, UUID>{

}
