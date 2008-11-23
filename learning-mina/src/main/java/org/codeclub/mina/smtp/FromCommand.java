package org.codeclub.mina.smtp;

import org.apache.mina.core.session.IoSession;

import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class FromCommand implements Command {
    private static final Pattern PATTERN = Pattern.compile("[A-Za-z ]+:<(.+)>");

    public void execute(IoSession session, String command) {
        Email email = (Email) session.getAttribute("message");
        if (email == null) {
            email = new Email();
            session.setAttribute("message", email);
        }

        email.setFrom(getEmailAddress(command));
        
        session.write("250 OK");
    }

    private String getEmailAddress(String command) {
        Matcher matcher = PATTERN.matcher(command);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public boolean isSupported(String command) {
        return command.toLowerCase().startsWith("mail from");
    }

}
