package se.fusion1013.blocks;

import io.wispforest.owo.itemgroup.OwoItemSettings;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import se.fusion1013.Main;
import se.fusion1013.items.CustomItemGroupRegistry;
import se.fusion1013.items.CustomItemRegistry;

import static se.fusion1013.items.CustomItemGroupRegistry.COBALT_GROUP_KEY;

public class CustomBlockRegistry {

    public static final Block WHITE_CHISELED_COPPER =                register("white_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block LIGHT_GRAY_CHISELED_COPPER =           register("light_gray_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block GRAY_CHISELED_COPPER =                 register("gray_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block BLACK_CHISELED_COPPER =                register("black_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block BROWN_CHISELED_COPPER =                register("brown_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block RED_CHISELED_COPPER =                  register("red_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block ORANGE_CHISELED_COPPER =               register("orange_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block YELLOW_CHISELED_COPPER =               register("yellow_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block LIME_CHISELED_COPPER =                 register("lime_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block GREEN_CHISELED_COPPER =                register("green_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block CYAN_CHISELED_COPPER =                 register("cyan_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block LIGHT_BLUE_CHISELED_COPPER =           register("light_blue_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block BLUE_CHISELED_COPPER =                 register("blue_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block PURPLE_CHISELED_COPPER =               register("purple_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block MAGENTA_CHISELED_COPPER =              register("magenta_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block PINK_CHISELED_COPPER =                 register("pink_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));

    public static final Block EXPOSED_WHITE_CHISELED_COPPER =        register("exposed_white_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block EXPOSED_LIGHT_GRAY_CHISELED_COPPER =   register("exposed_light_gray_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block EXPOSED_GRAY_CHISELED_COPPER =         register("exposed_gray_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block EXPOSED_BLACK_CHISELED_COPPER =        register("exposed_black_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block EXPOSED_BROWN_CHISELED_COPPER =        register("exposed_brown_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block EXPOSED_RED_CHISELED_COPPER =          register("exposed_red_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block EXPOSED_ORANGE_CHISELED_COPPER =       register("exposed_orange_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block EXPOSED_YELLOW_CHISELED_COPPER =       register("exposed_yellow_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block EXPOSED_LIME_CHISELED_COPPER =         register("exposed_lime_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block EXPOSED_GREEN_CHISELED_COPPER =        register("exposed_green_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block EXPOSED_CYAN_CHISELED_COPPER =         register("exposed_cyan_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block EXPOSED_LIGHT_BLUE_CHISELED_COPPER =   register("exposed_light_blue_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block EXPOSED_BLUE_CHISELED_COPPER =         register("exposed_blue_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block EXPOSED_PURPLE_CHISELED_COPPER =       register("exposed_purple_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block EXPOSED_MAGENTA_CHISELED_COPPER =      register("exposed_magenta_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block EXPOSED_PINK_CHISELED_COPPER =         register("exposed_pink_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));

    public static final Block WEATHERED_WHITE_CHISELED_COPPER =      register("weathered_white_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block WEATHERED_LIGHT_GRAY_CHISELED_COPPER = register("weathered_light_gray_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block WEATHERED_GRAY_CHISELED_COPPER =       register("weathered_gray_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block WEATHERED_BLACK_CHISELED_COPPER =      register("weathered_black_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block WEATHERED_BROWN_CHISELED_COPPER =      register("weathered_brown_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block WEATHERED_RED_CHISELED_COPPER =        register("weathered_red_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block WEATHERED_ORANGE_CHISELED_COPPER =     register("weathered_orange_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block WEATHERED_YELLOW_CHISELED_COPPER =     register("weathered_yellow_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block WEATHERED_LIME_CHISELED_COPPER =       register("weathered_lime_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block WEATHERED_GREEN_CHISELED_COPPER =      register("weathered_green_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block WEATHERED_CYAN_CHISELED_COPPER =       register("weathered_cyan_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block WEATHERED_LIGHT_BLUE_CHISELED_COPPER = register("weathered_light_blue_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block WEATHERED_BLUE_CHISELED_COPPER =       register("weathered_blue_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block WEATHERED_PURPLE_CHISELED_COPPER =     register("weathered_purple_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block WEATHERED_MAGENTA_CHISELED_COPPER =    register("weathered_magenta_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block WEATHERED_PINK_CHISELED_COPPER =       register("weathered_pink_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));

    public static final Block OXIDIZED_WHITE_CHISELED_COPPER =      register("oxidized_white_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block OXIDIZED_LIGHT_GRAY_CHISELED_COPPER = register("oxidized_light_gray_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block OXIDIZED_GRAY_CHISELED_COPPER =       register("oxidized_gray_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block OXIDIZED_BLACK_CHISELED_COPPER =      register("oxidized_black_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block OXIDIZED_BROWN_CHISELED_COPPER =      register("oxidized_brown_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block OXIDIZED_RED_CHISELED_COPPER =        register("oxidized_red_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block OXIDIZED_ORANGE_CHISELED_COPPER =     register("oxidized_orange_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block OXIDIZED_YELLOW_CHISELED_COPPER =     register("oxidized_yellow_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block OXIDIZED_LIME_CHISELED_COPPER =       register("oxidized_lime_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block OXIDIZED_GREEN_CHISELED_COPPER =      register("oxidized_green_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block OXIDIZED_CYAN_CHISELED_COPPER =       register("oxidized_cyan_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block OXIDIZED_LIGHT_BLUE_CHISELED_COPPER = register("oxidized_light_blue_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block OXIDIZED_BLUE_CHISELED_COPPER =       register("oxidized_blue_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block OXIDIZED_PURPLE_CHISELED_COPPER =     register("oxidized_purple_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block OXIDIZED_MAGENTA_CHISELED_COPPER =    register("oxidized_magenta_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));
    public static final Block OXIDIZED_PINK_CHISELED_COPPER =       register("oxidized_pink_chiseled_copper", new Block(AbstractBlock.Settings.create().strength(3, 6)));

    public static final Block EXPOSED_COPPER_VENT = register("exposed_copper_vent", new CopperVentBlock(FabricBlockSettings.copyOf(Blocks.COPPER_BLOCK)));

    public static Block register(String name, Block block) {
        var item = Registry.register(Registries.ITEM, new Identifier(Main.MOD_NAMESPACE, name), new BlockItem(block, new FabricItemSettings()));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(content -> {
            content.addAfter(Items.WAXED_OXIDIZED_CHISELED_COPPER, item);
        });
        ItemGroupEvents.modifyEntriesEvent(COBALT_GROUP_KEY).register(content -> {
            content.add(item);
        });
        return Registry.register(Registries.BLOCK, new Identifier(Main.MOD_NAMESPACE, name), block);
    }

    public static void register() {}

}
