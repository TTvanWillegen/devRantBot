package messaging.commands.welcomeMessage;

import messaging.StandardMessage;

/**
 * @author Toby T. van Willegen
 * @version 1.1, 2017-12-20.
 */
public class WelcomeMessageCommand {
    public static String getResponse(String content) {
        if (content.startsWith("!setWelcomeMessage ")) {
            String welcomeMessage = content.replace("!setWelcomeMessage ", "");
            StandardMessage.getInstance().setWelcomeMessage(welcomeMessage);
            if (!welcomeMessage.contains("<name>")) {
                return "Quack! I set the welcome message, but are your sure "
                           + "you don't want to personalise it with <name>?";
            } else {
                return "Quack! I set the welcome message!";
            }
        } else if (content.startsWith("!welcomeMessage")) {
            return "Quack! The welcome message would be _" +
                       StandardMessage.getInstance().getWelcomeMessage() +
                       "_";
        } else if (content.startsWith("!resetWelcomeMessage")) {
            StandardMessage.getInstance().resetWelcomeMessage();
            return "Quack! I reset the Welcome Message to standard!";
        }
        return null;
    }

}
