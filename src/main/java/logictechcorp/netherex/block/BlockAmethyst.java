/*
 * NetherEx
 * Copyright (c) 2016-2020 by LogicTechCorp
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
import logictechcorp.libraryex.block.property.BlockProperties;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.entity.neutral.EntityGoldGolem;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMaterialMatcher;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAmethyst extends BlockMod
{
    private BlockPattern golemBasePattern;
    private BlockPattern golemPattern;

    public BlockAmethyst()
    {
        super(NetherEx.getResource("amethyst_block"), new BlockProperties(Material.IRON, MapColor.PURPLE).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.IRON).hardness(5.0F).resistance(10.0F));
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
        this.tryToSpawnGolem(world, pos);
    }

    private void tryToSpawnGolem(World world, BlockPos pos)
    {
        BlockPattern.PatternHelper golemPattern = this.getGolemPattern().match(world, pos);

        if(golemPattern != null)
        {
            for(int palmOffset = 0; palmOffset < this.getGolemPattern().getPalmLength(); palmOffset++)
            {
                for(int thumbOffset = 0; thumbOffset < this.getGolemPattern().getThumbLength(); thumbOffset++)
                {
                    world.setBlockState(golemPattern.translateOffset(palmOffset, thumbOffset, 0).getPos(), Blocks.AIR.getDefaultState(), 2);
                }
            }

            BlockPos blockpos = golemPattern.translateOffset(1, 2, 0).getPos();
            EntityGoldGolem goldGolem = new EntityGoldGolem(world);
            goldGolem.setPlayerCreated(true);
            goldGolem.setLocationAndAngles((double) blockpos.getX() + 0.5D, (double) blockpos.getY() + 0.05D, (double) blockpos.getZ() + 0.5D, 0.0F, 0.0F);
            world.spawnEntity(goldGolem);

            for(EntityPlayerMP player : world.getEntitiesWithinAABB(EntityPlayerMP.class, goldGolem.getEntityBoundingBox().grow(5.0D)))
            {
                CriteriaTriggers.SUMMONED_ENTITY.trigger(player, goldGolem);
            }

            for(int i = 0; i < 120; i++)
            {
                world.spawnParticle(EnumParticleTypes.SNOWBALL, (double) blockpos.getX() + world.rand.nextDouble(), (double) blockpos.getY() + world.rand.nextDouble() * 3.9D, (double) blockpos.getZ() + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
            }

            for(int palmOffset = 0; palmOffset < this.getGolemPattern().getPalmLength(); palmOffset++)
            {
                for(int thumbOffset = 0; thumbOffset < this.getGolemPattern().getThumbLength(); thumbOffset++)
                {
                    BlockWorldState worldState = golemPattern.translateOffset(palmOffset, thumbOffset, 0);
                    world.notifyNeighborsRespectDebug(worldState.getPos(), Blocks.AIR, false);
                }
            }
        }
    }

    public boolean canDispenserPlace(World world, BlockPos pos)
    {
        return this.getGolemBasePattern().match(world, pos) != null;
    }

    protected BlockPattern getGolemBasePattern()
    {
        if(this.golemBasePattern == null)
        {
            this.golemBasePattern = FactoryBlockPattern.start().aisle("~ ~", "###", "~#~").where('#', BlockWorldState.hasState(BlockStateMatcher.forBlock(Blocks.GOLD_BLOCK))).where('~', BlockWorldState.hasState(BlockMaterialMatcher.forMaterial(Material.AIR))).build();
        }

        return this.golemBasePattern;
    }

    private BlockPattern getGolemPattern()
    {
        if(this.golemPattern == null)
        {
            this.golemPattern = FactoryBlockPattern.start().aisle("~^~", "###", "~#~").where('^', BlockWorldState.hasState((state -> state != null && state.getBlock() == this))).where('#', BlockWorldState.hasState(BlockStateMatcher.forBlock(Blocks.GOLD_BLOCK))).where('~', BlockWorldState.hasState(BlockMaterialMatcher.forMaterial(Material.AIR))).build();
        }

        return this.golemPattern;
    }
}
