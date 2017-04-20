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

package nex.entity.ai;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

public class EntityAIUseFenceGate extends EntityAIFenceGateInteract
{
    boolean closeFenceGate;
    int closeFenceGateTemporisation;

    public EntityAIUseFenceGate(EntityLiving entitylivingIn, boolean shouldClose)
    {
        super(entitylivingIn);
        theEntity = entitylivingIn;
        closeFenceGate = shouldClose;
    }

    @Override
    public boolean continueExecuting()
    {
        return closeFenceGate && closeFenceGateTemporisation > 0 && super.continueExecuting();
    }

    @Override
    public void startExecuting()
    {
        closeFenceGateTemporisation = 20;
        openFenceGate(true);
    }

    @Override
    public void resetTask()
    {
        if(closeFenceGate)
        {
            openFenceGate(false);
        }
    }

    @Override
    public void updateTask()
    {
        closeFenceGateTemporisation--;
        super.updateTask();
    }

    private void openFenceGate(boolean open)
    {
        World world = theEntity.getEntityWorld();
        IBlockState state = world.getBlockState(fenceGatePos);

        if(state.getBlock() instanceof BlockFenceGate)
        {
            world.setBlockState(fenceGatePos, state.withProperty(BlockFenceGate.OPEN, open), 10);
            world.markBlockRangeForRenderUpdate(fenceGatePos, fenceGatePos);
            world.playEvent(null, open ? 1008 : 1014, fenceGatePos, 0);
        }
    }
}
