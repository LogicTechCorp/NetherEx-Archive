package logictechcorp.netherex.village;

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExLootTables;
import logictechcorp.netherex.init.NetherExTextures;
import net.minecraft.util.ResourceLocation;

public class PigtificateProfessionForager extends PigtificateProfession
{
    private final PigtificateProfession.Career hunter = new CareerHunter(this);
    private final PigtificateProfession.Career gatherer = new CareerGatherer(this);
    private final PigtificateProfession.Career scavenger = new CareerScavenger(this);

    public PigtificateProfessionForager()
    {
        super(new ResourceLocation(NetherEx.MOD_ID + ":forager"));
        this.registerCareer(this.hunter);
        this.registerCareer(this.gatherer);
        this.registerCareer(this.scavenger);
    }

    public Career getHunterCareer()
    {
        return this.hunter;
    }

    public Career getGathererCareer()
    {
        return this.gatherer;
    }

    public Career getScavengerCareer()
    {
        return this.scavenger;
    }

    static class CareerHunter extends PigtificateProfession.Career
    {
        CareerHunter(PigtificateProfessionForager profession)
        {
            super(new ResourceLocation(NetherEx.MOD_ID + ":hunter"),
                    profession,
                    NetherExLootTables.PIGTIFICATE_HUNTER,
                    NetherExTextures.PIGTIFICATE_HUNTER,
                    NetherExTextures.PIGTIFICATE_HUNTER_ALT
            );
        }
    }

    static class CareerGatherer extends PigtificateProfession.Career
    {
        CareerGatherer(PigtificateProfessionForager profession)
        {
            super(new ResourceLocation(NetherEx.MOD_ID + ":gatherer"),
                    profession,
                    NetherExLootTables.PIGTIFICATE_GATHERER,
                    NetherExTextures.PIGTIFICATE_GATHERER,
                    NetherExTextures.PIGTIFICATE_GATHERER_ALT
            );
        }
    }

    static class CareerScavenger extends PigtificateProfession.Career
    {
        CareerScavenger(PigtificateProfessionForager profession)
        {
            super(new ResourceLocation(NetherEx.MOD_ID + ":scavenger"),
                    profession,
                    NetherExLootTables.PIGTIFICATE_SCAVENGER,
                    NetherExTextures.PIGTIFICATE_SCAVENGER,
                    NetherExTextures.PIGTIFICATE_SCAVENGER_ALT
            );
        }
    }
}
