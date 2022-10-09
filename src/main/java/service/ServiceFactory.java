package service;

public class ServiceFactory {

    private static volatile ServiceFactory instance;

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
//        ServiceFactory localInstance = instance;
        if (instance == null) {
            synchronized (ServiceFactory.class) {
//                localInstance = instance;
                if (instance == null) {
//                    instance = localInstance = new ServiceFactory();
                    instance = new ServiceFactory();
                }
            }
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
}
