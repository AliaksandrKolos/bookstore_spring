package com.kolos.bookstore.service.impl;


import com.kolos.bookstore.data.entity.Order;
import com.kolos.bookstore.data.repository.OrderRepository;
import com.kolos.bookstore.service.OrderMapper;
import com.kolos.bookstore.service.OrderService;
import com.kolos.bookstore.service.dto.OrderDto;
import com.kolos.bookstore.service.dto.OrderStatusUpdateDto;
import com.kolos.bookstore.service.exception.InvalidOrderStatusTransitionException;
import com.kolos.bookstore.service.exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final MessageSource messageSource;
    private final OrderMapper orderMapper;
    @PersistenceContext
    private EntityManager manager;


    @Override
    public OrderDto get(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            throw new NotFoundException(messageSource.getMessage("order.not_found", new Object[0], LocaleContextHolder.getLocale()));
        }
        return orderMapper.toDto(order);
    }

    @Override
    public Page<OrderDto> getAll(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderMapper::toDto);

    }


    @Override
    @Transactional
    public OrderDto create(OrderDto dto) {
        Order order = orderMapper.toEntity(dto);
        order.setUser(manager.merge(order.getUser()));
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

    @Transactional
    @Override
    public Page<OrderDto> getOrdersByUserId(Long id, Pageable pageable) {
        return orderRepository.findByUserId(id, pageable).map(orderMapper::toDto);
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
            throw new InvalidOrderStatusTransitionException(messageSource.getMessage("order.cannot_change_cancelled", new Object[0], LocaleContextHolder.getLocale()));
        }
        order.setStatus(Order.Status.CANCELLED);
        orderRepository.save(order);
    }


    private void validateChangeStatus(Long orderId, OrderStatusUpdateDto.Status dtoStatus, Order order) {
        if (order == null) {
            throw new NotFoundException(messageSource.getMessage("order.not_found", new Object[0], LocaleContextHolder.getLocale()));
        }

        if (order.getStatus().equals(Order.Status.CANCELLED)) {
            throw new InvalidOrderStatusTransitionException(messageSource.getMessage("order.cannot_change_cancelled", new Object[0], LocaleContextHolder.getLocale()));
        }

        if (order.getStatus().equals(Order.Status.PENDING)) {
            if (!dtoStatus.equals(OrderStatusUpdateDto.Status.CANCELLED) && !dtoStatus.equals(OrderStatusUpdateDto.Status.PAID)) {
                throw new InvalidOrderStatusTransitionException(messageSource.getMessage("order.invalid_status_transition_pending", new Object[0], LocaleContextHolder.getLocale()));
            }
        }

        if (order.getStatus().equals(Order.Status.PAID)) {
            if (!dtoStatus.equals(OrderStatusUpdateDto.Status.DELIVERED) && !dtoStatus.equals(OrderStatusUpdateDto.Status.CANCELLED)) {
                throw new InvalidOrderStatusTransitionException(messageSource.getMessage("order.invalid_status_transition_paid", new Object[0], LocaleContextHolder.getLocale()));
            }
        }

        if (order.getStatus().equals(Order.Status.DELIVERED)) {
            if (!dtoStatus.equals(OrderStatusUpdateDto.Status.CANCELLED)) {
                throw new InvalidOrderStatusTransitionException(messageSource.getMessage("order.invalid_status_transition_delivered", new Object[0], LocaleContextHolder.getLocale()));
            }
        }
    }
}
