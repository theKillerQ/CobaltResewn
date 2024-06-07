package se.fusion1013.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtFloat;
import net.minecraft.nbt.NbtList;
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
    public static final String NBT_KEY_OFFSET = "offset";
    public static final String NBT_KEY_OFFSET_FREQUENCY = "offset_frequency";
    public static final String NBT_KEY_OFFSET_AMPLITUDE = "offset_amplitude";

    public static final String NBT_KEY_SCALE = "scale";
    public static final String NBT_KEY_SCALE_FREQUENCY = "scale_frequency";
    public static final String NBT_KEY_SCALE_AMPLITUDE = "scale_amplitude";

    public static final String NBT_KEY_ROTATION = "rotation";
    public static final String NBT_KEY_ROTATION_SPEED = "rotation_speed";

    private Vector3f offset = new Vector3f();
    private Vector3f offsetFrequency = new Vector3f();
    private Vector3f offsetAmplitude = new Vector3f();

    private Vector3f scale = new Vector3f();
    private Vector3f scaleFrequency = new Vector3f();
    private Vector3f scaleAmplitude = new Vector3f();

    private Vector3f rotation = new Vector3f();
    private Vector3f rotationSpeed = new Vector3f();

    public ItemDisplayBlockEntity(BlockPos pos, BlockState state) {
        super(CobaltBlockEntityTypes.ITEM_DISPLAY_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        offset = getVector(nbt, NBT_KEY_OFFSET);
        offsetAmplitude = getVector(nbt, NBT_KEY_OFFSET_AMPLITUDE);
        offsetFrequency = getVector(nbt, NBT_KEY_OFFSET_FREQUENCY);

        scale = getVector(nbt, NBT_KEY_SCALE);
        scaleAmplitude = getVector(nbt, NBT_KEY_SCALE_AMPLITUDE);
        scaleFrequency = getVector(nbt, NBT_KEY_SCALE_FREQUENCY);

        rotation = getVector(nbt, NBT_KEY_ROTATION);
        rotationSpeed = getVector(nbt, NBT_KEY_ROTATION_SPEED);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        putVector(offset, nbt, NBT_KEY_OFFSET);
        putVector(offsetAmplitude, nbt, NBT_KEY_OFFSET_AMPLITUDE);
        putVector(offsetFrequency, nbt, NBT_KEY_OFFSET_FREQUENCY);

        putVector(scale, nbt, NBT_KEY_SCALE);
        putVector(scaleAmplitude, nbt, NBT_KEY_SCALE_AMPLITUDE);
        putVector(scaleFrequency, nbt, NBT_KEY_SCALE_FREQUENCY);

        putVector(rotation, nbt, NBT_KEY_ROTATION);
        putVector(rotationSpeed, nbt, NBT_KEY_ROTATION_SPEED);
    }

    private static void putVector(Vector3f vec, NbtCompound nbt, String key) {
        NbtList list = new NbtList();

        list.add(0, NbtFloat.of(vec.x));
        list.add(1, NbtFloat.of(vec.y));
        list.add(2, NbtFloat.of(vec.z));

        nbt.put(key, list);
    }

    private static Vector3f getVector(NbtCompound nbt, String key) {
        var compound = nbt.getList(key, NbtList.FLOAT_TYPE);
        return new Vector3f(
            compound.getFloat(0),
            compound.getFloat(1),
            compound.getFloat(2)
        );
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
        buffer.writeVector3f(offset);
        buffer.writeVector3f(offsetFrequency);
        buffer.writeVector3f(offsetAmplitude);

        // Scale
        buffer.writeVector3f(scale);
        buffer.writeVector3f(scaleFrequency);
        buffer.writeVector3f(scaleAmplitude);

        // Rotation
        buffer.writeVector3f(rotation);
        buffer.writeVector3f(rotationSpeed);
    }

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
