package com.hancai.rabbitmqproducer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitmq 配置类
 *
 * @author diaohancai
 */
@Configuration
public class RabbitMQConfig {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * rabbitmq 工厂连接
     *
     * @return
     */
    /*@Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setAddresses("192.168.7.3");
        cachingConnectionFactory.setPort(5672);
        cachingConnectionFactory.setUsername("guest");
        cachingConnectionFactory.setPassword("guest");
        cachingConnectionFactory.setVirtualHost("/diao");
        cachingConnectionFactory.setPublisherConfirms(true);
        cachingConnectionFactory.setPublisherReturns(true);
        return cachingConnectionFactory;
    }*/

    /**
     * rabbitmq message json converter
     *
     * @return
     */
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * rabbitmq 客户端模板
     *
     * @param connectionFactory
     * @param messageConverter
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        /*
         * 如果消息没有投递到任何一个queue中
         * true：启用return机制
         * false: 直接丢弃消息
         */
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setMessageConverter(messageConverter); // 消息以json格式发送

        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if(ack) {
                    logger.info("ack");
                } else {
                    logger.error("no ack: {}", cause);
                }
            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                logger.info("message: {}", message);
                logger.info("replyCode: {}", replyCode);
                logger.info("replyText: {}", replyText);
                logger.info("exchange: {}", exchange);
                logger.info("routingKey: {}", routingKey);
            }
        });
        return rabbitTemplate;
    }

}
