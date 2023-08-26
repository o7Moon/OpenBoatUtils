package dev.o7moon.openboatutils;

import java.util.ArrayList;

public enum Modes {
    RALLY,
    RALLY_BLUE,
    BA,
    PARKOUR;

    public static void setMode(Modes mode) {
        switch (mode){
            case RALLY:
                OpenBoatUtils.SetAllBlocksSlipperiness(0.98f);
                OpenBoatUtils.setFallDamage(false);
                OpenBoatUtils.setAirControl(true);
                OpenBoatUtils.SetStepSize(1.25f);
                return;
            case RALLY_BLUE:
                OpenBoatUtils.SetAllBlocksSlipperiness(0.989f);
                OpenBoatUtils.setFallDamage(false);
                OpenBoatUtils.setAirControl(true);
                OpenBoatUtils.SetStepSize(1.25f);
                return;
            case BA:
                OpenBoatUtils.setFallDamage(false);
                OpenBoatUtils.setAirControl(true);
                OpenBoatUtils.SetBlockSlipperiness("minecraft:air", 0.98f);
                OpenBoatUtils.SetStepSize(1.25f);
                OpenBoatUtils.setWaterElevation(true);
                return;
            case PARKOUR:
                OpenBoatUtils.SetAllBlocksSlipperiness(0.98f);
                OpenBoatUtils.setFallDamage(false);
                OpenBoatUtils.setAirControl(true);
                OpenBoatUtils.setJumpForce(0.36f);
                OpenBoatUtils.SetStepSize(0.5f);
                return;
        }
    }
}
