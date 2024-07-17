package dev.o7moon.openboatutils.mixin;

import dev.o7moon.openboatutils.OpenBoatUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public class EntityMixin {

    @Shadow
    private EntityType<?> type;

    /**
     * @author Silly
     * @reason Silly
     */
    @Overwrite
    public float getStepHeight() {
        if (type == EntityType.BOAT || type == EntityType.CHEST_BOAT) {
            return OpenBoatUtils.currentStepHeight;
        }

        return 0f;
    }
}
