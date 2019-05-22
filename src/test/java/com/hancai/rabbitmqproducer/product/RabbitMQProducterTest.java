package com.hancai.rabbitmqproducer.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hancai.rabbitmqproducer.RabbitmqProducerApplicationTests;
import com.hancai.rabbitmqproducer.model.Student;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.UUID;

/**
 * rabbitmq producter 测试
 *
 * @author diaohancai
 */
public class RabbitMQProducterTest extends RabbitmqProducerApplicationTests {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /*
     * CorrelationData.id是用来标志message的，每个message都有唯一一个CorrelationData.id
     */

    /**
     * /diao direct exchange -> queue1 product message test
     */
    @Test
    public void diaoDirectExchangeQueue1ProductTest() throws JsonProcessingException {
        String megID = UUID.randomUUID().toString();
        logger.info("correlation id : {}", megID);
        CorrelationData correlationData = new CorrelationData(megID);
        String msg = "queue1 一条热狗发个说说";
        logger.info("send message: {}", msg);

        rabbitTemplate.convertAndSend("amq.direct", "route1", msg, correlationData);
    }

    /**
     * /diao direct exchange -> queue2 product message test
     */
    @Test
    public void diaoDirectExchangeQueue2ProductTest() {
        String megID = UUID.randomUUID().toString();
        logger.info("correlation id : {}", megID);
        CorrelationData correlationData = new CorrelationData(megID);
        String msg = "this is queue2 啦啦啦";
        logger.info("send message: {}", msg);

        rabbitTemplate.convertAndSend("amq.direct", "route2", msg, correlationData);
    }

    /**
     * /diao direct exchange -> queue3 product message test
     */
    @Test
    public void diaoDirectExchangeQueue3ProductTest() {
        String megID = UUID.randomUUID().toString();
        logger.info("correlation id : {}", megID);
        CorrelationData correlationData = new CorrelationData(megID);
        String msg = "direct exchange queue3 啦啦啦";
        logger.info("send message: {}", msg);

        rabbitTemplate.convertAndSend("amq.direct", "route3", msg, correlationData);
    }

    /**
     * /diao direct exchange -> queue2 product json message test
     */
    @Test
    public void diaoDirectExchangeQueue2Product4EntityTest() throws JsonProcessingException {
        String megID = UUID.randomUUID().toString();
        logger.info("correlation id : {}", megID);
        CorrelationData correlationData = new CorrelationData(megID);

        Student student = new Student(1, "刁汉财", new Date());

        logger.info("send message: {}", student);

        rabbitTemplate.convertAndSend("amq.direct", "route2", student, correlationData);
    }

}
