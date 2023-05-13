package name.modid;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.GameRules;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
public class AutoEz implements ModInitializer {
    private static final Map<ServerPlayerEntity, ServerPlayerEntity> killers = new HashMap<>();
    private static final Random random = new Random();

    private static final String[] DEATH_MESSAGES = {
            "LMFAO EZ",
            "bro you died? %s",
            "try again next time %s",
            "ez %s",
            "ggwp %s",
            "EZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ %s"
    };

    private ServerPlayerEntity yourPlayerEntity;

    @Override
    public void onInitialize() {
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            if (alive && killers.containsKey(oldPlayer)) {
                ServerPlayerEntity attacker = killers.get(oldPlayer);

                // Check if the killer is you
                if (attacker == yourPlayerEntity) {
                    String deathMessage = DEATH_MESSAGES[random.nextInt(DEATH_MESSAGES.length)];
                    deathMessage = deathMessage.replace("%s", oldPlayer.getDisplayName().getString());
                    Text message = Text.of(deathMessage);
                    attacker.sendMessage(message, false);
                }

                killers.remove(oldPlayer);
            }
        });
    }

    public static void setKiller(ServerPlayerEntity player, ServerPlayerEntity killer) {
        killers.put(player, killer);
    }
}