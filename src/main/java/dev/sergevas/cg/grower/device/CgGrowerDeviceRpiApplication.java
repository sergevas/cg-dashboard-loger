package dev.sergevas.cg.grower.device;

import dev.sergevas.cg.grower.device.pump.adapter.pi4j.PumpStateControl;
import dev.sergevas.cg.grower.device.pump.application.service.PumpHandler;
import dev.sergevas.cg.grower.device.pump.model.PumpState;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.MediaType;
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
//        var pumpStateControl = new PumpStateControl();
//        var pumpHandler = new PumpHandler(pumpStateControl);
        return new SpringApplicationBuilder(CgGrowerDeviceRpiApplication.class)
                .sources(autoConfigurationClasses)
                .initializers((GenericApplicationContext applicationContext) -> {
                    applicationContext.registerBean(PumpStateControl.class, PumpStateControl::new);
                    applicationContext.registerBean(PumpHandler.class, () -> new PumpHandler(applicationContext.getBean(PumpStateControl.class)));
                    applicationContext.registerBean(RouterFunction.class, () -> {
                        return route()
                                .PUT("/cg/gateway/device/grower/0001/pump/state", request -> request.bodyToMono(PumpState.class)
//                                        .doOnNext(pumpHandler::updateState)
                                        .doOnNext(applicationContext.getBean(PumpHandler.class)::updateState)
                                        .then(ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).build()))
                                .build();
                    });
                }).build();
    }

    public static void main(String[] args) {
        buildApp().run(args);
    }
}
