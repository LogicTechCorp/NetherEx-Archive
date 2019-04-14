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

package logictechcorp.netherex.world.biome;

import logictechcorp.libraryex.world.biome.BiomeDataConfigurable;
import logictechcorp.netherex.NetherEx;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldType;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class NetherBiomeDataBOP extends BiomeDataConfigurable
{
    public NetherBiomeDataBOP(ResourceLocation biomeRegistryName, int weight, boolean enabled, boolean genDefaultFeatures)
    {
        super(biomeRegistryName, weight, enabled, genDefaultFeatures);
    }

    @Override
    public boolean isEnabled()
    {
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

        if(server != null && NetherEx.BIOMES_O_PLENTY_LOADED)
        {
            WorldType worldType = server.getEntityWorld().getWorldType();
            return worldType.getName().equalsIgnoreCase(NetherEx.MOD_ID);
        }

        return false;
    }
}
