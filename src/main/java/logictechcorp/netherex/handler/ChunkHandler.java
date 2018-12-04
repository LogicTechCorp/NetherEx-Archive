package logictechcorp.netherex.handler;

import logictechcorp.libraryex.event.world.ChunkGenerateEvent;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.capability.CapabilityBlightChunkData;
import logictechcorp.netherex.capability.IBlightChunkData;
import logictechcorp.netherex.init.NetherExBiomes;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
public class ChunkHandler
{
    @SubscribeEvent
    public static void onChunkGenerate(ChunkGenerateEvent event)
    {
        World world = event.getWorld();
        Chunk chunk = event.getChunk();
        int chunkX = chunk.x;
        int chunkZ = chunk.z;

        if(!world.isRemote)
        {
            if(world.provider.getDimension() == DimensionType.NETHER.getId())
            {
                boolean collapsed = false;

                for(byte biomeId : chunk.getBiomeArray())
                {
                    Biome biome = Biome.getBiomeForId(biomeId);

                    if(!collapsed && biome == NetherExBiomes.REGROWTHS_COLLAPSE)
                    {
                        collapsed = true;
                    }
                }

                World overworld = DimensionManager.getWorld(DimensionType.OVERWORLD.getId());

                if(overworld != null && collapsed)
                {
                    IBlightChunkData data = overworld.getCapability(CapabilityBlightChunkData.INSTANCE, null);

                    if(data != null)
                    {
                        data.addChunk(new ChunkPos((chunkX * 8), (chunkZ * 8)));
                    }
                }
            }
        }
    }
}
