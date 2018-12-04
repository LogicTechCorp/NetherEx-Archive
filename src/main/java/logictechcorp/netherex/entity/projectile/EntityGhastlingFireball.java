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

package logictechcorp.netherex.entity.projectile;

import logictechcorp.netherex.entity.boss.EntityGhastQueen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityGhastlingFireball extends EntityFireball
{
    public int explosionPower;

    public EntityGhastlingFireball(World world)
    {
        super(world);
    }

    public EntityGhastlingFireball(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ)
    {
        super(worldIn, shooter, accelX, accelY, accelZ);
    }

    @Override
    protected void onImpact(RayTraceResult result)
    {
        if(!this.world.isRemote)
        {
            if(result.entityHit != null && !(result.entityHit instanceof EntityGhastQueen))
            {
                result.entityHit.attackEntityFrom(new EntityDamageSourceIndirect("ghastlingFireball", this, this.shootingEntity).setFireDamage().setProjectile(), 6.0F);
                this.applyEnchantments(this.shootingEntity, result.entityHit);
            }

            boolean grief = this.world.getGameRules().getBoolean("mobGriefing");
            this.world.newExplosion(null, this.posX, this.posY, this.posZ, (float) this.explosionPower, grief, grief);
            this.setDead();
        }
    }
}
