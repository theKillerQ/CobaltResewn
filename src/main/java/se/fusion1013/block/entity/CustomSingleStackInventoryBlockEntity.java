package se.fusion1013.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import se.fusion1013.block.inventory.ImplementedInventory;

public class CustomSingleStackInventoryBlockEntity extends BlockEntity implements ImplementedInventory {

    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);

    public CustomSingleStackInventoryBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityType.DECORATED_POT, pos, state);
    }

    /// Save / Load Inventory

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, items);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt = Inventories.writeNbt(nbt, items);
        super.writeNbt(nbt);
    }

    ///

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }
}
