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
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import nex.NetherEx;
import nex.block.BlockMushroom;
import nex.init.NetherExBlocks;

import java.util.List;
import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class WorldGenElderMushroom extends WorldGenerator
{
    private List<MushroomSize> mushroomSizes = Lists.newArrayList(
            new MushroomSize("brown_tiny", 6),
            new MushroomSize("brown_tiny_variant", 6),
            new MushroomSize("brown_small", 5),
            new MushroomSize("brown_small_variant", 5),
            new MushroomSize("brown_medium", 4),
            new MushroomSize("brown_medium_variant", 4),
            new MushroomSize("brown_large", 3),
            new MushroomSize("brown_huge", 2),
            new MushroomSize("brown_gigantic", 1),
            new MushroomSize("red_tiny", 6),
            new MushroomSize("red_tiny_variant", 6),
            new MushroomSize("red_small", 5),
            new MushroomSize("red_small_variant", 5),
            new MushroomSize("red_small_variant_2", 5),
            new MushroomSize("red_medium", 4),
            new MushroomSize("red_large", 3),
            new MushroomSize("red_huge", 2),
            new MushroomSize("red_gigantic", 1)
    );

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        while(world.isAirBlock(pos) && pos.getY() > 32)
        {
            pos = pos.down();
        }

        for(int posX = -1; posX < 2; posX++)
        {
            for(int posZ = -1; posZ < 2; posZ++)
            {
                if(world.getBlockState(pos.add(posX, 0, posZ)).getBlock() != NetherExBlocks.BLOCK_HYPHAE)
                {
                    return false;
                }
            }
        }

        Mirror[] mirrors = Mirror.values();
        Mirror mirror = mirrors[rand.nextInt(mirrors.length)];
        Rotation[] rotations = Rotation.values();
        Rotation rotation = rotations[rand.nextInt(rotations.length)];
        MinecraftServer minecraftServer = world.getMinecraftServer();
        TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();
        Template template = templateManager.getTemplate(minecraftServer, new ResourceLocation(NetherEx.MOD_ID + ":plant_mushroom_" + getRandomMushroom(rand)));
        PlacementSettings placementSettings = new PlacementSettings().setMirror(mirror).setRotation(rotation).setReplacedBlock(Blocks.AIR);
        BlockPos structureSize = Template.transformedBlockPos(placementSettings.copy(), template.getSize());
        float airAmount = 0;
        float blockAmount = MathHelper.abs((structureSize.getX() + 2) * (structureSize.getY() + 1) * (structureSize.getZ() + 2));

        for(int posZ = -1; posZ < structureSize.getZ() + 1; posZ++)
        {
            for(int posX = -1; posX < structureSize.getX() + 1; posX++)
            {
                for(int posY = 0; posY < structureSize.getY() + 1; posY++)
                {
                    BlockPos newPos = pos.add(-(posX / 2), posY + 1, -(posZ / 2));
                    Block block = world.getBlockState(newPos).getBlock();

                    if(world.isAirBlock(newPos))
                    {
                        airAmount += 1.0F;
                    }
                    else if(block == NetherExBlocks.BLOCK_NETHERRACK || block == Blocks.GLOWSTONE || block instanceof BlockMushroom)
                    {
                        return false;
                    }
                }
            }
        }

        if(MathHelper.abs(airAmount) / MathHelper.abs(blockAmount) >= 0.75F)
        {
            template.addBlocksToWorld(world, pos.add(-(structureSize.getX() / 2), 1, -(structureSize.getZ() / 2)), placementSettings.copy());
        }

        return false;
    }

    private String getRandomMushroom(Random rand)
    {
        MushroomSize mushroom = WeightedRandom.getRandomItem(rand, mushroomSizes);
        return mushroom.name;
    }

    public class MushroomSize extends WeightedRandom.Item
    {
        private String name;

        public MushroomSize(String nameIn, int weight)
        {
            super(weight);
            name = nameIn;
        }
    }
}
