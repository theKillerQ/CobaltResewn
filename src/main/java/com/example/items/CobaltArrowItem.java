package com.example.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CobaltArrowItem extends ArrowItem {

    private final Formatting nameFormatting;
    private final CustomEntityFactory<? extends PersistentProjectileEntity> entityFactory;

    public CobaltArrowItem(Settings settings, Formatting nameFormatting, CustomEntityFactory<? extends PersistentProjectileEntity> factory) {
        super(settings);
        this.nameFormatting = nameFormatting;
        this.entityFactory = factory;
    }

    @Override
    public Text getName(ItemStack stack) {
        Text text = super.getName(stack);
        return text.copy().formatted(nameFormatting);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable(getTranslationKey(stack) + ".tooltip").formatted(Formatting.DARK_GRAY));
    }

    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
        return entityFactory.create(shooter.getX(), shooter.getEyeY() - 0.10000000149011612D, shooter.getZ(), world, stack);
    }

    public CustomEntityFactory<? extends PersistentProjectileEntity> getEntityFactory() {
        return entityFactory;
    }

    public interface CustomEntityFactory<T extends PersistentProjectileEntity> {
        T create(double x, double y, double z, World world, ItemStack stack);
    }
}
