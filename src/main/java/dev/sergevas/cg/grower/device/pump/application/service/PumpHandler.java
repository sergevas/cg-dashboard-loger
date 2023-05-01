package dev.sergevas.cg.grower.device.pump.application.service;

import dev.sergevas.cg.grower.device.pump.adapter.pi4j.PumpStateControl;
import dev.sergevas.cg.grower.device.pump.model.PumpState;

public class PumpHandler {

    private final PumpStateControl pumpStateControl;

    public PumpHandler(PumpStateControl pumpStateControl) {
        this.pumpStateControl = pumpStateControl;
    }

    public PumpState updateState(PumpState pumpState) {
        this.pumpStateControl.updateState(pumpState);
        return pumpState;
    }
}
