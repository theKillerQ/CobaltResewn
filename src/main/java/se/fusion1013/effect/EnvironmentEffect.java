package se.fusion1013.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

/**
 * Allows for various environmental effects to be applied to the player, such as different fog distances.
 */
public class EnvironmentEffect extends StatusEffect {

    public EnvironmentEffect() {
        super(StatusEffectCategory.NEUTRAL, 0x818da1);
    }
}
