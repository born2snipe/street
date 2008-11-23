package org.codeclub.mina.smtp;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;


public class SmtpServer {
    private IoAcceptor acceptor;

    public SmtpServer() {
        acceptor = new NioSocketAcceptor();

        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));

        acceptor.setHandler(new SmtpIoHandler());

        acceptor.getSessionConfig().setReadBufferSize(2048);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
    }


    public void startUp(int port) {
        try {
            acceptor.bind(new InetSocketAddress(port));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void shutdown() {
        if (acceptor != null) {
            acceptor.unbind();
        }
    }

    public static void main(String args[]) {
        new SmtpServer().startUp(1025);
    }
}
