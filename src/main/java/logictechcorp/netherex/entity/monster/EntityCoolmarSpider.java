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

package logictechcorp.netherex.entity.monster;

import logictechcorp.netherex.init.NetherExEffects;
import logictechcorp.netherex.init.NetherExLootTables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityCoolmarSpider extends EntitySpider
{
    public EntityCoolmarSpider(World world)
    {
        super(world);
        this.setSize(1.5F, 1.0F);
        this.isImmuneToFire = true;
    }
    
    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity)
    {
        if(entity instanceof EntityPlayer)
        {
            ((EntityPlayer) entity).addPotionEffect(new PotionEffect(NetherExEffects.FROSTBITE, 320, 0));
        }
        return super.attackEntityAsMob(entity);
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return NetherExLootTables.COOLMAR_SPIDER;
    }
}
