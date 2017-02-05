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

package nex.init;

import net.minecraft.init.Blocks;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class NetherExAchievements
{
    public static final Achievement STAYIN_FROSTY = new Achievement("achievement.stayinFrosty", "nex:stayinFrosty", 0, 0, Blocks.NETHERRACK, null).registerStat();
    public static final Achievement UH_OH = new Achievement("achievement.uhOh", "nex:uhOh", 1, 0, Blocks.NETHERRACK, null).registerStat();
    public static final Achievement CUTE_BUT_DEADLY = new Achievement("achievement.cuteButDeadly", "nex:cuteButDeadly", 2, 0, Blocks.NETHERRACK, null).registerStat();

    public static final Achievement IN_PLAIN_SIGHT = new Achievement("achievement.inPlainSight", "nex:inPlainSight", 0, 1, Blocks.NETHERRACK, null).registerStat();
    public static final Achievement FROM_WITHIN = new Achievement("achievement.fromWithin", "nex:fromWithin", 2, 1, Blocks.NETHERRACK, IN_PLAIN_SIGHT).registerStat();

    public static void init()
    {
        AchievementPage.registerAchievementPage(
                new AchievementPage("NetherEx",
                        STAYIN_FROSTY,
                        CUTE_BUT_DEADLY,
                        UH_OH,
                        IN_PLAIN_SIGHT,
                        FROM_WITHIN));
    }
}
