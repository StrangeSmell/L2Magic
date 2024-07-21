package dev.xkmc.l2magic.content.engine.helper;

import com.mojang.serialization.Codec;
import com.tterrag.registrate.util.OneTimeEventReceiver;
import dev.xkmc.l2magic.init.L2Magic;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public record EngineRegistryInstance<T>(ResourceKey<Registry<T>> key, Supplier<Registry<T>> registry) {

	public static <E> EngineRegistryInstance<E> of(String id) {
		ResourceKey<Registry<E>> key = ResourceKey.createRegistryKey(L2Magic.loc(id));
		RegistryBuilder<E> ans = new RegistryBuilder<>(key);
		Registry<E> reg = ans.create();
		OneTimeEventReceiver.addModListener(L2Magic.REGISTRATE, NewRegistryEvent.class, (e) -> e.register(reg));
		return new EngineRegistryInstance<>(key, () -> reg);
	}

	public Codec<T> codec() {
		return registry.get().byNameCodec();
	}

}
