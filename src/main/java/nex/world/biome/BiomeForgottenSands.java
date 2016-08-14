package nex.world.biome;

import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import nex.NetherEx;
import nex.Settings;
import nex.entity.hostile.EntityPordenfer;

public class BiomeForgottenSands extends NEXBiome
{
    public BiomeForgottenSands()
    {
        super(new BiomeProperties("Forgotten Sands"));

        topBlock = Blocks.SOUL_SAND.getDefaultState();

        spawnableMonsterList.add(new SpawnListEntry(EntityPordenfer.class, 100, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 20, 4, 4));

        register(Settings.forgottenSandsBiomeId, NetherEx.MOD_ID + ":forgottenSands");
    }

    @Override
    public Biome getBiome()
    {
        return this;
    }
}
