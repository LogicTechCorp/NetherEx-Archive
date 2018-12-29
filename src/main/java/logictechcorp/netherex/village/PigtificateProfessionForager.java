package logictechcorp.netherex.village;

import logictechcorp.libraryex.village.Trade;
import logictechcorp.libraryex.village.TradeStack;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExBlocks;
import logictechcorp.netherex.init.NetherExItems;
import logictechcorp.netherex.init.NetherExLootTables;
import logictechcorp.netherex.init.NetherExTextures;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;

public class PigtificateProfessionForager extends PigtificateProfession
{
    public PigtificateProfessionForager()
    {
        super(new ResourceLocation(NetherEx.MOD_ID + ":forager"));
    }

    public void registerDefaultCareers()
    {
        this.registerCareer(new CareerHunter(this));
        this.registerCareer(new CareerGatherer(this));
        this.registerCareer(new CareerScavenger(this));
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

            this.addTrade(new Trade(new TradeStack(Items.ROTTEN_FLESH, 9, 15), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 1), TradeStack.EMPTY, 4, 16, 1));
            this.addTrade(new Trade(new TradeStack(Items.SPIDER_EYE, 4, 6), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 1), TradeStack.EMPTY, 2, 8, 1));
            this.addTrade(new Trade(new TradeStack(Items.MAGMA_CREAM, 1, 2), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 1), TradeStack.EMPTY, 1, 8, 1));
            this.addTrade(new Trade(new TradeStack(NetherExItems.GHAST_MEAT_COOKED, 3, 7), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 2), TradeStack.EMPTY, 2, 4, 2));
            this.addTrade(new Trade(new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 2, 2), new TradeStack(NetherExItems.GHAST_MEAT_RAW, 1, 1), TradeStack.EMPTY, 2, 8, 2));
            this.addTrade(new Trade(new TradeStack(Items.ENDER_PEARL, 2, 4), new TradeStack(NetherExItems.GHAST_MEAT_RAW, 4, 6), TradeStack.EMPTY, 1, 8, 3));
            this.addTrade(new Trade(new TradeStack(Items.BLAZE_ROD, 2, 4), new TradeStack(NetherExItems.GHAST_MEAT_RAW, 4, 6), TradeStack.EMPTY, 1, 8, 3));
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

            this.addTrade(new Trade(new TradeStack(NetherExItems.CONGEALED_MAGMA_CREAM, 2, 2), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 1), TradeStack.EMPTY, 1, 8, 1));
            this.addTrade(new Trade(new TradeStack(NetherExBlocks.BROWN_ELDER_MUSHROOM, 2, 4), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 3), TradeStack.EMPTY, 2, 8, 1));
            this.addTrade(new Trade(new TradeStack(NetherExBlocks.RED_ELDER_MUSHROOM, 2, 4), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 3), TradeStack.EMPTY, 2, 8, 1));
            this.addTrade(new Trade(new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 1), new TradeStack(NetherExItems.WITHER_BONE, 32, 64), TradeStack.EMPTY, 1, 4, 1));
            this.addTrade(new Trade(new TradeStack(NetherExItems.ENOKI_MUSHROOM, 3, 3), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 2, 4), TradeStack.EMPTY, 2, 8, 2));
            this.addTrade(new Trade(new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 3, 3), new TradeStack(Items.QUARTZ, 12, 16), TradeStack.EMPTY, 1, 3, 2));
            this.addTrade(new Trade(new TradeStack(Items.NETHER_WART, 3, 5), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 3, 4), TradeStack.EMPTY, 2, 6, 3));
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

            this.addTrade(new Trade(new TradeStack(Blocks.COBBLESTONE, 4, 16), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 2), TradeStack.EMPTY, 4, 16, 1));
            this.addTrade(new Trade(new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 2), new TradeStack(Blocks.STONE, 16, 32), TradeStack.EMPTY, 1, 4, 1));
            this.addTrade(new Trade(new TradeStack(Blocks.DIRT, 1, 4), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 2), TradeStack.EMPTY, 4, 16, 1));
            this.addTrade(new Trade(new TradeStack(Blocks.GRAVEL, 8, 16), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 2), TradeStack.EMPTY, 4, 16, 1));
            this.addTrade(new Trade(new TradeStack(Blocks.LOG, 1, 4), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 3, 8), TradeStack.EMPTY, 2, 8, 2));
            this.addTrade(new Trade(new TradeStack(Items.IRON_INGOT, 1, 3), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 2), TradeStack.EMPTY, 1, 8, 2));
            this.addTrade(new Trade(new TradeStack(Items.COAL, 3, 7), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 2), TradeStack.EMPTY, 2, 4, 2));
            this.addTrade(new Trade(new TradeStack(Items.COAL, 1, 3, 7), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 2), TradeStack.EMPTY, 2, 4, 2));
            this.addTrade(new Trade(new TradeStack(Items.DIAMOND, 1, 2), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 4, 8), TradeStack.EMPTY, 1, 3, 3));
            this.addTrade(new Trade(new TradeStack(Items.BOOK, 1, 3), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 2, 4), TradeStack.EMPTY, 1, 4, 3));

        }
    }
}
