package logictechcorp.netherex.init;

import logictechcorp.libraryex.util.InjectionHelper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.village.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class NetherExPigtificates
{
    public static final PigtificateProfessionLeader LEADER = InjectionHelper.nullValue();
    public static final PigtificateProfessionDimwit DIMWIT = InjectionHelper.nullValue();
    public static final PigtificateProfessionForager FORAGER = InjectionHelper.nullValue();
    public static final PigtificateProfessionSmith SMITH = InjectionHelper.nullValue();
    public static final PigtificateProfessionSorcerer SORCERER = InjectionHelper.nullValue();

    public static void registerPigtificateCareers()
    {
        LEADER.registerDefaultCareers();
        DIMWIT.registerDefaultCareers();
        FORAGER.registerDefaultCareers();
        SMITH.registerDefaultCareers();
        SORCERER.registerDefaultCareers();
    }

    @Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterPigtificateProfessions(RegistryEvent.Register<PigtificateProfession> event)
        {
            event.getRegistry().registerAll(
                    new PigtificateProfessionLeader(),
                    new PigtificateProfessionDimwit(),
                    new PigtificateProfessionForager(),
                    new PigtificateProfessionSmith(),
                    new PigtificateProfessionSorcerer()
            );
        }
    }
}
