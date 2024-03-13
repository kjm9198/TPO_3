/**
 *
 *  @author Bożek Michał S24864
 *
 */

package zad1;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Server {
    String host;
    int port;
    private final ServerSocketChannel ssc;
    SelectionKey sKey;
    Map<SocketChannel, String> cNames;
    Map<SocketChannel, String> cLogs;
    private Selector selector = null;
    StringBuilder log;
    volatile boolean status;

    Server(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        cNames = new HashMap<>();
        cLogs = new HashMap<>();
        log = new StringBuilder();
        ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(host, port));
        ssc.configureBlocking(false);
        selector = Selector.open();
        sKey = ssc.register(selector, SelectionKey.OP_ACCEPT);
        log = new StringBuilder();
    }

    public void startServer() {
        new Thread(() -> {
            status = true;
            while (status) {
                try {
                    selector.select();
                    Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                    Set<SelectionKey> keysSelection = selector.selectedKeys();
                    Iterator<SelectionKey> selectionKeyIterator = selectionKeySet.iterator();
                    while (selectionKeyIterator.hasNext()) {
                        SelectionKey key = selectionKeyIterator.next();
                        selectionKeyIterator.remove();
                        if (key.isAcceptable()) {
                            SocketChannel clientChannel = ssc.accept();
                            clientChannel.configureBlocking(false);
                            clientChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                            continue;
                        }
                        if (key.isReadable()) {
                            SocketChannel clientChannel = (SocketChannel) key.channel();
                            serviceRequest(clientChannel);
                        }

                    }
                } catch (IOException e) {
                }
            }
        }).start();
    }

    private static Charset charset = StandardCharsets.UTF_8;

    public void stopServer() {

        status = false;

    }

    public String getServerLog() {

        return log.toString();
    }

    private void serviceRequest(SocketChannel socketChannel) {
        if (!socketChannel.isOpen()) return;
        StringBuffer str = new StringBuffer();
        str.setLength(0);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        byteBuffer.clear();
        try {
            for (int bytesRead = socketChannel.read(byteBuffer); bytesRead > 0; bytesRead = socketChannel.read(byteBuffer)) {
                byteBuffer.flip();
                CharBuffer charBuffer = charset.decode(byteBuffer);
                str.append(charBuffer);
            }
            handleRequest(socketChannel, str.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleRequest(SocketChannel socketChannel, String request) throws IOException {
        StringBuilder response = new StringBuilder();
        String[] array = new String[4];
        array[3] = "";
        if (request.contains("login")) {

            String str = "logged in";
            response.append(str);
            cNames.put(socketChannel, request.substring(request.indexOf(' ') + 1));
            log.append(cNames.get(socketChannel) + " logged in at " + LocalTime.now() + '\n');
            cLogs.put(socketChannel, "=== " + cNames.get(socketChannel) + " log start ===" + '\n' + "logged in" + '\n');

        } else if (request.equals("bye")) {
            String str = "logged out";
            response.append(str);
            log.append(cNames.get(socketChannel) + " logged out at " + LocalTime.now() + '\n');
            cLogs.put(socketChannel, cLogs.get(socketChannel) + str + '\n' + "=== " + cNames.get(socketChannel) + " log end ===" + '\n');
        } else if (request.equals("bye and log transfer")) {
            String str = "logged out";
            cLogs.put(socketChannel, cLogs.get(socketChannel) + str + '\n' + "=== " + cNames.get(socketChannel) + " log end ===" + '\n');
            log.append(cNames.get(socketChannel) + " logged out at " + LocalTime.now() + '\n');
            response.append(cLogs.get(socketChannel));
        } else {
            String result = Time.timePassed(request.substring(0, request.indexOf(' ')), request.substring(request.indexOf(' ') + 1));
            response.append(result);
            log.append(cNames.get(socketChannel) + " request at " + LocalTime.now() + ": \"" + request + "\"" + '\n');
            cLogs.put(socketChannel, cLogs.get(socketChannel) + "Request: " + request + '\n' + "Result: " + '\n' + result + '\n');
        }
        ByteBuffer out = ByteBuffer.allocateDirect(response.toString().getBytes().length);
        out.put(charset.encode(response.toString()));
        out.flip();
        socketChannel.write(out);
    }
}