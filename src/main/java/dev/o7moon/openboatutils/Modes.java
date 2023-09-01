package dev.o7moon.openboatutils;

public enum Modes {
    RALLY,
    RALLY_BLUE,
    BA,
    PARKOUR,
    BA_BLUE;

    public static void setMode(Modes mode) {
        switch (mode){
            case RALLY:
                OpenBoatUtils.setAllBlocksSlipperiness(0.98f);
                OpenBoatUtils.setFallDamage(false);
                OpenBoatUtils.setAirControl(true);
                OpenBoatUtils.setStepSize(1.25f);
                return;
            case RALLY_BLUE:
                OpenBoatUtils.setAllBlocksSlipperiness(0.989f);
                OpenBoatUtils.setFallDamage(false);
                OpenBoatUtils.setAirControl(true);
                OpenBoatUtils.setStepSize(1.25f);
                return;
            case BA:
                OpenBoatUtils.setFallDamage(false);
                OpenBoatUtils.setAirControl(true);
                OpenBoatUtils.setBlockSlipperiness("minecraft:air", 0.98f);
                OpenBoatUtils.setStepSize(1.25f);
                OpenBoatUtils.setWaterElevation(true);
                return;
            case PARKOUR:
                OpenBoatUtils.setAllBlocksSlipperiness(0.98f);
                OpenBoatUtils.setFallDamage(false);
                OpenBoatUtils.setAirControl(true);
                OpenBoatUtils.setJumpForce(0.36f);
                OpenBoatUtils.setStepSize(0.5f);
                return;
            case BA_BLUE:
                OpenBoatUtils.setFallDamage(false);
                OpenBoatUtils.setAirControl(true);
                OpenBoatUtils.setBlockSlipperiness("minecraft:air", 0.989f);
                OpenBoatUtils.setStepSize(1.25f);
                OpenBoatUtils.setWaterElevation(true);
                return;
        }
    }
}
