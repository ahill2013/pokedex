package io.github.ahill2013.willow;

import sx.blah.discord.api.IDiscordClient;

public class Willow {

    public static void main(String[] args) {

        // Pass the bot token in as a command line argument
        String token = args[0];
        IDiscordClient client = BotUtils.getBuiltDiscordClient(token);

        // Register a listener via the EventSubscriber annotation which allows for organisation and delegation of events
        client.getDispatcher().registerListener(new CommandHandler());

        // Only login after all events are registered otherwise some may be missed.
        client.login();

    }

}
