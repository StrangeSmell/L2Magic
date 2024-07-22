package dev.xkmc.l2magic.content.particle.render;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;

public record SpriteGeom(float u0, float u1, float v0, float v1) {

	public static final Codec<SpriteGeom> CODEC = RecordCodecBuilder.create(i -> i.group(
			Codec.FLOAT.fieldOf("x").forGetter(e -> e.u0),
			Codec.FLOAT.fieldOf("y").forGetter(e -> e.v0),
			Codec.FLOAT.fieldOf("w").forGetter(e -> e.u1 - e.u0),
			Codec.FLOAT.fieldOf("h").forGetter(e -> e.v1 - e.v0)
	).apply(i, (x, y, w, h) -> new SpriteGeom(x, x + w, y, y + h)));

	public static final SpriteGeom INSTANCE = new SpriteGeom(0, 1, 0, 1);

	public static SpriteGeom breaking(RandomSource random) {
		float u0 = random.nextFloat() * 0.75f;
		float v0 = random.nextFloat() * 0.75f;
		return new SpriteGeom(u0, u0 + 0.25f, v0, v0 + 0.25f);
	}
}
