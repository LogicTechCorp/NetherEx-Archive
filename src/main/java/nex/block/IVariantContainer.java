package nex.block;

import net.minecraft.util.IStringSerializable;

/**
 * An interface used to get Item/Block variants
 * <p>
 * Inspired by Vazkii:
 * https://github.com/Vazkii/Psi/blob/master/src/main/java/vazkii/psi/common/item/base/IVariantHolder.java
 * https://github.com/Vazkii/Psi/blob/master/src/main/java/vazkii/psi/common/block/base/IVariantEnumHolder.java
 */
public interface IVariantContainer<T extends Enum<T> & IStringSerializable>
{
    String[] getVariants();

    Class<T> getVariantEnum();
}
