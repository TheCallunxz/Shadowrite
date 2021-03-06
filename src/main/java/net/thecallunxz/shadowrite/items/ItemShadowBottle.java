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

public class ItemShadowBottle extends ItemBase {

	public ItemShadowBottle(String name) {
		super(name);
		this.maxStackSize = 1;
		this.setMaxDamage(1000);
	}
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(new TextComponentTranslation("tooltip.shadowbottle").getFormattedText());
    }
	
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if(entityIn.onGround) {
			stack.setItemDamage(0);
		}
		
		if(worldIn.isRemote) {
			if(entityIn instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entityIn;
				if(itemSlot < 9) {
					boolean first = true;
					for(int i = 0; i < itemSlot; i++) {
						if(player.inventory.getStackInSlot(i).getItem() == InitItems.shadow_bottle) {
							if(player.inventory.getStackInSlot(i).getItemDamage() == 0) {
								first = false;
								break;
							}
						}
					}
					
					if(stack.getItemDamage() != 0) {
						first = false;
					}
					
					if(first) {
						if(ShadowClientUtil.checkJumpWithoutSneak() && (!player.onGround)) {
							ShadowritePacket.INSTANCE.sendToServer(new ShadowJumpServer(itemSlot));
							stack.setItemDamage(999);
							player.onGround = false;
							player.motionY = 0.6D;
							player.fallDistance = -1;
						}
					}
				}
			}
		}
    }
	
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
}
