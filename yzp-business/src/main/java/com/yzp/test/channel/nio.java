package com.yzp.test.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2023/2/22 14:07
 */
public class nio {

    //    public static void main(String[] args) throws IOException {
//        // 指定需要生成的文件名称
//        String targetFileName = "target-file.txt";
//        // 创建 RandomAccessFile, 赋予可读(r)、可写(w)的权限
//        RandomAccessFile accessFile = new RandomAccessFile(targetFileName, "rw");
//        FileChannel fileChannel = accessFile.getChannel();
//
//        // 创建 ByteBuffer 并写入数据
//        ByteBuffer buffer = ByteBuffer.allocate(16);
//        buffer.put("shDEq".getBytes(StandardCharsets.UTF_8));
//        // 切换到 buffer 的读模式
//        buffer.flip();
//        // 调用 write 将 buffer 的数据写入到 channel, channel 再写数据到磁盘文件
//        fileChannel.write(buffer);
//
//        // 相当于清空 buffer
//        buffer.clear();
//        // 将之前写入到 channel 的数据再读入到 buffer
//        fileChannel.read(buffer);
//
//        // 打印 buffer 中的内容
//        System.out.print(buffer.getChar());
//        System.out.print(buffer.getChar());
//        System.out.print(buffer.getChar());
//        System.out.print(buffer.getChar());
//        System.out.print(buffer.getChar());
//
//        fileChannel.close();
//    }
    //ServerSocketChannel
    public static void main(String[] args) throws IOException {
        // 打开一个 ServerSocketChannel 
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //这里我们使用的是 Java NIO 中默认的阻塞模式，仅仅作为一个掩饰，如果想要 ServerSocketChannel 进入非阻塞模式，可在 open 之后，调用:
        //serverSocketChannel.configureBlocking(false);

        // 绑定 9090 端口
        serverSocketChannel.bind(new InetSocketAddress(9090));

        // 开始接受客户端连接
        SocketChannel socketChannel = serverSocketChannel.accept();
        // 获取连接成功
        System.out.printf("socketChannel %s connected\n", socketChannel);
        // 准备 ByteBuffer 以从 socketChannel 中读取数据
        ByteBuffer buffer = ByteBuffer.allocate(16);

        // 开始读取数据
        System.out.println("before read");
        int read = socketChannel.read(buffer);
        System.out.printf("read complete, read bytes length: %s \n", read);

        printBuffer(buffer);
    }
    //SocketChannel
    public static void main2(String[] args) throws IOException {
        // 打开一个 SocketChannel
        SocketChannel socketChannel = SocketChannel.open();
        // 连接到 localhost 的 9090 端口
        socketChannel.connect(new InetSocketAddress("localhost", 9090));

        // 准备 ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put(Charset.defaultCharset().encode("test"));

        // 将 buffer 切换成读模式 & 向 channel 中写入数据
        buffer.flip();
        socketChannel.write(buffer);
    }
    //Datagram
    public static void main3(String[] args) throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();

        // 构建 buffer 数据
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put(Charset.defaultCharset().encode("test"));

        // 切换到 buffer 的读模式
        buffer.flip();
        datagramChannel.send(buffer, new InetSocketAddress("localhost", 8080));
    }
    public static void main4(String[] args) throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.bind(new InetSocketAddress(9090));

        ByteBuffer buffer = ByteBuffer.allocate(16);
        datagramChannel.receive(buffer);

        printBuffer(buffer);
    }

    /**
     * 打印出ByteBuf的信息
     */
    private static void printBuffer(ByteBuffer buffer) {
        System.out.println("capacity：" + buffer.capacity());
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.print(buffer.get());
        }
    }

}
