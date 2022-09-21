package gateway;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * gateway Application
 *
 * @author YangZhiPeng
 * @date 2022/9/20 17:13
 */
@EnableDiscoveryClient
@SpringBootApplication
@Slf4j
public class GatewayApplication {

    public static void main(String[] args) {
        TimeInterval timeInterval = DateUtil.timer();
        SpringApplication.run(GatewayApplication.class, args);
        if (log.isInfoEnabled()) {
            log.info(">>>>>>>>GatewayApplication本次启动耗时[{}]毫秒<<<<<<<<", timeInterval.interval());
        }
    }

}
