package com.demo.ai.conusmer;

import com.demo.ai.entity.JdFruit;
import com.demo.ai.entity.JdHelp;
import com.demo.ai.entity.Order;
import com.demo.ai.service.JdFruitService;
import com.demo.ai.service.JdHelpService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class FruitRabbitReceiver {
    @Autowired
    private JdFruitService jdFruitService;
    @Autowired
    private JdHelpService jdHelp;
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "fruit",
                    durable = "true"),
            exchange = @Exchange(value = "fruit",
                    durable = "true",
                    type = "topic",
                    ignoreDeclarationExceptions = "true"),
            key = "jdhelper.*"
    )
    )
    @RabbitHandler
    public void onMessage(Message message, Channel channel) throws Exception {
        System.err.println("--------------------------------------");
        System.err.println("消费端fruit: " + message.getPayload());
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        String md5 = (String) message.getHeaders().get("md5");
        JdFruit jdFruit = new JdFruit();
        jdFruit.setUserMd5(md5);
        jdFruit.setUserStatus("1");
        jdFruit.setUniqueId((String)message.getHeaders().get("spring_returned_message_correlation"));
        jdFruit.setUserTodaystatus("1");
        jdFruit.setTodaycount(1);
        jdFruit.setUpdateTime(new Date());
        jdFruit.setCreateTime(new Date());

        jdFruitService.insert(jdFruit);
        //存数据到数据库
        JdHelp jdFruit1 = new JdHelp();
        jdFruit1.setUserMd5(md5);
        jdFruit1.setIp((String) message.getHeaders().get("ip"));
        jdFruit1.setTaskType("fruit");
        jdFruit1.setUserStatus("1");
        jdFruit1.setUniqueId((String)message.getHeaders().get("spring_returned_message_correlation"));
        jdFruit1.setUserTodaystatus("1");
        jdFruit1.setTodaycount(1);
        jdFruit1.setUpdateTime(new Date());
        jdFruit1.setCreateTime(new Date());
        jdHelp.insert(jdFruit1);
        //手工ACK
        channel.basicAck(deliveryTag, false);
    }


    /**
     * spring.rabbitmq.listener.order.queue.name=queue-2
     * spring.rabbitmq.listener.order.queue.durable=true
     * spring.rabbitmq.listener.order.exchange.name=exchange-1
     * spring.rabbitmq.listener.order.exchange.durable=true
     * spring.rabbitmq.listener.order.exchange.type=topic
     * spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions=true
     * spring.rabbitmq.listener.order.key=springboot.*
     *
     * @param order
     * @param channel
     * @param headers
     * @throws Exception
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${spring.rabbitmq.listener.order.queue.name}",
                    durable = "${spring.rabbitmq.listener.order.queue.durable}"),
            exchange = @Exchange(value = "${spring.rabbitmq.listener.order.exchange.name}",
                    durable = "${spring.rabbitmq.listener.order.exchange.durable}",
                    type = "${spring.rabbitmq.listener.order.exchange.type}",
                    ignoreDeclarationExceptions = "${spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions}"),
            key = "${spring.rabbitmq.listener.order.key}"
    )
    )
    @RabbitHandler
    public void onOrderMessage(@Payload Order order,
                               Channel channel,
                               @Headers Map<String, Object> headers) throws Exception {
        System.err.println("--------------------------------------");
        System.err.println("消费端order: " + order.getId());
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        //手工ACK
        channel.basicAck(deliveryTag, false);
    }


}
