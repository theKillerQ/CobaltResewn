package se.fusion1013.items.armor;

import com.mojang.datafixers.util.Function3;
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
import net.minecraft.util.Arm;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import se.fusion1013.Main;
import se.fusion1013.items.*;
import se.fusion1013.items.materials.CobaltArmorMaterial;
import se.fusion1013.util.item.ArmorUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
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

    public final CobaltArmorMaterial material;

    // Item set
    private IItemSetMethods setMethods;
    public ItemSet itemSet;

    public CobaltArmorSet(CobaltArmorMaterial material) {
        this.material = material;
        REGISTERED_ARMOR_SETS.put(material.getName(), this);
    }

    public void register(String setId, BiFunction<String, Item, Item> registerFunction) {
        // Register the parts
        registeredHelmet = registerFunction.apply(setId + "_helmet", helmet.getItem());
        registeredChestplate = registerFunction.apply(setId + "_chestplate", chestplate.getItem());
        registeredLeggings = registerFunction.apply(setId + "_leggings", leggings.getItem());
        registeredBoots = registerFunction.apply(setId + "_boots", boots.getItem());

        // Create item set if methods are present
        if (setMethods != null) {
            Main.LOGGER.info("Registered armor set with item set methods");
            itemSet = ItemSet.register(new Identifier(setId), new ItemSet.ItemSetItem[] {
                    new ItemSet.ItemSetItem(registeredHelmet, ItemSet.ItemLocation.Armor),
                    new ItemSet.ItemSetItem(registeredChestplate, ItemSet.ItemLocation.Armor),
                    new ItemSet.ItemSetItem(registeredLeggings, ItemSet.ItemLocation.Armor),
                    new ItemSet.ItemSetItem(registeredBoots, ItemSet.ItemLocation.Armor)
            }, setMethods);
        }
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

        private IItemSetMethods itemSetMethods;

        public Builder(CobaltArmorMaterial material, CobaltItemConfiguration configuration) {
            this.material = material;
            this.configuration = configuration;
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

        public Builder withSetBonus(IItemSetMethods methods) {
            itemSetMethods = methods;
            return this;
        }

        public CobaltArmorSet build() {
            var set = new CobaltArmorSet(material);

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

            set.setMethods = itemSetMethods;

            return set;
        }
    }
}
