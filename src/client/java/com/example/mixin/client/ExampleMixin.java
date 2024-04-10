package com.example.mixin.client;

import com.example.ExampleMod;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class ExampleMixin {
    @Inject(at = @At("HEAD"), method = "init()V")
    private void init(CallbackInfo info) {
        // ExampleMod.LOGGER.info("This line is printed by an example mod mixin!");
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {

        /*
        TestScreen.draw(io -> {
            // Example on how to use a custom Font
            // ImGui.pushFont(ImGuiImpl.defaultFont);
            if (ImGui.begin("Hello World")) {
                ImGui.end();
            }

            ImGui.showDemoWindow();
            // ImGui.popFont();
        });

         */
    }
}
