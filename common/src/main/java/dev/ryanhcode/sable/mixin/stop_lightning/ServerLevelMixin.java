package dev.ryanhcode.sable.mixin.stop_lightning;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.ryanhcode.sable.api.sublevel.SubLevelContainer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Prevents lightning or skeleton traps from spawning inside plots to avoid inflating spawn rates
 */
@Mixin(ServerLevel.class)
public class ServerLevelMixin {

    @WrapOperation(method="tickChunk", at=@At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;isThundering()Z"))
    private boolean sable$preventLightningInPlot(final ServerLevel instance, final Operation<Boolean> original, @Local final LevelChunk chunk) {
        final SubLevelContainer plotContainer = SubLevelContainer.getContainer((ServerLevel) (Object) this);
        assert plotContainer != null;

        if (plotContainer.getPlot(chunk.getPos()) != null) {
            return false;
        }
        return original.call(instance);
    }

}
