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

package nex.block;

import com.google.common.base.CaseFormat;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nex.NetherEx;
import nex.item.ItemBlockVariantContainer;

public class BlockNetherEx extends Block implements IVariantContainer
{
    private final String[] VARIANTS;
    private String propertyName;

    public BlockNetherEx(String name, boolean disableVariantsIn, Material material, SoundType type, String propertyNameIn, String... variants)
    {
        super(material);

        setSoundType(type);
        setCreativeTab(NetherEx.CREATIVE_TAB);

        propertyName = propertyNameIn;

        if(variants.length == 0)
        {
            variants = new String[]{name};
        }

        VARIANTS = variants;

        if(isBaseClass())
        {
            registerAndSetName(name, disableVariantsIn);
        }
    }

    @Override
    public String[] getVariants()
    {
        return VARIANTS;
    }

    @Override
    public String getPropertyName()
    {
        return propertyName;
    }

    public boolean isBaseClass()
    {
        return true;
    }

    public void registerAndSetName(String name, boolean disableSubtypes)
    {
        setRegistryName(NetherEx.MOD_ID + ":" + name);
        setUnlocalizedName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, getRegistryName().toString()));
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlockVariantContainer(this, getPropertyName(), disableSubtypes), getRegistryName());
    }
}
