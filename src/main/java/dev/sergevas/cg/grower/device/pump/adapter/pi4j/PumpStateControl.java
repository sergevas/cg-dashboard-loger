package dev.sergevas.cg.grower.device.pump.adapter.pi4j;

import com.pi4j.io.gpio.*;
import com.pi4j.platform.Platform;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import com.pi4j.platform.PlatformManager;
import dev.sergevas.cg.grower.device.pump.DeviceException;
import dev.sergevas.cg.grower.device.pump.model.PumpState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class PumpStateControl implements InitializingBean, DisposableBean {

    private static final Logger LOG = LoggerFactory.getLogger(PumpStateControl.class);

    private static final String DIGITAL_OUTPUT_PUMP_STATE_CONTROL = "DIGITAL_OUTPUT_PUMP_STATE_CONTROL";

    private static final Pin PUMP_STATE_CONTROL_GPIO_PIN = OrangePiPin.GPIO_07;

    private GpioController gpioController;
    private GpioPinDigitalOutput digitalOutput;

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
        try {
            PlatformManager.setPlatform(Platform.ORANGEPI);
        } catch (PlatformAlreadyAssignedException e) {
            throw new DeviceException(e);
        }
        gpioController = GpioFactory.getInstance();
        digitalOutput = gpioController.provisionDigitalOutputPin(PUMP_STATE_CONTROL_GPIO_PIN, DIGITAL_OUTPUT_PUMP_STATE_CONTROL, PinState.LOW);
        digitalOutput.setShutdownOptions(false, PinState.LOW);
    }

    @Override
    public void destroy() {
        LOG.info(this.getClass().getName() + ".destroy...");
        gpioController.shutdown();
    }
}
