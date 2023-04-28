package com.yzp.test.charset;

import lombok.SneakyThrows;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2023/2/28 9:07
 */
public class CharsetTest {

    @SneakyThrows
    public static void main(String[] args) {
        //获取GBK的字符集实例
        Charset charset = Charset.defaultCharset();
        //获取字符集实例的 编码器
        CharsetEncoder encoder = charset.newEncoder();
        //获取字符集实例 的解码器
        CharsetDecoder decoder = charset.newDecoder();
        //分配一个字符缓冲区，且添加一些字符，在GBK中一个中文字符需要使用3个字节来表示。
        CharBuffer charBuffer = CharBuffer.allocate(1024);
        charBuffer.put("阿帕奇");
        //切换字符缓冲区为读模式
        charBuffer.flip();
        //使用GBK的字符集实例 对 字符缓冲区进行编码，从而得到一个字节缓冲区。
        ByteBuffer byteBuffer = encoder.encode(charBuffer);
        //输出得到的字节缓冲区中数据
        for (int i = 0; i < 9; i++) {
            System.out.println(byteBuffer.get());
        }
        //切换编码得到的字节缓冲区 为写模式
        byteBuffer.flip();
        //使用GBK的字符集实例 解码 字节缓冲区。
        CharBuffer decodeCharBuffer = decoder.decode(byteBuffer);
        //输出解码后的字节缓冲区
        System.out.println(decodeCharBuffer);
    }

}
