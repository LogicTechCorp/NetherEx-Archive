package nex.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nex.ModContent;
import nex.api.NetherExAPI;
import nex.block.BlockModNetherrack;
import nex.block.BlockModOre;
import nex.item.ItemBlockMeta;
import nex.world.biome.BiomeGelid;
import nex.world.biome.BiomeSoulSandDesert;

public class ModRegistry
{
    public static final ModRegistry INSTANCE = new ModRegistry();

    private ModRegistry() {}

    public void initBlocksAndItems()
    {
        ModContent.Blocks.netherrack = registerBlock(new BlockModNetherrack(), BlockModNetherrack.EnumType.getNames());
        ModContent.Blocks.ore = registerBlock(new BlockModOre(), BlockModOre.EnumType.getNames());
    }

    public void initBiomes()
    {
        ModContent.Biomes.gelid = registerBiome(48, "gelid", new BiomeGelid(new Biome.BiomeProperties("Gelid")), 10, BiomeDictionary.Type.NETHER, BiomeDictionary.Type.COLD);
        ModContent.Biomes.soul_sand_desert = registerBiome(49, "soul_sand_desert", new BiomeSoulSandDesert(new Biome.BiomeProperties("Soul Sand Desert")), 10, BiomeDictionary.Type.NETHER, BiomeDictionary.Type.DRY);

        for(BiomeManager.BiomeEntry entry : ModContent.biomes)
        {
            NetherExAPI.addBiome(entry.biome, entry.itemWeight);
        }
    }

    private Block registerBlock(Block block)
    {
        GameRegistry.register(block);
        GameRegistry.register(new ItemBlock(block), block.getRegistryName());
        return block;
    }

    private Block registerBlock(Block block, String[] names)
    {
        GameRegistry.register(block);
        GameRegistry.register(new ItemBlockMeta(block, names, true), block.getRegistryName());
        return block;
    }

    private void registerTileEntity(Class<? extends TileEntity> tile, String name)
    {
        GameRegistry.registerTileEntity(tile, name);
    }

    private Fluid registerFluid(Fluid fluid)
    {
        FluidRegistry.registerFluid(fluid);
        return fluid;
    }

    private Item registerItem(Item item)
    {
        GameRegistry.register(item);
        return item;
    }

    private Biome registerBiome(int id, String name, Biome biome, int weight, BiomeDictionary.Type... types)
    {
        Biome.registerBiome(id, name, biome);
        BiomeDictionary.registerBiomeType(biome, types);
        ModContent.biomes.add(new BiomeManager.BiomeEntry(biome, weight));
        return biome;
    }
}
