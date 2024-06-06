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
        super.appendTooltip(stack, world, tooltip, context);
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
    public Item getItem() {
        return this;
    }
}
