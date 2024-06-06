package se.fusion1013.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import se.fusion1013.Main;
import se.fusion1013.block.entity.CobaltBlockEntityTypes;
import se.fusion1013.block.entity.ItemDisplayBlockEntity;

public class ItemDisplayScreenHandler extends ScreenHandler {

    private Vector3f offset;
    private Vector3f offsetFrequency;
    private Vector3f offsetAmplitude;

    private Vector3f scale;
    private Vector3f scaleFrequency;
    private Vector3f scaleAmplitude;

    private Vector3f rotation;
    private Vector3f rotationSpeed;

    public ItemDisplayScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buffer) {
        this(syncId, playerInventory);

        // Load data from buffer
        offset = buffer.readVector3f();
        offsetFrequency = buffer.readVector3f();
        offsetAmplitude = buffer.readVector3f();
        scale = buffer.readVector3f();
        scaleFrequency = buffer.readVector3f();
        scaleAmplitude = buffer.readVector3f();
        rotation = buffer.readVector3f();
        rotationSpeed = buffer.readVector3f();
    }

    public ItemDisplayScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(CobaltScreenHandlers.ITEM_DISPLAY_SCREEN_HANDLER, syncId);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    // Getters

    public Vector3f getOffset() {
        return offset;
    }

    public Vector3f getOffsetFrequency() {
        return offsetFrequency;
    }

    public Vector3f getOffsetAmplitude() {
        return offsetAmplitude;
    }

    public Vector3f getScale() {
        return scale;
    }

    public Vector3f getScaleFrequency() {
        return scaleFrequency;
    }

    public Vector3f getScaleAmplitude() {
        return scaleAmplitude;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getRotationSpeed() {
        return rotationSpeed;
    }
}
