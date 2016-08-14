package nex.world.biome;

import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.world.biome.Biome;
import nex.NetherEx;
import nex.Settings;
import nex.api.block.NEXBlocks;
import nex.entity.passive.EntityTortoise;

public class BiomeBlazingInferno extends NEXBiome
{
    public BiomeBlazingInferno()
    {
        super(new BiomeProperties("Blazing Inferno"));

        topBlock = NEXBlocks.netherrack.getStateFromMeta(1);
        fillerBlock = NEXBlocks.netherrack.getStateFromMeta(1);

        spawnableMonsterList.add(new SpawnListEntry(EntityTortoise.class, 100, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityBlaze.class, 20, 4, 4));

        register(Settings.blazingInfernoBiomeId, NetherEx.MOD_ID + ":blazingInferno");
    }

    @Override
    public Biome getBiome()
    {
        return this;
    }
}
