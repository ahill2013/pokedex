package io.github.ahill2013.willow;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.ArrayList;

public interface Command {

    void runCommand(MessageReceivedEvent event, ArrayList<String> args);

}
