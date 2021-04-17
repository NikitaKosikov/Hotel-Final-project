package by.epam_training.final_task.controller.listener;

import by.epam_training.final_task.controller.listener.exception.ListenerException;
import by.epam_training.final_task.dao.impl.connection_pool.ConnectionPool;
import by.epam_training.final_task.dao.impl.connection_pool.ConnectionPoolException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {
    private static final Logger logger= Logger.getLogger(ContextListener.class.getName());
    private static final String ERROR_TO_INIT_POOL="Error while initializing pool data";
    private static final String ERROR_ON_CLOSING_POOL="Error while closing connection pool";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            ConnectionPool.getInstance().init();
        } catch (ConnectionPoolException e) {
            logger.log(Level.FATAL,ERROR_TO_INIT_POOL);
            throw new ListenerException(ERROR_TO_INIT_POOL, e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            ConnectionPool.getInstance().destroy();
        } catch (ConnectionPoolException e) {
            logger.log(Level.FATAL,ERROR_ON_CLOSING_POOL);
            throw new ListenerException(ERROR_ON_CLOSING_POOL,e);
        }
    }
}