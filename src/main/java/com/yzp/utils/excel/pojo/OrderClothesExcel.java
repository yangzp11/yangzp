package com.yzp.utils.excel.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;


/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2022/8/31 13:43
 */
@Data
@ContentRowHeight(15) //内容行高
@HeadRowHeight(20)//表头行高q
public class OrderClothesExcel {

    /**
     * 序号，自增
     */
    @ExcelProperty(value = "序号", index = 0)
    @ColumnWidth(20)
    private Long serialNumber;

    /**
     * 订单衣物主键
     */
    @ExcelProperty(value = "订单衣物主键", index = 1)
    @ColumnWidth(20)
    private String orderClothesId;

    /**
     * 衣物ID
     */
    @ExcelProperty(value = "衣物ID", index = 2)
    @ColumnWidth(20)
    private Integer clothesId;

    /**
     * 衣物号
     */
    @ExcelProperty(value = "衣物号", index = 3)
    @ColumnWidth(20)
    private String clothesNum;

    /**
     * 衣物状态
     */
    @ExcelProperty(value = "衣物状态", index = 4)
    @ColumnWidth(20)
    private Integer clothesStatus;

    /**
     * 衣物名称
     */
    @ExcelProperty(value = "衣物名称", index = 5)
    @ColumnWidth(20)
    private String clothesName;

    /**
     * 订单主键
     */
    @ExcelProperty(value = "订单主键", index = 6)
    @ColumnWidth(20)
    private String orderId;

    /**
     * 最新反洗时间
     */
    @ExcelProperty(value = "最新反洗时间", index = 7)
    @ColumnWidth(20)
    private String rewashTime;

    /**
     * 操作
     */
    @ExcelProperty(value = "操作", index = 8)
    @ColumnWidth(20)
    private Integer operateStatus;

    /**
     * 最新操作时间
     */
    @ExcelProperty(value = "最新操作时间", index = 9)
    @ColumnWidth(20)
    private String operateTime;
}
