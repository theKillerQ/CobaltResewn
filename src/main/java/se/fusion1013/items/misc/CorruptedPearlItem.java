package se.fusion1013.items.misc;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import se.fusion1013.effect.CobaltEffects;
import se.fusion1013.items.CobaltItem;
import se.fusion1013.items.CobaltItemConfiguration;

import java.util.List;

public class CorruptedPearlItem extends CobaltItem {

    public CorruptedPearlItem() {
        super(CobaltItemConfiguration.create(Formatting.DARK_PURPLE), new FabricItemSettings());
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.addStatusEffect(new StatusEffectInstance(CobaltEffects.CORRUPTION_SPREAD, 10, 0, true, false, false));
        user.playSound(SoundEvents.ENTITY_ILLUSIONER_PREPARE_MIRROR, SoundCategory.PLAYERS, 1, 1);

        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(Text.empty());
        tooltip.add(Text.translatable("item.cobalt.item_use.header").formatted(Formatting.GOLD));
        tooltip.add(Text.translatable("item.cobalt.corrupted_pearl.ability").formatted(Formatting.GRAY));
    }
}
