package org.codeclub.mina.smtp;

import org.jmock.MockObjectTestCase;
import org.jmock.Mock;
import org.apache.mina.core.session.IoSession;


public class EhloCommandTest extends MockObjectTestCase {
    private EhloCommand command;
    private Mock session;

    public void setUp() {
        session = mock(IoSession.class);

        command = new EhloCommand();
    }
    
    public void test_execute() {
        session.expects(once()).method("write").with(eq("250 How you doin?"));

        command.execute((IoSession) session.proxy(), "EHLO server");
    }

    public void test_isSupported() {
        assertTrue(command.isSupported("EHLO"));
        assertTrue(command.isSupported("ehlo"));
        assertTrue(command.isSupported("EhlO"));
        assertTrue(command.isSupported("HELO"));
        assertTrue(command.isSupported("helo"));
        assertTrue(command.isSupported("HelO"));
        assertTrue(command.isSupported("HelO Mr. Smtp Server"));
        assertFalse(command.isSupported("Nope"));
    }
}
