package se.fusion1013.items.armor;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import se.fusion1013.items.CobaltItemConfiguration;
import se.fusion1013.items.ICobaltArmorItem;
import se.fusion1013.items.materials.CobaltArmorMaterial;
import se.fusion1013.util.item.ArmorUtil;

public class CobaltArmorSet {

    // Registered items
    public Item registeredHelmet;
    public Item registeredChestplate;
    public Item registeredLeggings;
    public Item registeredBoots;

    // Armor pieces
    private boolean hasHelmet;
    public ICobaltArmorItem helmet;

    private boolean hasChestplate;
    public ICobaltArmorItem chestplate;

    private boolean hasLeggings;
    public ICobaltArmorItem leggings;

    private boolean hasBoots;
    public ICobaltArmorItem boots;

    public ArmorSetBonus setBonus;

    public CobaltArmorSet() {}

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

    private static void applySetBonusTooltips(ICobaltArmorItem item, ArmorSetBonus setBonus) {
        item.addTooltip("");
        item.addTooltip(Text.translatable("item.cobalt.armor.set_bonus_header").formatted(Formatting.GOLD));
        for (String s : setBonus.tooltip()) item.addTooltip(Text.translatable(s).formatted(Formatting.GRAY));
    }

    private static void applySetBonus(ICobaltArmorItem item, ArmorSetBonus setBonus) {
        item.setArmorBonusTickExecutor(setBonus.executor());
    }

    public static class Builder {

        private final CobaltArmorMaterial material;
        private final CobaltItemConfiguration configuration;

        // Armor pieces
        private boolean hasHelmet;
        private ICobaltArmorItem helmet;

        private boolean hasChestplate;
        private ICobaltArmorItem chestplate;

        private boolean hasLeggings;
        private ICobaltArmorItem leggings;

        private boolean hasBoots;
        private ICobaltArmorItem boots;

        private ArmorSetBonus setBonus;

        public Builder(CobaltArmorMaterial material, CobaltItemConfiguration configuration) {
            this.material = material;
            this.configuration = configuration;
        }

        public Builder withSetBonus(ArmorSetBonus setBonus) {
            this.setBonus = setBonus;
            return this;
        }

        public Builder withAll() {
            withHelmet();
            withChestplate();
            withLeggings();
            withBoots();
            return this;
        }

        public Builder withHelmet() { return withHelmet(false); }

        public Builder withHelmet(boolean asEquipment) {
            hasHelmet = true;
            helmet = ArmorUtil.getArmorItem(material, asEquipment, ArmorItem.Type.HELMET, configuration);
            return this;
        }

        public Builder withChestplate() { return withChestplate(false); }

        public Builder withChestplate(boolean asEquipment) {
            hasChestplate = true;
            chestplate = ArmorUtil.getArmorItem(material, asEquipment, ArmorItem.Type.CHESTPLATE, configuration);
            return this;
        }

        public Builder withLeggings() { return withLeggings(false); }

        public Builder withLeggings(boolean asEquipment) {
            hasLeggings = true;
            leggings = ArmorUtil.getArmorItem(material, asEquipment, ArmorItem.Type.LEGGINGS, configuration);
            return this;
        }

        public Builder withBoots() { return withBoots(false); }

        public Builder withBoots(boolean asEquipment) {
            hasBoots = true;
            boots = ArmorUtil.getArmorItem(material, asEquipment, ArmorItem.Type.BOOTS, configuration);
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
