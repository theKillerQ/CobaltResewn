package se.fusion1013.items.armor;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import se.fusion1013.items.CobaltItemConfiguration;
import se.fusion1013.items.ICobaltArmorItem;
import se.fusion1013.items.materials.CobaltArmorMaterial;
import se.fusion1013.util.item.ArmorUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class CobaltArmorSet {

    public static final Map<String, CobaltArmorSet> REGISTERED_ARMOR_SETS = new HashMap<>();

    // Registered items
    public Item registeredHelmet;
    public Item registeredChestplate;
    public Item registeredLeggings;
    public Item registeredBoots;

    // Armor pieces
    private boolean hasHelmet;
    public ICobaltArmorItem helmet;

    private boolean hasChestplate;
    public ICobaltArmorItem chestplate;

    private boolean hasLeggings;
    public ICobaltArmorItem leggings;

    private boolean hasBoots;
    public ICobaltArmorItem boots;

    public ArmorSetBonus setBonus;

    public final CobaltArmorMaterial material;

    private BiConsumer<MinecraftServer, PlayerEntity> triggerAbility;

    public CobaltArmorSet(CobaltArmorMaterial material) {
        this.material = material;
        REGISTERED_ARMOR_SETS.put(material.getName(), this);
    }

    private void applySetBonus() {
        if (hasBoots) applySetBonus(boots, setBonus);
        else if (hasLeggings) applySetBonus(leggings, setBonus);
        else if (hasChestplate) applySetBonus(chestplate, setBonus);
        else if (hasHelmet) applySetBonus(helmet, setBonus);
    }

    private static void applySetBonus(ICobaltArmorItem item, ArmorSetBonus setBonus) {
        item.setArmorBonusTickExecutor(setBonus.executor());
    }

    public void triggerArmorAbility(MinecraftServer server, PlayerEntity player) {
        if (triggerAbility != null) triggerAbility.accept(server, player);
    }

    public static class Builder {

        private final CobaltArmorMaterial material;
        private final CobaltItemConfiguration configuration;

        // Armor pieces
        private boolean hasHelmet;
        private boolean helmetAsEquipment;
        private ICobaltArmorItem helmet;

        private boolean hasChestplate;
        private boolean chestplateAsEquipment;
        private ICobaltArmorItem chestplate;

        private boolean hasLeggings;
        private boolean leggingsAsEquipment;
        private ICobaltArmorItem leggings;

        private boolean hasBoots;
        private boolean bootsAsEquipment;
        private ICobaltArmorItem boots;

        private ArmorSetBonus setBonus;

        private BiConsumer<MinecraftServer, PlayerEntity> triggerAbility;

        public Builder(CobaltArmorMaterial material, CobaltItemConfiguration configuration) {
            this.material = material;
            this.configuration = configuration;
        }

        public Builder withSetBonus(ArmorSetBonus setBonus) {
            this.setBonus = setBonus;
            return this;
        }

        public Builder withAll() {
            withHelmet();
            withChestplate();
            withLeggings();
            withBoots();
            return this;
        }

        public Builder withHelmet() { return withHelmet(false); }

        public Builder withHelmet(boolean asEquipment) {
            hasHelmet = true;
            helmetAsEquipment = asEquipment;
            return this;
        }

        public Builder withChestplate() { return withChestplate(false); }

        public Builder withChestplate(boolean asEquipment) {
            hasChestplate = true;
            chestplateAsEquipment = asEquipment;
            return this;
        }

        public Builder withLeggings() { return withLeggings(false); }

        public Builder withLeggings(boolean asEquipment) {
            hasLeggings = true;
            leggingsAsEquipment = asEquipment;
            return this;
        }

        public Builder withBoots() { return withBoots(false); }

        public Builder withBoots(boolean asEquipment) {
            hasBoots = true;
            bootsAsEquipment = asEquipment;
            return this;
        }

        public Builder withTriggerAbility(BiConsumer<MinecraftServer, PlayerEntity> trigger) {
            this.triggerAbility = trigger;
            return this;
        }

        public CobaltArmorSet build() {
            var set = new CobaltArmorSet(material);

            // Apply modifications to the config that all items inherit from
            if (setBonus != null) {
                configuration.tooltip("");
                configuration.tooltip(Text.translatable("item.cobalt.armor.set_bonus_header").formatted(Formatting.GOLD));
                for (String s : setBonus.tooltip()) configuration.tooltip(Text.translatable(s).formatted(Formatting.GRAY));
            }

            // Apply trigger ability
            if (triggerAbility != null) {
                set.triggerAbility = triggerAbility;
                configuration.tooltip("");

                var header = Text.empty()
                        .append(Text.literal("[Set Bonus] Press ").formatted(Formatting.GOLD))
                        .append(Text.keybind("key.cobalt.armor_trigger").formatted(Formatting.GOLD))
                        .append(Text.literal(": ").formatted(Formatting.GOLD))
                        .append(Text.translatable("item.cobalt." + material.getName() + ".trigger_ability.header").formatted(Formatting.GOLD))
                        .append(Text.literal(" [").formatted(Formatting.GOLD))
                        .append(Text.translatable("item.cobalt." + material.getName() + ".trigger_ability.cost").formatted(Formatting.GRAY))
                        .append(Text.literal("]").formatted(Formatting.GOLD));

                configuration.tooltip(header);
                configuration.tooltip(Text.translatable("item.cobalt." + material.getName() + ".trigger_ability.tooltip").formatted(Formatting.GRAY));
            }

            // Create the armor items
            if (hasHelmet) helmet = ArmorUtil.getArmorItem(material, helmetAsEquipment, ArmorItem.Type.HELMET, configuration);
            if (hasChestplate) chestplate = ArmorUtil.getArmorItem(material, chestplateAsEquipment, ArmorItem.Type.CHESTPLATE, configuration);
            if (hasLeggings) leggings = ArmorUtil.getArmorItem(material, leggingsAsEquipment, ArmorItem.Type.LEGGINGS, configuration);
            if (hasBoots) boots = ArmorUtil.getArmorItem(material, bootsAsEquipment, ArmorItem.Type.BOOTS, configuration);

            set.hasHelmet = hasHelmet;
            set.helmet = helmet;

            set.hasChestplate = hasChestplate;
            set.chestplate = chestplate;

            set.hasLeggings = hasLeggings;
            set.leggings = leggings;

            set.hasBoots = hasBoots;
            set.boots = boots;

            if (setBonus != null) {
                set.setBonus = setBonus;
                set.applySetBonus();
            }

            return set;
        }
    }
}
