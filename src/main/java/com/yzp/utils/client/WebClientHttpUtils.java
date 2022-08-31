package com.yzp.utils.client;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.NonNull;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.function.Consumer;

/**
 * WebClient 请求工具类
 *
 * @author YangZhiPeng
 */
public final class WebClientHttpUtils {

    private static final Logger log = LoggerFactory.getLogger(WebClientHttpUtils.class);


    /**
     * 禁止实例化
     */
    private WebClientHttpUtils() {
        throw new AssertionError(">>>>>>>>类[WebClientHttpUtils]禁止实例化<<<<<<<<");
    }

    /**
     * GET  retrieve 方式 请求 返回单条信息
     *
     * @param uri String 请求地址
     * @return String
     */
    public static String doGetRetrieve(@NonNull final String uri) {
        Mono<String> mono = WebClientHttpUtils.getWebClient().get().uri(uri).retrieve().bodyToMono(String.class);
        return mono.block();
    }

    /**
     * GET retrieve 方式 请求 返回单条信息  转为 T
     *
     * @param uri   String 请求地址
     * @param clazz Class T
     * @return T
     */
    public static <T> T doGetRetrieve2T(@NonNull final String uri, @NonNull final Class<T> clazz) {
        Mono<T> mono = WebClientHttpUtils.getWebClient().get().uri(uri).retrieve().bodyToMono(clazz);
        return mono.block();
    }

    /**
     * GET  retrieve 方式 请求 返回单条信息
     *
     * @param uri String 请求地址
     * @return String
     */
    public static String doGetRetrieveParams(@NonNull final String uri, @NonNull final String params) {
        Mono<String> mono = WebClientHttpUtils.getWebClient().get().uri(uri, params).retrieve().bodyToMono(String.class);
        return mono.block();
    }

    /**
     * GET exchange 方式 请求 返回单条信息
     *
     * @param uri String 请求地址
     * @return PairUtils
     */
    public static PairUtils<HttpStatus, String> doGetExchange(@NonNull final String uri) {
        Mono<ClientResponse> mono =
                WebClientHttpUtils.getWebClient().get().uri(uri)
                        .acceptCharset(StandardCharsets.UTF_8).exchangeToMono(response -> response.bodyToMono(ClientResponse.class));
        return WebClientHttpUtils.getExchangeResponse2GeT(mono, uri);
    }

    /**
     * POST exchange 请求 返回单条信息 json 格式请求  MediaType 默认 application/json
     *
     * @param uri        String 请求地址
     * @param jsonString String json 字符串
     * @param consumer   Consumer 请求头参数
     * @return PairUtils left->网络状态,right->响应参数
     */
    public static PairUtils<HttpStatus, String> doPostExchangeJson(@NonNull final String uri,
                                                                   @NonNull final String jsonString,
                                                                   @NonNull final Consumer<HttpHeaders> consumer) {
        Mono<ClientResponse> mono =
                WebClientHttpUtils.getWebClient(60, 60, 60).post().uri(uri)
                        .headers(consumer)
                        .acceptCharset(StandardCharsets.UTF_8).contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(jsonString).exchangeToMono(response -> response.bodyToMono(ClientResponse.class)).timeout(Duration.ofSeconds(60));
        return WebClientHttpUtils.getExchangeResponse2GeT(mono, uri);
    }

    /**
     * POST exchange 请求 返回单条信息 json 格式请求  MediaType 默认 application/json
     *
     * @param uri        String 请求地址
     * @param jsonString String json 字符串
     * @return PairUtils left->网络状态,right->响应参数
     */
    public static PairUtils<HttpStatus, String> doPostExchangeJson(@NonNull final String uri,
                                                                   @NonNull final String jsonString) {
        Mono<ClientResponse> mono =
                WebClientHttpUtils.getWebClient(60, 60, 60).post().uri(uri)
                        .acceptCharset(StandardCharsets.UTF_8).contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(jsonString).exchangeToMono(response -> response.bodyToMono(ClientResponse.class)).timeout(Duration.ofSeconds(60));
        return WebClientHttpUtils.getExchangeResponse2GeT(mono, uri);
    }

    /**
     * 获取 WebClient
     *
     * @return WebClient
     */
    private static WebClient getWebClient() {
        // HttpClient.wiretap("logger", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
        // 超时设置 连接2s 读取 10s 写 10s
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(
                        WebClientHttpUtils.getTcpClient(2, 10, 10))))
                .build();
    }

    /**
     * 获取 WebClient
     *
     * @return WebClient
     */
    private static WebClient getWebClient(int connectTimeout, int readTimeout, int writeTimeout) {

        // 超时设置
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(WebClientHttpUtils.getTcpClient(connectTimeout, readTimeout
                        , writeTimeout))))
                .build();
    }

    /**
     * WebClient 超时设置
     *
     * @param connectTimeout int
     * @param readTimeout    int
     * @param writeTimeout   int
     * @return WebClient
     */
    private static TcpClient getTcpClient(int connectTimeout, int readTimeout, int writeTimeout) {
        return TcpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout * 1000)
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(readTimeout))
                                .addHandlerLast(new WriteTimeoutHandler(writeTimeout)));
    }

    /**
     * 获取接口响应  exchange 方式
     *
     * @param mono Mono<ClientResponse>
     * @param uri  String
     * @return PairUtils
     */
    private static PairUtils<HttpStatus, String> getExchangeResponse2GeT(@NonNull final Mono<ClientResponse> mono,
                                                                         @NonNull final String uri) {
        ClientResponse response = mono.block();
        if (ObjectUtils.isEmpty(response)) {
            log.error(">>>>>>>>请求[{}]异常<<<<<<<<", uri);
            return PairUtils.of(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }
        if (response.statusCode().is2xxSuccessful()) {
            String result = response.bodyToMono(String.class).block();
            if (StringUtils.isNotBlank(result)) {
                return PairUtils.of(response.statusCode(), result);
            }
            return PairUtils.of(response.statusCode(), StringUtils.EMPTY);
        } else if (response.statusCode().is4xxClientError()) {
            log.error(">>>>>>>>请求[{}]异常,返回Code[{},{}]<<<<<<<<", uri, response.statusCode(), response.statusCode().getReasonPhrase());
            String message = response.bodyToMono(String.class).block();
            if (StringUtils.isNotBlank(message)) {
                return PairUtils.of(response.statusCode(), message);
            }
            return PairUtils.of(response.statusCode(), StringUtils.EMPTY);
        } else if (response.statusCode().is5xxServerError()) {
            log.error(">>>>>>>>请求[{}]异常]<<<<<<<<", uri);
            return PairUtils.of(response.statusCode(), response.statusCode().getReasonPhrase());
        }
        log.error(">>>>>>>>请求[{}]异常]<<<<<<<<", uri);
        return PairUtils.of(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

    }

}
