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

package logictechcorp.netherex;

import logictechcorp.libraryex.world.biome.BiomeDataManager;
import logictechcorp.netherex.proxy.ClientProxy;
import logictechcorp.netherex.proxy.CommonProxy;
import logictechcorp.netherex.world.biome.NetherBiomeDataManager;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(NetherEx.MOD_ID)
public class NetherEx
{
    public static final String MOD_ID = "netherex";
    public static final ItemGroup ITEM_GROUP = new ItemGroup(MOD_ID)
    {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon()
        {
            return new ItemStack(Blocks.NETHERRACK);
        }
    };
    public static final BiomeDataManager BIOME_DATA_MANAGER = new NetherBiomeDataManager();

    public static final Logger LOGGER = LogManager.getLogger("NetherEx");

    public NetherEx()
    {
        DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new).registerHandlers();
        NetherExConfig.registerConfigs();
    }
}
