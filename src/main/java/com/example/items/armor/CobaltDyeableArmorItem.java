package com.example.items.armor;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CobaltDyeableArmorItem extends DyeableArmorItem {

    private final Formatting nameFormatting;

    public CobaltDyeableArmorItem(ArmorMaterial material, Type type, Settings settings, Formatting nameFormatting) {
        super(material, type, settings);

        this.nameFormatting = nameFormatting;
    }

    @Override
    public Text getName(ItemStack stack) {
        Text text = super.getName(stack);
        return text.copy().formatted(nameFormatting);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable(getTranslationKey(stack) + ".tooltip").formatted(Formatting.DARK_GRAY));
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public void postProcessNbt(NbtCompound nbt) {
        super.postProcessNbt(nbt);
        nbt.putBoolean("Unbreakable", true);
    }


}
