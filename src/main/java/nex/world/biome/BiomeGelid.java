package nex.world.biome;

import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import nex.ModContent;
import nex.handler.ConfigurationHandler;
import nex.world.gen.feature.WorldGenMinableMeta;
import nex.world.gen.feature.WorldGenNetherIceSpike;

import java.util.Random;

public class BiomeGelid extends Biome
{
    private WorldGenerator rimeGen = new WorldGenMinableMeta(ModContent.Blocks.ore.getStateFromMeta(0), ConfigurationHandler.Settings.rimeOreRarity, ModContent.Blocks.netherrack, 0);
    private WorldGenNetherIceSpike iceSpike = new WorldGenNetherIceSpike();

    public BiomeGelid(BiomeProperties properties)
    {
        super(properties);
        this.topBlock = Blocks.PACKED_ICE.getDefaultState();
        this.fillerBlock = ModContent.Blocks.netherrack.getStateFromMeta(0);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
    }

    @Override
    public void decorate(World worldIn, Random rand, BlockPos pos)
    {
        for(int i = 0; i < ConfigurationHandler.Settings.rimeOreRarity; i++)
        {
            int j = rand.nextInt(16) + 8;
            int k = rand.nextInt(108) + 10;
            int l = rand.nextInt(16) + 8;
            this.rimeGen.generate(worldIn, rand, pos.add(j, k, l));
        }

        for(int i = 0; i < ConfigurationHandler.Settings.gelidIceSpikeRarity; i++)
        {
            int j = rand.nextInt(16) + 8;
            int k = rand.nextInt(128);
            int l = rand.nextInt(16) + 8;
            this.iceSpike.generate(worldIn, rand, pos.add(j, k, l));
        }
    }
}
