package se.fusion1013.items;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class CobaltTrinketItem extends TrinketItem {

    private final Formatting m_nameFormatting;
    private final TrinketModifierProvider m_modifierProvider;

    public CobaltTrinketItem(Settings settings, TrinketModifierProvider modifierProvider, Formatting nameFormatting) {
        super(settings);
        this.m_modifierProvider = modifierProvider;
        this.m_nameFormatting = nameFormatting;
    }

    @Override
    public Text getName(ItemStack stack) {
        Text text = super.getName(stack);
        return text.copy().formatted(m_nameFormatting);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        var modifiers = super.getModifiers(stack, slot, entity, uuid);
        return m_modifierProvider.getModifiers(modifiers, stack, slot, entity, uuid);


        // SlotAttributes.addSlotModifier(modifiers, "feet/aglet", uuid, 1, EntityAttributeModifier.Operation.ADDITION);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable(getTranslationKey(stack) + ".tooltip").formatted(Formatting.DARK_GRAY));
    }

    // TODO: Add builder

    public interface TrinketModifierProvider {
        Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(Multimap<EntityAttribute, EntityAttributeModifier> modifiers, ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid);
    }
}
