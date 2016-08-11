package nex.world.biome;

import net.minecraft.world.biome.Biome;
import nex.NetherEx;
import nex.Settings;

public class BiomeHell extends NEXBiome
{
    public BiomeHell()
    {
        super(new BiomeProperties("Hell"));
    }

    @Override
    public int getId()
    {
        return Settings.hellBiomeId;
    }

    @Override
    public String getName()
    {
        return NetherEx.MOD_ID + ":hell";
    }

    @Override
    public Biome getBiome()
    {
        return this;
    }

    @Override
    public boolean generateFire()
    {
        return true;
    }

    @Override
    public boolean generateGlowStone()
    {
        return true;
    }

    @Override
    public boolean generateQuartz()
    {
        return true;
    }

    @Override
    public boolean generateMagma()
    {
        return true;
    }

    @Override
    public boolean generateLavaTrap()
    {
        return true;
    }

    @Override
    public boolean generateLavaSpring()
    {
        return true;
    }

    @Override
    public boolean generateMushrooms()
    {
        return true;
    }
}
