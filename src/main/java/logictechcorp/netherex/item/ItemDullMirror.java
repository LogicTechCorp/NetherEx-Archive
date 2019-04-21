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

import logictechcorp.libraryex.item.ItemMod;
import logictechcorp.libraryex.utility.NBTHelper;
import logictechcorp.libraryex.utility.RandomHelper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExItems;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class ItemDullMirror extends ItemMod
{
    public ItemDullMirror()
    {
        super(NetherEx.getResource("dull_mirror"), NetherExItems.getDefaultItemProperties().copy().maxDamage(6));
        this.addPropertyOverride(new ResourceLocation("on"), (stack, world, entity) -> !stack.hasTagCompound() ? 0.0F : stack.getTagCompound().hasKey("SpawnPoint") ? 1.0F : 0.0F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        NBTTagCompound compound = stack.getTagCompound();

        if(compound != null)
        {
            if(compound.hasKey("SpawnDimension"))
            {
                DimensionType type = DimensionManager.getProviderType(compound.getInteger("SpawnDimension"));
                tooltip.add(I18n.format("tooltip.netherex:dull_mirror.spawn_dimension", StringUtils.capitalize(type.getName())));
            }
            if(compound.hasKey("SpawnPoint"))
            {
                BlockPos spawnPos = NBTUtil.getPosFromTag(compound.getCompoundTag("SpawnPoint"));
                tooltip.add(I18n.format("tooltip.netherex:dull_mirror.spawn_point", spawnPos.getX() + " " + spawnPos.getY() + " " + spawnPos.getZ()));
            }
            if(compound.hasKey("DeathPoint"))
            {
                BlockPos deathPos = NBTUtil.getPosFromTag(compound.getCompoundTag("DeathPoint"));
                tooltip.add(I18n.format("tooltip.netherex:dull_mirror.death_point", deathPos.getX() + " " + deathPos.getY() + " " + deathPos.getZ()));
            }
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        if(stack.getItemDamage() == (this.getMaxDamage(stack) - 1))
        {
            NBTTagCompound compound = NBTHelper.ensureTagExists(stack);

            if(!compound.getBoolean("RemovedSpawn") && entity instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) entity;

                if(compound.hasKey("SpawnDimension") && compound.hasKey("SpawnPoint"))
                {
                    int bedDimension = compound.getInteger("BedDimension");
                    player.setSpawnDimension(bedDimension);
                    player.setSpawnChunk(NBTUtil.getPosFromTag(compound.getCompoundTag("BedPoint")), true, bedDimension);
                    compound.setBoolean("RemovedSpawn", true);
                }
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);

        if(stack.getItemDamage() < this.getMaxDamage(stack) && player.isSneaking())
        {
            int spawnDimension = world.provider.getDimension();
            BlockPos spawnPoint = player.getPosition();
            NBTTagCompound compound = NBTHelper.ensureTagExists(stack);

            if(!compound.hasKey("SpawnDimension") || !compound.hasKey("SpawnPoint"))
            {
                compound.setInteger("BedDimension", player.getSpawnDimension());
                compound.setTag("BedPoint", NBTUtil.createPosTag(player.getBedLocation(player.getSpawnDimension())));
                compound.setInteger("SpawnDimension", spawnDimension);
                compound.setTag("SpawnPoint", NBTUtil.createPosTag(spawnPoint));
                player.setSpawnDimension(spawnDimension);
                player.setSpawnChunk(spawnPoint, true, spawnDimension);

                if(!world.isRemote)
                {
                    player.sendMessage(new TextComponentTranslation("tooltip.netherex:dull_mirror.spawn_point_set", spawnPoint.getX() + " " + spawnPoint.getY() + " " + spawnPoint.getZ()));
                }

                world.playSound((double) spawnPoint.getX() + 0.5D, (double) spawnPoint.getY() + 0.5D, (double) spawnPoint.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, RandomHelper.getRandom().nextFloat() * 0.4F + 0.8F, false);
            }
            else
            {
                int bedDimension = compound.getInteger("BedDimension");
                compound.removeTag("SpawnDimension");
                compound.removeTag("SpawnPoint");
                player.setSpawnDimension(bedDimension);
                player.setSpawnChunk(NBTUtil.getPosFromTag(compound.getCompoundTag("BedPoint")), true, bedDimension);

                if(!world.isRemote)
                {
                    player.sendMessage(new TextComponentTranslation("tooltip.netherex:dull_mirror.spawn_point_removed", spawnPoint.getX() + " " + spawnPoint.getY() + " " + spawnPoint.getZ()));
                }

                world.playSound((double) spawnPoint.getX() + 0.5D, (double) spawnPoint.getY() + 0.5D, (double) spawnPoint.getZ() + 0.5D, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 0.5F, RandomHelper.getRandom().nextFloat() * 0.4F + 0.8F, false);
            }

            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }

        return new ActionResult<>(EnumActionResult.FAIL, stack);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        return (double) stack.getItemDamage() / ((double) stack.getMaxDamage() - 1.0D);
    }

    @Override
    public void setDamage(ItemStack stack, int damage)
    {
        if(damage < this.getMaxDamage(stack))
        {
            super.setDamage(stack, damage);
        }
    }
}
