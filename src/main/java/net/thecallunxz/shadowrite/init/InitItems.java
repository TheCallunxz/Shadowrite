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

@ObjectHolder(Reference.MOD_ID)
public class InitItems {

	@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
	public static class RegistrationHandler {
		public static final Set<Item> ITEMS = new HashSet<>();

		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Item> event) {
			final Item[] items = {};
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
