package dev.xkmc.l2magic.content.engine.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2magic.content.engine.context.EngineContext;
import dev.xkmc.l2magic.content.engine.core.ConfiguredEngine;
import dev.xkmc.l2magic.content.engine.core.EngineType;
import dev.xkmc.l2magic.content.engine.variable.DoubleVariable;
import dev.xkmc.l2magic.init.registrate.EngineRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.FallingBlockEntity;

public record KnockBlock(
		DoubleVariable speed,
		DoubleVariable damagePerBlock,
		DoubleVariable maxDamage
) implements IBlockProcessor<KnockBlock> {

	public static final MapCodec<KnockBlock> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			DoubleVariable.codec("speed", KnockBlock::speed),
			DoubleVariable.optionalCodec("damagePerBlock", KnockBlock::damagePerBlock),
			DoubleVariable.optionalCodec("maxDamage", KnockBlock::maxDamage)
	).apply(i, (a, b, c) -> new KnockBlock(a, b.orElse(DoubleVariable.ZERO), c.orElse(b.orElse(DoubleVariable.ZERO)))));

	@Override
	public EngineType<KnockBlock> type() {
		return EngineRegistry.KNOCK_BLOCK.get();
	}

	@Override
	public void execute(EngineContext ctx) {
		var level = ctx.user().level();
		var pos = BlockPos.containing(ctx.loc().pos());
		var state = level.getBlockState(pos);
		if (state.canBeReplaced()) return;
		FallingBlockEntity e = FallingBlockEntity.fall(level, pos, state);
		e.setDeltaMovement(0, speed.eval(ctx), 0);
		e.hasImpulse = true;
		e.setHurtsEntities((float) damagePerBlock().eval(ctx), (int) maxDamage().eval(ctx));
	}

}
