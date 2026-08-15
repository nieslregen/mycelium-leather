package com.nieslregen.mob;


import com.nieslregen.MyceliumLeatherMod;

public class CustomOccupantData {

    private final CustomOccupant occupant;
    private int ticksInContainer;

    public CustomOccupantData(final CustomOccupant occupant) {
        this.occupant = occupant;
        this.ticksInContainer = occupant.ticksInContainer();
    }

    public boolean tick() {
        MyceliumLeatherMod.LOGGER.info("ticks in container " + this.ticksInContainer);
        return this.ticksInContainer++ > this.occupant.minTicksInContainer();
    }

    public CustomOccupant toOccupant() {
        return new CustomOccupant(this.occupant.entityData(), this.ticksInContainer, this.occupant.minTicksInContainer());
    }

//    public boolean hasNectar() {
//        return this.occupant.entityData.getUnsafe().getBooleanOr("HasNectar", false);
//    }
}
