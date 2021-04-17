package by.epam_training.final_task.service.impl;

import by.epam_training.final_task.dao.DAOException;
import by.epam_training.final_task.dao.DAOProvider;
import by.epam_training.final_task.dao.UserDAO;
import by.epam_training.final_task.entity.RegistrationInfo;
import by.epam_training.final_task.entity.User;
import by.epam_training.final_task.service.ServiceException;
import by.epam_training.final_task.service.UserService;
import by.epam_training.final_task.service.exception.*;
import by.epam_training.final_task.service.validation.UserValidator;

import java.util.ArrayList;
import java.util.List;


public class UserServiceImpl implements UserService {
    private static final String LOCALE_PHONE_NUMBER_ERROR = "Locale.error.phone.number";
    private static final String LOCALE_EMAIL_ERROR = "Locale.error.email";
    private static final String LOCALE_PASSWORD_ERROR = "Locale.error.password";
    private static final String LOCALE_EMPTY_NAME = "Locale.empty.name";
    private static final String LOCALE_EMPTY_SURNAME = "Locale.empty.surname";
    private static final String LOCALE_EMPTY_REPEAT_PASSWORD = "Locale.empty.repeat.password";
    private static final String LOCALE_EMPTY_PASSWORD = "Locale.empty.password";
    private static final String LOCALE_ERROR_PASSWORD_NEW_AND_CURRENT = "Locale.error.password.new.and.current";
    private static final String LOCALE_ERROR_PASSWORD_NEW_AND_REPEAT = "Locale.error.password.new.and.repeat";
    private static final String LOCALE_ERROR_PASSWORD_USER_AND_CURRENT = "Locale.error.password.user.and.current";

    @Override
    public User authorization(String email, String password) throws ServiceException {
        DAOProvider provider = DAOProvider.getInstance();
        UserDAO userDAO = provider.getUserDAO();

        User user;
        try {
            user = userDAO.authorization(email, password);
        }catch(DAOException e) {
            throw new ServiceException(e);
        }

        return user;
    }

    @Override
    public User registration(RegistrationInfo regInfo, String repeatPassword) throws ServiceException {


        DAOProvider provider = DAOProvider.getInstance();
        UserDAO userDAO = provider.getUserDAO();

        String name = regInfo.getName();
        String surname= regInfo.getSurname();
        String email = regInfo.getEmail();
        String phoneNumber = regInfo.getPhoneNumber();
        String password = regInfo.getPassword();

        List<Exception> userExceptionList = new ArrayList<>();

        if ("".equals(name)){
            userExceptionList.add(new EmptyNameException(LOCALE_EMPTY_NAME));
        }
        if ("".equals(surname)){
            userExceptionList.add(new EmptySurnameException(LOCALE_EMPTY_SURNAME));
        }
        if ("".equals(repeatPassword)){
            userExceptionList.add(new EmptyRepeatPasswordException(LOCALE_EMPTY_REPEAT_PASSWORD));
        }
        if ("".equals(password)){
            userExceptionList.add(new EmptyPasswordException(LOCALE_EMPTY_PASSWORD));
        }

        if (!UserValidator.isValidPhone(phoneNumber)){
            userExceptionList.add(new InvalidPhoneNumber(LOCALE_PHONE_NUMBER_ERROR));
        }
        if (!UserValidator.isValidEmail(email)){
            userExceptionList.add(new InvalidUserEmail(LOCALE_EMAIL_ERROR));
        }
        if (!UserValidator.isValidPasswordAndRepeatPassword(regInfo.getPassword(), repeatPassword)){
            userExceptionList.add(new InvalidPassword(LOCALE_PASSWORD_ERROR));
        }

        if (!userExceptionList.isEmpty()){
            throw new UserException("User data format error", userExceptionList);
        }

        User user;
        try {
            user = userDAO.registration(regInfo);
        }catch(DAOException e) {
            throw new ServiceException(e);
        }

        return user;
    }

    @Override
    public User changeAvatar(User user, String url) throws ServiceException {
        DAOProvider provider = DAOProvider.getInstance();
        UserDAO userDAO = provider.getUserDAO();
        try {
            user = userDAO.uploadAvatar(user, url);
        }catch(DAOException e) {
            throw new ServiceException(e);
        }

        return user;
    }

    @Override
    public User updateUser(User user, RegistrationInfo regInfo) throws ServiceException  {
        DAOProvider provider = DAOProvider.getInstance();
        UserDAO userDAO = provider.getUserDAO();

        String name = regInfo.getName();
        String surname= regInfo.getSurname();
        String email = regInfo.getEmail();
        String phoneNumber = regInfo.getPhoneNumber();

        List<Exception> userExceptionList = new ArrayList<>();

        if ("".equals(name)){
            userExceptionList.add(new EmptyNameException(LOCALE_EMPTY_NAME));
        }
        if ("".equals(surname)){
            userExceptionList.add(new EmptySurnameException(LOCALE_EMPTY_SURNAME));
        }

        if (!UserValidator.isValidPhone(phoneNumber)){
            userExceptionList.add(new InvalidPhoneNumber(LOCALE_PHONE_NUMBER_ERROR));
        }
        if (!UserValidator.isValidEmail(email)){
            userExceptionList.add(new InvalidUserEmail(LOCALE_EMAIL_ERROR));
        }

        if (!userExceptionList.isEmpty()){
            throw new UserException("User data format error", userExceptionList);
        }

        try {
            user = userDAO.updateUser(user, regInfo);
        }catch(DAOException e) {
            throw new ServiceException(e);
        }

        return user;
    }

    @Override
    public User changePassword(User user, String currentPassword, String newPassword, String repeatPassword)
            throws ServiceException, InvalidPassword {
        DAOProvider provider = DAOProvider.getInstance();
        UserDAO userDAO = provider.getUserDAO();

        if (!UserValidator.isValidUserPasswordAndCurrentPassword(user.getPassword(), currentPassword)){
            throw new InvalidPassword(LOCALE_ERROR_PASSWORD_USER_AND_CURRENT);
        }
        if (!UserValidator.isValidCurrentPasswordAndNewPassword(currentPassword, newPassword)){
            throw new InvalidPassword(LOCALE_ERROR_PASSWORD_NEW_AND_CURRENT);
        }
        if (!UserValidator.isValidPasswordAndRepeatPassword(newPassword, repeatPassword)){
            throw new InvalidPassword(LOCALE_ERROR_PASSWORD_NEW_AND_REPEAT);
        }

        try {
            user = userDAO.changePassword(user, newPassword);
        }catch(DAOException e) {
            throw new ServiceException(e);
        }

        return user;
    }
}
