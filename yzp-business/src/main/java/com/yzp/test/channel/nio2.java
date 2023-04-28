package com.yzp.test.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2023/2/22 17:57
 */
public class nio2 {
    //Datagram
    public static void main(String[] args) throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();

        // 构建 buffer 数据
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put(Charset.defaultCharset().encode("test"));

        // 切换到 buffer 的读模式
        buffer.flip();
        datagramChannel.send(buffer, new InetSocketAddress("localhost", 9090));
    }
}
