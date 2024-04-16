package se.fusion1013.items;

import io.wispforest.lavender.book.LavenderBookItem;
import se.fusion1013.Main;
import se.fusion1013.entity.ExplosiveArrowEntity;
import se.fusion1013.entity.LightningArrowEntity;
import se.fusion1013.items.armor.ArmorSetBonus;
import se.fusion1013.items.armor.CobaltArmorItem;
import se.fusion1013.items.armor.CobaltArmorSet;
import se.fusion1013.items.consumable.CobaltHealingItem;
import se.fusion1013.items.consumable.MysteryMedicineItem;
import se.fusion1013.items.crossbow.CobaltCrossbowItem;
import se.fusion1013.items.materials.CobaltArmorMaterial;
import se.fusion1013.items.sword.InfectedSwordItem;
import se.fusion1013.items.sword.SampleDrillItem;
import se.fusion1013.items.tools.BasicDrillItem;
import se.fusion1013.items.tools.CobaltAxeItem;
import se.fusion1013.items.sword.CobaltSwordItem;
import se.fusion1013.items.tools.CobaltPickaxeItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
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
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import se.fusion1013.items.materials.CobaltArmorMaterials;
import se.fusion1013.items.trinket.CobaltTrinketItem;
import se.fusion1013.items.trinket.MechanicSpectaclesTrinket;

import static se.fusion1013.Main.MOD_NAMESPACE;

public class CustomItemRegistry {


    public static final Item ICON_ITEM = new Item(new Item.Settings());
    public static final LavenderBookItem WF_INSTRUCTION_MANUAL = LavenderBookItem.registerForBook(new Identifier("cobalt", "wf_instruction_manual"), new FabricItemSettings());


    // --- ITEM GROUPS
    private static final RegistryKey<ItemGroup> COBALT_GROUP_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier("cobalt", "item_group_all"));
    public static final ItemGroup COBALT_GROUP = register(COBALT_GROUP_KEY, FabricItemGroup.builder()
            .icon(() -> new ItemStack(ICON_ITEM))
            .displayName(Text.translatable("itemGroup.cobalt.items"))
            .entries((displayContext, entries) -> {
                entries.add(WF_INSTRUCTION_MANUAL);
            })
            .build());



    // --- ITEMS

    // Adventure Set
    public static final Item ADVENTURE_SWORD = register("adventure_sword", new CobaltSwordItem(ToolMaterials.STONE, -2+4, -4+1.6f, new FabricItemSettings(), Formatting.DARK_GREEN));
    public static final CobaltArmorSet ADVENTURE_ARMOR_SET = registerSet("adventure", new CobaltArmorSet.Builder(CobaltArmorMaterials.ADVENTURE, Formatting.DARK_GREEN)
            .withHelmet(false).withChestplate(false).withLeggings(false).withBoots(false).build());





    // Diving Set
    /*
    public static final Item DIVING_HELMET = register("diving_helmet", new CobaltEquipmentItem.Builder(CobaltArmorMaterials.DIVE, new FabricItemSettings(), EquipmentSlot.HEAD, Formatting.GOLD)
            .attributeModifier(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier("cobalt.diving_helmet.armor", 3, EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.HEAD)
            .attributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier("cobalt.diving_helmet.move_speed", -.1, EntityAttributeModifier.Operation.MULTIPLY_TOTAL), EquipmentSlot.HEAD)
            .build());
     */
    public static final CobaltArmorSet DIVING_ARMOR_SET = registerSet("diving", new CobaltArmorSet.Builder(CobaltArmorMaterials.DIVE, Formatting.GOLD)
                    .withHelmet(true).withChestplate(false).withLeggings(false).withBoots(false).withSetBonus(new ArmorSetBonus(new String[] { "item.cobalt.diving_set_bonus.tooltip" }, (stack, world, entity, slot, selected) -> {
                        CobaltArmorItem.addSetBonusStatusEffect(entity, new StatusEffectInstance(StatusEffects.WATER_BREATHING, 20, 0));
                    })).build());
    public static final Item HARPOON_GUN = register("harpoon_gun", new CobaltCrossbowItem(new FabricItemSettings()));





    // Lumberjack Set
    public static final CobaltArmorSet LUMBERJACK_ARMOR_SET = registerSet("lumberjack", new CobaltArmorSet.Builder(CobaltArmorMaterials.LUMBERJACK, Formatting.DARK_GREEN)
            .withHelmet(false).withChestplate(false).withLeggings(false).withBoots(false).build());
    public static final Item LUMBERJACK_AXE = register("lumberjack_axe", new CobaltAxeItem.Builder(ToolMaterials.STONE, -2+7, -4+0.8f, new FabricItemSettings(), Formatting.DARK_GREEN)
            .attributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier("cobalt.lumberjack.axe", -.02, EntityAttributeModifier.Operation.MULTIPLY_TOTAL), EquipmentSlot.MAINHAND)
            .build());





    // Infected Adventure Set
    public static final Item INFECTED_ADVENTURE_SWORD = register("infected_adventure_sword", new InfectedSwordItem(ToolMaterials.STONE, -2+5, -4+1.6f, new FabricItemSettings(), Formatting.DARK_PURPLE, 4, 60*20, ((world, user, hand) -> {
        user.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, SoundCategory.PLAYERS, 1, 1);
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 20*60, 0));
    })));





    // Guard Set
    public static final CobaltArmorSet GUARD_ARMOR_SET = registerSet("guard", new CobaltArmorSet.Builder(CobaltArmorMaterials.GUARD, Formatting.GRAY)
            .withHelmet(false).withChestplate(false).withLeggings(false).withBoots(false).build());





    // Hunter Set
    public static final CobaltArmorSet HUNTER_ARMOR_SET = registerSet("hunter", new CobaltArmorSet.Builder(CobaltArmorMaterials.HUNTER, Formatting.GRAY)
            .withHelmet(false).withChestplate(false).withLeggings(false).withBoots(false).build());
    public static final Item HUNTER_CROSSBOW = register("hunter_crossbow", new CobaltCrossbowItem(new FabricItemSettings()));
    public static final Item HUNTER_GLOVE = register("hunter_gloves", new CobaltTrinketItem(new FabricItemSettings(), (modifiers, stack, slot, entity, uuid) -> {
        modifiers.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier("cobalt.hunter_glove.armor", 1, EntityAttributeModifier.Operation.ADDITION));
        return modifiers;
    }, Formatting.GRAY));





    // Mechanic Set
    public static final CobaltArmorSet MECHANIC_ARMOR_SET = registerSet("mechanic", new CobaltArmorSet.Builder(CobaltArmorMaterials.MECHANIC, Formatting.DARK_GRAY)
            .withHelmet(false).withChestplate(false).withLeggings(false).withBoots(false).build());
    public static final CobaltArmorSet REINFORCED_MECHANIC_ARMOR_SET = registerSet("reinforced_mechanic", new CobaltArmorSet.Builder(CobaltArmorMaterials.REINFORCED_MECHANIC, Formatting.DARK_GRAY)
            .withHelmet(false).withChestplate(false).withLeggings(false).withBoots(false).build());
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
    public static final Item MECHANIC_SPECTACLES = register("mechanic_spectacles", new MechanicSpectaclesTrinket(new FabricItemSettings(), (modifiers, stack, slot, entity, uuid) -> modifiers, Formatting.DARK_GRAY));





    // Miner Set
    public static final Item MINER_PICKAXE = register("miner_pickaxe", new CobaltPickaxeItem.Builder(ToolMaterials.STONE, -2+9, -4+0.8f, new FabricItemSettings(), Formatting.DARK_GRAY)
            .attributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier("cobalt.miner_pickaxe.speed", -.05, EntityAttributeModifier.Operation.MULTIPLY_TOTAL), EquipmentSlot.MAINHAND)
            .build());
    public static final Item BASIC_DRILL = register("basic_drill", new BasicDrillItem(ToolMaterials.STONE, -2+5, -4+1.4f, new Item.Settings()));
    public static final CobaltArmorSet MINER_ARMOR_SET = registerSet("miner", new CobaltArmorSet.Builder(CobaltArmorMaterials.MINER, Formatting.DARK_GRAY)
            .withHelmet(true).withChestplate(false).withLeggings(false).withBoots(false).build());





    // Prospector Set
    public static final CobaltArmorSet PROSPECTOR_ARMOR_SET = registerSet("prospector", new CobaltArmorSet.Builder(CobaltArmorMaterials.PROSPECTOR, Formatting.GOLD)
            .withHelmet(true).withChestplate(false).withLeggings(false).withBoots(false).build());
    public static final Item PROSPECTOR_PICKAXE = register("prospector_pickaxe", new CobaltPickaxeItem.Builder(ToolMaterials.STONE, -2+4, -4+1.8f, new FabricItemSettings(), Formatting.GOLD)
            .attributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier("cobalt.prospector_pickaxe.speed", 0.1f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL), EquipmentSlot.MAINHAND)
            .build());





    // Tinker Set
    public static final CobaltArmorSet TINKER_ARMOR_SET = registerSet("tinker", new CobaltArmorSet.Builder(CobaltArmorMaterials.TINKER, Formatting.GOLD)
            .withHelmet(false).withChestplate(false).withLeggings(false).withBoots(false).build());
    public static final CobaltArmorSet REINFORCED_TINKER_ARMOR_SET = registerSet("reinforced_tinker", new CobaltArmorSet.Builder(CobaltArmorMaterials.REINFORCED_TINKER, Formatting.GOLD)
            .withHelmet(false).withChestplate(false).withLeggings(false).withBoots(false).build());
    public static final Item GEARSTRAP = register("gearstrap", new CobaltTrinketItem(new Item.Settings(), (modifiers, stack, slot, entity, uuid) -> {
        modifiers.put(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier(uuid, "cobalt:knockback_resistance", .05f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
        modifiers.put(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier(uuid, "cobalt:health", 10, EntityAttributeModifier.Operation.ADDITION));
        return modifiers;
    }, Formatting.GOLD));





    // Mutant Researcher
    // TODO: Add special ability
    public static final Item SAMPLE_DRILL = register("sample_drill", new SampleDrillItem(ToolMaterials.STONE, -2+2, -4+2, new FabricItemSettings(), Formatting.LIGHT_PURPLE));





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
    public static final Item MYSTERY_MEDICINE = register("mystery_medicine", new MysteryMedicineItem(new FabricItemSettings()));





    // Power Cells
    public static final Item BATTERY = register("battery", new CobaltItem(new FabricItemSettings().maxCount(24), Formatting.DARK_AQUA));




    // Arrows
    public static final Item LIGHTNING_ARROW = register("lightning_arrow", new CobaltArrowItem(new FabricItemSettings(), Formatting.DARK_AQUA, LightningArrowEntity::new));
    public static final Item EXPLOSIVE_ARROW = register("explosive_arrow", new CobaltArrowItem(new FabricItemSettings(), Formatting.DARK_AQUA, ExplosiveArrowEntity::new));





    public static final Item PRESSURE_GAUGE = register("pressure_gauge", new CobaltItem(new FabricItemSettings(), Formatting.GOLD));





    public static void register() {
        register("icon_item", ICON_ITEM);

        registerDispenserBlockBehaviour(LIGHTNING_ARROW);
        registerDispenserBlockBehaviour(EXPLOSIVE_ARROW);
    }

    private static Item register(String itemId, Item item) {
        Registry.register(Registries.ITEM, new Identifier(MOD_NAMESPACE, itemId), item);
        ItemGroupEvents.modifyEntriesEvent(COBALT_GROUP_KEY).register(content -> {
            content.add(item);
        });
        return item;
    }

    private static CobaltArmorSet registerSet(String setId, CobaltArmorSet set) {
        register(setId + "_helmet", set.helmet.getItem());
        register(setId + "_chestplate", set.chestplate.getItem());
        register(setId + "_leggings", set.leggings.getItem());
        register(setId + "_boots", set.boots.getItem());
        return set;
    }

    private static ItemGroup register(RegistryKey<ItemGroup> key, ItemGroup group) {
        Registry.register(Registries.ITEM_GROUP, key, group);
        return group;
    }

    private static void registerDispenserBlockBehaviour(Item item) {
        DispenserBlock.registerBehavior(item, new ProjectileDispenserBehavior() {
            @Override
            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                if (item instanceof CobaltArrowItem cobaltArrowItem) {
                    var arrowEntity = cobaltArrowItem.getEntityFactory().create(position.getX(), position.getY(), position.getZ(), world, stack);
                    arrowEntity.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
                    return arrowEntity;
                }

                return null;
            }
        });
    }
}
