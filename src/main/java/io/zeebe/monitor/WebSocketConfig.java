package io.zeebe.monitor;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import java.util.ArrayList;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
  @Value("${spring.data.web.allowedOriginsUrls}")
  private String allowedOriginsUrls;

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/");
    config.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    String[] allowedOriginsUrlArr = this.allowedOriginsUrls.split(";");
    if(allowedOriginsUrlArr.length>0){
        registry.addEndpoint("/notifications").setAllowedOrigins(allowedOriginsUrlArr).withSockJS();
    }else{
        registry.addEndpoint("/notifications").withSockJS();
    }
  }
}
