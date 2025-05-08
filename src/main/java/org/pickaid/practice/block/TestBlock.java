package org.pickaid.practice.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.pickaid.practice.init.BlockEntities;

public class TestBlock extends BaseEntityBlock {
    private static final VoxelShape SHAPE = box(1.0D, 0.0D, 1.0D, 15.0D, 12.0D, 15.0D);

    public TestBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new TestEntityBlock(pPos, pState);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer,
                                 InteractionHand pHand, BlockHitResult pHit) {
        if (pHand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }

        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (!(blockEntity instanceof TestEntityBlock testEntity)) {
            return InteractionResult.PASS;
        }

        ItemStack heldItem = pPlayer.getItemInHand(pHand);
        ItemStack currentItem = testEntity.getDisplayItem();

        if (!heldItem.isEmpty()) {
            ItemStack displayCopy = heldItem.copy();
            displayCopy.setCount(1);
            testEntity.setDisplayItem(displayCopy);
            if (!pPlayer.getAbilities().instabuild) {
                heldItem.shrink(1);
            }

            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        else if (!currentItem.isEmpty()) {
            if (!pLevel.isClientSide) {
                pPlayer.addItem(currentItem.copy());
            }
            testEntity.setDisplayItem(ItemStack.EMPTY);

            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }

        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof TestEntityBlock testEntity) {
                ItemStack displayItem = testEntity.getDisplayItem();
                if (!displayItem.isEmpty() && !pLevel.isClientSide) {
                    popResource(pLevel, pPos, displayItem.copy());
                }
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return null;
    }
}