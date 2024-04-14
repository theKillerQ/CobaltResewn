package se.fusion1013;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import se.fusion1013.entity.CustomEntityRegistry;
import se.fusion1013.items.CustomItemRegistry;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("cobalt");
	public static final String MOD_NAMESPACE = "cobalt";
	public static MinecraftServer server;

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");

		ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStart);

		CustomItemRegistry.register();
		CustomEntityRegistry.register();

	}

	private void onServerStart(MinecraftServer server) {
		ExampleMod.server = server;
	}
}