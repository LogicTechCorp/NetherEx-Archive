package nex.item;

import net.minecraft.util.IStringSerializable;

/**
 * This is a base implementation for items that use an enum to add variants
 * <p>
 * Inspired by Vazkii:
 * https://github.com/Vazkii/Psi/blob/master/src/main/java/vazkii/psi/common/block/base/BlockMetaVariants.java
 */
public class ItemVariantContainer<T extends Enum<T> & IStringSerializable> extends ItemBase
{
    public final Class<T> CLS;

    public ItemVariantContainer(String name, Class<T> cls)
    {
        super(name, enumToArray(cls));
        CLS = cls;
    }

    private static String[] enumToArray(Class cls)
    {
        Enum[] values = (Enum[]) cls.getEnumConstants();
        String[] variants = new String[values.length];

        for(int i = 0; i < values.length; i++)
        {
            variants[i] = values[i].name().toLowerCase();
        }

        return variants;
    }

    @Override
    public Class<T> getVariantEnum()
    {
        return CLS;
    }
}
