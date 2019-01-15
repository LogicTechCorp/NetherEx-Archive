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

package logictechcorp.netherex.fixer;

import com.google.common.collect.ImmutableMap;
import logictechcorp.netherex.NetherEx;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.datafix.IFixableData;
import net.minecraft.world.chunk.NibbleArray;

import java.util.Map;

public class BlockFlatteningFixer implements IFixableData
{
    private static final Map<String, String> OLD_TO_NEW_ID_MAP = ImmutableMap.<String, String>builder()
            // Old named blocks
            .put("nex:block_netherrack.3", "netherex:gloomy_netherrack")
            .put("nex:block_netherrack.2", "netherex:lively_netherrack")
            .put("nex:block_netherrack.0", "netherex:fiery_netherrack")
            .put("nex:block_netherrack.1", "netherex:icy_netherrack")
            .put("nex:block_netherrack_path.0", "netherex:netherrack_path")
            .put("nex:block_netherrack_path.4", "netherex:gloomy_netherrack_path")
            .put("nex:block_netherrack_path.3", "netherex:lively_netherrack_path")
            .put("nex:block_netherrack_path.1", "netherex:fiery_netherrack_path")
            .put("nex:block_netherrack_path.2", "netherex:icy_netherrack_path")
            .put("nex:block_nether_brick.3", "netherex:gloomy_nether_brick")
            .put("nex:block_nether_brick.2", "netherex:lively_nether_brick")
            .put("nex:block_nether_brick.0", "netherex:fiery_nether_brick")
            .put("nex:block_nether_brick.1", "netherex:icy_nether_brick")
            .put("nex:block_basalt.0", "netherex:basalt")
            .put("nex:block_basalt.1", "netherex:smooth_basalt")
            .put("nex:block_basalt.2", "netherex:basalt_brick")
            .put("nex:block_basalt.3", "netherex:basalt_pillar")
            .put("nex:block_hyphae.0", "netherex:hyphae")
            .put("nex:block_sand_soul_tilled.0", "netherex:tilled_soul_sand")
            .put("nex:block_glass_soul.0", "netherex:soul_glass")
            .put("nex:block_glass_pane_soul.0", "netherex:soul_glass_pane")
            .put("nex:block_amethyst.0", "netherex:amethyst_block")
            .put("nex:block_rime.0", "netherex:rime_block")
            .put("nex:block_ice_frostburn.0", "netherex:frostburn_ice")
            .put("nex:block_bone_sliver.0", "netherex:bone_sliver")
            .put("nex:block_bone_chunk.0", "netherex:bone_chunk")
            .put("nex:block_iron_worn.0", "netherex:worn_iron")
            .put("nex:block_portal_nether.0", "netherex:nether_portal")
            .put("nex:tile_urn_sorrow.0", "netherex:urn_of_sorrow")
            .put("nex:ore_quartz.0", "netherex:quartz_ore")
            .put("nex:ore_quartz.1", "netherex:quartz_ore")
            .put("nex:ore_quartz.2", "netherex:quartz_ore")
            .put("nex:ore_quartz.3", "netherex:quartz_ore")
            .put("nex:ore_amethyst.0", "netherex:amethyst_ore")
            .put("nex:ore_rime.0", "netherex:rime_ore")
            .put("nex:plant_thornstalk.0", "netherex:thornstalk")
            .put("nex:plant_mushroom_elder.0", "netherex:brown_elder_mushroom")
            .put("nex:plant_mushroom_elder.1", "netherex:red_elder_mushroom")
            .put("nex:plant_mushroom_elder_cap.0", "netherex:brown_elder_mushroom_cap")
            .put("nex:plant_mushroom_elder_cap.1", "netherex:red_elder_mushroom_cap")
            .put("nex:plant_mushroom_elder_stem.0", "netherex:elder_mushroom_stem")
            .put("nex:plant_mushroom_enoki_cap.0", "netherex:enoki_mushroom_cap")
            .put("nex:plant_mushroom_enoki_stem.0", "netherex:enoki_mushroom_stem")
            .put("nex:fluid_ichor.0", "netherex:ichor")
            .put("nex:slab_vanilla.0", "netherex:red_nether_brick_slab")
            .put("nex:slab_vanilla.8", "netherex:red_nether_brick_slab")
            .put("nex:slab_brick_nether.3", "netherex:gloomy_nether_brick_slab")
            .put("nex:slab_brick_nether.2", "netherex:lively_nether_brick_slab")
            .put("nex:slab_brick_nether.0", "netherex:fiery_nether_brick_slab")
            .put("nex:slab_brick_nether.1", "netherex:icy_nether_brick_slab")
            .put("nex:slab_brick_nether.11", "netherex:gloomy_nether_brick_slab")
            .put("nex:slab_brick_nether.10", "netherex:lively_nether_brick_slab")
            .put("nex:slab_brick_nether.8", "netherex:fiery_nether_brick_slab")
            .put("nex:slab_brick_nether.9", "netherex:icy_nether_brick_slab")
            .put("nex:slab_basalt.0", "netherex:basalt_slab")
            .put("nex:slab_basalt.1", "netherex:smooth_basalt_slab")
            .put("nex:slab_basalt.2", "netherex:basalt_brick_slab")
            .put("nex:slab_basalt.3", "netherex:basalt_pillar_slab")
            .put("nex:slab_basalt.8", "netherex:basalt_slab")
            .put("nex:slab_basalt.9", "netherex:smooth_basalt_slab")
            .put("nex:slab_basalt.10", "netherex:basalt_brick_slab")
            .put("nex:slab_basalt.11", "netherex:basalt_pillar_slab")
            .put("nex:slab_vanilla_double.0", "netherex:red_nether_brick_slab")
            .put("nex:slab_brick_nether_double.3", "netherex:gloomy_nether_brick_slab")
            .put("nex:slab_brick_nether_double.2", "netherex:lively_nether_brick_slab")
            .put("nex:slab_brick_nether_double.0", "netherex:fiery_nether_brick_slab")
            .put("nex:slab_brick_nether_double.1", "netherex:icy_nether_brick_slab")
            .put("nex:slab_basalt_double.0", "netherex:basalt_slab")
            .put("nex:slab_basalt_double.1", "netherex:smooth_basalt_slab")
            .put("nex:slab_basalt_double.2", "netherex:basalt_brick_slab")
            .put("nex:slab_basalt_double.3", "netherex:basalt_pillar_slab")
            .put("nex:stairs_brick_nether_gloomy.0", "netherex:gloomy_nether_brick_stairs")
            .put("nex:stairs_brick_nether_lively.0", "netherex:lively_nether_brick_stairs")
            .put("nex:stairs_brick_nether_fiery.0", "netherex:fiery_nether_brick_stairs")
            .put("nex:stairs_brick_nether_icy.0", "netherex:icy_nether_brick_stairs")
            .put("nex:stairs_basalt_normal.0", "netherex:basalt_stairs")
            .put("nex:stairs_basalt_smooth.0", "netherex:smooth_basalt_stairs")
            .put("nex:stairs_basalt_brick.0", "netherex:basalt_brick_stairs")
            .put("nex:stairs_basalt_pillar.0", "netherex:basalt_pillar_stairs")
            .put("nex:wall_vanilla.0", "netherex:quartz_wall")
            .put("nex:wall_vanilla.1", "netherex:nether_brick_wall")
            .put("nex:wall_vanilla.2", "netherex:red_nether_brick_wall")
            .put("nex:wall_brick_nether.0", "netherex:gloomy_nether_brick_wall")
            .put("nex:wall_brick_nether.1", "netherex:lively_nether_brick_wall")
            .put("nex:wall_brick_nether.2", "netherex:fiery_nether_brick_wall")
            .put("nex:wall_brick_nether.3", "netherex:icy_nether_brick_wall")
            .put("nex:wall_basalt.0", "netherex:basalt_wall")
            .put("nex:wall_basalt.1", "netherex:smooth_basalt_wall")
            .put("nex:wall_basalt.2", "netherex:basalt_brick_wall")
            .put("nex:wall_basalt.3", "netherex:basalt_pillar_wall")
            .put("nex:fence_vanilla.0", "netherex:quartz_fence")
            .put("nex:fence_vanilla.1", "netherex:red_nether_brick_fence")
            .put("nex:fence_brick_nether.3", "netherex:gloomy_nether_brick_fence")
            .put("nex:fence_brick_nether.2", "netherex:lively_nether_brick_fence")
            .put("nex:fence_brick_nether.0", "netherex:fiery_nether_brick_fence")
            .put("nex:fence_brick_nether.1", "netherex:icy_nether_brick_fence")
            .put("nex:fence_basalt.0", "netherex:basalt_fence")
            .put("nex:fence_basalt.1", "netherex:smooth_basalt_fence")
            .put("nex:fence_basalt.2", "netherex:basalt_brick_fence")
            .put("nex:fence_basalt.3", "netherex:basalt_pillar_fence")
            .put("nex:fence_gate_quartz.0", "netherex:basalt_fence")
            .put("nex:fence_gate_brick_nether.0", "netherex:basalt_fence")
            .put("nex:fence_gate_brick_nether_red.0", "netherex:basalt_fence")
            .put("nex:fence_gate_basalt_normal.0", "netherex:basalt_fence")
            .put("nex:fence_gate_basalt_smooth.0", "netherex:basalt_fence_gate")
            .put("nex:fence_gate_basalt_brick.0", "netherex:basalt_fence")
            .put("nex:fence_gate_basalt_pillar.0", "netherex:basalt_fence")
            .put("nex:fence_gate_brick_nether_gloomy.0", "netherex:basalt_fence")
            .put("nex:fence_gate_brick_nether_lively.0", "netherex:basalt_fence")
            .put("nex:fence_gate_brick_nether_fiery.0", "netherex:basalt_fence")
            .put("nex:fence_gate_brick_nether_icy.0", "netherex:basalt_fence")

            // New named blocks
            .put("nex:netherrack.3", "netherex:gloomy_netherrack")
            .put("nex:netherrack.2", "netherex:lively_netherrack")
            .put("nex:netherrack.0", "netherex:fiery_netherrack")
            .put("nex:netherrack.1", "netherex:icy_netherrack")
            .put("nex:netherrack_path.0", "netherex:netherrack_path")
            .put("nex:netherrack_path.4", "netherex:gloomy_netherrack_path")
            .put("nex:netherrack_path.3", "netherex:lively_netherrack_path")
            .put("nex:netherrack_path.1", "netherex:fiery_netherrack_path")
            .put("nex:netherrack_path.2", "netherex:icy_netherrack_path")
            .put("nex:nether_brick.3", "netherex:gloomy_nether_brick")
            .put("nex:nether_brick.2", "netherex:lively_nether_brick")
            .put("nex:nether_brick.0", "netherex:fiery_nether_brick")
            .put("nex:nether_brick.1", "netherex:icy_nether_brick")
            .put("nex:basalt.0", "netherex:basalt")
            .put("nex:basalt.1", "netherex:smooth_basalt")
            .put("nex:basalt.2", "netherex:basalt_brick")
            .put("nex:basalt.3", "netherex:basalt_pillar")
            .put("nex:quartz_ore.0", "netherex:quartz_ore")
            .put("nex:quartz_ore.1", "netherex:quartz_ore")
            .put("nex:quartz_ore.2", "netherex:quartz_ore")
            .put("nex:quartz_ore.3", "netherex:quartz_ore")
            .put("nex:elder_mushroom.0", "netherex:brown_elder_mushroom")
            .put("nex:elder_mushroom.1", "netherex:red_elder_mushroom")
            .put("nex:elder_mushroom_cap.0", "netherex:brown_elder_mushroom_cap")
            .put("nex:elder_mushroom_cap.1", "netherex:red_elder_mushroom_cap")
            .put("nex:vanilla_slab.0", "netherex:red_nether_brick_slab")
            .put("nex:vanilla_slab.8", "netherex:red_nether_brick_slab")
            .put("nex:nether_brick_slab.3", "netherex:gloomy_nether_brick_slab")
            .put("nex:nether_brick_slab.2", "netherex:lively_nether_brick_slab")
            .put("nex:nether_brick_slab.0", "netherex:fiery_nether_brick_slab")
            .put("nex:nether_brick_slab.1", "netherex:icy_nether_brick_slab")
            .put("nex:nether_brick_slab.11", "netherex:gloomy_nether_brick_slab")
            .put("nex:nether_brick_slab.10", "netherex:lively_nether_brick_slab")
            .put("nex:nether_brick_slab.8", "netherex:fiery_nether_brick_slab")
            .put("nex:nether_brick_slab.9", "netherex:icy_nether_brick_slab")
            .put("nex:basalt_slab.0", "netherex:basalt_slab")
            .put("nex:basalt_slab.1", "netherex:smooth_basalt_slab")
            .put("nex:basalt_slab.2", "netherex:basalt_brick_slab")
            .put("nex:basalt_slab.3", "netherex:basalt_pillar_slab")
            .put("nex:basalt_slab.8", "netherex:basalt_slab")
            .put("nex:basalt_slab.9", "netherex:smooth_basalt_slab")
            .put("nex:basalt_slab.10", "netherex:basalt_brick_slab")
            .put("nex:basalt_slab.11", "netherex:basalt_pillar_slab")
            .put("nex:vanilla_slab_double.0", "netherex:red_nether_brick_slab")
            .put("nex:nether_brick_slab_double.3", "netherex:gloomy_nether_brick_slab")
            .put("nex:nether_brick_slab_double.2", "netherex:lively_nether_brick_slab")
            .put("nex:nether_brick_slab_double.0", "netherex:fiery_nether_brick_slab")
            .put("nex:nether_brick_slab_double.1", "netherex:icy_nether_brick_slab")
            .put("nex:basalt_slab_double.0", "netherex:basalt_slab")
            .put("nex:basalt_slab_double.1", "netherex:smooth_basalt_slab")
            .put("nex:basalt_slab_double.2", "netherex:basalt_brick_slab")
            .put("nex:basalt_slab_double.3", "netherex:basalt_pillar_slab")
            .put("nex:basalt_smooth_stairs.0", "netherex:smooth_basalt_stairs")
            .put("nex:vanilla_wall.0", "netherex:quartz_wall")
            .put("nex:vanilla_wall.1", "netherex:nether_brick_wall")
            .put("nex:vanilla_wall.2", "netherex:red_nether_brick_wall")
            .put("nex:nether_brick_wall.3", "netherex:gloomy_nether_brick_wall")
            .put("nex:nether_brick_wall.2", "netherex:lively_nether_brick_wall")
            .put("nex:nether_brick_wall.0", "netherex:fiery_nether_brick_wall")
            .put("nex:nether_brick_wall.1", "netherex:icy_nether_brick_wall")
            .put("nex:basalt_wall.0", "netherex:basalt_wall")
            .put("nex:basalt_wall.1", "netherex:smooth_basalt_wall")
            .put("nex:basalt_wall.2", "netherex:basalt_brick_wall")
            .put("nex:basalt_wall.3", "netherex:basalt_pillar_wall")
            .put("nex:vanilla_fence.0", "netherex:quartz_fence")
            .put("nex:vanilla_fence.1", "netherex:red_nether_brick_fence")
            .put("nex:nether_brick_fence.3", "netherex:gloomy_nether_brick_fence")
            .put("nex:nether_brick_fence.2", "netherex:lively_nether_brick_fence")
            .put("nex:nether_brick_fence.0", "netherex:fiery_nether_brick_fence")
            .put("nex:nether_brick_fence.1", "netherex:icy_nether_brick_fence")
            .put("nex:basalt_fence.0", "netherex:basalt_fence")
            .put("nex:basalt_fence.1", "netherex:smooth_basalt_fence")
            .put("nex:basalt_fence.2", "netherex:basalt_brick_fence")
            .put("nex:basalt_fence.3", "netherex:basalt_pillar_fence")
            .put("nex:basalt_smooth_fence_gate.0", "netherex:smooth_basalt_fence_gate")

            // New mod id blocks
            .put("netherex:netherrack.3", "netherex:gloomy_netherrack")
            .put("netherex:netherrack.2", "netherex:lively_netherrack")
            .put("netherex:netherrack.0", "netherex:fiery_netherrack")
            .put("netherex:netherrack.1", "netherex:icy_netherrack")
            .put("netherex:netherrack_path.0", "netherex:netherrack_path")
            .put("netherex:netherrack_path.4", "netherex:gloomy_netherrack_path")
            .put("netherex:netherrack_path.3", "netherex:lively_netherrack_path")
            .put("netherex:netherrack_path.1", "netherex:fiery_netherrack_path")
            .put("netherex:netherrack_path.2", "netherex:icy_netherrack_path")
            .put("netherex:nether_brick.3", "netherex:gloomy_nether_brick")
            .put("netherex:nether_brick.2", "netherex:lively_nether_brick")
            .put("netherex:nether_brick.0", "netherex:fiery_nether_brick")
            .put("netherex:nether_brick.1", "netherex:icy_nether_brick")
            .put("netherex:basalt.0", "netherex:basalt")
            .put("netherex:basalt.1", "netherex:smooth_basalt")
            .put("netherex:basalt.2", "netherex:basalt_brick")
            .put("netherex:basalt.3", "netherex:basalt_pillar")
            .put("netherex:quartz_ore.0", "netherex:quartz_ore")
            .put("netherex:quartz_ore.1", "netherex:quartz_ore")
            .put("netherex:quartz_ore.2", "netherex:quartz_ore")
            .put("netherex:quartz_ore.3", "netherex:quartz_ore")
            .put("netherex:elder_mushroom.0", "netherex:brown_elder_mushroom")
            .put("netherex:elder_mushroom.1", "netherex:red_elder_mushroom")
            .put("netherex:elder_mushroom_cap.0", "netherex:brown_elder_mushroom_cap")
            .put("netherex:elder_mushroom_cap.1", "netherex:red_elder_mushroom_cap")
            .put("netherex:vanilla_slab.0", "netherex:red_nether_brick_slab")
            .put("netherex:vanilla_slab.8", "netherex:red_nether_brick_slab")
            .put("netherex:nether_brick_slab.3", "netherex:gloomy_nether_brick_slab")
            .put("netherex:nether_brick_slab.2", "netherex:lively_nether_brick_slab")
            .put("netherex:nether_brick_slab.0", "netherex:fiery_nether_brick_slab")
            .put("netherex:nether_brick_slab.1", "netherex:icy_nether_brick_slab")
            .put("netherex:nether_brick_slab.11", "netherex:gloomy_nether_brick_slab")
            .put("netherex:nether_brick_slab.10", "netherex:lively_nether_brick_slab")
            .put("netherex:nether_brick_slab.8", "netherex:fiery_nether_brick_slab")
            .put("netherex:nether_brick_slab.9", "netherex:icy_nether_brick_slab")
            .put("netherex:basalt_slab.0", "netherex:basalt_slab")
            .put("netherex:basalt_slab.1", "netherex:smooth_basalt_slab")
            .put("netherex:basalt_slab.2", "netherex:basalt_brick_slab")
            .put("netherex:basalt_slab.3", "netherex:basalt_pillar_slab")
            .put("netherex:basalt_slab.8", "netherex:basalt_slab")
            .put("netherex:basalt_slab.9", "netherex:smooth_basalt_slab")
            .put("netherex:basalt_slab.10", "netherex:basalt_brick_slab")
            .put("netherex:basalt_slab.11", "netherex:basalt_pillar_slab")
            .put("netherex:vanilla_slab_double.0", "netherex:red_nether_brick_slab")
            .put("netherex:nether_brick_slab_double.3", "netherex:gloomy_nether_brick_slab")
            .put("netherex:nether_brick_slab_double.2", "netherex:lively_nether_brick_slab")
            .put("netherex:nether_brick_slab_double.0", "netherex:fiery_nether_brick_slab")
            .put("netherex:nether_brick_slab_double.1", "netherex:icy_nether_brick_slab")
            .put("netherex:basalt_slab_double.0", "netherex:basalt_slab")
            .put("netherex:basalt_slab_double.1", "netherex:smooth_basalt_slab")
            .put("netherex:basalt_slab_double.2", "netherex:basalt_brick_slab")
            .put("netherex:basalt_slab_double.3", "netherex:basalt_pillar_slab")
            .put("netherex:vanilla_wall.0", "netherex:quartz_wall")
            .put("netherex:vanilla_wall.1", "netherex:nether_brick_wall")
            .put("netherex:vanilla_wall.2", "netherex:red_nether_brick_wall")
            .put("netherex:nether_brick_wall.3", "netherex:gloomy_nether_brick_wall")
            .put("netherex:nether_brick_wall.2", "netherex:lively_nether_brick_wall")
            .put("netherex:nether_brick_wall.0", "netherex:fiery_nether_brick_wall")
            .put("netherex:nether_brick_wall.1", "netherex:icy_nether_brick_wall")
            .put("netherex:basalt_wall.0", "netherex:basalt_wall")
            .put("netherex:basalt_wall.1", "netherex:smooth_basalt_wall")
            .put("netherex:basalt_wall.2", "netherex:basalt_brick_wall")
            .put("netherex:basalt_wall.3", "netherex:basalt_pillar_wall")
            .put("netherex:vanilla_fence.0", "netherex:quartz_fence")
            .put("netherex:vanilla_fence.1", "netherex:red_nether_brick_fence")
            .put("netherex:nether_brick_fence.3", "netherex:gloomy_nether_brick_fence")
            .put("netherex:nether_brick_fence.2", "netherex:lively_nether_brick_fence")
            .put("netherex:nether_brick_fence.0", "netherex:fiery_nether_brick_fence")
            .put("netherex:nether_brick_fence.1", "netherex:icy_nether_brick_fence")
            .put("netherex:basalt_fence.0", "netherex:basalt_fence")
            .put("netherex:basalt_fence.1", "netherex:smooth_basalt_fence")
            .put("netherex:basalt_fence.2", "netherex:basalt_brick_fence")
            .put("netherex:basalt_fence.3", "netherex:basalt_pillar_fence")
            .build();

    @Override
    public int getFixVersion()
    {
        return 209;
    }

    @Override
    public NBTTagCompound fixTagCompound(NBTTagCompound compound)
    {
        try
        {
            NBTTagCompound level = compound.getCompoundTag("Level");
            NBTTagList sections = level.getTagList("Sections", 10);

            for(int sectionIndex = 0; sectionIndex < sections.tagCount(); sectionIndex++)
            {
                NBTTagCompound section = sections.getCompoundTagAt(sectionIndex);
                byte[] blockIds = section.getByteArray("Blocks");
                NibbleArray data = new NibbleArray(section.getByteArray("Data"));
                NibbleArray blockIdExtension = section.hasKey("Add", 7) ? new NibbleArray(section.getByteArray("Add")) : null;

                for(int i = 0; i < blockIds.length; i++)
                {
                    int extension = blockIdExtension == null ? 0 : blockIdExtension.getFromIndex(i);
                    int oldBlockId = extension << 8 | (blockIds[i] & 255);
                    int oldMeta = data.getFromIndex(i);
                    String oldRegistryName = Block.getBlockById(oldBlockId).getRegistryName().toString();
                    String newRegistryName = OLD_TO_NEW_ID_MAP.get(oldRegistryName + "." + oldMeta);

                    if(newRegistryName != null)
                    {
                        Block newBlock = Block.getBlockFromName(newRegistryName);

                        if(newBlock != null)
                        {
                            int newBlockId = Block.getIdFromBlock(newBlock);
                            blockIds[i] = (byte) (newBlockId & 0xFF);

                            if(blockIdExtension != null)
                            {
                                blockIdExtension.setIndex(i, (newBlockId >> 8) & 0xF);
                            }

                            data.setIndex(i, 0);

                            NetherEx.LOGGER.info("Data fixed the block {}, with a meta of {}. It is now {}.", oldRegistryName, oldMeta, newBlock.getRegistryName().toString());
                        }
                    }
                }
            }
        }
        catch(Exception e)
        {
            NetherEx.LOGGER.warn("Unable to data fix blocks, level format may be missing tags.");
        }

        return compound;
    }
}
