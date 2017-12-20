package messaging;

import net.dv8tion.jda.core.entities.MessageChannel;

/**
 * @author Toby T. van Willegen
 * @version 1.1, 2017-12-19.
 */
public class MessageSender {
    private static MessageChannel mainChannel = null;

    public static void setMainChannel(MessageChannel channel) {
        mainChannel = channel;
    }

    public static void sendMessage(String message) {
        sendMessage(message, mainChannel);
    }

    public static void sendMessage(String message, MessageChannel channel) {
        if (message != null) {
            channel.sendMessage(message).queue();
        }
    }
}
