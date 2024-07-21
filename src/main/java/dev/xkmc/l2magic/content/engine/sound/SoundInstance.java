package dev.xkmc.l2magic.content.engine.sound;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2magic.content.engine.context.EngineContext;
import dev.xkmc.l2magic.content.engine.core.ConfiguredEngine;
import dev.xkmc.l2magic.content.engine.core.EngineType;
import dev.xkmc.l2magic.content.engine.variable.DoubleVariable;
import dev.xkmc.l2magic.init.registrate.EngineRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public record SoundInstance(
		SoundEvent sound,
		DoubleVariable volume,
		DoubleVariable pitch
) implements ConfiguredEngine<SoundInstance> {

	public static final MapCodec<SoundInstance> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			BuiltInRegistries.SOUND_EVENT.byNameCodec().fieldOf("sound").forGetter(e -> e.sound),
			DoubleVariable.optionalCodec("volume", e -> e.volume),
			DoubleVariable.optionalCodec("pitch", e -> e.pitch)
	).apply(i, (a, b, c) -> new SoundInstance(a,
			b.orElse(DoubleVariable.of("1")),
			c.orElse(DoubleVariable.of("1"))
	)));

	@Override
	public EngineType<SoundInstance> type() {
		return EngineRegistry.SOUND.get();
	}

	@Override
	public void execute(EngineContext ctx) {
		var pos = ctx.loc().pos();
		float vol = (float) volume.eval(ctx);
		float pit = (float) pitch.eval(ctx);
		ctx.user().level().playLocalSound(pos.x, pos.y, pos.z, sound, SoundSource.PLAYERS, vol, pit, false);
	}

}
