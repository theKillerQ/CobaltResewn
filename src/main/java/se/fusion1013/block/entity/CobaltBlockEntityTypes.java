package se.fusion1013.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import se.fusion1013.Main;
import se.fusion1013.block.CobaltBlocks;

public class CobaltBlockEntityTypes {

    public static final BlockEntityType<CustomSingleStackInventoryBlockEntity>    EXPOSED_COPPER_CRATE;
    public static final BlockEntityType<CustomSingleStackInventoryBlockEntity>    ANCIENT_POT_1_BLOCK_ENTITY;
    public static final BlockEntityType<CustomSingleStackInventoryBlockEntity>    ANCIENT_POT_2_BLOCK_ENTITY;
    public static final BlockEntityType<CustomSingleStackInventoryBlockEntity>    ANCIENT_POT_3_BLOCK_ENTITY;
    public static final BlockEntityType<CustomSingleStackInventoryBlockEntity>    ANCIENT_POT_4_BLOCK_ENTITY;
    public static final BlockEntityType<SculkSpreaderBlockEntity>                 SCULK_SPREADER;
    public static final BlockEntityType<SpeakerBlockEntity>                       SPEAKER;
    public static final BlockEntityType<ParticleBlockEntity>                      PARTICLE_BLOCK;
    public static final BlockEntityType<RuneBlockEntity>                          RUNE_BLOCK;
    public static final BlockEntityType<ForgeSideCrystalBlockEntity>              FORGE_SIDE_CRYSTAL_BLOCK;

    private static <T extends BlockEntity> BlockEntityType<T> register(String id, FabricBlockEntityTypeBuilder<T> builder) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_NAMESPACE, id), builder.build());
    }

    public static void registerAll() {}

    static {
        EXPOSED_COPPER_CRATE = register("exposed_copper_crate_block_entity", FabricBlockEntityTypeBuilder.create(CustomSingleStackInventoryBlockEntity::new, CobaltBlocks.EXPOSED_COPPER_CRATE));
        ANCIENT_POT_1_BLOCK_ENTITY = register("ancient_pot_1_block_entity", FabricBlockEntityTypeBuilder.create(CustomSingleStackInventoryBlockEntity::new, CobaltBlocks.ANCIENT_POT_1));
        ANCIENT_POT_2_BLOCK_ENTITY = register("ancient_pot_2_block_entity", FabricBlockEntityTypeBuilder.create(CustomSingleStackInventoryBlockEntity::new, CobaltBlocks.ANCIENT_POT_2));
        ANCIENT_POT_3_BLOCK_ENTITY = register("ancient_pot_3_block_entity", FabricBlockEntityTypeBuilder.create(CustomSingleStackInventoryBlockEntity::new, CobaltBlocks.ANCIENT_POT_3));
        ANCIENT_POT_4_BLOCK_ENTITY = register("ancient_pot_4_block_entity", FabricBlockEntityTypeBuilder.create(CustomSingleStackInventoryBlockEntity::new, CobaltBlocks.ANCIENT_POT_4_TOP));
        SCULK_SPREADER = register("sculk_spreader_block_entity", FabricBlockEntityTypeBuilder.create(SculkSpreaderBlockEntity::new, CobaltBlocks.SCULK_SPREADER));
        SPEAKER = register("speaker_block_entity", FabricBlockEntityTypeBuilder.create(SpeakerBlockEntity::new, CobaltBlocks.OXIDIZED_COPPER_SPEAKER, CobaltBlocks.EXPOSED_COPPER_SPEAKER, CobaltBlocks.WEATHERED_COPPER_SPEAKER, CobaltBlocks.COPPER_SPEAKER));
        PARTICLE_BLOCK = register("particle_block_entity", FabricBlockEntityTypeBuilder.create(ParticleBlockEntity::new, CobaltBlocks.PARTICLE_COMMAND_BLOCK));
        RUNE_BLOCK = register("rune_block_entity", FabricBlockEntityTypeBuilder.create(RuneBlockEntity::new, CobaltBlocks.RUNE_BLOCK));
        FORGE_SIDE_CRYSTAL_BLOCK = register("forge_side_crystal_block_entity", FabricBlockEntityTypeBuilder.create(ForgeSideCrystalBlockEntity::new, CobaltBlocks.FORGE_SIDE_CRYSTAL_BLOCK));
    }

}
