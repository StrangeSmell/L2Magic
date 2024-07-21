package dev.xkmc.l2magic.content.entity.renderer;

import com.mojang.serialization.MapCodec;

public interface ProjectileRenderType<T extends Record & ProjectileRenderData<T>> {

	MapCodec<T> codec();

}
