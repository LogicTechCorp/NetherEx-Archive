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

package logictechcorp.netherex.advancements.criterion;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.entity.passive.EntityPigtificate;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class PigtificateTradeTrigger implements ICriterionTrigger<PigtificateTradeTrigger.Instance>
{
    private static final ResourceLocation ID = new ResourceLocation(NetherEx.MOD_ID + ":pigtificate_trade");
    private final Map<PlayerAdvancements, PigtificateTradeTrigger.Listeners> listeners = new HashMap<>();

    @Override
    public void addListener(PlayerAdvancements advancements, ICriterionTrigger.Listener<PigtificateTradeTrigger.Instance> listener)
    {
        PigtificateTradeTrigger.Listeners listeners = this.listeners.get(advancements);

        if(listeners == null)
        {
            listeners = new PigtificateTradeTrigger.Listeners(advancements);
            this.listeners.put(advancements, listeners);
        }

        listeners.add(listener);
    }

    @Override
    public void removeListener(PlayerAdvancements advancements, ICriterionTrigger.Listener<PigtificateTradeTrigger.Instance> listener)
    {
        PigtificateTradeTrigger.Listeners listeners = this.listeners.get(advancements);

        if(listeners != null)
        {
            listeners.remove(listener);

            if(listeners.isEmpty())
            {
                this.listeners.remove(advancements);
            }
        }
    }

    @Override
    public void removeAllListeners(PlayerAdvancements advancements)
    {
        this.listeners.remove(advancements);
    }

    @Override
    public PigtificateTradeTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context)
    {
        EntityPredicate pigtificate = EntityPredicate.deserialize(json.get("pigtificate"));
        ItemPredicate item = ItemPredicate.deserialize(json.get("item"));
        return new PigtificateTradeTrigger.Instance(pigtificate, item);
    }

    public void trigger(EntityPlayerMP player, EntityPigtificate pigtificate, ItemStack item)
    {
        PigtificateTradeTrigger.Listeners listeners = this.listeners.get(player.getAdvancements());

        if(listeners != null)
        {
            listeners.trigger(player, pigtificate, item);
        }
    }

    @Override
    public ResourceLocation getId()
    {
        return ID;
    }

    public static class Instance extends AbstractCriterionInstance
    {
        private final EntityPredicate pigtificate;
        private final ItemPredicate item;

        public Instance(EntityPredicate pigtificate, ItemPredicate item)
        {
            super(PigtificateTradeTrigger.ID);
            this.pigtificate = pigtificate;
            this.item = item;
        }

        public boolean test(EntityPlayerMP player, EntityPigtificate pigtificate, ItemStack item)
        {
            if(!this.pigtificate.test(player, pigtificate))
            {
                return false;
            }
            else
            {
                return this.item.test(item);
            }
        }
    }

    static class Listeners
    {
        private final PlayerAdvancements playerAdvancements;
        private final Set<ICriterionTrigger.Listener<PigtificateTradeTrigger.Instance>> listeners = new HashSet<>();

        public Listeners(PlayerAdvancements playerAdvancementsIn)
        {
            this.playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty()
        {
            return this.listeners.isEmpty();
        }

        public void add(ICriterionTrigger.Listener<PigtificateTradeTrigger.Instance> listener)
        {
            this.listeners.add(listener);
        }

        public void remove(ICriterionTrigger.Listener<PigtificateTradeTrigger.Instance> listener)
        {
            this.listeners.remove(listener);
        }

        public void trigger(EntityPlayerMP player, EntityPigtificate pigtificate, ItemStack item)
        {
            List<ICriterionTrigger.Listener<PigtificateTradeTrigger.Instance>> list = null;

            for(ICriterionTrigger.Listener<PigtificateTradeTrigger.Instance> listener : this.listeners)
            {
                if((listener.getCriterionInstance()).test(player, pigtificate, item))
                {
                    if(list == null)
                    {
                        list = new ArrayList<>();
                    }

                    list.add(listener);
                }
            }

            if(list != null)
            {
                for(ICriterionTrigger.Listener<PigtificateTradeTrigger.Instance> listener : list)
                {
                    listener.grantCriterion(this.playerAdvancements);
                }
            }
        }
    }
}
