package com.ferraz.customer;

import com.ferraz.amqp.RabbitMQMessageProducer;
import com.ferraz.clients.fraud.FraudCheckResponse;
import com.ferraz.clients.fraud.FraudClient;
import com.ferraz.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse.isFraudster())
            throw new IllegalStateException("Fraudster");

        NotificationRequest notificationRequest = new NotificationRequest(
                String.format("Hi %s, welcome!", customer.getFirstName()),
                customer.getEmail(),
                customer.getId()
        );

        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "notification.key"
        );

    }
}
