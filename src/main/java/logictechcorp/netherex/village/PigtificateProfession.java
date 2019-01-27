/*
 * NetherEx
 * Copyright (c) 2016-2019 by LogicTechCorp
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

package logictechcorp.netherex.village;

import logictechcorp.libraryex.entity.trader.TraderProfession;
import net.minecraft.util.ResourceLocation;

public abstract class PigtificateProfession extends TraderProfession<PigtificateProfession, PigtificateProfession.Career>
{
    public PigtificateProfession(ResourceLocation name)
    {
        super(name);
    }

    public static class Career extends TraderProfession.Career<PigtificateProfession, PigtificateProfession.Career>
    {
        protected Career(ResourceLocation name, PigtificateProfession profession, ResourceLocation lootTable, ResourceLocation texture, ResourceLocation alternateTexture)
        {
            super(name, profession, lootTable, texture, alternateTexture);
        }
    }
}
