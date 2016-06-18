package nex;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;

import java.util.List;

public class ModContent
{
    public static List<BiomeManager.BiomeEntry> biomes = Lists.newArrayList();

    public static class Biomes
    {
        public static Biome gelid;
        public static Biome soul_sand_desert;
    }

    public static class Blocks
    {
        public static Block netherrack;
        public static Block ore;
    }
}
