package com.yzp.utils.qrcode;

import cn.hutool.core.util.CharsetUtil;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.experimental.UtilityClass;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2022/12/12 16:08
 */
@UtilityClass
public class QRCodeUtils {

    /**
     * 生成二维码图片
     *
     * @param useLog 使用图标
     */
    public static void encodeQRCode(Boolean useLog) throws IOException, WriterException {
        String contents = "内容";
        // 二维码内容 //https://www.baidu.com
        int width = 430; // 二维码图片宽度 300
        int height = 430; // 二维码图片高度300

        String format = "jpg";// 二维码的图片格式 gif

        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        // 指定纠错等级,纠错级别（L 7%、M 15%、Q 25%、H 30%）
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 内容所使用字符集编码
        hints.put(EncodeHintType.CHARACTER_SET, CharsetUtil.UTF_8);
//      hints.put(EncodeHintType.MAX_SIZE, 350);//设置图片的最大值
//      hints.put(EncodeHintType.MIN_SIZE, 100);//设置图片的最小值
        hints.put(EncodeHintType.MARGIN, 1);//设置二维码边的空度，非负数

        BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,//要编码的内容
                //编码类型，目前zxing支持：Aztec 2D,CODABAR 1D format,Code 39 1D,Code 93 1D ,Code 128 1D,
                //Data Matrix 2D , EAN-8 1D,EAN-13 1D,ITF (Interleaved Two of Five) 1D,
                //MaxiCode 2D barcode,PDF417,QR Code 2D,RSS 14,RSS EXPANDED,UPC-A 1D,UPC-E 1D,UPC/EAN extension,UPC_EAN_EXTENSION
                BarcodeFormat.QR_CODE,
                width, //条形码的宽度
                height, //条形码的高度
                hints);//生成条形码时的一些配置,此项可选

        // 生成二维码
        File outputFile = new File("D:" + "\\qrcode" + "\\mycode11.gif");//指定输出路径
        MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile, useLog);
    }

    public static String deEncodeByPath(BufferedImage image) {
        String content = null;
        //BufferedImage image;
        try {
            //URL url = new URL(path);
            //image = ImageIO.read(path);
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap<>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);
            System.out.println("图片中内容 : " + result.getText());
            content = result.getText();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return content;
    }

    public static void main(String[] args) throws IOException, WriterException {
        encodeQRCode(Boolean.TRUE);
//        File file = new File("D:" + "\\qrcode" + "\\mycode2.gif");
//        //FileInputStream inputStream = new FileInputStream(file);
//        BufferedImage image = ImageIO.read(file);
//        String content = deEncodeByPath(image);
//        System.out.println(content);
        //二维码有效期
    }


}
