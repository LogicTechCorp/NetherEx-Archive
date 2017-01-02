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

package nex.client.gui;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import nex.NetherEx;
import nex.handler.ConfigurationHandler;

import java.util.List;
import java.util.Set;

public class ModGuiConfig extends GuiConfig
{
    public ModGuiConfig(GuiScreen parentScreen)
    {
        super(parentScreen, getConfigElements(), NetherEx.MOD_ID, false, false, I18n.format("config." + NetherEx.MOD_ID));
    }

    private static List<IConfigElement> getConfigElements()
    {
        Configuration configuration = ConfigurationHandler.getConfiguration();

        ConfigCategory parentCategory = configuration.getCategory(Configuration.CATEGORY_GENERAL);
        Set<ConfigCategory> childCategories = parentCategory.getChildren();

        for(ConfigCategory category : childCategories)
        {
            category.setLanguageKey("config.nex:" + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, category.getName()));

            List<String> booleanProperties = Lists.newArrayList();
            List<String> intProperties = Lists.newArrayList();

            for(Property property : category.values())
            {
                property.setLanguageKey(category.getLanguagekey() + "." + property.getName());

                if(property.isBooleanValue())
                {
                    booleanProperties.add(property.getName());
                }
                else if(property.isIntValue())
                {
                    intProperties.add(property.getName());
                }
            }

            category.setPropertyOrder(intProperties);
            category.setPropertyOrder(booleanProperties);
        }

        return new ConfigElement(parentCategory).getChildElements();
    }
}
