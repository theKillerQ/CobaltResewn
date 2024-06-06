package se.fusion1013.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.world.EditGameRulesScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import se.fusion1013.Main;
import se.fusion1013.networking.CobaltNetworkingConstants;
import se.fusion1013.screen.ItemDisplayScreenHandler;

import java.util.Arrays;

@Environment(EnvType.CLIENT)
public class ItemDisplayScreen extends HandledScreen<ItemDisplayScreenHandler> {

    // GUI components
    public ButtonWidget button1;
    public ButtonWidget button2;

    public TextFieldWidget textFieldWidget;

    public ItemDisplayScreen(ItemDisplayScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {

        button1 = ButtonWidget.builder(Text.literal("Button 1"), button -> {
            Main.LOGGER.info("You clicked button 1!");
        })
                .dimensions(width / 2 - 205, 20, 200, 20)
                .tooltip(Tooltip.of(Text.literal("Tooltip of button 1")))
                .build();
        button2 = ButtonWidget.builder(Text.literal("Button 2"), button -> {
            Main.LOGGER.info("You clicked button 2!");
        })
                .dimensions(width / 2 + 5, 20, 200, 20)
                .tooltip(Tooltip.of(Text.literal("Tooltip of button 2")))
                .build();
        textFieldWidget = new TextFieldWidget(textRenderer, width / 2 - 205, 40, 200, 20, Text.literal("Test"));
        textFieldWidget.setChangedListener(s -> {
            var value = Float.parseFloat(s);
        });

        addDrawableChild(button1);
        addDrawableChild(button2);
        addDrawableChild(textFieldWidget);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        // TODO
    }

    private void sendUpdateItemDisplayBlock(int index, float value) {
        PacketByteBuf buffer = new PacketByteBuf(Unpooled.buffer());
        buffer.writeInt(index);
        buffer.writeFloat(value);
        // buffer.writeBlockPos(blockPos);
        ClientPlayNetworking.send(CobaltNetworkingConstants.UPDATE_ITEM_DISPLAY_C2S, buffer);
    }
}
