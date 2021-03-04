package com.bosssoft.hr.train.j2se.basic.example.socket;


import com.bosssoft.hr.train.j2se.basic.example.collection.Constraint;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * @description: 服务端
 * @author: lwh
 * @create: 2020/11/5 14:49
 * @version: v1.0
 **/
@Slf4j
public class ServerSocket implements Starter {
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
    private Byte    Buffer write = ByteBuffer.allocate(BUFFER);

    /**
     * 字符集设置
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
        ServerSocketChannel server = null;
        try {
            // 服务初始化
            server = ServerSocketChannel.open();
            // 绑定主机和监听端口
            server.bind(new InetSocketAddress(HOST, PORT));
            // 设置为非阻塞模式
            server.configureBlocking(false);
            selector = Selector.open();
            // 注册OP_ACCEPT事件（即监听该事件，如果有客户端发来连接请求，则该键在select()后被选中）
            server.register(selector, SelectionKey.OP_ACCEPT);
            // 轮询服务
            while (true) {
                // 选择就绪事件
                selector.select();
                // 获取已选择的就绪事件集合
                Set<SelectionKey> keys = selector.selectedKeys();
                for (SelectionKey key : keys) {
                    // 对事件进行处理
                    handle(key);
                }
                // 清空事件
                keys.clear();
            }
        } catch (ClosedSelectorException e) {
            log.info(Constraint.LOG_TAG, "客户端已断开连接");
        } catch (IOException e) {
            log.info(Constraint.LOG_TAG, e.getMessage());
        } finally {
            close(server);
            close(selector);
        }

    }

    /**
     * 事件处理
     */
    private void handle(SelectionKey key) throws IOException {
        if (key.isAcceptable()) {
            // ACCEPT事件
            ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
            SocketChannel client = serverChannel.accept();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
        } else if (key.isReadable()) {
            // READ事件
            SocketChannel client = (SocketChannel) key.channel();
            // 接受客户端发送的数据
            String message = receive(client);
            if (message.isEmpty()) {
                // 客户端异常
                key.cancel();
                selector.wakeup();
            } else {
                log.info(Constraint.LOG_TAG, message);
            }
            // 回应客户端
            send(client, "Hello Client");
            // 退出
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
