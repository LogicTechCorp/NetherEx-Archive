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

package nex.world.biome;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nex.NetherEx;
import nex.Settings;
import nex.init.ModBiomes;

public abstract class BiomeNetherEx extends Biome
{
    public IBlockState lavaSpringTargetBlock = Blocks.NETHERRACK.getDefaultState();
    public IBlockState smallBrownMushroomTargetBlock = Blocks.NETHERRACK.getDefaultState();
    public IBlockState smallRedMushroomTargetBlock = Blocks.NETHERRACK.getDefaultState();
    public IBlockState quartzOreBlock = Blocks.QUARTZ_ORE.getDefaultState();
    public IBlockState quartzTargetBlock = Blocks.NETHERRACK.getDefaultState();
    public IBlockState magmaTargetBlock = Blocks.NETHERRACK.getDefaultState();
    public IBlockState lavaTrapTargetBlock = Blocks.NETHERRACK.getDefaultState();

    public String settingCategory = Settings.CATEGORY_BIOME;

    public BiomeNetherEx(BiomeProperties properties)
    {
        super(properties);

        topBlock = Blocks.NETHERRACK.getDefaultState();
        fillerBlock = Blocks.NETHERRACK.getDefaultState();

        spawnableMonsterList.clear();
        spawnableCreatureList.clear();
        spawnableWaterCreatureList.clear();
        spawnableCaveCreatureList.clear();
    }

    @Override
    public BiomeDecorator createBiomeDecorator()
    {
        return new BiomeDecoratorNether();
    }

    public void register(String name, int weight)
    {
        BiomeDictionary.registerBiomeType(this, BiomeDictionary.Type.NETHER);
        GameRegistry.register(setRegistryName(new ResourceLocation(NetherEx.MOD_ID, name)));
        ModBiomes.addBiome(new BiomeManager.BiomeEntry(this, weight));
    }
}