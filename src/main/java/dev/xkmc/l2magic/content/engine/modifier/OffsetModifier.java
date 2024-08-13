package dev.xkmc.l2magic.content.engine.modifier;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2magic.content.engine.context.EngineContext;
import dev.xkmc.l2magic.content.engine.context.LocationContext;
import dev.xkmc.l2magic.content.engine.core.Modifier;
import dev.xkmc.l2magic.content.engine.core.ModifierType;
import dev.xkmc.l2magic.content.engine.variable.DoubleVariable;
import dev.xkmc.l2magic.init.registrate.EngineRegistry;
import net.minecraft.world.phys.Vec3;

public record OffsetModifier(DoubleVariable x, DoubleVariable y, DoubleVariable z)
		implements Modifier<OffsetModifier> {

	public static final OffsetModifier ABOVE = of("0", "1", "0");
	public static final OffsetModifier BELOW = of("0", "-1", "0");

	public static MapCodec<OffsetModifier> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			DoubleVariable.optionalCodec("x", OffsetModifier::x),
			DoubleVariable.optionalCodec("y", OffsetModifier::y),
			DoubleVariable.optionalCodec("z", OffsetModifier::z)
	).apply(i, (x, y, z) -> new OffsetModifier(
			x.orElse(DoubleVariable.ZERO),
			y.orElse(DoubleVariable.ZERO),
			z.orElse(DoubleVariable.ZERO))));

	public static OffsetModifier of(String x, String y, String z) {
		return new OffsetModifier(
				DoubleVariable.of(x),
				DoubleVariable.of(y),
				DoubleVariable.of(z));
	}

	@Override
	public ModifierType<OffsetModifier> type() {
		return EngineRegistry.OFFSET.get();
	}

	@Override
	public LocationContext modify(EngineContext ctx) {
		return ctx.loc().add(new Vec3(
				x.eval(ctx), y.eval(ctx), z.eval(ctx)
		));
	}

}
