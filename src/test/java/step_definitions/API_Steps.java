package step_definitions;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cucumber.api.Scenario;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import page_objects.MemberValidation_pageobjects;
import utilities.Runtime_TestData;
import utilities.TakeScreenshot;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Properties;

import static org.testng.Assert.assertEquals;


public class API_Steps {
    public WebDriver driver;
    public JsonObject myJsonObj;
    public Scenario _scenario;
    public JSONObject MemberInfoJsonObj;
    public JSONObject CardInfoJsonObj;
    public JSONObject POSJsonObj;
    public DataBase_connection db;
    public MemberValidation_pageobjects member;
    public TakeScreenshot screenshot;


    public static Properties properties = null;

    public static JSONObject jsonObject = null;


    static {
        properties = new Properties();
    }

    public API_Steps(){
        driver = Hooks.driver;
        myJsonObj = Hooks.myJsonObj;
        _scenario = Hooks._scenario;

        member = new MemberValidation_pageobjects(driver,_scenario);
        screenshot = new TakeScreenshot(driver,_scenario);

        System.out.println("API Steps constructor \n");
    }



    @Given("^I Send POST Request and Receive Response$")
    public void Login_to_workbench_and_post_Notification() throws Throwable {


        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(new FileReader("src/test/resources/json.json"));
        String env = System.getProperty("Environment");
        System.out.println("------" + env + "------");

        String URL1 = "";
        try {

            //DFSDM ENVIRONMENT
            if (env.equalsIgnoreCase("Preprod")){
                URL1 = myJsonObj.get("URI_DFSDM").getAsString();

                System.out.println("Execution Environment: Preprod");
            } else if (env.equalsIgnoreCase("QACore2")){
                URL1 = myJsonObj.get("URI_QACore2").getAsString();

                System.out.println("Execution Environment: QACore2");
            }
            System.out.println(URL1);

            URL url = new URL (URL1);
            String encoding = Base64.getEncoder().encodeToString("automation:~g59RcHZy3X]MfpP".getBytes("utf-8"));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Basic " + encoding);
            OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            osw.write(json.toString());
            osw.flush();
            osw.close();
            os.close();  //don't forget to close the OutputStream
            connection.connect();

            String result;
            BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            int result2 = bis.read();
            while(result2 != -1) {
                buf.write((byte) result2);
                result2 = bis.read();
            }
            result = buf.toString();
            System.out.println(result);
            _scenario.write("========API POST Result=========");
            _scenario.write("Transaction Value==="+result);

        } catch(Exception e) {
            e.printStackTrace();
            _scenario.write("========API POST Result=========");
            _scenario.write("Fail");

        }



    }

    @Given("^I read the Profile Information from Sales force UI$")
    public void I_read_the_Profile_Information_from_Sales_force_UI() throws Throwable {
        System.out.println(Instant.now().toString() + "Workbench Page");
        Thread.sleep(1000);


        //Switch to frame
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src, 'Basic')]")));
        System.out.println("switch to frame its working ");
        //Thread.sleep(10000);

        //Title code
        //WebElement Membership_salutation = driver.findElement(MemberValidation_pageobjects.Salutation);
        Select select = new Select(member.Salutation);
        WebElement option = select.getFirstSelectedOption();
        String Mem_Salutation = option.getText();
        Runtime_TestData.Salutation = Mem_Salutation;
        System.out.println("The UI Mem_Salutation is : " + Mem_Salutation);
        _scenario.write("The UI Mem_Salutation is : " + Mem_Salutation);

        //First Name
       // WebElement Membership_First_Name = driver.findElement(MemberValidation_pageobjects.FirstName);
        String FirstName = member.FirstName.getAttribute("value");
        Runtime_TestData.FirstName = FirstName;
        System.out.println("The UI FirstName is : " + FirstName);
        _scenario.write("The UI FirstName is : " + FirstName);

        //Last Name
       // WebElement Membership_Last_Name = driver.findElement(MemberValidation_pageobjects.LastName);
        String lastName = member.LastName.getAttribute("value");
        Runtime_TestData.LastName = lastName;
        System.out.println("The UI lastName is : " + lastName);
        _scenario.write("The UI lastName is : " + lastName);

        //Gender code
        Select Gender = new Select(member.GenderCode);
        WebElement GenderCode = Gender.getFirstSelectedOption();
        String gender = GenderCode.getText();
        //*Runtime_TestData.gender = gender;
        System.out.println("The gender is : " + gender );
        if (gender.equals("Female")) {
            Runtime_TestData.gender = "F";
        } else if (gender.equals("Male")) {
            Runtime_TestData.gender = "M";
        } else {
            _scenario.write("========Gender=========");
            _scenario.write(" Gender Value is Error in UI");
            System.out.println("Gender is not available in Json");
        }
        System.out.println("The UI Gender is : " + Runtime_TestData.gender);
        _scenario.write("The UI Gender is : " + Runtime_TestData.gender);

        //EmailAddressText
        //WebElement Membership_EmailAddressText = driver.findElement(MemberValidation_pageobjects.EmailAddressText);
        String emailAddressText = member.EmailAddressText.getAttribute("value");
        Runtime_TestData.emailAddressText = emailAddressText;
        System.out.println("The UI emailAddressText is : " + emailAddressText);
        _scenario.write("The UI emailAddressText is : " + emailAddressText);

        //IsEmptyEmail
        if (emailAddressText.isEmpty()) {
            Runtime_TestData.IsEmptyEmail = "1";
        } else if (!emailAddressText.isEmpty()) {
            Runtime_TestData.IsEmptyEmail = "0";
        } else {
            _scenario.write("========IsEmptyEmail=========");
            _scenario.write(" IsEmptyEmail is Error in UI");
            System.out.println("IsEmptyEmail is not available in Json");
        }
        System.out.println("The UI IsEmptyEmail is : " + Runtime_TestData.IsEmptyEmail);
        _scenario.write("The UI IsEmptyEmail is : " + Runtime_TestData.IsEmptyEmail);

        //ContactNumberText
        //WebElement Membership_ContactNumberText = driver.findElement(MemberValidation_pageobjects.ContactNumberText);
        String contactNumberText = member.ContactNumberText.getAttribute("value");
        Runtime_TestData.contactNumberText = contactNumberText;
        System.out.println("The UI contactNumberText is : " + contactNumberText);
        _scenario.write("The UI contactNumberText is : " + contactNumberText);

        //ValidMobileNo1
        if (contactNumberText.isEmpty()) {
            Runtime_TestData.ValidMobileNo1 = "0";
        } else if (!emailAddressText.isEmpty()) {
            Runtime_TestData.ValidMobileNo1 = "1";
        }  else {
            _scenario.write("========ValidMobileNo1=========");
            _scenario.write(" ValidMobileNo1 is Error in UI");
            System.out.println("ValidMobileNo1 is not available in Json");
        }


        System.out.println("The UI ValidMobileNo1 is : " + Runtime_TestData.ValidMobileNo1);
        _scenario.write("The UI ValidMobileNo1 is : " + Runtime_TestData.ValidMobileNo1);


        //Mobile1AreaCode
        Select Mobile1AreaCode = new Select(member.Mobile1AreaCode);
        WebElement Membership_Mobile1AreaCode = Mobile1AreaCode.getFirstSelectedOption();
        String mobile1AreaCode = Membership_Mobile1AreaCode.getText();
        Runtime_TestData.mobile1AreaCode = mobile1AreaCode;
        System.out.println("The UI mobile1AreaCode is : " + mobile1AreaCode);
        _scenario.write("The UI mobile1AreaCode is : " + mobile1AreaCode);

        //ageRange
        Select AgeRange = new Select(member.AgeRange);
        WebElement Membership_AgeRange = AgeRange.getFirstSelectedOption();
        String ageRange = Membership_AgeRange.getText();
        if(ageRange.contains("None")){
            ageRange = "";
        }
        Runtime_TestData.ageRange = ageRange;
        System.out.println("The UI ageRange is : " + ageRange);
        _scenario.write("The UI ageRange is : " + ageRange);

        //nativeSalutation
        Select NativeSalutation = new Select(member.NativeSalutation);
        WebElement Membership_NativeSalutation = NativeSalutation.getFirstSelectedOption();
        String nativeSalutation = Membership_NativeSalutation.getText();
        if(nativeSalutation.contains("None")){
            nativeSalutation = "";
        }
        Runtime_TestData.nativeSalutation = nativeSalutation;
        System.out.println("The UI nativeSalutation is : " + nativeSalutation);
        _scenario.write("The UI nativeSalutation is : " + nativeSalutation);

        //EnglishCountry
        Select EnglishCountry = new Select(member.EnglishCountry);
        WebElement Membership_EnglishCountry = EnglishCountry.getFirstSelectedOption();
        String englishCountry = Membership_EnglishCountry.getText();
        Runtime_TestData.englishCountry = englishCountry;
        System.out.println("The UI englishCountry is : " + englishCountry);
        _scenario.write("The UI englishCountry is : " + englishCountry);


        screenshot.takeScreenshot();

        //IsInvalidEmail
        //WebElement Is_Invalid_Email = driver.findElement(member.IsInvalidEmail);
        boolean IsvalidEmail = member.IsInvalidEmail.isSelected();
        if (IsvalidEmail) {
            Runtime_TestData.IsInvalidEmail = "0";
        } else if (!IsvalidEmail) {
            Runtime_TestData.IsInvalidEmail = "1";
        } else {
            _scenario.write("========IsInvalidEmail=========");
            _scenario.write(" IsInvalidEmail is Error in UI");
            System.out.println("IsInvalidEmail is not available in Json");
        }
        System.out.println("The UI IsInvalidEmail is : " + Runtime_TestData.IsInvalidEmail);
        _scenario.write("The UI IsInvalidEmail is : " + Runtime_TestData.IsInvalidEmail);


        //Contact Information
        //SpokenLanguage

        driver.switchTo().defaultContent();
        WebElement brand1 = driver.findElement(MemberValidation_pageobjects.profile_Preferences);
        brand1.click();
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src, 'Preferences')]")));
        System.out.println("switch to frame its working ");

        Thread.sleep(300);
        Select SpokenLanguage = new Select(member.SpokenLanguage);
        WebElement Membership_SpokenLanguage = SpokenLanguage.getFirstSelectedOption();
        String spokenLanguage = Membership_SpokenLanguage.getText();
        Runtime_TestData.spokenLanguage = spokenLanguage;
        System.out.println("The UI spokenLanguage is : " + spokenLanguage);
        _scenario.write("The UI spokenLanguage is : " + spokenLanguage);

        Thread.sleep(1000);
        //Opt In Marketing & Promotional (Phone)
        //WebElement Opt_In_Phone = driver.findElement(MemberValidation_pageobjects.OptInPhone);
        boolean OptInPhone = member.OptInPhone.isSelected();
        if (OptInPhone) {
            Runtime_TestData.OptInPhone = "1";
        } else if (!OptInPhone) {
            Runtime_TestData.OptInPhone = "0";
        } else {
            _scenario.write("========OptInPhone=========");
            _scenario.write(" OptInPhone is Error in UI");
            System.out.println("OptInPhone is not available in Json");
        }
        System.out.println("The UI Opt In Marketing & Promotional (Phone) is : " + Runtime_TestData.OptInPhone);
        _scenario.write("The UI Opt In Marketing & Promotional (Phone)  is : " + Runtime_TestData.OptInPhone);

        //Opt In Marketing & Promotional (Email)
        //WebElement Opt_In_Email = driver.findElement(MemberValidation_pageobjects.OptInEmail);
        boolean OptInEmail = member.OptInEmail.isSelected();
        if (OptInEmail) {
            Runtime_TestData.OptInEmail = "1";
        } else if (!OptInEmail) {
            Runtime_TestData.OptInEmail = "0";
        } else {
            _scenario.write("========OptInEmail=========");
            _scenario.write(" OptInEmail is Error in UI");
            System.out.println("OptInEmail is not available in Json");
        }
        System.out.println("The UI Opt In Marketing & Promotional (Email) is : " + Runtime_TestData.OptInEmail);
        _scenario.write("The UI Opt In Marketing & Promotional (Email)  is : " + Runtime_TestData.OptInEmail);

        //Opt In Marketing & Promotional (Mail)
        //WebElement Opt_In_Mail = driver.findElement(MemberValidation_pageobjects.OptInMail);
        boolean OptInMail = member.OptInMail.isSelected();
        if (OptInMail) {
            Runtime_TestData.OptInMail = "1";
        } else if (!OptInMail) {
            Runtime_TestData.OptInMail = "0";
        } else {
            _scenario.write("========OptInMail=========");
            _scenario.write(" OptInMail is Error in UI");
            System.out.println("OptInMail is not available in Json");
        }
        System.out.println("The UI Opt In Marketing & Promotional (Mail) is : " + Runtime_TestData.OptInMail);
        _scenario.write("The UI Opt In Marketing & Promotional (Mail)  is : " + Runtime_TestData.OptInMail);

        if (Runtime_TestData.OptInPhone.equals("1") && Runtime_TestData.OptInEmail.equals("1") && Runtime_TestData.OptInMail.equals("1")) {
            Runtime_TestData.IsContactable = "1";
        } else {
            Runtime_TestData.IsContactable = "0";
        }
        System.out.println("The UI isContactable is : " + Runtime_TestData.IsContactable);
        _scenario.write("The UI isContactable is : " + Runtime_TestData.IsContactable);


        screenshot.takeScreenshot();
        //Member Cycle
        driver.switchTo().defaultContent();
        //WebElement brand = driver.findElement(MemberValidation_pageobjects.member_cycle);
        member.member_cycle.click();
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src, 'Membership')]")));
        System.out.println("switch to frame its working ");

        //Card Tier
        //WebElement Current_Mem_Tier = driver.findElement(MemberValidation_pageobjects.Curr_Mem_Tier);
        String CurrentMemTier = member.Mem_points.getText();
        Runtime_TestData.CurrentMemTier = CurrentMemTier;
        System.out.println("The UI Current Membership Tier is : " + CurrentMemTier);
        _scenario.write("The UI Current Membership Tier is : " + CurrentMemTier);

        //EnrollmentLocation
        WebElement enrollment_location = driver.findElement(MemberValidation_pageobjects.enrollment_location);
        String enrollmentLocation = enrollment_location.getText();
        Runtime_TestData.EnrollmentLocation = enrollmentLocation;
        System.out.println("The UI EnrollmentLocation is : " + enrollmentLocation);
        _scenario.write("The UI EnrollmentLocation is : " + enrollmentLocation);

        //SourceSystem
        //WebElement Data_Source_Enrolment = driver.findElement(MemberValidation_pageobjects.Data_Source_Enrolment);
        String DataSourceEnrolment = member.Data_Source_Enrolment.getText();
        Runtime_TestData.DataSourceEnrollment = DataSourceEnrolment;
        System.out.println("The UI Data Source Enrollment is : " + DataSourceEnrolment);
        _scenario.write("The UI Data Source Enrollment is : " + DataSourceEnrolment);

        //Enrollment Date
        //WebElement Enrollment_Date = driver.findElement(MemberValidation_pageobjects.Enroll_Date);
        String EnrollmentDate = member.Enroll_Date.getText();
        Date myDate = new SimpleDateFormat("dd/MM/yyyy").parse(EnrollmentDate);
        EnrollmentDate = new SimpleDateFormat("yyyy-MM-dd").format(myDate);
        Runtime_TestData.EnrollmentDate = EnrollmentDate;
        System.out.println("The UI Enrollment Date is : " + EnrollmentDate);
        _scenario.write("The UI Enrollment Date is : " + EnrollmentDate);

        screenshot.takeScreenshot();

        driver.switchTo().defaultContent();
    }

    @And("^Read Json file$")
    public  void main() throws Throwable {

        try {

            JSONParser jsonParser = new JSONParser();

            File file = new File("src/test/resources/json.json");

            Object object = jsonParser.parse(new FileReader(file));

            jsonObject = (JSONObject) object;
            getJsonObjects(jsonObject);
            // parseJson(jsonObject);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    //Get the Member Info Json Object
    public void getJsonObjects (JSONObject jsonObject) throws ParseException {
        JSONObject crm = (JSONObject)jsonObject.get("CRM");
        JSONObject crmCRM = (JSONObject) crm.get("crm:CRM");

        //Retrieve Customer:MemberInfo JSON Object
        JSONArray crmCustomer = (JSONArray)crmCRM.get("crm:Customer");
        for (int k = 0; k < crmCustomer.size(); k++) {

            JSONObject crmCustomer2 = (JSONObject) crmCustomer.get(k);
            MemberInfoJsonObj = (JSONObject) crmCustomer2.get("cus:MemberInfo");
            System.out.println(MemberInfoJsonObj);
        }

        //Retrieve CardInfoJsonObj JSON Object
        JSONArray crmMemberCard = (JSONArray)crmCRM.get("crm:MemberCard");
        for (int k = 0; k < crmMemberCard.size(); k++) {

            JSONObject crmMemberCard2 = (JSONObject) crmMemberCard.get(k);
            CardInfoJsonObj = (JSONObject) crmMemberCard2.get("mc:CardInfo");
            System.out.println(CardInfoJsonObj);
        }
        //Retrieve POSJsonObj JSON Object
        JSONObject crmLoyaltyService = (JSONObject)crmCRM.get("crm:loyaltyService");
        JSONObject ltscommon = (JSONObject)crmLoyaltyService.get("lts:common");
        POSJsonObj = (JSONObject)ltscommon.get("lts:POS");
        System.out.println(POSJsonObj);


    }


    @And("^I query$")
    public  void Iquery() throws Throwable {


        /*String dbquery = "API";
        db = new DataBase_connection();
        db.DbConnection(null, dbquery,"select GenericStr1 ,GenericStr6 ,S1.Name ,GenericStr8 ,Email,GenericStr4 ,GenericStr7 ,AreaCode ,MobileNo ,S4.Name ,S.Name,S7.Name ,GenericStr48 ,GenericStr44 ,GenericStr39 ,GenericStr15 ,GenericDate1 ,C.MembershipStatusCode ,Gender ,IsInvalidEmail , IsEmptyEmail ,GenericStr14 ,S3.Name ,GenericStr32 ,GenericStr49 , IsEmailOptOut,IsOptOutMobile1,IsOptOutMobile2,IsOptOutWorkPhone,IsOptOutAddressEnglish,IsOptOutAddressOthers \n"  +
                "from MatrixTpReward.dbo.Member Member left join MatrixTpReward.dbo.SystemCode S on S.Code=Member.Country left join MatrixTpReward.dbo.SystemCode S1 on S1.Code=Member.Salutation left join MatrixTpReward.dbo.SystemCode S2 on S2.Code=Member.GenericStr12 left join MatrixTpReward.dbo.SystemCode S3 on S3.Code=Member.GenericStr28 left join MatrixTpReward.dbo.SystemCode S4 on S4.Code=Member.GenericStr37 left join MatrixTpReward.dbo.SystemCode S5 on S5.Code=Member.GenericStr41 left join MatrixTpReward.DFS.DFS_Member DM on DM.Member_AutoID=Member.AutoID left join MatrixTpReward.dbo.SystemCode S6 On S6.ParentCode=916 and S6.Code=CAST(DM.MarketingSource AS VARCHAR(5)) left join MatrixTpReward.dbo.SystemCode S7 On S7.ParentCode=905 and S7.Code=CAST(DM.AgeRange AS VARCHAR(5)) inner join MatrixTpReward.dbo.Card C on C.MemberID=Member.ID where C.CardNo IN ('" + CardInfoJsonObj.get("mc:CardNo") + "');\n");

        dbquery = "nativeSalutation";
        db.DbConnection(null, dbquery,"select description from  MatrixTpReward.dfs.GenderSalutation WITH (NOLOCK) where salutationcode IN ('" + db.NativeSalutationCode + "');\n");
*/
    }

    @And("^I Validate Request Payload with Member Profile Information$")
    public  void I_Validate_Request_Payload_with_Member_Profile_Information( ) throws Throwable {


        //Salutation
        try{
            if (MemberInfoJsonObj.get("cus:TitleCode")!=null){
                System.out.println("========Title code=========");
                System.out.println("JSON: "+MemberInfoJsonObj.get("cus:TitleCode"));
                System.out.println("UI: "+Runtime_TestData.Salutation);
                System.out.println("DB: "+db.Salutation);

                assertEquals(Runtime_TestData.Salutation,MemberInfoJsonObj.get("cus:TitleCode").toString());
                assertEquals(db.Salutation,MemberInfoJsonObj.get("cus:TitleCode").toString());
                _scenario.write("========Title code=========");
                _scenario.write("DB Value==="+db.Salutation);
                _scenario.write("UI Value==="+Runtime_TestData.Salutation);
                _scenario.write("Json Value==="+MemberInfoJsonObj.get("cus:TitleCode").toString());
                //Runtime_TestData.Api_Value_TitleCode = a;
            }
        }catch(NoSuchElementException ex){
            _scenario.write("========Title code=========");
            _scenario.write(" Title code  is Mismatch");
        }

        //GenderCode
        try{
            if (MemberInfoJsonObj.get("cus:GenderCode")!=null){
                System.out.println("========Gender=========");
                System.out.println("JSON: "+MemberInfoJsonObj.get("cus:GenderCode"));
                System.out.println("UI: "+Runtime_TestData.gender);
                System.out.println("DB: "+db.Gender);


                //System.out.println("$$$$$$$$$"+Runtime_TestData.Api_Value_GenderCode);
                assertEquals(Runtime_TestData.gender,MemberInfoJsonObj.get("cus:GenderCode").toString());
                assertEquals(db.Gender,MemberInfoJsonObj.get("cus:GenderCode").toString());
                _scenario.write("========Gender=========");
                _scenario.write("DB Value==="+db.Gender);
                _scenario.write("UI Value==="+Runtime_TestData.gender);
                _scenario.write("Json Value==="+MemberInfoJsonObj.get("cus:GenderCode").toString());
            }
        }catch(NoSuchElementException ex){
            _scenario.write("========Gender=========");
            _scenario.write(" Gender Value is Mismatch");
        }


        //EmailAddressText
        try{
            if (MemberInfoJsonObj.get("cus:EmailAddressText")!=null){
                System.out.println("========EmailAddressText=========");
                System.out.println("JSON: "+MemberInfoJsonObj.get("cus:EmailAddressText"));
                System.out.println("UI: "+Runtime_TestData.emailAddressText);
                System.out.println("DB: "+db.Email);

                assertEquals(Runtime_TestData.emailAddressText,MemberInfoJsonObj.get("cus:EmailAddressText").toString());
                assertEquals(db.Email,MemberInfoJsonObj.get("cus:EmailAddressText").toString());
                _scenario.write("========EmailAddressText=========");
                _scenario.write("DB Value==="+db.Email);
                _scenario.write("UI Value==="+Runtime_TestData.emailAddressText);
                _scenario.write("Json Value==="+MemberInfoJsonObj.get("cus:EmailAddressText").toString());
            }
        }catch(NoSuchElementException ex){
            _scenario.write("========EmailAddressText=========");
            _scenario.write(" EmailAddressText  is Mismatch");
        }


        //ContactNumberText
        try {
            if (MemberInfoJsonObj.get("cus:ContactNumberText") != null) {
                System.out.println("========ContactNumberText=========");
                System.out.println("JSON: "+MemberInfoJsonObj.get("cus:ContactNumberText"));
                System.out.println("UI: "+Runtime_TestData.contactNumberText);
                System.out.println("DB: "+db.MobileNo);

                assertEquals(Runtime_TestData.contactNumberText,MemberInfoJsonObj.get("cus:ContactNumberText").toString());
                assertEquals(db.MobileNo,MemberInfoJsonObj.get("cus:ContactNumberText").toString());
                _scenario.write("========ContactNumberText=========");
                _scenario.write("DB Value==="+db.MobileNo);
                _scenario.write("UI Value==="+Runtime_TestData.contactNumberText);
                _scenario.write("Json Value==="+MemberInfoJsonObj.get("cus:ContactNumberText").toString());
            }
        }catch(NoSuchElementException ex){
            _scenario.write("========ContactNumberText=========");
            _scenario.write(" ContactNumberText  is Mismatch");
        }

        //IsEmptyEmail
        try {
            if (MemberInfoJsonObj.get("cus:IsEmptyEmail") != null) {
                // Runtime_TestData.Api_Value_IsEmptyEmail = MemberInfoJsonObj.get("cus:IsEmptyEmail").toString();
                System.out.println("JSON: "+MemberInfoJsonObj.get("cus:IsEmptyEmail"));
                System.out.println("UI: "+Runtime_TestData.IsEmptyEmail);
                System.out.println("DB: "+db.IsEmptyEmail);

                assertEquals(db.IsEmptyEmail,Runtime_TestData.IsEmptyEmail);
                _scenario.write("========IsEmptyEmail=========");
                _scenario.write("DB Value==="+db.IsEmptyEmail);
                _scenario.write("UI Value==="+Runtime_TestData.IsEmptyEmail);
                _scenario.write("Json Value==="+MemberInfoJsonObj.get("cus:IsEmptyEmail").toString());
            }
        }catch(NoSuchElementException ex){
            _scenario.write("========IsEmptyEmail=========");
            _scenario.write(" IsEmptyEmail  is Mismatch");
        }


        //IsInvalidEmail
        try {
            if (MemberInfoJsonObj.get("cus:IsInvalidEmail") != null) {
                // Runtime_TestData.Api_Value_IsEmptyEmail = jsonObject.get("cus:IsEmptyEmail").toString();
                System.out.println("========IsInvalidEmail=========");
                System.out.println("JSON: "+MemberInfoJsonObj.get("cus:IsInvalidEmail"));
                System.out.println("UI: "+Runtime_TestData.IsInvalidEmail);
                System.out.println("DB: "+db.IsInvalidEmail);

                assertEquals(Runtime_TestData.IsInvalidEmail,MemberInfoJsonObj.get("cus:IsInvalidEmail").toString());
                assertEquals(db.IsInvalidEmail,MemberInfoJsonObj.get("cus:IsInvalidEmail").toString());

                _scenario.write("========IsInvalidEmail=========");
                _scenario.write("DB Value==="+db.IsInvalidEmail);
                _scenario.write("UI Value==="+Runtime_TestData.IsInvalidEmail);
                _scenario.write("Json Value==="+MemberInfoJsonObj.get("cus:IsInvalidEmail").toString());
            }
        }catch(NoSuchElementException ex){
            _scenario.write("========IsInvalidEmail=========");
            _scenario.write(" IsInvalidEmail  is Mismatch");
        }


        //FirstName
        try {
            if (MemberInfoJsonObj.get("cus:FirstName")!=null){
                System.out.println("========FirstName=========");
                System.out.println("JSON: "+MemberInfoJsonObj.get("cus:FirstName"));
                System.out.println("UI: "+Runtime_TestData.FirstName);
                System.out.println("DB: "+db.FirstName);

                assertEquals(Runtime_TestData.FirstName,MemberInfoJsonObj.get("cus:FirstName").toString());
                assertEquals(db.FirstName,MemberInfoJsonObj.get("cus:FirstName").toString());
                _scenario.write("========FirstName=========");
                _scenario.write("DB Value==="+db.FirstName);
                _scenario.write("UI Value==="+Runtime_TestData.FirstName);
                _scenario.write("Json Value==="+MemberInfoJsonObj.get("cus:FirstName").toString());
                // Runtime_TestData.Api_Value_FirstName = jsonObject.get("cus:FirstName").toString();
            }
        }catch(NoSuchElementException ex){
            _scenario.write("========FirstName=========");
            _scenario.write(" Firstname  is Mismatch");
        }


        //LastName
        try{
            if (MemberInfoJsonObj.get("cus:LastName")!=null){
                System.out.println("========LastName=========");
                System.out.println("JSON: "+MemberInfoJsonObj.get("cus:LastName"));
                System.out.println("UI: "+Runtime_TestData.LastName);
                System.out.println("DB: "+db.LastName);

                assertEquals(Runtime_TestData.LastName,MemberInfoJsonObj.get("cus:LastName").toString());
                assertEquals(db.LastName,MemberInfoJsonObj.get("cus:LastName").toString());
                _scenario.write("========LastName=========");
                _scenario.write("DB Value==="+db.LastName);
                _scenario.write("UI Value==="+Runtime_TestData.LastName);
                _scenario.write("Json Value==="+MemberInfoJsonObj.get("cus:LastName").toString());
                // Runtime_TestData.Api_Value_LastName = jsonObject.get("cus:LastName").toString();
            }
        }catch(NoSuchElementException ex){
            _scenario.write("========LastName=========");
            _scenario.write(" LastName  is Mismatch");
        }


        //NativeSalutation
        try {
            if (MemberInfoJsonObj.get("cus:NativeSalutation") != null) {
                System.out.println("========NativeSalutation=========");
                System.out.println("JSON: " + MemberInfoJsonObj.get("cus:NativeSalutation"));
                System.out.println("UI: " + Runtime_TestData.nativeSalutation);
                System.out.println("DB: " + db.NativeSalutation);

                assertEquals(Runtime_TestData.nativeSalutation, MemberInfoJsonObj.get("cus:NativeSalutation").toString());
                assertEquals(db.NativeSalutation, MemberInfoJsonObj.get("cus:NativeSalutation").toString());
                _scenario.write("========NativeSalutation=========");
                _scenario.write("DB Value===" + db.NativeSalutation);
                _scenario.write("UI Value===" + Runtime_TestData.nativeSalutation);
                _scenario.write("Json Value===" + MemberInfoJsonObj.get("cus:NativeSalutation").toString());
                //  Runtime_TestData.Api_Value_NativeSalutation = jsonObject.get("cus:NativeSalutation").toString();
            }
        }catch(NoSuchElementException ex){
            _scenario.write("========NativeSalutation=========");
            _scenario.write(" NativeSalutation  is Mismatch");
        }

        //MobileAreaCode
        try {
            if (MemberInfoJsonObj.get("cus:Mobile1AreaCode") != null) {
                System.out.println("========Mobile1AreaCode=========");
                System.out.println("JSON: " + MemberInfoJsonObj.get("cus:Mobile1AreaCode"));
                System.out.println("UI: " + Runtime_TestData.mobile1AreaCode);
                System.out.println("DB: " + db.MobileAreaCode);
                String[] mobileAreaSplitStr = Runtime_TestData.mobile1AreaCode.split("\\s+");
                assertEquals(mobileAreaSplitStr[0], MemberInfoJsonObj.get("cus:Mobile1AreaCode").toString());
                assertEquals(db.MobileAreaCode, MemberInfoJsonObj.get("cus:Mobile1AreaCode").toString());
                _scenario.write("========Mobile1AreaCode=========");
                _scenario.write("DB Value===" + db.MobileAreaCode);
                _scenario.write("UI Value===" + Runtime_TestData.mobile1AreaCode);
                _scenario.write("Json Value===" + MemberInfoJsonObj.get("cus:Mobile1AreaCode").toString());
                //Runtime_TestData.Api_Value_Mobile1AreaCode = jsonObject.get("cus:Mobile1AreaCode").toString();
            }
        }catch(NoSuchElementException ex){
            _scenario.write("========Mobile1AreaCode=========");
            _scenario.write(" Mobile1AreaCode  is Mismatch");
        }

        //ValidMobileNo1
        try {
            if (MemberInfoJsonObj.get("cus:ValidMobileNo1") != null) {
                // Runtime_TestData.Api_Value_ValidMobileNo1 = jsonObject.get("cus:ValidMobileNo1").toString();
                System.out.println("========ValidMobileNo1=========");
                System.out.println("JSON: " + MemberInfoJsonObj.get("cus:ValidMobileNo1"));
                System.out.println("UI: " + Runtime_TestData.ValidMobileNo1);
                System.out.println("DB: " + db.ValidMobileNo1);

                assertEquals(db.ValidMobileNo1,Runtime_TestData.ValidMobileNo1);
                _scenario.write("========ValidMobileNo1=========");
                _scenario.write("DB Value===" + db.ValidMobileNo1);
                _scenario.write("UI Value==="+Runtime_TestData.ValidMobileNo1);
                _scenario.write("Json Value===" + MemberInfoJsonObj.get("cus:ValidMobileNo1").toString());
            }
        }catch(NoSuchElementException ex){
            _scenario.write("========ValidMobileNo1=========");
            _scenario.write(" ValidMobileNo1  is Mismatch");
        }


        //EnglishCountry
        try{
            if (MemberInfoJsonObj.get("cus:EnglishCountry")!=null){
                // Runtime_TestData.Api_Value_EnglishCountry = jsonObject.get("cus:EnglishCountry").toString();
                System.out.println("========EnglishCountry=========");
                System.out.println("JSON: " + MemberInfoJsonObj.get("cus:EnglishCountry"));
                System.out.println("UI: "+Runtime_TestData.englishCountry);
                System.out.println("DB: " + db.EnglishCountry);

                assertEquals(Runtime_TestData.englishCountry,MemberInfoJsonObj.get("cus:EnglishCountry").toString());
                assertEquals(db.EnglishCountry, MemberInfoJsonObj.get("cus:EnglishCountry").toString());
                _scenario.write("========EnglishCountry=========");
                _scenario.write("DB Value===" + db.EnglishCountry);
                _scenario.write("UI Value==="+Runtime_TestData.englishCountry);
                _scenario.write("Json Value===" + MemberInfoJsonObj.get("cus:EnglishCountry").toString());
            }
        }catch(NoSuchElementException ex){
            _scenario.write("========EnglishCountry=========");
            _scenario.write(" EnglishCountry  is Mismatch");
        }


        //spokenLanguage
        try {
            if (MemberInfoJsonObj.get("cus:SpokenLanguage") != null) {
                // Runtime_TestData.Api_Value_SpokenLanguage = jsonObject.get("cus:SpokenLanguage").toString();
                System.out.println("========SpokenLanguage=========");
                System.out.println("JSON: " + MemberInfoJsonObj.get("cus:SpokenLanguage"));
                System.out.println("UI: "+Runtime_TestData.spokenLanguage);
                System.out.println("DB: " + db.SpokenLanguageCode);

                assertEquals(Runtime_TestData.spokenLanguage,MemberInfoJsonObj.get("cus:SpokenLanguage").toString());
                assertEquals(db.SpokenLanguageCode, MemberInfoJsonObj.get("cus:SpokenLanguage").toString());
                _scenario.write("========SpokenLanguage=========");
                _scenario.write("DB Value===" + db.SpokenLanguageCode);
                _scenario.write("UI Value==="+Runtime_TestData.spokenLanguage);
                _scenario.write("Json Value===" + MemberInfoJsonObj.get("cus:SpokenLanguage").toString());
            }
        }catch(NoSuchElementException ex){
            _scenario.write("========spokenLanguage=========");
            _scenario.write(" spokenLanguage  is Mismatch");
        }


        //IsContactable
        try {
            if (MemberInfoJsonObj.get("cus:IsContactable") != null) {

                System.out.println("========IsContactable=========");
                System.out.println("JSON: " + MemberInfoJsonObj.get("cus:IsContactable"));
                System.out.println("UI: "+Runtime_TestData.IsContactable);
                System.out.println("DB: " + db.IsContactable);

                assertEquals(Runtime_TestData.IsContactable,MemberInfoJsonObj.get("cus:IsContactable").toString());
                assertEquals(db.IsContactable, MemberInfoJsonObj.get("cus:IsContactable").toString());
                _scenario.write("========IsContactable=========");
                _scenario.write("DB Value===" + db.IsContactable);
                _scenario.write("UI Value==="+Runtime_TestData.IsContactable);
                _scenario.write("Json Value===" + MemberInfoJsonObj.get("cus:IsContactable").toString());
            }
        }catch(NoSuchElementException ex){
            _scenario.write("========IsContactable=========");
            _scenario.write(" IsContactable  is Mismatch");
        }




        //CardPickupMethod
        try {
            if (MemberInfoJsonObj.get("cus:CardPickupMethod") != null) {
                // Runtime_TestData.Api_Value_CardPickupMethod = jsonObject.get("cus:CardPickupMethod").toString();
                System.out.println("========CardPickupMethod=========");
                System.out.println("JSON: " + MemberInfoJsonObj.get("cus:CardPickupMethod"));
                //System.out.println("UI: "+Runtime_TestData.);
                System.out.println("DB: " + db.CardPickupMethod);

                assertEquals(db.CardPickupMethod, MemberInfoJsonObj.get("cus:CardPickupMethod").toString());
                _scenario.write("========CardPickupMethod=========");
                _scenario.write("DB Value===" + db.CardPickupMethod);
                //_scenario.write("UI Value==="+Runtime_TestData.);
                _scenario.write("Json Value===" + MemberInfoJsonObj.get("cus:CardPickupMethod").toString());
            }
        }catch(NoSuchElementException ex){
            _scenario.write("========CardPickupMethod=========");
            _scenario.write(" CardPickupMethod  is Mismatch");
        }


        //SourceSystem
        try {
            if (MemberInfoJsonObj.get("cus:SourceSystem") != null) {
                //  Runtime_TestData.Api_Value_SourceSystem = jsonObject.get("cus:SourceSystem").toString();
                System.out.println("========SourceSystem=========");
                System.out.println("JSON: " + MemberInfoJsonObj.get("cus:SourceSystem"));
                System.out.println("UI: "+Runtime_TestData.DataSourceEnrollment);
                System.out.println("DB: " + db.SourceSystem);

                assertEquals(Runtime_TestData.DataSourceEnrollment, MemberInfoJsonObj.get("cus:SourceSystem").toString());
                assertEquals(db.SourceSystem, MemberInfoJsonObj.get("cus:SourceSystem").toString());
                _scenario.write("========SourceSystem=========");
                _scenario.write("DB Value===" + db.SourceSystem);
                _scenario.write("UI Value==="+Runtime_TestData.DataSourceEnrollment);
                _scenario.write("Json Value===" + MemberInfoJsonObj.get("cus:SourceSystem").toString());
            }
        }catch(NoSuchElementException ex){
            _scenario.write("========SourceSystem=========");
            _scenario.write(" SourceSystem  is Mismatch");
        }

        if (MemberInfoJsonObj.get("cus:Override")!=null){
            // Runtime_TestData.Api_Value_Override = MemberInfoJsonObj.get("cus:Override").toString();
            //assertEquals(db.Override,MemberInfoJsonObj.get("cus:Override").toString());
            System.out.println(MemberInfoJsonObj.get("cus:Override"));
        }


        //RegistrationDivisionCode
        try {
            if (MemberInfoJsonObj.get("cus:RegistrationDivisionCode") != null) {
                //  Runtime_TestData.Api_Value_RegistrationDivisionCode = MemberInfoJsonObj.get("cus:RegistrationDivisionCode").toString();
                System.out.println("========RegistrationDivisionCode=========");
                System.out.println("JSON: " + MemberInfoJsonObj.get("cus:RegistrationDivisionCode"));
                //System.out.println("UI: "+Runtime_TestData.);
                System.out.println("DB: " + db.RegistrationDivisionCode);

                assertEquals(db.RegistrationDivisionCode, MemberInfoJsonObj.get("cus:RegistrationDivisionCode").toString());
                _scenario.write("========RegistrationDivisionCode=========");
                _scenario.write("DB Value===" + db.RegistrationDivisionCode);
                // _scenario.write("UI Value==="+Runtime_TestData.);
                _scenario.write("Json Value===" + MemberInfoJsonObj.get("cus:RegistrationDivisionCode").toString());
            }
        } catch(NoSuchElementException ex){
            _scenario.write("========RegistrationDivisionCode=========");
            _scenario.write(" RegistrationDivisionCode  is Mismatch");
        }


        //RegistrationLocationID
        try{
            if (MemberInfoJsonObj.get("cus:RegistrationLocationID")!=null){
                //  Runtime_TestData.Api_Value_RegistrationLocationID = MemberInfoJsonObj.get("cus:RegistrationLocationID").toString();
                System.out.println("========RegistrationLocationID=========");
                System.out.println("JSON: " + MemberInfoJsonObj.get("cus:RegistrationLocationID"));
                //System.out.println("UI: "+Runtime_TestData.);
                System.out.println("DB: " + db.RegistrationLocationID);

                assertEquals(db.RegistrationLocationID,MemberInfoJsonObj.get("cus:RegistrationLocationID").toString());
                _scenario.write("========RegistrationLocationID=========");
                _scenario.write("DB Value===" + db.RegistrationLocationID);
                // _scenario.write("UI Value==="+Runtime_TestData.);
                _scenario.write("Json Value===" + MemberInfoJsonObj.get("cus:RegistrationLocationID").toString());
            }
        } catch(NoSuchElementException ex){
            _scenario.write("========RegistrationLocationID=========");
            _scenario.write(" RegistrationLocationID  is Mismatch");
        }


        //CustomerRegistrationDatetime
        try {
            if (MemberInfoJsonObj.get("cus:CustomerRegistrationDatetime") != null) {
                //  Runtime_TestData.Api_Value_CustomerRegistrationDatetime = MemberInfoJsonObj.get("cus:CustomerRegistrationDatetime").toString();
                String jsonCustomerRegDate = MemberInfoJsonObj.get("cus:CustomerRegistrationDatetime").toString();
                jsonCustomerRegDate = jsonCustomerRegDate.substring(0, 10);

                String dbCustomerRegDate = db.CustomerRegistrationDatetime;
                dbCustomerRegDate = dbCustomerRegDate.substring(0,10);

                System.out.println("========CustomerRegistrationDatetime=========");
                System.out.println("JSON: " + MemberInfoJsonObj.get("cus:CustomerRegistrationDatetime"));
                System.out.println("UI: "+Runtime_TestData.EnrollmentDate);
                System.out.println("DB: " + db.CustomerRegistrationDatetime);

                assertEquals(Runtime_TestData.EnrollmentDate, jsonCustomerRegDate);
                assertEquals(dbCustomerRegDate, jsonCustomerRegDate);
                _scenario.write("========CustomerRegistrationDatetime=========");
                _scenario.write("DB Value===" + db.CustomerRegistrationDatetime);
                _scenario.write("UI Value==="+Runtime_TestData.EnrollmentDate);
                _scenario.write("Json Value===" + MemberInfoJsonObj.get("cus:CustomerRegistrationDatetime").toString());
            }
        }catch(NoSuchElementException ex){
            _scenario.write("========CustomerRegistrationDatetime=========");
            _scenario.write(" CustomerRegistrationDatetime  is Mismatch");
        }


        //AgeRange
        try{
            if (MemberInfoJsonObj.get("cus:AgeRange")!=null){
                // Runtime_TestData.Api_Value_AgeRange = MemberInfoJsonObj.get("cus:AgeRange").toString();
                System.out.println("========AgeRange=========");
                System.out.println("JSON: " + MemberInfoJsonObj.get("cus:AgeRange"));
                System.out.println("UI: " + Runtime_TestData.ageRange);
                System.out.println("DB: " + db.AgeRange);
                if(db.AgeRange == null){
                    db.AgeRange = "";
                }
                assertEquals(Runtime_TestData.ageRange,MemberInfoJsonObj.get("cus:AgeRange").toString());
                assertEquals(db.AgeRange,MemberInfoJsonObj.get("cus:AgeRange").toString());
                _scenario.write("========AgeRange=========");
                _scenario.write("DB Value==="+db.AgeRange);
                _scenario.write("UI Value==="+Runtime_TestData.ageRange);
                _scenario.write("Json Value==="+MemberInfoJsonObj.get("cus:AgeRange").toString());
            }
        }catch(NoSuchElementException ex){
            _scenario.write("========AgeRange=========");
            _scenario.write(" AgeRange  is Mismatch");
        }


        //CardNo
        if (CardInfoJsonObj.get("mc:CardNo")!=null){
            // Runtime_TestData.Api_Value_CardNo = CardInfoJsonObj.get("cus:CardNo").toString();
            //assertEquals(db.CardNo,CardInfoJsonObj.get("cus:CardNo").toString());
            System.out.println(CardInfoJsonObj.get("mc:CardNo"));
        }

        //MembershipCardTypeCode
        if (CardInfoJsonObj.get("mc:MembershipCardTypeCode")!=null){
            // Runtime_TestData.Api_Value_MembershipCardTypeCode = CardInfoJsonObj.get("cus:MembershipCardTypeCode").toString();
            //assertEquals(db.CardNo,CardInfoJsonObj.get("cus:CardNo").toString());
            System.out.println(CardInfoJsonObj.get("mc:MembershipCardTypeCode"));
        }

        //OutletCode
        if (POSJsonObj.get("pos:OutletCode")!=null){
            // Runtime_TestData.Api_Value_OutletCode = POSJsonObj.get("cus:OutletCode").toString();
            System.out.println(POSJsonObj.get("pos:OutletCode"));
        }

        //CashierID
        if (POSJsonObj.get("pos:CashierID")!=null){
            //  Runtime_TestData.Api_Value_CashierID = POSJsonObj.get("cus:CashierID").toString();
            System.out.println(POSJsonObj.get("pos:CashierID"));
        }

    }


}
