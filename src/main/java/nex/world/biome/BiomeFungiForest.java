package nex.world.biome;

import net.minecraft.world.biome.Biome;
import nex.NetherEx;
import nex.Settings;
import nex.api.block.NEXBlocks;
import nex.entity.neutral.EntityMogus;

public class BiomeFungiForest extends NEXBiome
{
    public BiomeFungiForest()
    {
        super(new BiomeProperties("Fungi Forest"));

        topBlock = NEXBlocks.mycelium.getDefaultState();
        fillerBlock = NEXBlocks.netherrack.getDefaultState();

        spawnableMonsterList.add(new SpawnListEntry(EntityMogus.class, 100, 8, 8));

        register(Settings.fungiForestBiomeId, NetherEx.MOD_ID + ":fungiForest");
    }

    @Override
    public Biome getBiome()
    {
        return this;
    }
}
