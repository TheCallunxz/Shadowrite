package net.thecallunxz.shadowrite.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thecallunxz.shadowrite.init.InitBlocks;
import net.thecallunxz.shadowrite.init.InitItems;
import net.thecallunxz.shadowrite.init.InitSounds;

public class BlockShadowriteOre extends BlockBase {

	public BlockShadowriteOre(String name) {
		super(Material.ROCK, name);
		this.setHardness(5.0F);
		this.setResistance(2000.0F);
		this.setHarvestLevel("pickaxe", 3);
		this.setLightOpacity(1);
		this.setSoundType(SoundType.STONE);
	}
	
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
		if (rand.nextInt(10) == 0)
        {
            worldIn.playSound((double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F), InitSounds.whisper, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.2F, false);
        }

		for (int i = 0; i < 3; ++i)
        {
            double d0 = (double)pos.getX() + rand.nextDouble();
            double d1 = (double)pos.getY() + rand.nextDouble() * 0.5D + 0.5D;
            double d2 = (double)pos.getZ() + rand.nextDouble();
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }
	
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return InitItems.shadowrite_singularity;
    }
	
	public int quantityDropped(Random random)
    {
        return 1;
    }
	
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }
	
	public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
	
	public boolean isFullCube(IBlockState state)
    {
        return false;
    }
	
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
        Block block = iblockstate.getBlock();

        if (this == InitBlocks.shadowrite_ore)
        {
            if (blockState != iblockstate)
            {
                return true;
            }

            if (block == this)
            {
                return false;
            }
        }

        return block == this ? false : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }
}
