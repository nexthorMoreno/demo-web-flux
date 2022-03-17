package com.demo.hello;

import org.json.JSONException;
import org.json.JSONObject;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
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

	public Mono<ServerResponse> hello5(ServerRequest request) {
		//prints request data
/*		request.bodyToMono(String.class)
				.subscribe(item -> System.out.println("Body" + item));*/

		Mono<JSONObject> reqJSONObject = getReqJSONObject(request);
		printJObj(reqJSONObject);


		return Mono.empty();

		/* //prints without changes
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromDataBuffers(dataBuffer));
*/

/*		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromDataBuffers(request
						.body(BodyExtractors.toDataBuffers())));*/
	}

	private Mono<JSONObject> getReqJSONObject(ServerRequest request) throws JSONException {
		Mono<String>  body = request.bodyToMono(String.class);
		return body.map(item -> new JSONObject(item));
	}

	public void printJObj(Mono<JSONObject> jsonObjectMono) {
		jsonObjectMono.subscribe(item -> System.out.println(item));
	}

	public Mono<ServerResponse> hello(ServerRequest request) {
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromDataBuffers(request.body(BodyExtractors.toDataBuffers())));
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


/*	Mono<Void> fileWritten = WebClient.create().post()
			.uri(uriBuilder -> uriBuilder.path("/file/").build())
			.exchange()
			.flatMap(response -> {
				if (MediaType.APPLICATION_JSON_UTF8.equals(response.headers().contentType().get())) {
					Mono<NoPayloadResponseDto> dto = response.bodyToMono(NoPayloadResponseDto.class);
					return createErrorFile(dto);
				}
				else {
					Flux<DataBuffer> body = response.bodyToFlux(DataBuffer.class);
					return createSpreadsheet(body);
				}
			});*/
}