package com.calamitaid.events.eventsservice;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.calamitaid.events.eventsservice.model.Event;
import com.calamitaid.events.eventsservice.repository.EventRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

//@EnableEurekaClient
@Slf4j
@SpringBootApplication
public class EventsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventsServiceApplication.class, args);
	}
	
	@Bean
	ApplicationRunner init(EventRepository eventRepository) {
		
		Event evt1 = new Event(UUID.randomUUID(),"Flood near Thummulla", LocalDate.now(), "Duminda", "Kurunegala");
		Event evt2 = new Event(UUID.randomUUID(), "Flood near Galle town", LocalDate.now(), "Rajitha", "Galle");
		
		Set<Event> events = Set.of(evt1, evt2);
		
		return args -> {
			eventRepository.deleteAll()
			.thenMany(Flux.just(events).flatMap(eventRepository::saveAll))
			.thenMany(eventRepository.findAll())
			.subscribe(event -> log.info("Saving" + event.toString()));
		};
	}

}
