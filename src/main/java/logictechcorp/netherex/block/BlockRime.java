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

import logictechcorp.libraryex.block.BlockMod;
import logictechcorp.libraryex.block.HarvestLevel;
import logictechcorp.libraryex.block.HarvestTool;
import logictechcorp.libraryex.block.builder.BlockProperties;
import logictechcorp.libraryex.utility.CollectionHelper;
import logictechcorp.libraryex.utility.EntityHelper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.handler.ConfigHandler;
import logictechcorp.netherex.init.NetherExEffects;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockRime extends BlockMod
{
    public BlockRime()
    {
        super(NetherEx.getResource("rime_block"), new BlockProperties(Material.ROCK, MapColor.LIGHT_BLUE).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(5.0F).resistance(10.0F).lightLevel(0.9375F));
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
    {
        for(int x = -1; x < 2; x++)
        {
            for(int z = -1; z < 2; z++)
            {
                for(int y = -1; y < 2; y++)
                {
                    this.freezeSurroundings(world, pos.add(x, y, z), world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + x, pos.getY() + y, pos.getZ() + z)));
                }
            }
        }

        world.scheduleUpdate(pos, this, MathHelper.getInt(random, 20, 40));
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
        world.scheduleUpdate(pos, this, MathHelper.getInt(RANDOM, 20, 40));
    }

    private void freezeSurroundings(World world, BlockPos pos, List<EntityLivingBase> entities)
    {
        IBlockState state = world.getBlockState(pos);

        if(state == Blocks.WATER.getDefaultState() && ConfigHandler.blockConfig.rime.canFreezeWater)
        {
            world.setBlockState(pos, Blocks.ICE.getDefaultState(), 3);
        }
        else if(state == Blocks.LAVA.getDefaultState() && ConfigHandler.blockConfig.rime.canFreezeLava)
        {
            world.setBlockState(pos, Blocks.MAGMA.getDefaultState(), 3);
        }

        for(EntityLivingBase entity : entities)
        {
            boolean canFreeze = !(entity instanceof EntityPlayer) && !CollectionHelper.contains(ConfigHandler.potionEffectConfig.freeze.mobBlacklist, EntityHelper.getEntityLocation(entity));

            if(canFreeze && ConfigHandler.blockConfig.rime.canFreezeMobs)
            {
                entity.addPotionEffect(new PotionEffect(NetherExEffects.FREEZE, 300, 0));
            }
        }
    }
}
