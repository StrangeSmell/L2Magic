package dev.xkmc.l2magic.init.data.spell;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import dev.xkmc.l2magic.content.engine.context.DataGenContext;
import dev.xkmc.l2magic.content.engine.core.ConfiguredEngine;
import dev.xkmc.l2magic.content.engine.iterator.DelayedIterator;
import dev.xkmc.l2magic.content.engine.iterator.LinearIterator;
import dev.xkmc.l2magic.content.engine.iterator.RingIterator;
import dev.xkmc.l2magic.content.engine.logic.ListLogic;
import dev.xkmc.l2magic.content.engine.logic.ProcessorEngine;
import dev.xkmc.l2magic.content.engine.logic.RandomVariableLogic;
import dev.xkmc.l2magic.content.engine.modifier.*;
import dev.xkmc.l2magic.content.engine.particle.SimpleParticleInstance;
import dev.xkmc.l2magic.content.engine.processor.DamageProcessor;
import dev.xkmc.l2magic.content.engine.processor.KnockBackProcessor;
import dev.xkmc.l2magic.content.engine.processor.PushProcessor;
import dev.xkmc.l2magic.content.engine.selector.BoxSelector;
import dev.xkmc.l2magic.content.engine.selector.LinearCubeSelector;
import dev.xkmc.l2magic.content.engine.selector.SelectionType;
import dev.xkmc.l2magic.content.engine.spell.SpellAction;
import dev.xkmc.l2magic.content.engine.spell.SpellCastType;
import dev.xkmc.l2magic.content.engine.spell.SpellTriggerType;
import dev.xkmc.l2magic.content.engine.variable.DoubleVariable;
import dev.xkmc.l2magic.content.engine.variable.IntVariable;
import dev.xkmc.l2magic.content.entity.core.ProjectileConfig;
import dev.xkmc.l2magic.content.entity.engine.CustomProjectileShoot;
import dev.xkmc.l2magic.content.entity.motion.MovePosMotion;
import dev.xkmc.l2magic.init.data.DataGenCachedHolder;
import dev.xkmc.l2magic.init.data.SpellDataGenEntry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Map;

public class ArrowSpells extends SpellDataGenEntry {


	public static final ResourceKey<SpellAction> ARROW = spell("magic_arrows");
	public static final ResourceKey<SpellAction> ARROW_RING = spell("magic_arrow_ring");
	public static final ResourceKey<SpellAction> CIRCULAR = spell("circular");
	public static final ResourceKey<SpellAction> CIRCULAR_ENTITY = spell("circular_entity");
	public static final DataGenCachedHolder<ProjectileConfig> CIRCULAR_PROJECTILE = projectile("circular_projectile");

	@Override
	public void genLang(RegistrateLangProvider pvd) {
		pvd.add(SpellAction.lang(ARROW_RING.location()), "Sword of Seven");
		pvd.add(SpellAction.lang(ARROW.location()), "Angelic Judgement");
		pvd.add(SpellAction.lang(CIRCULAR.location()), "Three Bodies");
		pvd.add(SpellAction.lang(CIRCULAR_ENTITY.location()), "Three Moons");
	}

	@Override
	public void register(BootstrapContext<SpellAction> ctx) {
		new SpellAction(
				arrowRing(new DataGenContext(ctx)),
				Items.SPECTRAL_ARROW, 400,
				SpellCastType.INSTANT,
				SpellTriggerType.FACING_FRONT
		).verifyOnBuild(ctx, ARROW_RING);
		new SpellAction(
				arrows(new DataGenContext(ctx)),
				Items.ARROW, 500,
				SpellCastType.INSTANT,
				SpellTriggerType.FACING_FRONT
		).verifyOnBuild(ctx, ARROW);
		new SpellAction(
				circular(new DataGenContext(ctx)),
				Items.CONDUIT, 600,
				SpellCastType.INSTANT,
				SpellTriggerType.SELF_POS
		).verifyOnBuild(ctx, CIRCULAR);

		new SpellAction(
				circularEntity(new DataGenContext(ctx)),
				Items.SHULKER_SHELL, 650,
				SpellCastType.INSTANT,
				SpellTriggerType.SELF_POS
		).verifyOnBuild(ctx, CIRCULAR_ENTITY);
	}

	@Override
	public void registerProjectile(BootstrapContext<ProjectileConfig> ctx) {
		circularProjectile(new DataGenContext(ctx))
				.verifyOnBuild(ctx, CIRCULAR_PROJECTILE);
	}

	private static ConfiguredEngine<?> arrowRing(DataGenContext ctx) {
		return new ListLogic(List.of(
				shoot(ctx).move(OffsetModifier.of("0", "-0.1", "0")),
				new RandomVariableLogic("r", 1,
						new RingIterator(
								DoubleVariable.of("0.5"),
								DoubleVariable.of("r0*360"),
								DoubleVariable.of("360+r0*360"),
								IntVariable.of("7"),
								false,
								shoot(ctx).move(RotationModifier.of("0", "75")),
								null
						).move(new Dir2NormalModifier()))
		));
	}

	private static ConfiguredEngine<?> arrows(DataGenContext ctx) {
		return new RingIterator(
				DoubleVariable.of("0.5"),
				DoubleVariable.of("-30"),
				DoubleVariable.of("30"),
				IntVariable.of("7"),
				true,
				shootMove(ctx),
				null
		).move(OffsetModifier.of("0", "-0.1", "0"));
	}

	private static ConfiguredEngine<?> circular(DataGenContext ctx) {
		return new DelayedIterator(
				IntVariable.of("60"),
				IntVariable.of("1"),
				new RingIterator(
						DoubleVariable.of("i*0.1+0.5"),
						DoubleVariable.of("-180+i*14"),
						DoubleVariable.of("180+i*14"),
						IntVariable.of("3"),
						false,
						new ListLogic(List.of(
								new ProcessorEngine(SelectionType.ENEMY,
										new BoxSelector(
												DoubleVariable.of("1"),
												DoubleVariable.of("1"),
												true
										), List.of(
										new DamageProcessor(
												ctx.damage(DamageTypes.INDIRECT_MAGIC),
												DoubleVariable.of("6"),
												true, true),
										new PushProcessor(
												DoubleVariable.of("0.3"),
												DoubleVariable.of("0"),
												DoubleVariable.of("10"),
												PushProcessor.Type.UNIFORM
										)
								)),
								new SimpleParticleInstance(
										ParticleTypes.END_ROD,
										DoubleVariable.ZERO
								)
						)),
						null
				).move(new ToCurrentCasterPosModifier(),
						OffsetModifier.of("0", "1", "0")
				), "i"
		);
	}

	private static ConfiguredEngine<?> shoot(DataGenContext ctx) {
		int dis = 24;
		double step = 0.2;
		double rad = 1;
		return new ListLogic(List.of(
				new ProcessorEngine(SelectionType.ENEMY,
						new LinearCubeSelector(
								IntVariable.of(dis / rad + ""),
								DoubleVariable.of(rad + "")
						), List.of(new DamageProcessor(
								ctx.damage(DamageTypes.INDIRECT_MAGIC),
								DoubleVariable.of("6"),
								true, true),
						KnockBackProcessor.of("1")
				)),
				new LinearIterator(
						DoubleVariable.of(step + ""),
						Vec3.ZERO,
						DoubleVariable.ZERO,
						IntVariable.of(dis / step + ""),
						true,
						new SimpleParticleInstance(
								ParticleTypes.END_ROD,
								DoubleVariable.ZERO
						),
						null
				)));
	}

	private static ConfiguredEngine<?> shootMove(DataGenContext ctx) {
		int dis = 24;
		double rad = 1;
		return new DelayedIterator(IntVariable.of(dis + ""), IntVariable.of("1"),
				new ListLogic(List.of(
						new ProcessorEngine(SelectionType.ENEMY,
								new BoxSelector(
										DoubleVariable.of(rad + ""),
										DoubleVariable.of(rad + ""),
										true
								), List.of(new DamageProcessor(
										ctx.damage(DamageTypes.INDIRECT_MAGIC),
										DoubleVariable.of("6"),
										true, true),
								new PushProcessor(
										DoubleVariable.of("0.5"),
										DoubleVariable.ZERO,
										DoubleVariable.ZERO,
										PushProcessor.Type.UNIFORM
								)
						)),
						new SimpleParticleInstance(
								ParticleTypes.END_ROD,
								DoubleVariable.ZERO
						)
				)).move(ForwardOffsetModifier.of(rad + "*i")), "i");
	}

	private static ProjectileConfig circularProjectile(DataGenContext ctx) {
		return ProjectileConfig.builder(SelectionType.ENEMY_NO_FAMILY, "angle")
				.motion(new MovePosMotion(List.of(new SetPosModifier(
						DoubleVariable.of("CasterX+(0.5+TickCount*0.1)*cosDegree(angle+TickCount*14)"),
						DoubleVariable.of("CasterY+1"),
						DoubleVariable.of("CasterZ+(0.5+TickCount*0.1)*sinDegree(angle+TickCount*14)")
				))))
				.tick(new ListLogic(List.of(
						new ProcessorEngine(SelectionType.ENEMY,
								new BoxSelector(
										DoubleVariable.of("1"),
										DoubleVariable.of("1"),
										true
								), List.of(
								new DamageProcessor(
										ctx.damage(DamageTypes.INDIRECT_MAGIC),
										DoubleVariable.of("6"),
										true, true),
								new PushProcessor(
										DoubleVariable.of("0.3"),
										DoubleVariable.of("0"),
										DoubleVariable.of("10"),
										PushProcessor.Type.UNIFORM
								)
						)),
						new SimpleParticleInstance(
								ParticleTypes.END_ROD,
								DoubleVariable.ZERO
						)
				))).build();
	}

	private static ConfiguredEngine<?> circularEntity(DataGenContext ctx) {
		return new RingIterator(
				DoubleVariable.of("0.5"),
				DoubleVariable.of("-180"),
				DoubleVariable.of("180"),
				IntVariable.of("3"),
				false,
				new CustomProjectileShoot(
						DoubleVariable.ZERO,
						CIRCULAR_PROJECTILE,
						IntVariable.of("60"),
						true, true,
						Map.of("angle", DoubleVariable.of("i*120"))
				),
				"i"
		).move(new ToCurrentCasterPosModifier(),
				OffsetModifier.of("0", "1", "0"));
	}

}
