package com.example.items;

import com.example.items.armor.CobaltArmorItem;
import com.example.items.armor.CobaltArmorSet;
import com.example.items.consumable.CobaltHealingItem;
import com.example.items.consumable.MysteryMedicineItem;
import com.example.items.crossbow.CobaltCrossbowItem;
import com.example.items.materials.CobaltArmorMaterial;
import com.example.items.sword.InfectedSwordItem;
import com.example.items.sword.SampleDrillItem;
import com.example.items.tools.BasicDrillItem;
import com.example.items.tools.CobaltAxeItem;
import com.example.items.sword.CobaltSwordItem;
import com.example.items.tools.CobaltPickaxeItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import static com.example.items.materials.CobaltArmorMaterials.*;

public class CustomItemRegistry {

    public static final String MOD_NAMESPACE = "cobalt";


    public static final Item ICON_ITEM = new Item(new Item.Settings());


    // --- ITEM GROUPS
    private static final RegistryKey<ItemGroup> COBALT_GROUP_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier("cobalt", "item_group_all"));
    private static final ItemGroup COBALT_GROUP = register(COBALT_GROUP_KEY, FabricItemGroup.builder()
            .icon(() -> new ItemStack(ICON_ITEM))
            .displayName(Text.translatable("itemGroup.cobalt.items"))
            .build());


    // --- ITEMS

    // Adventure Set
    public static final Item ADVENTURE_SWORD = register("adventure_sword", new CobaltSwordItem(ToolMaterials.STONE, -2+4, -4+1.6f, new FabricItemSettings(), Formatting.DARK_GREEN));
    public static final CobaltArmorSet ADVENTURE_ARMOR_SET = registerSet("adventure", ADVENTURE, Formatting.DARK_GREEN);




    // Diving Set
    public static final Item DIVING_HELMET = register("diving_helmet", new CobaltEquipmentItem.Builder(new FabricItemSettings(), EquipmentSlot.HEAD, Formatting.GOLD)
            .attributeModifier(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier("cobalt.diving_helmet.armor", 3, EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.HEAD)
            .attributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier("cobalt.diving_helmet.move_speed", -.1, EntityAttributeModifier.Operation.MULTIPLY_TOTAL), EquipmentSlot.HEAD)
            .build());
    public static final CobaltArmorSet DIVING_ARMOR_SET = registerSet("diving", DIVE, Formatting.GOLD, true);
    public static final Item HARPOON_GUN = register("harpoon_gun", new CobaltCrossbowItem(new FabricItemSettings()));





    // Lumberjack Set
    public static final CobaltArmorSet LUMBERJACK_ARMOR_SET = registerSet("lumberjack", LUMBERJACK, Formatting.DARK_GREEN);
    public static final Item LUMBERJACK_AXE = register("lumberjack_axe", new CobaltAxeItem.Builder(ToolMaterials.STONE, -2+7, -4+0.8f, new FabricItemSettings(), Formatting.DARK_GREEN)
            .attributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier("cobalt.lumberjack.axe", -.02, EntityAttributeModifier.Operation.MULTIPLY_TOTAL), EquipmentSlot.MAINHAND)
            .build());





    // Infected Adventure Set
    public static final Item INFECTED_ADVENTURE_SWORD = register("infected_adventure_sword", new InfectedSwordItem(ToolMaterials.STONE, -2+5, -4+1.6f, new FabricItemSettings(), Formatting.DARK_PURPLE, 4, 60*20, ((world, user, hand) -> {
        user.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, SoundCategory.PLAYERS, 1, 1);
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 20*60, 0));
    })));





    // Guard Set
    public static final CobaltArmorSet GUARD_ARMOR_SET = registerSet("guard", GUARD, Formatting.GRAY);





    // Hunter Set
    public static final CobaltArmorSet HUNTER_ARMOR_SET = registerSet("hunter", HUNTER, Formatting.GRAY);
    public static final Item HUNTER_CROSSBOW = register("hunter_crossbow", new CobaltCrossbowItem(new FabricItemSettings()));
    public static final Item HUNTER_GLOVE = register("hunter_gloves", new CobaltTrinketItem(new FabricItemSettings(), (modifiers, stack, slot, entity, uuid) -> {
        modifiers.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier("cobalt.hunter_glove.armor", 1, EntityAttributeModifier.Operation.ADDITION));
        return modifiers;
    }, Formatting.GRAY));





    // Mechanic Set
    public static final CobaltArmorSet MECHANIC_ARMOR_SET = registerSet("mechanic", MECHANIC, Formatting.DARK_GRAY);
    public static final CobaltArmorSet REINFORCED_MECHANIC_ARMOR_SET = registerSet("reinforced_mechanic", REINFORCED_MECHANIC, Formatting.DARK_GRAY);
    public static final Item MECHANICAL_HAND = register("mechanical_hand", new CobaltTrinketItem(new Item.Settings(), (modifiers, stack, slot, entity, uuid) -> {
        modifiers.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(uuid, "cobalt.mechanical_hand.attack_damage", .1f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
        return modifiers;
    }, Formatting.DARK_GRAY));
    public static final Item HEAVY_WRENCH = register("heavy_wrench", new CobaltSwordItem.Builder(ToolMaterials.STONE, -2+9, -4+0.9f, new FabricItemSettings(), Formatting.DARK_GRAY)
            .attributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier("cobalt.heavy_wrench.speed", -.1, EntityAttributeModifier.Operation.MULTIPLY_TOTAL), EquipmentSlot.MAINHAND)
            .build());
    public static final Item MECHANIC_GLOVES = register("mechanic_gloves", new CobaltTrinketItem(new FabricItemSettings(), (modifiers, stack, slot, entity, uuid) -> {
        modifiers.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier("cobalt.mechanic_gloves.move_speed", -0.05, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
        modifiers.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier("cobalt.mechanic_gloves.damage", 0.05, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
        modifiers.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier("cobalt.mechanic_gloves.armor", 2, EntityAttributeModifier.Operation.ADDITION));
        modifiers.put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier("cobalt.mechanic_gloves.toughness", 1, EntityAttributeModifier.Operation.ADDITION));
        return modifiers;
    }, Formatting.DARK_GRAY));





    // Miner Set
    public static final Item MINER_PICKAXE = register("miner_pickaxe", new CobaltPickaxeItem.Builder(ToolMaterials.STONE, -2+9, -4+0.8f, new FabricItemSettings(), Formatting.DARK_GRAY)
            .attributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier("cobalt.miner_pickaxe.speed", -.05, EntityAttributeModifier.Operation.MULTIPLY_TOTAL), EquipmentSlot.MAINHAND)
            .build());
    public static final Item BASIC_DRILL = register("basic_drill", new BasicDrillItem(ToolMaterials.STONE, -2+5, -4+1.4f, new Item.Settings()));
    public static final Item MINER_HELMET = register("miner_helmet", new CobaltEquipmentItem.Builder(new FabricItemSettings(), EquipmentSlot.HEAD, Formatting.DARK_GRAY)
            .attributeModifier(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier("cobalt.miner_helmet.armor", 1.0, EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.HEAD)
            .build());
    public static final CobaltArmorSet MINER_ARMOR_SET = registerSet("miner", MINER, Formatting.DARK_GRAY, true);





    // Prospector Set
    public static final CobaltArmorSet PROSPECTOR_ARMOR_SET = registerSet("prospector", PROSPECTOR, Formatting.GOLD, true);
    public static final Item PROSPECTOR_HELMET = register("prospector_helmet", new CobaltEquipmentItem.Builder(new FabricItemSettings(), EquipmentSlot.HEAD, Formatting.GOLD)
            .attributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier("cobalt.prospector_helmet.move_speed", 0.01, EntityAttributeModifier.Operation.MULTIPLY_TOTAL), EquipmentSlot.HEAD)
            .attributeModifier(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier("cobalt.prospector_helmet.armor", 2, EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.HEAD)
            .build());
    public static final Item PROSPECTOR_PICKAXE = register("prospector_pickaxe", new CobaltPickaxeItem.Builder(ToolMaterials.STONE, -2+4, -4+1.8f, new FabricItemSettings(), Formatting.GOLD)
            .attributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier("cobalt.prospector_pickaxe.speed", 0.1f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL), EquipmentSlot.MAINHAND)
            .build());





    // Tinker Set
    public static final CobaltArmorSet TINKER_ARMOR_SET = registerSet("tinker", TINKER, Formatting.GOLD);
    public static final CobaltArmorSet REINFORCED_TINKER_ARMOR_SET = registerSet("reinforced_tinker", REINFORCED_TINKER, Formatting.GOLD);
    public static final Item GEARSTRAP = register("gearstrap", new CobaltTrinketItem(new Item.Settings(), (modifiers, stack, slot, entity, uuid) -> {
        modifiers.put(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier(uuid, "cobalt:knockback_resistance", .05f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
        modifiers.put(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier(uuid, "cobalt:health", 10, EntityAttributeModifier.Operation.ADDITION));
        return modifiers;
    }, Formatting.GOLD));





    // Mutant Researcher
    // TODO: Add special ability
    // public static final Item SAMPLE_DRILL = register("sample_drill", new SampleDrillItem(ToolMaterials.STONE, -2+2, -4+2, new FabricItemSettings(), Formatting.LIGHT_PURPLE));





    // Healing
    public static final Item PAINKILLERS = register("painkillers", new CobaltHealingItem(new FabricItemSettings().maxCount(4), Formatting.WHITE, 5));
    public static final Item BANDAGE = register("bandage", new CobaltHealingItem(new FabricItemSettings().maxCount(4), Formatting.WHITE, 10));
    public static final Item FIRST_AID_KIT = register("first_aid_kit", new CobaltHealingItem(new FabricItemSettings().maxCount(2), Formatting.WHITE, 20));
    public static final Item PNEUMATIC_NEEDLE = register("pneumatic_needle", new CobaltHealingItem(new FabricItemSettings().maxCount(1), Formatting.WHITE, 40));




    // Gears
    public static final Item RUINED_GEAR = register("ruined_gear", new CobaltItem(new FabricItemSettings(), Formatting.GRAY));
    public static final Item TARNISHED_GEAR = register("tarnished_gear", new CobaltItem(new FabricItemSettings(), Formatting.GRAY));
    public static final Item AVERAGE_GEAR = register("average_gear", new CobaltItem(new FabricItemSettings(), Formatting.GRAY));
    public static final Item REMARKABLE_GEAR = register("remarkable_gear", new CobaltItem(new FabricItemSettings(), Formatting.GRAY));




    // Medicine
    // public static final Item MYSTERY_MEDICINE = register("mystery_medicine", new MysteryMedicineItem(new FabricItemSettings()));





    // Power Cells
    public static final Item BATTERY = register("battery", new CobaltItem(new FabricItemSettings(), Formatting.DARK_AQUA));





    public static void register() {
        register("icon_item", ICON_ITEM);
    }

    private static Item register(String itemId, Item item) {
        Registry.register(Registries.ITEM, new Identifier(MOD_NAMESPACE, itemId), item);
        ItemGroupEvents.modifyEntriesEvent(COBALT_GROUP_KEY).register(content -> {
            content.add(item);
        });
        return item;
    }

    private static ArmorItem register(String itemId, ArmorItem armorItem) {
        return (ArmorItem) register(itemId, (Item) armorItem);
    }

    private static CobaltArmorSet registerSet(String setId, CobaltArmorMaterial material, Formatting nameFormatting) {
        return registerSet(setId, material, nameFormatting, false);
    }
    private static CobaltArmorSet registerSet(String setId, CobaltArmorMaterial material, Formatting nameFormatting, boolean ignoreHelmet) {
        ArmorItem helmet = null;
        if (!ignoreHelmet) helmet = register(setId + "_helmet", new CobaltArmorItem(material, ArmorItem.Type.HELMET, new FabricItemSettings(), nameFormatting));
        var chestplate = register(setId + "_chestplate", new CobaltArmorItem(material, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), nameFormatting));
        var leggings = register(setId + "_leggings", new CobaltArmorItem(material, ArmorItem.Type.LEGGINGS, new FabricItemSettings(), nameFormatting));
        var boots = register(setId + "_boots", new CobaltArmorItem(material, ArmorItem.Type.BOOTS, new FabricItemSettings(), nameFormatting));
        return new CobaltArmorSet(helmet, chestplate, leggings, boots);
    }

    private static ItemGroup register(RegistryKey<ItemGroup> key, ItemGroup group) {
        Registry.register(Registries.ITEM_GROUP, key, group);
        return group;
    }
}
