package ua.nure.lymar.airlines.controller;

import java.util.HashMap;

import ua.nure.lymar.airlines.controller.adminCommands.AlertEditCommand;
import ua.nure.lymar.airlines.controller.adminCommands.AlertListCommand;
import ua.nure.lymar.airlines.controller.adminCommands.AlertSaveCommand;
import ua.nure.lymar.airlines.controller.crewCommands.AddStaffInCrewCommand;
import ua.nure.lymar.airlines.controller.crewCommands.AlertToAdminCommand;
import ua.nure.lymar.airlines.controller.crewCommands.CrewDeleteCommand;
import ua.nure.lymar.airlines.controller.crewCommands.CrewEditCommand;
import ua.nure.lymar.airlines.controller.crewCommands.CrewListCommand;
import ua.nure.lymar.airlines.controller.crewCommands.CrewSaveCommand;
import ua.nure.lymar.airlines.controller.crewCommands.CrewShowCommand;
import ua.nure.lymar.airlines.controller.crewCommands.DeleteStaffFromCrewCommand;
import ua.nure.lymar.airlines.controller.flightCommands.*;
import ua.nure.lymar.airlines.controller.staffCommands.StaffDeleteCommand;
import ua.nure.lymar.airlines.controller.staffCommands.StaffEditCommand;
import ua.nure.lymar.airlines.controller.staffCommands.StaffListCommand;
import ua.nure.lymar.airlines.controller.staffCommands.StaffSaveCommand;
import ua.nure.lymar.airlines.controller.userCommands.SaveMessageCommand;
import ua.nure.lymar.airlines.controller.userCommands.UserDeleteCommand;
import ua.nure.lymar.airlines.controller.userCommands.UserEditCommand;
import ua.nure.lymar.airlines.controller.userCommands.UserEditPassword;
import ua.nure.lymar.airlines.controller.userCommands.UserListCommand;
import ua.nure.lymar.airlines.controller.userCommands.UserSaveCommand;
import ua.nure.lymar.airlines.controller.userCommands.UserSavePassword;
import ua.nure.lymar.airlines.controller.userCommands.UserSetDefaultPassword;
import ua.nure.lymar.airlines.util.Commands;

class CommandMap {
    private static final HashMap<String, Command> commandMap = new HashMap<>();

    static {
        commandMap.put(Commands.USERLIST_COMMAND, new UserListCommand());
        commandMap.put(Commands.USEREDIT_COMMAND, new UserEditCommand());
        commandMap.put(Commands.USEREDITPASS_COMMAND, new UserEditPassword());
        commandMap.put(Commands.USERSAVEPASS_COMMAND, new UserSavePassword());
        commandMap.put(Commands.USERSETDEFPASS_COMMAND, new UserSetDefaultPassword());
        commandMap.put(Commands.USERSAVE_COMMAND, new UserSaveCommand());
        commandMap.put(Commands.USERDELETE_COMMAND, new UserDeleteCommand());
        commandMap.put(Commands.STAFFLIST_COMMAND, new StaffListCommand());
        commandMap.put(Commands.STAFFEDIT_COMMAND, new StaffEditCommand());
        commandMap.put(Commands.STAFFSAVE_COMMAND, new StaffSaveCommand());
        commandMap.put(Commands.STAFFDELETE_COMMAND, new StaffDeleteCommand());
        commandMap.put(Commands.LOGIN_COMMAND, new LoginCommand());
        commandMap.put(Commands.LOGOUT_COMMAND, new LogoutCommand());
        commandMap.put(Commands.RUSLANGUAGE_COMMAND, new RUSLanguageCommand());
        commandMap.put(Commands.ENGLANGUAGE_COMMAND, new ENGLanguageCommand());
        commandMap.put(Commands.MAIN_COMMAND, new MainCommand());
        commandMap.put(Commands.INDEX_PAGE_COMMAND, new MainCommand());
        commandMap.put(Commands.CREWLIST_COMMAND, new CrewListCommand());
        commandMap.put(Commands.CREWEDIT_COMMAND, new CrewEditCommand());
        commandMap.put(Commands.CREWSAVE_COMMAND, new CrewSaveCommand());
        commandMap.put(Commands.CREWDELETE_COMMAND, new CrewDeleteCommand());
        commandMap.put(Commands.CREWSHOW_COMMAND, new CrewShowCommand());
        commandMap.put(Commands.ADDSTAFFINCREW_COMMAND, new AddStaffInCrewCommand());
        commandMap.put(Commands.DELSTAFFFROMCREW_COMMAND, new DeleteStaffFromCrewCommand());
        commandMap.put(Commands.FLIGHTLIST_COMMAND, new FlightListCommand());
        commandMap.put(Commands.FLIGHTEDIT_COMMAND, new FlightEditCommand());
        commandMap.put(Commands.FLIGHTSAVE_COMMAND, new FlightSaveCommand());
        commandMap.put(Commands.FLIGHTDELETE_COMMAND, new FlightDeleteCommand());
        commandMap.put(Commands.ALERT_TO_ADMIN_COMMAND, new AlertToAdminCommand());
        commandMap.put(Commands.SAVE_MESSAGE_COMMAND, new SaveMessageCommand());
        commandMap.put(Commands.ALERT_LIST_COMMAND, new AlertListCommand());
        commandMap.put(Commands.ALERT_EDIT_COMMAND, new AlertEditCommand());
        commandMap.put(Commands.ALERT_SAVE_COMMAND, new AlertSaveCommand());
        commandMap.put(Commands.SORT_FLIGHTS_BYNAME_COMMAND, new SortFlightsByNameCommand());
        commandMap.put(Commands.SORT_FLIGHTS_BYDATE_COMMAND, new SortFlightsByDateCommand());
        commandMap.put(Commands.SORT_FLIGHTS_BYDEPARTURE_COMMAND, new SortFlightsByDepartureCityCommand());
        commandMap.put(Commands.SORT_FLIGHTS_BYARRIVAL_COMMAND, new SortFlightsByArrivalCityCommand());
        commandMap.put(Commands.FLIGHT_SEARCH_COMMAND, new FlightSearchCommand());
        commandMap.put(Commands.FLIGHT_SEARCH_RESULT_COMMAND, new FlightSearchResultCommand());
    }

    static Command getCommand(String name){
        if (name != null || commandMap.containsKey(name))
            return commandMap.get(name);
        return null;
    }
}
