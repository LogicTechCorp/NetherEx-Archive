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

package logictechcorp.netherex.entity.hostile;

import logictechcorp.netherex.potion.NetherExEffects;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;

public class CoolmarSpiderEntity extends SpiderEntity
{
    public CoolmarSpiderEntity(EntityType<? extends SpiderEntity> entityType, World world)
    {
        super(entityType, world);
    }

    public static boolean canSpawn(EntityType<CoolmarSpiderEntity> entityType, IWorld world, SpawnReason spawnReason, BlockPos pos, Random random)
    {
        Block block = world.getBlockState(pos.down()).getBlock();
        return world.getDifficulty() != Difficulty.PEACEFUL && block != Blocks.MAGMA_BLOCK;

    }

    @Override
    protected void registerAttributes()
    {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity)
    {
        if(entity instanceof PlayerEntity)
        {
            ((PlayerEntity) entity).addPotionEffect(new EffectInstance(NetherExEffects.FROSTBITTEN.get(), 320, 0));
        }
        return super.attackEntityAsMob(entity);
    }
}