package org.pickaid.practice.block.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.pickaid.practice.block.TestEntityBlock;

public class TestBlockEntityRender implements BlockEntityRenderer<TestEntityBlock> {
    private final ItemRenderer itemRenderer;

    public TestBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = Minecraft.getInstance().getItemRenderer();
    }

    @Override
    public void render(TestEntityBlock blockEntity, float partialTick, @NotNull PoseStack poseStack,
                       @NotNull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemStack displayItem = blockEntity.getDisplayItem();

        if (displayItem.isEmpty()) {
            return;
        }

        poseStack.pushPose();

        poseStack.translate(0.5, 1.25, 0.5);

        long gameTime = blockEntity.getLevel().getGameTime();
        float time = gameTime + partialTick;

        float bobbing = Mth.sin(time / 20.0F) * 0.4F;
        poseStack.translate(0, bobbing, 0);


        float angleX = time / 30.0F * (float) Math.PI * 3.0F;
        float angleY = time / 40.0F * (float) Math.PI * 5.0F;
        float angleZ = time / 50.0F * (float) Math.PI * 7.0F;
        poseStack.mulPose(Axis.XP.rotation(angleX));
        poseStack.mulPose(Axis.YP.rotation(angleY));
        poseStack.mulPose(Axis.ZP.rotation(angleZ));

        float scale = 0.5F + Mth.sin(time / 15.0F) * 0.1F;
        poseStack.scale(scale, scale, scale);

        itemRenderer.renderStatic(displayItem, ItemDisplayContext.FIXED, packedLight,
                OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 0);

        poseStack.popPose();
    }
}