package logictechcorp.netherex.village;

import logictechcorp.libraryex.util.ItemHelper;
import logictechcorp.libraryex.village.Trade;
import logictechcorp.libraryex.village.TradeStack;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExItems;
import logictechcorp.netherex.init.NetherExLootTables;
import logictechcorp.netherex.init.NetherExTextures;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;

public class PigtificateProfessionSorcerer extends PigtificateProfession
{
    public PigtificateProfessionSorcerer()
    {
        super(new ResourceLocation(NetherEx.MOD_ID + ":sorcerer"));
    }

    public void registerDefaultCareers()
    {
        this.registerCareer(new CareerEnchanter(this));
        this.registerCareer(new CareerBrewer(this));
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

            this.addTrade(new Trade(new TradeStack(Items.BOOK, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 5, 10), TradeStack.EMPTY, 2, 8, 1));
            this.addTrade(new Trade(new TradeStack(Items.GLOWSTONE_DUST, 3, 6), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 1), TradeStack.EMPTY, 4, 8, 1));
            this.addTrade(new Trade(new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 2), new TradeStack(Items.BLAZE_ROD, 4, 8), TradeStack.EMPTY, 1, 4, 1));
            this.addTrade(new Trade(new TradeStack(ItemHelper.getRandomlyEnchantedBook(1), 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 4, 16), TradeStack.EMPTY, 1, 2, 1));
            this.addTrade(new Trade(new TradeStack(ItemHelper.getRandomlyEnchantedBook(3), 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 8, 32), TradeStack.EMPTY, 1, 4, 2));
            this.addTrade(new Trade(new TradeStack(ItemHelper.getRandomlyEnchantedBook(3), 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 8, 32), TradeStack.EMPTY, 1, 4, 2));
            this.addTrade(new Trade(new TradeStack(Items.EXPERIENCE_BOTTLE, 1, 4), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 6, 16), TradeStack.EMPTY, 1, 2, 3));
            this.addTrade(new Trade(new TradeStack(ItemHelper.getRandomlyEnchantedBook(5), 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 12, 48), TradeStack.EMPTY, 1, 6, 3));
            this.addTrade(new Trade(new TradeStack(ItemHelper.getRandomlyEnchantedBook(5), 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 12, 48), TradeStack.EMPTY, 1, 6, 3));
            this.addTrade(new Trade(new TradeStack(ItemHelper.getRandomlyEnchantedBook(5), 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 12, 48), TradeStack.EMPTY, 1, 6, 3));
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

            this.addTrade(new Trade(new TradeStack(NetherExItems.SPORE, 2, 5), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 1), TradeStack.EMPTY, 1, 8, 1));
            this.addTrade(new Trade(new TradeStack(Items.MAGMA_CREAM, 2, 5), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 2), TradeStack.EMPTY, 2, 4, 1));
            this.addTrade(new Trade(new TradeStack(Items.GLASS_BOTTLE, 3, 6), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 1), TradeStack.EMPTY, 4, 8, 1));
            this.addTrade(new Trade(new TradeStack(ItemHelper.getRandomPotion(), 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 3, 4), TradeStack.EMPTY, 1, 4, 1));
            this.addTrade(new Trade(new TradeStack(Items.BLAZE_POWDER, 1, 2), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 6, 12), TradeStack.EMPTY, 1, 4, 2));
            this.addTrade(new Trade(new TradeStack(ItemHelper.getRandomPotion(), 1, 2), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 4, 5), TradeStack.EMPTY, 1, 2, 2));
            this.addTrade(new Trade(new TradeStack(ItemHelper.getRandomPotion(), 1, 3), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 5, 6), TradeStack.EMPTY, 1, 2, 3));
        }
    }
}
