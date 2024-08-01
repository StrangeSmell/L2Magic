package dev.xkmc.l2magic.init.data;

import com.mojang.datafixers.util.Either;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import dev.xkmc.l2magic.content.engine.spell.SpellAction;
import dev.xkmc.l2magic.content.entity.core.ProjectileConfig;
import dev.xkmc.l2magic.init.L2Magic;
import dev.xkmc.l2magic.init.registrate.EngineRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class SpellDataGenEntry {

	protected static ResourceKey<SpellAction> spell(String id) {
		return ResourceKey.create(EngineRegistry.SPELL, L2Magic.loc(id));
	}

	protected static ProjectileHolder projectile(String id) {
		return new ProjectileHolder(ResourceKey.create(EngineRegistry.PROJECTILE, L2Magic.loc(id)));
	}

	public void registerProjectile(BootstrapContext<ProjectileConfig> ctx) {

	}

	public abstract void register(BootstrapContext<SpellAction> ctx);

	public abstract void genLang(RegistrateLangProvider pvd);

	public static class ProjectileHolder implements Holder<ProjectileConfig> {

		public final ResourceKey<ProjectileConfig> key;
		public ProjectileConfig value;

		public ProjectileHolder(ResourceKey<ProjectileConfig> key) {
			this.key = key;
		}

		public void write(ProjectileConfig config){
			value = config;
		}

		@Override
		public ProjectileConfig value() {
			return value;
		}

		@Override
		public boolean isBound() {
			return true;
		}

		@Override
		public boolean is(ResourceLocation location) {
			return key.location().equals(location);
		}

		@Override
		public boolean is(ResourceKey<ProjectileConfig> resourceKey) {
			return key.equals(resourceKey);
		}

		@Override
		public boolean is(Predicate<ResourceKey<ProjectileConfig>> predicate) {
			return predicate.test(key);
		}

		@Override
		public boolean is(TagKey<ProjectileConfig> tagKey) {
			return false;
		}

		@Override
		public boolean is(Holder<ProjectileConfig> holder) {
			return holder == this || key.equals(holder.getKey());
		}

		@Override
		public Stream<TagKey<ProjectileConfig>> tags() {
			return Stream.empty();
		}

		@Override
		public Either<ResourceKey<ProjectileConfig>, ProjectileConfig> unwrap() {
			return Either.left(key);
		}

		@Override
		public Optional<ResourceKey<ProjectileConfig>> unwrapKey() {
			return Optional.of(key);
		}

		@Override
		public Kind kind() {
			return Kind.REFERENCE;
		}

		@Override
		public boolean canSerializeIn(HolderOwner<ProjectileConfig> owner) {
			return true;
		}
	}

}
