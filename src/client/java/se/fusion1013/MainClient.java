package se.fusion1013;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import se.fusion1013.entity.CustomEntityRegistry;
import se.fusion1013.items.trinkets.BackpackItem;
import se.fusion1013.model.CobaltPredicateProviderRegister;
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
import se.fusion1013.util.FacilityStatus;

public class MainClient implements ClientModInitializer {

	private static KeyBinding toggleGuiKeybind;

	public static final Item BACKPACK = new BackpackItem(new FabricItemSettings());

	public static final EntityModelLayer MODEL_CORRUPTED_CORE_LAYER = new EntityModelLayer(new Identifier("cobalt", "corrupted_core"), "main");

	@Override
	public void onInitializeClient() {
		initializeKeybinds();
		registerItems();

		EntityRendererRegistry.register(CustomEntityRegistry.LIGHTNING_ARROW, LightningArrowEntityRenderer::new);
		EntityRendererRegistry.register(CustomEntityRegistry.EXPLOSIVE_ARROW, ExplosiveArrowEntityRenderer::new);

		EntityRendererRegistry.register(CustomEntityRegistry.CORRUPTED_CORE, CorruptedCoreEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(MODEL_CORRUPTED_CORE_LAYER, CorruptedCoreEntityModel::getTexturedModelData);

		ClientPlayNetworking.registerGlobalReceiver(CobaltNetworkingConstants.WF_FACILITY_STATUS_PACKET_ID, (client, handler, buf, responseSender) -> {
			client.execute(() -> {
				FacilityStatus.POWER_CURRENT = buf.readInt();
				FacilityStatus.PRESSURE_CURRENT = buf.readInt();
			});
		});
	}

	private void registerItems() {
		Registry.register(Registries.ITEM, new Identifier("cobalt", "backpack"), BACKPACK);
		TrinketRendererRegistry.registerRenderer(BACKPACK, (TrinketRenderer) BACKPACK);
		CobaltPredicateProviderRegister.register();
	}

	private void initializeKeybinds() {
	}
}