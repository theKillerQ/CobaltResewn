package se.fusion1013.items.consumable;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.world.World;

import java.util.Random;

public class MysteryMedicineItem extends Item {

    private final static StatusEffect[] POSITIVE_POOL = new StatusEffect[] {
            StatusEffects.REGENERATION,
            StatusEffects.STRENGTH,
            StatusEffects.ABSORPTION,
            StatusEffects.HASTE
    };
    private final static StatusEffect[] NEGATIVE_POOL = new StatusEffect[] {
            StatusEffects.BLINDNESS,
            StatusEffects.DARKNESS,
            StatusEffects.HUNGER,
            StatusEffects.MINING_FATIGUE,
            StatusEffects.SLOWNESS,
    };

    public MysteryMedicineItem(Settings settings) {

        super(settings.food(new FoodComponent.Builder()
                .hunger(4)
                .saturationModifier(.5f)
                .build())
        );
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        var rand = new Random();
        // addEffect(user, POSITIVE_POOL[rand.nextInt(0, POSITIVE_POOL.length)], 180*20, 0);
        // addEffect(user, NEGATIVE_POOL[rand.nextInt(0, NEGATIVE_POOL.length)], 120*20, 1);

        return super.finishUsing(stack, world, user);
    }

    private static void addEffect(LivingEntity user, StatusEffect effect, int randomDuration, int randomAmplifier) {
        var rand = new Random();
        user.addStatusEffect(new StatusEffectInstance(effect, rand.nextInt(200, randomDuration+1), rand.nextInt(0, randomAmplifier+1)));
    }
}
