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

package logictechcorp.netherex.entity.ai;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

public class EntityAIUseFenceGate extends EntityAIFenceGateInteract
{
    private boolean closeAfterUse;
    private int closeCounter;

    public EntityAIUseFenceGate(EntityLiving entity, boolean closeAfterUse)
    {
        super(entity);
        this.entity = entity;
        this.closeAfterUse = closeAfterUse;
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return this.closeAfterUse && this.closeCounter > 0 && super.shouldContinueExecuting();
    }

    @Override
    public void startExecuting()
    {
        this.closeCounter = 20;
        this.useFenceGate(true);
    }

    @Override
    public void resetTask()
    {
        if(this.closeAfterUse)
        {
            this.useFenceGate(false);
        }
    }

    @Override
    public void updateTask()
    {
        this.closeCounter--;
        super.updateTask();
    }

    private void useFenceGate(boolean open)
    {
        World world = this.entity.getEntityWorld();
        IBlockState state = world.getBlockState(this.fenceGatePos);

        if(state.getBlock() instanceof BlockFenceGate)
        {
            world.setBlockState(this.fenceGatePos, state.withProperty(BlockFenceGate.OPEN, open), 10);
            world.markBlockRangeForRenderUpdate(this.fenceGatePos, this.fenceGatePos);
            world.playEvent(null, open ? 1008 : 1014, this.fenceGatePos, 0);
        }
    }
}
