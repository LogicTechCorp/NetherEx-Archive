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

import logictechcorp.libraryex.utility.NBTHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class DullMirrorItem extends Item
{
    public DullMirrorItem(Properties properties)
    {
        super(properties);
        this.addPropertyOverride(new ResourceLocation("on"), (stack, world, entity) -> !stack.hasTag() ? 0.0F : stack.getTag().contains("SpawnPoint") ? 1.0F : 0.0F);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag)
    {
        CompoundNBT compound = stack.getTag();

        if(compound != null)
        {
            if(compound.contains("SpawnDimension"))
            {
                DimensionType type = DimensionType.byName(new ResourceLocation(compound.getString("SpawnDimension")));
                tooltip.add(new TranslationTextComponent("tooltip.netherex.dull_mirror.spawn_dimension", StringUtils.capitalize(type.getRegistryName().getPath().replace("_", " "))));
            }
            if(compound.contains("SpawnPoint"))
            {
                BlockPos spawnPos = NBTUtil.readBlockPos(compound.getCompound("SpawnPoint"));
                tooltip.add(new TranslationTextComponent("tooltip.netherex.dull_mirror.spawn_point", spawnPos.getX() + " " + spawnPos.getY() + " " + spawnPos.getZ()));
            }
            if(compound.contains("DeathPoint"))
            {
                BlockPos deathPos = NBTUtil.readBlockPos(compound.getCompound("DeathPoint"));
                tooltip.add(new TranslationTextComponent("tooltip.netherex.dull_mirror.death_point", deathPos.getX() + " " + deathPos.getY() + " " + deathPos.getZ()));
            }
        }
    }


    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        if(stack.getDamage() == (this.getMaxDamage(stack) - 1))
        {
            CompoundNBT compound = NBTHelper.ensureTagExists(stack);

            if(!compound.getBoolean("RemovedSpawn") && entity instanceof PlayerEntity)
            {
                PlayerEntity player = (PlayerEntity) entity;

                if(compound.contains("SpawnDimension") && compound.contains("SpawnPoint"))
                {
                    DimensionType bedDimension = DimensionType.byName(new ResourceLocation(compound.getString("BedDimension")));

                    if(bedDimension != null)
                    {
                        player.setSpawnDimenion(bedDimension);
                        player.setSpawnPoint(NBTUtil.readBlockPos(compound.getCompound("BedPoint")), true, bedDimension);
                        compound.putBoolean("RemovedSpawn", true);
                    }
                }
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
    {
        ItemStack stack = player.getHeldItem(hand);

        if(!world.isRemote)
        {
            if(stack.getDamage() < stack.getMaxDamage() && player.isSneaking())
            {
                DimensionType spawnDimension = world.getDimension().getType();
                BlockPos spawnPoint = player.getPosition();
                CompoundNBT compound = NBTHelper.ensureTagExists(stack);

                if(!compound.contains("SpawnDimension") || !compound.contains("SpawnPoint"))
                {
                    DimensionType bedDimension = player.getSpawnDimension();
                    BlockPos bedPos = player.getBedLocation(bedDimension);

                    if(bedPos == null)
                    {
                        bedPos = world.getServer().getWorld(bedDimension).getSpawnPoint();
                    }

                    compound.putString("BedDimension", bedDimension.getRegistryName().toString());
                    compound.put("BedPoint", NBTUtil.writeBlockPos(bedPos));
                    compound.putString("SpawnDimension", spawnDimension.getRegistryName().toString());
                    compound.put("SpawnPoint", NBTUtil.writeBlockPos(spawnPoint));
                    player.setSpawnDimenion(spawnDimension);
                    player.setSpawnPoint(spawnPoint, true, spawnDimension);
                    player.sendMessage(new TranslationTextComponent("tooltip.netherex.dull_mirror.spawn_point_set", spawnPoint.getX() + " " + spawnPoint.getY() + " " + spawnPoint.getZ()));
                    world.playSound((double) spawnPoint.getX() + 0.5D, (double) spawnPoint.getY() + 0.5D, (double) spawnPoint.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.4F + 0.8F, false);
                }
                else
                {
                    DimensionType bedDimension = DimensionType.byName(new ResourceLocation(compound.getString("BedDimension")));

                    if(bedDimension != null)
                    {
                        compound.remove("SpawnDimension");
                        compound.remove("SpawnPoint");
                        player.setSpawnDimenion(bedDimension);
                        player.setSpawnPoint(NBTUtil.readBlockPos(compound.getCompound("BedPoint")), true, bedDimension);
                        player.sendMessage(new TranslationTextComponent("tooltip.netherex.dull_mirror.spawn_point_removed", spawnPoint.getX() + " " + spawnPoint.getY() + " " + spawnPoint.getZ()));
                        world.playSound((double) spawnPoint.getX() + 0.5D, (double) spawnPoint.getY() + 0.5D, (double) spawnPoint.getZ() + 0.5D, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.4F + 0.8F, false);
                    }
                }

                return new ActionResult<>(ActionResultType.SUCCESS, stack);
            }
        }

        return new ActionResult<>(ActionResultType.FAIL, stack);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        return (double) stack.getDamage() / ((double) stack.getMaxDamage() - 1.0D);
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