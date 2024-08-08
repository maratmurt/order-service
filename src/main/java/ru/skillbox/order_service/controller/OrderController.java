package ru.skillbox.order_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.order_service.event.OrderEvent;
import ru.skillbox.order_service.model.Order;

import java.util.UUID;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Value("${app.kafka.producer.topic}")
    private String producerTopic;

    @PostMapping
    public ResponseEntity<String> sendOrder(@RequestBody Order order) {
        OrderEvent event = new OrderEvent(order.getProduct(), order.getQuantity());
        kafkaTemplate.send(producerTopic, UUID.randomUUID().toString(), event);

        return ResponseEntity.ok("The order is sent");
    }

}
