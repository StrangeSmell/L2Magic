package dev.xkmc.l2magic.init.data.spell;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import dev.xkmc.l2magic.content.engine.block.SetBlock;
import dev.xkmc.l2magic.content.engine.context.DataGenContext;
import dev.xkmc.l2magic.content.engine.core.ConfiguredEngine;
import dev.xkmc.l2magic.content.engine.predicate.FullTopSurfaceCondition;
import dev.xkmc.l2magic.content.engine.predicate.ReplaceableCondition;
import dev.xkmc.l2magic.content.engine.spell.SpellAction;
import dev.xkmc.l2magic.content.engine.spell.SpellCastType;
import dev.xkmc.l2magic.content.engine.spell.SpellTriggerType;
import dev.xkmc.l2magic.content.engine.variable.DoubleVariable;
import dev.xkmc.l2magic.init.data.SpellDataGenEntry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class CobWebSpells extends SpellDataGenEntry {

	public static final ResourceKey<SpellAction> AREA = spell("cobwebs");

	@Override
	public void register(BootstrapContext<SpellAction> ctx) {
		new SpellAction(cobwebs(new DataGenContext(ctx)),
				Items.COBWEB, 612, SpellCastType.INSTANT, SpellTriggerType.TARGET_POS
		).verifyOnBuild(ctx, AREA);
	}

	@Override
	public void genLang(RegistrateLangProvider pvd) {
		pvd.add(SpellAction.lang(AREA.location()), "Spider Nest");
	}

	private static ConfiguredEngine<?> cobwebs(DataGenContext ctx) {
		return new SetBlock(Blocks.COBWEB.defaultBlockState()).circular(
				DoubleVariable.of("6"), DoubleVariable.of("2"),
				ReplaceableCondition.INS, FullTopSurfaceCondition.INS);
	}
}
