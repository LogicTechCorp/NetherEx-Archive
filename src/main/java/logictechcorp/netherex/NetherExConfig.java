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

package logictechcorp.netherex;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class NetherExConfig
{
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static
    {
        Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static class Common
    {
        //Entity settings
        public final ForgeConfigSpec.IntValue spinoutSpinTime;
        public final ForgeConfigSpec.IntValue spinoutSpinCooldown;

        public Common(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Common configuration settings")
                    .push("common");
            builder.comment("Entity configuration settings")
                    .push("entity");
            builder.comment("Spinout configuration settings")
                    .push("spinout");

            this.spinoutSpinTime = builder
                    .comment("The number of seconds a spin will last.")
                    .defineInRange("spinTime", 6, 1, Integer.MAX_VALUE);
            this.spinoutSpinCooldown = builder
                    .comment("The number of seconds between each spin.")
                    .defineInRange("spinCooldown", 2, 1, Integer.MAX_VALUE);

            builder.pop();
            builder.pop();
            builder.pop();
        }
    }
}
