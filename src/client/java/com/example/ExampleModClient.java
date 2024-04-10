package com.example;

import com.example.items.trinkets.TestHatTrinket;
import com.example.model.CobaltPredicateProviderRegister;
import com.example.screen.ImGuiTest;
import com.example.screen.ImguiScreen;
import com.example.screen.ItemEditorScreen;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import imgui.ImGui;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class ExampleModClient implements ClientModInitializer {

	private static KeyBinding toggleGuiKeybind;

	// public static final Item TEST_HAT = new TestHatTrinket(new Item.Settings());

	@Override
	public void onInitializeClient() {
		initializeKeybinds();
		registerItems();
	}

	private void registerItems() {
		// Registry.register(Registries.ITEM, new Identifier("cobalt", "test_hat"), TEST_HAT);
		// TrinketRendererRegistry.registerRenderer(TEST_HAT, (TrinketRenderer) TEST_HAT);
		CobaltPredicateProviderRegister.register();
	}

	private void initializeKeybinds() {
		toggleGuiKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.cobalt.togglegui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "key.cobalt.header"));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (toggleGuiKeybind.wasPressed()) {
				if (MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().currentScreen == null) {
					// Open item editor
					ItemEditorScreen screen = new ItemEditorScreen();
					screen.open();
				}
			}
		});
	}
}