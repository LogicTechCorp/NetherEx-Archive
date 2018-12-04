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

package logictechcorp.netherex.entity.ai;

import logictechcorp.netherex.entity.monster.EntityGhastling;
import logictechcorp.netherex.entity.projectile.EntityGhastlingFireball;
import logictechcorp.netherex.init.NetherExSoundEvents;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAIGhastlingFireballAttack extends EntityAIBase
{
    private final EntityGhast ghast;
    public int attackCounter;

    public EntityAIGhastlingFireballAttack(EntityGhastling ghast)
    {
        this.ghast = ghast;
    }

    @Override
    public boolean shouldExecute()
    {
        return this.ghast.getAttackTarget() != null;
    }

    @Override
    public void startExecuting()
    {
        this.attackCounter = 0;
    }

    @Override
    public void resetTask()
    {
        this.ghast.setAttacking(false);
    }

    @Override
    public void updateTask()
    {
        EntityLivingBase target = this.ghast.getAttackTarget();

        if(target.getDistanceSq(this.ghast) < 4096.0D && this.ghast.canEntityBeSeen(target))
        {
            World world = this.ghast.getEntityWorld();
            this.attackCounter++;

            if(this.attackCounter == 10)
            {
                this.ghast.playSound(NetherExSoundEvents.GHASTLING_WARN, 10.0F, (this.ghast.getRNG().nextFloat() - this.ghast.getRNG().nextFloat()) * 0.2F + 1.0F);
            }

            if(this.attackCounter == 20)
            {
                Vec3d look = this.ghast.getLook(1.0F);
                double distanceX = target.posX - (this.ghast.posX + look.x * 4.0D);
                double distanceY = target.getEntityBoundingBox().minY + (double) (target.height / 2.0F) - (0.5D + this.ghast.posY + (double) (this.ghast.height / 2.0F));
                double distanceZ = target.posZ - (this.ghast.posZ + look.z * 4.0D);
                this.ghast.playSound(NetherExSoundEvents.GHASTLING_SHOOT, 10.0F, (this.ghast.getRNG().nextFloat() - this.ghast.getRNG().nextFloat()) * 0.2F + 1.0F);
                EntityGhastlingFireball fireball = new EntityGhastlingFireball(world, this.ghast, distanceX, distanceY, distanceZ);
                fireball.explosionPower = this.ghast.getFireballStrength();
                fireball.posX = this.ghast.posX + look.x * 4.0D;
                fireball.posY = this.ghast.posY + (double) (this.ghast.height / 2.0F) + 0.5D;
                fireball.posZ = this.ghast.posZ + look.z * 4.0D;
                world.spawnEntity(fireball);
                this.attackCounter = -40;
            }
        }
        else if(this.attackCounter > 0)
        {
            this.attackCounter--;
        }

        this.ghast.setAttacking(this.attackCounter > 10);
    }
}
