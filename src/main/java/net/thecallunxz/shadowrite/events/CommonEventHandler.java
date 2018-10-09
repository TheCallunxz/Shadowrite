package net.thecallunxz.shadowrite.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleExplosionLarge;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.thecallunxz.shadowrite.entities.EntityFloatingItem;
import net.thecallunxz.shadowrite.items.ItemBase;
import net.thecallunxz.shadowrite.items.ItemFloating;

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
}
