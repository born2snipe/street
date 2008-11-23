package org.codeclub.mina.smtp;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


public class SmtpIoHandler extends IoHandlerAdapter {
    private List<Command> commands = new ArrayList<Command>();

    public SmtpIoHandler() {
        commands.add(new QuitCommand());
        commands.add(new EhloCommand());
        commands.add(new FromCommand());
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        session.write("220 SMTP Test Server written with Apache MINA");
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        String str = message.toString();

        for (Command command : commands) {
            if (command.isSupported(str)) {
                command.execute(session, str);
                return;
            }
        }

        session.write("500 What are you doing dummy?!");
    }

    public void setCommands(Command... commands) {
        this.commands.addAll(Arrays.asList(commands));
    }
}
