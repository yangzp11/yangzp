package com.yzp.iot.s7;

import com.github.xingshuangs.iot.common.enums.EDataType;
import com.github.xingshuangs.iot.protocol.s7.enums.EPlcType;
import com.github.xingshuangs.iot.protocol.s7.serializer.S7Parameter;
import com.github.xingshuangs.iot.protocol.s7.serializer.S7Serializer;
import com.github.xingshuangs.iot.protocol.s7.service.S7PLC;

import java.util.ArrayList;
import java.util.List;

/**
 * desc
 *
 * @author:YangZhiPeng
 * @date: 2024/9/3 11:43
 */
public class SiemensExample {

	public static void main(String[] args) {
		S7PLC s7PLC = new S7PLC(EPlcType.S200_SMART, "192.168.0.24", 102);
		S7Serializer s7Serializer = S7Serializer.newInstance(s7PLC);
		List<S7Parameter> list = new ArrayList<>();
		list.add(new S7Parameter("VW20", EDataType.INT16));
		list.add(new S7Parameter("VD30", EDataType.FLOAT32));
		list.add(new S7Parameter("V1.0", EDataType.BOOL));
		List<S7Parameter> result = s7Serializer.read(list);
		System.out.println(s7PLC.readInt16("VW20"));
		System.out.println(s7PLC.readFloat32("VD30"));
		// s7PLC.writeBoolean("V1.0",false);
		System.out.println(s7PLC.readBoolean("V1.0"));
	}

}
