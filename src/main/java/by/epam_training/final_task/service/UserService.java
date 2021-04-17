package by.epam_training.final_task.service;

import by.epam_training.final_task.dao.impl.exception.EmailOfUserAlreadyExistException;
import by.epam_training.final_task.entity.Card;
import by.epam_training.final_task.entity.RegistrationInfo;
import by.epam_training.final_task.entity.User;
import by.epam_training.final_task.service.exception.*;

public interface UserService {
    User authorization (String email, String password) throws ServiceException;
    User registration(RegistrationInfo regInfo, String repeatPassword) throws ServiceException, EmailOfUserAlreadyExistException;
    User changeAvatar(User user, String url) throws ServiceException;
    User updateUser(User user, RegistrationInfo reiInfo) throws ServiceException;
    User changePassword(User user,String currentPassword, String newPassword, String repeatPassword) throws ServiceException, InvalidPassword;
}
