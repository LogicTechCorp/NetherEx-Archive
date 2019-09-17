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

package logictechcorp.netherex.block;

import logictechcorp.libraryex.block.EternalFireBlock;
import logictechcorp.libraryex.utility.RandomHelper;
import logictechcorp.netherex.potion.NetherExEffects;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlueFireBlock extends EternalFireBlock
{
    public BlueFireBlock(Properties builder)
    {
        super(builder);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity)
    {
        if(entity instanceof ItemEntity)
        {
            entity.remove();
        }
        else if(entity instanceof LivingEntity)
        {
            if(!entity.isImmuneToFire())
            {
                int ticks = RandomHelper.getNumberInRange(1, 70, world.rand);
                ((LivingEntity) entity).addPotionEffect(new EffectInstance(NetherExEffects.FIRE_BURNING, ticks));
            }
        }
    }
}
