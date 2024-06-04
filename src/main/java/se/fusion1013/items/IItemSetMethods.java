package se.fusion1013.items;

import dev.emi.trinkets.api.SlotReference;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface IItemSetMethods {

    default void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {}
    default void trinketTick(ItemStack stack, SlotReference slot, LivingEntity entity) {}

    Text[] appendTooltip();

}
