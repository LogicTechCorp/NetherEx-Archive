package nex.world.biome;

import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import nex.NetherEx;
import nex.Settings;
import nex.api.block.NEXBlocks;
import nex.entity.hostile.EntityWight;

public class BiomeFreezingBlizzard extends NEXBiome
{
    public BiomeFreezingBlizzard()
    {
        super(new BiomeProperties("Freezing Blizzard"));

        topBlock = Blocks.PACKED_ICE.getDefaultState();
        fillerBlock = NEXBlocks.netherrack.getStateFromMeta(2);

        spawnableMonsterList.add(new SpawnListEntry(EntityWight.class, 100, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityGhast.class, 20, 4, 4));

        register(Settings.freezingBlizzardBiomeId, NetherEx.MOD_ID + ":freezingBlizzard");
    }

    @Override
    public Biome getBiome()
    {
        return this;
    }
}
