package soda.npe.gateway;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import soda.npe.common.utils.JwtAuthUtil;

/**
 * 管理员登录拦截Token校验过滤器
 */
@Slf4j
@Component
public class AdminTokenFilter implements GlobalFilter, Ordered {

    @Resource
    private JwtAuthUtil jwtAuthUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //SpringCloud Gateway的GetPath()有对应微服务的名称作为前缀！
        //判断是否是管理员，即/admin/开头，不是管理员接口则跳过放行（其实先执行了admin的过滤器
        if (!exchange.getRequest().getPath().toString().matches("(.*)/admin/(.*)")) {
            return chain.filter(exchange);
        }
        //判断当前访问的端口是否需要鉴权，放行登录接口
        if (exchange.getRequest().getPath().toString().matches("(.*)/public/(.*)")) {
            //不需要鉴权
            return chain.filter(exchange);
        }
        //鉴权，读取请求中的token
        String jwt = exchange.getRequest().getHeaders().getFirst("AdminAuthorization");
        if (StrUtil.isBlank(jwt) || !jwtAuthUtil.validation(jwt)) {
            log.info("拦截了未登录请求 {}", exchange.getRequest().getURI());
            //没有token或者校验失败，设置498并且立刻返回
            // - 设置Response的状态码
            exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(498));
            // - 过滤器链不继续往下传，立刻返回
            return exchange.getResponse().setComplete();
        } else {
            //正常放行
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return 0; //0是admin，/admin优先级比/**要高
    }
}
