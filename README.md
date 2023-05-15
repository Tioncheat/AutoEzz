# AutoEzz

By using this mod, whenever you kill another player in Minecraft, a random death message will be chosen and sent to the chat, with the name of the killed player included. This adds a fun and interactive element to the game, allowing other players to see your victory message when you eliminate someone.

Example of what it will print in the chat:

```bro you died? playername```

**How this mod works**
Initialization: The mod implements the ClientModInitializer interface, which allows it to initialize and set up the mod when the client starts.

Event Registration: The mod registers an event listener for the AttackEntityCallback event. This event is triggered whenever a player attacks an entity.

Checking for Player Kill: Inside the event listener, the mod checks if the attacked entity is an instance of PlayerEntity (meaning another player) and if that player is dead.

Generating Death Message: If the attacked player is dead, the mod selects a random death message from an array of predefined death messages.

Formatting and Sending Chat Message: The selected death message is formatted by appending the name of the attacked player. The formatted message is then sent to the chat using the sendChatMessage method.

Message Display: The death message is displayed in the chat for everyone to see.
