package org.pickaid.practice.block;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.pickaid.practice.init.BlockEntities;

public class TestEntityBlock extends BlockEntity {
    private ItemStack displayItem = ItemStack.EMPTY;

    public TestEntityBlock(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntities.TEST_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    public ItemStack getDisplayItem() {
        return displayItem;
    }

    public void setDisplayItem(ItemStack item) {
        this.displayItem = item.copy();
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("DisplayItem")) {
            displayItem = ItemStack.of(tag.getCompound("DisplayItem"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (!displayItem.isEmpty()) {
            CompoundTag itemTag = new CompoundTag();
            displayItem.save(itemTag);
            tag.put("DisplayItem", itemTag);
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }
}