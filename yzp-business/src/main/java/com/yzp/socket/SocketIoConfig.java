package com.yzp.socket;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.store.RedissonStoreFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

/**
 * desc
 *
 * @author:YangZhiPeng
 * @date: 2024/2/2 17:38
 */
@Configurable
@Slf4j
@RequiredArgsConstructor
public class SocketIoConfig {

	@Bean("SocketIOServer")
	public SocketIOServer createSocketIOServer(RedissonClient redissonClient) {
		//socketIo的配置
		Configuration config = new Configuration();
		config.setHostname("192.168.1.120");
		//注意：开启socket的端口和spring tomcat服务的端口不能是同一个
		config.setPort(9090);
		config.setPingTimeout(500);
		config.setPingInterval(500);

		//config.setBossThreads(1);
		//config.setWorkerThreads(20);
		config.setAllowCustomRequests(true);

		//json数据的支持（默认就是JacksonJsonSupport）
		//config.setJsonSupport(new JacksonJsonSupport());
		//Access-Control-Allow-Origin 跨域支持
		//config.setOrigin("localhost");
		//redisson存储
		config.setStoreFactory(new RedissonStoreFactory(redissonClient));
		//server 自定义权限和异常监听
		// config.setAuthorizationListener(socketAuthorizationListener);
		// config.setExceptionListener(socketExceptionListener);

		//socket的配置
		SocketConfig socketConfig = new SocketConfig();
		socketConfig.setReuseAddress(true);
		socketConfig.setTcpNoDelay(true);
		socketConfig.setSoLinger(0);
		config.setSocketConfig(socketConfig);
		SocketIOServer socketIOServer = new SocketIOServer(config);
		this.addNamespace(socketIOServer, "/admin");
		return socketIOServer;
	}

	@Bean
	public SocketIoRun socketIoRun() {
		return new SocketIoRun();
	}

	/**
	 * 添加namespace至socketIo server
	 */
	public void addNamespace(SocketIOServer socketIOServer, String namespaceName) {
		SocketIONamespace socketIoNamespace = socketIOServer.addNamespace(namespaceName);
		socketIoNamespace.addConnectListener(client -> {
			String clientInfo = client.getRemoteAddress().toString();
			String clientIp = clientInfo.substring(1, clientInfo.indexOf(":"));
			log.info("主题:{},:建立客户端连接，ip:{},sessionId:{}", namespaceName, clientIp, client.getSessionId());
		});
		socketIoNamespace.addDisconnectListener(client -> {
			String clientInfo = client.getRemoteAddress().toString();
			String clientIp = clientInfo.substring(1, clientInfo.indexOf(":"));
			log.info("主题:{},客户端断开连接，ip:{},sessionId:{}", namespaceName, clientIp, client.getSessionId());
		});
		socketIoNamespace.addEventListener("message", String.class, (client, data, arg2) -> {
			String clientInfo = client.getRemoteAddress().toString();
			String clientIp = clientInfo.substring(1, clientInfo.indexOf(":"));
			log.info("主题:{},客户端收到消息，ip:{},sessionId:{},data:{}",
				namespaceName, clientIp, client.getSessionId(), data);
		});
	}

	/*
前端使用：
npm install socket.io-client
import {Manager} from 'socket.io-client';
  managerSocket = new Manager('http://192.168.1.125:9090'
    , {
      autoConnect: false
    });
  adminSocket = managerSocket.socket("/admin");
  //   adminSocket = managerSocket.socket("");  直接空的话也可以，连接默认的"" namespace
  adminSocket.on('connect', () => {
    console.log('admin namespace connected');
  });
  // 在某个命名空间下监听消息事件
  adminSocket.on('message', (data: any) => {
    console.log('Received message:', data);
    // 处理接收到的数据
  });
 */

}

