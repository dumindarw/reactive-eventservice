package com.calamitaid.events.eventsservice.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.calamitaid.events.eventsservice.model.Event;
import com.calamitaid.events.eventsservice.repository.EventRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class EventController {
	
	private EventRepository eventRepository;
	
	public EventController(EventRepository repo) {
		this.eventRepository = repo;
	}
	
	@PostMapping("/events")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Event> addEvent(@RequestBody Event event){
		return eventRepository.save(event);
	}
	
	@GetMapping("/events")
	public Flux<Event> getEvents(){
		return eventRepository.findAll();
	}
	
	@DeleteMapping("/events/{id}")
	public Mono<ResponseEntity<Void>> deleteEvent(@PathVariable("id") UUID eventId){
		
		return eventRepository.findById(eventId)
				.flatMap(event -> eventRepository.delete(event)
						.then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
				.defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
		
	}

}
