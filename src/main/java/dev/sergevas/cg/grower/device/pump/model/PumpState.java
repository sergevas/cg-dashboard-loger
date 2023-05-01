package dev.sergevas.cg.grower.device.pump.model;

import java.util.Objects;
import java.util.StringJoiner;

public class PumpState {
    private String state;

    public String getState() {
        return state;
    }

    public PumpState setState(String state) {
        this.state = state;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PumpState pump = (PumpState) o;
        return Objects.equals(state, pump.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PumpState.class.getSimpleName() + "[", "]")
                .add("state='" + state + "'")
                .toString();
    }
}
