/*
 * Copyright (C) 2016.  LogicTechCorp
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

package nex.world.gen.feature;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import nex.NetherEx;
import nex.init.NetherExBlocks;

import java.util.List;
import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class WorldGenElderMushroom extends WorldGenerator
{
    private MushroomSize tiny = new MushroomSize("tiny", 6);
    private MushroomSize small = new MushroomSize("small", 5);
    private MushroomSize medium = new MushroomSize("medium", 4);
    private MushroomSize large = new MushroomSize("large", 3);
    private MushroomSize huge = new MushroomSize("huge", 2);
    private MushroomSize gigantic = new MushroomSize("gigantic", 1);

    private List<MushroomSize> mushroomSizes = Lists.newArrayList(tiny, small, medium, large, huge, gigantic);

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        while(world.isAirBlock(pos) && pos.getY() > 32)
        {
            pos = pos.down();
        }

        for(int posZ = -1; posZ <= 1; posZ++)
        {
            for(int posX = -1; posX <= 1; posX++)
            {
                if(world.getBlockState(pos.add(posX, 0, posZ)).getBlock() != NetherExBlocks.BLOCK_HYPHAE)
                {
                    return false;
                }
            }
        }

        WorldServer worldServer = (WorldServer) world;
        MinecraftServer minecraftServer = world.getMinecraftServer();
        TemplateManager templateManager = worldServer.getStructureTemplateManager();
        Template template = templateManager.getTemplate(minecraftServer, new ResourceLocation(NetherEx.MOD_ID + ":plant_mushroom_" + (rand.nextBoolean() ? "brown_" : "red_") + getRandomMushroomSize(rand)));
        PlacementSettings placementSettings = new PlacementSettings().setRotation(Rotation.values()[rand.nextInt(Rotation.values().length)]).setReplacedBlock(Blocks.AIR);
        BlockPos structureSize = Template.transformedBlockPos(placementSettings, template.getSize());
        float airAmount = 0;
        float blockAmount = structureSize.getX() * structureSize.getY() * structureSize.getZ();

        for(int posZ = -1; posZ < structureSize.getZ() + 1; posZ++)
        {
            for(int posX = -1; posX < structureSize.getX() + 1; posX++)
            {
                for(int posY = 0; posY < structureSize.getY() + 1; posY++)
                {
                    BlockPos newPos = pos.add(-(posX / 2), posY, -(posZ / 2));
                    Block block = world.getBlockState(newPos).getBlock();

                    if(world.isAirBlock(newPos))
                    {
                        airAmount += 1.0F;
                    }
                    else if(block == NetherExBlocks.PLANT_MUSHROOM_BROWN || block == NetherExBlocks.PLANT_MUSHROOM_RED)
                    {
                        return false;
                    }
                }
            }
        }

        if(airAmount / blockAmount >= 0.75F)
        {
            template.addBlocksToWorld(world, pos.add(-(structureSize.getX() / 2), 1, -(structureSize.getZ() / 2)), placementSettings);
        }

        return false;
    }

    private String getRandomMushroomSize(Random rand)
    {
        MushroomSize mushroom = WeightedRandom.getRandomItem(rand, mushroomSizes);
        return mushroom.size;
    }

    public class MushroomSize extends WeightedRandom.Item
    {
        private String size;

        public MushroomSize(String sizeIn, int weight)
        {
            super(weight);
            size = sizeIn;
        }
    }
}
