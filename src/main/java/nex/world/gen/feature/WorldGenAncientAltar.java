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

import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Random;

public class WorldGenAncientAltar extends WorldGenerator
{
    private String[] altars = new String[]{"intact", "ruined", "ruined", "destroyed", "destroyed", "destroyed"};
    private Rotation[] rotations = new Rotation[]{Rotation.NONE, Rotation.CLOCKWISE_90, Rotation.CLOCKWISE_180, Rotation.COUNTERCLOCKWISE_90};

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        WorldServer worldServer = (WorldServer) world;
        MinecraftServer minecraftServer = world.getMinecraftServer();
        TemplateManager templateManager = worldServer.getStructureTemplateManager();
        Template template = templateManager.get(minecraftServer, new ResourceLocation("nex", "altar/altar_ancient_" + altars[rand.nextInt(altars.length)]));

        if(template == null)
        {
            return false;
        }
        else if(isSoulSand(world, pos) && isSoulSand(world, pos.north()) && isSoulSand(world, pos.east()) && isSoulSand(world, pos.south()) && isSoulSand(world, pos.west()))
        {
            template.addBlocksToWorld(world, pos, new PlacementSettings().setRotation(rotations[rand.nextInt(rotations.length)]).setReplacedBlock(Blocks.AIR), 3);
            return true;
        }

        return false;
    }

    private boolean isSoulSand(World world, BlockPos pos)
    {
        return world.getBlockState(pos).getBlock() == Blocks.SOUL_SAND;
    }
}
