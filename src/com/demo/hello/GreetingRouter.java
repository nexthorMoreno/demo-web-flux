package com.demo.hello;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration(proxyBeanMethods = false)
public class GreetingRouter {
	public static final String NAMESPACE = "master";
	public static final String API_VERSION = "/v1";
	public static final String NAMESPACE_VERSION = NAMESPACE + API_VERSION;

	@Bean
	public RouterFunction<ServerResponse> route(GreetingHandler greetingHandler) {

		return RouterFunctions
			.route(GET("/asyncapi/service").and(accept(MediaType.APPLICATION_JSON)), greetingHandler::hello1)
			.andRoute(GET("/hello").and(accept(MediaType.APPLICATION_JSON)), greetingHandler::hello)
				.andRoute(GET("/hello2/{name}").and(accept(MediaType.APPLICATION_JSON)), greetingHandler::hello2)
			.andRoute(GET("/hello3").and(accept(MediaType.APPLICATION_JSON)), greetingHandler::hello3);
	}
}
