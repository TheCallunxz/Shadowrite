package net.thecallunxz.shadowrite.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thecallunxz.shadowrite.entities.EntityFloatingItem;
import net.thecallunxz.shadowrite.init.InitBlocks;
import net.thecallunxz.shadowrite.init.InitItems;
import net.thecallunxz.shadowrite.init.InitSounds;
import net.thecallunxz.shadowrite.world.WorldDataShadowrite;

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
	
	@SideOnly(Side.CLIENT)
    public boolean addDestroyEffects(World world, BlockPos pos, net.minecraft.client.particle.ParticleManager manager)
    {
		for (int i = 0; i < 150; ++i)
	    {
	        double d0 = (double)pos.getX() + world.rand.nextDouble();
	        double d1 = (double)pos.getY() + world.rand.nextDouble();
	        double d2 = (double)pos.getZ() + world.rand.nextDouble();
	        world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0, d1, d2, world.rand.nextDouble()/2 - 0.25, 0.0D, world.rand.nextDouble()/2 - 0.25);
	    }
		
		for (int i = 0; i < 150; ++i)
	    {
	        double d0 = (double)pos.getX() + world.rand.nextDouble();
	        double d1 = (double)pos.getY() + world.rand.nextDouble();
	        double d2 = (double)pos.getZ() + world.rand.nextDouble();
	        world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0, d1, d2, world.rand.nextDouble() - 0.5, 0.0D, world.rand.nextDouble() - 0.5);
	    }
		
		world.playSound((double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F, false);
		
	    return false;
    }
	
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return null;
    }
	
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
		super.harvestBlock(worldIn, player, pos, state, te, stack);
		worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
		EntityFloatingItem itemFloat = new EntityFloatingItem(worldIn, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, new ItemStack(InitItems.shadowrite_singularity));
		itemFloat.setPickupDelay(60);
		worldIn.spawnEntity(itemFloat);
		
		WorldDataShadowrite data = WorldDataShadowrite.get(worldIn);
		if(!data.isShadowsReleased()) {
			data.setShadowsReleased(true);
			for(EntityPlayer messagedPlayer : worldIn.playerEntities) {
				messagedPlayer.sendMessage(new TextComponentTranslation("message.player.shadowsreleased").setStyle((new Style()).setObfuscated(true).setColor(TextFormatting.BLACK)));
				messagedPlayer.sendMessage(new TextComponentTranslation("message.player.shadowsreleased").setStyle((new Style()).setColor(TextFormatting.RED)));
				messagedPlayer.sendMessage(new TextComponentTranslation("message.player.shadowsreleased").setStyle((new Style()).setObfuscated(true).setColor(TextFormatting.BLACK)));
			}
		}
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
