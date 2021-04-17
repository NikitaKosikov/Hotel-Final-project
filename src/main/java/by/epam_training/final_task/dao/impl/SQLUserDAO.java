package by.epam_training.final_task.dao.impl;

import by.epam_training.final_task.dao.DAOException;
import by.epam_training.final_task.dao.UserDAO;
import by.epam_training.final_task.dao.impl.connection_pool.ConnectionPool;
import by.epam_training.final_task.dao.impl.connection_pool.ConnectionPoolException;
import by.epam_training.final_task.entity.RegistrationInfo;
import by.epam_training.final_task.entity.User;
import by.epam_training.final_task.entity.UserType;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.*;

public class SQLUserDAO implements UserDAO {

    private static final Logger logger = Logger.getLogger(SQLUserDAO.class.getName());

    private static final String FIND_USER_BY_EMAIL = "SELECT * FROM users WHERE email=?";

    private static final String FIND_ID_OF_USER = "SELECT user_id FROM users WHERE email=?";

    private static final String FIND_USER_BY_EMAIL_AND_PASSWORD = "SELECT * FROM users " +
            "WHERE email=? AND password=?";

    private static final String INSERT_USER = "INSERT INTO users " +
            "(name,surname,email, password, phone_number, role_id) VALUES (?,?,?,?,?,1)";

    private static final String UPDATE_AVATAR = "UPDATE users SET photo=? WHERE user_id=?";

    private static final String UPDATE_USER = "UPDATE users " +
            "SET name=?, surname=?, email=?, phone_number=? WHERE user_id=?";

    private static final String UPDATE_PASSWORD = "UPDATE users SET password=? WHERE user_id=?";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();



    private static final String NAME_USER = "name";
    private static final String ROLE_ID = "role_id";
    private static final String USER_ID = "user_id";
    private static final String SURNAME_USER = "surname";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String PHOTO_USER = "photo";
    private static final String PHOTO_URL = "images/user_profile/default_photo.png";
    private static final String PHOTO_PATH = "images/user_profile/";

    @Override
    public User registration(RegistrationInfo regInfo) throws DAOException {
        User user = new User();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try(Connection connection = connectionPool.takeConnection()){

            preparedStatement = connection.prepareStatement(FIND_USER_BY_EMAIL);

            String name = regInfo.getName();
            String surname = regInfo.getSurname();
            String email = regInfo.getEmail();
            String password = regInfo.getPassword();
            String phoneNumber = regInfo.getPhoneNumber();

            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                logger.info("User with this email already exist");
                return null;
            }

            preparedStatement = connection.prepareStatement(INSERT_USER);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, phoneNumber);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(FIND_ID_OF_USER);

            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                int userId = resultSet.getInt(USER_ID);
                user.setUserId(userId);
            }

            user.setName(name);
            user.setSurname(surname);
            user.setEmail(email);
            user.setPassword(password);
            user.setPhoneNumber(phoneNumber);
            user.setUrlPhoto(PHOTO_URL);
            user.setUserType(UserType.CLIENT);
            user.setRoleId(1);

        }catch (SQLException | ConnectionPoolException e) {
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
        return user;
    }

    @Override
    public User authorization(String email, String password) throws DAOException{
        User user;
        ResultSet resultSet = null;
        try(Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_EMAIL_AND_PASSWORD);){



            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            logger.info("User verification has started");
            if (!resultSet.next()){
                logger.info("The password or email was entered incorrectly");
                return null;
            }

            int userId = resultSet.getInt(USER_ID);
            String name = resultSet.getString(NAME_USER);
            int role = resultSet.getInt(ROLE_ID);
            String surname = resultSet.getString(SURNAME_USER);
            String phoneNumber = resultSet.getString(PHONE_NUMBER);
            String urlPhoto = resultSet.getString(PHOTO_USER);


            user = new User();
            user.setUserId(userId);
            user.setName(name);
            user.setSurname(surname);
            user.setEmail(email);
            user.setPassword(password);
            user.setPhoneNumber(phoneNumber);
            user.setRoleId(role);
            user.setUrlPhoto(urlPhoto);

        }catch (SQLException | ConnectionPoolException exception) {
            throw new DAOException(exception);
        }finally {
            if (resultSet!=null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new DAOException(e);
                }
            }
        }
        return user;
    }

    @Override
    public User uploadAvatar(User user, String url) throws DAOException {
        url = PHOTO_PATH + url;
        Connection connection = null;

        PreparedStatement preparedStatement = null;
        try{
            logger.info("Connecting with database");
            connection = connectionPool.takeConnection();
            logger.info("Successfully connecting");

            preparedStatement = connection.prepareStatement(UPDATE_AVATAR);

            preparedStatement.setString(1, url);
            preparedStatement.setInt(2, user.getUserId());
            preparedStatement.executeUpdate();

            user.setUrlPhoto(url);

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
        return user;
    }

    @Override
    public User updateUser(User user, RegistrationInfo regInfo) throws DAOException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;

        try (Connection connection = connectionPool.takeConnection()){

            preparedStatement = connection.prepareStatement(FIND_USER_BY_EMAIL);

            String name = regInfo.getName();
            String surname = regInfo.getSurname();
            String email = regInfo.getEmail();
            String phoneNumber = regInfo.getPhoneNumber();

            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && !email.equals(user.getEmail())){
                logger.info("User with this email already exist");
                return null;
            }

            preparedStatement = connection.prepareStatement(UPDATE_USER);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phoneNumber);
            preparedStatement.setInt(5, user.getUserId());
            preparedStatement.executeUpdate();

            user.setName(name);
            user.setSurname(surname);
            user.setEmail(email);
            user.setPhoneNumber(phoneNumber);

        }catch (SQLException | ConnectionPoolException e){
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

        return user;
    }


    @Override
    public User changePassword(User user, String password) throws DAOException {

        try(Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PASSWORD)){

            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, user.getUserId());
            preparedStatement.executeUpdate();

            user.setPassword(password);

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
        return user;
    }
}
