package se.fusion1013.commands;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.block.entity.BlockEntityType;

import static net.minecraft.server.command.CommandManager.*;

/**
 * Handles registering custom {@link com.mojang.brigadier.Command}s.
 */
public class CobaltCommands {

    public static void register() {
        CommandRegistrationCallback.EVENT.register(FogCommand::register);
    }

}
