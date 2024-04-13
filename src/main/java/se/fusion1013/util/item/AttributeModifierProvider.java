package se.fusion1013.util.item;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;

public record AttributeModifierProvider(EntityAttribute attribute, EntityAttributeModifier modifier) {
}
