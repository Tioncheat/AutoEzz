# AutoEzz

The mod initializes as a Fabric mod by implementing the ModInitializer interface. This allows the mod to run code during the game initialization phase.

The mod listens to the AFTER_RESPAWN event provided by the ServerPlayerEvents class. This event is triggered after a player respawns in the game.

When a player respawns, the mod checks if the player was killed by another player. It does this by checking if the killed player exists in the killers map, which stores the relationship between killed players and their killers.

If the killed player is found in the killers map, the mod retrieves the killer associated with the killed player.

The mod compares the retrieved killer with your player entity (yourPlayerEntity) to determine if you are the killer.

If you are the killer, the mod randomly selects a death message from the DEATH_MESSAGES array. The %s placeholder in the death message is replaced with the killed player's display name.

The mod sends the death message to your player entity using the sendMessage() method of the ServerPlayerEntity class.

The mod removes the killed player from the killers map, as the death message has been sent.

Example of what it will print in the chat:

```bro you died? playername```
