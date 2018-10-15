package net.thecallunxz.shadowrite.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleExplosionLarge;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.thecallunxz.shadowrite.entities.EntityFloatingItem;
import net.thecallunxz.shadowrite.entities.EntityShadowWarrior;
import net.thecallunxz.shadowrite.init.InitItems;
import net.thecallunxz.shadowrite.items.ItemBase;
import net.thecallunxz.shadowrite.items.ItemFloating;
import net.thecallunxz.shadowrite.world.WorldDataShadowrite;

public class CommonEventHandler {
	
	@SubscribeEvent
	public void onEntityJoin(EntityJoinWorldEvent event) {
		if (event.getWorld().isRemote) {
			return;
		}
		
		if (event.getEntity().getClass() == EntityItem.class) {
			EntityItem item = (EntityItem) event.getEntity();
			if (!item.getItem().isEmpty() && item.getItem().getItem() instanceof ItemFloating) {
				EntityFloatingItem itemFloat = new EntityFloatingItem((EntityItem) event.getEntity());
				event.getEntity().setDead();
				itemFloat.setPickupDelay(40);
				event.setResult(Event.Result.DENY);
				event.setCanceled(true);
				event.getWorld().spawnEntity(itemFloat);
				return;
				
			}
		}
	}
	
	@SubscribeEvent
	public void onWorldTick(WorldTickEvent event) {
		if(!event.world.isRemote && event.world.provider.getDimension() == 0) {
			WorldDataShadowrite data = WorldDataShadowrite.get(event.world);
			if(data.hasDaylightReset()) {
				if(!isFullDay(event.world)) {
					if(event.world.getCurrentMoonPhaseFactor() == 0 && data.isShadowsReleased()) {
						data.setDaylightReset(false);
						for(EntityPlayer messagedPlayer : event.world.playerEntities) {
							messagedPlayer.sendMessage(new TextComponentTranslation("message.player.darkmoon").setStyle((new Style()).setObfuscated(true).setColor(TextFormatting.BLACK)));
							messagedPlayer.sendMessage(new TextComponentTranslation("message.player.darkmoon").setStyle((new Style()).setColor(TextFormatting.RED)));
							messagedPlayer.sendMessage(new TextComponentTranslation("message.player.darkmoon").setStyle((new Style()).setObfuscated(true).setColor(TextFormatting.BLACK)));
						}
					}
				}
			}else {
				if(isFullDay(event.world)) {
					data.setDaylightReset(true);
				}
			}
		}
	}
	
	private boolean isFullDay(World world) {
		return world.getSkylightSubtracted() < 6;
	}
	
	@SubscribeEvent
	public void onShadowSpawn(LivingSpawnEvent.CheckSpawn event) {
		if(event.getEntityLiving() instanceof EntityShadowWarrior) {
			WorldDataShadowrite data = WorldDataShadowrite.get(event.getWorld());
			EntityPlayer playerNear = event.getWorld().getNearestAttackablePlayer(event.getX(), event.getY(), event.getZ(), 75, 25, null, null);
			boolean flag = false;
			boolean flag1 = false;
			
			if(playerNear != null) {
				flag = playerNear.getHeldItemMainhand().getItem() == InitItems.shadowrite_torch;
				flag1 = playerNear.getHeldItemOffhand().getItem() == InitItems.shadowrite_torch;
			}
			
			if(!data.isShadowsReleased()) {
				event.setResult(Result.DENY);
			}else if((event.getWorld().getCurrentMoonPhaseFactor() != 0  || event.getWorld().provider.getDimension() != 0) && !flag && !flag1 ){
				if(event.getWorld().rand.nextInt(20) != 0) {
					event.setResult(Result.DENY);
				}
			}
		}
	}
}
