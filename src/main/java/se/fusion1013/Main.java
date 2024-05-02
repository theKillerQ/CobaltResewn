package se.fusion1013;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import se.fusion1013.block.CobaltBlocks;
import se.fusion1013.block.entity.CobaltBlockEntityTypes;
import se.fusion1013.effect.CobaltEffects;
import se.fusion1013.entity.CustomEntityRegistry;
import se.fusion1013.items.CustomItemGroupRegistry;
import se.fusion1013.items.CobaltItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.fusion1013.networking.CobaltServerNetworking;

public class Main implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("cobalt");
	public static final String MOD_NAMESPACE = "cobalt";
	public static MinecraftServer server;

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");

		ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStart);

		CobaltItems.register();
		CustomEntityRegistry.register();
		CobaltBlocks.register();
		CustomItemGroupRegistry.register();
		CobaltBlockEntityTypes.registerAll();
		CobaltEffects.registerAll();
		CobaltServerNetworking.register();
	}

	private void onServerStart(MinecraftServer server) {
		Main.server = server;
	}
}