package se.fusion1013.items.crossbow;

import com.google.common.collect.Multimap;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import se.fusion1013.items.CobaltItemConfiguration;

import java.util.List;

public class CobaltCrossbowItem extends CrossbowItem {

    private final CobaltItemConfiguration configuration;

    public CobaltCrossbowItem(CobaltItemConfiguration configuration, Settings settings) {
        super(settings);
        this.configuration = configuration;
    }

    @Override
    public Text getName(ItemStack stack) {
        return configuration.applyNameFormatting(super.getName(stack));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        configuration.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        return configuration.getAttributeModifiers(super.getAttributeModifiers(stack, slot), stack, slot);
    }

    @Override
    public void postProcessNbt(NbtCompound nbt) {
        super.postProcessNbt(nbt);
        configuration.postProcessNbt(nbt);
    }
}
