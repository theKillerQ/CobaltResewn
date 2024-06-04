package se.fusion1013.items;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import se.fusion1013.Main;
import se.fusion1013.util.item.ArmorUtil;

import java.util.*;

/**
 * An {@link ItemSet} is a set of {@link net.minecraft.item.Item}s that have some property when equipped together.
 *
 * @author Fusion1013
 */
public class ItemSet {

    private static final Map<Identifier, ItemSet> ITEM_SETS = new HashMap<>();
    private static final Map<Item, List<ItemSet>> ITEM_SET_OWNERS = new HashMap<>();
    private static final Map<Item, List<ItemSet>> ITEM_SET_MEMBERS = new HashMap<>();

    private final ItemSetItem[] items;
    private final IItemSetMethods setMethods;

    public ItemSet(ItemSetItem[] items, IItemSetMethods methods) {
        this.items = items;
        setMethods = methods;
    }

    // -- Validity Check

    /**
     * Checks if the entity has all the {@link Item}s in this set active.
     * @param entity the {@link Entity} that has the {@link Item}s.
     * @return true if all {@link Item}s are active.
     */
    public boolean hasItems(Entity entity) {
        if (entity instanceof PlayerEntity player) return hasItems(player);
        return false;
    }

    private boolean hasItems(PlayerEntity player) {
        boolean hasItems = true;

        for (ItemSetItem item : items) {
            switch (item.requiredLocation) {

                case Inventory -> hasItems = player.getInventory().containsAny(stack -> stack.getItem() == item.item) && hasItems;
                case Armor -> hasItems = ArmorUtil.isWearingArmorItem(player, item.item) && hasItems;
                case Trinket -> {
                    var trinketComponent = TrinketsApi.getTrinketComponent(player);
                    if (trinketComponent.isPresent()) {
                        hasItems = trinketComponent.get().isEquipped(item.item) && hasItems;
                    }
                }
                case Hand -> hasItems = player.getMainHandStack().getItem() == item.item && hasItems;
                case Offhand -> hasItems = player.getOffHandStack().getItem() == item.item && hasItems;
            }
        }

        return hasItems;
    }

    // -- Register

    /**
     * Registers a new {@link ItemSet}.
     * @param identifier
     * @param items
     * @param methods
     * @return
     */
    public static ItemSet register(Identifier identifier, ItemSetItem[] items, IItemSetMethods methods) {
        if (items.length <= 0) {
            Main.LOGGER.error("Could not register item set with 0 members");
            return null;
        }

        // Create the item set
        var set = new ItemSet(items, methods);

        // Register the item set
        // Add it to the ItemSet map with the identifier
        // Add it to the item set owners with the first item in the set. This item will perform all the item set actions
        ITEM_SETS.put(identifier, set);
        ITEM_SET_OWNERS.computeIfAbsent(items[0].item, k -> new ArrayList<>()).add(set);

        // Add all items in the set to the item set members map
        for (ItemSetItem itemSetItem : items) {
            ITEM_SET_MEMBERS.computeIfAbsent(itemSetItem.item, k -> new ArrayList<>()).add(set);
        }

        return set;
    }

    // -- Events

    public static void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        var sets = ITEM_SET_MEMBERS.get(stack.getItem());
        if (sets == null) return;

        // Get the tooltips
        var tips = getTooltips(sets, stack.getItem());
        if (tips.isEmpty()) return; // If there are no tooltips, do not add anything

        // Append a space and the header
        tooltip.add(Text.empty());
        tooltip.add(Text.translatable("item_set.cobalt.header").formatted(Formatting.GOLD));

        tooltip.addAll(tips);
    }

    private static List<Text> getTooltips(List<ItemSet> sets, Item item) {
        List<Text> tooltips = new ArrayList<>();

        for (ItemSet set : sets) {
            boolean addTooltip = true;

            for (ItemSetItem itemSetItem : set.items) {
                if (itemSetItem.item == item && !itemSetItem.showTooltip) {
                    addTooltip = false;
                    break;
                }
            }

            if (!addTooltip) continue;

            var text = set.setMethods.appendTooltip();
            tooltips.addAll(Arrays.asList(text));
            tooltips.add(Text.empty());
        }

        return tooltips;
    }

    public static void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        var sets = ITEM_SET_OWNERS.get(stack.getItem()); // Get all the item sets where this item is the owner
        if (sets == null) return;
        for (ItemSet set : sets) {
            if (!set.hasItems(entity)) continue; // Do not activate if entity does not have all the items

            set.setMethods.inventoryTick(stack, world, entity, slot, selected);
        }
    }

    public static void trinketTick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        var sets = ITEM_SET_OWNERS.get(stack.getItem()); // Get all the item sets where this item is the owner
        if (sets == null) return;
        for (ItemSet set : sets) {
            if (!set.hasItems(entity)) continue; // Do not activate if entity does not have all the items

            set.setMethods.trinketTick(stack, slot, entity);
        }
    }

    public static void entityDamaged(LivingEntity entity, DamageSource source, float amount) {

    }

    public enum ItemLocation {
        Inventory, Armor, Trinket, Hand, Offhand
    }

    public static final class ItemSetItem {
        private final Item item;
        private final ItemLocation requiredLocation;
        private final boolean showTooltip;

        public ItemSetItem(Item item, ItemLocation requiredLocation) {
            this(item, requiredLocation, true);
        }

        public ItemSetItem(Item item, ItemLocation requiredLocation, boolean showTooltip) {
            this.item = item;
            this.requiredLocation = requiredLocation;
            this.showTooltip = showTooltip;
        }

        public Item item() {
            return item;
        }

        public ItemLocation requiredLocation() {
            return requiredLocation;
        }

        public boolean showTooltip() {
            return showTooltip;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (ItemSetItem) obj;
            return Objects.equals(this.item, that.item) &&
                    Objects.equals(this.requiredLocation, that.requiredLocation) &&
                    this.showTooltip == that.showTooltip;
        }

        @Override
        public int hashCode() {
            return Objects.hash(item, requiredLocation, showTooltip);
        }

        @Override
        public String toString() {
            return "ItemSetItem[" +
                    "item=" + item + ", " +
                    "requiredLocation=" + requiredLocation + ", " +
                    "showTooltip=" + showTooltip + ']';
        }
    }

}
