package se.fusion1013;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import org.lwjgl.glfw.GLFW;
import se.fusion1013.block.CobaltBlocks;
import se.fusion1013.entity.CustomEntityRegistry;
import se.fusion1013.items.trinkets.BackpackItem;
import se.fusion1013.model.CobaltPredicateProviderRegister;
import se.fusion1013.networking.CobaltClientNetworking;
import se.fusion1013.networking.CobaltNetworkingConstants;
import se.fusion1013.render.entity.CorruptedCoreEntityModel;
import se.fusion1013.render.entity.CorruptedCoreEntityRenderer;
import se.fusion1013.render.entity.ExplosiveArrowEntityRenderer;
import se.fusion1013.render.entity.LightningArrowEntityRenderer;
import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static se.fusion1013.networking.CobaltNetworkingConstants.*;

public class MainClient implements ClientModInitializer {

	private static KeyBinding armorTriggerKeyBinding;
	private static KeyBinding armorToggleKeyBinding;

	public static final Item BACKPACK = new BackpackItem(new FabricItemSettings());

	public static final EntityModelLayer MODEL_CORRUPTED_CORE_LAYER = new EntityModelLayer(new Identifier("cobalt", "corrupted_core"), "main");

	@Override
	public void onInitializeClient() {
		initializeKeybinds();
		registerItems();

		// Entity rendering
		EntityRendererRegistry.register(CustomEntityRegistry.LIGHTNING_ARROW, LightningArrowEntityRenderer::new);
		EntityRendererRegistry.register(CustomEntityRegistry.EXPLOSIVE_ARROW, ExplosiveArrowEntityRenderer::new);

		EntityRendererRegistry.register(CustomEntityRegistry.CORRUPTED_CORE, CorruptedCoreEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(MODEL_CORRUPTED_CORE_LAYER, CorruptedCoreEntityModel::getTexturedModelData);

		// Block rendering
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
				CobaltBlocks.SCULK_GRASS,
				CobaltBlocks.SHORT_SCULK_GRASS
		);

		// Networking
		CobaltClientNetworking.register();
	}

	private void registerItems() {
		Registry.register(Registries.ITEM, new Identifier("cobalt", "backpack"), BACKPACK);
		TrinketRendererRegistry.registerRenderer(BACKPACK, (TrinketRenderer) BACKPACK);
		CobaltPredicateProviderRegister.register();
	}

	private void initializeKeybinds() {
		armorTriggerKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.cobalt.armor_trigger",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_X,
				"category.cobalt.main"
		));
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (armorTriggerKeyBinding.wasPressed()) {
				if (client.player == null) continue;

				PacketByteBuf buf = PacketByteBufs.create();
				ClientPlayNetworking.send(KEY_ARMOR_TRIGGER_ID_C2S, buf);
			}
		});

		armorToggleKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.cobalt.armor_toggle",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_G,
				"category.cobalt.main"
		));
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (armorToggleKeyBinding.wasPressed()) {
				if (client.player == null) continue;

				PacketByteBuf buf = PacketByteBufs.create();
				ClientPlayNetworking.send(CobaltNetworkingConstants.KEY_ARMOR_TOGGLE_ID, buf);
			}
		});
	}
}