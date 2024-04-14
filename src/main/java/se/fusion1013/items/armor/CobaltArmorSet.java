package se.fusion1013.items.armor;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import se.fusion1013.items.ICobaltItem;
import se.fusion1013.items.materials.CobaltArmorMaterial;
import se.fusion1013.util.item.ArmorUtil;

import java.util.List;

public class CobaltArmorSet {

    // Armor pieces
    private boolean hasHelmet;
    public ICobaltItem helmet;

    private boolean hasChestplate;
    public ICobaltItem chestplate;

    private boolean hasLeggings;
    public ICobaltItem leggings;

    private boolean hasBoots;
    public ICobaltItem boots;

    public ArmorSetBonus setBonus;

    private CobaltArmorSet() {}

    private void applySetBonus() {
        if (hasHelmet) applySetBonusTooltips(helmet, setBonus);
        if (hasChestplate) applySetBonusTooltips(chestplate, setBonus);
        if (hasLeggings) applySetBonusTooltips(leggings, setBonus);
        if (hasBoots) applySetBonusTooltips(boots, setBonus);

        if (hasBoots) applySetBonus(boots, setBonus);
        else if (hasLeggings) applySetBonus(leggings, setBonus);
        else if (hasChestplate) applySetBonus(chestplate, setBonus);
        else if (hasHelmet) applySetBonus(helmet, setBonus);
    }

    private static void applySetBonusTooltips(ICobaltItem item, ArmorSetBonus setBonus) {
        item.addTooltip("");
        item.addTooltip(Text.translatable("item.cobalt.armor.set_bonus_header").formatted(Formatting.GOLD));
        for (String s : setBonus.tooltip()) item.addTooltip(Text.translatable(s).formatted(Formatting.GRAY));
    }

    private static void applySetBonus(ICobaltItem item, ArmorSetBonus setBonus) {
        item.setArmorBonusTickExecutor(setBonus.executor());
    }

    public static class Builder {

        private final CobaltArmorMaterial material;
        private final Formatting nameFormatting;

        // Armor pieces
        private boolean hasHelmet;
        private ICobaltItem helmet;

        private boolean hasChestplate;
        private ICobaltItem chestplate;

        private boolean hasLeggings;
        private ICobaltItem leggings;

        private boolean hasBoots;
        private ICobaltItem boots;

        private ArmorSetBonus setBonus;

        public Builder(CobaltArmorMaterial material, Formatting nameFormatting) {
            this.material = material;
            this.nameFormatting = nameFormatting;
        }

        public Builder withSetBonus(ArmorSetBonus setBonus) {
            this.setBonus = setBonus;
            return this;
        }

        public Builder withHelmet(boolean asEquipment) {
            hasHelmet = true;
            helmet = ArmorUtil.getArmorItem(material, asEquipment, ArmorItem.Type.HELMET, nameFormatting);
            return this;
        }

        public Builder withChestplate(boolean asEquipment) {
            hasChestplate = true;
            chestplate = ArmorUtil.getArmorItem(material, asEquipment, ArmorItem.Type.CHESTPLATE, nameFormatting);
            return this;
        }

        public Builder withLeggings(boolean asEquipment) {
            hasLeggings = true;
            leggings = ArmorUtil.getArmorItem(material, asEquipment, ArmorItem.Type.LEGGINGS, nameFormatting);
            return this;
        }

        public Builder withBoots(boolean asEquipment) {
            hasBoots = true;
            boots = ArmorUtil.getArmorItem(material, asEquipment, ArmorItem.Type.BOOTS, nameFormatting);
            return this;
        }

        public CobaltArmorSet build() {
            var set = new CobaltArmorSet();

            set.hasHelmet = hasHelmet;
            set.helmet = helmet;

            set.hasChestplate = hasChestplate;
            set.chestplate = chestplate;

            set.hasLeggings = hasLeggings;
            set.leggings = leggings;

            set.hasBoots = hasBoots;
            set.boots = boots;

            if (setBonus != null) {
                set.setBonus = setBonus;
                set.applySetBonus();
            }

            return set;
        }

    }
}
