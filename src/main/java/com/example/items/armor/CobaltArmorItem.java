package com.example.items.armor;

import com.example.items.materials.CobaltArmorMaterial;
import com.example.util.item.AttributeModifierProvider;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ArmorItem;
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

public class CobaltArmorItem extends ArmorItem {

    private final Formatting nameFormatting;
    private final CobaltArmorMaterial cobaltMaterial;

    private List<Text> tooltip = new ArrayList<>();
    private Map<EquipmentSlot, List<AttributeModifierProvider>> attributeModifiers = new HashMap<>();

    public CobaltArmorItem(CobaltArmorMaterial material, ArmorItem.Type type, Item.Settings settings, Formatting nameFormatting) {
        super(material, type, settings);

        this.nameFormatting = nameFormatting;
        cobaltMaterial = material;
    }

    @Override
    public Text getName(ItemStack stack) {
        Text text = super.getName(stack);
        return text.copy().formatted(nameFormatting);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable(getTranslationKey(stack) + ".tooltip").formatted(Formatting.DARK_GRAY));
        tooltip.addAll(this.tooltip);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        Multimap<EntityAttribute, EntityAttributeModifier> map = super.getAttributeModifiers(stack, slot);

        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        for (AttributeModifierProvider attribute : attributeModifiers.getOrDefault(slot, new ArrayList<>())) {
            builder.put(attribute.attribute(), attribute.modifier());
        }

        if (slot == type.getEquipmentSlot()) {
            for (AttributeModifierProvider attribute : cobaltMaterial.getAttributeModifiers(slot)) {
                builder.put(attribute.attribute(), attribute.modifier());
            }
        }
        builder.putAll(map);
        return builder.build();
    }

    @Override
    public void postProcessNbt(NbtCompound nbt) {
        super.postProcessNbt(nbt);
        nbt.putBoolean("Unbreakable", true);
    }

    public static class Builder {

        // Generic
        private final CobaltArmorMaterial material;
        private final ArmorItem.Type type;
        private final Item.Settings settings;
        private final Formatting nameFormatting;

        private final List<Text> m_tooltip = new ArrayList<>();
        private boolean m_isDamageable = false;
        private Map<EquipmentSlot, List<AttributeModifierProvider>> m_attributeModifiers = new HashMap<>();

        public Builder(CobaltArmorMaterial material, Type type, Settings settings, Formatting nameFormatting) {
            this.material = material;
            this.type = type;
            this.settings = settings;
            this.nameFormatting = nameFormatting;
        }

        public Builder tooltip(Text text) {
            m_tooltip.add(text);
            return this;
        }

        public Builder attributeModifier(EntityAttribute attribute, EntityAttributeModifier modifier, EquipmentSlot... slots) {
            for (EquipmentSlot slot : slots) {
                List<AttributeModifierProvider> list = m_attributeModifiers.computeIfAbsent(slot, k -> new ArrayList<>());
                list.add(new AttributeModifierProvider(attribute, modifier));
            }
            return this;
        }

        public CobaltArmorItem build() {
            CobaltArmorItem armor = new CobaltArmorItem(material, type, settings, nameFormatting);

            armor.tooltip = m_tooltip;
            armor.attributeModifiers = m_attributeModifiers;

            return armor;
        }
    }
}
