package com.yzp.socket;

import cn.hutool.extra.spring.SpringUtil;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;

/**
 * desc
 *
 * @author:YangZhiPeng
 * @date: 2024/2/4 16:01
 */
@Slf4j
public class SocketIOUtils {

	private static final SocketIOServer SOCKETIO_SERVER = SpringUtil.getBean(SocketIOServer.class);

	public static void sendAllMessage( String eventName, Object data) {
		for (SocketIOClient client : SOCKETIO_SERVER.getAllClients()) {
			sendMessageToClient(client, eventName, data);
		}
	}

	public static void sendSpaceMessage(String namespace, String eventName, Object data) {
		SocketIONamespace ns = SOCKETIO_SERVER.getNamespace(namespace);
		if (null == ns){
			log.error("namespace not exist "+ namespace);
			return;
		}
		for (SocketIOClient client : ns.getAllClients()) {
			sendMessageToClient(client, eventName, data);
		}
	}

	public static void sendMessageToClient(SocketIOClient client, String eventName, Object data) {
		if (client != null) {
			// 发送一个自定义事件以及附带的数据到指定客户端
			client.sendEvent(eventName, data);
		} else {
			// 客户端不存在或已断开连接，处理这种情况
			log.error("Failed to send event as client is not connected");
		}
	}

}
