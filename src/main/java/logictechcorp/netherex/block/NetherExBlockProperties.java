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

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.DyeColor;
import net.minecraftforge.common.ToolType;

public class NetherExBlockProperties
{
    // @formatter:off
    public static final Block.Properties GLOOMY_NETHERRACK              = createProperties(Material.ROCK, DyeColor.BROWN).harvestTool(ToolType.PICKAXE).hardnessAndResistance(0.4F);
    public static final Block.Properties GLOOMY_NETHERRACK_PATH         = createProperties(Material.ROCK, DyeColor.BROWN).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(0.5F, 2.0F);
    public static final Block.Properties GLOOMY_NETHER_BRICKS           = createProperties(Material.ROCK, DyeColor.BROWN).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties GLOOMY_NETHER_BRICK_SLAB       = createProperties(Material.ROCK, DyeColor.BROWN).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties GLOOMY_NETHER_BRICK_STAIRS     = createProperties(Material.ROCK, DyeColor.BROWN).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties GLOOMY_NETHER_BRICK_WALL       = createProperties(Material.ROCK, DyeColor.BROWN).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties GLOOMY_NETHER_BRICK_FENCE      = createProperties(Material.ROCK, DyeColor.BROWN).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties GLOOMY_NETHER_BRICK_FENCE_GATE = createProperties(Material.ROCK, DyeColor.BROWN).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties LIVELY_NETHERRACK              = createProperties(Material.ROCK, DyeColor.MAGENTA).harvestTool(ToolType.PICKAXE).hardnessAndResistance(0.4F);
    public static final Block.Properties LIVELY_NETHERRACK_PATH         = createProperties(Material.ROCK, DyeColor.MAGENTA).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(0.5F, 2.0F);
    public static final Block.Properties LIVELY_NETHER_BRICKS           = createProperties(Material.ROCK, DyeColor.MAGENTA).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties LIVELY_NETHER_BRICK_SLAB       = createProperties(Material.ROCK, DyeColor.MAGENTA).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties LIVELY_NETHER_BRICK_STAIRS     = createProperties(Material.ROCK, DyeColor.MAGENTA).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties LIVELY_NETHER_BRICK_WALL       = createProperties(Material.ROCK, DyeColor.MAGENTA).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties LIVELY_NETHER_BRICK_FENCE      = createProperties(Material.ROCK, DyeColor.MAGENTA).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties LIVELY_NETHER_BRICK_FENCE_GATE = createProperties(Material.ROCK, DyeColor.MAGENTA).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties FIERY_NETHERRACK               = createProperties(Material.ROCK, DyeColor.ORANGE).harvestTool(ToolType.PICKAXE).hardnessAndResistance(0.4F);
    public static final Block.Properties FIERY_NETHERRACK_PATH          = createProperties(Material.ROCK, DyeColor.ORANGE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(0.5F, 2.0F);
    public static final Block.Properties FIERY_NETHER_BRICKS            = createProperties(Material.ROCK, DyeColor.ORANGE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties FIERY_NETHER_BRICK_SLAB        = createProperties(Material.ROCK, DyeColor.ORANGE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties FIERY_NETHER_BRICK_STAIRS      = createProperties(Material.ROCK, DyeColor.ORANGE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties FIERY_NETHER_BRICK_WALL        = createProperties(Material.ROCK, DyeColor.ORANGE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties FIERY_NETHER_BRICK_FENCE       = createProperties(Material.ROCK, DyeColor.ORANGE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties FIERY_NETHER_BRICK_FENCE_GATE  = createProperties(Material.ROCK, DyeColor.ORANGE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties ICY_NETHERRACK                 = createProperties(Material.ROCK, DyeColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).hardnessAndResistance(0.4F);
    public static final Block.Properties ICY_NETHERRACK_PATH            = createProperties(Material.ROCK, DyeColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(0.5F, 2.0F);
    public static final Block.Properties ICY_NETHER_BRICKS              = createProperties(Material.ROCK, DyeColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties ICY_NETHER_BRICK_SLAB          = createProperties(Material.ROCK, DyeColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties ICY_NETHER_BRICK_STAIRS        = createProperties(Material.ROCK, DyeColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties ICY_NETHER_BRICK_WALL          = createProperties(Material.ROCK, DyeColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties ICY_NETHER_BRICK_FENCE         = createProperties(Material.ROCK, DyeColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties ICY_NETHER_BRICK_FENCE_GATE    = createProperties(Material.ROCK, DyeColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties NETHERRACK_PATH                = createProperties(Material.ROCK, DyeColor.RED).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(0.5F, 2.0F);
    public static final Block.Properties BASALT                         = createProperties(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties BASALT_SLAB                    = createProperties(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties BASALT_STAIRS                  = createProperties(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties BASALT_WALL                    = createProperties(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties BASALT_FENCE                   = createProperties(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties BASALT_FENCE_GATE              = createProperties(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties SMOOTH_BASALT                  = createProperties(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties SMOOTH_BASALT_SLAB             = createProperties(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties SMOOTH_BASALT_STAIRS           = createProperties(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties SMOOTH_BASALT_WALL             = createProperties(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties SMOOTH_BASALT_FENCE            = createProperties(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties SMOOTH_BASALT_FENCE_GATE       = createProperties(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties BASALT_BRICK                   = createProperties(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties BASALT_BRICK_SLAB              = createProperties(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties BASALT_BRICK_STAIRS            = createProperties(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties BASALT_BRICK_WALL              = createProperties(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties BASALT_BRICK_FENCE             = createProperties(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties BASALT_BRICK_FENCE_GATE        = createProperties(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties BASALT_PILLAR                  = createProperties(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties BASALT_PILLAR_SLAB             = createProperties(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties BASALT_PILLAR_STAIRS           = createProperties(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties BASALT_PILLAR_WALL             = createProperties(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties BASALT_PILLAR_FENCE            = createProperties(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties BASALT_PILLAR_FENCE_GATE       = createProperties(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F);
    public static final Block.Properties FROSTBURN_ICE                  = createProperties(Material.ICE, DyeColor.LIGHT_BLUE).sound(SoundType.GLASS).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(0.5F, 1.0F).notSolid();
    public static final Block.Properties RIME_ORE                       = createProperties(Material.ROCK, DyeColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).hardnessAndResistance(3.0F);
    public static final Block.Properties RIME_BLOCK                     = createProperties(Material.ROCK, DyeColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(5.0F, 10.0F).lightValue(13);
    public static final Block.Properties BROWN_ELDER_MUSHROOM           = createProperties(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.PLANT).lightValue(1);
    public static final Block.Properties RED_ELDER_MUSHROOM             = createProperties(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.PLANT);
    public static final Block.Properties BROWN_ELDER_MUSHROOM_CAP       = createProperties(Material.WOOD, MaterialColor.DIRT).hardnessAndResistance(0.2F).sound(SoundType.WOOD);
    public static final Block.Properties RED_ELDER_MUSHROOM_CAP         = createProperties(Material.WOOD, MaterialColor.RED).hardnessAndResistance(0.2F).sound(SoundType.WOOD);
    public static final Block.Properties ELDER_MUSHROOM_STEM            = createProperties(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(0.2F).sound(SoundType.WOOD);
    public static final Block.Properties ENOKI_MUSHROOM_CAP             = createProperties(Material.PLANTS, MaterialColor.PURPLE).tickRandomly().hardnessAndResistance(0.4F).sound(SoundType.WOOD);
    public static final Block.Properties ENOKI_MUSHROOM_STEM            = createProperties(Material.PLANTS, MaterialColor.PURPLE).hardnessAndResistance(0.4F).sound(SoundType.WOOD);
    public static final Block.Properties BLUE_FIRE                      = createProperties(Material.FIRE, MaterialColor.LIGHT_BLUE).tickRandomly().doesNotBlockMovement().hardnessAndResistance(0.0F).lightValue(15).sound(SoundType.CLOTH).noDrops();
    public static final Block.Properties SOUL_GLASS                     = createProperties(Material.GLASS, DyeColor.BROWN).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid();
    public static final Block.Properties SOUL_GLASS_PANE                = createProperties(Material.GLASS).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid();
    // @formatter:on

    private static Block.Properties createProperties(Material material)
    {
        return createProperties(material, material.getColor());
    }

    private static Block.Properties createProperties(Material material, MaterialColor color)
    {
        return Block.Properties.create(material, color);
    }

    private static Block.Properties createProperties(Material material, DyeColor color)
    {
        return Block.Properties.create(material, color);
    }
}
