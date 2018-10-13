package net.thecallunxz.shadowrite.entities.ai;

import com.google.common.base.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemShield;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.thecallunxz.shadowrite.entities.EntityShadowWarrior;

public class EntityAIChangeStance extends EntityAIBase {
	private World world;
	private EntityShadowWarrior entitycommon;

	public EntityAIChangeStance(World worldIn, EntityShadowWarrior entitycommon) {
		this.world = worldIn;
		this.entitycommon = entitycommon;
		this.setMutexBits(8);
	}

	@Override
	public boolean shouldContinueExecuting() {
		return true;
	}

	@Override
	public boolean shouldExecute() {
		return true;
	}

	@Override
	public void updateTask() {
		if(entitycommon.getHeldItemOffhand().getItem() instanceof ItemShield) {
			if(entitycommon.getAttackTarget() != null) {
				if(entitycommon.getDistance(entitycommon.getAttackTarget()) < 5) {
					if(entitycommon.ticksExisted % 60 == 0) {
						if(entitycommon.isShieldOut()) {
							entitycommon.setShieldOut(false);
							entitycommon.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
							entitycommon.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0D);
						}else {
							entitycommon.setShieldOut(true);
							entitycommon.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15D);
							entitycommon.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1D);
						}
					}
				}else if(entitycommon.getAttackTarget().getHeldItemMainhand().getItem() instanceof ItemBow) {
					entitycommon.setShieldOut(true);
					entitycommon.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15D);
					entitycommon.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1D);
				}else {
					entitycommon.setShieldOut(false);
					entitycommon.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
					entitycommon.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0D);
				}
			}
		}
	}
}