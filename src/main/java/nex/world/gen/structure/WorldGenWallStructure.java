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

@SuppressWarnings("ConstantConditions")
public class WorldGenWallStructure extends WorldGenerator
{
    private final List<WeightedUtil.NamedItem> variants = Lists.newArrayList();
    private final ResourceLocation[] spawnerMobs;
    private final ResourceLocation[] lootTables;

    public WorldGenWallStructure(String biomeName, String structureName, String[] variantsIn, String[] spawnerMobsIn, ResourceLocation[] lootTablesIn)
    {
        if(!variantsIn[0].equals(""))
        {
            for(int i = 0; i < variantsIn.length; i++)
            {
                variants.add(new WeightedUtil.NamedItem(structureName + "_" + (biomeName.equals("") ? "" : biomeName + "_") + variantsIn[i], i + 1));
            }
        }
        else
        {
            variants.add(new WeightedUtil.NamedItem(structureName + (biomeName.equals("") ? "" : "_" + biomeName), 1));
        }

        spawnerMobs = new ResourceLocation[spawnerMobsIn.length];

        for(int i = 0; i < spawnerMobsIn.length; i++)
        {
            spawnerMobs[i] = new ResourceLocation(spawnerMobsIn[i]);
        }

        lootTables = new ResourceLocation[lootTablesIn.length];

        for(int i = 0; i < lootTablesIn.length; i++)
        {
            lootTables[i] = lootTablesIn[i];
        }
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

        PlacementSettings placementSettings = new PlacementSettings().setMirror(mirror).setRotation(rotation).setReplacedBlock(Blocks.AIR).setRandom(rand);
        BlockPos structureSize = Template.transformedBlockPos(placementSettings, template.getSize());
        BlockPos newPos = new BlockPos(pos.getX() - structureSize.getX() / 2, 96, pos.getZ() - structureSize.getZ() / 2);
        BlockPos spawnPos = WorldGenUtil.getSuitableWallPos(world, newPos, structureSize, 0.8F);

        if(spawnPos != BlockPos.ORIGIN)
        {
            WorldGenUtil.generateStructure(world, spawnPos, rand, template, placementSettings, lootTables, spawnerMobs);
            return true;
        }
        return false;
    }
}
