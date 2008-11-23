package org.codeclub.mina.smtp;

import org.jmock.MockObjectTestCase;
import org.jmock.Mock;
import org.apache.mina.core.session.IoSession;


public class QuitCommandTest extends MockObjectTestCase {
    private QuitCommand command;
    private Mock session;

    public void test_execute() {
        session.expects(once()).method("close");

        command.execute((IoSession) session.proxy(), "QUIT");
    }

    public void test_isSupported() {
        assertTrue(command.isSupported("QUIT"));
        assertTrue(command.isSupported("quit"));
        assertTrue(command.isSupported("QuiT"));
        assertFalse(command.isSupported("new"));
    }

    protected void setUp() throws Exception {
        super.setUp();
        command = new QuitCommand();
        session = mock(IoSession.class);
    }
}
