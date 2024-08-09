package dev.xkmc.l2magic.content.engine.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2magic.content.engine.context.EngineContext;
import dev.xkmc.l2magic.content.engine.core.ConfiguredEngine;
import dev.xkmc.l2magic.content.engine.core.EngineType;
import dev.xkmc.l2magic.init.registrate.EngineRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public record SetBlock(
		BlockState state
) implements ConfiguredEngine<SetBlock> {

	public static final MapCodec<SetBlock> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			BlockUtils.BLOCK_OR_STATE.fieldOf("state").forGetter(SetBlock::state)
	).apply(i, SetBlock::new));

	@Override
	public EngineType<SetBlock> type() {
		return EngineRegistry.SET_BLOCK.get();
	}

	@Override
	public void execute(EngineContext ctx) {
		ctx.user().level().setBlockAndUpdate(BlockPos.containing(ctx.loc().pos()), state);
	}

}
