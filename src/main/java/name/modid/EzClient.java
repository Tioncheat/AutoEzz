package name.modid;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.client.MinecraftClient;
public class EzClient implements ClientModInitializer {
    private final String[] deathMessages = {
            "LMFAO EZZ",
            "bro you died? ",
            "try again next time, ",
            "ez, ",
            "Getter better next time, sorry lol, ",
            "EZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ "
    };

    @Override
    public void onInitializeClient() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (entity instanceof PlayerEntity) {
                PlayerEntity attackedPlayer = (PlayerEntity) entity;

                if (attackedPlayer.isDead()) {
                    // Generate a random death message
                    String deathMessage = deathMessages[(int) (Math.random() * deathMessages.length)];

                    // Create the death message text
                    String formattedMessage = deathMessage + attackedPlayer.getName().getString();

                    // Send the death message
                    MinecraftClient.getInstance().player.networkHandler.sendChatMessage(formattedMessage);

                    // Return ActionResult.PASS to allow the attack to proceed
                    return ActionResult.PASS;
                }
            }

            // Return ActionResult.PASS to allow the attack to proceed
            return ActionResult.PASS;
        });
    }
}