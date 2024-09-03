package com.yzp.socket;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

/**
 * desc
 *
 * @author:YangZhiPeng
 * @date: 2024/2/2 18:07
 */
@Slf4j
public class SocketIoRun implements CommandLineRunner {
    @Autowired
    private SocketIOServer socketIOServer;

    @Override
    public void run(String... args) {
      //  log.info("启动成功：{}", args);
        socketIOServer.start();
    }
}
