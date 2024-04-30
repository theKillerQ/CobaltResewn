package se.fusion1013.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.Identifier;

public class ToggleImageButton extends ImageButton {

    protected boolean state;

    public void setState(boolean state) {
        this.state = state;
    }

    public ToggleImageButton(int x, int y, Identifier texture, PressAction onPress, boolean state) {
        super(x, y, texture, onPress);
        this.state = state;
    }

    @Override
    protected void renderImage(DrawContext context, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

        if (state) context.drawTexture(texture, getX() + 2, getY() + 2, 16, 0, 16, 16, 32, 32);
        else context.drawTexture(texture, getX() + 2, getY() + 2, 0, 0, 16, 16, 32, 32);
    }

}
