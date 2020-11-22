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

package logictechcorp.netherex.mobeffect;

import logictechcorp.libraryex.potion.MobEffectMod;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.NetherExConfig;
import logictechcorp.netherex.entity.monster.EntityGhastling;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

public class MobEffectCrying extends MobEffectMod
{
    public MobEffectCrying()
    {
        super(NetherEx.instance, "crying", true, 103, 62, 124);
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier)
    {
        World world = entity.getEntityWorld();

        if(this.canSpawnGhastling(entity) && world.rand.nextInt(NetherExConfig.mobEffect.lost.ghastlingSpawnRarity) == 0)
        {
            BlockPos newPos = entity.getPosition().add(0, 5, 0).offset(entity.getHorizontalFacing().getOpposite(), 5);

            if(!world.isRemote && world.isAirBlock(newPos))
            {
                EntityGhastling ghastling = new EntityGhastling(world);
                ghastling.setLocationAndAngles(newPos.getX(), newPos.getY(), newPos.getZ(), entity.rotationYaw, entity.rotationPitch);
                ghastling.setAttackTarget(entity);
                world.spawnEntity(ghastling);
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return true;
    }

    public boolean canSpawnGhastling(EntityLivingBase entity)
    {
        return entity instanceof EntityPlayer && entity.getEntityWorld().provider.getDimensionType() == DimensionType.NETHER;
    }
}
