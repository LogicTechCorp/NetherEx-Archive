/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
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

package nex.village;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class VillageFenceGateInfo
{
    private final BlockPos fenceGateBlockPos;
    private final BlockPos insideBlock;
    private final EnumFacing insideDirection;
    private int lastActivityTimestamp;
    private boolean isDetachedFromVillageFlag;
    private int fenceGateOpeningRestrictionCounter;

    public VillageFenceGateInfo(BlockPos pos, int deltaX, int deltaZ, int timestamp)
    {
        this(pos, getFaceDirection(deltaX, deltaZ), timestamp);
    }

    private static EnumFacing getFaceDirection(int deltaX, int deltaZ)
    {
        return deltaX < 0 ? EnumFacing.WEST : (deltaX > 0 ? EnumFacing.EAST : (deltaZ < 0 ? EnumFacing.NORTH : EnumFacing.SOUTH));
    }

    public VillageFenceGateInfo(BlockPos pos, EnumFacing facing, int timestamp)
    {
        fenceGateBlockPos = pos;
        insideDirection = facing;
        insideBlock = pos.offset(facing, 3);
        lastActivityTimestamp = timestamp;
    }

    public int getDistanceSquared(int x, int y, int z)
    {
        return (int) fenceGateBlockPos.distanceSq((double) x, (double) y, (double) z);
    }

    public int getDistanceToFenceGateBlockSq(BlockPos pos)
    {
        return (int) pos.distanceSq(getFenceGateBlockPos());
    }

    public int getDistanceToInsideBlockSq(BlockPos pos)
    {
        return (int) insideBlock.distanceSq(pos);
    }

    public boolean isInsideSide(BlockPos pos)
    {
        int i = pos.getX() - fenceGateBlockPos.getX();
        int j = pos.getZ() - fenceGateBlockPos.getY();
        return i * insideDirection.getFrontOffsetX() + j * insideDirection.getFrontOffsetZ() >= 0;
    }

    public void resetFenceGateOpeningRestrictionCounter()
    {
        fenceGateOpeningRestrictionCounter = 0;
    }

    public void incrementFenceGateOpeningRestrictionCounter()
    {
        fenceGateOpeningRestrictionCounter++;
    }

    public int getFenceGateOpeningRestrictionCounter()
    {
        return fenceGateOpeningRestrictionCounter;
    }

    public BlockPos getFenceGateBlockPos()
    {
        return fenceGateBlockPos;
    }

    public BlockPos getInsideBlockPos()
    {
        return insideBlock;
    }

    public int getInsideOffsetX()
    {
        return insideDirection.getFrontOffsetX() * 2;
    }

    public int getInsideOffsetZ()
    {
        return insideDirection.getFrontOffsetZ() * 2;
    }

    public int getLastActivityTimestamp()
    {
        return lastActivityTimestamp;
    }

    public void setLastActivityTimestamp(int timestamp)
    {
        lastActivityTimestamp = timestamp;
    }

    public boolean getIsDetachedFromVillageFlag()
    {
        return isDetachedFromVillageFlag;
    }

    public void setIsDetachedFromVillageFlag(boolean detached)
    {
        isDetachedFromVillageFlag = detached;
    }

    public EnumFacing getInsideDirection()
    {
        return insideDirection;
    }
}
