package net.thecallunxz.shadowrite.init;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;
import net.thecallunxz.shadowrite.ItemModelProvider;
import net.thecallunxz.shadowrite.Reference;
import net.thecallunxz.shadowrite.blocks.BlockShadowriteOre;
import net.thecallunxz.shadowrite.items.ItemBase;
import net.thecallunxz.shadowrite.items.ItemFloating;
import net.thecallunxz.shadowrite.items.ItemShadowriteTorch;

@ObjectHolder(Reference.MOD_ID)
public class InitItems {

	public static final ItemFloating shadowrite_singularity = new ItemFloating("shadowrite_singularity", 2.5D);
	public static final ItemBase shadow_fabric = new ItemBase("shadow_fabric");
	public static final ItemBase shadow_cloth = new ItemBase("shadow_cloth");
	public static final ItemShadowriteTorch shadowrite_torch = new ItemShadowriteTorch("shadowrite_torch");
	public static final ItemFloating shadowrite_gem = new ItemFloating("shadowrite_gem", 1D);
	public static final ItemFloating shadowrite_core = new ItemFloating("shadowrite_core", 1D);
	
	@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
	public static class RegistrationHandler {
		public static final Set<Item> ITEMS = new HashSet<>();

		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Item> event) {
			
			final Item[] items = {
					shadowrite_singularity,
					shadowrite_torch,
					shadow_fabric,
					shadow_cloth,
					shadowrite_gem,
					shadowrite_core,
			};
			
			final IForgeRegistry<Item> registry = event.getRegistry();
			for (final Item item : items) {
				if (item instanceof ItemModelProvider) {
					((ItemModelProvider) item).registerItemModel(item);
				}
				registry.register(item);
				ITEMS.add(item);
			}
		}
	}
}
