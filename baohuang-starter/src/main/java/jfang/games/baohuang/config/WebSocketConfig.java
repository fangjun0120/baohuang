package jfang.games.baohuang.config;

import jfang.games.baohuang.intercepter.MessageInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author Jun
 * @date 2020/4/30
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // prefix pattern for client subscription
        config.enableSimpleBroker("/topic", "/user");
        // prefix pattern handled by controllers
        config.setApplicationDestinationPrefixes("/app");
        // prefix pattern for point 2 point subscription
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // endpoint url which client used to connect
        registry.addEndpoint("/endpoint/ws");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new MessageInterceptor());
    }

}
