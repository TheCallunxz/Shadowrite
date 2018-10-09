package net.thecallunxz.shadowrite.items;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.thecallunxz.shadowrite.entities.EntityFloatingItem;

public class ItemFloating extends ItemBase {

	public ItemFloating(String name) {
		super(name);
	}

	@Nullable
    public Entity createEntity(World world, Entity location, ItemStack itemstack)
    {
		System.out.println("WORKING");
        return new EntityFloatingItem(world, location.posX, location.posY, location.posZ, itemstack);
    }
}
