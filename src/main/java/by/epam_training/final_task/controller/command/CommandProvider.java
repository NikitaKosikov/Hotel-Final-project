package by.epam_training.final_task.controller.command;

import by.epam_training.final_task.controller.command.impl.*;
import by.epam_training.final_task.controller.command.impl.admin.*;
import by.epam_training.final_task.controller.command.impl.ChangeOrder;
import by.epam_training.final_task.controller.command.impl.ChangePassword;
import by.epam_training.final_task.controller.command.impl.ChangeProfile;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private Map<CommandName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(CommandName.GOTOINDEXPAGE, new GoToIndexPage());
        commands.put(CommandName.GOTOAUTHORIZATIONPAGE, new GoToAuthorizationPage());
        commands.put(CommandName.GOTOREGISTRATIONPAGE, new GoToRegistrationPage());
        commands.put(CommandName.GOTOPROFILEPAGE, new GoToProfilePage());
        commands.put(CommandName.GOTOACTIVEORDERSPAGE, new GoToActiveOrdersPage());
        commands.put(CommandName.GOTOARCHIVEORDERSPAGE, new GoToArchiveOrdersPage());
        commands.put(CommandName.GOTORETURNEDORDERSPAGE, new GoToReturnedOrdersPage());
        commands.put(CommandName.GOTOADDITIONALSERVICESPAGE, new GoToAdditionalServicesPage());
        commands.put(CommandName.GOTOPAYMENTPAGE, new GoToPaymentPage());
        commands.put(CommandName.GOTOADDROOMPAGE, new GoToAddRoomPage());
        commands.put(CommandName.GOTOCHANGEROOMPAGE, new GoToChangeRoomPage());
        commands.put(CommandName.GOTOCHANGESERVICEPAGE, new GoToChangeServicePage());
        commands.put(CommandName.GOTOADDSERVICEPAGE, new GoToAddServicePage());
        commands.put(CommandName.GOTOBLOCKEDROOMSPAGE, new GoToBlockedRoomsPage());
        commands.put(CommandName.GOTOBLOCKEDSERVICESPAGE, new GoToBlockedServicesPage());
        commands.put(CommandName.SHOWMYADDITIONALSERVICEPAGE, new ShowMyAdditionalServicePage());
        commands.put(CommandName.CHANGELOCALE, new ChangeLocale());
        commands.put(CommandName.CHANGEPROFILE, new ChangeProfile());
        commands.put(CommandName.CHANGEORDER, new ChangeOrder());
        commands.put(CommandName.CHANGEPASSWORD, new ChangePassword());
        commands.put(CommandName.CHANGEROOM, new ChangeRoom());
        commands.put(CommandName.RETURNORDER, new ReturnOrder());
        commands.put(CommandName.RESERVATIONROOM, new ReservationRoom());
        commands.put(CommandName.RESERVATIONSERVICE, new ReservationService());
        commands.put(CommandName.TOPAYORDER, new ToPayOrder());
        commands.put(CommandName.SEARCHROOMSBYCRYTERIA, new SearchRoomsByCriteria());
        commands.put(CommandName.SIGNIN, new SignIn());
        commands.put(CommandName.SIGNUP, new SignUp());
        commands.put(CommandName.LOGOUT, new Logout());
        commands.put(CommandName.ADDNEWROOM, new AddNewRoom());
        commands.put(CommandName.BLOCKROOM, new BlockRoom());
        commands.put(CommandName.ADDNEWSERVICE, new AddNewService());
        commands.put(CommandName.BLOCKSERVICE, new BlockService());
        commands.put(CommandName.CHANGESERVICE, new ChangeService());
        commands.put(CommandName.UNBLOCKROOM, new UnblockRoom());
        commands.put(CommandName.UNBLOCKSERVICE, new UnblockService());
    }

    public Command takeCommand(String command){
        CommandName commandName = CommandName.valueOf(command.toUpperCase());
        return commands.get(commandName);
    }
}
