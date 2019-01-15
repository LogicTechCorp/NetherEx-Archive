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

package logictechcorp.netherex.item;

import logictechcorp.libraryex.item.ItemBlockEdible;
import logictechcorp.libraryex.item.builder.ItemEdibleBuilder;
import logictechcorp.netherex.init.NetherExBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class ItemBlockElderMushroom extends ItemBlockEdible
{
    public ItemBlockElderMushroom(Block block)
    {
        super(block, new ItemEdibleBuilder(0, 0.0F, false));
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving)
    {
        if(entityLiving instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entityLiving;

            if(this.block == NetherExBlocks.BROWN_ELDER_MUSHROOM)
            {
                player.attackEntityFrom(DamageSource.GENERIC, player.getRNG().nextInt((12 - 8) + 1) + 4);
                player.getFoodStats().setFoodLevel(20);

            }
            else if(this.block == NetherExBlocks.RED_ELDER_MUSHROOM)
            {
                player.heal(player.getMaxHealth());
                player.getFoodStats().setFoodLevel(0);
            }

            world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
            this.onFoodEaten(stack, world, player);
            player.addStat(StatList.getObjectUseStats(this));
        }

        stack.shrink(1);
        return stack;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);

        if(stack.getItemDamage() == 0 ? player.getFoodStats().needFood() : player.getHealth() < player.getMaxHealth())
        {
            player.setActiveHand(hand);
            return new ActionResult(EnumActionResult.SUCCESS, stack);
        }
        else
        {
            return new ActionResult(EnumActionResult.FAIL, stack);
        }
    }
}
