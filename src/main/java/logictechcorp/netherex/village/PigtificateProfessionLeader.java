package logictechcorp.netherex.village;

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExLootTables;
import logictechcorp.netherex.init.NetherExPigtificates;
import logictechcorp.netherex.init.NetherExTextures;
import net.minecraft.util.ResourceLocation;

public class PigtificateProfessionLeader extends PigtificateProfession
{
    public PigtificateProfessionLeader()
    {
        super(new ResourceLocation(NetherEx.MOD_ID + ":leader"));
        this.registerCareer(new CareerChief());
    }

    public class CareerChief extends PigtificateProfession.Career
    {
        CareerChief()
        {
            super(new ResourceLocation(NetherEx.MOD_ID + ":chief"),
                    NetherExPigtificates.LEADER,
                    NetherExLootTables.PIGTIFICATE_CHIEF,
                    NetherExTextures.PIGTIFICATE_CHIEF,
                    NetherExTextures.PIGTIFICATE_CHIEF_ALT
            );
        }
    }
}
