package dev.xkmc.l2magic.content.engine.iterator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2magic.content.engine.context.EngineContext;
import dev.xkmc.l2magic.content.engine.context.LocationContext;
import dev.xkmc.l2magic.content.engine.core.ConfiguredEngine;
import dev.xkmc.l2magic.content.engine.core.EngineType;
import dev.xkmc.l2magic.content.engine.variable.DoubleVariable;
import dev.xkmc.l2magic.init.registrate.EngineRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public record BlockInRangeIterator(
		DoubleVariable range,
		DoubleVariable delayPerBlock,
		boolean plane,
		ConfiguredEngine<?> child,
		@Nullable String variable
) implements ConfiguredEngine<BlockInRangeIterator> {

	public static final MapCodec<BlockInRangeIterator> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			DoubleVariable.codec("range", BlockInRangeIterator::range),
			DoubleVariable.optionalCodec("delayPerBlock", BlockInRangeIterator::delayPerBlock),
			Codec.BOOL.optionalFieldOf("plane").forGetter(e -> Optional.of(e.plane)),
			ConfiguredEngine.codec("child", BlockInRangeIterator::child),
			Codec.STRING.optionalFieldOf("variable").forGetter(e -> Optional.ofNullable(e.variable))
	).apply(i, (a, b, p, c, d) -> new BlockInRangeIterator(
			a, b.orElse(DoubleVariable.ZERO), p.orElse(false), c, d.orElse(null))));

	@Override
	public EngineType<BlockInRangeIterator> type() {
		return EngineRegistry.BLOCK_IN_RANGE.get();
	}

	@Override
	public void execute(EngineContext ctx) {
		double rad = range.eval(ctx);
		double rate = delayPerBlock.eval(ctx);
		int step = (int) Math.ceil(rad);
		var p = ctx.loc().pos();
		BlockPos pos = BlockPos.containing(p);
		for (int x = -step; x <= step; x++) {
			for (int z = -step; z <= step; z++) {
				if (plane)
					onBlock(ctx, x, 0, z, pos, p, rad, rate);
				else for (int y = -step; y <= step; y++)
					onBlock(ctx, x, y, z, pos, p, rad, rate);
			}
		}
	}

	private void onBlock(EngineContext ctx, int x, int y, int z, BlockPos pos, Vec3 p, double rad, double rate) {
		var npos = pos.offset(x, y, z).getCenter();
		double dist = npos.distanceTo(p);
		if (dist > rad) return;
		int delay = (int) Math.round(rate * dist);
		var dir = dist < 0.5 ? ctx.loc().dir() : npos.subtract(p).normalize();
		double dx = npos.x - p.x;
		double dy = npos.y - p.y;
		double dz = npos.z - p.z;
		ctx.schedule(delay, () -> ctx.execute(
				LocationContext.of(npos, dir),
				Map.of(
						variable + "_x", dx,
						variable + "_y", dy,
						variable + "_z", dz,
						variable + "_r", dist
				),
				child
		));
	}

	@Override
	public Set<String> verificationParameters() {
		if (variable == null) return Set.of();
		return Set.of(variable + "_x", variable + "_y", variable + "_z", variable + "_r");
	}
}
