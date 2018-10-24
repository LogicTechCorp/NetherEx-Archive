package nex.world.biome;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldType;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class BOPNetherBiomeWrapper extends NetherBiomeWrapper
{
    public BOPNetherBiomeWrapper(ResourceLocation biomeRegistryName, int weight, boolean enabled, boolean genDefaultFeatures)
    {
        super(biomeRegistryName, weight, enabled, genDefaultFeatures);
    }

    @Override
    public boolean isEnabled()
    {
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

        if(server != null)
        {
            WorldType worldType = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld().getWorldType();
            return worldType.getName().equalsIgnoreCase("BIOMESOP") || worldType.getName().equalsIgnoreCase("lostcities_bop");
        }

        return false;
    }
}
