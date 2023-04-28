package com.yzp.test.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2023/2/22 17:59
 */
public class nio4 {

    public static void main(String[] args) throws IOException {
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
