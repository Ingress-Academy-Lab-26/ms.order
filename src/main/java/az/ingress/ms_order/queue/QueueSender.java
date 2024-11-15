package az.ingress.ms_order.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueueSender {
    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public <T> void sendMessageToQ(String queueName, T dto) {
        amqpTemplate.convertAndSend(queueName, objectMapper.writeValueAsString(dto));
    }
}
