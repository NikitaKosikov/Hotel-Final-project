package by.epam_training.final_task.service;

import by.epam_training.final_task.service.impl.*;

public final class ServiceProvider {

    private static final ServiceProvider instance = new ServiceProvider();

    private ServiceProvider() {}

    private final UserService userService = new UserServiceImpl();
    private final OrderService orderService = new OrderServiceImpl();
    private final RoomService roomService = new RoomServiceImpl();
    private final AdditionalServiceService additionalService = new AdditionalServiceImpl();

    public static ServiceProvider getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public RoomService getRoomService() {
        return roomService;
    }

    public AdditionalServiceService getAdditionalService() {
        return additionalService;
    }

}
