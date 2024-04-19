package se.fusion1013.items.armor;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.server.network.ServerPlayerEntity;
import se.fusion1013.Main;
import se.fusion1013.items.CobaltEquipmentItem;
import se.fusion1013.items.CobaltItemConfiguration;
import se.fusion1013.items.ICobaltArmorItem;
import se.fusion1013.items.materials.CobaltArmorMaterial;
import se.fusion1013.networking.CobaltNetworkingConstants;
import se.fusion1013.util.item.ArmorUtil;
import com.google.common.collect.Multimap;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CobaltArmorItem extends ArmorItem implements ICobaltArmorItem {

    private final CobaltItemConfiguration configuration;
    private final CobaltArmorMaterial cobaltMaterial;
    private IArmorTickExecutor setBonusTickExecutor;

    public CobaltArmorItem(CobaltArmorMaterial material, ArmorItem.Type type, CobaltItemConfiguration configuration, Item.Settings settings) {
        super(material, type, settings);

        this.configuration = configuration;
        cobaltMaterial = material;
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
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        armorSetBonusTick(stack, world, entity, slot, selected);
    }

    private void armorSetBonusTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (setBonusTickExecutor == null) return;

        if (entity instanceof PlayerEntity player) {
            if (!isWearingArmorSet(player, material)) return;
            setBonusTickExecutor.execute(stack, world, entity, slot, selected);
        }
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        var map = configuration.getAttributeModifiers(super.getAttributeModifiers(stack, slot), stack, slot);
        map = ArmorUtil.getAttributeModifiers(map, cobaltMaterial, type, slot);
        return map;
    }

    @Override
    public void postProcessNbt(NbtCompound nbt) {
        super.postProcessNbt(nbt);
        configuration.postProcessNbt(nbt);
    }

    @Override
    public void setArmorBonusTickExecutor(IArmorTickExecutor executor) {
        setBonusTickExecutor = executor;
    }

    @Override
    public Item getItem() {
        return this;
    }

    public static class Builder {

        // Generic
        private final CobaltArmorMaterial material;
        private final ArmorItem.Type type;
        private final CobaltItemConfiguration configuration;
        private final Item.Settings settings;
        private IArmorTickExecutor setBonusTickExecutor;

        public Builder(CobaltArmorMaterial material, Type type, CobaltItemConfiguration configuration, Settings settings) {
            this.material = material;
            this.type = type;
            this.configuration = configuration;
            this.settings = settings;
        }

        public Builder armorSetBonusTick(IArmorTickExecutor tickExecutor) {
            this.setBonusTickExecutor = tickExecutor;
            return this;
        }

        public CobaltArmorItem build() {
            CobaltArmorItem armor = new CobaltArmorItem(material, type, configuration, settings);
            armor.setBonusTickExecutor = setBonusTickExecutor;
            return armor;
        }
    }

    private static boolean isWearingArmorSet(PlayerEntity player, ArmorMaterial material) {
        ArmorMaterial boots = getArmorMaterial(player.getInventory().getArmorStack(0).getItem());
        ArmorMaterial leggings = getArmorMaterial(player.getInventory().getArmorStack(1).getItem());
        ArmorMaterial chestplate = getArmorMaterial(player.getInventory().getArmorStack(2).getItem());
        ArmorMaterial helmet = getArmorMaterial(player.getInventory().getArmorStack(3).getItem());

        return  boots == material &&
                leggings == material &&
                chestplate == material &&
                helmet == material;
    }

    private static ArmorMaterial getArmorMaterial(Item item) {
        if (item instanceof ArmorItem armorItem) return armorItem.getMaterial();
        if (item instanceof CobaltEquipmentItem equipmentItem) return equipmentItem.material;
        return null;
    }

    public static void addSetBonusStatusEffect(Entity entity, StatusEffectInstance effect) {
        if (entity instanceof PlayerEntity player) addSetBonusStatusEffect(player, effect);
    }

    public static void addSetBonusStatusEffect(PlayerEntity player, StatusEffectInstance effect) {
        boolean hasEffect = player.hasStatusEffect(effect.getEffectType());

        if (!hasEffect) {
            player.addStatusEffect(new StatusEffectInstance(effect.getEffectType(), effect.getDuration(), effect.getAmplifier(), false, false, true));
        }

        if (player.getActiveStatusEffects().containsKey(effect.getEffectType())) {
            if (player.getActiveStatusEffects().get(effect.getEffectType()).getDuration() < 221) {
                player.addStatusEffect(new StatusEffectInstance(effect.getEffectType(), effect.getDuration(), effect.getAmplifier(), false, false, true));
            }
        }
    }

    public interface IArmorTickExecutor {
        void execute(ItemStack stack, World world, Entity entity, int slot, boolean selected);
    }
}
