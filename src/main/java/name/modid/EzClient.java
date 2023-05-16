package name.modid;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.client.MinecraftClient;

public class EzClient implements ClientModInitializer {
    private final static String[] deathMessages = {
            "LMFAO EZZ",
            "bro you died? ",
            "try again next time, ",
            "ez, ",
            "Getter better next time, sorry lol, ",
            "EZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ "
    };

    private static boolean modEnabled = true;

    @Override
    public void onInitializeClient() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (entity instanceof PlayerEntity) {
                PlayerEntity attackedPlayer = (PlayerEntity) entity;

                if (attackedPlayer.isDead() && modEnabled) {
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

        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            if (client.player != null) {
                sendChatMessageToPlayer(client.player, "AutoEz has been turned on");
            }
        });

        // Register command to toggle mod on/off
        registerClientCommand();
    }
    private static void registerClientCommand() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(ClientCommandManager.literal("autoez")
                    .executes(context -> {
                        context.getSource().sendFeedback(Text.of("AutoEz is currently " + (modEnabled ? "enabled" : "disabled")));
                        return 1;
                    })
                    .then(ClientCommandManager.literal("on")
                            .executes(context -> {
                                if(modEnabled)
                                {
                                    context.getSource().sendFeedback(Text.of("AutoEz is already turned on"));
                                }
                                modEnabled = true;
                                context.getSource().sendFeedback(Text.of("AutoEz has been turned on"));
                                return 1;
                            }))
                    .then(ClientCommandManager.literal("off")
                            .executes(context -> {
                                if(!modEnabled)
                                {
                                    context.getSource().sendFeedback(Text.of("AutoEz is already turned off"));
                                }
                                modEnabled = false;
                                context.getSource().sendFeedback(Text.of("AutoEz has been turned off"));
                                return 1;
                            })));
        });
    }

    private void sendChatMessageToPlayer(PlayerEntity player, String message) {
        if (player instanceof ServerPlayerEntity) {
            Text text = Text.of(message);
            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(text);
        }
    }
}