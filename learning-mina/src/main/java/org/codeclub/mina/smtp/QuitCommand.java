package org.codeclub.mina.smtp;

import org.apache.mina.core.session.IoSession;


public class QuitCommand implements Command {
    public void execute(IoSession session, String command) {
        session.close();
    }

    public boolean isSupported(String command) {
        return "QUIT".equalsIgnoreCase(command);
    }
}
