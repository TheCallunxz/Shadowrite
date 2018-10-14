package net.thecallunxz.shadowrite.world;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.thecallunxz.shadowrite.init.InitBlocks;

public class OreGenSingle extends WorldGenerator
    {
        @Override
        public boolean generate(World worldIn, Random rand, BlockPos blockpos)
        {
        	if(rand.nextBoolean()) {
                net.minecraft.block.state.IBlockState state = worldIn.getBlockState(blockpos);
                if (state.getBlock().isReplaceableOreGen(state, worldIn, blockpos, net.minecraft.block.state.pattern.BlockMatcher.forBlock(Blocks.STONE)))
                {
                    worldIn.setBlockState(blockpos, InitBlocks.shadowrite_ore.getDefaultState(), 16 | 2);
                }
        	}
            return true;
        }
    }