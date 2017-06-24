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

package nex.world.gen.feature;

import com.google.common.collect.Lists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import nex.util.WeightedUtil;

import java.util.List;
import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class WorldGenElderMushroom extends WorldGenerator
{
    private final List<WeightedUtil.NamedItem> variants;
    private final boolean isWorldGen;

    public static List<WeightedUtil.NamedItem> brownVariants = Lists.newArrayList(
            new WeightedUtil.NamedItem("plant_mushroom_elder_brown_tiny", 6),
            new WeightedUtil.NamedItem("plant_mushroom_elder_brown_tiny_variant", 6),
            new WeightedUtil.NamedItem("plant_mushroom_elder_brown_small", 5),
            new WeightedUtil.NamedItem("plant_mushroom_elder_brown_small_variant", 5),
            new WeightedUtil.NamedItem("plant_mushroom_elder_brown_medium", 4),
            new WeightedUtil.NamedItem("plant_mushroom_elder_brown_medium_variant", 4),
            new WeightedUtil.NamedItem("plant_mushroom_elder_brown_large", 3),
            new WeightedUtil.NamedItem("plant_mushroom_elder_brown_huge", 2),
            new WeightedUtil.NamedItem("plant_mushroom_elder_brown_gigantic", 1)
    );

    public static List<WeightedUtil.NamedItem> redVariants = Lists.newArrayList(
            new WeightedUtil.NamedItem("plant_mushroom_elder_red_tiny", 6),
            new WeightedUtil.NamedItem("plant_mushroom_elder_red_tiny_variant", 6),
            new WeightedUtil.NamedItem("plant_mushroom_elder_red_small", 5),
            new WeightedUtil.NamedItem("plant_mushroom_elder_red_small_variant", 5),
            new WeightedUtil.NamedItem("plant_mushroom_elder_red_small_variant_2", 5),
            new WeightedUtil.NamedItem("plant_mushroom_elder_red_medium", 4),
            new WeightedUtil.NamedItem("plant_mushroom_elder_red_large", 3),
            new WeightedUtil.NamedItem("plant_mushroom_elder_red_huge", 2),
            new WeightedUtil.NamedItem("plant_mushroom_elder_red_gigantic", 1)
    );

    public static List<WeightedUtil.NamedItem> allVariants = Lists.newArrayList(
            new WeightedUtil.NamedItem("plant_mushroom_elder_brown_tiny", 6),
            new WeightedUtil.NamedItem("plant_mushroom_elder_brown_tiny_variant", 6),
            new WeightedUtil.NamedItem("plant_mushroom_elder_brown_small", 5),
            new WeightedUtil.NamedItem("plant_mushroom_elder_brown_small_variant", 5),
            new WeightedUtil.NamedItem("plant_mushroom_elder_brown_medium", 4),
            new WeightedUtil.NamedItem("plant_mushroom_elder_brown_medium_variant", 4),
            new WeightedUtil.NamedItem("plant_mushroom_elder_brown_large", 3),
            new WeightedUtil.NamedItem("plant_mushroom_elder_brown_huge", 2),
            new WeightedUtil.NamedItem("plant_mushroom_elder_brown_gigantic", 1),
            new WeightedUtil.NamedItem("plant_mushroom_elder_red_tiny", 6),
            new WeightedUtil.NamedItem("plant_mushroom_elder_red_tiny_variant", 6),
            new WeightedUtil.NamedItem("plant_mushroom_elder_red_small", 5),
            new WeightedUtil.NamedItem("plant_mushroom_elder_red_small_variant", 5),
            new WeightedUtil.NamedItem("plant_mushroom_elder_red_small_variant_2", 5),
            new WeightedUtil.NamedItem("plant_mushroom_elder_red_medium", 4),
            new WeightedUtil.NamedItem("plant_mushroom_elder_red_large", 3),
            new WeightedUtil.NamedItem("plant_mushroom_elder_red_huge", 2),
            new WeightedUtil.NamedItem("plant_mushroom_elder_red_gigantic", 1)
    );

    public WorldGenElderMushroom(List<WeightedUtil.NamedItem> variantsIn, boolean isWorldGenIn)
    {
        variants = variantsIn;
        isWorldGen = isWorldGenIn;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        //while(isWorldGen && world.isAirBlock(pos) && pos.getY() > 32)
        //{
        //    pos = pos.down();
        //}
//
        //for(int posX = -1; posX < 2; posX++)
        //{
        //    for(int posZ = -1; posZ < 2; posZ++)
        //    {
        //        BlockPos newPos = pos.add(posX, 0, posZ);
        //        IBlockState state = world.getBlockState(newPos);
//
        //        if(!state.getBlock().canSustainPlant(state, world, newPos, EnumFacing.UP, NetherExBlocks.PLANT_MUSHROOM_ELDER) && state.getBlock() != Blocks.SOUL_SAND)
        //        {
        //            return false;
        //        }
        //    }
        //}
//
        //pos = pos.up();
//
        //Mirror[] mirrors = Mirror.values();
        //Mirror mirror = mirrors[rand.nextInt(mirrors.length)];
        //Rotation[] rotations = Rotation.values();
        //Rotation rotation = rotations[rand.nextInt(rotations.length)];
        //MinecraftServer minecraftServer = world.getMinecraftServer();
        //TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();
        //Template template = templateManager.getTemplate(minecraftServer, WeightedUtil.getRandomNamedItem(rand, variants));
        //PlacementSettings placementSettings = new PlacementSettings().setMirror(mirror).setRotation(rotation).setReplacedBlock(Blocks.AIR);
        //BlockPos structureSize = Template.transformedBlockPos(placementSettings.copy(), template.getSize());
        //float airAmount = 0;
        //float blockAmount = MathHelper.abs((structureSize.getX() + 2) * (structureSize.getY() + 1) * (structureSize.getZ() + 2));
//
        //for(int posX = -1; posX < structureSize.getX() + 1; posX++)
        //{
        //    for(int posZ = -1; posZ < structureSize.getZ() + 1; posZ++)
        //    {
        //        for(int posY = 0; posY < structureSize.getY() + 1; posY++)
        //        {
        //            BlockPos newPos = pos.add(-(posX / 2), posY, -(posZ / 2));
        //            Block block = world.getBlockState(newPos).getBlock();
//
        //            if(world.isAirBlock(newPos))
        //            {
        //                airAmount += 1.0F;
        //            }
        //            else if(block == Blocks.NETHERRACK || block == Blocks.GLOWSTONE || block == NetherExBlocks.BLOCK_NETHERRACK || block == NetherExBlocks.PLANT_MUSHROOM_ELDER_CAP || block == NetherExBlocks.PLANT_MUSHROOM_ELDER_STEM)
        //            {
        //                return false;
        //            }
        //        }
        //    }
        //}
//
        //if(MathHelper.abs(airAmount) / MathHelper.abs(blockAmount) >= 0.75F)
        //{
        //    if(!isWorldGen)
        //    {
        //        world.setBlockToAir(pos);
        //    }
//
        //    template.addBlocksToWorld(world, pos.add(-(structureSize.getX() / 2), 0, -(structureSize.getZ() / 2)), placementSettings.copy());
        //    return true;
        //}

        return false;
    }
}
