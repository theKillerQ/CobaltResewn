package se.fusion1013.util.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.Formatting;
import se.fusion1013.items.CobaltEquipmentItem;
import se.fusion1013.items.ICobaltItem;
import se.fusion1013.items.armor.ArmorSetBonus;
import se.fusion1013.items.armor.CobaltArmorItem;
import se.fusion1013.items.materials.CobaltArmorMaterial;

public class ArmorUtil {

    public static CobaltEquipmentItem getEquipmentItem(CobaltArmorMaterial material, ArmorItem.Type armorType, Formatting nameFormatting, int stackSize) {
        var equipmentSlot = switch (armorType) {
            case HELMET -> EquipmentSlot.HEAD;
            case CHESTPLATE -> EquipmentSlot.CHEST;
            case LEGGINGS -> EquipmentSlot.LEGS;
            case BOOTS -> EquipmentSlot.FEET;
        };

        var builder = new CobaltEquipmentItem.Builder(material, new FabricItemSettings().maxCount(stackSize), equipmentSlot, nameFormatting);

        // Add armor and toughness attributes to item
        builder.attributeModifier(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier("cobalt." + material.getName() + ".armor", material.getProtection(armorType), EntityAttributeModifier.Operation.ADDITION));
        builder.attributeModifier(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier("cobalt." + material.getName() + ".toughness", material.getProtection(armorType), EntityAttributeModifier.Operation.ADDITION));

        return builder.build();
    }

    public static CobaltArmorItem getArmorItem(CobaltArmorMaterial material, ArmorItem.Type armorType, Formatting nameFormatting) {
        var builder = new CobaltArmorItem.Builder(material, armorType, new FabricItemSettings(), nameFormatting);
        return builder.build();
    }

    public static ICobaltItem getArmorItem(CobaltArmorMaterial material, boolean asEquipment, ArmorItem.Type armorType, Formatting nameFormatting) {
        if (asEquipment) return ArmorUtil.getEquipmentItem(material, armorType, nameFormatting, 1);
        else return ArmorUtil.getArmorItem(material, armorType, nameFormatting);
    }

}
