package se.fusion1013.items.tools;

import se.fusion1013.util.item.AttributeModifierProvider;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CobaltAxeItem extends AxeItem {

    private final Formatting m_nameFormatting;

    private List<Text> m_tooltip = new ArrayList<>();
    private boolean m_isDamageable = false;
    private Map<EquipmentSlot, List<AttributeModifierProvider>> m_attributeModifiers = new HashMap<>();

    public CobaltAxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings, Formatting nameFormatting) {
        super(material, attackDamage, attackSpeed, settings);

        this.m_nameFormatting = nameFormatting;
    }

    @Override
    public Text getName(ItemStack stack) {
        Text text = super.getName(stack);
        return text.copy().formatted(m_nameFormatting);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable(getTranslationKey(stack) + ".tooltip").formatted(Formatting.DARK_GRAY));
        tooltip.addAll(m_tooltip);
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
        private final ToolMaterial material;
        private final int attackDamage;
        private final float attackSpeed;
        private final Item.Settings settings;
        private final Formatting nameFormatting;

        private final List<Text> m_tooltip = new ArrayList<>();
        private boolean m_isDamageable = false;
        private final Map<EquipmentSlot, List<AttributeModifierProvider>> m_attributeModifiers = new HashMap<>();

        public Builder(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings, Formatting nameFormatting) {
            this.material = material;
            this.attackDamage = attackDamage;
            this.attackSpeed = attackSpeed;
            this.settings = settings;
            this.nameFormatting = nameFormatting;
        }

        public CobaltAxeItem.Builder tooltip(Text text) {
            m_tooltip.add(text);
            return this;
        }

        public CobaltAxeItem.Builder attributeModifier(EntityAttribute attribute, EntityAttributeModifier modifier, EquipmentSlot... slots) {
            for (EquipmentSlot slot : slots) {
                List<AttributeModifierProvider> list = m_attributeModifiers.computeIfAbsent(slot, k -> new ArrayList<>());
                list.add(new AttributeModifierProvider(attribute, modifier));
            }
            return this;
        }

        public CobaltAxeItem build() {
            CobaltAxeItem axe = new CobaltAxeItem(material, attackDamage, attackSpeed, settings, nameFormatting);

            axe.m_tooltip = m_tooltip;
            axe.m_isDamageable = m_isDamageable;
            axe.m_attributeModifiers = m_attributeModifiers;

            return axe;
        }
    }
}
