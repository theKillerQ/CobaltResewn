package se.fusion1013.commands;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

import static net.minecraft.server.command.CommandManager.*;

public class CobaltCommands {

    public static void register() {
        CommandRegistrationCallback.EVENT.register(FogCommand::register);
    }

}
