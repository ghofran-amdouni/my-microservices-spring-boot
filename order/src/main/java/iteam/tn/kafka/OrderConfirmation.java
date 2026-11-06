package iteam.tn.kafka;
import iteam.tn.customer.CustomerResponse;
import iteam.tn.order.PaymentMethod;
import iteam.tn.product.PurchaseResponse;
import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
