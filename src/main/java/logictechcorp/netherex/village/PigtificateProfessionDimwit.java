/*
 * LibraryEx
 * Copyright (c) 2017-2018 by MineEx
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
 *
 */

package logictechcorp.netherex.village;

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExLootTables;
import logictechcorp.netherex.init.NetherExTextures;
import net.minecraft.util.ResourceLocation;

public class PigtificateProfessionDimwit extends PigtificateProfession
{
    public PigtificateProfessionDimwit()
    {
        super(new ResourceLocation(NetherEx.MOD_ID + ":dimwit"));
    }

    public void registerDefaultCareers()
    {
        this.registerCareer(new CareerNincompoop(this));
    }

    static class CareerNincompoop extends Career
    {
        CareerNincompoop(PigtificateProfessionDimwit profession)
        {
            super(new ResourceLocation(NetherEx.MOD_ID + ":nincompoop"),
                    profession,
                    NetherExLootTables.PIGTIFICATE_NINCOMPOOP,
                    NetherExTextures.PIGTIFICATE_NINCOMPOOP,
                    NetherExTextures.PIGTIFICATE_NINCOMPOOP_ALT
            );
        }
    }
}
