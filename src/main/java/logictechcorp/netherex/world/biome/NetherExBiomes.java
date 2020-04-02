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

import logictechcorp.libraryex.resource.BuiltinDataPack;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.NetherExConfig;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class NetherExBiomes
{
    public static final DeferredRegister<Biome> BIOMES = new DeferredRegister<>(ForgeRegistries.BIOMES, NetherEx.MOD_ID);

    // @formatter:off
    public static final RegistryObject<Biome> RUTHLESS_SANDS   = registerBiome("ruthless_sands", BasicNetherBiome::new);
    public static final RegistryObject<Biome> FUNGI_FOREST     = registerBiome("fungi_forest", BasicNetherBiome::new);
    public static final RegistryObject<Biome> TORRID_WASTELAND = registerBiome("torrid_wasteland", BasicNetherBiome::new);
    public static final RegistryObject<Biome> ARCTIC_ABYSS     = registerBiome("arctic_abyss", BasicNetherBiome::new);
    // @formatter:on

    public static void registerBiomePacks(MinecraftServer server)
    {
        ResourcePackList<ResourcePackInfo> resourcePacks = server.getResourcePacks();
        ModFile modFile = ModList.get().getModFileById(NetherEx.MOD_ID).getFile();

        if(NetherExConfig.NETHER.biomePackUseDefaultBiomePack.get())
        {
            resourcePacks.addPackFinder(new BuiltinDataPack(modFile, "default_nether_biome_pack"));
        }

        if(NetherExConfig.NETHER.biomePackUseNetherExBiomePack.get())
        {
            resourcePacks.addPackFinder(new BuiltinDataPack(modFile, "netherex_nether_biome_pack"));
        }

        if(ModList.get().isLoaded("biomesoplenty") && NetherExConfig.NETHER.biomePackUseBOPBiomePack.get())
        {
            resourcePacks.addPackFinder(new BuiltinDataPack(modFile, "bop_nether_biome_pack"));
        }
    }

    private static <B extends Biome> RegistryObject<B> registerBiome(String name, Supplier<B> supplier)
    {
        return BIOMES.register(name, supplier);
    }
}
