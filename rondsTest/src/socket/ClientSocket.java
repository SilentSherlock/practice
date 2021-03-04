package com.bosssoft.hr.train.j2se.basic.example.socket;

import com.bosssoft.hr.train.j2se.basic.example.collection.Constraint;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * @description: 客户端 与服务端类似
 * @author: lwh
 * @create: 2020/11/5 14:49
 * @version: v1.0
 **/
@Slf4j
public class ClientSocket implements Starter {
    /**
     * 主机名
     **/
    private static final String HOST = "localhost";

    /**
     * 指定端口号
     */
    private static final int PORT = 9999;

    /**
     * 缓冲大小
     */
    private static final int BUFFER = 4096;

    /**
     * 读缓冲
     */
    private ByteBuffer read = ByteBuffer.allocate(BUFFER);

    /**
     * 写缓冲
     */
    private ByteBuffer write = ByteBuffer.allocate(BUFFER);

    /**
     * 字符集
     */
    private Charset charset = StandardCharsets.UTF_8;

    /**
     * 选择器
     */
    private Selector selector;

    /**
     * socket 初始化工作 ip指定 端口分配 等
     */
    @Override
    public void start() {
        SocketChannel client = null;
        try {
            client = SocketChannel.open();
            client.configureBlocking(false);
            selector = Selector.open();
            client.register(selector, SelectionKey.OP_CONNECT);
            client.connect(new InetSocketAddress(HOST, PORT));
            while (true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                for (SelectionKey key : keys) {
                    handle(key);
                }
                keys.clear();
            }
        } catch (ClosedSelectorException e) {
            log.info(Constraint.LOG_TAG, "服务端已断开连接");
        } catch (IOException e) {
            log.info(Constraint.LOG_TAG, e.getMessage());
        } finally {
            close(client);
            close(selector);
        }
    }

    /**
     * 事件处理
     */
    private void handle(SelectionKey key) throws IOException {
        if (key.isConnectable()) {
            SocketChannel clientChannel = (SocketChannel) key.channel();
            if (clientChannel.isConnectionPending()) {
                clientChannel.finishConnect();
            }
            clientChannel.register(selector, SelectionKey.OP_READ);
            send(clientChannel, "Hello Server");
        } else if (key.isReadable()) {
            SocketChannel clientChannel = (SocketChannel) key.channel();
            String message = receive(clientChannel);
            if (message.isEmpty()) {
                close(selector);
            } else {
                log.info(Constraint.LOG_TAG, message);
            }
            close(selector);
        }
    }

    /**
     * 接收消息
     */
    private String receive(SocketChannel client) throws IOException {
        read.clear();
        StringBuilder msg = new StringBuilder();
        while (client.read(read) > 0) {
            read.flip();
            msg.append(charset.decode(read));
        }
        return msg.toString();
    }

    /**
     * 发送消息
     */
    private void send(SocketChannel client, String message) throws IOException {
        write.clear();
        write.put(charset.encode(message));
        write.flip();
        while (write.hasRemaining()) {
            client.write(write);
        }
    }

    /**
     * 关闭资源
     */
    private void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                log.info(Constraint.LOG_TAG, e.getMessage());
            }
        }
    }
}
