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

import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import nex.util.WeightedUtil;
import nex.util.WorldGenUtil;

import java.util.List;
import java.util.Random;
import java.util.Set;

@SuppressWarnings("ConstantConditions")
public class WorldGenGroundStructure extends WorldGenerator
{
    private final Set<IBlockState> allowedBlocks;
    private final List<WeightedUtil.NamedItem> variants = Lists.newArrayList();
    private final ResourceLocation[] spawnerMobs;
    private final boolean hasChest;
    private final ResourceLocation lootTable;

    public WorldGenGroundStructure(String biomeName, String structureName, String[] variantsIn, Set<IBlockState> allowedBlocksIn, String[] spawnerMobsIn, boolean hasChestIn, ResourceLocation lootTableIn)
    {
        allowedBlocks = allowedBlocksIn;

        if(!variantsIn[0].equals(""))
        {
            for(int i = 0; i < variantsIn.length; i++)
            {
                variants.add(new WeightedUtil.NamedItem(structureName + "_" + biomeName + "_" + variantsIn[i], i + 1));
            }
        }
        else
        {
            variants.add(new WeightedUtil.NamedItem(structureName + "_" + biomeName, 1));
        }

        if(!spawnerMobsIn[0].equals(""))
        {
            spawnerMobs = new ResourceLocation[spawnerMobsIn.length];

            for(int i = 0; i < spawnerMobsIn.length; i++)
            {
                spawnerMobs[i] = new ResourceLocation(spawnerMobsIn[i]);
            }
        }
        else
        {
            spawnerMobs = new ResourceLocation[0];
        }

        hasChest = hasChestIn;
        lootTable = lootTableIn;
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
        BlockPos spawnPos = WorldGenUtil.getSuitableGroundPos(world, newPos, allowedBlocks, structureSize, 0.8F);

        if(spawnPos != BlockPos.ORIGIN)
        {
            template.addBlocksToWorld(world, spawnPos, settings.copy(), 3);

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
}
