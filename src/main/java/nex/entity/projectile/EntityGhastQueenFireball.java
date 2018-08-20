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

package nex.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityGhastQueenFireball extends EntityFireball
{
    public int explosionPower;

    public EntityGhastQueenFireball(World worldIn)
    {
        super(worldIn);
    }

    public EntityGhastQueenFireball(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ)
    {
        super(worldIn, shooter, accelX, accelY, accelZ);
    }

    @Override
    protected void onImpact(RayTraceResult result)
    {
        if(!world.isRemote)
        {
            if(result.entityHit != null)
            {
                result.entityHit.attackEntityFrom(new EntityDamageSourceIndirect("ghastQueenFireball", this, shootingEntity).setFireDamage().setProjectile(), 24.0F);
                applyEnchantments(shootingEntity, result.entityHit);
            }

            boolean grief = world.getGameRules().getBoolean("mobGriefing");
            world.newExplosion(null, posX, posY, posZ, (float) explosionPower, grief, grief);
            setDead();
        }
    }
}
