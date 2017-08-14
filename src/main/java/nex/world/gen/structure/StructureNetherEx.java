/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
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

package nex.world.gen.structure;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.Random;

public abstract class StructureNetherEx extends StructureComponent
{
    private final Field FIELD_MIRROR = ReflectionHelper.findField(StructureComponent.class, "field_186168_b", "mirror");
    private final Field FIELD_ROTATION = ReflectionHelper.findField(StructureComponent.class, "field_186169_c", "rotation");

    protected StructureNetherEx()
    {

    }

    public StructureNetherEx(int componentType)
    {
        super(componentType);
    }

    @Override
    protected abstract void writeStructureToNBT(NBTTagCompound compound);

    @Override
    protected abstract void readStructureFromNBT(NBTTagCompound compound, TemplateManager templateManager);

    @Override
    public abstract boolean addComponentParts(World world, Random rand, StructureBoundingBox boundingBoxIn);

    protected Mirror getMirror()
    {
        try
        {
            return (Mirror) FIELD_MIRROR.get(this);
        }
        catch(IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return Mirror.NONE;
    }

    protected Rotation getRotation()
    {
        try
        {
            return (Rotation) FIELD_ROTATION.get(this);
        }
        catch(IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return Rotation.NONE;
    }
}
