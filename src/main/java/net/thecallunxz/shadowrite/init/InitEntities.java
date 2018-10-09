package net.thecallunxz.shadowrite.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thecallunxz.shadowrite.Main;
import net.thecallunxz.shadowrite.Reference;
import net.thecallunxz.shadowrite.entities.EntityFloatingItem;
import net.thecallunxz.shadowrite.entities.EntityShadowWarrior;
import net.thecallunxz.shadowrite.render.RenderFloatingItem;
import net.thecallunxz.shadowrite.render.RenderShadowWarrior;


public class InitEntities {
	
	public static void register() {
		registerEntity(EntityFloatingItem.class, "floatingitem", 64, 1, true, false);
		
		registerEntity(EntityShadowWarrior.class, "shadowwarrior", 80, 3, false, true);
    }
	
	@SideOnly(Side.CLIENT)
    public static void initModels() {
		RenderingRegistry.registerEntityRenderingHandler(EntityShadowWarrior.class, RenderShadowWarrior.FACTORY);
    }
	
	private static int entityID = 0;
	
	/**
	 * Register an entity with the specified tracking values.
	 *
	 * @param entityClass          The entity's class
	 * @param entityName           The entity's unique name
	 * @param trackingRange        The range at which MC will send tracking updates
	 * @param updateFrequency      The frequency of tracking updates
	 * @param sendsVelocityUpdates Whether to send velocity information packets as well
	 */
	private static void registerEntity(Class<? extends Entity> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, boolean monsterEgg) {
		if(monsterEgg) {
			EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + entityName), entityClass, entityName, entityID++, Main.instance, trackingRange, updateFrequency, sendsVelocityUpdates, 0xFFFFFF, 0xFFFFFF);
		}else {
			EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + entityName), entityClass, entityName, entityID++, Main.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
		}
	}

}
