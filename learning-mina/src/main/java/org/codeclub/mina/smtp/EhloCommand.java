package org.codeclub.mina.smtp;

import org.apache.mina.core.session.IoSession;


public class EhloCommand implements Command {
    public void execute(IoSession session, String command) {
        session.write("250 How you doin?");
    }

    public boolean isSupported(String command) {
        return command.toLowerCase().startsWith("helo") || command.toLowerCase().startsWith("ehlo");
    }
}
