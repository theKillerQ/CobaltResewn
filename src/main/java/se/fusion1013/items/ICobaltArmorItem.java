package se.fusion1013.items;

import net.minecraft.item.Item;
import net.minecraft.text.Text;
import se.fusion1013.items.armor.CobaltArmorItem;

public interface ICobaltArmorItem {

    @Deprecated
    default void addTooltip(String translatableString) {}
    @Deprecated
    default void addTooltip(Text text) {}
    Item getItem();

}
