package org.codeclub.mina.smtp;

import org.apache.mina.core.session.IoSession;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;


public class FromCommandTest extends MockObjectTestCase {
    private FromCommand command;
    private Mock session;

    public void setUp() {
        session = mock(IoSession.class);

        command = new FromCommand();
    }

    public void test_execute_EmailNotInSession() {
        Email email = new Email();

        session.expects(once()).method("getAttribute").with(eq("message")).will(returnValue(null));
        session.expects(once()).method("setAttribute").with(eq("message"), eq(email));
        session.expects(once()).method("write").with(eq("250 OK"));

        command.execute((IoSession) session.proxy(), "mail from:<joe@yahoo.com>");
    }
    
    public void test_execute_EmailInSession() {
        Email email = new Email();

        session.expects(once()).method("getAttribute").with(eq("message")).will(returnValue(email));
        session.expects(once()).method("write").with(eq("250 OK"));

        command.execute((IoSession) session.proxy(), "mail from:<joe@yahoo.com>");

        assertEquals("joe@yahoo.com", email.getFrom());
    }

    public void testIsSupported() {
        assertTrue(command.isSupported("MAIL FROM"));
        assertTrue(command.isSupported("mail from"));
        assertTrue(command.isSupported("MAIL from"));
        assertTrue(command.isSupported("MAIL from: joe@yahoo.com"));
        assertFalse(command.isSupported("MAIL to"));
    }
}
