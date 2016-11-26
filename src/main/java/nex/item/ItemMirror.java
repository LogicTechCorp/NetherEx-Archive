/*
 * Copyright (C) 2016.  LogicTechCorp
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

package nex.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.util.NBTUtil;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("ConstantConditions, unchecked")
public class ItemMirror extends ItemNetherEx
{
    public ItemMirror()
    {
        super("item_mirror");

        setMaxStackSize(1);

        addPropertyOverride(new ResourceLocation("on"), (stack, worldIn, entityIn) ->
                !stack.hasTagCompound() ? 0.0F : stack.getTagCompound().hasKey("SpawnPointMirror") ? 1.0F : 0.0F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
    {
        NBTUtil.setTag(stack);

        NBTTagCompound compound = stack.getTagCompound();

        if(compound.hasKey("SpawnPointBed"))
        {
            tooltip.add(I18n.format("tooltip.nex:itemMirror.bedSpawnPoint", Arrays.toString(compound.getIntArray("SpawnPointBed"))));
        }
        if(compound.hasKey("SpawnPointMirror"))
        {
            tooltip.add(I18n.format("tooltip.nex:itemMirror.mirrorSpawnPoint", Arrays.toString(compound.getIntArray("SpawnPointMirror"))));
        }
        if(compound.hasKey("DeathPoint"))
        {
            tooltip.add(I18n.format("tooltip.nex:itemMirror.deathPoint", Arrays.toString(compound.getIntArray("DeathPoint"))));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        BlockPos bed = player.getBedLocation();
        BlockPos spawn = world.getSpawnPoint();
        BlockPos mirror = player.getPosition();
        ItemStack stack = player.getHeldItem(hand);

        if(player.isSneaking())
        {
            if(stack.getItem() == this)
            {
                NBTTagCompound compound = NBTUtil.setTag(stack, stack.getTagCompound());

                if(bed != null)
                {
                    compound.setIntArray("SpawnPointBed", new int[]{bed.getX(), bed.getY(), bed.getZ()});
                }
                else
                {
                    compound.setIntArray("SpawnPointBed", new int[]{spawn.getX(), spawn.getY(), spawn.getZ()});
                }
                compound.setIntArray("SpawnPointMirror", new int[]{mirror.getX(), mirror.getY(), mirror.getZ()});
                stack.setTagCompound(compound);

                return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
            }
        }

        return new ActionResult(EnumActionResult.FAIL, player.getHeldItem(hand));
    }
}
