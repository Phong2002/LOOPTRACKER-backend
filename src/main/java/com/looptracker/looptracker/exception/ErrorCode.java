package com.looptracker.looptracker.exception;

import java.util.Map;

public class ErrorCode {
    //      AUTH
    public static final String USER_ACCOUNT_IS_LOCKED = "AU_00001";
    public static final String OLD_PASSWORD_IS_INCORRECT = "AU_00002";

//    REGISTRATION_REQUEST

    public static final String REGISTRATION_REQUEST_NOT_FOUND = "RR_00001";

    //    TOURPACKAGE
    public static final String TOUR_PACKAGE_ALREADY_EXISTS = "TP_00001";
    public static final String TOUR_PACKAGE_NOT_FOUND = "TP_00002";

    //    TOUR_INSTANCE
    public static final String TOUR_INSTANCE_NOT_FOUND = "TI_00001";
    public static final String TOUR_INSTANCE_ALREADY_COMPLETED = "TI_00001";

    //    TOUR_ASSIGMENT
    public static final String TOUR_ASSIGNMENT_NOT_FOUND = "TA_00001";

    //    ITEM
    public static final String ITEM_NOT_FOUND = "IT_00001";

    //    ASSIGNMENT ITEM
    public static final String ASSIGNMENT_ITEM_NOT_FOUND = "IT_00001";

    //    PASSENGER
    public static final String PASSENGER_NOT_FOUND = "PA_00001";

    //    RIDER
    public static final String RIDER_NOT_FOUND = "RD_00001";
    public static final String TOUR_GUIDE_NOT_FOUND = "RD_00002";
    public static final String MUST_HAVE_ROLE_RIDER = "RD_00003";
    public static final String RIDER_IN_OTHER_TOUR = "RD_00004";
    public static final String RIDER_IS_NOT_READY = "RD_00005";
    public static final String CAN_NOT_UPDATE_DRIVER_STATUS = "RD_00006";

    //    USER
    public static final String USER_NOT_FOUND = "U_00001";

    //    USER INFORMATION
    public static final String CCCD_ALREADY_EXISTS = "UI_00001";
    public static final String GPLX_ALREADY_EXISTS = "UI_00002";
    public static final String EMAIL_ALREADY_EXISTS = "UI_00003";
    public static final String PHONE_NUMBER_ALREADY_EXISTS = "UI_00004";

    //    ROLE
    public static final String MUST_HAVE_ADMIN_ROLE = "RL_00001";
    public static final String MUST_HAVE_MANAGER_ROLE = "RL_00002";
    public static final String MUST_HAVE_TOUR_GUIDE_ROLE = "RL_00003";
    public static final String MUST_HAVE_EASY_RIDER_ROLE = "RL_00004";

    //    ACTION
    public static final String CAN_NOT_DELETE = "AC_00001";

}
