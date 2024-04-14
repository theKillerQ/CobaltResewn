package se.fusion1013.items;

import net.minecraft.item.Item;
import net.minecraft.text.Text;
import se.fusion1013.items.armor.CobaltArmorItem;

public interface ICobaltItem {

    void addTooltip(String translatableString);
    void addTooltip(Text text);
    void setArmorBonusTickExecutor(CobaltArmorItem.IArmorTickExecutor executor);
    Item getItem();

}
