package dev.xkmc.l2magic.content.particle.core;

import com.mojang.serialization.MapCodec;
import dev.xkmc.l2magic.init.registrate.LMItems;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.Nullable;

public class LMGenericParticleOption implements ParticleOptions {

	public static final MapCodec<LMGenericParticleOption> CODEC = MapCodec.unit(LMGenericParticleOption::new);
	public static final StreamCodec<RegistryFriendlyByteBuf, LMGenericParticleOption> STREAM_CODEC = StreamCodec.unit(new LMGenericParticleOption());

	@Nullable
	private final ClientParticleData data;

	public LMGenericParticleOption(ClientParticleData data) {
		this.data = data;
	}

	public LMGenericParticleOption() {
		this.data = null;
	}

	@Override
	public ParticleType<?> getType() {
		return LMItems.GENERIC_PARTICLE.get();
	}

	public LMParticleData data() {
		return data == null ? ClientParticleData.DEFAULT : data;
	}

}
