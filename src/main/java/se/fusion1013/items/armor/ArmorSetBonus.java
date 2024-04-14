package se.fusion1013.items.armor;

import net.minecraft.text.Text;

public record ArmorSetBonus(String[] tooltip, CobaltArmorItem.IArmorTickExecutor executor) {
}
