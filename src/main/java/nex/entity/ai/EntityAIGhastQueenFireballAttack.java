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
    public int attackTimer;

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
        attackTimer = 0;
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
            attackTimer++;

            if(attackTimer == 10)
            {
                world.playEvent(null, 1015, new BlockPos(ghast), 0);
            }

            if(attackTimer == 20)
            {
                Vec3d vec3d = ghast.getLook(1.0F);
                double d2 = target.posX - (ghast.posX + vec3d.x * 4.0D);
                double d3 = target.getEntityBoundingBox().minY + (double) (target.height / 2.0F) - (0.5D + ghast.posY + (double) (ghast.height / 2.0F));
                double d4 = target.posZ - (ghast.posZ + vec3d.z * 4.0D);
                ghast.playSound(NetherExSoundEvents.GHAST_QUEEN_SHOOT, 10.0F, (ghast.getRNG().nextFloat() - ghast.getRNG().nextFloat()) * 0.2F + 1.0F);
                EntityGhastQueenFireball fireball = new EntityGhastQueenFireball(world, ghast, d2, d3, d4);
                fireball.explosionPower = ghast.getFireballStrength();
                fireball.posX = ghast.posX + vec3d.x * 4.0D;
                fireball.posY = ghast.posY + (double) (ghast.height / 2.0F) + 0.5D;
                fireball.posZ = ghast.posZ + vec3d.z * 4.0D;
                world.spawnEntity(fireball);
                attackTimer = -40;
            }
        }
        else if(attackTimer > 0)
        {
            --attackTimer;
        }

        ghast.setAttacking(attackTimer > 10);
    }
}
