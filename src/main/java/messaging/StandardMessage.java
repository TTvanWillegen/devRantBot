package messaging;

/**
 * @author Toby T. van Willegen
 * @version 1.1, 2017-12-19.
 */
public class StandardMessage {
    private static final String MESSAGE_WELCOME_STANDARD = "Quack! Welcome to the devRant discord"
                                                               + " <name>!";
    private static final String MESSAGE_LEAVE_STANDARD = "**Beep boop** _eeh_ Quack! "
                                                             + "Unfortunately we "
                                                             + "lost <name>.";

    private static StandardMessage ourInstance = new StandardMessage();
    private String welcomeMessage = MESSAGE_WELCOME_STANDARD;
    private String leaveMessage = MESSAGE_LEAVE_STANDARD;

    private StandardMessage() {
    }

    public static StandardMessage getInstance() {
        return ourInstance;
    }

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    public void resetWelcomeMessage() {setWelcomeMessage(MESSAGE_WELCOME_STANDARD);}

    public String getleaveMessage() {
        return leaveMessage;
    }

    public void setLeaveMessage(String leaveMessage) {
        this.leaveMessage = leaveMessage;
    }

    public void resetLeaveMessage() {setLeaveMessage(MESSAGE_LEAVE_STANDARD);}
}
