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
    private final BlockPos pos;
    private final BlockPos insidePos;
    private final EnumFacing inside;
    private int lastActivityTime;
    private boolean isDetachedFromVillage;
    private int openRestrictionCounter;

    public PigtificateVillageFenceGateInfo(BlockPos pos, int deltaX, int deltaZ, int time)
    {
        this(pos, getFaceDirection(deltaX, deltaZ), time);
    }

    public PigtificateVillageFenceGateInfo(BlockPos pos, EnumFacing facing, int time)
    {
        this.pos = pos;
        this.inside = facing;
        this.insidePos = pos.offset(facing, 2);
        this.lastActivityTime = time;
    }

    private static EnumFacing getFaceDirection(int deltaX, int deltaZ)
    {
        return deltaX < 0 ? EnumFacing.WEST : (deltaX > 0 ? EnumFacing.EAST : (deltaZ < 0 ? EnumFacing.NORTH : EnumFacing.SOUTH));
    }

    public int getDistanceSquared(int x, int y, int z)
    {
        return (int) this.pos.distanceSq((double) x, (double) y, (double) z);
    }

    public int getDistanceToFenceGateSq(BlockPos pos)
    {
        return (int) pos.distanceSq(this.getPos());
    }

    public int getDistanceToInsideBlockSq(BlockPos pos)
    {
        return (int) this.insidePos.distanceSq(pos);
    }

    public boolean isInside(BlockPos pos)
    {
        int x = pos.getX() - this.pos.getX();
        int z = pos.getZ() - this.pos.getY();
        return x * this.inside.getXOffset() + z * this.inside.getZOffset() >= 0;
    }

    public void resetOpenRestrictionCounter()
    {
        this.openRestrictionCounter = 0;
    }

    public void incrementOpenRestrictionCounter()
    {
        this.openRestrictionCounter++;
    }

    public int getOpenRestrictionCounter()
    {
        return this.openRestrictionCounter;
    }

    public BlockPos getPos()
    {
        return this.pos;
    }

    public BlockPos getInsidePos()
    {
        return this.insidePos;
    }

    public int getInsideOffsetX()
    {
        return this.inside.getXOffset() * 2;
    }

    public int getInsideOffsetZ()
    {
        return this.inside.getZOffset() * 2;
    }

    public int getLastActivityTime()
    {
        return this.lastActivityTime;
    }

    public void setLastActivityTime(int time)
    {
        this.lastActivityTime = time;
    }

    public boolean isDetachedFromVillageFlag()
    {
        return this.isDetachedFromVillage;
    }

    public void setDetachedFromVillageFlag(boolean detached)
    {
        this.isDetachedFromVillage = detached;
    }

    public EnumFacing getInside()
    {
        return this.inside;
    }
}
