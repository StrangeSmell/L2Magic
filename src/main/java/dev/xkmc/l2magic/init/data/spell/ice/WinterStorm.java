package dev.xkmc.l2magic.init.data.spell.ice;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import dev.xkmc.l2magic.content.engine.context.DataGenContext;
import dev.xkmc.l2magic.content.engine.core.ConfiguredEngine;
import dev.xkmc.l2magic.content.engine.iterator.DelayedIterator;
import dev.xkmc.l2magic.content.engine.iterator.RingRandomIterator;
import dev.xkmc.l2magic.content.engine.logic.ListLogic;
import dev.xkmc.l2magic.content.engine.logic.PredicateLogic;
import dev.xkmc.l2magic.content.engine.logic.ProcessorEngine;
import dev.xkmc.l2magic.content.engine.logic.RandomVariableLogic;
import dev.xkmc.l2magic.content.engine.modifier.*;
import dev.xkmc.l2magic.content.engine.particle.SimpleParticleInstance;
import dev.xkmc.l2magic.content.engine.processor.DamageProcessor;
import dev.xkmc.l2magic.content.engine.processor.EffectProcessor;
import dev.xkmc.l2magic.content.engine.processor.PushProcessor;
import dev.xkmc.l2magic.content.engine.selector.ArcCubeSelector;
import dev.xkmc.l2magic.content.engine.selector.LinearCubeSelector;
import dev.xkmc.l2magic.content.engine.selector.SelectionType;
import dev.xkmc.l2magic.content.engine.spell.SpellAction;
import dev.xkmc.l2magic.content.engine.spell.SpellCastType;
import dev.xkmc.l2magic.content.engine.spell.SpellTriggerType;
import dev.xkmc.l2magic.content.engine.variable.BooleanVariable;
import dev.xkmc.l2magic.content.engine.variable.DoubleVariable;
import dev.xkmc.l2magic.content.engine.variable.IntVariable;
import dev.xkmc.l2magic.content.entity.motion.MovePosMotion;
import dev.xkmc.l2magic.content.particle.engine.CustomParticleInstance;
import dev.xkmc.l2magic.content.particle.engine.RenderTypePreset;
import dev.xkmc.l2magic.content.particle.engine.SimpleParticleData;
import dev.xkmc.l2magic.init.data.SpellDataGenEntry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;

import java.util.List;

public class WinterStorm extends SpellDataGenEntry {

	public static final ResourceKey<SpellAction> WINTER = spell("winter_storm");
	public static final ResourceKey<SpellAction> TORNADO = spell("tornado");

	@Override
	public void genLang(RegistrateLangProvider pvd) {
		pvd.add(SpellAction.lang(WINTER.location()), "Winter Storm");
		pvd.add(SpellAction.lang(TORNADO.location()), "Tornado");
	}

	@Override
	public void register(BootstrapContext<SpellAction> ctx) {
		new SpellAction(
				winterStorm(new DataGenContext(ctx), 4, 1.5, 1),
				Items.SNOWBALL, 100,
				SpellCastType.CONTINUOUS,
				SpellTriggerType.SELF_POS
		).verifyOnBuild(ctx, WINTER);
		new SpellAction(
				tornado(new DataGenContext(ctx)),
				Items.POWDER_SNOW_BUCKET, 100,
				SpellCastType.CONTINUOUS,
				SpellTriggerType.FACING_FRONT
		).verifyOnBuild(ctx, TORNADO);
	}

	private static ConfiguredEngine<?> winterStorm(DataGenContext ctx, double r, double y, double size) {
		return new ListLogic(List.of(
				new PredicateLogic(
						BooleanVariable.of("TickUsing>=10"),
						new ProcessorEngine(SelectionType.ENEMY,
								new ArcCubeSelector(
										IntVariable.of("11"),
										DoubleVariable.of(r + ""),
										DoubleVariable.of(size * 2 + ""),
										DoubleVariable.of("-180"),
										DoubleVariable.of("-180+360/12*11")
								),
								List.of(
										new DamageProcessor(ctx.damage(DamageTypes.FREEZE),
												DoubleVariable.of("4"), true, true),
										new PushProcessor(
												DoubleVariable.of("0.1"),
												DoubleVariable.of("75"),
												DoubleVariable.ZERO,
												PushProcessor.Type.TO_CENTER
										),
										new EffectProcessor(
												MobEffects.MOVEMENT_SLOWDOWN,//TODO LCEffects.ICE.get(),
												IntVariable.of("100"),
												IntVariable.of("0"),
												false, false
										)
								)), null),
				new DelayedIterator(
						IntVariable.of("10"),
						IntVariable.of("2"),
						new RingRandomIterator(
								DoubleVariable.of((r - size) + ""),
								DoubleVariable.of((r + size) + ""),
								DoubleVariable.of("-180"),
								DoubleVariable.of("180"),
								IntVariable.of("5"),
								new SimpleParticleInstance(
										ParticleTypes.SNOWFLAKE,
										DoubleVariable.of("0.5")
								).move(RotationModifier.of("75"),
										OffsetModifier.of("0", "rand(" + (y - size) + "," + (y + size) + ")", "0")
								), null
						), null
				)
		));
	}


	private static ConfiguredEngine<?> tornado(DataGenContext ctx) {
		double vsp = 0.5;
		int life = 20;
		double rate = Math.tan(10 * Mth.DEG_TO_RAD);
		double w = vsp * 180 / Math.PI / 2;
		double ir = 0.3;
		String radius = ir + "+TickCount*" + vsp * rate;
		String angle = w / (vsp * rate) + "*(log(" + radius + ")+log(" + ir + "))";
		return new ListLogic(List.of(
				new PredicateLogic(
						BooleanVariable.of("TickUsing>=10"),
						new ProcessorEngine(SelectionType.ENEMY,
								new LinearCubeSelector(
										IntVariable.of("6"),
										DoubleVariable.of("1.5")
								),
								List.of(
										new DamageProcessor(ctx.damage(DamageTypes.FREEZE),
												DoubleVariable.of("4"), true, true),
										new PushProcessor(
												DoubleVariable.of("0.1"),
												DoubleVariable.ZERO,
												DoubleVariable.ZERO,
												PushProcessor.Type.TO_CENTER
										),
										new EffectProcessor(
												MobEffects.MOVEMENT_SLOWDOWN,//TODO LCEffects.ICE.get(),
												IntVariable.of("100"),
												IntVariable.of("0"),
												false, false
										)
								)), null),
				new DelayedIterator(
						IntVariable.of("10"),
						IntVariable.of("1"),
						new RandomVariableLogic("r", 1,
								new RingRandomIterator(
										DoubleVariable.of(ir + ""),
										DoubleVariable.of(ir + ""),
										DoubleVariable.of("-180"),
										DoubleVariable.of("180"),
										IntVariable.of("3"),
										new CustomParticleInstance(
												DoubleVariable.of("0"),
												DoubleVariable.of("0.15"),
												IntVariable.of("" + life),
												true,
												new MovePosMotion(List.of(
														ForwardOffsetModifier.of("-" + ir),
														RotationModifier.of(angle),
														ForwardOffsetModifier.of(radius),
														new Normal2DirModifier(),
														ForwardOffsetModifier.of("TickCount*" + vsp)
												)),
												new SimpleParticleData(
														RenderTypePreset.NORMAL,
														ParticleTypes.SNOWFLAKE
												)
										).move(NormalOffsetModifier.of("rand(" + (-vsp) + "," + vsp + ")")), null
								).move(new Dir2NormalModifier())
						), null
				)
		));
	}

}
