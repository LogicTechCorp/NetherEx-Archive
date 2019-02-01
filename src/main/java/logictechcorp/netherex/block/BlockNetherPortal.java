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

import logictechcorp.libraryex.block.BlockModPortal;
import logictechcorp.libraryex.block.builder.BlockBuilder;
import logictechcorp.libraryex.utility.BlockHelper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.handler.ConfigHandler;
import logictechcorp.netherex.init.NetherExBlocks;
import logictechcorp.netherex.world.TeleporterNetherEx;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ITeleporter;

import java.util.Random;

public class BlockNetherPortal extends BlockModPortal
{
    public BlockNetherPortal()
    {
        super(NetherEx.getResource("nether_portal"), new BlockBuilder(Material.PORTAL, MapColor.PURPLE).sound(SoundType.GLASS).lightLevel(0.75F).hardness(-1.0F).tickRandomly());
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
    {
        super.updateTick(world, pos, state, random);

        if(world.provider.isSurfaceWorld() && world.getGameRules().getBoolean("doMobSpawning") && ConfigHandler.blockConfig.netherPortal.pigmanSpawnRarity > 0)
        {
            if(random.nextInt(ConfigHandler.blockConfig.netherPortal.pigmanSpawnRarity) < world.getDifficulty().getId())
            {
                int posY = pos.getY();
                BlockPos blockPos;

                for(blockPos = pos; !world.getBlockState(blockPos).isSideSolid(world, blockPos, EnumFacing.UP) && blockPos.getY() > 0; blockPos = blockPos.down())
                {

                }

                if(posY > 0 && !world.getBlockState(blockPos.up()).isNormalCube())
                {
                    Entity entity = ItemMonsterPlacer.spawnCreature(world, EntityList.getKey(EntityPigZombie.class), (double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 1.1D, (double) blockPos.getZ() + 0.5D);

                    if(entity != null)
                    {
                        entity.timeUntilPortal = entity.getPortalCooldown();
                    }
                }
            }
        }
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity)
    {
        if(!entity.isRiding() && !entity.isBeingRidden() && entity.isNonBoss())
        {
            if(entity.timeUntilPortal > 0)
            {
                entity.timeUntilPortal = entity.getPortalCooldown();
            }
            else
            {
                if(!world.isRemote)
                {
                    WorldServer worldServer = (WorldServer) world;
                    MinecraftServer minecraftServer = worldServer.getMinecraftServer();

                    if(minecraftServer != null)
                    {
                        PlayerList playerList = minecraftServer.getPlayerList();
                        int dimension = entity.dimension == DimensionType.NETHER.getId() ? DimensionType.OVERWORLD.getId() : DimensionType.NETHER.getId();
                        ITeleporter teleporter = TeleporterNetherEx.getTeleporterForWorld(minecraftServer, dimension);

                        entity.setPortal(pos);
                        entity.timeUntilPortal = entity.getPortalCooldown();

                        if(entity instanceof EntityPlayerMP)
                        {
                            playerList.transferPlayerToDimension((EntityPlayerMP) entity, dimension, teleporter);
                        }
                        else
                        {
                            playerList.transferEntityToWorld(entity, entity.dimension, worldServer, minecraftServer.getWorld(dimension), teleporter);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isPortalIgniter(World world, BlockPos pos)
    {
        return world.getBlockState(pos).getBlock() == NetherExBlocks.BLUE_FIRE;
    }

    @Override
    public boolean isPortalPart(World world, BlockPos pos)
    {
        Block block = world.getBlockState(pos).getBlock();
        return BlockHelper.isOreDict(block, "obsidian") || block == NetherExBlocks.BLUE_FIRE || block == this;
    }
}
