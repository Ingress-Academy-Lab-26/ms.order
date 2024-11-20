package az.ingress.ms_order.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueueSender implements QueueProducer{
    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void sendMessageToQ(String queueName,Object dto) {
        amqpTemplate.convertAndSend(queueName, objectMapper.writeValueAsString(dto));
    }
}
