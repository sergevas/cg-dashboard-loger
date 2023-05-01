package dev.sergevas.cg.grower.device.pump.adapter.pi4j;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import dev.sergevas.cg.grower.device.pump.model.PumpState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class PumpStateControl implements InitializingBean, DisposableBean {

    private static final Logger LOG = LoggerFactory.getLogger(PumpStateControl.class);

    private static final String DIGITAL_OUTPUT_PUMP_STATE_CONTROL = "digital-output-pump-state-control";
    private static final int PUMP_STATE_CONTROL_GPIO_PIN = 17;

    private Context pi4jContext;
    private DigitalOutput digitalOutput;

    public void updateState(PumpState pumpState) {
        LOG.info("Have got {}", pumpState);


        if ("on".equalsIgnoreCase(pumpState.getState())) {
            LOG.info("Pump ON");
            digitalOutput.high();
        } else if ("off".equalsIgnoreCase(pumpState.getState())) {
            digitalOutput.low();
            LOG.info("Pump OFF");
        }
    }

    @Override
    public void afterPropertiesSet() {
        LOG.info(this.getClass().getName() + ".afterPropertiesSet()...");

        pi4jContext = Pi4J.newAutoContext();
        digitalOutput = DigitalOutput.newBuilder(pi4jContext)
                .address(PUMP_STATE_CONTROL_GPIO_PIN)
                .id(DIGITAL_OUTPUT_PUMP_STATE_CONTROL)
                .shutdown(DigitalState.LOW)
                .provider("pigpio-digital-output")
                .build();
    }

    @Override
    public void destroy() {
        LOG.info(this.getClass().getName() + ".destroy...");
        pi4jContext.shutdown();
    }
}
