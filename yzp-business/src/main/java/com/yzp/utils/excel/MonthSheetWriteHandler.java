package com.yzp.utils.excel;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * Sheet Handler
 *
 * @author YangZhiPeng
 * @date 2022/8/31 13:39
 */
public class MonthSheetWriteHandler implements SheetWriteHandler {

    /**
     * 第几行开始 0
     */
    private final Integer firstRow;
    /**
     * 到第几行结束
     */
    private final Integer lastRow;
    /**
     * 到第几列开始
     */
    private final Integer firstCol;
    /**
     * 到第几列结束
     */
    private final Integer lastCol;

    /**
     *插入的内容
     */
    private String text="内容";

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        Workbook workbook = writeWorkbookHolder.getWorkbook();

        Sheet sheet = workbook.getSheetAt(0);

        //设置标题

        Row row2 = sheet.createRow(0);

        row2.setHeight((short) 500);

        Cell cell1 = row2.createCell(0);

        cell1.setCellValue(text);

        CellStyle cellStyle = workbook.createCellStyle();

        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        Font font = workbook.createFont();

        font.setBold(true);

        font.setFontHeight((short) 250);

        cellStyle.setFont(font);

        cell1.setCellStyle(cellStyle);
        //row表示占多少单元
        sheet.addMergedRegionUnsafe(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));

    }

    public MonthSheetWriteHandler(Integer firstRow, Integer lastRow, Integer firstCol, Integer lastCol, String text) {
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        this.firstCol = firstCol;
        this.lastCol = lastCol;
        this.text = text;
    }

}
