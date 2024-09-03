package com.yzp.iot.modbus;

import com.github.xingshuangs.iot.common.buff.EByteBuffFormat;
import com.github.xingshuangs.iot.common.enums.EDataType;
import com.github.xingshuangs.iot.common.serializer.ByteArrayParameter;
import com.github.xingshuangs.iot.common.serializer.ByteArraySerializer;
import com.github.xingshuangs.iot.protocol.modbus.service.ModbusTcp;

import java.util.ArrayList;
import java.util.List;

/**
 * desc
 *
 * @author:YangZhiPeng
 * @date: 2024/8/29 16:06
 */
public class ModbusExample {
	    public static void main(String[] args) {
			ModbusTcp modbusTcp = new ModbusTcp(1, "192.168.0.1", 502);

        System.out.println(modbusTcp.readCoil(2011, 2).get(0));
        System.out.println(modbusTcp.readBoolean(2011, 2));
        modbusTcp.writeFloat32(1110, 39.2F);
        System.out.println(modbusTcp.readFloat32(1110, EByteBuffFormat.BA_DC));

		//批量读取
        int startAddress = 11;
        //计算这个组地址的差值，确定要读的寄存器数量，最后需要再加2（上面限制了临界点123+2，最大quantity只能为125）
        byte[] bytes = modbusTcp.readHoldRegister(startAddress, 125);
        List<ByteArrayParameter> list = new ArrayList<>();
        //(1101-1100)*2(字节偏移量，假设从1100开始读的，读第二个1101), 4(根据类型，float2个字节就是4个byte),1(代表只读一个)
        list.add(new ByteArrayParameter( (111 - startAddress) * 2, 4,
            1, EDataType.INT16, false, EByteBuffFormat.BA_DC));
        list.add(new ByteArrayParameter( (116 - startAddress) * 2, 4,
            1, EDataType.INT16, false, EByteBuffFormat.BA_DC));
        list.add(new ByteArrayParameter( (120 - startAddress) * 2, 4,
            1, EDataType.INT16, false, EByteBuffFormat.BA_DC));
        ByteArraySerializer serializer = ByteArraySerializer.newInstance();
        List<ByteArrayParameter> valueList = serializer.extractParameter(list, bytes);
        System.out.println(valueList);


        modbusTcp.close();
    }
}
