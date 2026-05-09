package dev.ryanhcode.sable.mixin.impact;

import dev.ryanhcode.sable.api.block.BlockWithSubLevelCollisionCallback;
import dev.ryanhcode.sable.api.physics.callback.BlockSubLevelCollisionCallback;
import dev.ryanhcode.sable.physics.callback.DestructiveBlockCallback;
import dev.ryanhcode.sable.physics.callback.ExplosiveBlockCallback;
import net.minecraft.world.level.block.BellBlock;
import net.minecraft.world.level.block.StainedGlassBlock;
import net.minecraft.world.level.block.TntBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(StainedGlassBlock.class)
public abstract class StainedGlassBlockMixin implements BlockWithSubLevelCollisionCallback {

    @Override
    public BlockSubLevelCollisionCallback sable$getCallback() {
        return DestructiveBlockCallback.INSTANCE;
    }

}
