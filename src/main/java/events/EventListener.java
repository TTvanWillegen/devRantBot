package events;

import messaging.MessageSender;
import messaging.StandardMessage;
import net.dv8tion.jda.client.events.group.GroupUserLeaveEvent;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
//import net.dv8tion.jda.core.events.guild.member.GuildMemberNickChangeEvent;

/**
 * @author Toby T. van Willegen
 * @version 1.1, 2017-12-19.
 */
public class EventListener implements net.dv8tion.jda.core.hooks.EventListener {
    @Override
    public void onEvent(Event event) {
        if (event instanceof GuildMemberJoinEvent) {
            GuildMemberJoinEvent guildMemberJoinEvent = (GuildMemberJoinEvent) event;

            String welcomeMessage = StandardMessage.getInstance().getWelcomeMessage();

            if(welcomeMessage != null) {
                MessageSender.sendMessage(welcomeMessage.replace("<name>", guildMemberJoinEvent
                                                                               .getMember()
                                                                               .getAsMention()));
            }
//        } else if (event instanceof GuildMemberNickChangeEvent) {
//            GuildMemberNickChangeEvent guildMemberNickChangeEvent =
//                (GuildMemberNickChangeEvent) event;
//
//
//            String prevNick = guildMemberNickChangeEvent.getPrevNick();
//            if (prevNick == null) {
//                prevNick = guildMemberNickChangeEvent.getUser().getName();
//            }
//
//            String newNick = guildMemberNickChangeEvent.getNewNick();
//            if (newNick == null) {
//                newNick = guildMemberNickChangeEvent.getMember().getEffectiveName();
//            }
//
//            MessageSender.sendMessage("Quack! " + prevNick + " is now known as " + newNick);
        } else if (event instanceof GroupUserLeaveEvent) {
            GroupUserLeaveEvent groupUserLeaveEvent = (GroupUserLeaveEvent) event;
            String name = groupUserLeaveEvent.getUser().getName();

            String leaveMessage = StandardMessage.getInstance().getleaveMessage();

            MessageSender.sendMessage(leaveMessage.replace("<name>", name));
        }
    }
}
