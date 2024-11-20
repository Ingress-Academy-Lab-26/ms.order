package az.ingress.ms_order.model.queue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {
    private String payload;
    private ChannelType channelType;
}
