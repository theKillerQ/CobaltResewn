package se.fusion1013.items;

import com.google.common.collect.Multimap;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CobaltArrowItem extends ArrowItem {

    private final CobaltItemConfiguration configuration;
    private final CustomEntityFactory<? extends PersistentProjectileEntity> entityFactory;

    public CobaltArrowItem(CobaltItemConfiguration configuration, Settings settings, CustomEntityFactory<? extends PersistentProjectileEntity> factory) {
        super(settings);
        this.configuration = configuration;
        this.entityFactory = factory;
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
