package com.nieslregen.block.custom;


public class CustomOccupantData {

    private final CustomOccupant occupant;
    private int ticksInContainer;

    public CustomOccupantData(final CustomOccupant occupant) {
        this.occupant = occupant;
        this.ticksInContainer = occupant.ticksInContainer();
    }

    public boolean tick() {
        return this.ticksInContainer++ > this.occupant.minTicksInContainer();
    }

    public CustomOccupant toOccupant() {
        return new CustomOccupant(this.occupant.entityData(), this.ticksInContainer, this.occupant.minTicksInContainer());
    }

//    public boolean hasNectar() {
//        return this.occupant.entityData.getUnsafe().getBooleanOr("HasNectar", false);
//    }
}
