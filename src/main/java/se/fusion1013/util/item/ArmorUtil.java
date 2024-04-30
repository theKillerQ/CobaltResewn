package se.fusion1013.util.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import se.fusion1013.Main;
import se.fusion1013.items.CobaltEquipmentItem;
import se.fusion1013.items.CobaltItemConfiguration;
import se.fusion1013.items.ICobaltArmorItem;
import se.fusion1013.items.armor.CobaltArmorItem;
import se.fusion1013.items.materials.CobaltArmorMaterial;

import static net.minecraft.item.ArmorItem.Type.*;

public class ArmorUtil {

    /***
     * Applies the relevant armor attributes to the map, from the supplied material.
     *
     * @param map the attribute map builder.
     * @param material the material of the item.
     * @param armorType the armor type of the item.
     * @param slot the inventory slot the item goes into.
     * @return attribute modifier map builder.
     */
    public static Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(
            Multimap<EntityAttribute, EntityAttributeModifier> map,
            CobaltArmorMaterial material,
            ArmorItem.Type armorType,
            EquipmentSlot slot
    ) {
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();

        if (slot == armorType.getEquipmentSlot()) {
            for (AttributeModifierProvider attribute : material.getAttributeModifiers(slot)) {
                builder.put(attribute.attribute(), attribute.modifier());
            }
        }
        builder.putAll(map);
        return builder.build();
    }

    public static ArmorItem.Type toArmorType(EquipmentSlot slot) {
        switch (slot) {
            case FEET -> {
                return BOOTS;
            }
            case LEGS -> {
                return LEGGINGS;
            }
            case CHEST -> {
                return CHESTPLATE;
            }
            case HEAD -> {
                return HELMET;
            }
        }
        return null;
    }

    public static CobaltEquipmentItem getEquipmentItem(CobaltArmorMaterial material, ArmorItem.Type armorType, CobaltItemConfiguration configuration, int stackSize) {
        var equipmentSlot = switch (armorType) {
            case HELMET -> EquipmentSlot.HEAD;
            case CHESTPLATE -> EquipmentSlot.CHEST;
            case LEGGINGS -> EquipmentSlot.LEGS;
            case BOOTS -> EquipmentSlot.FEET;
        };

        // Create copy of config
        var config = configuration.clone();

        // Add armor and toughness attributes to item
        config.attributeModifier(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier("cobalt." + material.getName() + ".armor", material.getProtection(armorType), EntityAttributeModifier.Operation.ADDITION), equipmentSlot);
        config.attributeModifier(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier("cobalt." + material.getName() + ".toughness", material.getProtection(armorType), EntityAttributeModifier.Operation.ADDITION), equipmentSlot);

        return new CobaltEquipmentItem(material, config, new FabricItemSettings(), equipmentSlot);
    }

    public static CobaltArmorItem getArmorItem(CobaltArmorMaterial material, ArmorItem.Type armorType, CobaltItemConfiguration configuration) {
        var builder = new CobaltArmorItem.Builder(material, armorType, configuration, new FabricItemSettings());
        return builder.build();
    }

    public static ICobaltArmorItem getArmorItem(CobaltArmorMaterial material, boolean asEquipment, ArmorItem.Type armorType, CobaltItemConfiguration configuration) {
        if (asEquipment) return ArmorUtil.getEquipmentItem(material, armorType, configuration, 1);
        else return ArmorUtil.getArmorItem(material, armorType, configuration);
    }

    public static boolean isWearingArmorSet(PlayerEntity player, ArmorMaterial material) {
        ArmorMaterial boots = getArmorMaterial(player.getInventory().getArmorStack(0).getItem());
        ArmorMaterial leggings = getArmorMaterial(player.getInventory().getArmorStack(1).getItem());
        ArmorMaterial chestplate = getArmorMaterial(player.getInventory().getArmorStack(2).getItem());
        ArmorMaterial helmet = getArmorMaterial(player.getInventory().getArmorStack(3).getItem());

        return  boots == material &&
                leggings == material &&
                chestplate == material &&
                helmet == material;
    }

    public static ArmorMaterial getArmorMaterial(Item item) {
        if (item instanceof ArmorItem armorItem) return armorItem.getMaterial();
        if (item instanceof CobaltEquipmentItem equipmentItem) return equipmentItem.material;
        return null;
    }

}
