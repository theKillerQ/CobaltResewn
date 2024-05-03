package se.fusion1013.items.consumable;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import se.fusion1013.items.CobaltItemConfiguration;

public class LiquidCourageItem extends CobaltDrinkItem {

    public LiquidCourageItem(CobaltItemConfiguration configuration, Settings settings) {
        super(configuration, settings.food(new FoodComponent.Builder()
                .hunger(0)
                .saturationModifier(0f)
                .alwaysEdible()
                .build())
        );
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (world.isClient()) return super.finishUsing(stack, world, user);

        user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 120*20, 0));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 30*20, 0));

        return super.finishUsing(stack, world, user);
    }
}
