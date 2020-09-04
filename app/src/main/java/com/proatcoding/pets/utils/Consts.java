package com.proatcoding.pets.utils;

/**
 * Created by User on .
 */
public interface Consts {

    String PET_CARE = "PetStand";
    String BASE_URL = "http://pet-platform.com/Admin/WebService/";
    String BASE_PAYMENT = "http://paymentgw.el3zoma.com/api/WeAccept/PlaceOrder.php/";
    String PAYTM_CHECKSUM = "http://139.59.88.66/Paytm/generateChecksum.php"; //Old url http://139.59.45.232/Paytm/generateChecksum.php
    String PAYTM_CALLBACK_URL = "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID="; //https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=
    String PAYTM_TRANS_STATUS_URL = "http://139.59.93.133/Paytm/trans_status.php?order_id=";
    String PAYTM_MID = "peteat25308631392900"; //old VqOOGn90907310937924

    ///
    /*API DETAILS*/
    String SEND_OTP = "sendOTP";
    String LOGIN_MOBILE_NO = "loginByMobileNo";
    String SIGNUP_BY_MOBILE_NO = "signUpByMobileNo";
    String HOMEDATA = "homedata";
    String GETALLPETTYPE = "getallpettype";
    String GETALLPRODUCT = "getAllProduct";
    String GET_ALL_PRODUCT_BY_CAT_NAME = "getAllProductByCatName";
    String UPDATEPROFILEUSER = "updateProfileUser";
    String ADDPET = "add_pet";
    String UPDATE_PET = "update_pet";
    String GET_REMINDERS = "get_reminders";
    String CONFIRM_REMINDER = "change_status";
    String HISTORY = "reminder_history";
    String GET_MY_PET = "getMyPet";
    String CREATE_REMINDER = "create_reminder";
    String GET_NEAR_BY_USER = "getNearByUser";
    String GET_PET_BY_USER = "getPetByUser";
    String GETALLBREEDBYTYPE = "getAllBreedByType";
    String GETMYPET = "getMyPet";
    String UPDATE__PRIVATE = "updatePrivate";
    String UPDATE_SALE_NOTSALE = "updateSaleNotSale";
    String ADD_TO_CART = "addTocart";
    String ADD_TO_CART_SHOPIFY = "addTocartShopify";
    String GET_MY_CART = "getMyCart";
    String GET_MY_CART_SHOPIFY = "getMyCartShopify";
    String REMOVE_PRODUCT_CART = "remove_product_cart";
    String UPDATE_CART_QUANTITY = "updateCartQuantity";
    String CHECK_PRODUCT = "checkProduct";
    String ORDER = "order";
    String GET_MEMORIES = "getMemories";
    String ADD_MEMORIES = "addMemories";
    String ADD_CONTACT = "addContact";
    String SET_MANUAL_ACTIVITY = "set_manual_activity";
    String GET_SINGLE_PET = "getSinglePet";
    String GET_ALL_PETS = "getAllPets";
    String GET_MY_ORDER = "getMyOrder";
    String GET_MY_ORDER_DETAILS = "getMyOrderDetails";
    String GET_FILTER_ITEM = "getFilterItem";
    String GET_MARKET_FILTER_ITEM = "getMarketFilterItem";
    String FILTER = "Filter";
    String GET_POST_API = "get_post";
    String GET_MY_POST_API = "get_my_post";
    String LIKE_API = "like";
    String DISLIKE_API = "dislike";
    String GET_COMMENTS_API = "get_comments";
    String COMMENT_API = "comment";
    String ABUSE_POST_API = "abuse_post";
    String DELETE_POST_API = "delete_post";
    String DELETE_PET_API = "deletePet";
    String VIEW_PET_PROFILE = "viewPetProfile";
    String ADD_POST_API = "add_post";
    String EDIT_POST_API = "edit_post";
    String RATING_API = "rating";
    String REVIEW_PRODUCT = "reviewProduct";
    String JOIN_COMUNITY = "joincomunity";
    String GET_ALL_RATING = "getAllRating";
    String IMAGE_UPLOAD_FOR_LIST = "image_upload_for_list";
    String GET_CHAT_API = "getChat";
    String SEND_CHAT_API = "sendmsg";
    String GET_CHAT_HISTORY_API = "getChatHistory";
    String GETCONTACTREQUEST = "getContactRequest";
    String GETNOTIFICATTION = "getNotifications";

    String GETALLCAT = "getAllCat";
    String GET_ALL_BRAND = "getAllbrand";
    String GET_MY_REQUEST = "getMyRequest";
    String ADD_EVENT = "add_event";
    String EDIT_EVENT = "edit_event";
    String GETMYEVENT = "getMyEvent";
    String GETALLEVENT = "getAllEvent";
    String JOIN_EVENT = "joinEvent";
    String REMOVE_USER = "removeUser";
    String JOIN_EVENT_USER_LIST = "joinEventUserList";
    String DELETE_EVENT = "delete_event";
    String EDIT_WALL_COMMENT = "editWallComment";
    String DELETE_WALL_COMMENT = "deleteWallComment";
    String GET_MY_PET_VIEWER = "getMyPetViewer";
    String GET_LIKED_USER = "getlikedUser";
    String GET_MY_PET_FOLLOWERS = "getMyPetFollowers";
    String GET_SINGLE_PRODUCTS = "getSingleProducts";

    String ADD_PET_MARKET = "addPetMarket";
    String EDIT_PET_MARKET = "editPetMarket";
    String GET_PET_MARKET = "getPetMarket";
    String PET_MARKET_FILTER = "petMarketFilter";
    String GET_MY_PETS_MARKET = "getMyPetsMarket";
    String ADD_FAV_PET = "addFavPet";
    String REMOVE_FAV_PET = "removeFavPet";
    String ADD_COMMENT_PET = "addCommentPet";
    String GET_COMMENT_PET = "getCommentPet";
    String UPDATE_ADOPT = "updateAdopt";
    String FOLLOW_PET_PROFILE = "followPetProfile";
    String ADD_PET_MEDIA = "addPetMedia";
    String DELETE_MEDIA = "deleteMedia";
    String DELETE_PET_FROM_MARKET = "deletePetFromMarket";
    String GET_MY_SHOPIFY_ORDER = "getMyShopifyOrder";
    String REMOVE_COMMENT_PET = "removeCommentPet";
    String EDIT_COMMENT = "editComment";
    String GET_ORDER_STATUS = "get_order_status";
    String SIGN_UP = "signUp";
    String LOGIN = "login";
    String FORGOTPASSWORD = "forgotPassword";
    String CHANGE_PASSWORD = "changePassword";
    String GET_ALL_NEAR_BY_VSS = "getAllNearByVss";
    String ADDRESS_FILL1 = "addressFill1";
    String SEARCH = "search";
    String GET_CURRENT_VERSION = "currentversion";
    String GET_PROMO_CODE = "getPromoCode";
    String CHECK_PROMO_CODE = "CheckPromoCode";

    String CREATE_ORDERS = "http://phpstack-225750-688566.cloudwaysapps.com/store/create_orders.php";
    String GET_PRODUCTS = "http://phpstack-225750-688566.cloudwaysapps.com/store/get_products.php";
    String GET_ORDER = "http://phpstack-225750-688566.cloudwaysapps.com/store/get_orders.php";
    String GET_ORDER_ = "http://phpstack-225750-688566.cloudwaysapps.com/store/get_orders.php";

    /*API DETAILS*
    /*Project imp*/
    String LOGINDTO = "logindto";
    String USER_PROFILE = "user_profile";
    String PET_PROFILE = "pet_profile";
    String PRODUCT_DETAILS = "product_details";
    String MAKE_ORDER = "make_order";
    String P_PET_TYPE = "p_pet_type";
    String CAT_NAME = "cat_name";
    String P_TYPE = "p_type";
    String P_PET_TYPE_NAME = "p_pet_type_name";
    String P_SUB_PET_TYPE = "p_sub_pet_type";
    String EXTRA_SPACE_PHOTO = "extra_space_photo";
    String FINAL_PRICE = "final_price";
    String RATING = "rating";
    String PAYAMENT_URL = "payamenturl";
    String EVENT_DTO = "eventdto";
    String PET_DTO = "petdto";
    String GET_POST_BY_ID = "getPostById";
    String PUBLIC_WALL_DTO = "public_wall_dto";
    String PUBLIC_PRIVATE = "public_private";
    String BRAND_ID = "brand_id";
    String BRAND_NAME = "brand_name";
    String C_ID = "c_id";
    String P_ID = "p_id";
    String DTO = "dto";
    String CART = "cart";
    /*Project imp*/

    /*Cart ADD UPDATE REMOVE*/
    String PRODUCT_ID = "product_id";
    String QUANTITY = "quantity";
    String PRODUCT_NAME = "product_name";
    String PRODUCT_IMG = "product_img";
    String PRODUCT_DESC = "product_desc";
    String FIRST_NAME = "first_name";
    String LAST_NAME = "last_name";
    String COLOR = "color";
    String SIZE = "size";
    String SHIPPING_COST = "shipping_cost";
    String MANDATORY = "mandatory";
    String WEIGHT = "weight";

    // String USER_ID = "user_id";
    /*Cart ADD UPDATE REMOVE*/

    /*update  pet and sale pet*/
    String SEL_NOTSEL = "sel_notsel";
    String _PRIVATE = "_private";

    /*update  pet and sale pet*/

    /*Appointment Status*/
    String REMINDERS_CONFIRM = "id";
    String CONFIRM_STATUS = "status";
    String CURRENCYTYPE = "currency_type";

    /*Appointment Status*/
    String COMUNITY_ID = "comunity_id";


    /*Appointment History*/
    //user id send
    /*Appointment History*/

    /*Appointment Get*/
    //user id send
    /*Appointment Get*/
    /*Get Near By User */
    String LATI = "lati";
    String LONGI = "longi";
    String TYPE_ID = "type_id";
    String ADOPT = "adopt";
    /*Get Near By User */

    /*Get My Order */
    String ORDER_ID = "order_id";
    String COMMENT_ID = "comment_id";
    String MEDIA_ID = "media_id";

    String PROMO_CODE_ID = "promo_code_id";
    String PROMO_CODE_TYPE = "promo_code_type";
    String FIGURE = "figure";
    String DISCOUNT = "discount";

    /*payment_mode:1
    promo_code_id:1
    promo_code_type:1
    figure:30
    promo_code:STEAL30
    final_price:1000
    discount:30*/

    /*Get My Order */

    /* Create Reminders*/
    String REMINDER_CATEGORY = "category";
    String REMINDER_DATE = "date";
    String REMINDERS_TIMEZONE = "timezone";
    String REMINDERS_REMIND = "advance";//value in min
    String REMINDERS_REPEAT = "repeat";//value in days
    String REMINDERS_REMARK = "remark";
    String REMINDERS_PETID = "pet_id";
    /* Create Reminders*/

    /*LOGIN DETAILS*/
    String MOBILE_NO = "mobile_no";
    String DEVICE_ID = "device_id";
    String OTP = "otp";
    String OS_TYPE = "os_type";
    String DEVICE_TOKAN = "device_tokan";
    String COUNTRY_CODE = "country_code";
    String PASSWORD = "password";
    String NEW_PASSWORD = "new_password";
    /*LOGIN DETAILS*/

    /*UPDATE PROFILE USER DETAILS*/
    String USER_ID = "user_id";
    String FIRSTNAME = "first_name";
    String LASTNAME = "last_name";
    String EMAIL = "email";
    String SPECIAL_NOTE = "special_note";
    String ADDRESS = "address";
    String ADDRESS1 = "address1";
    String USER_PROFILE_PIC = "profile_pic";
    String CITY = "city";
    String STATE = "state";
    String POSTCODE = "postcode";
    String COUNTRY = "country";
    String LANDMARK = "landMark";
    String NAME = "name";
    String COMMNET = "comment";
    String ZIP = "zip";
    String PRODUCTS = "products";
    String PAYMENT_ID = "payment_id";
    String P_NAME = "p_name";

    /*UPDATE PROFILE USER DETAILS*/

    /*Add Pet*/
    String PET_NAME = "petName";
    String BREEDID = "breed_id";
    String CURRENT_WEIGHT = "current_weight";
    String CURRENT_HEIGHT = "current_height";

    String ACTIVE_AREA = "active_area";
    String PETTYPE = "pet_type";
    String LIFESTYL = "lifestyle";
    String NEUTERED = "neutered";
    String TRAIND = "trained";
    String BIRTH_DATE = "birth_date";
    String ADOPTION_DATE = "adoption_date";
    String MEDICINES = "medicines";
    String PET_IMG_PATH = "pet_img_path";

    /*Add Pet*/


    String POOCH_PLAY = "PetStand";


    /*Food List data*/

    /*Food List data*/

    /*Pet Category*/
    String PET_ID = "pet_id";
    String FOLLOWER_USER_ID = "follower_user_id";
    /*Pet Category*/
    /*Pet Memories*/
    String DESCRIPTION = "description";

    /*Pet Memories*/
    /*Pet Contact*/
    String USER_ID_OWNER = "user_id_owner";
    /*Pet Contact*/

    /*Update Profile*/

    /*Update Profile*/
    String TIME_STAMP = "time_stamp";
    String MANUAL_ACTIVITY = "manualActivity";
    String UNIT = "unit";
    String TARGET = "target";
    /*set_manual_activity*/

    /*set_manual_activity*/


    String POOCH_PLAY_TRACKER_DATA = "trackerData";
    String DATABASE = "DB";

    // Breed Request Params
    String SEX = "sex";
    String PET_TYPE = "pet_type";

    String ADD_PET = "addpetver1";

    String UPDATE_USER_PROFILE = "editprofile";

    long one = 2592000000L;

    String CROP_4_3 = "4:3";

    String TERMS_TITLE = "Terms and Condition";
    String PRIVATE_TITLE = "Privacy Policy";
    String PRIVATE_URL = "http://phpstack-132936-1057235.cloudwaysapps.com/Admin/Admin/policy";
    String TERMS_URL = "http://phpstack-132936-1057235.cloudwaysapps.com/Admin/Admin/TermsAndCondition";

    String IMAGE_URI_CAMERA = "image_uri_camera";

    String CONTACT_URL = "http://phpstack-132936-1057235.cloudwaysapps.com/Admin/Admin/contactUs";
    String CONTACT_TITLE = "Contact US";
    String ABOUT_URL = "http://phpstack-132936-1057235.cloudwaysapps.com/Admin/Admin/aboutUs";
    String ABOUT_TITLE = "About US";

    String FAQ_URL = "http://samyotech.com/";
    String FAQ_TITLE = "FAQ";

    String REFUND_URL = "http://phpstack-132936-1057235.cloudwaysapps.com/Admin/Admin/refundpolicy";
    String REFUND_TITLE = "Refund Policy";

    String REPORT_URL = "http://samyotech.com/";
    String REPORT_TITLE = "Report a bug";

    //    String SHARE_TEXT = "Hi,\n\nI have been using PetStand and I thought that you’d like it too.\n\nPetStand app is available for free to download and use - \n• PetStand app tracks your dog’s physical activity (even without a tracker!)\n• It monitors and manages your dog’s weight\n• Based on your dog’s age, weight, conditions, activity levels and the brand of food it already eats, PetStand recommends how much food they need every day.\n• It learns and notifies you about your dog’s daily, periodic, vaccination and de- worming schedules.\n\nThank you!";
    String SERVER_ERROR = "Opps! It seems that server not responding. Please try after sometime.";
    String SHARE_TEXT = "Hi,\n\nI have been using PetStand and I thought that you’d like it too.\n\nPetStand app is available for free to download and use - \n• PetStand is the best online pet store which provides best products and services.\n• You can find near by veterinarian, grooming, hostels & trainers.\n• This app provides pet's wall(public) in which user can join pet community with other pet lovers.\n• You can set reminders for your pet it can be categorized in app.\n\nYou can download app. using this link- \n\nhttps://play.google.com/store/apps/details?id=com.samyotech.petstand\n\nThank you!!";

    //Reminders Functions


    //Edit Reminder
    String REMINDER_APP_ID = "app_id";

    //get all reminder in history


    //Delete Pet
    String DELETE_PET = "deletepetver1";
    String DELETE_PET_ID = "petid";
    String DELETE_VALUE = "deleted";

    /*Filter*/
    String NEAR_BY = "near_by";
    String BY_BREED = "breed_id";
    String BY_GENDER = "sex";
    String BY_COUNTRY = "country";
    String BY_STATE = "state";
    String BY_CITY = "city";
    String BY_PETTYPE = "pet_type";
    String BY_TYPE_PET = "type_id";
    String CAT_ID = "cat_id";
    String SUB_CAT_ID = "sub_cat_id";
    /*Filter*/


    String AUTHORIZATION = "Authorization";
    String FLAG = "flag";
    String CAMERA_ACCEPTED = "camera_accepted";
    String STORAGE_ACCEPTED = "storage_accepted";
    String MODIFY_AUDIO_ACCEPTED = "modify_audio_accepted";
    String NETWORK_ACCEPTED = "modify_audio_accepted";
    String FINE_LOC = "fine_loc";
    String CORAS_LOC = "coras_loc";
    String READ_SMS = "read_sms";
    String RECEIVE_SMS = "read_sms";

    String VIDEO_URI_CAMERA = "video_uri_camera";
    String TOKAN = "TOKAN";
    String _WALL_DTO = "_wall_dto";


    /*ADD POST*/
    String CONTENT = "content";
    String MEDIA = "media";
    String POSTTYPE = "postType";
    String TITLE = "title";
    String _ID = "_id";
    String VARIANT_ID = "variant_id";


    /*ADD POST*/

    /*GET POST*/
    String LIMIT = "limit";
    String OFFSET = "offset";
    /*GET POST*/


    /*LIKE*/
    String POSTID = "postID";

    /*LIKE*/

    /*abuse*/
    String REASON = "reason";
    String USERFILES = "userFiles";

    /*abuse*/


    String BROADCAST = "broadcast";
    String RECEIVER_ID = "receiver_id";
    String ARTIST_NAME = "artist_name";
    String ROLE = "role";
    String IMAGE = "image";
    String MESSAGE = "message";
    String SENDER_ID = "sender_id";
    String PAGE = "page";
    String SENDER_NAME = "sender_name";
    String CHAT_TYPE = "chat_type";
    String PAYMENT_STATUS = "payment_status";
    String CHAT = "chat_receive";
    String PAYMENT_MODE = "payment_mode";


    /*event*/
    String EVENT_NAME = "event_name";
    String EVENT_DATE = "event_date";
    String EVENT_DESC = "event_desc";
    String IMG_PATH = "img_path";
    String EVENT_TIME = "event_time";
    String LATITUDE = "latitude";
    String LONGITUDE = "longitude";
    String EVENT_ID = "event_id";
    //   String PET_TYPE = "pet_type";
    //String ADDRESS = "address";

    /*pet market*/
    //String TITLE = "title";
    String PRICE = "price";
    String SALE_TYPE = "sale_type";
    String NEAR_BY_ID = "nearby_id";
    String HOME_BREED = "home_breed";

    /*paytm*/
    String MID_PAYTM = "MID";
    String ORDER_ID_PAYTM = "ORDER_ID";
    String CUST_ID_PAYTM = "CUST_ID";
    String MOBILE_NO_PAYTM = "MOBILE_NO";
    String EMAIL_PAYTM = "EMAIL";
    String CHANNEL_ID_PAYTM = "CHANNEL_ID";
    String WEBSITE_PAYTM = "WEBSITE";
    String TXN_AMOUNT_PAYTM = "TXN_AMOUNT";
    String INDUSTRY_TYPE_ID_PAYTM = "INDUSTRY_TYPE_ID";
    String CALLBACK_URL_PAYTM = "CALLBACK_URL";
    String CHECKSUMHASH_PAYTM = "CHECKSUMHASH";
    /*paytm*/

    /*guest login*/
    String GUEST_EMAIL = "guest@gmail.com";
    String GUEST_PASSWORD = "";

    String DEVICE_TYPE = "device_type";
    String DEVICE_TYPE_VALUE = "ANDROID";

    int PROMO_CODE_REQUEST = 101;

    /*CheckPromoCode params*/
    String PROMO_CODE = "promo_code";

    String PROFILE_STATUS = "profile_status";

    String PAYMENT_FOR_PET = "payment";
    String FLUTTERWAVE_PUBLIC_key = "FLWPUBK_TEST-904b30338d15e9d2c74fbe2774351038-X";//  FLWPUBK-62d1284a28f68f5d26e6e6230fc7ce63-X
    String FLUTTERWAVE_SECRT_KEY = "FLWSECK_TEST-863be99c24b22fe7158c3477f8a1c314-X";// FLWSECK-21f0aa53644288e4b8c35ecd82918093-X
    String FLUTTERWAVE_ENCRYPTION_KEY = "FLWSECK_TESTc9134e2b02ea"; // 21f0aa5364422f4b1b623bd1
}
