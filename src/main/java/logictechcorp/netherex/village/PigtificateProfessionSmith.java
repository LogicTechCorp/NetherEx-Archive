package logictechcorp.netherex.village;

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExLootTables;
import logictechcorp.netherex.init.NetherExTextures;
import net.minecraft.util.ResourceLocation;

public class PigtificateProfessionSmith extends PigtificateProfession
{
    private final PigtificateProfession.Career armorsmith = new CareerArmorsmith(this);
    private final PigtificateProfession.Career toolsmith = new CareerToolsmith(this);

    public PigtificateProfessionSmith()
    {
        super(new ResourceLocation(NetherEx.MOD_ID + ":smith"));
        this.registerCareer(this.armorsmith);
        this.registerCareer(this.toolsmith);
    }

    public Career getArmorsmithCareer()
    {
        return this.armorsmith;
    }

    public Career getToolsmithCareer()
    {
        return this.toolsmith;
    }

    static class CareerArmorsmith extends PigtificateProfession.Career
    {
        CareerArmorsmith(PigtificateProfessionSmith profession)
        {
            super(new ResourceLocation(NetherEx.MOD_ID + ":armorsmith"),
                    profession,
                    NetherExLootTables.PIGTIFICATE_ARMORSMITH,
                    NetherExTextures.PIGTIFICATE_ARMORSMITH,
                    NetherExTextures.PIGTIFICATE_ARMORSMITH_ALT
            );
        }
    }

    static class CareerToolsmith extends PigtificateProfession.Career
    {
        CareerToolsmith(PigtificateProfessionSmith profession)
        {
            super(new ResourceLocation(NetherEx.MOD_ID + ":toolsmith"),
                    profession,
                    NetherExLootTables.PIGTIFICATE_TOOLSMITH,
                    NetherExTextures.PIGTIFICATE_TOOLSMITH,
                    NetherExTextures.PIGTIFICATE_TOOLSMITH_ALT
            );
        }
    }
}
