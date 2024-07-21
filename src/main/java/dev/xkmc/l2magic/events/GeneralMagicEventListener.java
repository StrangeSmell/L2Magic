package dev.xkmc.l2magic.events;

import dev.xkmc.l2magic.init.L2Magic;
import dev.xkmc.l2magic.init.registrate.EngineRegistry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.TagsUpdatedEvent;

@EventBusSubscriber(modid = L2Magic.MODID, bus = EventBusSubscriber.Bus.GAME)
public class GeneralMagicEventListener {

	@SubscribeEvent
	public static void onReload(TagsUpdatedEvent event) {
		event.getRegistryAccess().registryOrThrow(EngineRegistry.PROJECTILE)
				.holders().forEach(e -> e.value().verify(e.key().location()));
		event.getRegistryAccess().registryOrThrow(EngineRegistry.SPELL)
				.holders().forEach(e -> e.value().verify(e.key().location()));
	}

}
