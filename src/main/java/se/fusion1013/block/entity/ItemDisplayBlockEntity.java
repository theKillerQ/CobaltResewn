package se.fusion1013.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import se.fusion1013.Main;
import se.fusion1013.screen.ItemDisplayScreenHandler;

public class ItemDisplayBlockEntity extends CustomSingleStackInventoryBlockEntity implements ExtendedScreenHandlerFactory {

    // Offset
    public static final String NBT_KEY_OFFSET_X = "offset_x";
    public static final String NBT_KEY_OFFSET_Y = "offset_y";
    public static final String NBT_KEY_OFFSET_Z = "offset_z";

    public static final String NBT_KEY_OFFSET_X_FREQUENCY = "offset_x_frequency";
    public static final String NBT_KEY_OFFSET_Y_FREQUENCY = "offset_y_frequency";
    public static final String NBT_KEY_OFFSET_Z_FREQUENCY = "offset_z_frequency";

    public static final String NBT_KEY_OFFSET_X_AMPLITUDE = "offset_x_amplitude";
    public static final String NBT_KEY_OFFSET_Y_AMPLITUDE = "offset_y_amplitude";
    public static final String NBT_KEY_OFFSET_Z_AMPLITUDE = "offset_z_amplitude";


    public static final String NBT_KEY_SCALE_X = "scale_x";
    public static final String NBT_KEY_SCALE_Y = "scale_y";
    public static final String NBT_KEY_SCALE_Z = "scale_z";

    public static final String NBT_KEY_SCALE_X_FREQUENCY = "scale_x_frequency";
    public static final String NBT_KEY_SCALE_Y_FREQUENCY = "scale_y_frequency";
    public static final String NBT_KEY_SCALE_Z_FREQUENCY = "scale_z_frequency";

    public static final String NBT_KEY_SCALE_X_AMPLITUDE = "scale_x_amplitude";
    public static final String NBT_KEY_SCALE_Y_AMPLITUDE = "scale_y_amplitude";
    public static final String NBT_KEY_SCALE_Z_AMPLITUDE = "scale_z_amplitude";


    public static final String NBT_KEY_ROTATION_X = "rotation_x";
    public static final String NBT_KEY_ROTATION_Y = "rotation_y";
    public static final String NBT_KEY_ROTATION_Z = "rotation_z";

    public static final String NBT_KEY_ROTATION_X_SPEED = "rotation_x_speed";
    public static final String NBT_KEY_ROTATION_Y_SPEED = "rotation_y_speed";
    public static final String NBT_KEY_ROTATION_Z_SPEED = "rotation_z_speed";


    private float xOffset;
    private float yOffset;
    private float zOffset;

    private float xOffsetFrequency;
    private float yOffsetFrequency;
    private float zOffsetFrequency;

    private float xOffsetAmplitude;
    private float yOffsetAmplitude;
    private float zOffsetAmplitude;


    private float xScale = 1;
    private float yScale = 1;
    private float zScale = 1;

    private float xScaleFrequency;
    private float yScaleFrequency;
    private float zScaleFrequency;

    private float xScaleAmplitude;
    private float yScaleAmplitude;
    private float zScaleAmplitude;


    private float xRotation;
    private float yRotation;
    private float zRotation;

    private float xRotationSpeed;
    private float yRotationSpeed;
    private float zRotationSpeed;

    public ItemDisplayBlockEntity(BlockPos pos, BlockState state) {
        super(CobaltBlockEntityTypes.ITEM_DISPLAY_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        xOffset = nbt.getFloat(NBT_KEY_OFFSET_X);
        yOffset = nbt.getFloat(NBT_KEY_OFFSET_Y);
        zOffset = nbt.getFloat(NBT_KEY_OFFSET_Z);

        xOffsetFrequency = nbt.getFloat(NBT_KEY_OFFSET_X_FREQUENCY);
        yOffsetFrequency = nbt.getFloat(NBT_KEY_OFFSET_Y_FREQUENCY);
        zOffsetFrequency = nbt.getFloat(NBT_KEY_OFFSET_Z_FREQUENCY);

        xOffsetAmplitude = nbt.getFloat(NBT_KEY_OFFSET_X_AMPLITUDE);
        yOffsetAmplitude = nbt.getFloat(NBT_KEY_OFFSET_Y_AMPLITUDE);
        zOffsetAmplitude = nbt.getFloat(NBT_KEY_OFFSET_Z_AMPLITUDE);


        xScale = nbt.getFloat(NBT_KEY_SCALE_X);
        yScale = nbt.getFloat(NBT_KEY_SCALE_Y);
        zScale = nbt.getFloat(NBT_KEY_SCALE_Z);

        xScaleFrequency = nbt.getFloat(NBT_KEY_SCALE_X_FREQUENCY);
        yScaleFrequency = nbt.getFloat(NBT_KEY_SCALE_Y_FREQUENCY);
        zScaleFrequency = nbt.getFloat(NBT_KEY_SCALE_Z_FREQUENCY);

        xScaleAmplitude = nbt.getFloat(NBT_KEY_SCALE_X_AMPLITUDE);
        yScaleAmplitude = nbt.getFloat(NBT_KEY_SCALE_Y_AMPLITUDE);
        zScaleAmplitude = nbt.getFloat(NBT_KEY_SCALE_Z_AMPLITUDE);


        xRotation = nbt.getFloat(NBT_KEY_ROTATION_X);
        yRotation = nbt.getFloat(NBT_KEY_ROTATION_Y);
        zRotation = nbt.getFloat(NBT_KEY_ROTATION_Z);

        xRotationSpeed = nbt.getFloat(NBT_KEY_ROTATION_X_SPEED);
        yRotationSpeed = nbt.getFloat(NBT_KEY_ROTATION_Y_SPEED);
        zRotationSpeed = nbt.getFloat(NBT_KEY_ROTATION_Z_SPEED);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putFloat(NBT_KEY_OFFSET_X, xOffset);
        nbt.putFloat(NBT_KEY_OFFSET_Y, yOffset);
        nbt.putFloat(NBT_KEY_OFFSET_Z, zOffset);

        nbt.putFloat(NBT_KEY_OFFSET_X_FREQUENCY, xOffsetFrequency);
        nbt.putFloat(NBT_KEY_OFFSET_Y_FREQUENCY, yOffsetFrequency);
        nbt.putFloat(NBT_KEY_OFFSET_Z_FREQUENCY, zOffsetFrequency);

        nbt.putFloat(NBT_KEY_OFFSET_X_AMPLITUDE, xOffsetAmplitude);
        nbt.putFloat(NBT_KEY_OFFSET_Y_AMPLITUDE, yOffsetAmplitude);
        nbt.putFloat(NBT_KEY_OFFSET_Z_AMPLITUDE, zOffsetAmplitude);


        nbt.putFloat(NBT_KEY_SCALE_X, xScale);
        nbt.putFloat(NBT_KEY_SCALE_Y, yScale);
        nbt.putFloat(NBT_KEY_SCALE_Z, zScale);

        nbt.putFloat(NBT_KEY_SCALE_X_FREQUENCY, xScaleFrequency);
        nbt.putFloat(NBT_KEY_SCALE_Y_FREQUENCY, yScaleFrequency);
        nbt.putFloat(NBT_KEY_SCALE_Z_FREQUENCY, zScaleFrequency);

        nbt.putFloat(NBT_KEY_SCALE_X_AMPLITUDE, xScaleAmplitude);
        nbt.putFloat(NBT_KEY_SCALE_Y_AMPLITUDE, yScaleAmplitude);
        nbt.putFloat(NBT_KEY_SCALE_Z_AMPLITUDE, zScaleAmplitude);


        nbt.putFloat(NBT_KEY_ROTATION_X, xRotation);
        nbt.putFloat(NBT_KEY_ROTATION_Y, yRotation);
        nbt.putFloat(NBT_KEY_ROTATION_Z, zRotation);

        nbt.putFloat(NBT_KEY_ROTATION_X_SPEED, xRotationSpeed);
        nbt.putFloat(NBT_KEY_ROTATION_Y_SPEED, yRotationSpeed);
        nbt.putFloat(NBT_KEY_ROTATION_Z_SPEED, zRotationSpeed);
    }

    public float getxOffset() {
        return xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

    public float getzOffset() {
        return zOffset;
    }

    public float getxOffsetFrequency() {
        return xOffsetFrequency;
    }

    public float getyOffsetFrequency() {
        return yOffsetFrequency;
    }

    public float getzOffsetFrequency() {
        return zOffsetFrequency;
    }

    public float getxOffsetAmplitude() {
        return xOffsetAmplitude;
    }

    public float getyOffsetAmplitude() {
        return yOffsetAmplitude;
    }

    public float getzOffsetAmplitude() {
        return zOffsetAmplitude;
    }

    public float getxScale() {
        return xScale;
    }

    public float getyScale() {
        return yScale;
    }

    public float getzScale() {
        return zScale;
    }

    public float getxScaleFrequency() {
        return xScaleFrequency;
    }

    public float getyScaleFrequency() {
        return yScaleFrequency;
    }

    public float getzScaleFrequency() {
        return zScaleFrequency;
    }

    public float getxScaleAmplitude() {
        return xScaleAmplitude;
    }

    public float getyScaleAmplitude() {
        return yScaleAmplitude;
    }

    public float getzScaleAmplitude() {
        return zScaleAmplitude;
    }

    public float getxRotation() {
        return xRotation;
    }

    public float getyRotation() {
        return yRotation;
    }

    public float getzRotation() {
        return zRotation;
    }

    public float getxRotationSpeed() {
        return xRotationSpeed;
    }

    public float getyRotationSpeed() {
        return yRotationSpeed;
    }

    public float getzRotationSpeed() {
        return zRotationSpeed;
    }

    // These methods are from the NamedScreenHandlerFactory interface
    // createMenu creates the ScreenHandler itself
    // getDisplayName will provide its name which is normally shown at the top

    @Override
    public Text getDisplayName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ItemDisplayScreenHandler(syncId, playerInventory);
    }

    // This method gets called on the server when it requests the client to open the screenHandler.
    // The contents you write into the packetByteBuf will automatically be transferred in a packet to the client
    // and the ScreenHandler constructor with the packetByteBuf argument gets called on the client.
    //
    // The order you insert things here is the same as you need to extract them. You do not need to reverse the order.
    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buffer) {
        // Offset
        buffer.writeVector3f(new Vector3f(xOffset, yOffset, zOffset));
        buffer.writeVector3f(new Vector3f(xOffsetFrequency, yOffsetFrequency, zOffsetFrequency));
        buffer.writeVector3f(new Vector3f(xOffsetAmplitude, yOffsetAmplitude, zOffsetAmplitude));

        // Scale
        buffer.writeVector3f(new Vector3f(xScale, yScale, zScale));
        buffer.writeVector3f(new Vector3f(xScaleFrequency, yScaleFrequency, zScaleFrequency));
        buffer.writeVector3f(new Vector3f(xScaleAmplitude, yScaleAmplitude, zScaleAmplitude));

        // Rotation
        buffer.writeVector3f(new Vector3f(xRotation, yRotation, zRotation));
        buffer.writeVector3f(new Vector3f(xRotationSpeed, yRotationSpeed, zRotationSpeed));
    }
}
