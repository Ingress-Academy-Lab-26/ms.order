package az.ingress.ms_order.util;

import az.ingress.ms_order.dao.entity.OrderEntity;
import az.ingress.ms_order.dao.entity.OrderItemEntity;

import java.math.BigDecimal;

public class OrderUtil {

    public static void calculateTotalAmount(OrderEntity order) {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItemEntity orderItem : order.getOrderItems()) {
            total = total.add(orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        }
        order.setAmount(total);
    }

    public static String generatePayload(OrderEntity order) {
        return String.format("🎉 Order Confirmed! 🎉\n" +
                "Thank you for shopping with us! Your order ID: %s\n" +
                "Order totaling $%.2f has been successfully placed.\n" +
                "Items: %s\n" +
                "We’ll let you know once your order is on its way! 📦", order.getAmount(), order.getOrderItems(), order.getId());
    }
}
