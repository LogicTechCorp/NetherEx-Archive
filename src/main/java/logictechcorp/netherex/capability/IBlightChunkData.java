package logictechcorp.netherex.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Collection;

public interface IBlightChunkData extends INBTSerializable<NBTTagCompound>
{
    void tick(World world);

    void addChunk(ChunkPos chunkPos);

    void removeChunk(ChunkPos chunkPos);

    boolean hasChunk(ChunkPos chunkPos);

    CapabilityBlightChunkData.BlightChunk getChunk(ChunkPos chunkPos);

    Collection<CapabilityBlightChunkData.BlightChunk> getChunks();
}
