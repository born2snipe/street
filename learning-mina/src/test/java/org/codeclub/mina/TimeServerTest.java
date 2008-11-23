package org.codeclub.mina;

import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class TimeServerTest extends TestCase {

    public void test() throws IOException {
        TimeServer server = new TimeServer();

        server.startUp();

        Socket s = new Socket("localhost", 9123);

        try {
            send(s, "hello\n");
            System.out.println("getContent() = " + getContent(s));

        } finally {
            server.shutdown();
            s.close();
        }
    }

    private void send(Socket s, String text) throws IOException {
        OutputStream output = s.getOutputStream();
        output.write(text.getBytes());
        output.flush();
    }


    private String getContent(Socket s) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        InputStream input = null;
        try {
            input = s.getInputStream();
            int len = 0;
            byte buffer[] = new byte[1024];
            while ((len = input.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            return new String(baos.toString());
        } finally {
            if (input != null) input.close();
        }
    }
}
