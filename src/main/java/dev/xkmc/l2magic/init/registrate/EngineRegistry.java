package dev.xkmc.l2magic.init.registrate;

import dev.xkmc.l2core.init.reg.simple.Val;
import dev.xkmc.l2magic.content.engine.block.SetBlock;
import dev.xkmc.l2magic.content.engine.block.SetBlockFacing;
import dev.xkmc.l2magic.content.engine.core.*;
import dev.xkmc.l2magic.content.engine.helper.EngineRegistryInstance;
import dev.xkmc.l2magic.content.engine.iterator.*;
import dev.xkmc.l2magic.content.engine.logic.*;
import dev.xkmc.l2magic.content.engine.modifier.*;
import dev.xkmc.l2magic.content.engine.particle.*;
import dev.xkmc.l2magic.content.engine.predicate.AndPredicate;
import dev.xkmc.l2magic.content.engine.predicate.BlockPredicate;
import dev.xkmc.l2magic.content.engine.predicate.MovePredicate;
import dev.xkmc.l2magic.content.engine.predicate.OrPredicate;
import dev.xkmc.l2magic.content.engine.processor.*;
import dev.xkmc.l2magic.content.engine.selector.*;
import dev.xkmc.l2magic.content.engine.sound.SoundInstance;
import dev.xkmc.l2magic.content.engine.spell.SpellAction;
import dev.xkmc.l2magic.content.entity.core.Motion;
import dev.xkmc.l2magic.content.entity.core.MotionType;
import dev.xkmc.l2magic.content.entity.core.ProjectileConfig;
import dev.xkmc.l2magic.content.entity.engine.ArrowShoot;
import dev.xkmc.l2magic.content.entity.engine.CustomProjectileShoot;
import dev.xkmc.l2magic.content.entity.engine.TridentShoot;
import dev.xkmc.l2magic.content.entity.motion.MoveDeltaMotion;
import dev.xkmc.l2magic.content.entity.motion.MovePosMotion;
import dev.xkmc.l2magic.content.entity.motion.SimpleMotion;
import dev.xkmc.l2magic.content.entity.renderer.ProjectileRenderData;
import dev.xkmc.l2magic.content.entity.renderer.ProjectileRenderType;
import dev.xkmc.l2magic.content.particle.engine.*;
import dev.xkmc.l2magic.init.L2Magic;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class EngineRegistry {

	public static final ResourceKey<Registry<SpellAction>> SPELL = ResourceKey.createRegistryKey(L2Magic.loc("spell_action"));
	public static final ResourceKey<Registry<ProjectileConfig>> PROJECTILE = ResourceKey.createRegistryKey(L2Magic.loc("projectile"));

	public static final EngineRegistryInstance<EngineType<?>> ENGINE = EngineRegistryInstance.of("configured_engine");
	public static final EngineRegistryInstance<ModifierType<?>> MODIFIER = EngineRegistryInstance.of("modifier");
	public static final EngineRegistryInstance<PredicateType<?>> PREDICATE = EngineRegistryInstance.of("predicate");
	public static final EngineRegistryInstance<SelectorType<?>> SELECTOR = EngineRegistryInstance.of("selector");
	public static final EngineRegistryInstance<ProcessorType<?>> PROCESSOR = EngineRegistryInstance.of("processor");
	public static final EngineRegistryInstance<MotionType<?>> MOTION = EngineRegistryInstance.of("motion");
	public static final EngineRegistryInstance<ParticleRenderType<?>> PARTICLE_RENDERER = EngineRegistryInstance.of("particle_renderer");
	public static final EngineRegistryInstance<ProjectileRenderType<?>> PROJECTILE_RENDERER = EngineRegistryInstance.of("projectile_renderer");

	public static final Val<ModifierType<ForwardOffsetModifier>> FORWARD = register("forward", () -> ForwardOffsetModifier.CODEC);
	public static final Val<ModifierType<NormalOffsetModifier>> NORMAL_OFFSET = register("normal_offset", () -> NormalOffsetModifier.CODEC);//TODO doc
	public static final Val<ModifierType<RotationModifier>> ROTATE = register("rotate", () -> RotationModifier.CODEC);
	public static final Val<ModifierType<OffsetModifier>> OFFSET = register("offset", () -> OffsetModifier.CODEC);
	public static final Val<ModifierType<SetPosModifier>> POSITION = register("set_position", () -> SetPosModifier.CODEC);//TODO doc
	public static final Val<ModifierType<SetDirectionModifier>> DIRECTION = register("direction", () -> SetDirectionModifier.CODEC);
	public static final Val<ModifierType<RandomOffsetModifier>> RANDOM_OFFSET = register("random_offset", () -> RandomOffsetModifier.CODEC);
	public static final Val<ModifierType<SetNormalModifier>> NORMAL = register("set_normal", () -> SetNormalModifier.CODEC);
	public static final Val<ModifierType<Dir2NormalModifier>> DIR_2_NORMAL = register("direction_to_normal", () -> Dir2NormalModifier.CODEC);
	public static final Val<ModifierType<Normal2DirModifier>> NORMAL_2_DIR = register("normal_to_direction", () -> Normal2DirModifier.CODEC);
	public static final Val<ModifierType<ToCurrentCasterPosModifier>> TO_CASTER_POS = register("move_to_caster", () -> ToCurrentCasterPosModifier.CODEC);
	public static final Val<ModifierType<ToCurrentCasterDirModifier>> TO_CASTER_DIR = register("align_with_caster", () -> ToCurrentCasterDirModifier.CODEC);

	public static final Val<EngineType<PredicateLogic>> IF = register("if", () -> PredicateLogic.CODEC);
	public static final Val<EngineType<ListLogic>> LIST = register("list", () -> ListLogic.CODEC);
	public static final Val<EngineType<DelayLogic>> DELAY = register("delay", () -> DelayLogic.CODEC);
	public static final Val<EngineType<RandomVariableLogic>> RANDOM = register("random", () -> RandomVariableLogic.CODEC);
	public static final Val<EngineType<MoveEngine>> MOVE_ENGINE = register("move", () -> MoveEngine.CODEC);
	public static final Val<EngineType<ProcessorEngine>> PROCESS_ENGINE = register("processor", () -> ProcessorEngine.CODEC);

	public static final Val<EngineType<LoopIterator>> ITERATE = register("iterate", () -> LoopIterator.CODEC);
	public static final Val<EngineType<DelayedIterator>> ITERATE_DELAY = register("iterate_delayed", () -> DelayedIterator.CODEC);
	public static final Val<EngineType<WhileDelayedIterator>> WHILE_DELAY = register("while_delayed", () -> WhileDelayedIterator.CODEC);//TODO doc
	public static final Val<EngineType<LinearIterator>> ITERATE_LINEAR = register("iterate_linear", () -> LinearIterator.CODEC);
	public static final Val<EngineType<RingIterator>> ITERATE_ARC = register("iterate_arc", () -> RingIterator.CODEC);
	public static final Val<EngineType<RingRandomIterator>> RANDOM_FAN = register("random_pos_fan", () -> RingRandomIterator.CODEC);
	public static final Val<EngineType<SphereRandomIterator>> RANDOM_SPHERE = register("random_pos_sphere", () -> SphereRandomIterator.CODEC);//TODO doc

	public static final Val<EngineType<SimpleParticleInstance>> SIMPLE_PARTICLE = register("particle", () -> SimpleParticleInstance.CODEC);//TODO doc
	public static final Val<EngineType<BlockParticleInstance>> BLOCK_PARTICLE = register("block_particle", () -> BlockParticleInstance.CODEC);//TODO doc
	public static final Val<EngineType<ItemParticleInstance>> ITEM_PARTICLE = register("item_particle", () -> ItemParticleInstance.CODEC);//TODO doc
	public static final Val<EngineType<DustParticleInstance>> DUST_PARTICLE = register("dust_particle", () -> DustParticleInstance.CODEC);//TODO doc
	public static final Val<EngineType<TransitionParticleInstance>> TRANSITION_PARTICLE = register("transition_particle", () -> TransitionParticleInstance.CODEC);//TODO doc
	public static final Val<EngineType<CustomParticleInstance>> CUSTOM_PARTICLE = register("custom_particle", () -> CustomParticleInstance.CODEC);//TODO doc

	public static final Val<EngineType<SoundInstance>> SOUND = register("sound", () -> SoundInstance.CODEC);//TODO doc

	public static final Val<EngineType<SetBlock>> SET_BLOCK = register("set_block", () -> SetBlock.CODEC);//TODO doc
	public static final Val<EngineType<SetBlockFacing>> SET_BLOCK_FACING = register("set_block_facing", () -> SetBlockFacing.CODEC);//TODO doc

	public static final Val<EngineType<ArrowShoot>> ARROW = register("arrow", () -> ArrowShoot.CODEC);//TODO doc
	public static final Val<EngineType<TridentShoot>> TRIDENT = register("trident", () -> TridentShoot.CODEC);//TODO doc
	public static final Val<EngineType<CustomProjectileShoot>> CUSTOM_SHOOT = register("custom_projectile", () -> CustomProjectileShoot.CODEC);//TODO doc

	public static final Val<SelectorType<SelfSelector>> SELF = register("self", () -> SelfSelector.CODEC);
	public static final Val<SelectorType<MoveSelector>> MOVE_SELECTOR = register("move", () -> MoveSelector.CODEC);
	public static final Val<SelectorType<BoxSelector>> BOX = register("box", () -> BoxSelector.CODEC);
	public static final Val<SelectorType<CompoundEntitySelector>> COMPOUND = register("compound", () -> CompoundEntitySelector.CODEC);
	public static final Val<SelectorType<LinearCubeSelector>> LINEAR = register("line", () -> LinearCubeSelector.CODEC);
	public static final Val<SelectorType<ArcCubeSelector>> ARC = register("arc", () -> ArcCubeSelector.CODEC);
	public static final Val<SelectorType<ApproxCylinderSelector>> CYLINDER = register("cylinder", () -> ApproxCylinderSelector.CODEC);
	public static final Val<SelectorType<ApproxBallSelector>> BALL = register("ball", () -> ApproxBallSelector.CODEC);

	public static final Val<PredicateType<AndPredicate>> AND = register("and", () -> AndPredicate.CODEC);//TODO docs
	public static final Val<PredicateType<OrPredicate>> OR = register("or", () -> OrPredicate.CODEC);//TODO docs
	public static final Val<PredicateType<MovePredicate>> MOVE_PRED = register("move", () -> MovePredicate.CODEC);//TODO docs
	public static final Val<PredicateType<BlockPredicate>> BLOCK_PRED = register("block", () -> BlockPredicate.CODEC);//TODO docs

	public static final Val<ProcessorType<DamageProcessor>> DAMAGE = register("damage", () -> DamageProcessor.CODEC);
	public static final Val<ProcessorType<KnockBackProcessor>> KB = register("knockback", () -> KnockBackProcessor.CODEC);
	public static final Val<ProcessorType<PushProcessor>> PUSH_ENTITY = register("push", () -> PushProcessor.CODEC);
	public static final Val<ProcessorType<EffectProcessor>> EFFECT = register("effect", () -> EffectProcessor.CODEC);
	public static final Val<ProcessorType<PropertyProcessor>> PROP = register("property", () -> PropertyProcessor.CODEC);
	public static final Val<ProcessorType<TeleportProcessor>> TP = register("teleport", () -> TeleportProcessor.CODEC); //TODO docs

	public static final Val<MotionType<SimpleMotion>> SIMPLE_MOTION = register("simple", () -> SimpleMotion.CODEC); // TODO doc
	public static final Val<MotionType<MovePosMotion>> MOVE_MOTION = register("control_position", () -> MovePosMotion.CODEC);//TODO doc
	public static final Val<MotionType<MoveDeltaMotion>> DELTA_MOTION = register("control_velocity", () -> MoveDeltaMotion.CODEC);//TODO doc

	public static final Val<ParticleRenderType<SimpleParticleData>> SIMPLE_RENDER = register("simple", () -> SimpleParticleData.CODEC);//TODO doc
	public static final Val<ParticleRenderType<DustParticleData>> COLOR_RENDER = register("color", () -> DustParticleData.CODEC);//TODO doc
	public static final Val<ParticleRenderType<TransitionParticleData>> TRANSITION_RENDER = register("transition", () -> TransitionParticleData.CODEC);//TODO doc
	public static final Val<ParticleRenderType<BlockParticleData>> BLOCK_RENDER = register("block", () -> BlockParticleData.CODEC);//TODO doc
	public static final Val<ParticleRenderType<ItemParticleData>> ITEM_RENDER = register("item", () -> ItemParticleData.CODEC);//TODO doc
	public static final Val<ParticleRenderType<StaticTextureParticleData>> STATIC_RENDER = register("static", () -> StaticTextureParticleData.CODEC);//TODO doc
	public static final Val<ParticleRenderType<OrientedParticleData>> ORIENTED_RENDER = register("oriented", () -> OrientedParticleData.CODEC);//TODO doc

	private static <T extends Record & ConfiguredEngine<T>> Val<EngineType<T>>
	register(String id, EngineType<T> codec) {
		return new Val.Registrate<>(L2Magic.REGISTRATE.simple(id, ENGINE.key(), () -> codec));
	}

	private static <T extends Record & EntitySelector<T>> Val<SelectorType<T>>
	register(String id, SelectorType<T> codec) {
		return new Val.Registrate<>(L2Magic.REGISTRATE.simple(id, SELECTOR.key(), () -> codec));
	}

	private static <T extends Record & Modifier<T>> Val<ModifierType<T>>
	register(String id, ModifierType<T> codec) {
		return new Val.Registrate<>(L2Magic.REGISTRATE.simple(id, MODIFIER.key(), () -> codec));
	}

	private static <T extends Record & ContextPredicate<T>> Val<PredicateType<T>>
	register(String id, PredicateType<T> codec) {
		return new Val.Registrate<>(L2Magic.REGISTRATE.simple(id, PREDICATE.key(), () -> codec));
	}

	private static <T extends Record & EntityProcessor<T>> Val<ProcessorType<T>>
	register(String id, ProcessorType<T> codec) {
		return new Val.Registrate<>(L2Magic.REGISTRATE.simple(id, PROCESSOR.key(), () -> codec));
	}

	private static <T extends Record & Motion<T>> Val<MotionType<T>>
	register(String id, MotionType<T> codec) {
		return new Val.Registrate<>(L2Magic.REGISTRATE.simple(id, MOTION.key(), () -> codec));
	}

	private static <T extends Record & ParticleRenderData<T>> Val<ParticleRenderType<T>>
	register(String id, ParticleRenderType<T> codec) {
		return new Val.Registrate<>(L2Magic.REGISTRATE.simple(id, PARTICLE_RENDERER.key(), () -> codec));
	}

	private static <T extends Record & ProjectileRenderData<T>> Val<ProjectileRenderType<T>>
	register(String id, ProjectileRenderType<T> codec) {
		return new Val.Registrate<>(L2Magic.REGISTRATE.simple(id, PROJECTILE_RENDERER.key(), () -> codec));
	}

	public static void register() {

	}

}
