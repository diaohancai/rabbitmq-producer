package com.hancai.rabbitmqproducer.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitmq channel配置<br/>
 * <ul>
 * <li>exchange</li>
 * <li>queue</li>
 * <li>bind</li>
 * </ul>
 * 注意：exchange、queue、bind等@Bean注册，
 * 实际上是根据name在rabbitmq服务器中创建对应的exchange、queue和绑定路由关系，
 * 如果exchange、queue已经存在则不创建；不存在则新建。
 * 如果exchange、queue、bind已经在生产环境创建好了，客户端无需创建的话，该配置可以注销掉。
 *
 * @author diaohancai
 */
@Configuration
public class RabbitMQChannelConfig {

    //***************************** exchange start *****************************//

    /**
     * virtual host: /diao
     * exchange name: amq.direct
     *
     * @return
     */
    @Bean
    public DirectExchange diaoDirectExchange() {
        return new DirectExchange("amq.direct", true, false);
    }

    //***************************** exchange end *****************************//

    //***************************** queue start *****************************//

    /**
     * virtual host: /diao
     * queue name: queue1
     *
     * @return
     */
    @Bean
    public Queue diaoQueue1() {
        return QueueBuilder
                .durable("queue1")
                .build();
    }

    /**
     * virtual host: /diao
     * queue name: queue2
     *
     * @return
     */
    @Bean
    public Queue diaoQueue2() {
        return QueueBuilder
                .durable("queue2")
                .build();
    }

    /**
     * virtual host: /diao
     * queue name: queue3
     *
     * @return
     */
    @Bean
    public Queue diaoQueue3() {
        return QueueBuilder
                .durable("queue3")
                .build();
    }

    //***************************** queue end *****************************//

    //***************************** bind start *****************************//

    /**
     * virtual host: /diao
     * bind: amq.direct -> queue1
     * route key: route1
     * @param diaoDirectExchange
     * @param diaoQueue1
     * @return
     */
    @Bean
    public Binding diaoDirectExchangeQueue1(DirectExchange diaoDirectExchange,
                                            Queue diaoQueue1) {
        return BindingBuilder
                .bind(diaoQueue1)
                .to(diaoDirectExchange)
                .with("route1");
    }

    /**
     * virtual host: /diao
     * bind: amq.direct -> queue2
     * route key: route2
     * @param diaoDirectExchange
     * @param diaoQueue2
     * @return
     */
    @Bean
    public Binding diaoDirectExchangeQueue2(DirectExchange diaoDirectExchange,
                                            Queue diaoQueue2) {
        return BindingBuilder
                .bind(diaoQueue2)
                .to(diaoDirectExchange)
                .with("route2");
    }

    /**
     * virtual host: /diao
     * bind: amq.direct -> queue3
     * route key: route3
     * @param diaoDirectExchange
     * @param diaoQueue3
     * @return
     */
    @Bean
    public Binding diaoDirectExchangeQueue3(DirectExchange diaoDirectExchange,
                                            Queue diaoQueue3) {
        return BindingBuilder
                .bind(diaoQueue3)
                .to(diaoDirectExchange)
                .with("route3");
    }

    //***************************** bind end *****************************//

}
