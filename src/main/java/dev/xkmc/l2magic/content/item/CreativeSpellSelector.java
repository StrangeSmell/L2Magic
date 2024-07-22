package dev.xkmc.l2magic.content.item;

import dev.xkmc.l2itemselector.select.item.IItemSelector;
import dev.xkmc.l2magic.content.engine.spell.SpellAction;
import dev.xkmc.l2magic.init.registrate.EngineRegistry;
import dev.xkmc.l2magic.init.registrate.LMItems;
import net.minecraft.Util;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.CommonHooks;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CreativeSpellSelector extends IItemSelector {

	private static List<ResourceKey<SpellAction>> getSpells() {
		var reg = CommonHooks.resolveLookup(EngineRegistry.SPELL);
		if (reg == null) return List.of();
		return reg.listElementIds().toList();
	}

	public CreativeSpellSelector(ResourceLocation id) {
		super(id);
	}

	private ItemStack cache = ItemStack.EMPTY;

	@Override
	public boolean test(ItemStack stack) {
		if (stack.is(LMItems.CREATIVE_WAND.get())) {
			cache = stack;
			return true;
		}
		return false;
	}

	@Override
	public int getIndex(Player player) {
		ItemStack main = player.getMainHandItem();
		ItemStack off = player.getOffhandItem();
		ItemStack stack;
		if (test(main)) {
			stack = main;
		} else if (test(off)) {
			stack = off;
		} else return 0;
		var list = getSpells();
		if (list.size() >= 9) return 4;
		var id = WandItem.getSpellId(stack);
		if (id == null) return 0;
		int index = list.indexOf(id);
		return Math.max(index, 0);
	}

	private List<ItemStack> getListGeneric(Function<ResourceKey<SpellAction>, ItemStack> func) {
		var id = WandItem.getSpellId(cache);
		var ans = new ArrayList<ItemStack>();
		var list = getSpells();
		int index = id == null ? -1 : list.indexOf(id);
		if (list.size() < 9) {
			if (index < 0)
				ans.add(cache.copy());
			for (var e : list)
				ans.add(func.apply(e));
			return ans;
		}
		if (index < 0) {
			for (int i = 0; i < 4; i++)
				ans.add(func.apply(list.get(list.size() - 4 + i)));
			ans.add(cache.copy());
			for (int i = 0; i < 4; i++)
				ans.add(func.apply(list.get(i)));
			return ans;
		}
		for (int i = 0; i < 4; i++)
			ans.add(func.apply(list.get((index - 4 + i + list.size()) % list.size())));
		ans.add(func.apply(id));
		for (int i = 0; i < 4; i++)
			ans.add(func.apply(list.get((index + 1 + i) % list.size())));
		return ans;
	}

	@Override
	public List<ItemStack> getDisplayList() {
		var reg = CommonHooks.resolveLookup(EngineRegistry.SPELL);
		if (reg == null) return List.of();
		return getListGeneric(rl -> Util.make(reg.getOrThrow(rl).value().icon().getDefaultInstance(),
				stack -> stack.set(DataComponents.CUSTOM_NAME, Component.translatable(SpellAction.lang(rl.location())))));
	}

	@Override
	public List<ItemStack> getList() {
		return getListGeneric(rl -> WandItem.setSpell(LMItems.CREATIVE_WAND.asStack(), rl));
	}

}
