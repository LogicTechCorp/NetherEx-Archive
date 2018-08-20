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

package nex.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import nex.entity.boss.EntityGhastQueen;
import nex.entity.projectile.EntityGhastQueenFireball;
import nex.init.NetherExSoundEvents;

public class EntityAIGhastQueenFireballAttack extends EntityAIBase
{
    private final EntityGhast ghast;
    public int attackCounter;

    public EntityAIGhastQueenFireballAttack(EntityGhastQueen ghast)
    {
        this.ghast = ghast;
    }

    @Override
    public boolean shouldExecute()
    {
        return ghast.getAttackTarget() != null;
    }

    @Override
    public void startExecuting()
    {
        attackCounter = 0;
    }

    @Override
    public void resetTask()
    {
        ghast.setAttacking(false);
    }

    @Override
    public void updateTask()
    {
        EntityLivingBase target = ghast.getAttackTarget();

        if(target.getDistanceSq(ghast) < 4096.0D && ghast.canEntityBeSeen(target))
        {
            World world = ghast.world;
            attackCounter++;

            if(attackCounter == 10)
            {
                world.playEvent(null, 1015, new BlockPos(ghast), 0);
            }

            if(attackCounter == 20)
            {
                Vec3d look = ghast.getLook(1.0F);
                double distanceX = target.posX - (ghast.posX + look.x * 4.0D);
                double distanceY = target.getEntityBoundingBox().minY + (double) (target.height / 2.0F) - (0.5D + ghast.posY + (double) (ghast.height / 2.0F));
                double distanceZ = target.posZ - (ghast.posZ + look.z * 4.0D);
                ghast.playSound(NetherExSoundEvents.GHAST_QUEEN_SHOOT, 10.0F, (ghast.getRNG().nextFloat() - ghast.getRNG().nextFloat()) * 0.2F + 1.0F);
                EntityGhastQueenFireball fireball = new EntityGhastQueenFireball(world, ghast, distanceX, distanceY, distanceZ);
                fireball.explosionPower = ghast.getFireballStrength();
                fireball.posX = ghast.posX + look.x * 4.0D;
                fireball.posY = ghast.posY + (double) (ghast.height / 2.0F) + 0.5D;
                fireball.posZ = ghast.posZ + look.z * 4.0D;
                world.spawnEntity(fireball);
                attackCounter = -40;
            }
        }
        else if(attackCounter > 0)
        {
            attackCounter--;
        }

        ghast.setAttacking(attackCounter > 10);
    }
}
