package se.fusion1013.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.LootableInventory;
import net.minecraft.inventory.SingleStackInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import se.fusion1013.block.CustomBlockRegistry;

public class CopperCrateBlockEntity extends BlockEntity implements LootableInventory, SingleStackInventory {

    private ItemStack stack;
    @Nullable
    protected Identifier lootTableId;
    protected long lootTableSeed;

    public CopperCrateBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityType.DECORATED_POT, pos, state);
        this.stack = ItemStack.EMPTY;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        if (!writeLootTable(nbt) && !stack.isEmpty()) {
            nbt.put("item", stack.writeNbt(new NbtCompound()));
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        if (!readLootTable(nbt)) {
            if (nbt.contains("item", 10)) {
                stack = ItemStack.fromNbt(nbt.getCompound("item"));
            } else {
                stack = ItemStack.EMPTY;
            }
        }
    }

    @Nullable
    @Override
    public Identifier getLootTableId() {
        return lootTableId;
    }

    @Override
    public void setLootTableId(@Nullable Identifier lootTableId) {
        this.lootTableId = lootTableId;
    }

    @Override
    public long getLootTableSeed() {
        return lootTableSeed;
    }

    @Override
    public void setLootTableSeed(long lootTableSeed) {
        this.lootTableSeed = lootTableSeed;
    }

    @Override
    public ItemStack getStack() {
        generateLoot(null);
        return this.stack;
    }

    @Override
    public ItemStack decreaseStack(int count) {
        this.generateLoot(null);
        ItemStack itemStack = this.stack.split(count);
        if (this.stack.isEmpty()) {
            this.stack = ItemStack.EMPTY;
        }
        return itemStack;
    }

    @Override
    public void setStack(ItemStack stack) {
        generateLoot(null);
        this.stack = stack;
    }

    @Override
    public BlockEntity asBlockEntity() {
        return this;
    }

    public ItemStack asStack() {
        ItemStack itemStack = CustomBlockRegistry.EXPOSED_COPPER_CRATE.asItem().getDefaultStack();
        return itemStack;
    }
}
