package net.thecallunxz.shadowrite.items;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemShadowriteTorch extends ItemBase {

	public ItemShadowriteTorch(String name) {
		super(name);
		this.maxStackSize = 1;
	}
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(new TextComponentTranslation("tooltip.shadowritetorch").getFormattedText());
    }
	
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
	
}
