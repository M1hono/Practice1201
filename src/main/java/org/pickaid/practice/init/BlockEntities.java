package org.pickaid.practice.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.pickaid.practice.Practice;
import org.pickaid.practice.block.TestEntityBlock;

public class BlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Practice.MOD_ID);

    public static final RegistryObject<BlockEntityType<TestEntityBlock>> TEST_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("test_block_entity",
                    () -> BlockEntityType.Builder.of(TestEntityBlock::new
                            , Blocks.TEST_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}