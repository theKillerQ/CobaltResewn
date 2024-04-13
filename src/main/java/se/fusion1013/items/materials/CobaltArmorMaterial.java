package se.fusion1013.items.materials;

import se.fusion1013.util.item.AttributeModifierProvider;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

import java.util.ArrayList;
import java.util.List;

public class CobaltArmorMaterial implements ArmorMaterial {

    private final String m_name;
    private final int[] m_baseDurability;
    private final int[] m_protectionValues;
    private final int m_materialMultiplier;

    private List<AttributeModifierProvider> helmetAttributes = new ArrayList<>();
    private List<AttributeModifierProvider> chestplateAttributes = new ArrayList<>();
    private List<AttributeModifierProvider> leggingsAttributes = new ArrayList<>();
    private List<AttributeModifierProvider> bootsAttributes = new ArrayList<>();

    // Overrides
    private SoundEvent m_equipSound = SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
    private Ingredient m_repairIngredient = Ingredient.ofItems(Items.LEATHER);
    private float m_toughness = 0;
    private float m_knockbackResistance = 0;

    public CobaltArmorMaterial(String name, int[] baseDurability, int[] protectionValues, int materialMultiplier) {
        m_name = name;
        m_baseDurability = baseDurability;
        m_protectionValues = protectionValues;
        m_materialMultiplier = materialMultiplier;
    }

    @Override
    public int getDurability(ArmorItem.Type type) {
        return m_baseDurability[type.getEquipmentSlot().getEntitySlotId()] * m_materialMultiplier;
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        return m_protectionValues[type.getEquipmentSlot().getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return m_materialMultiplier;
    }

    @Override
    public SoundEvent getEquipSound() {
        return m_equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return m_repairIngredient;
    }

    @Override
    public String getName() {
        return m_name;
    }

    @Override
    public float getToughness() {
        return m_toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return m_knockbackResistance;
    }

    public List<AttributeModifierProvider> getAttributeModifiers(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> helmetAttributes;
            case CHEST -> chestplateAttributes;
            case LEGS -> leggingsAttributes;
            case FEET -> bootsAttributes;
            default -> new ArrayList<>();
        };
    }

    public static class Builder {

        private final String m_name;
        private final int[] m_baseDurability;
        private final int[] m_protectionValues;
        private final int m_materialMultiplier;

        private SoundEvent m_equipSound = SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
        private Ingredient m_repairIngredient = Ingredient.ofItems(Items.LEATHER);
        private float m_toughness = 0;
        private float m_knockbackResistance = 0;

        private List<AttributeModifierProvider> helmetAttributes = new ArrayList<>();
        private List<AttributeModifierProvider> chestplateAttributes = new ArrayList<>();
        private List<AttributeModifierProvider> leggingsAttributes = new ArrayList<>();
        private List<AttributeModifierProvider> bootsAttributes = new ArrayList<>();

        public Builder(String name, int[] baseDurability, int[] protectionValues, int materialMultiplier) {
            m_name = name;
            m_baseDurability = baseDurability;
            m_protectionValues = protectionValues;
            m_materialMultiplier = materialMultiplier;
        }

        public CobaltArmorMaterial build() {
            var mat = new CobaltArmorMaterial(m_name, m_baseDurability, m_protectionValues, m_materialMultiplier);

            mat.m_equipSound = m_equipSound;
            mat.m_repairIngredient = m_repairIngredient;
            mat.m_toughness = m_toughness;
            mat.m_knockbackResistance = m_knockbackResistance;

            mat.helmetAttributes = helmetAttributes;
            mat.chestplateAttributes = chestplateAttributes;
            mat.leggingsAttributes = leggingsAttributes;
            mat.bootsAttributes = bootsAttributes;

            return mat;
        }

        public Builder attribute(EntityAttribute attribute, EntityAttributeModifier modifier, EquipmentSlot slot) {
            var container = new AttributeModifierProvider(attribute, modifier);
            switch (slot) {
                case HEAD -> helmetAttributes.add(container);
                case CHEST -> chestplateAttributes.add(container);
                case LEGS -> leggingsAttributes.add(container);
                case FEET -> bootsAttributes.add(container);
            }
            return this;
        }

        public Builder equipSound(SoundEvent sound) {
            m_equipSound = sound;
            return this;
        }

        public Builder repairIngredient(Ingredient ingredient) {
            m_repairIngredient = ingredient;
            return this;
        }

        public Builder toughness(float toughness) {
            m_toughness = toughness;
            return this;
        }

        public Builder knockbackResistance(float knockbackResistance) {
            m_knockbackResistance = knockbackResistance;
            return this;
        }

    }
}
