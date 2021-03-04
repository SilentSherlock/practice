package problem;

/**
 * Date:2020/11/18
 * Description: optional class description
 **/

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * 抽象NIO通信中的通用方法
 * */
interface NIOPort {
    /**
     * 对触发的事件进行相应的处理
     * */
    void handle(SelectionKey key) throws IOException;
    /**
     * 从缓冲中读取字节流到通道中
     * */
    void send(SelectableChannel channel, Buffer buffer, String msg) throws IOException;
    /**
     * 从通道中读取字节流到缓冲中
     * */
    String receive(SelectableChannel channel, Buffer buffer) throws IOException;
    /**
     * 关闭一个流
     * */
    void close(Closeable closeable) throws IOException;
}
/**
 * 客户端
 * */
class NIOClient implements Runnable, NIOPort{

    private final static String local = "localhost";
    private final static int defaultPort = 27149;

    private String host;
    private int port;
    private String name;
    private ByteBuffer bufferReader;
    private ByteBuffer bufferWriter;
    private Selector selector;
    private Charset charset;

    public NIOClient(String host, int port, int capacity, Charset charset, String name) {
        this.host = host;
        this.port = port;
        bufferReader = ByteBuffer.allocate(capacity);
        bufferWriter = ByteBuffer.allocate(capacity);
        this.charset = charset;
        this.name = name;
    }

    public NIOClient(String name) {
        this(local, defaultPort, 4096, StandardCharsets.UTF_8, name);
    }

    @Override
    public void run() {
        SocketChannel client = null;

        try {
            client = SocketChannel.open();
            selector = Selector.open();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_CONNECT);
            client.connect(new InetSocketAddress(local, defaultPort));

            while (true) {
                if (!selector.isOpen()) break;
                selector.select();
                //System.out.println("client:" + selector.keys().size());
                Set<SelectionKey> keys = selector.selectedKeys();
                for (SelectionKey key : keys) {
                    handle(key);
                }
                keys.clear();//清除处理过的事件
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(client);
            close(selector);
        }

    }

    @Override
    public void handle(SelectionKey key) throws IOException {
        if (key.isConnectable()) {
            SocketChannel server = (SocketChannel) key.channel();
            if (server.isConnectionPending()) {
                server.finishConnect();
            }
            server.register(selector, SelectionKey.OP_READ);
            send(server, bufferWriter, "hello server, I'm " + name);
        }else if (key.isReadable()) {
            SocketChannel client = (SocketChannel) key.channel();
            System.out.println(receive(client, bufferReader));
            close(selector);
        }
    }

    @Override
    public void send(SelectableChannel channel, Buffer buffer, String msg) throws IOException {
        SocketChannel socketChannel = (SocketChannel) channel;
        ByteBuffer byteBuffer = (ByteBuffer) buffer;

        byteBuffer.clear();
        byteBuffer.put(charset.encode(msg));//写入编码后的信息
        byteBuffer.flip();//调整指针位置，由写入转为读取
        while (byteBuffer.hasRemaining()) {
            socketChannel.write(byteBuffer);//从缓冲中读取，向通道写入
        }
    }

    @Override
    public String receive(SelectableChannel channel, Buffer buffer) throws IOException {
        StringBuilder builder = new StringBuilder();
        SocketChannel socketChannel = (SocketChannel) channel;
        ByteBuffer byteBuffer = (ByteBuffer) buffer;

        byteBuffer.clear();
        while ((socketChannel.read(byteBuffer)) != 0) {//将通道中的字符读到缓存中
            buffer.flip();//调整指针位置，由写入变为读取
            builder.append(charset.decode(byteBuffer));
        }
        return builder.toString();
    }

    @Override
    public void close(Closeable closeable) {
        try {
            closeable.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/**
 * 服务端
 * */
class NIOServer implements Runnable, NIOPort{

    private final static String local = "localhost";
    private final static int defaultPort = 27149;

    private String host;
    private int port;
    private ByteBuffer bufferReader;
    private ByteBuffer bufferWriter;
    private Selector selector;
    private Charset charset;

    public NIOServer(String host, int port, int capacity, Charset charset) {
        this.host = host;
        this.port = port;
        bufferReader = ByteBuffer.allocate(capacity);
        bufferWriter = ByteBuffer.allocate(capacity);
        this.charset = charset;
    }

    public NIOServer() {
        this(local, defaultPort, 4096, StandardCharsets.UTF_8);
    }

    @Override
    public void run() {
        ServerSocketChannel channel = null;
        try {
            channel = ServerSocketChannel.open();
            channel.bind(new InetSocketAddress(local, defaultPort));
            channel.configureBlocking(false);

            selector = Selector.open();
            channel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                selector.select();
                System.out.println("server:" + selector.keys().size());
                Set<SelectionKey> keys = selector.selectedKeys();
                for (SelectionKey key : keys) {
                    handle(key);
                }
                keys.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(SelectionKey key) throws IOException {
        if (key.isAcceptable()) {
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
        }else if (key.isReadable()) {
            SocketChannel client = (SocketChannel) key.channel();
            String msg = receive(client, bufferReader);
            send(client, bufferWriter, "hello to '" + msg + "' from server");
            close(client);
        }
    }

    @Override
    public void send(SelectableChannel channel, Buffer buffer, String msg) throws IOException {
        SocketChannel socketChannel = (SocketChannel) channel;
        ByteBuffer byteBuffer = (ByteBuffer) buffer;

        byteBuffer.clear();
        byteBuffer.put(charset.encode(msg));//写入编码后的信息
        byteBuffer.flip();//调整指针位置，由写入转为读取
        while (byteBuffer.hasRemaining()) {
            socketChannel.write(byteBuffer);//从缓冲中读取，向通道写入
        }
    }

    @Override
    public String receive(SelectableChannel channel, Buffer buffer) throws IOException {
        StringBuilder builder = new StringBuilder();
        SocketChannel socketChannel = (SocketChannel) channel;
        ByteBuffer byteBuffer = (ByteBuffer) buffer;

        byteBuffer.clear();
        while ((socketChannel.read(byteBuffer)) != 0) {//将通道中的字符读到缓存中
            buffer.flip();//调整指针位置，由写入变为读取
            builder.append(charset.decode(byteBuffer));
        }
        return builder.toString();
    }

    @Override
    public void close(Closeable closeable) {
        try {
            closeable.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
public class NIOTest {
    public static void main(String[] args) {
        Thread server = new Thread(new NIOServer());
        server.start();
        String client = "client";
        for (int i = 0;i < 100;i++) {
            Thread thread = new Thread(new NIOClient(client + i), client+i);
            thread.start();
        }
    }
}
