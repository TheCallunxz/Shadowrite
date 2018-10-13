package net.thecallunxz.shadowrite.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.thecallunxz.shadowrite.Reference;

public class InitLootTables {

	public static final ResourceLocation shadowloot = new ResourceLocation(Reference.MOD_ID, "shadowwarrior");
	
	public static void register() {
		 LootTableList.register(shadowloot);
	}	
}
