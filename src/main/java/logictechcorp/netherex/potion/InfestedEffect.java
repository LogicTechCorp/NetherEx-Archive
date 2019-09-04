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

package logictechcorp.netherex.potion;

import logictechcorp.netherex.NetherExConfig;
import logictechcorp.netherex.entity.NetherExEntities;
import logictechcorp.netherex.entity.hostile.SporeEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.awt.*;

public class InfestedEffect extends Effect
{
    public InfestedEffect()
    {
        super(EffectType.HARMFUL, new Color(142, 96, 40).getRGB());
    }

    @Override
    public void performEffect(LivingEntity entity, int amplifier)
    {
        World world = entity.getEntityWorld();
        BlockPos pos = entity.getPosition();

        if(this.canSpreadSpores(entity) && world.rand.nextInt(NetherExConfig.EFFECT.infestedEffectSporeSpawnChance.get()) == 0)
        {
            if(world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(pos).expand(1, 1, 1)).size() < 3)
            {
                BlockPos newPos = pos.offset(Direction.Plane.HORIZONTAL.random(world.rand));

                if(!world.isRemote && world.isAirBlock(newPos) && !world.isAirBlock(newPos.down()))
                {
                    SporeEntity spore = new SporeEntity(NetherExEntities.SPORE, world);
                    spore.setLocationAndAngles(newPos.getX(), newPos.getY(), newPos.getZ(), entity.rotationYaw, entity.rotationPitch);
                    world.addEntity(spore);
                }
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return true;
    }

    public boolean canSpreadSpores(LivingEntity entity)
    {
        if(entity instanceof PlayerEntity)
        {
            return true;
        }

        return NetherExConfig.EFFECT.infestedEffectEntityBlacklist.get().contains(entity.getType().getRegistryName().toString());
    }
}