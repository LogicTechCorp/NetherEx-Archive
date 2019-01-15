/*
 * NetherEx
 * Copyright (c) 2016-2019 by LogicTechCorp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
