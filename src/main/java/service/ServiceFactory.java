package service;

public class ServiceFactory {

    private static volatile ServiceFactory instance;

    private ServiceFactory() {
    }

    public static synchronized ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }

    public static OrderService getOrderService() {
        return OrderService.getInstance();
    }

    public static OrderToServiceService getOrderToServiceService() {
        return OrderToServiceService.getInstance();
    }

    public static RespondService getRespondService() {
        return RespondService.getInstance();
    }

    public static ServiceForService getServiceService() {
        return ServiceForService.getInstance();
    }

    public static UserService getUserService() {
        return UserService.getInstance();
    }

    public static UserToOrderService getUserToOrderService() {
        return UserToOrderService.getInstance();
    }

    public static UserToServiceService getUserToServiceService() {
        return UserToServiceService.getInstance();
    }

    public static UserToRespondService getUserToRespondService() { return UserToRespondService.getInstance(); }
}
