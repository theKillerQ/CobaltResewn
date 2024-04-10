package com.example.util.item;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;

public record AttributeModifierProvider(EntityAttribute attribute, EntityAttributeModifier modifier) {
}
