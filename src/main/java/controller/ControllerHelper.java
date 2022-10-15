package controller;

import controller.command.DefaultCommand;
import controller.command.HomeCommand;
import controller.command.ICommand;
import controller.command.authorization.*;
import controller.command.user.GetOrdersCommand;
import controller.command.user.GetRespondCommand;
import controller.command.user.GetServiceCommand;
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
        commands.put(buildKey(bundle.getString("home.path"), "home"),
                new HomeCommand());
        commands.put(buildKey(bundle.getString("login.path"), null),
                new GetLoginCommand());
        commands.put(buildKey(bundle.getString("login.path"), "login.post"),
                new PostLoginCommand());
        commands.put(buildKey(bundle.getString("logout.path"), "logout"),
                new LogoutCommand());
//        commands.put(buildKey(bundle.getString("service.path"), null),
//                new GetServiceCommand());
//        commands.put(buildKey(bundle.getString("orders.path"), "user.orders"),
//                new GetOrdersCommand());
        commands.put(buildKey(bundle.getString("signup.path"), null),
                new GetRegistrationCommand());
        commands.put(buildKey(bundle.getString("signup.path"), "register.post"),
                new PostRegistrationCommand());
    }

    public ICommand getCommand(String path, String command) {
        return commands.getOrDefault(buildKey(path, command), DEFAULT_COMMAND);
    }

    private String buildKey(String path, String command) {
        return path + DELIMITER + command;
    }
}
