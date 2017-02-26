/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
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

package nex.world.gen.structure;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import nex.block.BlockUrnOfSorrow;
import nex.tileentity.TileEntityUrnOfSorrow;
import nex.util.WeightedUtil;
import nex.util.WorldGenUtil;

import java.util.Random;

public class WorldGenGhastCastle extends WorldGenAirStructure
{
    public WorldGenGhastCastle(String biomeName, String structureName, String[] variantsIn, String[] spawnerMobsIn, boolean hasChestIn, ResourceLocation lootTableIn)
    {
        super(biomeName, structureName, variantsIn, spawnerMobsIn, hasChestIn, lootTableIn);
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        rand = world.getChunkFromBlockCoords(pos).getRandomWithSeed(world.getSeed());

        Mirror[] mirrors = Mirror.values();
        Rotation[] rotations = Rotation.values();
        Mirror mirror = mirrors[rand.nextInt(mirrors.length)];
        Rotation rotation = rotations[rand.nextInt(rotations.length)];
        MinecraftServer server = world.getMinecraftServer();
        TemplateManager manager = world.getSaveHandler().getStructureTemplateManager();
        Template template = manager.getTemplate(server, WeightedUtil.getRandomStructure(rand, variants));

        PlacementSettings settings = new PlacementSettings().setMirror(mirror).setRotation(rotation).setReplacedBlock(Blocks.STRUCTURE_VOID).setRandom(rand);
        BlockPos structureSize = Template.transformedBlockPos(settings.copy(), template.getSize());
        BlockPos newPos = new BlockPos(pos.getX() - structureSize.getX() / 2, 96, pos.getZ() - structureSize.getZ() / 2);
        BlockPos spawnPos = WorldGenUtil.getSuitableAirPos(world, newPos, structureSize);

        if(spawnPos != BlockPos.ORIGIN)
        {
            template.addBlocksToWorld(world, spawnPos, settings.copy(), 3);
            setUrnImmovable(world, spawnPos, structureSize);

            if(spawnerMobs.length > 0)
            {
                WorldGenUtil.setSpawnerMob(world, spawnPos, structureSize, spawnerMobs[rand.nextInt(spawnerMobs.length)]);
            }
            if(hasChest)
            {
                WorldGenUtil.setChestContents(world, rand, spawnPos, structureSize, lootTable);
            }
            return true;
        }
        return false;
    }

    private static void setUrnImmovable(World world, BlockPos pos, BlockPos structureSize)
    {
        for(int x = 0; x <= MathHelper.abs(structureSize.getX()) - 2; x++)
        {
            for(int z = 0; z <= MathHelper.abs(structureSize.getZ()) - 2; z++)
            {
                for(int y = 0; y <= structureSize.getY() - 11; y++)
                {
                    int posX = structureSize.getX() > 0 ? structureSize.getX() - x : structureSize.getX() + x;
                    int posZ = structureSize.getZ() > 0 ? structureSize.getZ() - z : structureSize.getZ() + z;

                    BlockPos newPos = pos.add(posX, y, posZ);
                    Block block = world.getBlockState(newPos).getBlock();

                    if(block instanceof BlockUrnOfSorrow)
                    {
                        TileEntityUrnOfSorrow urn = (TileEntityUrnOfSorrow) world.getTileEntity(newPos);
                        urn.setCanBreak(false);
                    }
                }
            }
        }
    }
}
