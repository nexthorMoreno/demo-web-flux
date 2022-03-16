package com.demo.hello;

import org.reactivestreams.Publisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.concurrent.Callable;

/**
 * Working on it....
 */
@Service
public class GreetingService {

	private final ApplicationEventPublisher publisher;

	Callable<String> callableStr = () -> new String("{\"name\":\"Hello Service\"}");

	GreetingService(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

	public Mono<ServerResponse> hello2() {
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue("{\"name\":\"Hello Service\"}"));
	}

	public Mono<ServerResponse> hello3() {
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue("{\"name\":\"Hello Service\"}"));
	}



}
