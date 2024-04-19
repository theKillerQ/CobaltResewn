package se.fusion1013.items;

import se.fusion1013.items.armor.CobaltArmorItem;
import se.fusion1013.items.materials.CobaltArmorMaterial;
import se.fusion1013.util.item.ArmorUtil;
import com.google.common.collect.Multimap;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Equipment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CobaltEquipmentItem extends Item implements Equipment, ICobaltArmorItem {

    private final CobaltItemConfiguration configuration;
    private final EquipmentSlot slotType;
    public final CobaltArmorMaterial material;

    public CobaltEquipmentItem(CobaltArmorMaterial material, CobaltItemConfiguration configuration, Settings settings, EquipmentSlot slotType) {
        super(settings.maxCount(1));

        this.configuration = configuration;
        this.slotType = slotType;
        this.material = material;
    }

    @Override
    public EquipmentSlot getSlotType() {
        return slotType;
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
        var map = configuration.getAttributeModifiers(super.getAttributeModifiers(stack, slot), stack, slot);
        map = ArmorUtil.getAttributeModifiers(map, material, ArmorUtil.toArmorType(getSlotType()), slot);
        return map;
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public void postProcessNbt(NbtCompound nbt) {
        super.postProcessNbt(nbt);

    }

    @Override
    public void setArmorBonusTickExecutor(CobaltArmorItem.IArmorTickExecutor executor) {
        // TODO
    }

    @Override
    public Item getItem() {
        return this;
    }

}
