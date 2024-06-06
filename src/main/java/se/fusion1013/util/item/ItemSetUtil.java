package se.fusion1013.util.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

public class ItemSetUtil {

    public static void addSetBonusStatusEffect(Entity entity, StatusEffectInstance effect) {
        if (entity instanceof LivingEntity livingEntity) addSetBonusStatusEffect(livingEntity, effect);
    }

    private static void addSetBonusStatusEffect(LivingEntity livingEntity, StatusEffectInstance effect) {
        boolean hasEffect = livingEntity.hasStatusEffect(effect.getEffectType());

        // Add the effect to the entity if it does not already have it
        if (!hasEffect) livingEntity.addStatusEffect(new StatusEffectInstance(effect.getEffectType(), effect.getDuration(), effect.getAmplifier(), false, false, true));

        // Add the effect again if it is below specified duration
        if (livingEntity.getActiveStatusEffects().containsKey(effect.getEffectType())) {
            if (livingEntity.getActiveStatusEffects().get(effect.getEffectType()).getDuration() < 221) {
                livingEntity.addStatusEffect(new StatusEffectInstance(effect.getEffectType(), effect.getDuration(), effect.getAmplifier(), false, false, true));
            }
        }
    }

}
