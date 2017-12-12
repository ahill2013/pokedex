package io.github.ahill2013.willow;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.*;

public class CommandHandler {

    // Static Map of commands from string to functional implementation
    private static Map<String, Command> commandMap = new HashMap<>();

    //Statically populate commandMap with functions
    static {

        commandMap.put("ping", (event, args) -> {
            System.out.println("Sending response\n");
            BotUtils.sendMessage(event.getChannel(), "pong");
        });

        commandMap.put("help", (event, args) -> {
            StringBuilder helpMsg = new StringBuilder();
            helpMsg.append("Bot command prefix is: >\n\n");
            helpMsg.append("Available commands:\n");
            helpMsg.append("ping").append(" - an easy way to see if the bot is alive");

            System.out.println("sending response\n");
            BotUtils.sendMessage(event.getChannel(), helpMsg.toString());
        });

    }

    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event) {

        System.out.println("Received message: " + event.getMessage().getContent());
        System.out.println("From channel: " + event.getChannel().getName());
        System.out.println();

        // Split input into array
        String[] argArray = event.getMessage().getContent().split(" ");

        // If there is no command or prefix present, do nothing
        if (argArray.length == 0)
            return;
        // If the first argument does not start with the prefix, do nothing
        if (!argArray[0].startsWith(BotUtils.BOT_PREFIX))
            return;
        // Grab the command without the prefix
        String commandStr = argArray[0].substring(BotUtils.BOT_PREFIX.length());
        // Switch to a List for safer access of arguments
        List<String> argsList = new ArrayList<>(Arrays.asList(argArray));
        argsList.remove(0); // Remove the command

        if (commandMap.containsKey(commandStr))
            commandMap.get(commandStr).runCommand(event, argsList);

    }

}
