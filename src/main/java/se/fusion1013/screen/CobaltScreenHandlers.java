package se.fusion1013.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import se.fusion1013.Main;

public class CobaltScreenHandlers {

    public static ScreenHandlerType<ItemDisplayScreenHandler> ITEM_DISPLAY_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(ItemDisplayScreenHandler::new);

    static {
        ITEM_DISPLAY_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, new Identifier(Main.MOD_NAMESPACE, "item_display"), ITEM_DISPLAY_SCREEN_HANDLER);
    }

    public static void registerAll() {}

}
