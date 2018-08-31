package nex.potion;

import lex.potion.PotionLibEx;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import nex.NetherEx;

import java.util.ArrayList;
import java.util.List;

public class PotionBlueFire extends PotionLibEx
{
    public PotionBlueFire()
    {
        super(NetherEx.instance, "blue_fire", true, 0, 0, 0);
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier)
    {
        entity.attackEntityFrom(DamageSource.ON_FIRE, 1.0F);
    }

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return true;
    }

    @Override
    public boolean shouldRender(PotionEffect effect)
    {
        return false;
    }

    @Override
    public boolean shouldRenderInvText(PotionEffect effect)
    {
        return false;
    }

    @Override
    public boolean shouldRenderHUD(PotionEffect effect)
    {
        return false;
    }

    @Override
    public List<ItemStack> getCurativeItems()
    {
        return new ArrayList<>();
    }
}
