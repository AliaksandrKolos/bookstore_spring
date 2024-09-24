package com.kolos.bookstore.service.impl;


import com.kolos.bookstore.data.entity.Order;
import com.kolos.bookstore.data.repository.OrderRepository;
import com.kolos.bookstore.service.OrderService;
import com.kolos.bookstore.service.ServiceMapper;
import com.kolos.bookstore.service.dto.OrderDto;
import com.kolos.bookstore.service.dto.OrderStatusUpdateDto;
import com.kolos.bookstore.service.exception.InvalidOrderStatusTransitionException;
import com.kolos.bookstore.service.exception.NotFoundException;
import com.kolos.bookstore.service.util.MessageManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ServiceMapper serviceMapper;
    private final MessageManager messageManager;
    @PersistenceContext
    private EntityManager manager;


    @Override
    public OrderDto get(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            throw new NotFoundException(messageManager.getMessage("order.not_found") + id);
        }
        return serviceMapper.toDto(order);
    }

    @Override
    public Page<OrderDto> getAll(Pageable pageable) {
        return orderRepository.findAll(pageable).map(serviceMapper::toDto);

    }


    @Override
    @Transactional
    public OrderDto create(OrderDto dto) {
        Order order = serviceMapper.toEntity(dto);
        order.setUser(manager.merge(order.getUser()));
        Order savedOrder = orderRepository.save(order);
        return serviceMapper.toDto(savedOrder);
    }

    @Transactional
    @Override
    public Page<OrderDto> getOrdersByUserId(Long id, Pageable pageable) {
        return orderRepository.findByUserId(id, pageable).map(serviceMapper::toDto);
    }

    @Transactional
    @Override
    public void changeStatus(Long orderId, OrderStatusUpdateDto.Status dtoStatus) {
        Order order = orderRepository.findById(orderId).orElse(null);
        validateChangeStatus(orderId, dtoStatus, order);
        order.setStatus(Order.Status.valueOf(dtoStatus.name()));
        orderRepository.save(order);
    }


    @Override
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order.getStatus().equals(Order.Status.CANCELLED)) {
            throw new InvalidOrderStatusTransitionException(messageManager.getMessage("order.cannot_change_cancelled"));
        }
        order.setStatus(Order.Status.CANCELLED);
        orderRepository.save(order);
    }


    private void validateChangeStatus(Long orderId, OrderStatusUpdateDto.Status dtoStatus, Order order) {
        if (order == null) {
            throw new NotFoundException(messageManager.getMessage("order.not_found") + orderId);
        }

        if (order.getStatus().equals(Order.Status.CANCELLED)) {
            throw new InvalidOrderStatusTransitionException(messageManager.getMessage("order.cannot_change_cancelled"));
        }

        if (order.getStatus().equals(Order.Status.PENDING)) {
            if (!dtoStatus.equals(OrderStatusUpdateDto.Status.CANCELLED) && !dtoStatus.equals(OrderStatusUpdateDto.Status.PAID)) {
                throw new InvalidOrderStatusTransitionException(messageManager.getMessage("order.invalid_status_transition_pending"));
            }
        }

        if (order.getStatus().equals(Order.Status.PAID)) {
            if (!dtoStatus.equals(OrderStatusUpdateDto.Status.DELIVERED) && !dtoStatus.equals(OrderStatusUpdateDto.Status.CANCELLED)) {
                throw new InvalidOrderStatusTransitionException(messageManager.getMessage("order.invalid_status_transition_paid"));
            }
        }

        if (order.getStatus().equals(Order.Status.DELIVERED)) {
            if (!dtoStatus.equals(OrderStatusUpdateDto.Status.CANCELLED)) {
                throw new InvalidOrderStatusTransitionException(messageManager.getMessage("order.invalid_status_transition_delivered"));
            }
        }
    }
}
