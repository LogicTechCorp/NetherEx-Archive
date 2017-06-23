/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package nex.village;

import com.google.common.collect.Lists;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandom;
import net.minecraft.village.MerchantRecipe;
import nex.init.NetherExLootTables;
import nex.init.NetherExTextures;
import nex.util.RandomUtil;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Trade extends MerchantRecipe
{
    private final int outputMinStackSize;
    private final int outputMaxStackSize;
    private final int inputAMinStackSize;
    private final int inputAMaxStackSize;
    private final int inputBMinStackSize;
    private final int inputBMaxStackSize;
    private final int minTradesAvailable;
    private final int maxTradesAvailable;

    private final List<Trade.Offer.Enchantment> outputEnchantments;

    public Trade(ItemStack output, int outputMinStackSizeIn, int outputMaxStackSizeIn, ItemStack inputA, int inputAMinStackSizeIn, int inputAMaxStackSizeIn, ItemStack inputB, int inputBMinStackSizeIn, int inputBMaxStackSizeIn, int minTradesAvailableIn, int maxTradesAvailableIn, List<Trade.Offer.Enchantment> outputEnchantmentsIn)
    {
        super(inputA, inputB, output, 0, 1);

        outputMinStackSize = outputMinStackSizeIn;
        outputMaxStackSize = outputMaxStackSizeIn;
        inputAMinStackSize = inputAMinStackSizeIn;
        inputAMaxStackSize = inputAMaxStackSizeIn;
        inputBMinStackSize = inputBMinStackSizeIn;
        inputBMaxStackSize = inputBMaxStackSizeIn;
        minTradesAvailable = minTradesAvailableIn;
        maxTradesAvailable = maxTradesAvailableIn;
        outputEnchantments = outputEnchantmentsIn;
    }

    public MerchantRecipe getRandomTrade(Random rand)
    {
        ItemStack outputStack = getItemToSell().copy();
        ItemStack inputA = getItemToBuy().copy();
        ItemStack inputB = getSecondItemToBuy().copy();
        outputStack.setCount(RandomUtil.getNumberInRange(outputMinStackSize, outputMaxStackSize, rand));
        inputA.setCount(RandomUtil.getNumberInRange(inputAMinStackSize, inputAMaxStackSize, rand));
        inputB.setCount(RandomUtil.getNumberInRange(inputBMinStackSize, inputBMaxStackSize, rand));

        if(outputEnchantments.size() > 0)
        {
            for(Trade.Offer.Enchantment outputEnchantment : outputEnchantments)
            {
                Enchantment enchantment = Enchantment.getEnchantmentByLocation(outputEnchantment.getName());

                if(enchantment != null)
                {
                    if(outputStack.getItem() instanceof ItemEnchantedBook)
                    {
                        ((ItemEnchantedBook) outputStack.getItem()).addEnchantment(outputStack, new EnchantmentData(enchantment, RandomUtil.getNumberInRange(enchantment.getMinLevel(), enchantment.getMaxLevel(), rand)));
                    }
                    else
                    {
                        outputStack.addEnchantment(enchantment, RandomUtil.getNumberInRange(enchantment.getMinLevel(), enchantment.getMaxLevel(), rand));
                    }
                }
            }
        }

        return new MerchantRecipe(inputA, inputB, outputStack, 0, RandomUtil.getNumberInRange(minTradesAvailable, maxTradesAvailable, rand));
    }

    public static class TradeList
    {
        private String name;
        private List<Trade.Profession> professions;

        public String getName()
        {
            return name;
        }

        public List<Trade.Profession> getProfessions()
        {
            return professions;
        }
    }

    public static class Profession
    {
        private String name;
        private List<Trade.Career> careers;

        public String getName()
        {
            return name;
        }

        public List<Trade.Career> getCareers()
        {
            return careers;
        }

        public enum EnumType
        {
            LEADER,
            FORAGER,
            BLACKSMITH,
            SORCERER;

            public static EnumType getRandom(Random rand, boolean isLeader)
            {
                if(isLeader)
                {
                    return LEADER;
                }
                else
                {
                    List<EnumType> types = Lists.newArrayList();
                    Collections.addAll(types, values());
                    types.remove(LEADER);
                    return types.get(rand.nextInt(types.size()));
                }
            }

            public static EnumType fromIndex(int index)
            {
                EnumType profession = values()[index];
                return profession != null ? profession : FORAGER;
            }

            public static EnumType fromProfession(Trade.Profession profession)
            {
                return valueOf(profession.getName().toUpperCase());
            }
        }
    }

    public static class Career
    {
        private String name;
        private List<Trade.Offer> trades;

        public String getName()
        {
            return name;
        }

        public List<Trade.Offer> getTrades()
        {
            return trades;
        }

        public static class Weighted extends WeightedRandom.Item
        {
            private EnumType type;

            public Weighted(EnumType typeIn)
            {
                super(typeIn.getWeight());
                type = typeIn;
            }

            public EnumType getType()
            {
                return type;
            }
        }

        public enum EnumType
        {
            CHIEF(Trade.Profession.EnumType.LEADER, 16, NetherExTextures.ENTITY_PIGTIFICATE_CHIEF, NetherExLootTables.ENTITY_PIGTIFICATE_CHIEF),
            HUNTER(Trade.Profession.EnumType.FORAGER, 5, NetherExTextures.ENTITY_PIGTIFICATE_HUNTER, NetherExLootTables.ENTITY_PIGTIFICATE_HUNTER),
            GATHERER(Trade.Profession.EnumType.FORAGER, 5, NetherExTextures.ENTITY_PIGTIFICATE_GATHERER, NetherExLootTables.ENTITY_PIGTIFICATE_GATHERER),
            SCAVENGER(Trade.Profession.EnumType.FORAGER, 5, NetherExTextures.ENTITY_PIGTIFICATE_SCAVENGER, NetherExLootTables.ENTITY_PIGTIFICATE_SCAVENGER),
            ARMORSMITH(Trade.Profession.EnumType.BLACKSMITH, 8, NetherExTextures.ENTITY_PIGTIFICATE_ARMORSMITH, NetherExLootTables.ENTITY_PIGTIFICATE_ARMORSMITH),
            TOOLSMITH(Trade.Profession.EnumType.BLACKSMITH, 8, NetherExTextures.ENTITY_PIGTIFICATE_TOOLSMITH, NetherExLootTables.ENTITY_PIGTIFICATE_TOOLSMITH),
            ENCHANTER(Trade.Profession.EnumType.SORCERER, 8, NetherExTextures.ENTITY_PIGTIFICATE_ENCHANTER, NetherExLootTables.ENTITY_PIGTIFICATE_ENCHANTER),
            BREWER(Trade.Profession.EnumType.SORCERER, 8, NetherExTextures.ENTITY_PIGTIFICATE_BREWER, NetherExLootTables.ENTITY_PIGTIFICATE_BREWER);

            private Trade.Profession.EnumType profession;
            private int weight;
            private ResourceLocation texture;
            private ResourceLocation lootTable;

            EnumType(Trade.Profession.EnumType professionIn, int weightIn, ResourceLocation textureIn, ResourceLocation lootTableIn)
            {
                profession = professionIn;
                weight = weightIn;
                texture = textureIn;
                lootTable = lootTableIn;
            }

            public static EnumType fromIndex(int index)
            {
                EnumType career = values()[index];
                return career != null ? career : HUNTER;
            }

            public static EnumType fromCareer(Trade.Career career)
            {
                return valueOf(career.getName().toUpperCase());
            }

            public int getWeight()
            {
                return weight;
            }

            public ResourceLocation getTexture()
            {
                return texture;
            }

            public ResourceLocation getLootTable()
            {
                return lootTable;
            }

            public Trade.Profession.EnumType getProfession()
            {
                return profession;
            }
        }
    }

    public static class Offer
    {
        private Output output;
        private Input inputA;
        private Input inputB;
        private int minTradesAvailable;
        private int maxTradesAvailable;
        private int level;

        public Output getOutput()
        {
            return output;
        }

        public Input getInputA()
        {
            return inputA;
        }

        public Input getInputB()
        {
            return inputB;
        }

        public int getMinTradesAvailable()
        {
            return minTradesAvailable;
        }

        public int getMaxTradesAvailable()
        {
            return maxTradesAvailable;
        }

        public int getLevel()
        {
            return level;
        }


        public class Output
        {
            private String itemId;
            private int meta;
            private int minStackSize;
            private int maxStackSize;
            private List<Enchantment> enchantments;
            private Display display;

            public String getId()
            {
                return itemId;
            }

            public int getMeta()
            {
                return meta;
            }

            public int getMinStackSize()
            {
                return minStackSize;
            }

            public int getMaxStackSize()
            {
                return maxStackSize;
            }

            public List<Enchantment> getEnchantments()
            {
                return enchantments;
            }

            public Display getDisplay()
            {
                return display;
            }
        }

        public static class Input
        {
            private String itemId;
            private int meta;
            private int minStackSize;
            private int maxStackSize;

            public Input()
            {
                itemId = "minecraft:air";
                meta = 0;
                minStackSize = 1;
                maxStackSize = 1;
            }

            public String getId()
            {
                return itemId;
            }

            public int getMeta()
            {
                return meta;
            }

            public int getMinStackSize()
            {
                return minStackSize;
            }

            public int getMaxStackSize()
            {
                return maxStackSize;
            }
        }

        public class Enchantment
        {
            private String name;
            private int minLevel;
            private int maxLevel;

            public String getName()
            {
                return name;
            }

            public int getMinLevel()
            {
                return minLevel;
            }

            public int getMaxLevel()
            {
                return maxLevel;
            }
        }

        public class Display
        {
            private String name;
            private List<String> lore;

            public String getName()
            {
                return name;
            }

            public List<String> getLore()
            {
                return lore;
            }
        }
    }
}
