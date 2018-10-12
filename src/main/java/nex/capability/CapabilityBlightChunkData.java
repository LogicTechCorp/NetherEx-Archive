package nex.capability;

import com.google.common.collect.Lists;
import lex.capability.CapabilitySerializable;
import lex.util.WorldHelper;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nex.NetherEx;

import java.util.*;
import java.util.concurrent.Callable;

public class CapabilityBlightChunkData implements IBlightChunkData
{
    @CapabilityInject(IBlightChunkData.class)
    public static final Capability<IBlightChunkData> INSTANCE = null;
    private final Map<Long, BlightChunk> blightChunks = new HashMap<>();

    public CapabilityBlightChunkData()
    {
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound compound = new NBTTagCompound();
        NBTTagList blightChunkData = new NBTTagList();

        for(Map.Entry<Long, BlightChunk> entry : this.blightChunks.entrySet())
        {
            BlightChunk chunk = entry.getValue();
            NBTTagCompound blightChunkCompound = new NBTTagCompound();
            blightChunkCompound.setInteger("ChunkX", chunk.getChunkX());
            blightChunkCompound.setInteger("ChunkZ", chunk.getChunkZ());
            blightChunkCompound.setInteger("CleansedTick", chunk.getCleansedTick());
            blightChunkCompound.setBoolean("Ascended", chunk.isAscended());
            blightChunkCompound.setBoolean("Cleansed", chunk.isCleansed());
            blightChunkData.appendTag(blightChunkCompound);
        }

        compound.setTag("BlightChunkData", blightChunkData);
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound compound)
    {
        for(NBTBase base : compound.getTagList("BlightChunkData", Constants.NBT.TAG_COMPOUND))
        {
            NBTTagCompound blightChunkCompound = (NBTTagCompound) base;
            BlightChunk chunk = new BlightChunk(blightChunkCompound.getInteger("ChunkX"), blightChunkCompound.getInteger("ChunkZ"));
            chunk.setCleansedTick(blightChunkCompound.getInteger("CleansedTick"));
            chunk.setAscended(blightChunkCompound.getBoolean("Ascended"));
            chunk.setCleansed(blightChunkCompound.getBoolean("Cleansed"));
            this.blightChunks.put(ChunkPos.asLong(chunk.getChunkX(), chunk.getChunkZ()), chunk);
        }
    }

    @Override
    public void tick(World world)
    {
        for(BlightChunk chunk : this.blightChunks.values())
        {
            if(WorldHelper.isChunkLoaded(world, chunk.getPos()))
            {
                chunk.tick(world);

                if(chunk.isCleansed() && chunk.getCleansedTick() >= 72000)
                {
                    this.blightChunks.remove(ChunkPos.asLong(chunk.getChunkX(), chunk.getChunkZ()));
                }
            }
        }
    }

    @Override
    public void addChunk(ChunkPos chunkPos)
    {
        this.blightChunks.put(ChunkPos.asLong(chunkPos.x, chunkPos.z), new BlightChunk(chunkPos.x, chunkPos.z));
    }

    @Override
    public BlightChunk getBlightChunk(ChunkPos chunkPos)
    {
        return this.blightChunks.get(ChunkPos.asLong(chunkPos.x, chunkPos.z));
    }

    @Override
    public Collection<BlightChunk> getBlightChunks()
    {
        return this.blightChunks.values();
    }

    public static class Factory implements Callable<IBlightChunkData>
    {
        @Override
        public IBlightChunkData call()
        {
            return new CapabilityBlightChunkData();
        }
    }

    public static class Storage implements Capability.IStorage<IBlightChunkData>
    {
        @Override
        public NBTBase writeNBT(Capability<IBlightChunkData> capability, IBlightChunkData chunk, EnumFacing side)
        {
            return chunk.serializeNBT();
        }

        @Override
        public void readNBT(Capability<IBlightChunkData> capability, IBlightChunkData chunk, EnumFacing side, NBTBase base)
        {
            if(base instanceof NBTTagCompound)
            {
                chunk.deserializeNBT((NBTTagCompound) base);
            }
        }
    }

    public class BlightChunk
    {
        private int chunkX;
        private int chunkZ;
        private int cleansedTick;
        private boolean ascended;
        private boolean cleansed;

        BlightChunk(int chunkX, int chunkZ)
        {
            this.chunkX = chunkX;
            this.chunkZ = chunkZ;
        }

        void tick(World world)
        {
            Random rand = world.rand;

            if(!this.ascended && rand.nextInt(8) == 7)
            {
                BlockPos chunkStartPos = new BlockPos((this.chunkX << 4), 0, (this.chunkZ << 4));
                BlockPos chunkEndPos = new BlockPos((this.chunkX << 4) + 15, 0, (this.chunkZ << 4) + 15);
                List<BlockPos> chunkPositions = Lists.newArrayList(BlockPos.getAllInBox(chunkStartPos, chunkEndPos));
                Collections.shuffle(chunkPositions, rand);

                for(int i = 0; i < rand.nextInt(8) + 1; i++)
                {
                    world.setBlockState(world.getTopSolidOrLiquidBlock(chunkPositions.get(i)).down(), Blocks.NETHERRACK.getDefaultState());
                }

                this.ascended = true;
            }

            if(this.cleansed)
            {
                this.cleansedTick++;
            }
        }

        public int getCleansedTick()
        {
            return this.cleansedTick;
        }

        public boolean isAscended()
        {
            return this.ascended;
        }

        public boolean isCleansed()
        {
            return this.cleansed;
        }

        public int getChunkX()
        {
            return this.chunkX;
        }

        public int getChunkZ()
        {
            return this.chunkZ;
        }

        public ChunkPos getPos()
        {
            return new ChunkPos(this.chunkX, this.chunkZ);
        }

        public void setCleansedTick(int cleansedTick)
        {
            this.cleansedTick = cleansedTick;
        }

        public void setAscended(boolean ascended)
        {
            this.ascended = ascended;
        }

        public void setCleansed(boolean cleansed)
        {
            this.cleansed = cleansed;
        }
    }

    @Mod.EventBusSubscriber
    public static class Handler
    {
        @SubscribeEvent
        public static void onAttachCapabilities(AttachCapabilitiesEvent<World> event)
        {
            if(event.getObject().provider.getDimension() == DimensionType.OVERWORLD.getId())
            {
                event.addCapability(new ResourceLocation(NetherEx.MOD_ID + ":blight_chunk_data"), new CapabilitySerializable(INSTANCE));
            }
        }
    }
}
