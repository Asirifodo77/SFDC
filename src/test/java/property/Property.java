package property;

public class Property {
    public static int TIMEOUT_IN_SECONDS =180;
    public static int TIMEOUT_IN_MILISECONDS=10800;
    public static String API_EXPECTED_SUCCESS_MESSAGE="SUCCESS";
    public static String API_CANCELLED_MEMBER_MESSAGE = "CANCELLED MEMBER";
    public static String API_INACTIVE_CARD_MESSAGE = "Inactive card.";
    public static String API_CARDNO_NOT_FOUND_MESSAGE = "CARDNO NOT FOUND";
    public static String SCREENSHOT_IMAGE_TYPE="image/png";
    public static String TESTDATA_FILE_PATH="src//test//resources//test_data//Test_data";
    public static String BRANDMAPPING_FILE_PATH="src//test//resources//test_data//BrandMapping";
    public static int MAXIMUM_TIMEOUT_COUNT = 5;
    public static int MAXIMUM_TIMEOUT_COUNT_STAGGINGTABLE = 20;
    public static int RETRY_COUNT_FOR_GETTING_MEMBERID_FROM_SF_AND_MATRIX=30;
    public static int RETRY_COUNT_FOR_GETTING_CARD_RANGE_NAME_FROM_SF=10;
    public static int RETRY_COUNT_FOR_GETTING_CYCLE_END_DATE_FROM_MATRIX=10;
    //public static String CREATE_COBRAND_MEMBER_JSONFILE_PATH = "src//test//resources//test_data//Cobrand//CreateCobrandMember.json";
    public static String EXPECTED_COBRAND_CREATE_MEMBER_RETURN_MESSAGE = "Success";
    public static String EXPECTED_COBRAND_MOVEMENT_TYPE_SF_VALUE="NONPURCHASE";
    public static String EXPECTED_COBRAND_MOVEMENT_TYPE_MATRIX_VALUE="NON-PURCHASE";
    public static String EXPECTED_COBRAND_REMARKS_VALUE="New Cobrand with no purchase";
    public static double EXCHANGE_RATE_JPY_PREPROD=111.85;
    public static double EXCHANGE_RATE_JPY_QACORE2=111.85;
    public static String EXCEL_TEST_DATA_FILE_PATH="src//test//resources//test_data//";
    public static String EXCEL_FILE_EXTENSION=".xlsx";
    public static String EXPECTED_CUSTOMLOG_RETURN_MESSAGE_FOR_BLACKLISTED_MEMBER="BLACKLISTED MEMBER";
    public static String EXPECTED_CUSTOMLOG_RETURN_MESSAGE_FOR_CANCELLED_MEMBER="CANCELLED MEMBER";
    public static String ERROR_CLASS_FOR_API_CALLS="CRM_REST_CreateMember";
    public static String ERROR_CLASS_FOR_API_WHEN_ATP_FAILS="CRM_ProcessTransactions";
    public static String INVALID_CARD_RANGE_MAXIMUM_VALUE="5200 0051 4928";
    public static String INVALID_CARD_RANGE_MINIMUM_VALUE="5200 0051 4926";
    public static String INVALID_CARD_RANGE_STORE_LOCATION="OKINAWA AIRPORT";
    public static String INVALID_CARD_NUMBER="520000514927";

}
