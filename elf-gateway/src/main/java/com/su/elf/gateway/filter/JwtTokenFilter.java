package com.su.elf.gateway.filter;

import com.alibaba.fastjson.JSON;

import com.su.elf.auth.client.jwt.JwtTokenUtil;
import com.su.elf.common.CodeEnum;
import com.su.elf.common.entity.ResponseMessage;
import com.su.elf.common.utils.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("鉴权过滤器");
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String requestUrl = request.getPath().toString();
        boolean status = CollectionUtil.contains(whiteUrlList, requestUrl);
        if(!status){
            String token = request.getHeaders().getFirst("token");
            if (StringUtils.isBlank(token)) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return getVoidMono(response, CodeEnum.UN_AUTH);
            }else{
                String userKey = jwtTokenUtil.verifyToken(token, requestUrl);
                ServerHttpRequest mutableReq = request.mutate().header("userKey", userKey).build();
                ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();
                return chain.filter(mutableExchange);
            }
        }

        return chain.filter(exchange);

    }

    @Override
    public int getOrder() {
        return 1;
    }

    private Mono<Void> getVoidMono(ServerHttpResponse response, CodeEnum codeEnum) {
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        ResponseMessage responseMessage = ResponseMessage.error(codeEnum);
        DataBuffer dataBuffer = response.bufferFactory().wrap(JSON.toJSONString(responseMessage).getBytes());
        return response.writeWith(Flux.just(dataBuffer));
    }

}
