package logictechcorp.netherex.mobeffect;

import logictechcorp.libraryex.potion.MobEffectMod;
import logictechcorp.netherex.NetherEx;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.PotionEffect;

public class MobEffectSoulSucked extends MobEffectMod
{
    public MobEffectSoulSucked()
    {
        super(NetherEx.instance, "soul_sucked", true, 188, 93, 200);
        this.registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "6D9EEBB1-F8CD-44C0-86AA-8D36B03B3625", -0.3D, 2);
    }

    @Override
    public boolean hasStatusIcon()
    {
        return false;
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
}
