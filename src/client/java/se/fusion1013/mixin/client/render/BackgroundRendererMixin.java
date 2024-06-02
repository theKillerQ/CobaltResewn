package se.fusion1013.mixin.client.render;

import com.google.common.collect.Lists;
import net.minecraft.client.render.BackgroundRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import se.fusion1013.render.effect.EnvironmentFogModifier;

import java.util.List;

@Mixin(targets = {"net.minecraft.client.render.BackgroundRenderer"})
public class BackgroundRendererMixin {

    @Shadow @Final private static List<BackgroundRenderer.StatusEffectFogModifier> FOG_MODIFIERS = Lists.newArrayList(new BackgroundRenderer.StatusEffectFogModifier[]{new EnvironmentFogModifier(), new BackgroundRenderer.DarknessFogModifier(), new BackgroundRenderer.BlindnessFogModifier()});

}
