package se.fusion1013.items;

import dev.emi.trinkets.api.SlotReference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public interface IItemSetMethods {

    default void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {}
    default void trinketTick(ItemStack stack, SlotReference slot, LivingEntity entity) {}
    /**
     * Implement this if the entity should get some status effects while the set is active.
     * @return an array of status effects.
     */
    default StatusEffectInstance[] withActiveEffects() { return new StatusEffectInstance[0]; }

    default Text[] appendTooltipText() { return new Text[0]; }
    default String[] appendTooltipStrings() { return new String[0]; }

    default void triggerSetAbility(Entity entity) { }
    default Text[] setAbilityTooltipText() { return new Text[0]; }
    default String[] setAbilityTooltipString() { return new String[0]; }

}
