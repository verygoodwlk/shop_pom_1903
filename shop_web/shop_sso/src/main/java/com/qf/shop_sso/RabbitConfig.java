package com.qf.shop_sso;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/18 11:46
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue getQueue(){
        return new Queue("email_queue");
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("email_exchange");
    }

    @Bean
    public Binding getBinding(Queue getQueue, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(getQueue).to(fanoutExchange);
    }
}
