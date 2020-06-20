package com.elf.gateway.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.su.elf.auth.client.jwt.JwtTokenProvider;
import com.su.elf.common.CodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
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
@Component
public class JwtTokenFilter implements GatewayFilter, Ordered {

    /**
     * 不进行token校验的请求地址
     */
    @Value("#{'${jwt.}'.split(',')}")
    private List<String> whiteUrlList;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestUrl = exchange.getRequest().getPath().toString();
        boolean status = CollectionUtil.contains(whiteUrlList, requestUrl);
        if (!status){
            String token = exchange.getRequest().getHeaders().getFirst("token");
            //type用于区分不同的端，在做校验token时需要
//            String type= exchange.getRequest().getHeaders().getFirst("type");
            ServerHttpResponse response = exchange.getResponse();
            //没有数据
            if (StrUtil.isBlank(token)) {
                JSONObject message = new JSONObject();
                message.put("code", CodeEnum.UN_AUTH);
                message.put("message", "鉴权失败，无token");
                byte[] bits = message.toString().getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = response.bufferFactory().wrap(bits);
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().add("Content-Type", "text/json;charset=UTF-8");
                return response.writeWith(Mono.just(buffer));
            }else {
                //校验token
                String userKey = jwtTokenProvider.verufyToken(token);
                if (StrUtil.isEmpty(userKey)){
                    JSONObject message = new JSONObject();
                    message.put("message", "token错误");
                    message.put("code", CodeEnum.ILLEGAL_PARAM);
                    byte[] bits = message.toString().getBytes(StandardCharsets.UTF_8);
                    DataBuffer buffer = response.bufferFactory().wrap(bits);
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    response.getHeaders().add("Content-Type", "text/json;charset=UTF-8");
                    return response.writeWith(Mono.just(buffer));
                }
                // todo 校验权限

                //将现在的request，添加当前身份
                ServerHttpRequest mutableReq = exchange.getRequest().mutate().header("Authorization-UserKey", userKey).build();
                ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();
                return chain.filter(mutableExchange);
            }
        }
        return chain.filter(exchange);

    }

    @Override
    public int getOrder() {
        return 0;
    }

}
