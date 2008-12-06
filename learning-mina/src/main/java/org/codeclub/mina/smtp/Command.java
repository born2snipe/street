package org.codeclub.mina.smtp;

import org.apache.mina.core.session.IoSession;

/**
 * Created by IntelliJ IDEA.
 * User: dan
 * Date: Nov 23, 2008
 * Time: 10:34:60000 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Command {
    void execute(IoSession session, String command);

    boolean isSupported(String command);
}
