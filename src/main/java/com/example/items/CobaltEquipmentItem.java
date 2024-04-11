package com.example.items;

import com.example.util.item.AttributeModifierProvider;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Equipment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CobaltEquipmentItem extends Item implements Equipment {

    private final EquipmentSlot slotType;

    private final Formatting nameFormatting;

    private List<Text> m_tooltip = new ArrayList<>();
    private boolean m_isDamageable = false;
    private Map<EquipmentSlot, List<AttributeModifierProvider>> m_attributeModifiers = new HashMap<>();

    private List<StatusEffectInstance> wornEffects = new ArrayList<>();

    public CobaltEquipmentItem(Settings settings, EquipmentSlot slotType, Formatting nameFormatting) {
        super(settings);

        this.slotType = slotType;
        this.nameFormatting = nameFormatting;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if (slot != slotType.getEntitySlotId()) return;
        if (entity instanceof LivingEntity living) {
            for (StatusEffectInstance effect : wornEffects) {
                living.addStatusEffect(effect);
            }
        }
    }

    @Override
    public EquipmentSlot getSlotType() {
        return slotType;
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
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        Multimap<EntityAttribute, EntityAttributeModifier> map = super.getAttributeModifiers(stack, slot);

        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        for (AttributeModifierProvider attribute : m_attributeModifiers.getOrDefault(slot, new ArrayList<>())) {

            builder.put(attribute.attribute(), attribute.modifier());
        }
        builder.putAll(map);
        return builder.build();
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

    public static class Builder {

        // Generic
        private final Item.Settings settings;
        private final Formatting nameFormatting;
        private final EquipmentSlot slotType;

        private final List<Text> m_tooltip = new ArrayList<>();
        private boolean m_isDamageable = false;
        private final Map<EquipmentSlot, List<AttributeModifierProvider>> m_attributeModifiers = new HashMap<>();
        private List<StatusEffectInstance> wornEffects = new ArrayList<>();

        public Builder(Settings settings, EquipmentSlot slotType, Formatting nameFormatting) {
            this.settings = settings;
            this.nameFormatting = nameFormatting;
            this.slotType = slotType;
        }

        public CobaltEquipmentItem.Builder tooltip(Text text) {
            m_tooltip.add(text);
            return this;
        }

        public CobaltEquipmentItem.Builder attributeModifier(EntityAttribute attribute, EntityAttributeModifier modifier, EquipmentSlot... slots) {
            for (EquipmentSlot slot : slots) {
                List<AttributeModifierProvider> list = m_attributeModifiers.computeIfAbsent(slot, k -> new ArrayList<>());
                list.add(new AttributeModifierProvider(attribute, modifier));
            }
            return this;
        }

        public Builder wornEffect(StatusEffectInstance effect) {
            wornEffects.add(effect);
            return this;
        }

        public CobaltEquipmentItem build() {
            CobaltEquipmentItem equipment = new CobaltEquipmentItem(settings, slotType, nameFormatting);

            equipment.m_tooltip = m_tooltip;
            equipment.m_isDamageable = m_isDamageable;
            equipment.m_attributeModifiers = m_attributeModifiers;

            return equipment;
        }
    }
}
