package by.epam_training.final_task.dao.impl;

import by.epam_training.final_task.dao.AdditionalServiceDAO;
import by.epam_training.final_task.dao.DAOException;
import by.epam_training.final_task.dao.impl.connection_pool.ConnectionPool;
import by.epam_training.final_task.dao.impl.connection_pool.ConnectionPoolException;
import by.epam_training.final_task.entity.AdditionalService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SQLAdditionalServiceDAO implements AdditionalServiceDAO {
    private static final Logger logger = Logger.getLogger(SQLAdditionalServiceDAO.class.getName());

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String FIND_ALL_ADDITIONAL_SERVICES = "SELECT * FROM services WHERE status=?";

    private static final String FIND_ADDITIONAL_SERVICES_BY_ORDER_ID = "SELECT * FROM m2m_order_used_services " +
            "JOIN services s on s.service_id = m2m_order_used_services.service_id WHERE order_id=?";

    private static final String RESERVATION_SERVICE = "INSERT INTO m2m_order_used_services " +
            "(order_id, service_id, date_of_service) values (?, ?, ?)";

    private static final String FIND_COST_OF_SERVICE_BY_ID = "SELECT service_cost FROM services where service_id=?";

    private static final String FIND_COST_OF_ORDER_BY_ID = "SELECT cost FROM orders where order_id=?";

    private static final String UPDATE_COST_ORDER = "UPDATE orders set cost=? where order_id=?";

    private static final String INSERT_SERVICE = "INSERT services (name, service_cost) " +
            "VALUES (?,?)";

    private static final String UPDATE_SERVICE = "UPDATE services set name=?, service_cost=? WHERE service_id=?";

    private static final String UPDATE_IMAGE_SERVICE = "UPDATE services set photo=? WHERE service_id=?";

    private static final String BLOCK_SERVICE = "UPDATE services SET status='заблокирован' where service_id=?";

    private static final String UNBLOCK_SERVICE = "UPDATE services SET status='открыт' where service_id=?";

    private static final String FIND_SERVICE = "SELECT name FROM services WHERE name=?";

    private static final String FIND_ORDERS_BY_SERVICE_ID = "SELECT * " +
            "FROM m2m_order_used_services " +
            "INNER JOIN services s on m2m_order_used_services.service_id = s.service_id " +
            "WHERE s.service_id=?";

    private static final String SERVICE_ID= "service_id";
    private static final String NAME= "name";
    private static final String SERVICE_COST= "service_cost";
    private static final String COST_ORDER= "cost";
    private static final String URL_PHOTO= "photo";
    private static final String FORMAT_ORDER_DATE = "yyyy-MM-dd";
    private static final String DATE_OF_SERVICE_DB = "date_of_service";
    private static final String PHOTO_PATH = "images/services/";


    @Override
    public List<AdditionalService> findServicesByStatus(String status) throws DAOException {
        List<AdditionalService> additionalServices = new ArrayList<>();
        ResultSet resultSet = null;
        try(Connection connection = connectionPool.takeConnection();
            PreparedStatement  preparedStatement = connection.prepareStatement(FIND_ALL_ADDITIONAL_SERVICES)) {

            preparedStatement.setString(1, status);
            resultSet = preparedStatement.executeQuery();

            logger.info("Find services has started");
            while (resultSet.next()){
                int service_id = resultSet.getInt(SERVICE_ID);
                String name = resultSet.getString(NAME);
                int cost = resultSet.getInt(SERVICE_COST);
                String urlPhoto = resultSet.getString(URL_PHOTO);

                AdditionalService additionalService = new AdditionalService();
                additionalService.setServiceId(service_id);
                additionalService.setName(name);
                additionalService.setCost(cost);
                additionalService.setUrlPhoto(urlPhoto);
                additionalServices.add(additionalService);

            }
            logger.info("Search for services ended");
        }
        catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }finally {
            if (resultSet!=null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new DAOException(e);
                }
            }
        }
        return additionalServices;
    }

    @Override
    public List<AdditionalService> findServicesByOrderId(int orderId) throws DAOException {
        List<AdditionalService> additionalServices = new ArrayList<>();
        ResultSet resultSet = null;
        try(Connection connection = connectionPool.takeConnection();
            PreparedStatement  preparedStatement = connection.prepareStatement(FIND_ADDITIONAL_SERVICES_BY_ORDER_ID)) {
            logger.info("Find services has started");

            preparedStatement.setInt(1, orderId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int service_id = resultSet.getInt(SERVICE_ID);
                String name = resultSet.getString(NAME);
                int cost = resultSet.getInt(SERVICE_COST);
                String urlPhoto = resultSet.getString(URL_PHOTO);
                Date dateOfService = resultSet.getDate(DATE_OF_SERVICE_DB);

                AdditionalService additionalService = new AdditionalService();
                additionalService.setServiceId(service_id);
                additionalService.setName(name);
                additionalService.setCost(cost);
                additionalService.setUrlPhoto(urlPhoto);
                additionalService.setDateOfService(dateOfService);

                additionalServices.add(additionalService);
                }
            logger.info("Search for services ended");
        }
        catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }finally {
            if (resultSet!=null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new DAOException(e);
                }
            }
        }
        return additionalServices;
    }

    @Override
    public boolean reservationService(int orderId, int serviceId, Date dateService) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        SimpleDateFormat formatDate = new SimpleDateFormat(FORMAT_ORDER_DATE);
        try {
            dateService = formatDate.parse(formatDate.format(dateService));
        } catch (ParseException e) {
            throw new DAOException(e);
        }
        try {
            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement(FIND_COST_OF_SERVICE_BY_ID);

            preparedStatement.setInt(1, serviceId);
            resultSet = preparedStatement.executeQuery();

            int totalCost = 0;
            if (resultSet.next()){
                int costService = resultSet.getInt(SERVICE_COST);
                totalCost+=costService;
            }

            preparedStatement = connection.prepareStatement(FIND_COST_OF_ORDER_BY_ID);

            preparedStatement.setInt(1, orderId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                int costOrder = resultSet.getInt(COST_ORDER);
                totalCost+=costOrder;
            }

            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(RESERVATION_SERVICE);

            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, serviceId);
            preparedStatement.setObject(3, dateService);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(UPDATE_COST_ORDER);

            preparedStatement.setInt(1, totalCost);
            preparedStatement.setInt(2, orderId);
            preparedStatement.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);

        }catch (ConnectionPoolException | SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ignored) {

            }
            throw new DAOException(e);
        }finally{
            if (resultSet != null) {
                try {
                    resultSet.close();
                }
                catch (SQLException e){
                    logger.log(Level.FATAL, "Fatal error when closing resultSet", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                }catch (SQLException e){
                    logger.log(Level.FATAL, "Fatal error when closing preparedStatement", e);
                }
            }
            if (connectionPool !=null ){
                connectionPool.releaseConnection(connection);
            }
        }
        return true;
    }

    @Override
    public boolean addService(String name, String cost) throws DAOException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try(Connection connection = connectionPool.takeConnection()){

            preparedStatement = connection.prepareStatement(FIND_SERVICE);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            String nameDB= "";
            if (resultSet.next()){
                nameDB = resultSet.getString(NAME);
            }

            if (nameDB.equals(name)){
                logger.info("Additional service already exist");
                return false;
            }

            preparedStatement = connection.prepareStatement(INSERT_SERVICE);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, Integer.parseInt(cost));
            preparedStatement.executeUpdate();

        }
        catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                }catch (SQLException e){
                    logger.log(Level.FATAL, "Fatal error when closing resultSet", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                }catch (SQLException e){
                    logger.log(Level.FATAL, "Fatal error when closing preparedStatement", e);
                }
            }
        }
        return true;
    }

    @Override
    public boolean changeService(String name, String cost, int serviceId) throws DAOException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try(Connection connection = connectionPool.takeConnection()) {

            preparedStatement = connection.prepareStatement(FIND_ORDERS_BY_SERVICE_ID);
            preparedStatement.setInt(1, serviceId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                logger.info("The service has been reserved");
                return false;
            }

            preparedStatement = connection.prepareStatement(UPDATE_SERVICE);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, Integer.parseInt(cost));
            preparedStatement.setInt(3, serviceId);
            preparedStatement.executeUpdate();

        }
        catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }finally {

            if (resultSet != null) {
                try {
                    resultSet.close();
                }catch (SQLException e){
                    logger.log(Level.FATAL, "Fatal error when closing resultSet", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                }catch (SQLException e){
                    logger.log(Level.FATAL, "Fatal error when closing preparedStatement", e);
                }
            }
        }
        return true;
    }

    @Override
    public boolean blockService(int serviceId) throws DAOException {

        try(Connection connection = connectionPool.takeConnection();
            PreparedStatement  preparedStatement = connection.prepareStatement(BLOCK_SERVICE)) {

            preparedStatement.setInt(1,serviceId);
            preparedStatement.executeUpdate();

        }
        catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
        return true;
    }

    @Override
    public void unblockService(int serviceId) throws DAOException {
        try(Connection connection = connectionPool.takeConnection();
            PreparedStatement  preparedStatement = connection.prepareStatement(UNBLOCK_SERVICE)) {

            preparedStatement.setInt(1,serviceId);
            preparedStatement.executeUpdate();

        }
        catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void updateImageService(int serviceId, String imagePath) throws DAOException {
        imagePath = PHOTO_PATH + imagePath;
        Connection connection = null;

        PreparedStatement preparedStatement = null;
        try{
            logger.info("Connecting with database");
            connection = connectionPool.takeConnection();
            logger.info("Successfully connecting");

            preparedStatement = connection.prepareStatement(UPDATE_IMAGE_SERVICE);

            preparedStatement.setString(1, imagePath);
            preparedStatement.setInt(2, serviceId);
            preparedStatement.executeUpdate();

        }catch (SQLException | ConnectionPoolException e){
            throw new DAOException(e);
        }finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                }catch (SQLException e){
                    logger.log(Level.FATAL, "Fatal error when closing preparedStatement", e);
                }
            }
            if (connectionPool !=null ){
                connectionPool.releaseConnection(connection);
            }
        }
    }
}
