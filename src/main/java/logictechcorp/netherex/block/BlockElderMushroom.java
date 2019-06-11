/*
 * NetherEx
 * Copyright (c) 2016-2019 by LogicTechCorp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package logictechcorp.netherex.block;

import logictechcorp.libraryex.api.world.generation.trait.IBiomeTrait;
import logictechcorp.libraryex.block.BlockModSmallMushroom;
import logictechcorp.libraryex.block.property.BlockProperties;
import logictechcorp.libraryex.world.generation.trait.BiomeTraitBigMushroom;
import logictechcorp.netherex.init.NetherExBlocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

import java.util.Random;

public class BlockElderMushroom extends BlockModSmallMushroom
{
    public BlockElderMushroom(ResourceLocation registryName)
    {
        super(registryName, new BlockProperties(Material.PLANTS, MapColor.SNOW).sound(SoundType.PLANT), EnumPlantType.Nether);
    }

    @Override
    public void grow(World world, Random random, BlockPos pos, IBlockState state)
    {
        world.setBlockToAir(pos);
        IBiomeTrait elderMushroom = null;

        if(this == NetherExBlocks.BROWN_ELDER_MUSHROOM)
        {
            elderMushroom = BiomeTraitBigMushroom.create(trait ->
            {
                trait.generationAttempts(1);
                trait.mushroomCap(NetherExBlocks.BROWN_ELDER_MUSHROOM_CAP.getDefaultState());
                trait.mushroomStem(NetherExBlocks.ELDER_MUSHROOM_STEM.getDefaultState());
                trait.blockToPlaceOn(world.getBlockState(pos.down()));
                trait.shape(BiomeTraitBigMushroom.Shape.FLAT);
            });
        }
        else if(this == NetherExBlocks.RED_ELDER_MUSHROOM)
        {
            elderMushroom = BiomeTraitBigMushroom.create(trait -> {
                trait.generationAttempts(1);
                trait.mushroomCap(NetherExBlocks.RED_ELDER_MUSHROOM_CAP.getDefaultState());
                trait.mushroomStem(NetherExBlocks.ELDER_MUSHROOM_STEM.getDefaultState());
                trait.blockToPlaceOn(world.getBlockState(pos.down()));
                trait.shape(BiomeTraitBigMushroom.Shape.BULB);
            });
        }

        if(elderMushroom != null && !elderMushroom.generate(world, pos, random))
        {
            world.setBlockState(pos, state);
        }
    }
}
