package dev.xkmc.l2magic.content.engine.helper;

import com.mojang.serialization.Codec;
import dev.xkmc.l2magic.content.engine.context.BuilderContext;
import dev.xkmc.l2magic.content.engine.core.ConfiguredEngine;
import dev.xkmc.l2magic.content.engine.core.ParameterizedVerifiable;
import dev.xkmc.l2magic.content.engine.core.Verifiable;
import dev.xkmc.l2serial.serialization.type_cache.RecordCache;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EngineHelper {

	private static final Map<Class<?>, EngineHelper> CACHE = new LinkedHashMap<>();

	public static EngineHelper get(Class<?> cls) {
		if (CACHE.containsKey(cls)) {
			return CACHE.get(cls);
		}
		var ans = of(cls);
		CACHE.put(cls, ans);
		return ans;
	}

	public static void verifyFields(Verifiable obj, BuilderContext ctx, Class<?> cls) {
		if (!cls.isRecord())
			throw new IllegalStateException("class " + cls.getSimpleName() + " is not a record");
		try {
			var set = obj.verificationParameters();
			for (var e : get(cls).children) {
				Verifiable v = (Verifiable) e.get(obj);
				if (v != null) {
					if (v instanceof ParameterizedVerifiable)
						v.verify(ctx.of(e.getName(), set));
					else
						v.verify(ctx.of(e.getName()));
				}
			}
			for (var e : get(cls).collections) {
				Object x = e.get(obj);
				if (x instanceof List<?> l) {
					for (int i = 0; i < l.size(); i++) {
						if (l.get(i) instanceof Verifiable v)
							v.verify(ctx.of(e.getName() + "[" + i + "]"));
					}
				} else if (x instanceof Map<?, ?> map) {
					for (var ent : map.entrySet()) {
						if (ent.getValue() instanceof Verifiable v)
							v.verify(ctx.of(e.getName() + "[" + ent.getKey() + "]"));
					}
				}

			}
		} catch (Exception e) {
			throw new IllegalStateException("class " + cls.getSimpleName() + " failed configuration", e);
		}
	}

	private static EngineHelper of(Class<?> cls) {
		try {
			return new EngineHelper(cls);
		} catch (Exception e) {
			throw new IllegalStateException("class " + cls.getSimpleName() + " failed configuration", e);
		}
	}

	private final List<Field> children = new ArrayList<>();
	private final List<Field> collections = new ArrayList<>();

	public EngineHelper(Class<?> cls) throws Exception {
		assert cls.isRecord();
		var cache = RecordCache.get(cls);
		for (var e : cache.getFields()) {
			if (Verifiable.class.isAssignableFrom(e.getType())) {
				children.add(e);
			}
			if (List.class.isAssignableFrom(e.getType())) {
				collections.add(e);
			}
			if (Map.class.isAssignableFrom(e.getType())) {
				collections.add(e);
			}
		}
	}

	public static <T extends Enum<T>> Codec<T> enumCodec(Class<T> cls, T[] vals) {
		return Codec.STRING.xmap(e -> {
			try {
				return Enum.valueOf(cls, e);
			} catch (Exception ex) {
				throw new IllegalArgumentException(e + " is not a valid " + cls.getSimpleName() + ". Valid values are: " + List.of(vals));
			}
		}, Enum::name);
	}

}
