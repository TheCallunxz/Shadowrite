package net.thecallunxz.shadowrite.items;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thecallunxz.shadowrite.init.InitItems;
import net.thecallunxz.shadowrite.networking.ShadowritePacket;
import net.thecallunxz.shadowrite.networking.server.ShadowJumpServer;
import net.thecallunxz.shadowrite.util.ShadowClientUtil;

public class ItemShadowFeather extends ItemBase {

	public ItemShadowFeather(String name) {
		super(name);
		this.maxStackSize = 1;
	}
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(new TextComponentTranslation("tooltip.shadowfeather").getFormattedText());
    }

	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if(entityIn instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityIn;
			if(itemSlot < 9) {
				boolean first = true;
				for(int i = 0; i < itemSlot; i++) {
					if(player.inventory.getStackInSlot(i).getItem() == InitItems.shadow_feather) {
						first = false;
						break;
					}
				}
				
				if(first) {
					if(player.motionY < 0.0D && !player.onGround && player.isSneaking() && !player.capabilities.isFlying) {
						player.motionY *= 0.5D;
					}
					player.fallDistance = Math.min(1, player.fallDistance);
				}
			}
		}
    }
	
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
	
}
