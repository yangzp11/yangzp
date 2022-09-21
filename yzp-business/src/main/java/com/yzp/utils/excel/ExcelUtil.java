package com.yzp.utils.excel;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * ExcelUtil
 *
 * @author YangZhiPeng
 * @date 2022/8/31 13:38
 */
public class ExcelUtil {

    /**
     * 写excel工具类
     *
     * @param response
     * @param clazz
     * @param fileName
     * @param monthSheetWriteHandler 自定义插入内容
     * @return
     * @throws Exception
     */
    public static ExcelWriter writeExcel(HttpServletResponse response, Class<?> clazz, String fileName, MonthSheetWriteHandler monthSheetWriteHandler) throws Exception {

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        response.setHeader("Content-disposition", "attachment;filename= " + fileName + ExcelTypeEnum.XLSX.getValue());
        //内容样式策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //垂直居中,水平居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);

        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);

        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);

        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);

        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);

        //设置 自动换行
        contentWriteCellStyle.setWrapped(true);

        // 字体策略
        WriteFont contentWriteFont = new WriteFont();

        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) 10);
        contentWriteCellStyle.setWriteFont(contentWriteFont);

        //头策略使用默认
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        WriteFont writeFont = new WriteFont();
        //标题实体大小
        writeFont.setFontHeightInPoints((short) 11);
        //标题字体颜色
        //writeFont.setColor(IndexedColors.RED.index);
        //标题加粗
        writeFont.setBold(true);
        headWriteCellStyle.setWriteFont(writeFont);
        //设置标题 背景颜色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.index);
        //插入头部内容
        //导出
        ExcelWriterBuilder write = EasyExcel.write(response.getOutputStream(), clazz)
                .registerWriteHandler(new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle));

        if (ObjectUtil.isNotEmpty(monthSheetWriteHandler)) {
            write.registerWriteHandler(monthSheetWriteHandler);
        }
        return write.build();
    }

}
