package org.pickaid.practice;

import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.pickaid.practice.block.render.TestBlockEntityRender;
import org.pickaid.practice.init.BlockEntities;
import org.pickaid.practice.init.Blocks;

@Mod(Practice.MOD_ID)
public class Practice {
	public static final String MOD_ID = "practice";
	public static ResourceLocation id(String path)
	{
		return new ResourceLocation(MOD_ID, path);
	}

	public Practice() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addListener(this::onClientSetup);
		Blocks.register(bus);
		BlockEntities.register(bus);
	}

	private void onClientSetup(FMLClientSetupEvent event) {
		BlockEntityRendererRegistry.register(BlockEntities.TEST_BLOCK_ENTITY.get(),
				TestBlockEntityRender::new);
	}
}
