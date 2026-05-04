package dev.ryanhcode.sable.neoforge.mixin.compatibility.create.water_wheel_structure;

import com.simibubi.create.content.kinetics.waterwheel.WaterWheelStructuralBlock;
import dev.ryanhcode.sable.api.block.BlockSubLevelAssemblyListener;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(WaterWheelStructuralBlock.class)
public abstract class WaterWheelStructuralBlockMixin extends DirectionalBlock implements BlockSubLevelAssemblyListener {

    @Shadow
    public abstract boolean stillValid(BlockGetter level, BlockPos pos, BlockState state, boolean directlyAdjacent);

    protected WaterWheelStructuralBlockMixin(Properties p_52591_) {
        super(p_52591_);
    }

    public void afterMove(final ServerLevel originLevel, final ServerLevel resultingLevel, final BlockState newState, final BlockPos oldPos, final BlockPos newPos) {
    }

    @Override
    public void afterAssemble(final ServerLevel originLevel, final ServerLevel resultingLevel, final BlockState newState, final BlockPos oldPos, final BlockPos newPos) {
        if (!this.stillValid(resultingLevel, newPos, newState, false)) {
            resultingLevel.setBlockAndUpdate(newPos, Blocks.AIR.defaultBlockState());
        }
    }

}
