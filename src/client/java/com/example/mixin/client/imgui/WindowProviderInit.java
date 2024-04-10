package com.example.mixin.client.imgui;

import com.example.screen.ImguiScreen;
import net.minecraft.client.WindowSettings;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.WindowProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WindowProvider.class)
public class WindowProviderInit {
    @Inject(at = @At("TAIL"), method = "createWindow")
    public void onCreateWindow(WindowSettings settings, String videoMode, String title, CallbackInfoReturnable<Window> cir) {
        Window window = cir.getReturnValue();
        ImguiScreen.create(window);
    }
}
