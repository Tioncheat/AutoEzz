package name.modid;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
public class AutoEz implements ModInitializer {
    private static final Map<ServerPlayerEntity, ServerPlayerEntity> killers = new HashMap<>();
    private static final Random random = new Random();

    private final ServerPlayerEntity yourPlayerEntity;

    public AutoEz(ServerPlayerEntity yourPlayerEntity) {
        this.yourPlayerEntity = yourPlayerEntity;
    }

    @Override
    public void onInitialize() {
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            if (alive && killers.containsKey(oldPlayer)) {
                ServerPlayerEntity killer = killers.get(oldPlayer);

                if (killer == yourPlayerEntity) {
                    String deathMessage = getDeathMessage(oldPlayer);
                    Text message = Text.of("[" + deathMessage + "]");
                    killer.sendMessage(message, false);
                }

                killers.remove(oldPlayer);
            }
        });
    }

    private String getDeathMessage(ServerPlayerEntity player) {
        String[] deathMessages = {
                "LMFAO EZZ",
                "bro you died? " + player.getEntityName(),
                "try again next time, " + player.getEntityName(),
                "ez, " + player.getEntityName(),
                "GGWP, " + player.getEntityName(),
                "EZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ " + player.getEntityName()
        };

        return deathMessages[random.nextInt(deathMessages.length)];
    }
}