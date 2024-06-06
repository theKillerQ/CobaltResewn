package se.fusion1013.networking;

import net.minecraft.util.Identifier;

/**
 * All the networking constants used for cobalt.
 */
public class CobaltNetworkingConstants {

    // S2C
    public static final Identifier UPDATE_WF_STATUS_S2C = new Identifier("cobalt", "wf_facility_status_s2c");
    public static final Identifier UPDATE_WALKIETALKIE_S2C = new Identifier("cobalt", "update_walkietalkie_s2c");
    public static final Identifier OPEN_WALKIE_TALKIE_SCREEN_S2C = new Identifier("cobalt", "open_walkie_talkie_screen_s2c");
    public static final Identifier SET_FOG_VALUE_S2C = new Identifier("cobalt", "set_fog_value_s2c");
    public static final Identifier OPEN_ITEM_DISPLAY_SCREEN_S2C = new Identifier("cobalt", "open_item_display_screen_s2c");

    // C2S
    public static final Identifier ARMOR_SET_TOGGLE_ABILITY_C2S = new Identifier("cobalt", "armor_set_toggle_ability_c2s");
    public static final Identifier ARMOR_SET_TRIGGER_ABILITY_C2S = new Identifier("cobalt", "armor_set_trigger_ability_c2s");
    public static final Identifier UPDATE_WALKIETALKIE_C2S = new Identifier("cobalt", "update_walkietalkie_c2s");
    public static final Identifier UPDATE_SPEAKER_C2S = new Identifier("cobalt", "update_speaker_c2s");
    public static final Identifier UPDATE_ITEM_DISPLAY_C2S = new Identifier("cobalt", "update_item_display_c2s");
}
