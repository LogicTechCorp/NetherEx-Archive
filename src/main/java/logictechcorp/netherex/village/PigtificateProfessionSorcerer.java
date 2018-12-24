package logictechcorp.netherex.village;

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExLootTables;
import logictechcorp.netherex.init.NetherExTextures;
import net.minecraft.util.ResourceLocation;

public class PigtificateProfessionSorcerer extends PigtificateProfession
{
    private final PigtificateProfession.Career enchanter = new CareerEnchanter(this);
    private final PigtificateProfession.Career brewer = new CareerBrewer(this);

    public PigtificateProfessionSorcerer()
    {
        super(new ResourceLocation(NetherEx.MOD_ID + ":sorcerer"));
        this.registerCareer(this.enchanter);
        this.registerCareer(this.brewer);
    }

    public Career getEnchanterCareer()
    {
        return this.enchanter;
    }

    public Career getBrewerCareer()
    {
        return this.brewer;
    }

    static class CareerEnchanter extends PigtificateProfession.Career
    {
        CareerEnchanter(PigtificateProfessionSorcerer profession)
        {
            super(new ResourceLocation(NetherEx.MOD_ID + ":enchanter"),
                    profession,
                    NetherExLootTables.PIGTIFICATE_ENCHANTER,
                    NetherExTextures.PIGTIFICATE_ENCHANTER,
                    NetherExTextures.PIGTIFICATE_ENCHANTER_ALT
            );
        }
    }

    static class CareerBrewer extends PigtificateProfession.Career
    {
        CareerBrewer(PigtificateProfessionSorcerer profession)
        {
            super(new ResourceLocation(NetherEx.MOD_ID + ":brewer"),
                    profession,
                    NetherExLootTables.PIGTIFICATE_BREWER,
                    NetherExTextures.PIGTIFICATE_BREWER,
                    NetherExTextures.PIGTIFICATE_BREWER_ALT
            );
        }
    }
}
