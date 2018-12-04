package logictechcorp.netherex.village;

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExLootTables;
import logictechcorp.netherex.init.NetherExTextures;
import net.minecraft.util.ResourceLocation;

public class PigtificateProfessionLeader extends PigtificateProfession
{
    private final PigtificateProfession.Career chief = new CareerChief(this);

    public PigtificateProfessionLeader()
    {
        super(new ResourceLocation(NetherEx.MOD_ID + ":leader"));
        this.registerCareer(this.chief);
    }

    public Career getChiefCareer()
    {
        return this.chief;
    }

    static class CareerChief extends PigtificateProfession.Career
    {
        CareerChief(PigtificateProfessionLeader profession)
        {
            super(new ResourceLocation(NetherEx.MOD_ID + ":chief"),
                    profession,
                    NetherExLootTables.PIGTIFICATE_CHIEF,
                    NetherExTextures.PIGTIFICATE_CHIEF,
                    NetherExTextures.PIGTIFICATE_CHIEF_ALT
            );
        }
    }
}
