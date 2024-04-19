package se.fusion1013.items.sword;

import se.fusion1013.items.CobaltItemConfiguration;
import se.fusion1013.util.item.AttributeModifierProvider;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CobaltSwordItem extends SwordItem {

    private final CobaltItemConfiguration configuration;

    public CobaltSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, CobaltItemConfiguration configuration, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
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
