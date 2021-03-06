package messaging;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import messaging.commands.debug.DebugCommand;
import messaging.commands.welcomeMessage.WelcomeMessageCommand;
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
            stringMessage = WelcomeMessageCommand.getResponse(content);

        } else if (content.startsWith("!help")) {
            MessageSender.sendMessage(
                "**GENERAL**\n"
                    + "_!setMainChannel_ - Used to set the main channel, used for user joined "
                    + "messages and nickname changed messages.\n"
                    + "_!debug_ - What? This is used when you need to debug of course! Let this "
                    + "little quacker help you!\n\n"

                    + "**WELCOME MESSAGES** Current status: "
                    + (WelcomeMessageCommand.isEnabled() ? ":white_check_mark:\n" : ":x:\n")
                    + "_!toggleWelcomeMessage_ - Used to toggle the welcome messaging on and off.\n"
                    + "_!setWelcomeMessage_ - Used to set the welcome message. Use <name> in the "
                    + "provided string to personalise the message!\n"
                    + "_!welcomeMessage_ - Shows the current welcome message\n"
                    + "_!resetWelcomeMessage_ - resets the welcome message to the standard.\n\n"

                    + "**COLOURING**\n"
                    + "_!color <#color>/ !colour <#colour>_ - Used to give your name a pretty "
                    + "colour.\n\n"
                , channel);

        } else if (content.startsWith("!color #") || content.startsWith("!colour #")) {
            if (event.getGuild().getSelfMember().getPermissions()
                     .contains(Permission.MANAGE_ROLES)) {

                String colour = content.replace("!color ", "");
                colour = colour.replace("!colour ", "");


                if (event.getGuild().getRolesByName("USER-" + colour, true).size() == 0) {
                    Role everyone = event.getGuild().getRolesByName("@everyone", false).get(0);
                    event.getGuild().getController().createCopyOfRole(everyone)
                         .setName("USER-" + colour)
                         .setColor(Color.decode(colour))
                         .queue(
                             //If the role is created, remove all assigned USER-# from the user
                             roleCreated -> addRole(event, roleCreated)
                         )
                    ;
                } else {
                    addRole(event, event.getGuild()
                                        .getRolesByName("USER-" + colour, true).get(0));
                }
            } else {
                stringMessage = "Quack quack quackity quack! Eeeh I mean, sorry I don't have "
                                    + "permission to manage roles!";
            }
        }//mention id: <@162919329686355979>
        MessageSender.sendMessage(stringMessage, channel);
    }

    private void addRole(MessageReceivedEvent event, Role newRole) {
        List<Role> allRolesOfUser = event.getMember().getRoles();
        List<Role> rolesOfUserStartingWithUSER = new ArrayList<>();
        for (Role role : allRolesOfUser) {
            if (role.getName().contains("USER-")) {
                rolesOfUserStartingWithUSER.add(role);
            }
        }

        event.getGuild().getController()
             .removeRolesFromMember(event.getMember(), rolesOfUserStartingWithUSER)
             .queue( //If all USER-# are removed, add the new one.
                     rolesRemoved -> event.getGuild().getController()
                                          .addRolesToMember(event.getMember(), newRole)
                                          .queue());
    }
}
