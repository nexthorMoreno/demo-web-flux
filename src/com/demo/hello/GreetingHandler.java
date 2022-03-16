package com.demo.hello;

import org.json.JSONObject;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

import java.util.concurrent.Callable;

@Component
public class GreetingHandler {

	@Autowired
	private GreetingService greetingService;

	@Autowired
	private GreetingClient webClient;

	public Mono<ServerResponse> hello(ServerRequest request) {
		webClient.getMessage();

		System.out.println(request.bodyToMono(JSONObject.class).toString());

		System.out.println("MESSAGE: " + webClient.getMessage());

		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(new String("{\"name\":\"Hello\"}"))) ;
	}


	public Mono<ServerResponse> hello1(ServerRequest request) {
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(new String("{\"name\":\"Hello\"}"))) ;
	}


	public Mono<ServerResponse> hello2(ServerRequest request) {
		String name = request.pathVariable("name");




		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue("{\"name\":\"" + name + " \"}")) ;
	}

	/**
	 * still don't working
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> hello4(ServerRequest request) {
		Mono<String> response = Mono.just("{\"name\":\"Hello\"}");
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.bodyValue(Mono.fromCallable((Callable<? extends Mono<String>>) greetingService.hello3()));

	}

	/**
	 * still don't working
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> hello3(ServerRequest request) {
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(Mono.fromCallable(greetingService.callableStr));
	}


	private static Mono<ServerResponse> defaultReadResponse(Publisher<String> profiles) {
		return ServerResponse
				.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(profiles, String.class);
	}
}