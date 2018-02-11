/*
 * NetherEx
 * Copyright (c) 2016-2018 by MineEx
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

package nex.util;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Turns recipes into json form.
 * <p>
 * Written by the Botania team here:
 * https://github.com/Vazkii/Botania/blob/209b52bb80a766b15eff3c48cd1cd581f4020e97/src/main/java/vazkii/botania/common/crafting/ModCraftingRecipes.java
 *
 * @author Botania team
 */
public class RecipeHelper
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static File RECIPE_DIR = null;
    private static final Set<String> USED_ORE_DICTIONARY_NAMES = new TreeSet<>();
    private static final Set<String> USED_REGISTRY_NAMES = new TreeSet<>();

    public static void setupDir(File file)
    {
        if(RECIPE_DIR == null)
        {
            RECIPE_DIR = file;
        }
        if(!RECIPE_DIR.exists())
        {
            RECIPE_DIR.mkdir();
        }
    }

    private static void addShaped(ItemStack result, Object... components)
    {
        Map<String, Object> json = new LinkedHashMap<>();

        int i = 0;

        List<String> pattern = new ArrayList<>();

        while(i < components.length && components[i] instanceof String)
        {
            pattern.add((String) components[i]);
            i++;
        }

        boolean isOreDict = false;
        Map<String, Map<String, Object>> key = new HashMap<>();
        Character curKey = null;

        for(; i < components.length; i++)
        {
            Object o = components[i];

            if(o instanceof Character)
            {
                if(curKey != null)
                {
                    throw new IllegalArgumentException("Provided two char keys in a row");
                }
                curKey = (Character) o;
            }
            else
            {
                if(curKey == null)
                {
                    throw new IllegalArgumentException("Providing object without a char key");
                }
                if(o instanceof String)
                {
                    isOreDict = true;
                }
                key.put(Character.toString(curKey), serializeItem(o));
                curKey = null;
            }
        }

        json.put("type", isOreDict ? "forge:ore_shaped" : "minecraft:crafting_shaped");
        json.put("pattern", pattern);
        json.put("key", key);
        json.put("result", serializeItem(result));

        String registryName = result.getItem().getRegistryName().getResourcePath() + "_" + result.getItemDamage();
        File f = new File(RECIPE_DIR, registryName + ".json");

        while(f.exists())
        {
            registryName = findSuitableName(registryName);
            f = new File(RECIPE_DIR, registryName + ".json");
        }

        try(FileWriter w = new FileWriter(f))
        {
            GSON.toJson(json, w);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        USED_REGISTRY_NAMES.add(registryName);
    }

    private static void addShapeless(ItemStack result, Object... components)
    {
        Map<String, Object> json = new LinkedHashMap<>();

        boolean isOreDict = false;
        List<Map<String, Object>> ingredients = new ArrayList<>();

        for(Object o : components)
        {
            if(o instanceof String)
            {
                isOreDict = true;
            }

            ingredients.add(serializeItem(o));
        }

        json.put("type", isOreDict ? "forge:ore_shapeless" : "minecraft:crafting_shapeless");
        json.put("ingredients", ingredients);
        json.put("result", serializeItem(result));

        String registryName = result.getItem().getRegistryName().getResourcePath() + "_" + result.getItemDamage();
        File f = new File(RECIPE_DIR, registryName + ".json");

        while(f.exists())
        {
            registryName = findSuitableName(registryName);
            f = new File(RECIPE_DIR, registryName + ".json");
        }

        try(FileWriter w = new FileWriter(f))
        {
            GSON.toJson(json, w);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        USED_REGISTRY_NAMES.add(registryName);
    }

    private static String findSuitableName(String registryName)
    {
        if(registryName.contains("nex:"))
        {
            registryName = registryName.replace("nex:", "");
        }

        if(registryName.contains("minecraft:"))
        {
            registryName = registryName.replace("minecraft:", "");
        }

        if(USED_REGISTRY_NAMES.contains(registryName))
        {
            registryName = registryName + "_alt";
        }
        if(USED_REGISTRY_NAMES.contains(registryName))
        {
            registryName = registryName.replace("_alt", "_alt_2");
        }
        if(USED_REGISTRY_NAMES.contains(registryName))
        {
            registryName = registryName.replace("_alt_2", "_alt_3");
        }
        if(USED_REGISTRY_NAMES.contains(registryName))
        {
            registryName = registryName.replace("_alt_3", "_alt_4");
        }
        if(USED_REGISTRY_NAMES.contains(registryName))
        {
            registryName = registryName.replace("_alt_4", "_alt_5");
        }
        if(USED_REGISTRY_NAMES.contains(registryName))
        {
            registryName = registryName.replace("_alt_5", "_alt_6");
        }
        if(USED_REGISTRY_NAMES.contains(registryName))
        {
            registryName = registryName.replace("_alt_6", "_alt_7");
        }
        if(USED_REGISTRY_NAMES.contains(registryName))
        {
            registryName = registryName.replace("_alt_7", "_alt_8");
        }
        if(USED_REGISTRY_NAMES.contains(registryName))
        {
            registryName = registryName.replace("_alt_8", "_alt_9");
        }
        if(USED_REGISTRY_NAMES.contains(registryName))
        {
            registryName = registryName.replace("_alt_9", "_alt_10");
        }

        return registryName;
    }

    private static Map<String, Object> serializeItem(Object thing)
    {
        if(thing instanceof Item)
        {
            return serializeItem(new ItemStack((Item) thing));
        }
        if(thing instanceof Block)
        {
            return serializeItem(new ItemStack((Block) thing));
        }
        if(thing instanceof ItemStack)
        {
            ItemStack stack = (ItemStack) thing;
            Map<String, Object> ret = new HashMap<>();
            ret.put("item", stack.getItem().getRegistryName().toString());

            if(stack.getItem().getHasSubtypes() || stack.getItemDamage() != 0)
            {
                ret.put("data", stack.getItemDamage());
            }
            if(stack.getCount() > 1)
            {
                ret.put("count", stack.getCount());
            }

            if(stack.hasTagCompound())
            {
                ret.put("nbt", stack.getTagCompound().toString());
            }

            return ret;
        }
        if(thing instanceof String)
        {
            Map<String, Object> ret = new HashMap<>();
            USED_ORE_DICTIONARY_NAMES.add((String) thing);
            ret.put("item", "#" + ((String) thing).toUpperCase(Locale.ROOT));
            return ret;
        }

        throw new IllegalArgumentException("Not a block, item, stack, or od name");
    }

    private static void generateConstants()
    {
        List<Map<String, Object>> json = new ArrayList<>();

        for(String s : USED_ORE_DICTIONARY_NAMES)
        {
            Map<String, Object> entry = new HashMap<>();
            entry.put("name", s.toUpperCase(Locale.ROOT));
            entry.put("ingredient", ImmutableMap.of("type", "forge:ore_dict", "ore", s));
            json.add(entry);
        }

        try(FileWriter w = new FileWriter(new File(RECIPE_DIR, "_constants.json")))
        {
            GSON.toJson(json, w);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
