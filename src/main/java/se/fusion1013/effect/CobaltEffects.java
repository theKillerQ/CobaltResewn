package se.fusion1013.effect;

import net.minecraft.block.SculkSpreadable;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import se.fusion1013.Main;

public class CobaltEffects {

    public static final StatusEffect CROWD_CONTROL = register("crowd_control", new CrowdControlEffect());
    public static final StatusEffect CORRUPTION_SPREAD = register("corruption_spread", new CorruptionSpreadEffect());

    public static StatusEffect register(String id, StatusEffect effect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(Main.MOD_NAMESPACE, id), effect);
    }

    public static void registerAll() {}

}
