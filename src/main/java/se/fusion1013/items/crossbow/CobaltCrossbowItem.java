package se.fusion1013.items.crossbow;

import net.minecraft.item.CrossbowItem;
import net.minecraft.nbt.NbtCompound;

public class CobaltCrossbowItem extends CrossbowItem {

    public CobaltCrossbowItem(Settings settings) {
        super(settings);
    }

    @Override
    public void postProcessNbt(NbtCompound nbt) {
        super.postProcessNbt(nbt);
        nbt.putBoolean("Unbreakable", true);
    }
}
