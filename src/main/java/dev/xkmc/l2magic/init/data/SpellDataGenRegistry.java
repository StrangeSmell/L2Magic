package dev.xkmc.l2magic.init.data;

import dev.xkmc.l2magic.init.data.spell.ArrowSpells;
import dev.xkmc.l2magic.init.data.spell.BlockSpells;
import dev.xkmc.l2magic.init.data.spell.EchoSpells;
import dev.xkmc.l2magic.init.data.spell.MasterSpark;
import dev.xkmc.l2magic.init.data.spell.fire.FlameCharge;
import dev.xkmc.l2magic.init.data.spell.fire.FlamePillar;
import dev.xkmc.l2magic.init.data.spell.fire.FlameSpells;
import dev.xkmc.l2magic.init.data.spell.fire.MagmaShield;
import dev.xkmc.l2magic.init.data.spell.ground.EarthSpike;
import dev.xkmc.l2magic.init.data.spell.ground.MagnetCore;
import dev.xkmc.l2magic.init.data.spell.ice.IcyFlash;
import dev.xkmc.l2magic.init.data.spell.ice.WinterStorm;

import java.util.List;

public class SpellDataGenRegistry {

	public static final List<SpellDataGenEntry> LIST = List.of(
			new WinterStorm(),
			new FlameSpells(),
			new ArrowSpells(),
			new BlockSpells(),//id: 600
			new MagnetCore(),  // id:3000
			new MasterSpark(),
			new IcyFlash(),  // id:3100
			new EarthSpike(),  // id:3200
			new EchoSpells(),
			new FlameCharge(),  // id:3300
			new MagmaShield(),  // id:3400
			new FlamePillar()	// id:3500
	);

}
