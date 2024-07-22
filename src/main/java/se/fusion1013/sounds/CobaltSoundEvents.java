package se.fusion1013.sounds;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import se.fusion1013.Main;

public class CobaltSoundEvents {

    public static final SoundEvent LIGHT_HOLDER_INSERT = register("block.light_holder.insert");
    public static final SoundEvent LIGHT_HOLDER_REMOVE = register("block.light_holder.remove");

    public static SoundEvent register(String id) {
        Identifier identifier = new Identifier(Main.MOD_NAMESPACE + ":" + id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    public static void register() {}
}
