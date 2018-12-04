package logictechcorp.netherex.village;

import logictechcorp.libraryex.village.TraderProfession;
import net.minecraft.util.ResourceLocation;

public class PigtificateProfession extends TraderProfession<PigtificateProfession, PigtificateProfession.Career>
{
    public PigtificateProfession(ResourceLocation name)
    {
        super(name);
    }

    public class Career extends TraderProfession.Career
    {
        protected Career(ResourceLocation name, PigtificateProfession profession, ResourceLocation lootTable, ResourceLocation texture, ResourceLocation alternateTexture)
        {
            super(name, profession, lootTable, texture, alternateTexture);
        }
    }
}
