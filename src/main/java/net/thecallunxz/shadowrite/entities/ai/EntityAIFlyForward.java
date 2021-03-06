package net.thecallunxz.shadowrite.entities.ai;

import com.google.common.base.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.thecallunxz.shadowrite.entities.EntityShadowWarrior;

public class EntityAIFlyForward extends EntityAIBase {
	private World world;
	private EntityMob entitycommon;

	public EntityAIFlyForward(World worldIn, EntityMob entitycommon) {
		this.world = worldIn;
		this.entitycommon = entitycommon;
		this.setMutexBits(4);
	}

	@Override
	public boolean shouldContinueExecuting() {
		return !entitycommon.onGround;
	}

	@Override
	public boolean shouldExecute() {
		return !entitycommon.onGround;
	}

	@Override
	public void updateTask() {
		if(!entitycommon.onGround) {
			if(entitycommon.getAttackTarget() != null) {
				if(entitycommon.getDistance(entitycommon.getAttackTarget()) < 10) {

					Vec3d commonVec = entitycommon.getPositionVector();
					Vec3d victimVec = entitycommon.getAttackTarget().getPositionVector();
					
					Vec3d vecPull = victimVec.subtract(commonVec).normalize();
					
					if(entitycommon instanceof EntityShadowWarrior) {
						if(((EntityShadowWarrior) entitycommon).isShieldOut()) {
							entitycommon.motionX = vecPull.x * 0.1;
							entitycommon.motionZ = vecPull.z * 0.1;
						}else {
							entitycommon.motionX = vecPull.x * 0.2;
							entitycommon.motionZ = vecPull.z * 0.2;
						}
					}else {
						entitycommon.motionX = vecPull.x * 0.2;
						entitycommon.motionZ = vecPull.z * 0.2;
					}
				}
			}
		}
	}
}