package controller;

import controller.command.DefaultCommand;
import controller.command.HomeCommand;
import controller.command.ICommand;
import controller.command.admin.*;
import controller.command.user.GetUserFastBookCommand;
import controller.command.user.GetUserOrdersCommand;
import controller.command.user.GetUserSpecialistsCommand;
import controller.command.user.PostAddOrderCommand;
import controller.command.visitor.*;
import controller.util.constants.Views;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ControllerHelper {

    private final static String DELIMITER = ":";
    private static final ResourceBundle bundle = ResourceBundle.
            getBundle(Views.PAGES_BUNDLE);
    private final DefaultCommand DEFAULT_COMMAND = new DefaultCommand();
    private final Map<String, ICommand> commands = new HashMap<>();
    private static ControllerHelper instance;

    public static synchronized ControllerHelper getInstance() {
        if(instance == null) {
            instance = new ControllerHelper();
        }
        return instance;
    }

    private ControllerHelper() {
        init();
    }

    private void init() {
        commands.put(buildKey(bundle.getString("home.path"), null),
                new HomeCommand());
//        commands.put(buildKey(bundle.getString("home.path"), "home"),
//                new HomeCommand());
        commands.put(buildKey(bundle.getString("login.path"), null),
                new GetLoginCommand());
        commands.put(buildKey(bundle.getString("login.path"), "login"),
                new PostLoginCommand());
        commands.put(buildKey(bundle.getString("logout.path"), "logout"),
                new LogoutCommand());
        commands.put(buildKey(bundle.getString("signup.path"), null),
                new GetRegistrationCommand());
        commands.put(buildKey(bundle.getString("signup.path"), "registration"),
                new PostRegistrationCommand());
        commands.put(buildKey(bundle.getString("home.path"), "asc.title"),
                new PostAscTitleCommand());
        commands.put(buildKey(bundle.getString("home.path"), "desc.title"),
                new PostDescTitleCommand());
        commands.put(buildKey(bundle.getString("home.path"), "asc.price"),
                new PostAscPriceCommand());
        commands.put(buildKey(bundle.getString("home.path"), "desc.price"),
                new PostDescPriceCommand());
        commands.put(buildKey(bundle.getString("home.path"), "filter.service"),
                new PostFilterByServiceCommand());
        commands.put(buildKey(bundle.getString("fastBook"), null),
                new GetFastBookCommand());
        commands.put(buildKey(bundle.getString("specialists"), null),
                new GetSpecialistsCommand());
        commands.put(buildKey(bundle.getString("user.orders"), null),
                new GetUserOrdersCommand());
        commands.put(buildKey(bundle.getString("user.fastBook"), null),
                new GetUserFastBookCommand());
        commands.put(buildKey(bundle.getString("user.specialists"), null),
                new GetUserSpecialistsCommand());
        commands.put(buildKey(bundle.getString("user.orders"), "add.order"),
                new PostAddOrderCommand());
        commands.put(buildKey(bundle.getString("create"), null),
                new GetCreateCommand());
        commands.put(buildKey(bundle.getString("admin.orders"), null),
                new GetAllOrdersCommand());
        commands.put(buildKey(bundle.getString("admin.specialists"), null),
                new GetAllSpecialistsCommand());
        commands.put(buildKey(bundle.getString("admin.users"), null),
                new GetAllUsersCommand());
        commands.put(buildKey(bundle.getString("admin.orders"), "delete.order"),
                new PostDeleteOrderCommand());
        commands.put(buildKey(bundle.getString("admin.orders"), "change.status"),
                new PostChangeOrderStatusCommand());
        commands.put(buildKey(bundle.getString("admin.orders"), "change.paymentStatus"),
                new PostChangePaymentStatusCommand());
        commands.put(buildKey(bundle.getString("admin.orders"), "change.time"),
                new PostChangeOrderTimeCommand());
        commands.put(buildKey(bundle.getString("admin.specialists"), "delete.specialist"),
                new PostDeleteSpecialistCommand());
        commands.put(buildKey(bundle.getString("admin.users"), "delete.user"),
                new PostDeleteUserCommand());
        commands.put(buildKey(bundle.getString("home.path"), "delete.service"),
                new PostDeleteServiceCommand());

    }

    public ICommand getCommand(String path, String command) {
        return commands.getOrDefault(buildKey(path, command), DEFAULT_COMMAND);
    }

    private String buildKey(String path, String command) {
        return path + DELIMITER + command;
    }
}
