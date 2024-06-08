package se.fusion1013.gui.widget;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;
import se.fusion1013.Main;

public class VectorWidget extends ClickableWidget {

    private TextFieldWidget xField;
    private TextFieldWidget yField;
    private TextFieldWidget zField;

    private boolean xSelected;
    private boolean ySelected;
    private boolean zSelected;

    private Vector3f vector;

    public VectorWidget(int x, int y, int width, int height, Text message, TextRenderer textRenderer, Vector3f vector) {
        super(x, y, width, height, message);
        this.vector = vector;

        this.xField = new TextFieldWidget(textRenderer, x, y, 40, 20, Text.translatable("vector_edit.x"));
        this.xField.setText(String.valueOf(vector.x));

        this.yField = new TextFieldWidget(textRenderer, x + 45, y, 40, 20, Text.translatable("vector_edit.y"));
        this.yField.setText(String.valueOf(vector.y));

        this.zField = new TextFieldWidget(textRenderer, x + 90, y, 40, 20, Text.translatable("vector_edit.z"));
        this.zField.setText(String.valueOf(vector.z));
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        this.xField.render(context, mouseX, mouseY, delta);
        this.yField.render(context, mouseX, mouseY, delta);
        this.zField.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        xSelected = this.xField.mouseClicked(mouseX, mouseY, 0);
        ySelected = this.yField.mouseClicked(mouseX, mouseY, 0);
        zSelected = this.zField.mouseClicked(mouseX, mouseY, 0);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        boolean handled = false;

        if (xSelected) handled = this.xField.keyPressed(keyCode, scanCode, modifiers);
        if (ySelected) handled = this.yField.keyPressed(keyCode, scanCode, modifiers) || handled;
        if (zSelected) handled = this.zField.keyPressed(keyCode, scanCode, modifiers) || handled;
        return handled;
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        var handled = false;
        if (xSelected) handled = this.xField.charTyped(chr, modifiers);
        if (ySelected) handled = this.yField.charTyped(chr, modifiers) || handled;
        if (zSelected) handled = this.zField.charTyped(chr, modifiers) || handled;
        return handled;
    }

    @Override
    public void setFocused(boolean focused) {
        super.setFocused(focused);
        this.xField.setFocused(xSelected);
        this.yField.setFocused(ySelected);
        this.zField.setFocused(zSelected);
    }

    public Vector3f getVector() {
        try {
            float x = Float.parseFloat(this.xField.getText());
            float y = Float.parseFloat(this.yField.getText());
            float z = Float.parseFloat(this.zField.getText());
            return new Vector3f(x, y, z);
        } catch (NumberFormatException e) {
            return vector;
        }
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}
