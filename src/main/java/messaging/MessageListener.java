package messaging;

import messaging.commands.debug.DebugCommand;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * @author Toby T. van Willegen
 * @version 1.1, 2017-12-19.
 */
public class MessageListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();
        String content = message.getContentRaw();
        String stringMessage = null;

        // getRawContent() is an atomic getter
        // getContent() is a lazy getter which modifies the content for e.g. console view (strip
        // discord formatting)

        if (content.startsWith("!debug")) {
            stringMessage = DebugCommand.getResponse(content);

        } else if (content.startsWith("!setMainChannel")) {
            MessageSender.setMainChannel(channel);
            stringMessage = "Quack! This is where I make my nest!";

        } else if (content.contains("WelcomeMessage")) {
            stringMessage = DebugCommand.getResponse(content);

        } else if (content.startsWith("!help")) {
            MessageSender.sendMessage(
                "**GENERAL**\n"
                    + "_!setMainChannel_ - Used to set the main channel, used for user joined "
                    + "messages and nickname changed messages.\n"
                    + "_!debug_ - What? This is used when you need to debug of course! Let this "
                    + "little quacker help you!\n\n"

                    + "**WELCOME MESSAGES**\n"
                    + "_!setWelcomeMessage_ - Used to set the welcome message. Use <name> in the "
                    + "provided string to personalise the message!\n"
                    + "_!welcomeMessage_ - Shows the current welcome message\n"
                    + "_!resetWelcomeMessage_ - resets the welcome message to the standard.\n\n"
                , channel);

        } else if (content.startsWith("!color #") || content.startsWith("!colour #")) {
            if (event.getGuild().getSelfMember().getPermissions()
                     .contains(Permission.MANAGE_ROLES)) {

                String colour = content.replace("!color ", "");
                colour = colour.replace("!colour ", "");
                colour = colour.replace("#", "0x");


                if (event.getGuild().getRolesByName("@USER-" + colour, false).size() == 0) {
                    Role everyone = event.getGuild().getRolesByName("@everyone", false).get(0);
                    event.getGuild().getController().createCopyOfRole(everyone)
                         .setName("USER-" + colour)
                         .setColor(Integer.parseInt(colour))
                         .queue();
                }
                Role userColorRole = event.getGuild().getRolesByName("@USER-" + colour, false)
                                          .get(0);
                event.getGuild().getController().addRolesToMember(event.getMember(), userColorRole)
                     .queue();
            } else {
                stringMessage = "Quack quack quackity quack! Eeeh I mean, sorry I don't have "
                                    + "permission to manage roles!";
            }
        }//mention id: <@162919329686355979>
        MessageSender.sendMessage(stringMessage, channel);
    }
}