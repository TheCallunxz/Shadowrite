package net.thecallunxz.shadowrite.proxy;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.thecallunxz.shadowrite.Reference;
import net.thecallunxz.shadowrite.events.CommonEventHandler;
import net.thecallunxz.shadowrite.init.InitBlocks;
import net.thecallunxz.shadowrite.init.InitEntities;
import net.thecallunxz.shadowrite.init.InitLootTables;

public class CommonProxy {
	
	
	
	public void preInit(final FMLPreInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(new CommonEventHandler());
        InitLootTables.register(); 
        InitBlocks.registerTileEntities();
		InitEntities.register();
    }

    public void init(final FMLInitializationEvent e) {
    	
    }


    public void postInit(final FMLPostInitializationEvent e) {
    	
    }
    
    public void registerItemRenderer(Item item, int meta, String id) {
    	 
    }

    public boolean isClient() {
		return false;
	}
}

