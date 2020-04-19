package srsCommon;

// Need to eliminate
//<!-- #INCLUDE FILE=./adovbs.inc -->

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Utils.Encryption;
import com.Utils.GenericException;
import com.Utils.UserLib;
import com.rayweb.NavTab;
import com.rayweb.RayPage;

// Need to convert
//<!-- #INCLUDE FILE=./sasLocks.asp -->
//import sasLocks.*;

/** Resource Data Access **/
// *****************************************************************************************
// ** Handles database connectivity and data retrieval
// *****************************************************************************************
public class srsSiteLib
{

	// AppID Constants
	// static String APPID_DOMESTIC = "2000";
	// static String APPID_INTERNATIONAL = "2001";
	public static String APPID = "2002"; // "admin" login used by the Web
																			 // application, for updates and system
																			 // queries
	public static String APPKEY = "srsweb";

	public static final String SITE_ROOT = "/srs/";
	public static final String JSP_ROOT = SITE_ROOT + "web/";
	public static final String FORWARD_JSP_ROOT = "/web/";
	public static final String IMAGES = SITE_ROOT + "images/";
	public static final String SPACER = IMAGES + "space.gif";

	// Site URL
	private static String mSiteURL = null;

	// QDI Status Constants
	public static final String PARAM_ACTIVE = "active";
	public static final String PARAM_ALL = "all";
	public static final String PARAM_INTERIM = "interim";
	public static final String PARAM_CLOSED = "closed";
	public static final String PARAM_LATEST = "latest";
	public static final String PARAM_OPEN = "open";

	// Button Constants
	public static final String BUTTON_ACCEPT_NEW_CCSL_LIST = "Accept changes";
	public static final String BUTTON_ACCEPT_NEW_DSL_LIST = "Accept changed DSL";
	public static final String BUTTON_ACCEPT_UPDATE_DSL_LIST = "Accept updated DSL";
	public static final String BUTTON_ACCEPT_NEW_ASL_LIST = "Accept changed ASL";
	public static final String BUTTON_ACCEPT_UPDATE_ASL_LIST = "Accept updated ASL";
	public static final String BUTTON_ADD = "Add";
	public static final String BUTTON_ADD_ASL = "Add Site to ASL";
	public static final String BUTTON_ADD_CONFIRM = "Confirm Add";
	public static final String BUTTON_ADD_CONFIRMED = "Confirmed Add";
	public static final String BUTTON_ADD_SITE = "Add Site";
	public static final String BUTTON_AUTHENTICATE = "Authenticate";
	public static final String BUTTON_BACK = "<< Back";
	public static final String BUTTON_BACK2 = "New Search";
	public static final String BUTTON_CANCEL = "SRS Tools";
	public static final String BUTTON_CANCEL2 = "Cancel";
	public static final String BUTTON_CANCEL_ADD = "Cancel Add";
	public static final String BUTTON_CANCEL_ADD2 = "Cancel";
	public static final String BUTTON_CANCEL_UPDATE = "Cancel Update";
	public static final String BUTTON_CANCEL_UPDATE2 = "Cancel";
	public static final String BUTTON_CHANGE_PASSWORD = "Change Password";
	public static final String BUTTON_CLEAR = "Clear";
	public static final String BUTTON_CLOSE = "Close";
	public static final String BUTTON_CONTINUE = "Continue";
	public static final String BUTTON_CREATE = "Create Id";
	public static final String BUTTON_DELETE = "DELETE";
	public static final String BUTTON_EDIT = "Edit";
	public static final String BUTTON_EMAIL_PASSWORD = "E-mail Password";
	public static final String BUTTON_FINISH = "Finish";
	public static final String BUTTON_LOGIN = "Login";
	public static final String BUTTON_LOGOUT = "SRS Logout";
	public static final String BUTTON_NEXT = "Next >>";
	public static final String BUTTON_PREV = "<< Previous";
	public static final String BUTTON_NAME = "BUTTON";
	public static final String BUTTON_REJECT_NEW_CCSL_LIST = "Reject changes";
	public static final String BUTTON_REJECT_NEW_DSL_LIST = "Reject changed DSL ";
	public static final String BUTTON_REJECT_UPDATE_DSL_LIST = "Reject updated DSL ";
	public static final String BUTTON_REJECT_NEW_ASL_LIST = "Reject changed ASL ";
	public static final String BUTTON_REJECT_UPDATE_ASL_LIST = "Reject updated ASL ";
	public static final String BUTTON_REMOVE = "Remove";
	public static final String BUTTON_REMOVE_ASL = "ASL Sites";
	public static final String BUTTON_REMOVE_DSL = "Supplier DSL Sites";
	public static final String BUTTON_RESET_PW = "Reset Password";
	public static final String BUTTON_SAVE = "Save";
	public static final String BUTTON_SRS_HOME = "SRS Home";
	public static final String BUTTON_SRS_HOME_PAGE = "SRS Home";
	public static final String BUTTON_SEARCH_BID_EVAL = "Search Bid Evaluations";
	public static final String BUTTON_SELECT_BID_EVAL = "Select My Bid Evaluations";
	public static final String BUTTON_SELECT_SITE = "Select Site";
	public static final String BUTTON_UPDATE = "Update";
	public static final String BUTTON_UPDATE_CONFIRM = "Confirm Update";
	public static final String BUTTON_UPDATE_CONFIRMED = "Confirmed Update";
	public static final String BUTTON_SEARCH = "Search";
	public static final String BUTTON_ADD_SUPPLIER = "Add Supplier";
	public static final String BUTTON_DELETE_SELECTED = "Delete Selected";
	public static final String BUTTON_REVIEW_SELECTED = "Review Selected";
	public static final String BUTTON_EDIT_SELECTED = "Edit Selected";
	public static final String BUTTON_SELECT_NEW_SUPPLIER = "Select New Supplier for Evaluation";
	public static final String BUTTON_CREATE_NEW_EVAL = "Create New Evaluation";
	public static final String BUTTON_MODIFY_SEARCH = "<< Modify Search";
	public static final String BUTTON_SUPPLIER_CRITERIA = "<< Select Supplier Criteria";
	public static final String BUTTON_SRCH_RESULTS = "<< Search Results";
	public static final String BUTTON_SEL_BUSINESSES = "<< Select Businesses";
	public static final String BUTTON_SEL_SITES = "<< Select Sites";
	public static final String BUTTON_CLOSE_WINDOW = "Close Window";
	public static final String PARAM_RETURN_TO_PAGE = "rtp";
	public static final int PAGE_QDI_PROGRAM_SUMMARY_REPORT = 122;
	// DSL Sort Order Constants
	public static final int DSL_ADDED = 1;
	public static final int DSL_REPLACED = 2;
	public static final int DSL_REMOVED = 3;

	// ASL Sort Order Constants
	public static final int ASL_ADDED = 1;
	public static final int ASL_REPLACED = 2;
	public static final int ASL_REMOVED = 3;

	// DSL Sort Order Name Constants
	public static final String DSL_ADDED_NAME = "Added";
	public static final String DSL_REPLACED_NAME = "Removed";
	public static final String DSL_REMOVED_NAME = "Removed";

	// ASL Sort Order Name Constants
	public static final String ASL_ADDED_NAME = "Added";
	public static final String ASL_REPLACED_NAME = "Removed";
	public static final String ASL_REMOVED_NAME = "Removed";

	// DSL Action Codes
	public static final String DSL_ADD = "A";
	public static final String DSL_REMOVE = "R";

	// ASL Action Codes
	public static final String ASL_ADD = "A";
	public static final String ASL_REMOVE = "R";

	// DSL Modify Type Codes
	public static final String DSL_ADD_MODIFY = "A";
	public static final String DSL_UPDATE_MODIFY = "U";

	// ASL Modify Type Codes
	public static final String ASL_ADD_MODIFY = "A";
	public static final String ASL_UPDATE_MODIFY = "U";

	// DSL Modify Effort Codes
	public static final String DSL_CONFIRM_MODIFY = "C";
	public static final String DSL_TRY_MODIFY = "T";

	// Procedure Error Flag Constants
	public static final int SUCCESS = 0;
	public static final int CRITICAL = 1;
	public static final int WARNING = 2;
	public static final int INFORMATIONAL = 3;

	// Lock Type Constants
	public static final int LOCKTYPE_SUPPLIER_ASLCSL = 1;
	public static final int LOCKTYPE_SUPPLIER_RATINGS = 2;
	public static final int LOCKTYPE_SUPPLIER_AUDIT = 3;

	// Cookie Name Constants
	public static final String COOKIE_LOGIN = "li";
	public static final String COOKIE_USERID = "uid";
	public static final String COOKIE_ACL = "acl";
	public static final String COOKIE_APPID = "appid";
	public static final String COOKIE_LOCKID = "lid";
	public static final String COOKIE_ENCPW = "uencpw";
	public static final String COOKIE_DISPLAYNAME = "dname";
	public static final String COOKIE_EXPIRED = "expired";

	public static final String USERLIB_CUSTPARAM_APPID = "AppId";
	public static final String USERLIB_CUSTPARAM_LOCKID = "LockId";
	public static final String USERLIB_CUSTPARAM_ENCPW = "UEncpw";
	public static final String USERLIB_CUSTPARAM_DISPLAYNAME = "DName";
	public static final String USERLIB_CUSTPARAM_EXPIRED = "Expired";

	public static final String DATE_ENCRYPT_KEY = "dkey";

	// Other Constants
	public static final String DEFAULT_KEYWORD = "raytheon";
	public static final String DEFAULT_PASSWORD = "raytheon";
	public static final String APP_REG_SUBKEY = "Software\\Raytheon\\SAS";
	public static final String FIELD_DELIMITER = "@!@";
	public final static int DSL_ALL_DK = -1;
	public final static int ASL_ALL_DK = -1;
	public final static int ASL_UNKNOWN_DK = 0;
	public final static int SECONDS_UNTIL_TRANSACTIONS_EXPIRE = 3600;
	public static final String DSL_ALL_DESC = "ALL";
	public static final String ASL_ALL_DESC = "ALL";
	public static final String ASL_UNKNOWN_DESC = "UNKNOWN";
	public static String COPYRIGHT_YEAR = "2003";
	// SPM = SUPPLIER_PERFORMANCE_MULTIPLIER
	public static int SPM_PERCENT_DISPLAY_DECIMALS = 0;
	// redirected from the srs_add
	public static final String SRS_ADD_REDIR = "1";

	// Page Display States
	public static final int PAGE_ABORT = 1;
	public static final int PAGE_DISPLAY = 2;
	public static final int PAGE_DISPLAY_LIST = 3;
	public static final int PAGE_DISPLAY_SUPPLIER = 4;
	public static final int PAGE_DISPLAY_SUPPLIER_ADD_ASL = 5;
	public static final int PAGE_SUPPLIER_UPDATE_DSL = 5;
	public static final int PAGE_SUPPLIER_ADD_DSL = 6;
	public static final int PAGE_SUPPLIER_UPDATE_DSL_CONFIRM = 7;
	public static final int PAGE_SUPPLIER_ADD_DSL_CONFIRM = 8;
	public static final int PAGE_SUPPLIER_ADD_DSL_CONFIRMED = 9;
	public static final int PAGE_SUPPLIER_UPDATE_DSL_CONFIRMED = 10;
	public static final int PAGE_COMMENT_ADD = 11;
	public static final int PAGE_COMMENT_UPDATE = 12;
	public static final int PAGE_ADDED_COMMENT = 13;
	public static final int PAGE_UPDATED_COMMENT = 14;
	public static final int PAGE_SUPPLIER_ADD_CCSL = 15;
	public static final int PAGE_SUPPLIER_ADD_CCSL_CONFIRM = 16;
	public static final int PAGE_SUPPLIER_ADD_CCSL_CONFIRMED = 17;
	public static final int PAGE_CCSL_ADD = 18;
	public static final int PAGE_ADDED_CCSL = 19;
	public static final int PAGE_CCSL_ADD_CONFIRM = 20;
	public static final int PAGE_REMOVED_CCSL = 21;
	public static final int PAGE_PASSWORD_INITIAL = 22;
	public static final int PAGE_PASSWORD_FUNCTION_OK = 23;
	public static final int PAGE_PASSWORD_FUNCTION_NOT_OK = 24;
	public static final int PAGE_DISPLAY_SUPPLIER_ADD = 25;
	public static final int PAGE_DISPLAY_SUPPLIER_EDIT = 26;
	public static final int PAGE_SUPPLIER_ADD_ASL = 27;
	public static final int PAGE_SUPPLIER_UPDATE_ASL = 28;
	public static final int PAGE_SUPPLIER_UPDATE_ASL_CONFIRM = 29;
	public static final int PAGE_SUPPLIER_ADD_ASL_CONFIRM = 30;
	public static final int PAGE_SUPPLIER_ADD_ASL_CONFIRMED = 31;
	public static final int PAGE_SUPPLIER_UPDATE_ASL_CONFIRMED = 32;

	// Page Display Footer States
	public static final int SF_NONE = 0;
	public static final int SF_NORMAL = 1;
	public static final int SF_MINIMAL = 2;

	// Page Index Constants
	public static final int PAGE_NONE = 0;
	public static final int PAGE_HOME = 1;
	public static final int PAGE_SAS_MAINT = 2;
	public static final int PAGE_SAS_INFO = 3;
	public static final int PAGE_RATING_PARAM = 4;
	public static final int PAGE_RATING_MAINT = 5;
	public static final int PAGE_ASL_CLS_PARAM = 6;
	public static final int PAGE_ASL_ALERT_MAINT = 7;
	// public static final int PAGE_CSL_LIST = 8;
	public static final int PAGE_CSL_MAINT = 9;
	public static final int PAGE_CREATEID = 10;
	public static final int PAGE_INFO_PARAM = 11;
	public static final int PAGE_SUPPLIER_INFO = 12;
	public static final int PAGE_SUPPLIER_CSL_INFO = 13;
	public static final int PAGE_ASL_SUPPLIERS = 14;
	public static final int PAGE_CSL_SUPPLIERS = 15;
	public static final int PAGE_LOGIN = 16;
	public static final int PAGE_SUPPLIER_DELIVERIES = 17;
	public static final int PAGE_NEW_SUPPLIER = 18;
	public static final int PAGE_TRAINING = 19;
	public static final int PAGE_JAVASC_WARN = 20;
	public static final int PAGE_USER_PWD = 27;
	public static final int PAGE_NEWS_ADMIN = 30;
	public static final int PAGE_LEGACY_SUPPLIER_DELIVERIES = 31;
	public static final int PAGE_ASL_HISTORY = 32;
	public static final int PAGE_CSL_HISTORY = 33;
	public static final int PAGE_MULTI_PO_DETAIL = 34;
	public static final int PAGE_RATING_DISP = 35;
	public static final int PAGE_RATING_LEGACY_DISP = 36;
	public static final int PAGE_LEGACY_RATING_MAINT = 37;
	public static final int PAGE_LEGACY_RATING_PARAM = 38;
	public static final int PAGE_LEGACY_ADDRESS = 39;
	public static final int PAGE_LEGACY_ADDRESS_PARAM = 40;
	public static final int PAGE_LEGACY_SUPP_DETAIL_PARAM = 41;
	public static final int PAGE_LEGACY_INFO = 42;
	public static final int PAGE_ASL = 43;
	public static final int PAGE_COGNOS_PORTAL = 44;
	public static final int PAGE_DSL = 45;
	public static final int PAGE_CCSL = 46;
	public static final int PAGE_LEGACY_SUPPLIER_SEARCH = 47;
	public static final int PAGE_COMMENTS = 48;
	public static final int PAGE_NEW_USER = 49;
	public static final int PAGE_ENTERPRISE_SUPPLIER_SEARCH = 50;
	public static final int PAGE_PASSWORD = 51;
	public static final int PAGE_BID_EVAL_SITE = 52;
	public static final int PAGE_BID_EVAL_SELECT = 53;
	// public static final int PAGE_BID_EVAL = 54;
	public static final int PAGE_BID_EVAL_SUPPLIER = 55;
	public static final int PAGE_BID_EVAL_PART_SELECT = 56;
	public static final int PAGE_BID_EVAL_UCC_SELECT = 57;
	public static final int PAGE_BID_EVAL_SUPPLIER_SELECT = 58;
	public static final int PAGE_ADD_BID_EVAL = 59;
	public static final int PAGE_EDIT_BID_EVAL = 60;
	public static final int PAGE_USER_SRCH = 61;
	public static final int PAGE_MOD_USER_ROLES = 62;
	public static final int PAGE_CHANGE_PW = 63;
	public static final int PAGE_VIEW_USER_INFO = 64;
	public static final int PAGE_MOD_USER_INFO = 65;
	public static final int PAGE_NEWS_ADM = 66;
	public static final int PAGE_LOGOUT = 67;
	public static final int PAGE_QDI_HOME = 68;
	public static final int PAGE_QDI_NEW_EVAL = 69;
	public static final int PAGE_QDI_EDIT = 70;
	public static final int PAGE_QDI_LEAD_HOME = 71;
	public static final int PAGE_QDI_EDIT_SCORECARD = 72;
	public static final int PAGE_QDI_ASSIGN_LEAD = 73;
	public static final int PAGE_QDI_EDIT_LEAD_EVAL = 74;
	public static final int PAGE_QDI_USER_MAINT = 75;
	public static final int PAGE_QDI_BUSINESS = 76;
	public static final int PAGE_QDI_STATUS = 77;
	public static final int PAGE_QDI_ROADMAP = 78;
	public static final int PAGE_QDI_COMMENTS = 79;
	public static final int PAGE_PROGRAM_SEARCH = 80;
	public static final int PAGE_BUSINESS_UPDATE = 81;
	public static final int PAGE_PROGRAM_SELECT = 82;
	public static final int PAGE_USER_AUTHENTICATION = 83;
	public static final int PAGE_ADD_SUPPLIER = 84;
	public static final int PAGE_ADD_USER_ROLES = 85;
	public static final int PAGE_DEL_USER_ROLES = 86;
	public static final int PAGE_BID_EVAL_SEARCH = 87;
	public static final int PAGE_BID_EVAL_OWNERS = 88;
	public static final int PAGE_QDI_SUPPLIER_EVALS = 89;
	public static final int PAGE_QDI_SUPPLIER_SRCH = 90;
	public static final int PAGE_QDI_SUPPLIER_SRCH_CRIT = 91;
	public static final int PAGE_QDI_SUPPLIER_SRCH_SUITE = 92;
	public static final int PAGE_QDI_SUPPLIER_SRCH_RESULTS = 93;
	public static final int PAGE_QDI_SEL_BUSINESSES = 94;
	public static final int PAGE_QDI_IS_EVALUATOR = 95;
	public static final int PAGE_QDI_SEL_SITES = 96;
	public static final int PAGE_QDI_PROGRAM_SRCH_CRIT = 97;
	public static final int PAGE_QDI_PROGRAM_SRCH_RESULTS = 98;
	public static final int PAGE_QDI_PO_SRCH_CRIT = 99;
	public static final int PAGE_QDI_PO_SRCH_RESULTS = 100;
	public static final int PAGE_QDI_PROCESS_EVAL = 101;
	public static final int PAGE_QDI_MESSAGE = 102;
	public static final int PAGE_QDI_MOD_WEIGHTS = 103;
	public static final int PAGE_QDI_MOD_WEIGHTS_DISP = 104;
	public static final int PAGE_QDI_SCORECARD_SPRR_INT = 105;
	public static final int PAGE_QDI_SUPPLIER_EVALS_SPRR_INT = 106;
	public static final int PAGE_QDI_GLOBAL_STATUS = 107;

	// Page Footer Constants

	// Parmeter Constants
	public static final String PARAM_BID_EVAL_DK = "bedk";
	public static final String PARAM_BID_EVAL_FEED_CODE_DK = "befcdk";
	public static final String PARAM_BID_EVAL_SUPPLIER_DK = "besupdk";
	public static final String PARAM_BID_EVAL_SITE_DK = "besitedk";
	public static final String PARAM_BROWSER_TYPE = "pbt";
	public static final String PARAM_C3I_LOCATION = "c3il";
	// public static final String PARAM_CALENDAR_PERIOD_DK = "cpdk";
	public static final String PARAM_CHANGE_PASSWORD = "cp";
	public static final String PARAM_COMMODITY = "cmmd";
	public static final String PARAM_COOKIE_VALIDATE = "cv";
	// public static final String PARAM_DATE_PERIOD = "fdk";
	public static final String PARAM_ENTERPRISE_SUPPLIER_NAME = "esname";
	public static final String PARAM_ENTERPRISE_SUPPLIER_NUM = "esn";
	public static final String PARAM_FEED_CODE_DK = "fcdk";
	public static final String PARAM_HIDDEN_BUTTON = "hbutton";
	public static final String PARAM_JAVASCRIPT_TEST = "jst";
	public static final String PARAM_LEGACY_SUPPLIER_NUM = "lsn";
	public static final String PARAM_LOCK_ID = "hlockid";
	// The value of the following param is hardcoded in .htxt files.
	// Please do not change its value.
	public static final String PARAM_LOGIN_USERID = "Uid";
	// The value of the following param is hardcoded in .htxt files.
	// Please do not change its value.
	public static final String PARAM_LOGIN_PASSWORD = "Upw";
	public static final String PARAM_ORIGINAL_SUPPLIER_DK = "osdk";
	public static final String PARAM_PAGE_DEST = "pd";
	// The value of the following param is hardcoded in .htxt files.
	// Please do not change its value.
	public static final String PARAM_PAGE_HOME = "ph";
	// The value of the following param is hardcoded in .htxt files.
	// Please do not change its value.
	public static final String PARAM_PAGE_SOURCE = "ps";
	public static final String PARAM_PASSWORD_FUNCTION = "pwf";
	public static final String PARAM_PN = "pn";
	// The value of the following param is hardcoded in .htxt files.
	// Please do not change its value.
	public static final String PARAM_PASSWORD_FLAG = "pwdflag";
	public static final String PARAM_PLANT_LOCATION_DK = "pldk";
	public static final String PARAM_BUSINESS_DK = "budk";

	// The value of the following param is hardcoded in .htxt files.
	// Please do not change its value.
	public static final String PARAM_REPORT_FLAG = "rptflag";
	// The value of the following param is hardcoded in .htxt files.
	// Please do not change its value.
	public static final String PARAM_RESET_PASSWORD_FLAG = "resetpasswordflag";
	public static final String PARAM_SUPPLIER_DK = "sdk";
	public static final String PARAM_SUPPLIER_NUM = "sn";
	public static final String PARAM_UCC_HEADER_DK = "uhdk";
	public static final String PARAM_UCC_HEADER_LIST = "ucchl";
	// The value of the following param is hardcoded in .htxt files.
	// Please do not change its value.
	public static final String PARAM_USER_CREATE_FLAG = "usercreateflag";
	// The value of the following param is hardcoded in .htxt files.
	// Please do not change its value.
	public static final String PARAM_USER_DELETE_FLAG = "userdeleteflag";
	// The value of the following param is hardcoded in .htxt files.
	// Please do not change its value.
	public static final String PARAM_USER_DISPLAY_FLAG = "userdispflag";
	// The value of the following param is hardcoded in .htxt files.
	// Please do not change its value.
	public static final String PARAM_USER_MOD_FLAG = "usermodflag";
	// The value of the following param is hardcoded in .htxt files.
	// Please do not change its value.
	public static final String PARAM_USER_MOD_ROLE_FLAG = "usermodroleflag";
	public static final String PARAM_WEB_ESD = "esd";
	public static final String PARAM_WEB_ESDK = "esdk";
	public static final String PARAM_WINDOWPOPUP = "wp";
	public static final String PARAM_WINDOWPOPUP_LINKRETURN = "wpr";
	public static final String PARAM_DATE = "dateparam";
	public static final String PARAM_EMPID = "empId";
	public static final String PARAM_SCORECARD_DK = "scdk";
	public static final String PARAM_SCORING_DK = "sdk";
	public static final String PARAM_BUSINESS_CODE = "bc";
	public static final String PARAM_MAJOR_CRIT_ID = "mci";
	public static final String PARAM_SUB_CRIT_ID = "sci";
	public static final String PARAM_ROW_NUM = "rn";
	public static final String PARAM_SITE_LIST = "lstsite";
	public static final int PAGE_QDI_PROGRAM_REPORT = 109;
	public static final String PARAM_PROGRAM_REPORT_TEMPLATE = "prt";
	// SRS Access Control Constants
	public static final int ACE_DOMESTIC = 1;
	public static final int ACE_ASL = 2;
	public static final int ACE_DSL = 4;
	public static final int ACE_CCSL = 8;
	public static final int ACE_COSL = 8;
	public static final int ACE_COMMENTS_EDIT = 16;
	public static final int ACE_COMMENTS_VIEW = 32;
	public static final int ACE_CRSL = 64;
	public static final int ACE_BIDED = 128;
	public static final int ACE_SYSADM = 256;
	public static final int ACE_NEWSAD = 512;
	public static final int ACE_QDI = 1024;

	// SRS Bid Eval Supplier Constants
	public static final int MAX_BID_EVAL_SUP = 20;

	// Tab Constants
	public static final int TAB_SRS_HOME = 1;
	public static final int TAB_SRS_TRAIN = 2;

	public static final String SPRR_LINK = "/srsrc/rc/srsrcSelectReportType";

	// User Access Level Constants

	// Web Page Contact Info Constants
	// public static final String WEB_PAGE_CONTACT_NAME = "Cindee M. Cognetta";
	// public static final String WEB_PAGE_CONTACT_LOCATION = "Raytheon Company";
	// public static final String WEB_PAGE_CONTACT_EMAIL =
	// "Cindee_M_Cognetta@res.raytheon.com";
	public static String WEB_PAGE_CONTACT_NAME = "Michael Dalton";
	public static String WEB_PAGE_CONTACT_LOCATION = "Raytheon Company";
	public static String WEB_PAGE_CONTACT_EMAIL = "Michael_G_Dalton@raytheon.com";
	public static String WEB_SITE_CONTACT_INFO = "http://srs.app.ray.com:8080/srs/dl/srs_site_contacts.xls";

	// Shared Variables
	/*
	 * Public libUserId Public libUserSystemAdmin Public libUserQuality_Access
	 * Public libUserSupplyChain_Access Public libUserExecutive_Access Public
	 * libUserACL
	 */

	public static String getServletForwarding(int PageDest)
	{
		String URL = "";
		switch (PageDest)
		{
			case PAGE_QDI_PROGRAM_REPORT:
				URL = "/srsqdiProgramReport";
				break;
			case PAGE_ASL:
				URL = "/srsASL";
				break;
			case PAGE_CCSL:
				URL = "/srsCCSL";
				break;
			case PAGE_DSL:
				URL = "/srsDSL";
				break;
			case PAGE_HOME:
				URL = FORWARD_JSP_ROOT + "srsMain.jsp";
				break;
			case PAGE_LOGIN:
				URL = "/srsLogin";
				break;
			case PAGE_QDI_PROGRAM_SUMMARY_REPORT:
	    	URL = "/srsqdiProgramSumRpts";
	    	break;
			case PAGE_NEWS_ADMIN:
				URL = "/news/newsAdmin";
				break;
			case PAGE_SAS_INFO:
				URL = FORWARD_JSP_ROOT + "srsInfo.jsp";
				break;
			case PAGE_SAS_MAINT:
				URL = FORWARD_JSP_ROOT + "srsMaint.jsp";
				break;
			case PAGE_TRAINING:
				URL = "/srsTraining";
				break;
			case PAGE_USER_PWD:
				URL = "/srsID_Pwd";
				break;
			case PAGE_JAVASC_WARN:
				URL = FORWARD_JSP_ROOT + "logjsfail.htm";
				break;
			case PAGE_LEGACY_SUPPLIER_SEARCH:
				URL = "/srsLgcySuplSrch";
				break;
			case PAGE_ENTERPRISE_SUPPLIER_SEARCH:
				URL = "/srsEntprsSuplSrch";
				break;
			case PAGE_COMMENTS:
				URL = "/srsComments";
				break;
			case PAGE_NEW_USER:
				URL = "/srsNewUser";
				break;
			case PAGE_PASSWORD:
				URL = "/srsPassword";
				break;
			case PAGE_BID_EVAL_SITE:
				URL = "/srsBidEvalSite";
				break;
			case PAGE_BID_EVAL_SEARCH:
				URL = "/srsBidEvalSearch";
				break;
			case PAGE_BID_EVAL_OWNERS:
				URL = "/srsBidEvalOwners";
				break;
			case PAGE_BID_EVAL_SELECT:
				URL = "/srsBidEvalSelect";
				break;
			case PAGE_BID_EVAL_SUPPLIER:
				URL = "/srsBidEvalSupplier";
				break;
			case PAGE_BID_EVAL_PART_SELECT:
				URL = "/srsBidEvalPartSelect";
				break;
			case PAGE_BID_EVAL_UCC_SELECT:
				URL = "/srsBidEvalUccSelect";
				break;
			case PAGE_BID_EVAL_SUPPLIER_SELECT:
				URL = "/srsBidEvalSupplierSelect";
				break;
			case PAGE_ADD_BID_EVAL:
				URL = "/srsNewBidEval";
				break;
			case PAGE_EDIT_BID_EVAL:
				URL = "/srsEditBidEval";
				break;
			case PAGE_USER_SRCH:
				URL = "/srsUserSrch";
				break;
			// BK_ADDED_BY
			case PAGE_ADD_USER_ROLES:
				URL = "/srsAddUserRoles";
				break;
			// BK_ADDED_BY
			case PAGE_DEL_USER_ROLES:
				URL = "/srsDeletUserRoles";
				break;
			case PAGE_MOD_USER_ROLES:
				URL = "/srsModifyUserRoles";
				break;
			case PAGE_CHANGE_PW:
				URL = "/srsChangePassword";
				break;
			case PAGE_VIEW_USER_INFO:
				URL = "/srsChangePassword";
				break;
			case PAGE_MOD_USER_INFO:
				URL = "/srsModifyUserInfo";
				break;
			case PAGE_NEWS_ADM:
				URL = "/srsNewsAdmin";
				break;
			case PAGE_LOGOUT:
				URL = "/srsLogout";
				break;
			case PAGE_QDI_HOME:
				URL = "/srsqdiHome";
				break;
			case PAGE_QDI_NEW_EVAL:
				URL = "/srsqdiHome";
				break;
			case PAGE_QDI_EDIT:
				URL = "/srsqdiEdit";
				break;
			case PAGE_QDI_LEAD_HOME:
				URL = "/srsqdiLeadHome";
				break;
			case PAGE_QDI_SUPPLIER_SRCH:
				URL = "/srsqdiSupplierSrch";
				break;
			case PAGE_QDI_MESSAGE:
				URL = "/srsqdiMessage";
				break;
			case PAGE_QDI_MOD_WEIGHTS:
				URL = "/srsqdiModifyWeights";
				break;
			case PAGE_QDI_MOD_WEIGHTS_DISP:
				URL = "/srsqdiModifyWeightsDisp";
				break;
			case PAGE_QDI_SUPPLIER_SRCH_CRIT:
				URL = "/srsqdiSupplierSrchCrit";
				break;
			case PAGE_QDI_PROGRAM_SRCH_CRIT:
				URL = "/srsqdiProgramSrchCrit";
				break;
			case PAGE_QDI_PO_SRCH_CRIT:
				URL = "/srsqdiPOSrchCrit";
				break;
			case PAGE_QDI_SUPPLIER_SRCH_SUITE:
				URL = "/srsqdiSupplierSrchSuite";
				break;
			case PAGE_QDI_SUPPLIER_SRCH_RESULTS:
				URL = "/srsqdiSupplierSrchResults";
				break;
			case PAGE_QDI_PROGRAM_SRCH_RESULTS:
				URL = "/srsqdiProgramSrchResults";
				break;
			case PAGE_QDI_PO_SRCH_RESULTS:
				URL = "/srsqdiPOSrchResults";
				break;
			case PAGE_QDI_PROCESS_EVAL:
				URL = "/srsqdiProcessEval";
				break;
			case PAGE_QDI_EDIT_SCORECARD:
				URL = "/srsqdiScoreCard";
				break;
			case PAGE_QDI_SCORECARD_SPRR_INT:
				URL = "/srsqdiScoreCardSprrInt";
				break;
			case PAGE_QDI_ASSIGN_LEAD:
				URL = "/srsqdiLeadEvalMaint";
				break;
			case PAGE_QDI_EDIT_LEAD_EVAL:
				URL = "/srsqdiLeadEvalMaint";
				break;
			case PAGE_QDI_USER_MAINT:
				URL = "/srsqdiEvalTeamMaint";
				break;
			case PAGE_QDI_BUSINESS:
				URL = "/srsqdiSelectBusiness";
				break;
			case PAGE_QDI_STATUS:
				URL = "/srsqdiEvalStatus";
				break;
			case PAGE_QDI_SEL_BUSINESSES:
				URL = "/srsqdiSelBusinesses";
				break;
			case PAGE_QDI_SEL_SITES:
				URL = "/srsqdiSelSites";
				break;
			case PAGE_QDI_IS_EVALUATOR:
				URL = "/srsqdiIsEvaluator";
				break;
			case PAGE_QDI_SUPPLIER_EVALS:
				URL = "/srsqdiSupplierEvals";
				break;
			case PAGE_QDI_SUPPLIER_EVALS_SPRR_INT:
				URL = "/srsqdiSupplierEvalsSprrInt";
				break;
			case PAGE_QDI_GLOBAL_STATUS:
				URL = "/srsqdiGlobalStatus";
				break;
			case PAGE_QDI_ROADMAP:
				URL = "/srsqdiRoadmap";
				break;
			case PAGE_QDI_COMMENTS:
				URL = "/srsqdiComments";
				break;
			case PAGE_PROGRAM_SEARCH:
				URL = "/srsqdiProgramSrch";
				break;
			case PAGE_BUSINESS_UPDATE:
				URL = "/srsqdiBusinessUpdate";
				break;
			case PAGE_PROGRAM_SELECT:
				URL = FORWARD_JSP_ROOT + "srsqdiProgramSelection.jsp";
				break;
			case PAGE_USER_AUTHENTICATION:
				URL = "/srsNewUser";
				break;
			case PAGE_ADD_SUPPLIER:
				URL = "/srsLgcySuplAdd";
				break;
			default:
				URL = FORWARD_JSP_ROOT + "srsMain.jsp";
				break;
		}
		return URL;
	}

	public static String getServletRedirect(int PageDest)
	{
		String URL = "";
		switch (PageDest)
		{
			case PAGE_QDI_PROGRAM_SUMMARY_REPORT:
	    	URL = SITE_ROOT + "srsqdiProgramSumRpts";
	    	break;
			case PAGE_QDI_PROGRAM_REPORT:
				URL = SITE_ROOT + "srsqdiProgramReport";
				break;
			case PAGE_ASL:
				URL = SITE_ROOT + "srsASL";
				break;
			case PAGE_CCSL:
				URL = SITE_ROOT + "srsCCSL";
				break;
			case PAGE_DSL:
				URL = SITE_ROOT + "srsDSL";
				break;
			case PAGE_HOME:
				URL = JSP_ROOT + "srsMain.jsp";
				break;
			case PAGE_LOGIN:
				URL = SITE_ROOT + "srsLogin";
				break;
			case PAGE_NEWS_ADMIN:
				URL = SITE_ROOT + "news/newsAdmin";
				break;
			case PAGE_SAS_INFO:
				URL = JSP_ROOT + "srsInfo.jsp";
				break;
			case PAGE_SAS_MAINT:
				URL = JSP_ROOT + "srsMaint.jsp";
				break;
			case PAGE_TRAINING:
				URL = SITE_ROOT + "srsTraining";
				break;
			case PAGE_USER_PWD:
				URL = SITE_ROOT + "srsID_Pwd";
				break;
			case PAGE_JAVASC_WARN:
				URL = JSP_ROOT + "logjsfail.htm";
				break;
			case PAGE_LEGACY_SUPPLIER_SEARCH:
				URL = SITE_ROOT + "srsLgcySuplSrch";
				break;
			case PAGE_ENTERPRISE_SUPPLIER_SEARCH:
				URL = SITE_ROOT + "srsEntprsSuplSrch";
				break;
			case PAGE_COMMENTS:
				URL = SITE_ROOT + "srsComments";
				break;
			case PAGE_NEW_USER:
				URL = SITE_ROOT + "srsNewUser";
				break;
			case PAGE_PASSWORD:
				URL = SITE_ROOT + "srsPassword";
				break;
			case PAGE_BID_EVAL_SITE:
				URL = SITE_ROOT + "srsBidEvalSite";
				break;
			case PAGE_BID_EVAL_SEARCH:
				URL = SITE_ROOT + "srsBidEvalSearch";
				break;
			case PAGE_BID_EVAL_OWNERS:
				URL = SITE_ROOT + "srsBidEvalOwners";
				break;
			case PAGE_BID_EVAL_SELECT:
				URL = SITE_ROOT + "srsBidEvalSelect";
				break;
			case PAGE_BID_EVAL_SUPPLIER:
				URL = SITE_ROOT + "srsBidEvalSupplier";
				break;
			case PAGE_BID_EVAL_PART_SELECT:
				URL = SITE_ROOT + "srsBidEvalPartSelect";
				break;
			case PAGE_BID_EVAL_UCC_SELECT:
				URL = SITE_ROOT + "srsBidEvalUccSelect";
				break;
			case PAGE_BID_EVAL_SUPPLIER_SELECT:
				URL = SITE_ROOT + "srsBidEvalSupplierSelect";
				break;
			case PAGE_ADD_BID_EVAL:
				URL = SITE_ROOT + "srsNewBidEval";
				break;
			case PAGE_EDIT_BID_EVAL:
				URL = SITE_ROOT + "srsEditBidEval";
				break;
			case PAGE_USER_SRCH:
				URL = SITE_ROOT + "srsUserSrch";
				break;
			// BK_ADDED_BY
			case PAGE_ADD_USER_ROLES:
				URL = SITE_ROOT + "srsAddUserRoles";
				break;
			// BK_ADDED_BY
			case PAGE_DEL_USER_ROLES:
				URL = SITE_ROOT + "srsDeleteUserRoles";
				break;
			case PAGE_MOD_USER_ROLES:
				URL = SITE_ROOT + "srsModifyUserRoles";
				break;
			case PAGE_CHANGE_PW:
				URL = SITE_ROOT + "srsChangePassword";
				break;
			case PAGE_VIEW_USER_INFO:
				URL = SITE_ROOT + "srsChangePassword";
				break;
			case PAGE_MOD_USER_INFO:
				URL = SITE_ROOT + "srsModifyUserInfo";
				break;
			case PAGE_NEWS_ADM:
				URL = SITE_ROOT + "srsNewsAdmin";
				break;
			case PAGE_LOGOUT:
				URL = SITE_ROOT + "srsLogout";
				break;
			case PAGE_QDI_HOME:
				URL = SITE_ROOT + "srsqdiHome";
				break;
			case PAGE_QDI_NEW_EVAL:
				URL = SITE_ROOT + "srsqdiHome";
				break;
			case PAGE_QDI_EDIT:
				URL = SITE_ROOT + "srsqdiEdit";
				break;
			case PAGE_QDI_LEAD_HOME:
				URL = SITE_ROOT + "srsqdiLeadHome";
				break;
			case PAGE_QDI_SUPPLIER_SRCH:
				URL = SITE_ROOT + "srsqdiSupplierSrch";
				break;
			case PAGE_QDI_MESSAGE:
				URL = SITE_ROOT + "srsqdiMessage";
				break;
			case PAGE_QDI_MOD_WEIGHTS:
				URL = SITE_ROOT + "srsqdiModifyWeights";
				break;
			case PAGE_QDI_MOD_WEIGHTS_DISP:
				URL = SITE_ROOT + "srsqdiModifyWeightsDisp";
				break;
			case PAGE_QDI_SUPPLIER_SRCH_CRIT:
				URL = SITE_ROOT + "srsqdiSupplierSrchCrit";
				break;
			case PAGE_QDI_PROGRAM_SRCH_CRIT:
				URL = SITE_ROOT + "srsqdiProgramSrchCrit";
				break;
			case PAGE_QDI_PO_SRCH_CRIT:
				URL = SITE_ROOT + "srsqdiPOSrchCrit";
				break;
			case PAGE_QDI_SUPPLIER_SRCH_SUITE:
				URL = SITE_ROOT + "srsqdiSupplierSrchSuite";
				break;
			case PAGE_QDI_SUPPLIER_SRCH_RESULTS:
				URL = SITE_ROOT + "srsqdiSupplierSrchResults";
				break;
			case PAGE_QDI_PROGRAM_SRCH_RESULTS:
				URL = SITE_ROOT + "srsqdiProgramSrchResults";
				break;
			case PAGE_QDI_PO_SRCH_RESULTS:
				URL = SITE_ROOT + "srsqdiPOSrchResults";
				break;
			case PAGE_QDI_PROCESS_EVAL:
				URL = SITE_ROOT + "srsqdiProcessEval";
				break;
			case PAGE_QDI_EDIT_SCORECARD:
				URL = SITE_ROOT + "srsqdiScoreCard";
				break;
			case PAGE_QDI_SCORECARD_SPRR_INT:
				URL = SITE_ROOT + "srsqdiScoreCardSprrInt";
				break;
			case PAGE_QDI_ASSIGN_LEAD:
				URL = SITE_ROOT + "srsqdiLeadEvalMaint";
				break;
			case PAGE_QDI_EDIT_LEAD_EVAL:
				URL = SITE_ROOT + "srsqdiLeadEvalMaint";
				break;
			case PAGE_QDI_USER_MAINT:
				URL = SITE_ROOT + "srsqdiEvalTeamMaint";
				break;
			case PAGE_QDI_BUSINESS:
				URL = SITE_ROOT + "srsqdiSelectBusiness";
				break;
			case PAGE_QDI_STATUS:
				URL = SITE_ROOT + "srsqdiEvalStatus";
				break;
			case PAGE_QDI_SEL_BUSINESSES:
				URL = SITE_ROOT + "srsqdiSelBusinesses";
				break;
			case PAGE_QDI_SEL_SITES:
				URL = SITE_ROOT + "srsqdiSelSites";
				break;
			case PAGE_QDI_IS_EVALUATOR:
				URL = SITE_ROOT + "srsqdiIsEvaluator";
				break;
			case PAGE_QDI_SUPPLIER_EVALS:
				URL = SITE_ROOT + "srsqdiSupplierEvals";
				break;
			case PAGE_QDI_SUPPLIER_EVALS_SPRR_INT:
				URL = SITE_ROOT + "srsqdiSupplierEvalsSprrInt";
				break;
			case PAGE_QDI_GLOBAL_STATUS:
				URL = SITE_ROOT + "srsqdiGlobalStatus";
				break;
			case PAGE_QDI_ROADMAP:
				URL = SITE_ROOT + "srsqdiRoadmap";
				break;
			case PAGE_QDI_COMMENTS:
				URL = SITE_ROOT + "srsqdiComments";
				break;
			case PAGE_PROGRAM_SEARCH:
				URL = SITE_ROOT + "srsqdiProgramSrch";
				break;
			case PAGE_BUSINESS_UPDATE:
				URL = SITE_ROOT + "srsqdiBusinessUpdate";
				break;
			case PAGE_PROGRAM_SELECT:
				URL = JSP_ROOT + "srsqdiProgramSelection.jsp";
				break;
			case PAGE_USER_AUTHENTICATION:
				URL = SITE_ROOT + "srsNewUser";
				break;
			case PAGE_ADD_SUPPLIER:
				URL = SITE_ROOT + "srsLgcySuplAdd";
				break;
			default:
				URL = JSP_ROOT + "srsMain.jsp";
				break;
		}
		return URL;
	}

	public static void setRayWebTabs(RayPage oRayPage, int SelTabNum, boolean SelRollover)
	{
		/*
		 * ==================================* Setup Tab Objects
		 * ==================================
		 */
		NavTab NTab;
		NTab = oRayPage.getNavTabs().add();
		NTab.setHref("/srs/web/srsMain.jsp");
		NTab.setImageText("srs");
		NTab.setPageName("srs");
		NTab.setIsEnabled(true);
		NTab.setIsRollover(true);
		if (SelTabNum == TAB_SRS_HOME)
			NTab.setIsSelected(true);

		NTab = oRayPage.getNavTabs().add();
		NTab.setHref("/srs/web/srsTraining.jsp");
		NTab.setImageText("srs");
		NTab.setPageName("srs");
		NTab.setIsEnabled(true);
		NTab.setTabWidth(126);
		NTab.setIsRollover(true);
		if (SelTabNum == TAB_SRS_TRAIN)
			NTab.setIsSelected(true);
		/*
		 * NTab = oRayPage.getNavTabs().add();
		 * NTab.setHref("/srs/web/srsMaint.jsp"); NTab.setImageText("srs");
		 * NTab.setPageName("srs"); NTab.setIsEnabled(true);
		 * NTab.setIsRollover(true); if (SelTabNum==TAB_SRS_MAINT)
		 * NTab.setIsSelected(true);
		 * 
		 * NTab = oRayPage.getNavTabs().add();
		 * NTab.setHref("/srs/web/srsTraining.jsp"); NTab.setImageText("srs");
		 * NTab.setPageName("srs"); NTab.setIsEnabled(true);
		 * NTab.setIsRollover(true); if (SelTabNum==TAB_SRS_TRAIN)
		 * NTab.setIsSelected(true);
		 */
	}

	public static boolean isCurrentDate(String eDate)
	{
		boolean isCurrent = false;
		if (eDate != null)
		{
			if (!eDate.equals(""))
			{
				String dDate = Encryption.decryptString(eDate, DATE_ENCRYPT_KEY);
				String currentDate = getCurrentFormattedDate();
				if (dDate != null)
				{
					if (!dDate.equals(""))
					{
						if (currentDate != null)
						{
							if (dDate.equals(currentDate))
							{
								isCurrent = true;
							}
						}
					}
				}
			}
		}
		return isCurrent;
	}

	public static String getCurrentFormattedDate()
	{
		String dateString = "";
		try
		{
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
			dateString = dateFormat.format(date);
		}
		catch (Exception e)
		{
			dateString = "";
		}
		if (dateString == null)
		{
			dateString = "";
		}
		return dateString;
	}

	public static String getCurrentEncryptedDate()
	{
		String dateString = "";
		try
		{
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
			String tempDate = dateFormat.format(date);
			dateString = Encryption.encryptString(tempDate, DATE_ENCRYPT_KEY);
			if (dateString == null)
			{
				dateString = "";
			}
			return dateString;
		}
		catch (Exception e)
		{
			dateString = "";
			return dateString;
		}
	}

	public static boolean isUserLoggedIn(HttpServletRequest req, UserLib loginLib) throws GenericException
	{
		Cookie[] cookies = null;
		Cookie ck = null;
		boolean LoggedIn = false;

		try
		{
			cookies = req.getCookies();
			if (cookies != null)
			{
				for (int i = 0; i < cookies.length; i++)
				{
					ck = cookies[i];
					if (ck.getName().equals(COOKIE_USERID))
					{
						loginLib.setUserID(ck.getValue());
					}
					else if (ck.getName().equals(COOKIE_LOGIN))
					{
						try
						{
							loginLib.setLoggedIn(Integer.valueOf(ck.getValue()).intValue());
						}
						catch (NumberFormatException ne)
						{
							loginLib.setLoggedIn(0);
						}
						catch (Exception lie)
						{
							throw lie;
						}

					}
					else if (ck.getName().equals(COOKIE_ACL))
					{
						try
						{
							loginLib.setUserACL(Integer.valueOf(ck.getValue()).intValue());
						}
						catch (NumberFormatException ne)
						{
							loginLib.setUserACL(0);
						}
						catch (Exception lie)
						{
							throw lie;
						}

					}
					else if (ck.getName().equals(COOKIE_APPID))
					{
						loginLib.setCustomParameter(USERLIB_CUSTPARAM_APPID, ck.getValue());

					}
					else if (ck.getName().equals(COOKIE_LOCKID))
					{
						loginLib.setCustomParameter(USERLIB_CUSTPARAM_LOCKID, ck.getValue());

					}
					else if (ck.getName().equals(COOKIE_ENCPW))
					{
						loginLib.setCustomParameter(USERLIB_CUSTPARAM_ENCPW, ck.getValue());

					}
					else if (ck.getName().equals(COOKIE_DISPLAYNAME))
					{
						loginLib.setCustomParameter(USERLIB_CUSTPARAM_DISPLAYNAME, ck.getValue());

					}
				} // for...
			} // if(cookies)

			return (loginLib.getLoggedIn() == 1);

		}
		catch (Exception e)
		{
			throw new GenericException("[isUserLoggedIn] Error validating user info:  " + e.toString());
		}

	} // isUserLoggedIn

	public static boolean isUserPasswordExpired(HttpServletRequest req, UserLib loginLib) throws GenericException
	{

		Cookie[] cookies = null;
		Cookie ck = null;
		String expired = "";

		try
		{
			cookies = req.getCookies();
			if (cookies != null)
			{
				for (int i = 0; i < cookies.length; i++)
				{
					ck = cookies[i];
					if (ck.getName().equals(COOKIE_EXPIRED))
					{
						expired = ck.getValue();

					}
				} // for...
			} // if(cookies)

			if (expired.equals("true"))
			{
				return true;
			}
			else
			{
				return false;
			}

		}
		catch (Exception e)
		{
			throw new GenericException("[isUserPasswordExpired] Error validating user info:  " + e.toString());
		}

	} // isUserPasswordExpired

	public static void resetPwExpCookie(HttpServletRequest req, HttpServletResponse res)
	{
		Cookie ck = null;

		try
		{
			Cookie[] cookies = req.getCookies();
			if (cookies != null)
			{
				for (int i = 0; i < cookies.length; i++)
				{
					ck = cookies[i];
					if (ck.getName().equals(COOKIE_EXPIRED))
					{
						cookies[i].setValue("false");
						cookies[i].setPath("/");
						res.addCookie(cookies[i]);
					}
				}
			}
		}
		catch (Exception e)
		{
		}
	}

	public static boolean isUserAuthorized(int pDesiredAccess, UserLib loginLib)
	{
		// * Perform Bitwise comparison of ACL against Access
		return ((loginLib.getUserACL() & pDesiredAccess) == pDesiredAccess);
	}

	public static String getSiteURL()
	{
		//
		// As of 09292006, this method is used by external function only.
		// To prevent adding an extra properties file to the internal system,
		// the internal site url is obtained from srsLinkCognosDisp.getCognosURL().
		// mwang
		//
		if (mSiteURL == null)
		{
			ResourceBundle props = ResourceBundle.getBundle("site");
			String protocol = props.getString("protocol");
			String host = props.getString("host");
			String port = props.getString("port");

			StringBuffer siteURL = new StringBuffer(200);
			siteURL.append(protocol).append("://").append(host);
			if (!((port == null) || (port.length() == 0)))
				siteURL.append(":").append(port);

			mSiteURL = siteURL.toString();
		}

		return mSiteURL;

	}
}
