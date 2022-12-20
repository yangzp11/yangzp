package com.yzp.utils.qrcode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 二维码 添加自定义 logo图标 处理的方法,
 * 模仿微信自动生成二维码效果，有圆角边框，logo和二维码间有空白区，logo带有灰色边框
 */
public class LogoConfig {

    /**
     * 设置 logo
     *
     * @param matrixImage 源二维码图片
     * @return 返回带有logo的二维码图片
     */
    public BufferedImage LogoMatrix(BufferedImage matrixImage) throws IOException {
        //读取二维码图片，并构建绘图对象
        Graphics2D g2 = matrixImage.createGraphics();

        int matrixWidth = matrixImage.getWidth();
        int matrixHeigh = matrixImage.getHeight();

        //读取Logo图片
        BufferedImage logo = ImageIO.read(new File("D:\\qrcode\\微信图片_20221220162724.jpg"));
        //开始绘制图片
        g2.drawImage(logo, matrixWidth / 5 * 2, matrixHeigh / 5 * 2, matrixWidth / 5, matrixHeigh / 5, null);//绘制
        BasicStroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke);// 设置笔画对象
        //指定弧度的圆角矩形
        RoundRectangle2D.Double round = new RoundRectangle2D.Double(matrixWidth / 5.0 * 2, matrixHeigh / 5.0 * 2, matrixWidth / 5.0, matrixHeigh / 5.0, 20, 20);
        g2.setColor(Color.white);
        g2.draw(round);// 绘制圆弧矩形

        //设置logo 有一道灰色边框
        BasicStroke stroke2 = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke2);// 设置笔画对象
        RoundRectangle2D.Double round2 = new RoundRectangle2D.Double(matrixWidth / 5.0 * 2 + 2, matrixHeigh / 5.0 * 2 + 2, matrixWidth / 5.0 - 4, matrixHeigh / 5.0 - 4, 20, 20);
        g2.setColor(new Color(128, 128, 128));
        g2.draw(round2);// 绘制圆弧矩形
        //g2.setBackground(new Color(0, 229, 238));
        g2.dispose();
        matrixImage.flush();
        return matrixImage;
    }
}