package com.ssm.walk_match.service;

import java.util.Hashtable;

public class ServiceType {

	public static final String ROOT_DOMAIN = "http://kangshinhyun2.cafe24.com/";

    public static final int MSG_LOGIN = 1;
    public static final int MSG_JOIN = 2;
    public static final int MSG_JOIN_DOUBLE_ID = 3;
    public static final int MSG_IMG_UP = 4;
    public static final int MSG_MATCH = 5;
    public static final int MSG_MATCH_UP = 6;
    public static final int MSG_MATCH_STEP = 7;
    public static final int MSG_GCM_ID_JOIN = 8;
    public static final int MSG_GCM_GO_MESSAGE = 9;
    public static final int MSG_GCM_GO_MATCH = 10;
    public static final int MSG_GCM_GO_MATCH_GIVE_UP = 11;
    public static final int MSG_GCM_GO_MATCH_WIN = 12;
    public static final int MSG_GCM_GO_MATCH_END = 13;
    public static final int MSG_GEAR_STEP = 14;
    public static final int MSG_WALK_UP = 15;
    public static final int MSG_HISTORY = 16;

    private static final String URL_LOGIN 				 = 	 ROOT_DOMAIN + "login.php";
    private static final String URL_JOIN 				 =   ROOT_DOMAIN + "join.php";
    private static final String URL_JOIN_DOUBLE_ID 		 =   ROOT_DOMAIN + "join_double_id.php";
    private static final String URL_IMG_UP				 =   ROOT_DOMAIN + "img_up.php";
    private static final String URL_MATCH				 =   ROOT_DOMAIN + "match.php";
    private static final String URL_MATCH_UP			 =   ROOT_DOMAIN + "match_up.php";
    private static final String URL_MATCH_STEP			 =   ROOT_DOMAIN + "match_step.php";
    private static final String URL_GCM_ID_JOIN			 =   ROOT_DOMAIN + "gcm_id_join.php";
    private static final String URL_GCM_GO_MESSAGE		 = 	 ROOT_DOMAIN + "gcm_go_message.php";
    private static final String URL_GCM_GO_MATCH		 = 	 ROOT_DOMAIN + "gcm_go_match.php";
    private static final String URL_GCM_GO_MATCH_GIVE_UP = 	 ROOT_DOMAIN + "gcm_go_match_give_up.php";
    private static final String URL_GCM_GO_MATCH_WIN	 = 	 ROOT_DOMAIN + "gcm_go_match_win.php";
    private static final String URL_GCM_GO_MATCH_END	 = 	 ROOT_DOMAIN + "gcm_go_match_end.php";
    private static final String URL_GEAR_STEP			 = 	 ROOT_DOMAIN + "gear_step.php";
    private static final String URL_WALK_UP 			 = 	 ROOT_DOMAIN + "walk_up.php";
    private static final String URL_HISTORY				 = 	 ROOT_DOMAIN + "history.php";
    private Hashtable<Integer, String> msgURLTbl = new Hashtable<Integer, String>();

    private static ServiceType msgState = new ServiceType();

    private ServiceType() {
        msgURLTbl.put(new Integer(MSG_LOGIN), URL_LOGIN);
        msgURLTbl.put(new Integer(MSG_JOIN), URL_JOIN);
        msgURLTbl.put(new Integer(MSG_JOIN_DOUBLE_ID), URL_JOIN_DOUBLE_ID);
        msgURLTbl.put(new Integer(MSG_IMG_UP), URL_IMG_UP);
        msgURLTbl.put(new Integer(MSG_MATCH), URL_MATCH);
        msgURLTbl.put(new Integer(MSG_MATCH_UP), URL_MATCH_UP);
        msgURLTbl.put(new Integer(MSG_MATCH_STEP), URL_MATCH_STEP);
        msgURLTbl.put(new Integer(MSG_GCM_ID_JOIN), URL_GCM_ID_JOIN);
        msgURLTbl.put(new Integer(MSG_GCM_GO_MESSAGE), URL_GCM_GO_MESSAGE);
        msgURLTbl.put(new Integer(MSG_GCM_GO_MATCH), URL_GCM_GO_MATCH);
        msgURLTbl.put(new Integer(MSG_GCM_GO_MATCH_GIVE_UP), URL_GCM_GO_MATCH_GIVE_UP);
        msgURLTbl.put(new Integer(MSG_GCM_GO_MATCH_WIN), URL_GCM_GO_MATCH_WIN);
        msgURLTbl.put(new Integer(MSG_GCM_GO_MATCH_END), URL_GCM_GO_MATCH_END);
        msgURLTbl.put(new Integer(MSG_GEAR_STEP), URL_GEAR_STEP);
        msgURLTbl.put(new Integer(MSG_WALK_UP), URL_WALK_UP);
        msgURLTbl.put(new Integer(MSG_HISTORY), URL_HISTORY);
    }

    public static ServiceType getInstance() {
        return msgState;
    }

    public String getUrl(int serviceType) {
        return msgURLTbl.get(serviceType);
    }
}
