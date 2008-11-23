package org.codeclub.mina.smtp;

import org.apache.mina.core.session.IoSession;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;


public class SmtpIoHandlerTest extends MockObjectTestCase {
    private SmtpIoHandler handler;
    private Mock session;
    private Mock command;

    public void test_messageReceived_SupportedCommand() throws Exception {
        command.expects(once()).method("isSupported").with(eq("new")).will(returnValue(true));
        command.expects(once()).method("execute").with(eq(session.proxy()), eq("new"));

        handler.messageReceived((IoSession) session.proxy(), "new");
    }

    public void test_messageReceived_UnsupportedCommand() throws Exception {
        command.expects(once()).method("isSupported").with(eq("new")).will(returnValue(false));

        session.expects(once()).method("write").with(eq("500 What are you doing dummy?!"));

        handler.messageReceived((IoSession) session.proxy(), "new");
    }

    public void test_sessionCreated() throws Exception {
        session.expects(once()).method("write").with(eq("220 SMTP Test Server written with Apache MINA"));

        handler.sessionCreated((IoSession) session.proxy());
    }

    protected void setUp() throws Exception {
        super.setUp();
        handler = new SmtpIoHandler();
        session = mock(IoSession.class);
        command = mock(Command.class);
        handler.setCommands((Command) command.proxy());
    }
}
