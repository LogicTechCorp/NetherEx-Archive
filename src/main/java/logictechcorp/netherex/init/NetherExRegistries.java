package logictechcorp.netherex.init;

import logictechcorp.netherex.village.PigtificateProfession;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class NetherExRegistries
{
    public static final IForgeRegistry<PigtificateProfession> PIGTIFICATE_PROFESSIONS = GameRegistry.findRegistry(PigtificateProfession.class);
}
