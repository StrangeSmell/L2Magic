package dev.xkmc.l2magic.events;

import dev.xkmc.l2magic.init.L2Magic;
import net.neoforged.fml.common.EventBusSubscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

@EventBusSubscriber(modid = L2Magic.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ClientEventHandler {

	private static List<BooleanSupplier> TASKS = new ArrayList<>();

	public ClientEventHandler() {
	}

	public static synchronized void schedule(Runnable runnable) {
		TASKS.add(() -> {
			runnable.run();
			return true;
		});
	}

	public static synchronized void schedulePersistent(BooleanSupplier runnable) {
		TASKS.add(runnable);
	}

	private static synchronized void execute() {
		if (!TASKS.isEmpty()) {
			List<BooleanSupplier> temp = TASKS;
			TASKS = new ArrayList();
			temp.removeIf(BooleanSupplier::getAsBoolean);
			temp.addAll(TASKS);
			TASKS = temp;
		}
	}

}
