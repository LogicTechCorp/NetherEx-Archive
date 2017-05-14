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

package nex.village.trade;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import nex.NetherEx;
import nex.util.FileUtil;
import nex.util.NBTUtil;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class TradeListManager
{
    private static HashMap<TradeCareer.EnumType, HashMap<Integer, List<Trade>>> offerLists = Maps.newHashMap();
    private static Random rand = new Random();

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|TradeListManager");

    public static void init(File directory)
    {
        for(TradeCareer.EnumType career : TradeCareer.EnumType.values())
        {
            offerLists.put(career, Maps.newHashMap());
        }

        try
        {
            LOGGER.info("Copying the Trade List Directory to the config folder.");

            if(NetherEx.IS_DEV_ENV)
            {
                FileUtils.copyDirectory(new File(NetherEx.class.getResource("/assets/nex/trade_lists").getFile()), directory);
            }
            else
            {
                FileUtil.extractFromJar("/assets/nex/trade_lists", directory.getPath());
            }
        }
        catch(IOException e)
        {
            LOGGER.fatal("The attempt to copy the Trade List Directory to the config folder was unsuccessful.");
            LOGGER.fatal(e);
        }

        try
        {
            Gson gson = new Gson();
            List<File> tradeFiles = Lists.newArrayList(directory.listFiles());

            for(File tradeFile : tradeFiles)
            {
                String jsonText = Files.toString(tradeFile, Charsets.UTF_8);
                TradeList tradeList = gson.fromJson(jsonText, TradeList.class);

                LOGGER.info("Adding trades from the " + tradeList.getName() + ".");

                for(TradeProfession profession : tradeList.getProfessions())
                {
                    for(TradeCareer career : profession.getCareers())
                    {
                        for(TradeOffer offer : career.getTrades())
                        {
                            ItemStack outputStack;
                            TradeOffer.Output output = offer.getOutput();
                            String outputId = output.getName();
                            int outputMeta = output.getMeta();
                            TradeOffer.Amount outputAmount = output.getAmount();
                            List<TradeOffer.Enchantment> outputEnchantments = output.getEnchantments();
                            TradeOffer.Display outputDisplay = output.getDisplay();

                            if(Item.getByNameOrId(outputId) != null)
                            {
                                outputStack = new ItemStack(Item.getByNameOrId(outputId), 1, outputMeta);
                            }
                            else if(Block.getBlockFromName(outputId) != null)
                            {
                                outputStack = new ItemStack(Block.getBlockFromName(outputId), 1, outputMeta);
                            }
                            else
                            {
                                continue;
                            }

                            if(outputEnchantments == null)
                            {
                                outputEnchantments = Lists.newArrayList();
                            }

                            if(outputDisplay != null)
                            {
                                if(!Strings.isNullOrEmpty(outputDisplay.getName()))
                                {
                                    outputStack.setStackDisplayName(outputDisplay.getName());
                                }
                                if(outputDisplay.getLore().size() > 0)
                                {
                                    NBTUtil.setTag(outputStack);
                                    NBTTagList loreList = new NBTTagList();

                                    for(String lore : outputDisplay.getLore())
                                    {
                                        loreList.appendTag(new NBTTagString(lore));
                                    }

                                    NBTTagCompound displayCompound = new NBTTagCompound();
                                    displayCompound.setTag("Lore", loreList);
                                    NBTTagCompound compound = new NBTTagCompound();
                                    compound.setTag("display", displayCompound);
                                    NBTUtil.setTag(outputStack, compound);
                                }
                            }

                            ItemStack inputStackA;
                            TradeOffer.Input inputA = offer.getInputA();
                            String inputAId = inputA.getName();
                            int inputAMeta = inputA.getMeta();
                            TradeOffer.Amount inputAAmount = inputA.getAmount();

                            if(Item.getByNameOrId(inputAId) != null)
                            {
                                inputStackA = new ItemStack(Item.getByNameOrId(inputAId), 1, inputAMeta);
                            }
                            else if(Block.getBlockFromName(inputAId) != null)
                            {
                                inputStackA = new ItemStack(Block.getBlockFromName(inputAId), 1, inputAMeta);
                            }
                            else
                            {
                                continue;
                            }

                            ItemStack inputStackB;
                            TradeOffer.Input inputB = offer.getInputB();

                            if(inputB == null)
                            {
                                inputB = new TradeOffer.Input("minecraft:air", 0, new TradeOffer.Amount(1, 1));
                            }

                            String inputBId = inputB.getName();
                            int inputBMeta = inputB.getMeta();
                            TradeOffer.Amount inputBAmount = inputB.getAmount();

                            if(Item.getByNameOrId(inputBId) != null)
                            {
                                inputStackB = new ItemStack(Item.getByNameOrId(inputBId), 1, inputBMeta);
                            }
                            else if(Block.getBlockFromName(inputBId) != null)
                            {
                                inputStackB = new ItemStack(Block.getBlockFromName(inputBId), 1, inputBMeta);
                            }
                            else
                            {
                                continue;
                            }

                            TradeOffer.Amount offerAmount = offer.getAmount();
                            Trade trade = new Trade(outputStack, outputAmount, inputStackA, inputAAmount, inputStackB, inputBAmount, offerAmount, outputEnchantments);
                            offerLists.get(TradeCareer.EnumType.fromCareer(career)).computeIfAbsent(offer.getLevel(), k -> Lists.newArrayList()).add(trade);
                        }
                    }
                }
            }
        }
        catch(IOException e)
        {
            LOGGER.fatal("NetherEx was unable to read the Trade lists.");
            LOGGER.fatal(e);
        }
    }

    public static List<Trade> getTrades(TradeCareer.EnumType type, int level)
    {
        List<Trade> trades = Lists.newArrayList();

        if(offerLists.get(type).containsKey(level))
        {
            trades.addAll(offerLists.get(type).get(level));
        }

        return trades;
    }
}
