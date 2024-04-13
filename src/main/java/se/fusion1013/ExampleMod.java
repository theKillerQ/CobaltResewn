package se.fusion1013;

import se.fusion1013.entity.CustomEntityRegistry;
import se.fusion1013.items.CustomItemRegistry;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("cobalt");
	public static final String MOD_NAMESPACE = "cobalt";

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");

		CustomItemRegistry.register();
		CustomEntityRegistry.register();

	}
}