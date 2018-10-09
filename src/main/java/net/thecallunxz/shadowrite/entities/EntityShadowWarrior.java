package net.thecallunxz.shadowrite.entities;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thecallunxz.shadowrite.entities.ai.EntityAIFlyForward;

public class EntityShadowWarrior extends EntityMob {
	private static final DataParameter<Boolean> SWINGING_ARMS = EntityDataManager.<Boolean>createKey(EntityShadowWarrior.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> WARRIORTIER = EntityDataManager.<Integer>createKey(EntityShadowWarrior.class, DataSerializers.VARINT);
		
	
	public EntityShadowWarrior(World worldIn) {
		super(worldIn);
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIFlyForward(worldIn, this));
		this.tasks.addTask(3, new AIAttack(this));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {EntityShadowWarrior.class}));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false));
        this.setTier(world.rand.nextInt(4));
        
	}
	
	@Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
		this.setWeaponAndArmour();
		this.setEnchantmentBasedOnDifficulty(difficulty);
		return livingdata;
    }

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(50D);
	}

	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(SWINGING_ARMS, Boolean.valueOf(false));
		this.dataManager.register(WARRIORTIER, Integer.valueOf(0));
	}
	
	public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if (!this.onGround && this.motionY < 0.0D)
        {
            this.motionY *= 0.4D;
        }
    }
	
	public void setWeaponAndArmour() {
		double armour = 0;
		switch(getTier()) {
		case 0:
			this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.WOODEN_SWORD));
			if(world.rand.nextBoolean()) {
				this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
			}
			this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1D);
			this.experienceValue = 3;
			break;
		case 1:
			this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
			if(world.rand.nextBoolean()) {
				this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
			}
			if(world.rand.nextBoolean()) {
				this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET));
				armour += 2D;
			}
			if(world.rand.nextBoolean()) {
				this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.LEATHER_CHESTPLATE));
				armour += 2D;
			}
			this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2D);
			this.experienceValue = 5;
			break;
		case 2:
			this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
			if(world.rand.nextBoolean()) {
				this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
			}
			if(world.rand.nextBoolean()) {
				this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
				armour += 3D;
			}
			if(world.rand.nextBoolean()) {
				this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
				armour += 3D;
			}
			this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3D);
			this.experienceValue = 7;
			break;
		case 3:
			this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
			if(world.rand.nextBoolean()) {
				this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
			}
			if(world.rand.nextBoolean()) {
				this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
				armour += 4D;
			}
			if(world.rand.nextBoolean()) {
				this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
				armour += 4D;
			}
			this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4D);
			this.experienceValue = 10;
			break;
		}
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2D + armour);
		this.setDropChance(EntityEquipmentSlot.MAINHAND, 0.0F);
		this.setDropChance(EntityEquipmentSlot.OFFHAND, 0.0F);
		this.setDropChance(EntityEquipmentSlot.HEAD, 0.0F);
		this.setDropChance(EntityEquipmentSlot.CHEST, 0.0F);
	}

    public void fall(float distance, float damageMultiplier)
    {
    }
    
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (super.attackEntityFrom(source, amount))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

	@Override
	public int getMaxFallHeight() {
		return 100;
	}

	protected DamageSource getDamageSource() {
		return new EntityDamageSource("mob", this);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return null;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return null;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return null;
	}

	public int getTier() {
		return this.dataManager.get(WARRIORTIER);
	}
	
	public void setTier(int tier) {
		this.dataManager.set(WARRIORTIER, Integer.valueOf(tier));
	}

	@SideOnly(Side.CLIENT)
	public boolean isSwingingArms() {
		return ((Boolean) this.dataManager.get(SWINGING_ARMS)).booleanValue();
	}
	
	public void setSwingingArms(boolean swingingArms) {
		this.dataManager.set(SWINGING_ARMS, Boolean.valueOf(swingingArms));
	}

	static class AIAttack extends EntityAIAttackMelee {
		public AIAttack(EntityShadowWarrior infected) {
			super(infected, 1.0D, true);
		}

		@Override
		protected void checkAndPerformAttack(EntityLivingBase living, double number) {
			Random rand = new Random();
			double d0 = this.getAttackReachSqr(living);
			if (number <= (d0 * 0.75) && this.attackTick <= 0) {
				this.attackTick = 20;
				this.attacker.swingArm(EnumHand.MAIN_HAND);
				living.attackEntityFrom(DamageSource.causeMobDamage(this.attacker), (float) this.attacker.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
				living.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 200, 0, true, false));
			}
		}

		protected double getAttackReachSqr(EntityLivingBase attackTarget) {
			return (double) (2.0F + attackTarget.width);
		}
	}
}