package az.ingress.ms_order.queue;

public interface QueueProducer {
    void sendMessageToQ(String queueName,Object message);
}
