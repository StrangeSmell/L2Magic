package dev.xkmc.l2magic.content.entity.engine;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2magic.content.engine.context.EngineContext;
import dev.xkmc.l2magic.content.engine.core.EngineType;
import dev.xkmc.l2magic.content.engine.variable.DoubleVariable;
import dev.xkmc.l2magic.init.registrate.EngineRegistry;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.Items;

public record TridentShoot(
		DoubleVariable speed
) implements AbstractArrowShoot<TridentShoot> {

	public static final MapCodec<TridentShoot> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			DoubleVariable.optionalCodec("speed", e -> e.speed)
	).apply(i, (s) -> new TridentShoot(s.orElse(DoubleVariable.of("3")))));

	@Override
	public EngineType<TridentShoot> type() {
		return EngineRegistry.TRIDENT.get();
	}

	@Override
	public AbstractArrow arrow(EngineContext ctx) {
		return new ThrownTrident(ctx.user().level(), ctx.user().user(), Items.TRIDENT.getDefaultInstance());
	}

}
