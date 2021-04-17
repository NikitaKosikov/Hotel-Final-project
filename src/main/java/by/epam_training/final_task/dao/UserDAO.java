package by.epam_training.final_task.dao;

import by.epam_training.final_task.entity.RegistrationInfo;
import by.epam_training.final_task.entity.User;

public interface UserDAO {
    /**
     * Registration user.
     *
     * @param regInfo info about registration user.
     * @return User if the user was successfully registered, null if user with this email already exist.
     * @throws DAOException if an SQL error occurs.
     */
    User registration(RegistrationInfo regInfo) throws DAOException;

    /**
     * Authorization user.
     *
     * @param email email of user.
     * @param password password of user.
     * @return User if the user was successfully authorization, null if password or email is wrong.
     * @throws DAOException if an SQL error occurs.
     */
    User authorization(String email, String password) throws DAOException;
    User uploadAvatar(User user, String url) throws DAOException;

    /**
     * Updating user.
     *
     * @param user the user who changes.
     * @param regInfo info about registration user.
     * @return User if the user was successfully updated, null if user with this email already exist.
     * @throws DAOException if an SQL error occurs.
     */
    User updateUser(User user, RegistrationInfo regInfo) throws DAOException;

    /**
     * Updating user password.
     *
     * @param user the user who changes.
     * @param password new user password.
     * @return User if the password was successfully updated.
     * @throws DAOException if an SQL error occurs.
     */
    User changePassword(User user,String password) throws DAOException;

}
