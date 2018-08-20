/*
 * NetherEx
 * Copyright (c) 2016-2018 by MineEx
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

public class PigtificateVillageFenceGateInfo
{
    private final BlockPos fenceGatePos;
    private final BlockPos insideBlockPos;
    private final EnumFacing insideDirection;
    private int lastActivityTimestamp;
    private boolean isDetachedFromVillage;
    private int fenceGateOpeningRestrictionCounter;

    public PigtificateVillageFenceGateInfo(BlockPos pos, int deltaX, int deltaZ, int timestamp)
    {
        this(pos, getFaceDirection(deltaX, deltaZ), timestamp);
    }

    private static EnumFacing getFaceDirection(int deltaX, int deltaZ)
    {
        return deltaX < 0 ? EnumFacing.WEST : (deltaX > 0 ? EnumFacing.EAST : (deltaZ < 0 ? EnumFacing.NORTH : EnumFacing.SOUTH));
    }

    public PigtificateVillageFenceGateInfo(BlockPos pos, EnumFacing facing, int timestamp)
    {
        fenceGatePos = pos;
        insideDirection = facing;
        insideBlockPos = pos.offset(facing, 2);
        lastActivityTimestamp = timestamp;
    }

    public int getDistanceSquared(int x, int y, int z)
    {
        return (int) fenceGatePos.distanceSq((double) x, (double) y, (double) z);
    }

    public int getDistanceToFenceGateSq(BlockPos pos)
    {
        return (int) pos.distanceSq(getFenceGatePos());
    }

    public int getDistanceToInsideBlockSq(BlockPos pos)
    {
        return (int) insideBlockPos.distanceSq(pos);
    }

    public boolean isInsideSide(BlockPos pos)
    {
        int i = pos.getX() - fenceGatePos.getX();
        int j = pos.getZ() - fenceGatePos.getY();
        return i * insideDirection.getXOffset() + j * insideDirection.getZOffset() >= 0;
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

    public BlockPos getFenceGatePos()
    {
        return fenceGatePos;
    }

    public BlockPos getInsideBlockPos()
    {
        return insideBlockPos;
    }

    public int getInsideOffsetX()
    {
        return insideDirection.getXOffset() * 2;
    }

    public int getInsideOffsetZ()
    {
        return insideDirection.getZOffset() * 2;
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
        return isDetachedFromVillage;
    }

    public void setIsDetachedFromVillageFlag(boolean detached)
    {
        isDetachedFromVillage = detached;
    }

    public EnumFacing getInsideDirection()
    {
        return insideDirection;
    }
}
