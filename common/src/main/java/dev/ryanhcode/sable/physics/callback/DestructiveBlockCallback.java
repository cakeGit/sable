package dev.ryanhcode.sable.physics.callback;

import dev.ryanhcode.sable.Sable;
import dev.ryanhcode.sable.api.physics.callback.BlockSubLevelCollisionCallback;
import dev.ryanhcode.sable.api.physics.constraint.PhysicsConstraintHandle;
import dev.ryanhcode.sable.api.physics.handle.RigidBodyHandle;
import dev.ryanhcode.sable.companion.math.JOMLConversion;
import dev.ryanhcode.sable.mixinterface.block_properties.BlockStateExtension;
import dev.ryanhcode.sable.physics.config.block_properties.PhysicsBlockPropertyTypes;
import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import dev.ryanhcode.sable.sublevel.SubLevel;
import dev.ryanhcode.sable.sublevel.system.SubLevelPhysicsSystem;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3d;

public class DestructiveBlockCallback implements BlockSubLevelCollisionCallback {

    public static final DestructiveBlockCallback INSTANCE = new DestructiveBlockCallback();

    protected DestructiveBlockCallback() {}

    public double getTriggerVelocity() {
        return 4.0;
    }

    @Override
    public CollisionResult sable$onCollision(final BlockPos pos, final Vector3d pos1, final BlockPos FUCK, final Vector3d otherposshit, final double impactVelocity) {
        final double triggerVelocity = this.getTriggerVelocity();

        if (impactVelocity * impactVelocity < triggerVelocity * triggerVelocity) {
            return CollisionResult.NONE;
        }

        final SubLevelPhysicsSystem system = SubLevelPhysicsSystem.getCurrentlySteppingSystem();
        final ServerLevel level = system.getLevel();

        level.destroyBlock(FUCK, true);
        system.wakeUpObjectsAt(FUCK.getX(), FUCK.getY(), FUCK.getZ());

        //Get this sublevel, and decrease speed a little bit
        final SubLevel subLevel = Sable.HELPER.getContaining(level, pos);

        if (subLevel instanceof ServerSubLevel serverSubLevel) {
            final RigidBodyHandle handle = system.getPhysicsHandle(serverSubLevel);
            system.wakeUpObjectsAt(pos.getX(), pos.getY(), pos.getZ());
            handle.applyImpulseAtPoint(JOMLConversion.toJOML(pos.getCenter()), handle.getLinearVelocity(new Vector3d()).mul(-0.01));
        }

        return new CollisionResult(JOMLConversion.ZERO, true);
    }
}
