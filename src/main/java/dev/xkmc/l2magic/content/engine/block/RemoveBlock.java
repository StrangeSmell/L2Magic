package dev.xkmc.l2magic.content.engine.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2magic.content.engine.context.EngineContext;
import dev.xkmc.l2magic.content.engine.core.ConfiguredEngine;
import dev.xkmc.l2magic.content.engine.core.EngineType;
import dev.xkmc.l2magic.init.registrate.EngineRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;

public record RemoveBlock(
		boolean clearFluid
) implements ConfiguredEngine<RemoveBlock> {

	public static final MapCodec<RemoveBlock> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			Codec.BOOL.fieldOf("clearFluid").forGetter(RemoveBlock::clearFluid)
	).apply(i, RemoveBlock::new));

	@Override
	public EngineType<RemoveBlock> type() {
		return EngineRegistry.REMOVE_BLOCK.get();
	}

	@Override
	public void execute(EngineContext ctx) {
		var level = ctx.user().level();
		var pos = BlockPos.containing(ctx.loc().pos());
		if (clearFluid) level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		else level.removeBlock(pos, false);
	}

}
