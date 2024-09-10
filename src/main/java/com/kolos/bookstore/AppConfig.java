package com.kolos.bookstore;

import com.kolos.bookstore.controller.command.impl.*;
import com.kolos.bookstore.controller.command.impl.book.*;
import com.kolos.bookstore.controller.command.impl.order.*;
import com.kolos.bookstore.controller.command.impl.user.*;
import com.kolos.bookstore.data.connection.ConnectionManager;
import com.kolos.bookstore.data.connection.impl.ConnectionManagerImpl;
import com.kolos.bookstore.data.dao.BookDao;
import com.kolos.bookstore.data.dao.OrderDao;
import com.kolos.bookstore.data.dao.OrderItemDao;
import com.kolos.bookstore.data.dao.UserDao;
import com.kolos.bookstore.data.dao.impl.BookDaoImpl;
import com.kolos.bookstore.data.dao.impl.OrderDaoImpl;
import com.kolos.bookstore.data.dao.impl.OrderItemDaoImpl;
import com.kolos.bookstore.data.dao.impl.UserDaoImpl;
import com.kolos.bookstore.data.mapper.DataMapper;
import com.kolos.bookstore.data.mapper.DataMapperImpl;
import com.kolos.bookstore.data.repository.BookRepository;
import com.kolos.bookstore.data.repository.OrderRepository;
import com.kolos.bookstore.data.repository.UserRepository;
import com.kolos.bookstore.data.repository.impl.BookRepositoryImpl;
import com.kolos.bookstore.data.repository.impl.OrderRepositoryImpl;
import com.kolos.bookstore.data.repository.impl.UserRepositoryImpl;
import com.kolos.bookstore.platform.ConfigurationManager;
import com.kolos.bookstore.platform.impl.ConfigurationManagerImpl;
import com.kolos.bookstore.service.*;
import com.kolos.bookstore.service.impl.BookServiceImpl;
import com.kolos.bookstore.service.impl.DigestServiceImpl;
import com.kolos.bookstore.service.impl.OrderServiceImpl;
import com.kolos.bookstore.service.impl.UserServiceImpl;
import com.kolos.bookstore.service.mapper.ServiceMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:/application.properties")
@Configuration
public class AppConfig {


//    dataBase

    @Bean
    public ConfigurationManager configurationManager() {
        return new ConfigurationManagerImpl("/application.properties");
    }

    @Bean
    public ConnectionManager connectionManager(ConfigurationManager configurationManager) {
        return new ConnectionManagerImpl(configurationManager);
    }

//    dao

    @Bean
    public BookDao bookDao(ConnectionManager connectionManager) {
        return new BookDaoImpl(connectionManager);
    }

    @Bean
    public UserDao userDao(ConnectionManager connectionManager) {
        return new UserDaoImpl(connectionManager);
    }

    @Bean
    public OrderDao orderDao(ConnectionManager connectionManager) {
        return new OrderDaoImpl(connectionManager);
    }

    @Bean
    public OrderItemDao orderItemDao(ConnectionManager connectionManager) {
        return new OrderItemDaoImpl(connectionManager);
    }


//    mapper

    @Bean
    public DataMapper dataMapper() {
        return new DataMapperImpl();
    }

    @Bean
    public ServiceMapper serviceMapper(DigestService digestService) {
        return new ServiceMapperImpl(digestService);
    }


//    repository

    @Bean
    public BookRepository bookRepository(BookDao bookDao, DataMapper dataMapper) {
        return new BookRepositoryImpl(bookDao, dataMapper);
    }

    @Bean
    public UserRepository userRepository(UserDao userDao, DataMapper dataMapper) {
        return new UserRepositoryImpl(userDao, dataMapper);
    }

    @Bean
    public OrderRepository orderRepository(OrderDao orderDao, UserDao userDao, BookDao bookDao, OrderItemDao orderItemDao, DataMapper dataMapper) {
        return new OrderRepositoryImpl(orderDao, userDao, bookDao, orderItemDao, dataMapper);
    }


//    service

    @Bean
    public DigestService digestService() {
        return new DigestServiceImpl();
    }

    @Bean
    public BookService bookService(BookRepository bookRepository, ServiceMapper serviceMapper) {
        return new BookServiceImpl(bookRepository, serviceMapper);
    }

    @Bean
    public UserService userService(UserRepository userRepository, DigestService digestService, ServiceMapper serviceMapper) {
        return new UserServiceImpl(userRepository, digestService, serviceMapper);
    }

    @Bean
    public OrderService orderService(OrderRepository orderRepository, ServiceMapper serviceMapper) {
        return new OrderServiceImpl(orderRepository, serviceMapper);
    }


//    bookControllers

    @Bean
    public BookCommand bookCommand(BookService bookService) {
        return new BookCommand(bookService);
    }

    @Bean
    public BooksCommand booksCommand(BookService bookService) {
        return new BooksCommand(bookService);
    }

    @Bean
    public BookCreateFormCommand bookCreateFormCommand() {
        return new BookCreateFormCommand();
    }

    @Bean
    public BookCreateCommand bookCreateCommand(BookService bookService) {
        return new BookCreateCommand(bookService);
    }

    @Bean
    public BookEditCommand bookEditCommand(BookService bookService) {
        return new BookEditCommand(bookService);
    }

    @Bean
    public BookEditFormCommand bookEditFormCommand(BookService bookService) {
        return new BookEditFormCommand(bookService);
    }

    @Bean
    public BookDeleteCommand bookDeleteCommand(BookService bookService) {
        return new BookDeleteCommand(bookService);
    }

    @Bean
    public BooksSearchCommand booksSearchCommand(BookService bookService) {
        return new BooksSearchCommand(bookService);
    }


    //    userControllers

    @Bean
    public UserCommand userCommand(UserService userService) {
        return new UserCommand(userService);
    }

    @Bean
    public UsersCommand usersCommand(UserService userService) {
        return new UsersCommand(userService);
    }

    @Bean
    public UserCreateCommand userCreateCommand(UserService userService) {
        return new UserCreateCommand(userService);
    }

    @Bean
    public UserCreateFormCommand userCreateFormCommand() {
        return new UserCreateFormCommand();
    }

    @Bean
    public UserEditCommand userEditCommand(UserService userService) {
        return new UserEditCommand(userService);
    }

    @Bean
    public UserEditFormCommand userEditFormCommand(UserService userService) {
        return new UserEditFormCommand(userService);
    }

    @Bean
    public UserRegistrationForm UserRegistrationForm() {
        return new UserRegistrationForm();
    }

    @Bean
    public UserRegistrationCommand userRegistrationCommand(UserService userService) {
        return new UserRegistrationCommand(userService);
    }

    @Bean
    public LoginCommand loginCommand(UserService userService) {
        return new LoginCommand(userService);
    }

    @Bean
    public LoginFormCommand loginFormCommand() {
        return new LoginFormCommand();
    }

    @Bean
    public UserDeleteCommand userDeleteCommand(UserService userService) {
        return new UserDeleteCommand(userService);
    }

    @Bean
    public LogOutCommand logOutCommand() {
        return new LogOutCommand();
    }

    @Bean
    public UserSearchByLastName userSearchByLastName(UserService userService) {
        return new UserSearchByLastName(userService);
    }


//    orderControllers

    @Bean
    public OrderCommand orderCommand(OrderService orderService) {
        return new OrderCommand(orderService);
    }

    @Bean
    public OrdersCommand ordersCommand(OrderService orderService) {
        return new OrdersCommand(orderService);
    }

    @Bean
    public OrdersUserCommand ordersUserCommand(OrderService orderService) {
        return new OrdersUserCommand(orderService);
    }

    @Bean
    public OrderCancelCommand orderCancelCommand(OrderService orderService) {
        return new OrderCancelCommand(orderService);
    }

    @Bean
    public OrderChangeStatus orderChangeStatus(OrderService orderService) {
        return new OrderChangeStatus(orderService);
    }

    @Bean
    public OrderCreateCommand orderCreateCommand(OrderService orderService) {
        return new OrderCreateCommand(orderService);
    }


//    otherControllers

    @Bean
    public HomeCommand homeCommand() {
        return new HomeCommand();
    }

    @Bean
    public ErrorCommand errorCommand() {
        return new ErrorCommand();
    }

    @Bean
    public AddToCartCommand addToCartCommand(BookService bookService) {
        return new AddToCartCommand(bookService);
    }

    @Bean
    public CartCommand cartCommand() {
        return new CartCommand();
    }

    @Bean
    public ChangeLanguageCommand changeLanguageCommand() {
        return new ChangeLanguageCommand();
    }
}
