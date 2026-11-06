package iteam.tn.order;
import iteam.tn.kafka.OrderConfirmation;
import iteam.tn.customer.CustomerClient;
import iteam.tn.customer.CustomerResponse;
import iteam.tn.exception.BusinessException;
import iteam.tn.kafka.OrderProducer;
import iteam.tn.orderline.OrderLineRequest;
import iteam.tn.orderline.OrderLineService;
import iteam.tn.order.PaymentMethod;
import iteam.tn.payment.PaymentClient;
import iteam.tn.payment.PaymentRequest;
import iteam.tn.product.ProductClient;
import iteam.tn.product.PurchaseRequest;
import iteam.tn.product.PurchaseResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final CustomerClient customerClient;
    private final PaymentClient paymentClient;
    private final ProductClient productClient;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    @Transactional
    public Integer createOrder(OrderRequest request) {
        //check the customer
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("cannot create order::No customer existe with the customer id"));
        //purchase the product
        var purchasedProducts = productClient.purchaseProducts(request.products());
         //persist order
        var order = this.repository.save(mapper.toOrder(request));
        //persist orderline
        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity( )
                    )
            );
        }
        //to do start payment process
        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );//send the order confirmation to the broker

        return order.getId();
    }

    public List<OrderResponse> findAllOrders() {
        return this.repository.findAll()
                .stream()
                .map(this.mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer id) {
        return this.repository.findById(id)
                .map(this.mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", id)));
    }
}

