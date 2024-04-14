package se.fusion1013.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import static se.fusion1013.Main.MOD_NAMESPACE;

public class CustomEntityRegistry {

    public static EntityType<LightningArrowEntity> LIGHTNING_ARROW;
    public static EntityType<ExplosiveArrowEntity> EXPLOSIVE_ARROW;

    public static void register() {
        LIGHTNING_ARROW = register("lightning_arrow", createArrowEntityType(LightningArrowEntity::new));
        EXPLOSIVE_ARROW = register("explosive_arrow", createArrowEntityType(ExplosiveArrowEntity::new));
    }

    private static <T extends Entity> EntityType<T> register(String s, EntityType<T> entityType) {
        return Registry.register(Registries.ENTITY_TYPE, MOD_NAMESPACE + ":" + s, entityType);
    }

    private static <T extends Entity> EntityType<T> createArrowEntityType(EntityType.EntityFactory<T> factory) {
        return FabricEntityTypeBuilder.create(SpawnGroup.MISC, factory).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).trackRangeBlocks(4).trackedUpdateRate(20).build();
    }

}
