package se.fusion1013.items.armor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorMaterial;
import se.fusion1013.items.CobaltEquipmentItem;
import se.fusion1013.items.ICobaltItem;
import se.fusion1013.items.materials.CobaltArmorMaterial;
import se.fusion1013.util.item.AttributeModifierProvider;
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

public class CobaltArmorItem extends ArmorItem implements ICobaltItem {

    private final Formatting nameFormatting;
    private final CobaltArmorMaterial cobaltMaterial;

    private List<Text> tooltip = new ArrayList<>();
    private Map<EquipmentSlot, List<AttributeModifierProvider>> attributeModifiers = new HashMap<>();
    private IArmorTickExecutor setBonusTickExecutor;

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
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if (setBonusTickExecutor == null) return;

        if (entity instanceof PlayerEntity player) {
            if (!isWearingArmorSet(player, material)) return;
            setBonusTickExecutor.execute(stack, world, entity, slot, selected);
        }
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

    @Override
    public void addTooltip(String translatableString) {
        tooltip.add(Text.translatable(translatableString).formatted(Formatting.DARK_GRAY));
    }

    @Override
    public void addTooltip(Text text) {
        tooltip.add(text);
    }

    @Override
    public void setArmorBonusTickExecutor(IArmorTickExecutor executor) {
        setBonusTickExecutor = executor;
    }

    @Override
    public Item getItem() {
        return this;
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
        private IArmorTickExecutor setBonusTickExecutor;

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

        public Builder armorSetBonusTick(IArmorTickExecutor tickExecutor) {
            this.setBonusTickExecutor = tickExecutor;
            return this;
        }

        public CobaltArmorItem build() {
            CobaltArmorItem armor = new CobaltArmorItem(material, type, settings, nameFormatting);

            armor.tooltip = m_tooltip;
            armor.attributeModifiers = m_attributeModifiers;
            armor.setBonusTickExecutor = setBonusTickExecutor;

            return armor;
        }
    }

    private static boolean isWearingArmorSet(PlayerEntity player, ArmorMaterial material) {
        ArmorMaterial boots = getArmorMaterial(player.getInventory().getArmorStack(0).getItem());
        ArmorMaterial leggings = getArmorMaterial(player.getInventory().getArmorStack(1).getItem());
        ArmorMaterial chestplate = getArmorMaterial(player.getInventory().getArmorStack(2).getItem());
        ArmorMaterial helmet = getArmorMaterial(player.getInventory().getArmorStack(3).getItem());

        return  boots == material &&
                leggings == material &&
                chestplate == material &&
                helmet == material;
    }

    private static ArmorMaterial getArmorMaterial(Item item) {
        if (item instanceof ArmorItem armorItem) return armorItem.getMaterial();
        if (item instanceof CobaltEquipmentItem equipmentItem) return equipmentItem.material;
        return null;
    }

    public static void addSetBonusStatusEffect(Entity entity, StatusEffectInstance effect) {
        if (entity instanceof PlayerEntity player) addSetBonusStatusEffect(player, effect);
    }

    public static void addSetBonusStatusEffect(PlayerEntity player, StatusEffectInstance effect) {
        boolean hasEffect = player.hasStatusEffect(effect.getEffectType());

        if (!hasEffect) {
            player.addStatusEffect(new StatusEffectInstance(effect.getEffectType(), effect.getDuration(), effect.getAmplifier(), false, false, true));
        }

        if (player.getActiveStatusEffects().containsKey(effect.getEffectType())) {
            if (player.getActiveStatusEffects().get(effect.getEffectType()).getDuration() < 221) {
                player.addStatusEffect(new StatusEffectInstance(effect.getEffectType(), effect.getDuration(), effect.getAmplifier(), false, false, true));
            }
        }
    }

    public interface IArmorTickExecutor {
        void execute(ItemStack stack, World world, Entity entity, int slot, boolean selected);
    }
}
