package com.kolos.bookstore.controller.command;

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
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CommandFactory {

    private static final CommandFactory INSTANCE = new CommandFactory();
    private static final String DB_URL = "db.url";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_USER = "db.user";
    private static final String DB_DRIVER = "db.driver";
    private static final String DB_POOL_SIZE = "db.poolSize";
    private final List<Closeable> resources;

    private final Map<String, Command> commands;

    public static CommandFactory getInstance() {
        return INSTANCE;
    }

    private CommandFactory() {
        resources = new ArrayList<>();
        commands = new HashMap<>();

        //ConnectionManager
        ConfigurationManager configurationManager = new ConfigurationManagerImpl("/application.properties");
        String url = configurationManager.getProperty(DB_URL);
        String password = configurationManager.getProperty(DB_PASSWORD);
        String user = configurationManager.getProperty(DB_USER);
        String driver = configurationManager.getProperty(DB_DRIVER);
        ConnectionManager connectionManager = new ConnectionManagerImpl(url, password, user, driver);
        int poolSize = Integer.parseInt(configurationManager.getProperty(DB_POOL_SIZE));
        connectionManager.setPoolSize(poolSize);
        resources.add(connectionManager);

        //dao
        BookDao bookDao = new BookDaoImpl(connectionManager);
        UserDao userDao = new UserDaoImpl(connectionManager);
        OrderDao orderDao = new OrderDaoImpl(connectionManager);
        OrderItemDao orderItemDao = new OrderItemDaoImpl(connectionManager);

//        digestService
        DigestService digestService = new DigestServiceImpl();


//        mapper
        DataMapper dataMapper = new DataMapperImpl();
        ServiceMapper serviceMapper = new ServiceMapperImpl(digestService);




//        repository
        BookRepository bookRepository = new BookRepositoryImpl(bookDao, dataMapper);
        UserRepository userRepository = new UserRepositoryImpl(userDao, dataMapper);
        OrderRepository orderRepository = new OrderRepositoryImpl(orderDao, userDao, bookDao, orderItemDao, dataMapper);


        //service

        BookService bookService = new BookServiceImpl(bookRepository, serviceMapper);
        UserService userService = new UserServiceImpl(userRepository, digestService, serviceMapper);
        OrderService orderService = new OrderServiceImpl(orderRepository, serviceMapper);


        //book
        commands.put("book", new BookCommand(bookService));
        commands.put("books", new BooksCommand(bookService));
        commands.put("book_create_form", new BookCreateFormCommand());
        commands.put("book_create", new BookCreateCommand(bookService));
        commands.put("book_edit", new BookEditCommand(bookService));
        commands.put("book_edit_form", new BookEditFormCommand(bookService));
        commands.put("book_delete", new BookDeleteCommand(bookService));
        commands.put("books_search", new BooksSearchCommand(bookService));


        //user
        commands.put("user", new UserCommand(userService));
        commands.put("users", new UsersCommand(userService));
        commands.put("user_create", new UserCreateCommand(userService));
        commands.put("user_create_form", new UserCreateFormCommand());
        commands.put("user_edit", new UserEditCommand(userService));
        commands.put("user_edit_form", new UserEditFormCommand(userService));
        commands.put("user_registration_form", new UserRegistrationForm());
        commands.put("user_registration", new UserRegistrationCommand(userService));
        commands.put("user_login", new LoginCommand(userService));
        commands.put("user_login_form", new LoginFormCommand());
        commands.put("user_delete", new UserDeleteCommand(userService));
        commands.put("user_logOut", new LogOutCommand());
        commands.put("users_search_last_name", new UserSearchByLastName(userService));



//        order
        commands.put("order", new OrderCommand(orderService));
        commands.put("orders", new OrdersCommand(orderService));
        commands.put("orders_user", new OrdersUserCommand(orderService));
        commands.put("order_cancel", new OrderCancelCommand(orderService));
        commands.put("change_order_status", new OrderChangeStatus(orderService));




//        other
        commands.put("home", new HomeCommand());
        commands.put("error", new ErrorCommand());
        commands.put("addToCart", new AddToCartCommand(bookService));
        commands.put("cart", new CartCommand());
        commands.put("create_order", new OrderCreateCommand(orderService));
        commands.put("change_lang", new ChangeLanguageCommand());

    }

    public Command getCommand(String command) {
        Command commandInstatnce = commands.get(command);
        if (commandInstatnce == null) {
            commandInstatnce = commands.get("error");
        }
        return commandInstatnce;
    }

    public void shatdown() {
        resources.forEach(resource -> {
            try {
                resource.close();
            } catch (IOException e) {
                log.error("error");
            }
        });
    }

}
