import events.EventListener;
import javax.security.auth.login.LoginException;
import messaging.MessageListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

/**
 * @author Toby T. van Willegen
 * @version 1.1, 2017-12-19.
 */
public class Main {
    public static void main(String[] args) {
        try {
            JDA api = new JDABuilder(AccountType.BOT)
                          .setToken(args[0])
                          .setGame(Game.watching("you debugging."))
                          .buildAsync();
            api.addEventListener(new MessageListener());
            api.addEventListener(new EventListener());
        } catch (LoginException | RateLimitedException e) {
            e.printStackTrace();
        }
    }
}