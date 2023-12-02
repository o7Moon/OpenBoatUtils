package dev.o7moon.openboatutils;

public enum Modes {
    BROKEN_SLIME_RALLY,
    BROKEN_SLIME_RALLY_BLUE,
    BROKEN_SLIME_BA_NOFD,
    BROKEN_SLIME_PARKOUR,
    BROKEN_SLIME_BA_BLUE_NOFD,
    BROKEN_SLIME_PARKOUR_BLUE,
    BROKEN_SLIME_BA,
    BROKEN_SLIME_BA_BLUE,
    RALLY,
    RALLY_BLUE,
    BA_NOFD,
    PARKOUR,
    BA_BLUE_NOFD,
    PARKOUR_BLUE,
    BA,
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
            case BA_NOFD:
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
            case BA_BLUE_NOFD:
                OpenBoatUtils.setFallDamage(false);
                OpenBoatUtils.setAirControl(true);
                OpenBoatUtils.setBlockSlipperiness("minecraft:air", 0.989f);
                OpenBoatUtils.setStepSize(1.25f);
                OpenBoatUtils.setWaterElevation(true);
                return;
            case PARKOUR_BLUE:
                OpenBoatUtils.setAllBlocksSlipperiness(0.989f);
                OpenBoatUtils.setFallDamage(false);
                OpenBoatUtils.setAirControl(true);
                OpenBoatUtils.setJumpForce(0.36f);
                OpenBoatUtils.setStepSize(0.5f);
                return;
            case BA:
                OpenBoatUtils.setAirControl(true);
                OpenBoatUtils.setBlockSlipperiness("minecraft:air", 0.98f);
                OpenBoatUtils.setStepSize(1.25f);
                OpenBoatUtils.setWaterElevation(true);
                return;
            case BA_BLUE:
                OpenBoatUtils.setAirControl(true);
                OpenBoatUtils.setBlockSlipperiness("minecraft:air", 0.989f);
                OpenBoatUtils.setStepSize(1.25f);
                OpenBoatUtils.setWaterElevation(true);
                return;
            case BROKEN_SLIME_RALLY:
                OpenBoatUtils.setAllBlocksSlipperiness(0.98f);
                OpenBoatUtils.setFallDamage(false);
                OpenBoatUtils.setAirControl(true);
                OpenBoatUtils.setStepSize(1.25f);
                OpenBoatUtils.breakSlimePlease();
                return;
            case BROKEN_SLIME_RALLY_BLUE:
                OpenBoatUtils.setAllBlocksSlipperiness(0.989f);
                OpenBoatUtils.setFallDamage(false);
                OpenBoatUtils.setAirControl(true);
                OpenBoatUtils.setStepSize(1.25f);
                OpenBoatUtils.breakSlimePlease();
                return;
            case BROKEN_SLIME_BA_NOFD:
                OpenBoatUtils.setFallDamage(false);
                OpenBoatUtils.setAirControl(true);
                OpenBoatUtils.setBlockSlipperiness("minecraft:air", 0.98f);
                OpenBoatUtils.setStepSize(1.25f);
                OpenBoatUtils.setWaterElevation(true);
                OpenBoatUtils.breakSlimePlease();
                return;
            case BROKEN_SLIME_PARKOUR:
                OpenBoatUtils.setAllBlocksSlipperiness(0.98f);
                OpenBoatUtils.setFallDamage(false);
                OpenBoatUtils.setAirControl(true);
                OpenBoatUtils.setJumpForce(0.36f);
                OpenBoatUtils.setStepSize(0.5f);
                OpenBoatUtils.breakSlimePlease();
                return;
            case BROKEN_SLIME_BA_BLUE_NOFD:
                OpenBoatUtils.setFallDamage(false);
                OpenBoatUtils.setAirControl(true);
                OpenBoatUtils.setBlockSlipperiness("minecraft:air", 0.989f);
                OpenBoatUtils.setStepSize(1.25f);
                OpenBoatUtils.setWaterElevation(true);
                OpenBoatUtils.breakSlimePlease();
                return;
            case BROKEN_SLIME_PARKOUR_BLUE:
                OpenBoatUtils.setAllBlocksSlipperiness(0.989f);
                OpenBoatUtils.setFallDamage(false);
                OpenBoatUtils.setAirControl(true);
                OpenBoatUtils.setJumpForce(0.36f);
                OpenBoatUtils.setStepSize(0.5f);
                OpenBoatUtils.breakSlimePlease();
                return;
            case BROKEN_SLIME_BA:
                OpenBoatUtils.setAirControl(true);
                OpenBoatUtils.setBlockSlipperiness("minecraft:air", 0.98f);
                OpenBoatUtils.setStepSize(1.25f);
                OpenBoatUtils.setWaterElevation(true);
                OpenBoatUtils.breakSlimePlease();
                return;
            case BROKEN_SLIME_BA_BLUE:
                OpenBoatUtils.setAirControl(true);
                OpenBoatUtils.setBlockSlipperiness("minecraft:air", 0.989f);
                OpenBoatUtils.setStepSize(1.25f);
                OpenBoatUtils.setWaterElevation(true);
                OpenBoatUtils.breakSlimePlease();
                return;
        }
    }
}
