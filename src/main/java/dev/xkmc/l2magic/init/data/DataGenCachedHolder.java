package dev.xkmc.l2magic.init.data;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class DataGenCachedHolder<T> implements Holder<T> {

	public final ResourceKey<T> key;
	public T value;

	public DataGenCachedHolder(ResourceKey<T> key) {
		this.key = key;
	}

	public void gen(BootstrapContext<T> ctx, T val) {
		value = val;
		ctx.register(key, val);
	}

	@Override
	public T value() {
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
	public boolean is(ResourceKey<T> resourceKey) {
		return key.equals(resourceKey);
	}

	@Override
	public boolean is(Predicate<ResourceKey<T>> predicate) {
		return predicate.test(key);
	}

	@Override
	public boolean is(TagKey<T> tagKey) {
		return false;
	}

	@Override
	public boolean is(Holder<T> holder) {
		return holder == this || key.equals(holder.getKey());
	}

	@Override
	public Stream<TagKey<T>> tags() {
		return Stream.empty();
	}

	@Override
	public Either<ResourceKey<T>, T> unwrap() {
		return Either.left(key);
	}

	@Override
	public Optional<ResourceKey<T>> unwrapKey() {
		return Optional.of(key);
	}

	@Override
	public Kind kind() {
		return Kind.REFERENCE;
	}

	@Override
	public boolean canSerializeIn(HolderOwner<T> owner) {
		return true;
	}
}
