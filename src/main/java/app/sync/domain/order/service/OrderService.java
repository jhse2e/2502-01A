package app.sync.domain.order.service;

import app.sync.domain.order.db.entity.Order;
import app.sync.domain.order.db.entity.OrderProduct;
import app.sync.domain.order.db.repository.OrderProductRepository;
import app.sync.domain.order.db.repository.OrderRepository;
import app.sync.domain.order.dto.request.OrderCreateDto;
import app.sync.domain.order.dto.response.OrderCreateResultDto;
import app.sync.domain.order.dto.response.OrderGetResultDto;
import app.sync.domain.order.dto.response.OrderListGetResultDto;
import app.sync.domain.product.db.entity.Product;
import app.sync.domain.product.db.repository.ProductRepository;
import app.sync.domain.user.db.entity.User;
import app.sync.domain.user.db.repository.UserRepository;
import app.sync.global.exception.ServerException;
import app.sync.global.exception.ServerExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    /**
     * 주문 조회
     */
    @Transactional(readOnly = true)
    public OrderGetResultDto getOrder(Long userId, String orderId) {
        Order order = orderRepository.findByUserIdAndId(userId, orderId).orElseThrow(() -> new ServerException(ServerExceptionType.ORDER_NOT_EXISTED));

        return new OrderGetResultDto(order.getId(), order.getStatusType().getValue(), order.getAmount());
    }

    /**
     * 주문 목록 조회
     */
    @Transactional(readOnly = true)
    public OrderListGetResultDto getOrders(Long userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);
        List<OrderListGetResultDto.OrderDto> orderDtos = new ArrayList<>();

        for (Order order : orders) {
            List<OrderListGetResultDto.OrderProductDto> orderProductDtos = new ArrayList<>();

            for (OrderProduct orderProduct : orderProductRepository.findAllByOrderId(order.getId())) {
                orderProductDtos.add(new OrderListGetResultDto.OrderProductDto(orderProduct.getProduct().getName(), orderProduct.getPrice(), orderProduct.getQuantity()));
            }

            orderDtos.add(new OrderListGetResultDto.OrderDto(order.getId(), order.getStatusType().getValue(), order.getAmount(), orderProductDtos));
        }

        return new OrderListGetResultDto(orderDtos);
    }

    /**
     * 주문 생성
     */
    @Transactional
    public OrderCreateResultDto createOrder(Long userId, OrderCreateDto orderDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ServerException(ServerExceptionType.USER_NOT_EXISTED));
        Order order = Order.create(user);

        Integer orderAmount = 0;
        List<OrderProduct> orderProducts = new ArrayList<>();

        for (OrderCreateDto.ProductDto productDto : orderDto.products()) {
            Product product = productRepository.findById(productDto.id()).orElseThrow(() -> new ServerException(ServerExceptionType.PRODUCT_NOT_EXISTED));
            OrderProduct orderProduct = OrderProduct.create(order, product, product.getPrice(), productDto.quantity());
            orderProducts.add(orderProduct);
            orderAmount += product.getPriceSum(productDto.quantity());
        }

        order.updateAmountByOrder(orderAmount);
        orderRepository.save(order);
        orderProductRepository.saveAll(orderProducts);

        return new OrderCreateResultDto(order.getId());
    }
}