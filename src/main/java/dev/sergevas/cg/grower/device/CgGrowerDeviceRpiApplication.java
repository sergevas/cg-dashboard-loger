package dev.sergevas.cg.grower.device;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

//@SpringBootApplication
public class CgGrowerDeviceRpiApplication {

	private final static Class[] autoConfigurationClasses = {
			ReactiveWebServerFactoryAutoConfiguration.class,
			// web
			HttpHandlerAutoConfiguration.class,
			WebFluxAutoConfiguration.class,
			ErrorWebFluxAutoConfiguration.class,
	};

	public static SpringApplication buildApp() {


		return new SpringApplicationBuilder(CgGrowerDeviceRpiApplication.class)
				.sources(autoConfigurationClasses)
				.initializers((GenericApplicationContext applicationContext) -> {
					applicationContext.registerBean(RouterFunction.class, () -> {
						return route()
								.GET("/soil/tmp", request -> {
									return ServerResponse.ok().body("25", String.class);
								})
								.build();
					});
				}).build();
	}

	public static void main(String[] args) {
		buildApp().run(args);
	}
}
