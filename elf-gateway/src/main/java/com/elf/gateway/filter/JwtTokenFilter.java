package com.elf.gateway.filter;

import com.alibaba.fastjson.JSONObject;

import com.su.elf.common.utils.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Administrator
 * @Desc
 * @date 2020/6/20 8:36
 */
@Slf4j
@Component
public class JwtTokenFilter implements GlobalFilter, Ordered {

    /**
     * 不进行token校验的请求地址
     */
    @Value("#{'${jwt.whiteUrlList}'.split(',')}")
    private List<String> whiteUrlList;

//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestUrl = exchange.getRequest().getPath().toString();
        boolean status = CollectionUtil.contains(whiteUrlList, requestUrl);
        log.info("custom global filter");
        return chain.filter(exchange);

    }

    @Override
    public int getOrder() {
        return 1;
    }

}
