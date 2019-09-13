package page_objects;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import cucumber.api.Scenario;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.apache.http.HttpStatus;
import org.apache.struts.chain.commands.servlet.SetOriginalURI;
import org.json.JSONString;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.json.Json;
import org.testng.Assert;
import org.testng.annotations.Test;
import property.Property;
import utilities.createDbConnection;
import utilities.readTestData;
import utilities.validateAPIreturnMessage;

import java.io.*;
import java.net.ConnectException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;


public class DSAcreateMember_pageObjects {

    public Scenario _scenario;

    public JSONObject MemberInfoJsonObj;
    public JSONObject RewardCycleInfoJsonObj;
    public JSONObject CardInfoJsonObj;
    public JSONObject ContactJsonObj;
    public JSONObject POSJsonObj;
    public JSONObject jsonObject = null;
    public validateAPIreturnMessage apiValidate;

    public String MemberID="";
    public String NationalityCode="";
    public String OtherPhone2="";
    public String MobileNo2="";
    public String TitleCode="";
    public String GenderCode="";
    public String EmailAddressText="";
    public String PostalCode="";
    public String CityNameText="";
    public String CountryNameText="";
    public String ContactNumberText="";
    public String IsInvalidAddress="";
    public String IsInvalidEmail="";
    public String IsEmptyEmail="";
    public String EmailUnwillingToProvide="";
    public String FirstName="";
    public String FirstNameNative="";
    public String LastName="";
    public String LastNameNative="";
    public String BirthDate="";

    public String BirthMonth="";
    public String RegistrationDivisionCode="";
    public String RegistrationLocationID="";
    public String MailingAddress2="";
    public String ZipCode="";

    public String CorrCityNameText="";
    public String CorrStateNameText="";
    public String MailingAddress1="";
    public String MailingAddress3="";
    public String ResCityNameText="";

    public String ResStateNameText="";
    public String EnglishCountry="";
    public String ValidMobileNo1="";
    public String SpokenLanguage="";
    public String IsContactable="";

    public String CustomerRegistrationDatetime="";
    public String CustomerLeisureActivity="";
    public String CustomerShoppingPreference="";
    public String CustomerPreferredBrands="";
    public String LeisureActivitiesMultiple="";

    public String ShoppingPreferencesMultiple="";
    public String PreferredBrandsMultiple="";
    public String AgeRange="";
    public String MarketingSource="";
    public String ZipCodeValidFlag="";

    public String CardTier="";
    public String CardPickupMethod="";
    public String IpadID="";
    public String StaffID="";
    public String MarketingSourceOthers="";

    public String Mobile1AreaCode="";
    public String CreationDate="";
    public String UpdateDate="";
    public String CreationUserID="";
    public String UpdateUserID="";

    public String SourceSystem="";
    public String Override="";
    public String ContactDetails="";

    public String CardNo="";
    public String OutletCode="";
    public String CashierID="";
    
    public String RegistrationLocationCode="";
    public String HomeNo="";
    public String OtherAddress1="";
    public String ValidHomeNo="";
    public String ChildrenCountRange="";
    public String FirstPurchaseDate="";
    public String NoOfRepeatVisits="";
    

    public String ContactType="";
    public String ContactDetail="";
    public String NativeSalutation="";
    public String MembershipCardTypeCode="";
    
    public String POSNotesUpdatedOn="";
    public String CustomerEnrolmentSource="";
    public String LastPurchaseDate="";
    public String TotalDiscount="";
    public String AverageNoOfDaysPurchase="";
    public String IsEmailOptOut="";
    public String IsValidMobile2="";
    public String Mobile2AreaCode="";
    public String OtherAddress2="";
    public String OtherAddress3="";
    public String IsValidAddressOthers="";
    public String MemberPointBalance="";
    public String LastAccrualDate="";
    public String JoinedDate="";
    public String MemberStatus="";
    public String Remarks="";
    public String HomePhoneAreaCode="";
    public String PortalActivationFlag="";
    public String isValidPoints="";
    public String isActiveLoyalTMember="";
    public String IsVCardDownloaded="";
    public String TotalVisits="";
    public String CarryForwardDollarUSDAmount="";
    public String TotalSpending="";
    public String MembershipTierID="";
    public String StatusDollarUSDAmount="";
    public String StartDate="";
    public String EndDate="";
    public String StatusDollarToUpgrade="";
    public String StatusDollarToRenew="";
    public String MembershipcardStatusCode="";
    public String PtsHoldingDays="";
    public String TotalPointsBAL="";
    public String IssueDateKey="";
    public String EffectiveDateKey="";
    public String PurchaseRange="";
    public String LTV="";
    public String NoOfPurchases="";
    public String TotalUnits="";
    public String MembershipCardTierCode="";
    public String ReturnMessage="";
    public String ReturnStatus="";
    public String BalancePointCount="";
    public String ExpiringDate="";

    //constructor class
    public DSAcreateMember_pageObjects(Scenario _scenario) {
        this._scenario=_scenario;
    }

    //common methods

    public String readRequestFromJson(String FilePathTag) throws Exception {
        readTestData testData = new readTestData();

        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(testData.readTestData(FilePathTag)));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();

        } catch (FileNotFoundException FileNotFound) {
            System.out.println("Unable to find the Json file : "+FileNotFound);
            _scenario.write("Unable to find the Json file : "+FileNotFound);
            Assert.fail();

        } catch(Exception e) {
            System.out.println("Exception occurred during the reading of Json file - "+e);
            _scenario.write("Exception occurred during the reading of Json file - "+e);
            Assert.fail();
        }
        if(result.isEmpty() || result=="") {
            System.out.println("The Json file does not contain a valid request body.");
            _scenario.write("The Json file does not contain a valid request body.");
            Assert.fail();
        }
        else {
            System.out.println("Reading Json request body is Successful !");
            _scenario.write("Reading Json request body is Successful !");
        }
        return result;
    }

    public String getETRjsonPath(String env) {
        String filepath="";
        if(env.equalsIgnoreCase("Preprod")) {
            filepath = "ETR_RequestBody_FilePath_Preprod";
        } else if(env.equalsIgnoreCase("QACore2")) {
            filepath = "ETR_RequestBody_FilePath_QACore2";
        }
        return filepath;
    }

    public String getUpdateETRjsonPath(String env) {

        String filepath="";
        if(env.equalsIgnoreCase("Preprod")) {
            filepath = "ETR_SearchMember_RequestBody_FilePath_Preprod";
        } else if(env.equalsIgnoreCase("QACore2")) {
            filepath = "ETR_SearchMember_RequestBody_FilePath_QACore2";
        }
        return filepath;
    }

    public String getDSAjsonPath(String env) {
        String filepath="";
        if(env.equalsIgnoreCase("Preprod")) {
            filepath= "DSA_RequestBody_FilePath_Preprod";
        } else if (env.equalsIgnoreCase("QACore2")) {
            filepath = "DSA_RequestBody_FilePath_QACore2";
        }
        return filepath;
    }

    public String getDSAmemberContactDetailsUpdateJsonPath(String tag, String env) {
        String filepath="";
        if(env.equalsIgnoreCase("Preprod")) {
            filepath = tag+"_Preprod";
        } else if(env.equalsIgnoreCase("QACore2")) {
            filepath = tag+"_QACore2";
        }
        return filepath;
    }

    public String getPOSjsonPath(String env){
        String filepath="";
        if(env.equalsIgnoreCase("Preprod")) {
            filepath = "POS_RequestBody_FilePath_Preprod";
        } else if(env.equalsIgnoreCase("QACore2")) {
            filepath = "POS_RequestBody_FilePath_QACore2";
        }
        return filepath;
    }

    public String getPOSsearchMemberJsonPath(String env){
        String filepath="";
        if(env.equalsIgnoreCase("Preprod")) {
            filepath = "POS_SearchMember_RequestBody_FilePath_Preprod";
        } else if(env.equalsIgnoreCase("QACore2")) {
            filepath = "POS_SearchMember_RequestBody_FilePath_QACore2";
        }
        return filepath;
    }

    public String getETRgetMemberJsonPath(String env) {

        String filepath="";
        if(env.equalsIgnoreCase("Preprod")) {
            filepath = "ETR_GetMember_RequestBody_FilePath_Preprod";
        } else if(env.equalsIgnoreCase("QACore2")) {
            filepath = "ETR_GetMember_RequestBody_FilePath_QACore2";
        }
        return filepath;
    }

    public String getPOSgetMemberJsonPath(String env) {
        String filepath="";
        if(env.equalsIgnoreCase("Preprod")) {
            filepath = "POS_GetMember_RequestBody_FilePath_Preprod";
        } else if(env.equalsIgnoreCase("QACore2")) {
            filepath = "POS_GetMember_RequestBody_FilePath_QACore2";
        }
        return filepath;
    }

    public String getPOSgetMemberRequestBodyForAnyCard(String cardNo) throws Exception {
         String requestBody = "{\n" +
                "  \"CRM\" : {\n" +
                "    \"crm:CRM\" : {\n" +
                "      \"crm:Customer\" : [ {\n" +
                "          \"cus:MemberInfo\" : {\n" +
                "            \"cus:SourceSystem\" : \"Portal\"\n" +
                "          }\n" +
                "        }\n" +
                "      ],\n" +
                "      \"crm:MemberCard\" : [ {\n" +
                "          \"mc:CardInfo\" : {\n" +
                "            \"mc:CardNo\" : \""+cardNo+"\"\n" +
                "          }\n" +
                "        }\n" +
                "      ],\n" +
                "      \"crm:loyaltyService\" : {\n" +
                "        \"lts:common\" : {\n" +
                "          \"lts:POS\" : {\n" +
                "            \"pos:OutletCode\" : \"70-207\",\n" +
                "            \"pos:CashierID\" : \"0\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";

        return requestBody;
    }

    public String getPOSCreateMemberRequestBodyWithUniqueValues(String TitleCode, String GenderCode, String EmailAddressText, String ContactNumberText, String Mobile1AreaCode, String FirstName, String LastName, String cardNo) {

        String requestBody ="{\n" +
                "  \"CRM\" : {\n" +
                "  \"crm:CRM\" : {\n" +
                "    \"crm:Customer\" : [ {\n" +
                "      \"cus:MemberInfo\" : {\n" +
                "        \"cus:TitleCode\" : \""+TitleCode+"\",\n" +
                "        \"cus:GenderCode\" : \""+GenderCode+"\",\n" +
                "        \"cus:EmailAddressText\" : \""+EmailAddressText+"\",\n" +
                "        \"cus:ContactNumberText\" : \""+ContactNumberText+"\",\n" +
                "        \"cus:CardPickupMethod\":\"In Store\",\n" +
                "        \"cus:Mobile1AreaCode\" : \""+Mobile1AreaCode+"\",\n" +
                "        \"cus:CardTier\":\"LOYAL T\",\n" +
                "        \"cus:IsContactable\":\"1\",\n" +
                "        \"cus:FirstNameNative\" : \"Test Automation\",\n" +
                "        \"cus:FirstName\":\""+FirstName+"\",\n" +
                "        \"cus:LastName\" : \""+LastName+"\",\n" +
                "        \"cus:CountryNameText\" : \"Singapore\",\n" +
                "        \"cus:RegistrationDivisionCode\" : \"58\",\n" +
                "        \"cus:RegistrationLocationID\" : \"451\",\n" +
                "        \"cus:SpokenLanguage\" : \"English\",\n" +
                "        \"cus:SourceSystem\" : \"POS\",\n" +
                "        \"cus:AgeRange\" : \"Below 20\",\n" +
                "        \"cus:CreationUserID\" : \"Admin\",\n" +
                "        \"cus:CustomerRegistrationDatetime\" : \"2015-09-28T00:00:00\"\n" +
                "      }\n" +
                "    }\n" +
                "    ],\n" +
                "    \"crm:MemberCard\" : [ {\n" +
                "      \"mc:CardInfo\" : {\n" +
                "        \"mc:CardNo\" : \""+cardNo+"\"\n"  +
                "      }\n" +
                "    } ],\n" +
                "    \"crm:loyaltyService\" : {\n" +
                "      \"lts:common\" : {\n" +
                "        \"lts:POS\" : {\n" +
                "          \"pos:OutletCode\" : \"0\",\n" +
                "          \"pos:CashierID\" : \"Cashier001\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}\n" +
                "}";
        return requestBody;
    }

    public String getPOSsearchMemberRequestBodyForAnyCard(String cardNo) throws Exception {
        String requestBody = "{\"CRM\" : {\n" +
                "  \"crm:CRM\" : {\n" +
                "  \"crm:Customer\" : [ {\n" +
                "    \"cus:MemberInfo\" : {\n" +
                "      \"cus:MemberID\" : \"\",\n" +
                "      \"cus:EmailAddressText\" : \"\",\n" +
                "      \"cus:NationalityCode\" : \"\",\n" +
                "      \"cus:ContactNumberText\" : \"\",\n" +
                "      \"cus:FirstName\" : \"\",\n" +
                "      \"cus:LastName\" : \"\",\n" +
                "      \"cus:RegistrationDivisionCode\" : \"\",\n" +
                "      \"cus:NRIC\" : \"\",\n" +
                "      \"cus:Passport\" : \"\",\n" +
                "      \"cus:ContactDetails\" : [\n" +
                "        {\n" +
                "          \"cus:ContactType\" : \"WeChat\",\n" +
                "          \"cus:ContactDetail\" : \"weChatID\"\n" +
                "        }\n" +
                "      ]\n" +
                "\n" +
                "    }\n" +
                "  } ],\n" +
                "  \"crm:MemberCard\" : [ {\n" +
                "    \"mc:CardInfo\" : {\n" +
                "      \"mc:CardNo\" : \""+cardNo+"\"\n" +
                "    }\n" +
                "  } ],\n" +
                "  \"crm:loyaltyService\" : {\n" +
                "    \"lts:request\" : {\n" +
                "      \"lts:Command\" : \"DFS MEMBER ENQUIRY\",\n" +
                "      \"lts:DB\" : \"DFS\",\n" +
                "      \"lts:EnquiryCode\" : \"CRM\",\n" +
                "      \"lts:RequestDynamicFieldLists\" : {\n" +
                "        \"lts:DynamicField\" : [ {\n" +
                "          \"@Name\" : \"AdditionalInfo\"\n" +
                "        } ]\n" +
                "      },\n" +
                "      \"lts:RequestDynamicColumnLists\" : {\n" +
                "        \"lts:DynamicColumn\" : [ {\n" +
                "          \"@Name\" : \"ColumnName\"\n" +
                "        } ]\n" +
                "      }\n" +
                "    },\n" +
                "    \"lts:common\" : {\n" +
                "      \"lts:TransactionType\" : \"GenericVO.DFSMemberEnquiry\",\n" +
                "      \"lts:POS\" : {\n" +
                "        \"pos:OutletCode\" : \"70-207\",\n" +
                "        \"pos:PosID\" : \"PWHKGP2105\",\n" +
                "        \"pos:CashierID\" : \"183369\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}\n" +
                "}\n" +
                "}";
        return requestBody;
    }

    public String getPOSsearchMemberRequestBodyWithMultipleCriteria(String FirstName, String LastName,String EmailAddressText, String ContactNumberText) {
        String requestBody = "{\"CRM\" : {\n" +
                "  \"crm:CRM\" : {\n" +
                "  \"crm:Customer\" : [ {\n" +
                "    \"cus:MemberInfo\" : {\n" +
                "      \"cus:MemberID\" : \"\",\n" +
                "      \"cus:EmailAddressText\" : \""+EmailAddressText+"\",\n" +
                "      \"cus:NationalityCode\" : \"\",\n" +
                "      \"cus:ContactNumberText\" : \""+ContactNumberText+"\",\n" +
                "      \"cus:FirstName\" : \""+FirstName+"\",\n" +
                "      \"cus:LastName\" : \""+LastName+"\",\n" +
                "      \"cus:RegistrationDivisionCode\" : \"\",\n" +
                "      \"cus:NRIC\" : \"\",\n" +
                "      \"cus:Passport\" : \"\",\n" +
                "      \"cus:ContactDetails\" : [\n" +
                "        {\n" +
                "          \"cus:ContactType\" : \"WeChat\",\n" +
                "          \"cus:ContactDetail\" : \"weChatID\"\n" +
                "        }\n" +
                "      ]\n" +
                "\n" +
                "    }\n" +
                "  } ],\n" +
                "  \"crm:MemberCard\" : [ {\n" +
                "    \"mc:CardInfo\" : {\n" +
                "      \"mc:CardNo\" : \"\"\n" +
                "    }\n" +
                "  } ],\n" +
                "  \"crm:loyaltyService\" : {\n" +
                "    \"lts:request\" : {\n" +
                "      \"lts:Command\" : \"DFS MEMBER ENQUIRY\",\n" +
                "      \"lts:DB\" : \"DFS\",\n" +
                "      \"lts:EnquiryCode\" : \"CRM\",\n" +
                "      \"lts:RequestDynamicFieldLists\" : {\n" +
                "        \"lts:DynamicField\" : [ {\n" +
                "          \"@Name\" : \"AdditionalInfo\"\n" +
                "        } ]\n" +
                "      },\n" +
                "      \"lts:RequestDynamicColumnLists\" : {\n" +
                "        \"lts:DynamicColumn\" : [ {\n" +
                "          \"@Name\" : \"ColumnName\"\n" +
                "        } ]\n" +
                "      }\n" +
                "    },\n" +
                "    \"lts:common\" : {\n" +
                "      \"lts:TransactionType\" : \"GenericVO.DFSMemberEnquiry\",\n" +
                "      \"lts:POS\" : {\n" +
                "        \"pos:OutletCode\" : \"70-207\",\n" +
                "        \"pos:PosID\" : \"PWHKGP2105\",\n" +
                "        \"pos:CashierID\" : \"183369\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}\n" +
                "}\n" +
                "}";
        return requestBody;
    }

    public String getETRcreateMemberRequestBodyForAnyCard(String cardNo) {
        String requestBody = "{\n" +
                "  \"CRM\" : {\n" +
                "    \"crm:CRM\" : {\n" +
                "      \"crm:Customer\" : [ {\n" +
                "          \"cus:MemberInfo\" : {\n" +
                "            \"cus:TitleCode\" : \"Mr.\",\n" +
                "            \"cus:GenderCode\" : \"M\",\n" +
                "            \"cus:EmailAddressText\" : \"roshan@gmail.com\",\n" +
                "            \"cus:ContactNumberText\" : \"9789034311\",\n" +
                "            \"cus:IsInvalidEmail\" : \"0\",\n" +
                "            \"cus:IsEmptyEmail\" : \"0\",\n" +
                "            \"cus:FirstName\" : \"Roshan\",\n" +
                "            \"cus:LastName\" : \"Ranasinghe\",\n" +
                "            \"cus:NativeSalutation\" : \"\",\n" +
                "            \"cus:Mobile1AreaCode\" : \"91\",\n" +
                "            \"cus:ValidMobileNo1\" : \"1\",\n" +
                "            \"cus:EnglishCountry\" : \"Taiwan\",\n" +
                "            \"cus:SpokenLanguage\" : \"English\",\n" +
                "            \"cus:IsContactable\" : \"1\",\n" +
                "            \"cus:CardTier\" : \"LOYAL T\",\n" +
                "            \"cus:CardPickupMethod\" : \"In Store\",\n" +
                "            \"cus:SourceSystem\" : \"eTR\",\n" +
                "            \"cus:Override\" : \"true\",\n" +
                "            \"cus:RegistrationDivisionCode\" : \"58\",\n" +
                "            \"cus:RegistrationLocationID\" : \"451\",\n" +
                "            \"cus:CustomerRegistrationDatetime\" : \"2018-08-07 14:17:12.820\",\n" +
                "            \"cus:AgeRange\" : \"55-59\"\n" +
                "          }\n" +
                "      }\n" +
                "      ],\n" +
                "      \"crm:MemberCard\" : [ {\n" +
                "          \"mc:CardInfo\" : {\n" +
                "            \"mc:CardNo\" : \""+cardNo+"\",\n" +
                "            \"mc:MembershipCardTypeCode\" : \"101\"\n" +
                "          }\n" +
                "      }\n" +
                "      ],\n" +
                "      \"crm:loyaltyService\" : {\n" +
                "        \"lts:common\" : {\n" +
                "          \"lts:POS\" : {\n" +
                "            \"pos:OutletCode\" : \"0\",\n" +
                "            \"pos:CashierID\" : \"0\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
        return requestBody;
    }

    public String getPOScreateMemberRequestBodyForAnyCard(String cardNo) {
        String requestBody ="{\n" +
                "  \"CRM\" : {\n" +
                "  \"crm:CRM\" : {\n" +
                "    \"crm:Customer\" : [ {\n" +
                "      \"cus:MemberInfo\" : {\n" +
                "        \"cus:TitleCode\" : \"Mr.\",\n" +
                "        \"cus:GenderCode\" : \"M\",\n" +
                "        \"cus:EmailAddressText\" : \"testautomation@gmail.com\",\n" +
                "        \"cus:ContactNumberText\" : \"86707924\",\n" +
                "        \"cus:CardPickupMethod\":\"In Store\",\n" +
                "        \"cus:Mobile1AreaCode\" : \"65\",\n" +
                "        \"cus:CardTier\":\"LOYAL T\",\n" +
                "        \"cus:IsContactable\":\"1\",\n" +
                "        \"cus:FirstNameNative\" : \"Test Automation\",\n" +
                "        \"cus:FirstName\":\"TestAutomationPOS\",\n" +
                "        \"cus:LastName\" : \"DoNotUse\",\n" +
                "        \"cus:CountryNameText\" : \"Singapore\",\n" +
                "        \"cus:RegistrationDivisionCode\" : \"58\",\n" +
                "        \"cus:RegistrationLocationID\" : \"451\",\n" +
                "        \"cus:SpokenLanguage\" : \"English\",\n" +
                "        \"cus:SourceSystem\" : \"POS\",\n" +
                "        \"cus:AgeRange\" : \"Below 20\",\n" +
                "        \"cus:CreationUserID\" : \"Admin\",\n" +
                "        \"cus:CustomerRegistrationDatetime\" : \"2015-09-28T00:00:00\"\n" +
                "      }\n" +
                "    }\n" +
                "    ],\n" +
                "    \"crm:MemberCard\" : [ {\n" +
                "      \"mc:CardInfo\" : {\n" +
                "        \"mc:CardNo\" : \""+cardNo+"\"\n"  +
                "      }\n" +
                "    } ],\n" +
                "    \"crm:loyaltyService\" : {\n" +
                "      \"lts:common\" : {\n" +
                "        \"lts:POS\" : {\n" +
                "          \"pos:OutletCode\" : \"0\",\n" +
                "          \"pos:CashierID\" : \"Cashier001\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}\n" +
                "}";
        return requestBody;
    }

    public String getDSAcreateMemberRequestBodyForAnyCard(String cardNo) {
        String requestBody ="{\n" +
                "  \"CRM\": {\n" +
                "    \"crm:CRM\": {\n" +
                "      \"crm:Customer\": [\n" +
                "        {\n" +
                "          \"cus:MemberInfo\": {\n" +
                "            \"cus:TitleCode\": \"Mr.\",\n" +
                "            \"cus:GenderCode\": \"M\",\n" +
                "            \"cus:EmailAddressText\": \"testautomation@gmail.com\",\n" +
                "            \"cus:PostalCode\": \"A2346Z\",\n" +
                "            \"cus:CityNameText\": \"Chongqing\",\n" +
                "            \"cus:CountryNameText\": \"China\",\n" +
                "            \"cus:ContactNumberText\": \"8965379\",\n" +
                "            \"cus:IsInvalidAddress\": \"0\",\n" +
                "            \"cus:IsInvalidEmail\": \"0\",\n" +
                "            \"cus:IsEmptyEmail\": \"0\",\n" +
                "            \"cus:EmailUnwillingToProvide\": \"1\",\n" +
                "            \"cus:FirstName\": \"TestAutomationDSA\",\n" +
                "            \"cus:FirstNameNative\": \"这咏\",\n" +
                "            \"cus:LastName\": \"CodeCleanup\",\n" +
                "            \"cus:LastNameNative\": \"王\",\n" +
                "            \"cus:BirthDate\": \"01\",\n" +
                "            \"cus:BirthMonth\": \"10\",\n" +
                "            \"cus:RegistrationDivisionCode\": \"58\",\n" +
                "            \"cus:RegistrationLocationID\": \"451\",\n" +
                "            \"cus:MailingAddress2\": \"荃加城12A\",\n" +
                "            \"cus:ZipCode\": \"A2346Z\",\n" +
                "            \"cus:CorrCityNameText\": \"\",\n" +
                "            \"cus:CorrStateNameText\": \"\",\n" +
                "            \"cus:MailingAddress1\": \"A2346Z 中国重庆重庆\",\n" +
                "            \"cus:MailingAddress3\": \"地里街十八号\",\n" +
                "            \"cus:ResCityNameText\": \"Chongqing\",\n" +
                "            \"cus:ResStateNameText\": \"Chongqing\",\n" +
                "            \"cus:EnglishCountry\": \"China\",\n" +
                "            \"cus:ValidMobileNo1\": \"1\",\n" +
                "            \"cus:SpokenLanguage\": \"Simplified Chinese\",\n" +
                "            \"cus:IsContactable\": \"0\",\n" +
                "            \"cus:CustomerRegistrationDatetime\": \"2018-09-28 09:57:39.000\",\n" +
                "            \"cus:CustomerLeisureActivity\": \"\",\n" +
                "            \"cus:CustomerShoppingPreference\": \"\",\n" +
                "            \"cus:CustomerPreferredBrands\": \"\",\n" +
                "            \"cus:LeisureActivitiesMultiple\": \"Art & Culture|Sports\",\n" +
                "            \"cus:ShoppingPreferencesMultiple\": \"Beauty|Watches\",\n" +
                "            \"cus:PreferredBrandsMultiple\": \"1003|1009|1105\",\n" +
                "            \"cus:AgeRange\": \"35-39\",\n" +
                "            \"cus:MarketingSource\": \"Facebook\",\n" +
                "            \"cus:ZipCodeValidFlag\": \"1\",\n" +
                "            \"cus:CardTier\": \"PRESTIGE RUBY\",\n" +
                "            \"cus:CardPickupMethod\": \"By mail\",\n" +
                "            \"cus:IpadID\": \"AHKGISUN999\",\n" +
                "            \"cus:StaffID\": \"5432177\",\n" +
                "            \"cus:MarketingSourceOthers\": \"\",\n" +
                "            \"cus:Mobile1AreaCode\": \"1268\",\n" +
                "            \"cus:CreationDate\": \"2018-09-28T13:43:39\",\n" +
                "            \"cus:UpdateDate\": \"2018-09-28T13:43:39\",\n" +
                "            \"cus:CreationUserID\": \"5432199\",\n" +
                "            \"cus:UpdateUserID\": \"5432188\",\n" +
                "            \"cus:SourceSystem\": \"DSA\",\n" +
                "            \"cus:Override\": \"true\",\n" +
                "            \"cus:ContactDetails\": [\n" +
                "              {\n" +
                "                \"cus:ContactType\": \"WeChat\",\n" +
                "                \"cus:ContactDetail\": \"testautomation@gmail.com\"\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        }\n" +
                "      ],\n" +
                "      \"crm:MemberCard\": [\n" +
                "        {\n" +
                "          \"mc:CardInfo\": {\n" +
                "            \"mc:CardNo\": \""+cardNo+"\"\n" +
                "          }\n" +
                "        }\n" +
                "      ],\n" +
                "      \"crm:loyaltyService\": {\n" +
                "        \"lts:common\": {\n" +
                "          \"lts:POS\": {\n" +
                "            \"pos:OutletCode\": \"0\",\n" +
                "            \"pos:CashierID\": \"0\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
        return requestBody;
    }

    public void createMemberDSA(String requestBody) throws Exception {

        Response response = null;
        int statusCode = 0;
        String baseURL="";

        readTestData data = new readTestData();

        //reading the environment from the system properties
        if(System.getProperty("Environment").equalsIgnoreCase("Preprod")) {
            baseURL = data.readTestData("Preprod_DSA_External");
        } else if(System.getProperty("Environment").equalsIgnoreCase("QACore2")){
            baseURL = data.readTestData("QACore2_DSA_External");
        }

        String endpointURL = data.readTestData("DSA_Endpoint_URL");
        String authorization = data.readTestData("DSA_Authorization");

        String URL = baseURL + endpointURL;
        System.out.println(URL);

        try {
            response = given()
                    .headers("Content-Type", "application/json", "Authorization", authorization)
                    .body(requestBody)
                    .when()
                    .post(URL)
                    .then()
                    .contentType(ContentType.JSON)
                    .extract().response();

            statusCode = response.getStatusCode();

            //PRINTING THE RESPONSE IN CUCUMBER REPORT
            _scenario.write("=============== The API Response returned for DSA create member is as follows ===================");
            _scenario.write(response.asString());
            _scenario.write("=============== End of API Response body ==========================");


            System.out.println("Status code : "+ statusCode);
            _scenario.write("The API response Payload"+"\n"+response.asString());

        } catch (Exception e) {
            System.out.println("TEST FAILED INTENTIONALLY ! POST call to Salesforce was not successful. The exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY ! POST call to Salesforce was not successful. The exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY ! POST call to Salesforce was not successful. The exception - "+e.getMessage());
        }

        try {
            Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        } catch (AssertionError e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful with status code"+ response.getStatusCode() +" The actual status is - "+e);
            _scenario.write("The test was failed since POST call to Salesforce was not successful with status code"+ response.getStatusCode() +" The actual status is - "+e);
            Assert.fail();
        }

        //EVEN IF STATUS IS 200 OK , STILL VALIDATE IF RETURN MESSAGE IS - SUCCESS
        if(statusCode == HttpStatus.SC_OK && !response.getBody().toString().isEmpty()) {
            apiValidate = new validateAPIreturnMessage();

            try {
                apiValidate.validateAPIReturnMessage(_scenario,response);
            } catch (ParseException e) {
                System.out.println("ParseException occurred while validating the API return message - "+e);
                _scenario.write("ParseException occurred while validating the API return message - "+e);
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("IOException occurred while validating the API return message - "+e);
                _scenario.write("IOException occurred while validating the API return message - "+e);
            }
        }

    }

    public void createMemberETR(String requestBody) throws Exception {

        Response response = null;
        int statusCode = 0;
        String baseURL="";

        readTestData data = new readTestData();

        //reading the environment from the system properties
        if(System.getProperty("Environment").equalsIgnoreCase("Preprod")) {
            baseURL = data.readTestData("Preprod_ETR_External");
            System.out.println("Execution environment is - Preprod");
        } else if(System.getProperty("Environment").equalsIgnoreCase("QACore2")){
            baseURL = data.readTestData("QACore2_ETR_External");
            System.out.println("Execution environment is -QACore2");
        }

        String endpointURL = data.readTestData("ETR_Endpoint_URL").trim();
        String authorization = data.readTestData("ETR_Authorization").trim();

        String URL = baseURL + endpointURL;
        System.out.println(URL);

        try {
            response = given()
                    .headers("Content-Type", "application/json")      // "Authorization", authorization
                    .body(requestBody)
                    .auth().basic("automation","~g59RcHZy3X]MfpP")
                    .when()
                    .post(URL)
                    .then()
                    .contentType(ContentType.JSON)
                    .extract().response();

            statusCode = response.getStatusCode();

            //priting out the response on cucumber report
            _scenario.write("=============== The API response body for ETR create member is as follows ===================");
            _scenario.write(response.asString());
            _scenario.write("================================ End of API response body ===================================");

            System.out.println("Status code : "+ statusCode);
        } catch (Exception e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful");
            _scenario.write("The test was failed since POST call to Salesforce was not successful");
            Assert.fail();
        }

        try {
            Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        } catch (AssertionError e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful with status code"+ response.getStatusCode() +" The actual status is - "+e);
            _scenario.write("The test was failed since POST call to Salesforce was not successful with status code"+ response.getStatusCode() +" The actual status is - "+e);
            Assert.fail();
        }

//IF STATUS IS 200 OK , then extract values from the response
        if(statusCode == HttpStatus.SC_OK && !response.getBody().toString().isEmpty()) {
            apiValidate = new validateAPIreturnMessage();

            try {
                apiValidate.validateAPIReturnMessage(_scenario,response);
            } catch (ParseException e) {
                System.out.println("ParseException occurred while validating the API return message - "+e);
                _scenario.write("ParseException occurred while validating the API return message - "+e);
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("IOException occurred while validating the API return message - "+e);
                _scenario.write("IOException occurred while validating the API return message - "+e);
            }
        }

    }

    public Response getResponseOfCreateMemberETR(String requestBody) throws FileNotFoundException {
        Response response = null;
        int statusCode = 0;
        String baseURL="";

        readTestData data = new readTestData();

        //reading the environment from the system properties
        if(System.getProperty("Environment").equalsIgnoreCase("Preprod")) {
            baseURL = data.readTestData("Preprod_ETR_External");
            System.out.println("Execution environment is - Preprod");
        } else if(System.getProperty("Environment").equalsIgnoreCase("QACore2")){
            baseURL = data.readTestData("QACore2_ETR_External");
            System.out.println("Execution environment is -QACore2");
        }

        String endpointURL = data.readTestData("ETR_Endpoint_URL").trim();
        String authorization = data.readTestData("ETR_Authorization").trim();

        String URL = baseURL + endpointURL;
        System.out.println(URL);

        try {
            response = given()
                    .headers("Content-Type", "application/json")      // "Authorization", authorization
                    .body(requestBody)
                    .auth().basic("automation","~g59RcHZy3X]MfpP")
                    .when()
                    .post(URL)
                    .then()
                    //.contentType(ContentType.JSON)
                    .extract().response();

            statusCode = response.getStatusCode();

            //priting out the response on cucumber report
            _scenario.write("=============== The API response body for ETR create member is as follows ===================");
            _scenario.write(response.asString());
            _scenario.write("================================ End of API response body ===================================");

            System.out.println("Status code : "+ statusCode);
        } catch (Exception e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful. Exception -- "+e.getMessage());
            _scenario.write("The test was failed since POST call to Salesforce was not successful");
            Assert.fail();
        }

        return response;
    }

    public void updateMemberETR(String requestBody) throws Exception {
        Response response = null;
        int statusCode = 0;
        String baseURL="";

        readTestData data = new readTestData();

        //reading the environment from the system properties
        if(System.getProperty("Environment").equalsIgnoreCase("Preprod")) {
            baseURL = data.readTestData("Preprod_ETR_External");
            System.out.println("Execution environment is - Preprod");
        } else if(System.getProperty("Environment").equalsIgnoreCase("QACore2")){
            baseURL = data.readTestData("QACore2_ETR_External");
            System.out.println("Execution environment is -QACore2");
        }

        String endpointURL = data.readTestData("ETR_UpdateMember_Endpoint_URL").trim();
        //String authorization = data.readTestData("ETR_Authorization").trim();

        String URL = baseURL + endpointURL;
        System.out.println(URL);

        try {
            response = given()
                    .headers("Content-Type", "application/json")      // "Authorization", authorization
                    .body(requestBody)
                    .auth().basic("automation","~g59RcHZy3X]MfpP")
                    .when()
                    .post(URL)
                    .then()
                    .contentType(ContentType.JSON)
                    .extract().response();

            statusCode = response.getStatusCode();

            //PRINTING THE update response IN CUCUMBER REPORT
            _scenario.write("=============== The API response body for ETR Update member is as follows ===================");
            _scenario.write(response.asString());
            _scenario.write("================================ End of API response body ===================================");


            System.out.println("Status code : "+ statusCode);
        } catch (Exception e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful");
            _scenario.write("The test was failed since POST call to Salesforce was not successful");
            Assert.fail();
        }

        try {
            Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        } catch (AssertionError e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful with status code"+ response.getStatusCode() +" The actual status is - "+e);
            _scenario.write("The test was failed since POST call to Salesforce was not successful with status code"+ response.getStatusCode() +" The actual status is - "+e);
            Assert.fail();
        }

        //IF STATUS IS 200 OK , then extract values from the response
        if(statusCode == HttpStatus.SC_OK && !response.getBody().toString().isEmpty()) {
            apiValidate = new validateAPIreturnMessage();

            try {
                apiValidate.validateAPIReturnMessage(_scenario,response);
            } catch (ParseException e) {
                System.out.println("ParseException occurred while validating the API return message - "+e);
                _scenario.write("ParseException occurred while validating the API return message - "+e);
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("IOException occurred while validating the API return message - "+e);
                _scenario.write("IOException occurred while validating the API return message - "+e);
            }
        }


    }


    public void createMemberPOS(String requestBody) throws Exception {

        Response response = null;
        int statusCode = 0;
        String baseURL="";

        readTestData data = new readTestData();

        //reading the environment from the system properties
        if(System.getProperty("Environment").equalsIgnoreCase("Preprod")) {
            baseURL = data.readTestData("Preprod_POS_External");
        } else if(System.getProperty("Environment").equalsIgnoreCase("QACore2")){
            baseURL = data.readTestData("QACore2_POS_External");
        }

        String endpointURL = data.readTestData("POS_Endpoint_URL");
        String authorization = data.readTestData("POS_Authorization");

        String URL = baseURL + endpointURL;
        System.out.println(URL);

        try {
            response = given()
                    .headers("Content-Type", "application/json", "Authorization", authorization)
                    .body(requestBody)
                    .when()
                    .post(URL)
                    .then()
                    .contentType(ContentType.JSON)
                    .extract().response();

            statusCode = response.getStatusCode();

            //priting out the response on cucumber report
            _scenario.write("=============== The API Response body for POS create member is as follows ===================");
            _scenario.write(response.asString());
            _scenario.write("================================ End of API Response body ===================================");

            System.out.println("Status code : "+ statusCode);
        } catch (Exception e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful");
            _scenario.write("The test was failed since POST call to Salesforce was not successful");
            Assert.fail();
        }

        try {
            Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        } catch (AssertionError e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful with status code"+ response.getStatusCode() +" The actual status is - "+e);
            _scenario.write("The test was failed since POST call to Salesforce was not successful with status code"+ response.getStatusCode() +" The actual status is - "+e);
            Assert.fail();
        }

        //EVEN IF STATUS IS 200 OK , STILL VALIDATE IF RETURN MESSAGE IS - SUCCESS
        if(statusCode == HttpStatus.SC_OK && !response.getBody().toString().isEmpty()) {
            apiValidate = new validateAPIreturnMessage();

            try {
                apiValidate.validateAPIReturnMessage(_scenario,response);
            } catch (ParseException e) {
                System.out.println("ParseException occurred while validating the API return message - "+e);
                _scenario.write("ParseException occurred while validating the API return message - "+e);
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("IOException occurred while validating the API return message - "+e);
                _scenario.write("IOException occurred while validating the API return message - "+e);
            }
        }

    }

    public Response getResponseFromCreateMemberPOS(String requestBody) throws FileNotFoundException {
        Response response = null;
        int statusCode = 0;
        String baseURL="";

        readTestData data = new readTestData();

        //reading the environment from the system properties
        if(System.getProperty("Environment").equalsIgnoreCase("Preprod")) {
            baseURL = data.readTestData("Preprod_POS_External");
        } else if(System.getProperty("Environment").equalsIgnoreCase("QACore2")){
            baseURL = data.readTestData("QACore2_POS_External");
        }

        String endpointURL = data.readTestData("POS_Endpoint_URL");
        String authorization = data.readTestData("POS_Authorization");

        String URL = baseURL + endpointURL;
        System.out.println(URL);

        try {
            response = given()
                    .headers("Content-Type", "application/json", "Authorization", authorization)
                    .body(requestBody)
                    .when()
                    .post(URL)
                    .then()
                    //.contentType(ContentType.JSON)
                    .extract().response();

            statusCode = response.getStatusCode();

            //priting out the response on cucumber report
            _scenario.write("=============== The API Response body for POS create member is as follows ===================");
            _scenario.write(response.asString());
            _scenario.write("================================ End of API Response body ===================================");

            System.out.println("Status code : "+ statusCode);
        } catch (Exception e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful");
            _scenario.write("The test was failed since POST call to Salesforce was not successful");
            Assert.fail();
        }
        return response;
    }

    public Response getResponseFromCreateMemberDSA(String requestBody) throws FileNotFoundException {
        Response response = null;
        int statusCode = 0;
        String baseURL="";

        readTestData data = new readTestData();

        //reading the environment from the system properties
        if(System.getProperty("Environment").equalsIgnoreCase("Preprod")) {
            baseURL = data.readTestData("Preprod_DSA_External");
        } else if(System.getProperty("Environment").equalsIgnoreCase("QACore2")){
            baseURL = data.readTestData("QACore2_DSA_External");
        }

        String endpointURL = data.readTestData("DSA_Endpoint_URL");
        String authorization = data.readTestData("DSA_Authorization");

        String URL = baseURL + endpointURL;
        System.out.println(URL);

        try {
            response = given()
                    .headers("Content-Type", "application/json", "Authorization", authorization)
                    .body(requestBody)
                    .when()
                    .post(URL)
                    .then()
                    //.contentType(ContentType.JSON)
                    .extract().response();

            statusCode = response.getStatusCode();

            //PRINTING THE RESPONSE IN CUCUMBER REPORT
            _scenario.write("=============== The API Response returned for DSA create member is as follows ===================");
            _scenario.write(response.asString());
            _scenario.write("=============== End of API Response body ==========================");


            System.out.println("Status code : "+ statusCode);
            _scenario.write("The API response Payload"+"\n"+response.asString());

        } catch (Exception e) {
            System.out.println("TEST FAILED INTENTIONALLY ! POST call to Salesforce was not successful. The exception - "+e.getMessage());
            _scenario.write("TEST FAILED INTENTIONALLY ! POST call to Salesforce was not successful. The exception - "+e.getMessage());
            Assert.fail("TEST FAILED INTENTIONALLY ! POST call to Salesforce was not successful. The exception - "+e.getMessage());
        }

        return response;
    }

    public void searcheMemberPOS(String requestBody) throws Exception {

        Response response = null;
        int statusCode = 0;
        String baseURL="";

        readTestData data = new readTestData();

        //reading the environment from the system properties
        if(System.getProperty("Environment").equalsIgnoreCase("Preprod")) {
            baseURL = data.readTestData("Preprod_POS_External");
        } else if(System.getProperty("Environment").equalsIgnoreCase("QACore2")){
            baseURL = data.readTestData("QACore2_POS_External");
        }

        String endpointURL = data.readTestData("POS_SearchMember_URL");
        String authorization = data.readTestData("POS_Authorization");

        String URL = baseURL + endpointURL;
        System.out.println(URL);


        try {
            response = given()
                    .headers("Content-Type", "application/json", "Authorization", authorization)
                    .body(requestBody)
                    .when()
                    .post(URL)
                    .then()
                    .contentType(ContentType.JSON)
                    .extract().response();

            statusCode = response.getStatusCode();

            System.out.println("Status code : "+ statusCode);
            _scenario.write("Status code : "+ statusCode);

        } catch (Exception e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful");
            _scenario.write("The test was failed since POST call to Salesforce was not successful");
            Assert.fail();
        }

        try {
            Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        } catch (AssertionError e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful with status code"+ response.getStatusCode() +" The actual status is - "+e);
            _scenario.write("The test was failed since POST call to Salesforce was not successful with status code"+ response.getStatusCode() +" The actual status is - "+e);
            Assert.fail();
        }


        //IF STATUS IS 200 OK , then extract values from the response
            if(statusCode == HttpStatus.SC_OK && !response.getBody().toString().isEmpty()) {

                //Converting the response body into a String buffer
                BufferedReader br = new BufferedReader(new InputStreamReader(response.getBody().asInputStream()));
                String inputLine;
                StringBuffer resp = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    resp.append(inputLine);
                }
                br.close();

                //parse the response into a JSON object

                JSONParser jsonParser = new JSONParser();
                Object object = jsonParser.parse(resp.toString());
                jsonObject = (JSONObject) object;

                //Navigating through the json nodes
                JSONObject crmCRM = null;
                JSONArray crmCustomer = null;
                try {
                    JSONObject crm = (JSONObject) jsonObject.get("CRM");
                    crmCRM = (JSONObject) crm.get("crm:CRM");
                    crmCustomer = (JSONArray) crmCRM.get("crm:Customer");
                } catch (Exception e) {
                    System.out.println("The test was failed since the API response does not contain 'crm.Customer' Block. This means that expected structure of response have changed. Actual exception - "+e);
                    _scenario.write("The test was failed since the API response does not contain 'crm.Customer' Block. This means that expected structure of response have changed. Actual exception - "+e);
                    fail();
                }

                //capturing the return message
                //################# Start of LoyaltyServices block ###############
                JSONObject respInfo = null;
                try {
                    JSONObject crmLoyaltyService = (JSONObject) crmCRM.get("crm:loyaltyService");
                    respInfo = (JSONObject) crmLoyaltyService.get("lts:response");
                } catch (Exception e) {
                    System.out.println("The test was failed since the API response does not contain 'crm.loyaltyService' Block. This means that expected structure of response have changed. Actual exception - " + e);
                    _scenario.write("The test was failed since the API response does not contain 'crm.loyaltyService' Block. This means that expected structure of response have changed. Actual exception - " + e);
                    fail();
                }
                //getting values from lts:response block
//            for (int k = 0; k < crmLoyaltyService.size(); k++) {
//                JSONObject crmLoyalty = (JSONObject) crmLoyaltyService.get(k);
//                MemberInfoJsonObj = (JSONObject) crmLoyalty.get("lts:response");

                //getting the lts:ReturnStatus
                try {
                    ReturnStatus = respInfo.get("lts:ReturnStatus").toString();
                    if (ReturnStatus == null) {
                        ReturnStatus = "";
                    }
                } catch (Exception e) {
                    if (ReturnStatus == null) {
                        ReturnStatus = "";
                    }
                }

                //getting the lts:ReturnMessage
                try {
                    ReturnMessage = respInfo.get("lts:ReturnMessage").toString();
                    if (ReturnMessage == null) {
                        ReturnMessage = "";
                    }
                } catch (Exception e) {
                    if (ReturnMessage == null) {
                        ReturnMessage = "";
                    }
                }

                //################ End of LoyaltyServices Block ###################

                //getting values from crm:Customer block
                for (int k = 0; k < crmCustomer.size(); k++) {
                     JSONObject crmCustomer2 = (JSONObject) crmCustomer.get(k);
                     MemberInfoJsonObj = (JSONObject) crmCustomer2.get("cus:MemberInfo");

                //Setting values to string variables.
                    //MemberID = Card Number
                    try {
                        MemberID = MemberInfoJsonObj.get("cus:MemberID").toString();
                        if(MemberID==null){
                            MemberID="";
                        }
                    } catch (Exception e) {
                        if(MemberID==null){
                            MemberID="";
                        }
                    }

                    //First Name

                    try {
                        FirstName = MemberInfoJsonObj.get("cus:FirstName").toString();
                        if(FirstName==null){
                            FirstName="";
                        }
                    } catch (Exception e) {
                        if(FirstName==null){
                            FirstName="";
                        }
                    }

                    //Last Name
                    try {
                        LastName = MemberInfoJsonObj.get("cus:LastName").toString();
                        if(LastName==null){
                            LastName="";
                        }
                    } catch (Exception e) {
                        if(LastName==null){
                            LastName="";
                        }
                    }

                    //Nationality = SF. Basic information -> Nationality
                    try {
                        NationalityCode = MemberInfoJsonObj.get("cus:NationalityCode").toString();
                        if(NationalityCode==null){
                            NationalityCode="";
                        }
                    } catch (Exception e) {
                        if(NationalityCode==null){
                            NationalityCode="";
                        }
                    }

                    //Mobile Phone
                    try {
                        ContactNumberText = MemberInfoJsonObj.get("cus:ContactNumberText").toString();
                        if(ContactNumberText==null){
                            ContactNumberText="";
                        }
                    } catch (Exception e) {
                        if(ContactNumberText==null){
                            ContactNumberText="";
                        }
                    }

                    //Other Phone 1
                    try {
                        MobileNo2 = MemberInfoJsonObj.get("cus:MobileNo2").toString();
                        if(MobileNo2==null){
                            MobileNo2="";
                        }
                    } catch (Exception e) {
                        if(MobileNo2==null){
                            MobileNo2="";
                        }
                    }


                    //Other Phone 2
                    try {
                        OtherPhone2 = MemberInfoJsonObj.get("cus:ContactNo").toString();
                        if(OtherPhone2==null){
                            OtherPhone2="";
                        }
                    } catch (Exception e) {
                        if(OtherPhone2==null){
                            OtherPhone2="";
                        }
                    }

                    //Location of Residence (Mailing Address)
                    try {
                        CountryNameText = MemberInfoJsonObj.get("cus:CountryNameText").toString();
                        if(CountryNameText==null){
                            CountryNameText="";
                        }
                    } catch (Exception e) {
                        if(CountryNameText==null){
                            CountryNameText="";
                        }
                    }

                    //Email
                    try {
                        EmailAddressText = MemberInfoJsonObj.get("cus:EmailAddressText").toString();
                        if(EmailAddressText==null){
                            EmailAddressText="";
                        }
                    } catch (Exception e) {
                        if(EmailAddressText==null){
                            EmailAddressText="";
                        }
                    }

                    //Member status
                    try {
                        MemberStatus = MemberInfoJsonObj.get("cus:MemberStatus").toString();
                        if(MemberStatus==null){
                            MemberStatus="";
                        }
                    } catch (Exception e) {
                        if(MemberStatus==null){
                            MemberStatus="";
                        }
                    }

                    //Join Date
                    try {
                        JoinedDate = MemberInfoJsonObj.get("cus:JoinedDate").toString().substring(0,10).replaceAll("-","").trim();
                        if(JoinedDate==null){
                            JoinedDate="";
                        }
                    } catch (Exception e) {
                        if(JoinedDate==null){
                            JoinedDate="";
                        }
                    }


                }

                 //getting values from crm:MemberCard block
               // JSONObject crmCRM = (JSONObject) crm.get("crm:CRM");
                JSONArray crmMemberCard = null;
                try {
                    crmMemberCard = (JSONArray) crmCRM.get("crm:MemberCard");
                } catch (Exception e) {
                    System.out.println("The test was failed since the API response does not contain 'crm.MemberCard' Block. This means that expected structure of response have changed. Actual exception - "+e);
                    _scenario.write("The test was failed since the API response does not contain 'crm.MemberCard' Block. This means that expected structure of response have changed. Actual exception - "+e);
                    fail();
                }

                for (int i=0;i<crmMemberCard.size();i++){
                    try {
                        JSONObject crmMemberCard2 = (JSONObject) crmMemberCard.get(i);
                        MemberInfoJsonObj = (JSONObject) crmMemberCard2.get("mc:CardInfo");
                    } catch (Exception e) {
                        System.out.println("The test was failed since the API response does not contain 'mc.CardInfo' Block. This means that expected structure of response have changed. Actual exception - "+e);
                        _scenario.write("The test was failed since the API response does not contain 'mc.CardInfo' Block. This means that expected structure of response have changed. Actual exception - "+e);
                        fail();
                    }

                    //CardNo
                    try {
                        CardNo = MemberInfoJsonObj.get("mc:CardNo").toString();
                        if(CardNo==null){
                            CardNo ="";
                        }
                    } catch (Exception e) {
                        if(CardNo==null){
                            CardNo ="";
                        }
                    }

                    //MembershipCardTierCode
                    try {
                        MembershipCardTierCode = MemberInfoJsonObj.get("mc:MembershipCardTierCode").toString();
                        if(MembershipCardTierCode==null){
                            MembershipCardTierCode ="";
                        }
                    } catch (Exception e) {
                        if(MembershipCardTierCode==null){
                            MembershipCardTierCode ="";
                        }
                    }

                }


            } else  if (statusCode!=HttpStatus.SC_OK){
                System.out.println("POST request Error. Returned the status code "+ statusCode);
                _scenario.write("POST request Error. Returned the status code "+ statusCode);

            } else if (response.getBody().toString().isEmpty() || response.getBody().toString() ==""){
                System.out.println("The response body is null or its empty");
                _scenario.write("The response body is null or its empty");
            }



    }
    
    public void getMemberETR(String requestBody) throws Exception {

        Response response = null;
        int statusCode = 0;
        String baseURL="";

        readTestData data = new readTestData();

        //reading the environment from the system properties
        if(System.getProperty("Environment").equalsIgnoreCase("Preprod")) {
            baseURL = data.readTestData("Preprod_GetMember_External");  // Preprod_GetMember_External    // Preprod_ETR_External
            System.out.println("-------Preprod ---------");
        } else if(System.getProperty("Environment").equalsIgnoreCase("QACore2")){
            baseURL = data.readTestData("QACore2_GetMember_External");  // QACore2_GetMember_External   // QACore2_ETR_External
            System.out.println("-------QaCore2 ---------");
        }

        String endpointURL = data.readTestData("ETR_GetMember_Endpoint_URL");
        String authorization = data.readTestData("ETR_Authorization");

        String URL = baseURL + endpointURL;
        System.out.println(URL);

        System.out.println(requestBody);

        try {
            response = given()
                    .headers("Content-Type", "application/json", "Authorization", authorization) //"Authorization", authorization
                    .body(requestBody)
                    //.auth().basic("automation","~g59RcHZy3X]MfpP")
                    .when()
                    .post(URL)
                    .then()
                    .contentType(ContentType.JSON)
                    .extract().response();

            statusCode = response.getStatusCode();

            //PRINTING THE update response IN CUCUMBER REPORT
            _scenario.write("=============== The API response body for ETR get member is as follows ===================");
            _scenario.write(response.asString());
            _scenario.write("================================ End of API response body ===================================");

            System.out.println("Status code : "+ statusCode);
            _scenario.write("Status code : "+ statusCode);

        }  catch (Exception e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful");
            _scenario.write("The test was failed since POST call to Salesforce was not successful");
            Assert.fail();
        }

        try {
            Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        } catch (AssertionError e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful with status code"+ response.getStatusCode() +" The actual status is - "+e);
            _scenario.write("The test was failed since POST call to Salesforce was not successful with status code"+ response.getStatusCode() +" The actual status is - "+e);
            Assert.fail();
        }

        //IF STATUS IS 200 OK , then extract values from the response
        if(statusCode == HttpStatus.SC_OK && !response.getBody().toString().isEmpty()) {

            //Converting the response body into a String buffer
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getBody().asInputStream()));
            String inputLine;
            StringBuffer resp = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                resp.append(inputLine);
            }
            br.close();

            //parse the response into a JSON object

            JSONParser jsonParser = new JSONParser();
            Object object = jsonParser.parse(resp.toString());
            jsonObject = (JSONObject) object;

            //Navigating through the json nodes
            JSONObject crmCRM = null;
            JSONArray crmCustomer = null;

            //Trying to read CRM-->crm:Customer block
            try {
                JSONObject crm = (JSONObject) jsonObject.get("CRM");
                crmCRM = (JSONObject) crm.get("crm:CRM");

                crmCustomer = (JSONArray) crmCRM.get("crm:Customer");
            } catch (Exception e) {
                System.out.println("The test was failed since the API response does not contain 'crm.Customer' Block. This means that expected structure of response have changed. Actual exception - "+e);
                _scenario.write("The test was failed since the API response does not contain 'crm.Customer' Block. This means that expected structure of response have changed. Actual exception - "+e);
                fail();
            }

            // Validating the Return message from Loyalty service block to see if response is returning success message

            //################# Start of LoyaltyServices block ###############
            JSONObject respInfo = null;
            try {
                JSONObject crmLoyaltyService = (JSONObject) crmCRM.get("crm:loyaltyService");
                respInfo = (JSONObject) crmLoyaltyService.get("lts:response");
            } catch (Exception e) {
                System.out.println("The test was failed since the API response does not contain 'crm.loyaltyService' Block. This means that expected structure of response have changed. Actual exception - " + e);
                _scenario.write("The test was failed since the API response does not contain 'crm.loyaltyService' Block. This means that expected structure of response have changed. Actual exception - " + e);
                fail();
            }
            //getting values from lts:response block
//            for (int k = 0; k < crmLoyaltyService.size(); k++) {
//                JSONObject crmLoyalty = (JSONObject) crmLoyaltyService.get(k);
//                MemberInfoJsonObj = (JSONObject) crmLoyalty.get("lts:response");

            //getting the lts:ReturnStatus
            try {
                ReturnStatus = respInfo.get("lts:ReturnStatus").toString();
                if (ReturnStatus == null) {
                    ReturnStatus = "";
                }
            } catch (Exception e) {
                if (ReturnStatus == null) {
                    ReturnStatus = "";
                }
            }

            //getting the lts:ReturnMessage
            try {
                ReturnMessage = respInfo.get("lts:ReturnMessage").toString();
                if (ReturnMessage == null) {
                    ReturnMessage = "";
                }
            } catch (Exception e) {
                if (ReturnMessage == null) {
                    ReturnMessage = "";
                }
            }

            //################ End of LoyaltyServices Block ###################

            // ##############  Start of Customer block ###############
            //getting values from crm:Customer block
                for (int k = 0; k < crmCustomer.size(); k++) {

                    try {
                        JSONObject crmCustomer2 = (JSONObject) crmCustomer.get(k);
                        MemberInfoJsonObj = (JSONObject) crmCustomer2.get("cus:MemberInfo");
                    } catch (Exception e) {
                        System.out.println("The test was failed since the API response does not contain 'cus:MemberInfo' Block. This means that expected structure of response have changed. Actual exception - "+e);
                        _scenario.write("The test was failed since the API response does not contain 'cus:MemberInfo' Block. This means that expected structure of response have changed. Actual exception - "+e);
                        fail();
                    }


                    //Setting values to string variables.
                try {
                    MemberID = MemberInfoJsonObj.get("cus:MemberID").toString();
                    if(MemberID==null) {
                        MemberID ="";
                    }
                } catch (Exception e) {
                    if(MemberID==null) {
                        MemberID ="";
                    }
                }


                try {
                    TitleCode = MemberInfoJsonObj.get("cus:TitleCode").toString();
                    if(TitleCode==null) {
                        TitleCode ="";
                    }
                } catch (Exception e) {
                    if(TitleCode==null) {
                        TitleCode ="";
                    }
                }


                try {
                    GenderCode = MemberInfoJsonObj.get("cus:GenderCode").toString();
                    if(GenderCode==null) {
                        GenderCode ="";
                    }
                } catch (Exception e) {
                    if(GenderCode==null) {
                        GenderCode ="";
                    }
                }


                try {
                    EmailAddressText = MemberInfoJsonObj.get("cus:EmailAddressText").toString();
                    if(EmailAddressText==null) {
                        EmailAddressText ="";
                    }
                } catch (Exception e) {
                    if(EmailAddressText==null) {
                        EmailAddressText ="";
                    }
                }


                //PostalCode = MemberInfoJsonObj.get("cus:PostalCode").toString();
                //CityNameText = MemberInfoJsonObj.get("cus:CityNameText").toString();
                try {
                    CountryNameText = MemberInfoJsonObj.get("cus:CountryNameText").toString();
                    if(CountryNameText==null) {
                        CountryNameText ="";
                    }
                } catch (Exception e) {
                    if(CountryNameText==null) {
                        CountryNameText ="";
                    }
                }


                try {
                    ContactNumberText = MemberInfoJsonObj.get("cus:ContactNumberText").toString();
                    if(ContactNumberText==null) {
                        ContactNumberText ="";
                    }
                } catch (Exception e) {
                    if(ContactNumberText==null) {
                        ContactNumberText ="";
                    }
                }


                try {
                    IsInvalidAddress = MemberInfoJsonObj.get("cus:IsInvalidAddress").toString();
                    if(IsInvalidAddress==null) {
                        IsInvalidAddress ="";
                    }
                } catch (Exception e) {
                    if(IsInvalidAddress==null) {
                        IsInvalidAddress ="";
                    }
                }


                try {
                    IsInvalidEmail = MemberInfoJsonObj.get("cus:IsInvalidEmail").toString();
                    if(IsInvalidEmail==null) {
                        IsInvalidEmail ="";
                    }
                } catch (Exception e) {
                    if(IsInvalidEmail==null) {
                        IsInvalidEmail ="";
                    }
                }


                try {
                    IsEmptyEmail = MemberInfoJsonObj.get("cus:IsEmptyEmail").toString();
                    if(IsEmptyEmail==null) {
                        IsEmptyEmail ="";
                    }
                } catch (Exception e) {
                    if(IsEmptyEmail==null) {
                        IsEmptyEmail ="";
                    }
                }

                //EmailUnwillingToProvide = MemberInfoJsonObj.get("cus:EmailUnwillingToProvide").toString();

                //POSNotesUpdatedOn = MemberInfoJsonObj.get("cus:POSNotesUpdatedOn").toString();
                try {
                    FirstName = MemberInfoJsonObj.get("cus:FirstName").toString();
                    if(FirstName==null) {
                        FirstName ="";
                    }
                } catch (Exception e) {
                    if(FirstName==null) {
                        FirstName ="";
                    }
                }


                try {
                    FirstNameNative = MemberInfoJsonObj.get("cus:FirstNameNative").toString();
                    if(FirstNameNative==null) {
                        FirstNameNative ="";
                    }
                } catch (Exception e) {
                    if(FirstNameNative==null) {
                        FirstNameNative ="";
                    }
                }


                try {
                    LastName = MemberInfoJsonObj.get("cus:LastName").toString();
                    if(LastName==null) {
                        LastName ="";
                    }
                } catch (Exception e) {
                    if(LastName==null) {
                        LastName ="";
                    }
                }


                try {
                    LastNameNative = MemberInfoJsonObj.get("cus:LastNameNative").toString();
                    if(LastNameNative==null) {
                        LastNameNative ="";
                    }
                } catch (Exception e) {
                    if(LastNameNative==null) {
                        LastNameNative ="";
                    }
                }


                try {
                    NativeSalutation = MemberInfoJsonObj.get("cus:NativeSalutation").toString();
                    if(NativeSalutation==null) {
                        NativeSalutation ="";
                    }
                } catch (Exception e) {
                    if(NativeSalutation==null) {
                        NativeSalutation ="";
                    }
                }


                //BirthDate = MemberInfoJsonObj.get("cus:BirthDate").toString();
                //BirthMonth = MemberInfoJsonObj.get("cus:BirthMonth").toString();
                //CustomerEnrolmentSource = MemberInfoJsonObj.get("cus:CustomerEnrolmentSource").toString();
                //RegistrationDivisionCode = MemberInfoJsonObj.get("cus:RegistrationDivisionCode").toString();
                //RegistrationLocationID = MemberInfoJsonObj.get("cus:RegistrationLocationID").toString();
                //RegistrationLocationCode = MemberInfoJsonObj.get("cus:RegistrationLocationCode").toString();
                try {
                    HomeNo = MemberInfoJsonObj.get("cus:HomeNo").toString();
                    if(HomeNo==null) {
                        HomeNo ="";
                    }
                } catch (Exception e) {
                    if(HomeNo==null) {
                        HomeNo ="";
                    }
                }


                try {
                    OtherAddress1 = MemberInfoJsonObj.get("cus:OtherAddress1").toString();
                    if(OtherAddress1==null) {
                        OtherAddress1 ="";
                    }
                } catch (Exception e) {
                    if(OtherAddress1==null) {
                        OtherAddress1 ="";
                    }
                }


                try {
                    MailingAddress2 = MemberInfoJsonObj.get("cus:MailingAddress2").toString();
                    if(MailingAddress2==null) {
                        MailingAddress2 ="";
                    }
                } catch (Exception e) {
                    if(MailingAddress2==null) {
                        MailingAddress2 ="";
                    }
                }


                try {
                    ZipCode = MemberInfoJsonObj.get("cus:ZipCode").toString();
                    if(ZipCode==null) {
                        ZipCode ="";
                    }
                } catch (Exception e) {
                    if(ZipCode==null) {
                        ZipCode ="";
                    }
                }


                try {
                    CorrCityNameText = MemberInfoJsonObj.get("cus:CorrCityNameText").toString();
                    if(CorrCityNameText==null) {
                        CorrCityNameText ="";
                    }
                } catch (Exception e) {
                    if(CorrCityNameText==null) {
                        CorrCityNameText ="";
                    }
                }


                try {
                    CorrStateNameText = MemberInfoJsonObj.get("cus:CorrStateNameText").toString();
                    if(CorrStateNameText==null) {
                        CorrStateNameText ="";
                    }
                } catch (Exception e) {
                    if(CorrStateNameText==null) {
                        CorrStateNameText ="";
                    }
                }


                try {
                    MailingAddress1 = MemberInfoJsonObj.get("cus:MailingAddress1").toString();
                    if(MailingAddress1==null) {
                        MailingAddress1 ="";
                    }
                } catch (Exception e) {
                    if(MailingAddress1==null) {
                        MailingAddress1 ="";
                    }
                }


                try {
                    MailingAddress3 = MemberInfoJsonObj.get("cus:MailingAddress3").toString();
                    if(MailingAddress3==null) {
                        MailingAddress3 ="";
                    }
                } catch (Exception e) {
                    if(MailingAddress3==null) {
                        MailingAddress3 ="";
                    }
                }


                try {
                    ResCityNameText = MemberInfoJsonObj.get("cus:ResCityNameText").toString();
                    if(ResCityNameText==null) {
                        ResCityNameText ="";
                    }
                } catch (Exception e) {
                    if(ResCityNameText==null) {
                        ResCityNameText ="";
                    }
                }


                try {
                    ResStateNameText = MemberInfoJsonObj.get("cus:ResStateNameText").toString();
                    if(ResStateNameText==null) {
                        ResStateNameText ="";
                    }
                } catch (Exception e) {
                    if(ResStateNameText==null) {
                        ResStateNameText ="";
                    }
                }


                try {
                    EnglishCountry = MemberInfoJsonObj.get("cus:EnglishCountry").toString();
                    if(EnglishCountry==null) {
                        EnglishCountry ="";
                    }
                } catch (Exception e) {
                    if(EnglishCountry==null) {
                        EnglishCountry ="";
                    }
                }


                try {
                    ValidMobileNo1 = MemberInfoJsonObj.get("cus:ValidMobileNo1").toString();
                    if(ValidMobileNo1==null) {
                        ValidMobileNo1 ="";
                    }
                } catch (Exception e) {
                    if(ValidMobileNo1==null) {
                        ValidMobileNo1 ="";
                    }
                }


                try {
                    ValidHomeNo = MemberInfoJsonObj.get("cus:ValidHomeNo").toString();
                    if(ValidHomeNo==null) {
                        ValidHomeNo ="";
                    }
                } catch (Exception e) {
                    if(ValidHomeNo==null) {
                        ValidHomeNo ="";
                    }
                }


                try {
                    SpokenLanguage = MemberInfoJsonObj.get("cus:SpokenLanguage").toString();
                    if(SpokenLanguage==null) {
                        SpokenLanguage ="";
                    }
                } catch (Exception e) {
                    if(SpokenLanguage==null) {
                        SpokenLanguage ="";
                    }
                }

                //IsContactable = MemberInfoJsonObj.get("cus:IsContactable").toString();
                //ChildrenCountRange = MemberInfoJsonObj.get("cus:ChildrenCountRange").toString();
                //CustomerRegistrationDatetime = MemberInfoJsonObj.get("cus:CustomerRegistrationDatetime").toString().substring(0,10);
                //CustomerLeisureActivity = MemberInfoJsonObj.get("cus:CustomerLeisureActivity").toString();
                //CustomerShoppingPreference = MemberInfoJsonObj.get("cus:CustomerShoppingPreference").toString();
                //CustomerPreferredBrands = MemberInfoJsonObj.get("cus:CustomerPreferredBrands").toString();
                //LeisureActivitiesMultiple = MemberInfoJsonObj.get("cus:LeisureActivitiesMultiple").toString();
                //ShoppingPreferencesMultiple = MemberInfoJsonObj.get("cus:ShoppingPreferencesMultiple").toString();
                //PreferredBrandsMultiple = MemberInfoJsonObj.get("cus:PreferredBrandsMultiple").toString();
                //FirstPurchaseDate =  MemberInfoJsonObj.get("cus:FirstPurchaseDate").toString();
                //NoOfRepeatVisits =  MemberInfoJsonObj.get("cus:NoOfRepeatVisits").toString();
                //PurchaseRange = MemberInfoJsonObj.get("cus:PurchaseRange").toString();
                //LTV = MemberInfoJsonObj.get("cus:LTV").toString();
                //NoOfPurchases =  MemberInfoJsonObj.get("cus:NoOfPurchases").toString();
                //TotalUnits =  MemberInfoJsonObj.get("cus:TotalUnits").toString();
                //LastPurchaseDate = PreferredBrandsMultiple = MemberInfoJsonObj.get("cus:LastPurchaseDate").toString();
                //TotalDiscount = PreferredBrandsMultiple = MemberInfoJsonObj.get("cus:TotalDiscount").toString();
                //AverageNoOfDaysPurchase = PreferredBrandsMultiple = MemberInfoJsonObj.get("cus:AverageNoOfDaysPurchase").toString();
                //AgeRange = MemberInfoJsonObj.get("cus:AgeRange").toString();
                //MarketingSource = MemberInfoJsonObj.get("cus:MarketingSource").toString();
                //IsEmailOptOut = MemberInfoJsonObj.get("cus:IsEmailOptOut").toString();
                //IsValidMobile2 = MemberInfoJsonObj.get("cus:IsValidMobile2").toString();
                //Mobile2AreaCode = MemberInfoJsonObj.get("cus:Mobile2AreaCode").toString();
                //ZipCodeValidFlag = MemberInfoJsonObj.get("cus:ZipCodeValidFlag").toString();
                try {
                    OtherAddress2 = MemberInfoJsonObj.get("cus:OtherAddress2").toString();
                    if(OtherAddress2==null) {
                        OtherAddress2 ="";
                    }
                } catch (Exception e) {
                    if(OtherAddress2==null) {
                        OtherAddress2 ="";
                    }
                }


                try {
                    OtherAddress3 = MemberInfoJsonObj.get("cus:OtherAddress3").toString();
                    if(OtherAddress3==null) {
                        OtherAddress3 ="";
                    }
                } catch (Exception e) {
                    if(OtherAddress3==null) {
                        OtherAddress3 ="";
                    }
                }

                //IsValidAddressOthers = MemberInfoJsonObj.get("cus:IsValidAddressOthers").toString();
                    try {
                        MemberPointBalance = MemberInfoJsonObj.get("cus:MemberPointBalance").toString();
                        if(MemberPointBalance==null) {
                            MemberPointBalance ="";
                        }
                    } catch (Exception e) {
                        if(MemberPointBalance==null) {
                            MemberPointBalance ="";
                        }
                    }

                //LastAccrualDate = MemberInfoJsonObj.get("cus:LastAccrualDate").toString();
                //JoinedDate = MemberInfoJsonObj.get("cus:JoinedDate").toString();
                try {
                    MemberStatus = MemberInfoJsonObj.get("cus:MemberStatus").toString();
                    if(MemberStatus==null) {
                        MemberStatus ="";
                    }
                } catch (Exception e) {
                    if(MemberStatus==null) {
                        MemberStatus ="";
                    }
                }


                try {
                    CardTier = MemberInfoJsonObj.get("cus:CardTier").toString();
                    if(CardTier==null) {
                        CardTier ="";
                    }
                } catch (Exception e) {
                    if(CardTier==null) {
                        CardTier ="";
                    }
                }

                //CardPickupMethod = MemberInfoJsonObj.get("cus:CardPickupMethod").toString();
                //Remarks = MemberInfoJsonObj.get("cus:Remarks").toString();
                //IpadID = MemberInfoJsonObj.get("cus:IpadID").toString();
                //StaffID = MemberInfoJsonObj.get("cus:StaffID").toString();
                //MarketingSourceOthers = MemberInfoJsonObj.get("cus:MarketingSourceOthers").toString();

                //Mobile1AreaCode
                    try {
                        Mobile1AreaCode = MemberInfoJsonObj.get("cus:Mobile1AreaCode").toString();
                        if(Mobile1AreaCode==null) {
                            Mobile1AreaCode ="";
                        }
                    } catch (Exception e) {
                        if(Mobile1AreaCode==null) {
                            Mobile1AreaCode ="";
                        }
                    }

                //HomePhoneAreaCode = MemberInfoJsonObj.get("cus:HomePhoneAreaCode").toString();
                //CreationDate = MemberInfoJsonObj.get("cus:CreationDate").toString().substring(0,10);
                //UpdateDate = MemberInfoJsonObj.get("cus:UpdateDate").toString().substring(0,10);
                //CreationUserID = MemberInfoJsonObj.get("cus:CreationUserID").toString();
                //UpdateUserID = MemberInfoJsonObj.get("cus:UpdateUserID").toString();
                //SourceSystem = MemberInfoJsonObj.get("cus:SourceSystem").toString();
                //PortalActivationFlag = MemberInfoJsonObj.get("cus:PortalActivationFlag").toString();
                //isValidPoints = MemberInfoJsonObj.get("cus:isValidPoints").toString();
                //isActiveLoyalTMember = MemberInfoJsonObj.get("cus:isActiveLoyalTMember").toString();
                //IsVCardDownloaded = MemberInfoJsonObj.get("cus:IsVCardDownloaded").toString();



            }

            //##########################Getting values from crm:Membership block ############################
            //JSONArray crmMembership = (JSONArray) crmCRM.get("crm:Membership");


            JSONObject memInfo = null;
            try {
                JSONObject crmMembership = (JSONObject) crmCRM.get("crm:Membership");
                memInfo = (JSONObject) crmMembership.get("ms:MembershipInfo");
            } catch (Exception e) {
                if(ReturnMessage.equalsIgnoreCase(Property.API_CARDNO_NOT_FOUND_MESSAGE)) {
                    System.out.println("Test failed Intentionally !. Member Not found");
                    _scenario.write("Test failed Intentionally !. Member Not found");
                    fail("TEST FAILED INTENTIONALLY !. Member Not found");
                } else {
                    System.out.println("The test was failed since the API response does not contain 'crm.Membership' Block. This means that expected structure of response have changed. Actual exception - "+e);
                    _scenario.write("The test was failed since the API response does not contain 'crm.Membership' Block. This means that expected structure of response have changed. Actual exception - "+e);
                    fail("TEST FAILED INTENTIONALLY. ! API response does not contain 'crm.Membership' Block. This means that expected structure of response have changed.");
                }

            }
                String carryforwardStatusDollar = null;
                try {
                    carryforwardStatusDollar = (String) memInfo.get("ms:CarryForwardDollarUSDAmount");
                    if(carryforwardStatusDollar.contains(".")) {
                        int indexOfDecimalPoint = carryforwardStatusDollar.indexOf(".");
                        CarryForwardDollarUSDAmount = carryforwardStatusDollar.substring(0,indexOfDecimalPoint);
                    } else {
                        CarryForwardDollarUSDAmount = carryforwardStatusDollar.trim();
                    }
                } catch (Exception e) {
                    if(carryforwardStatusDollar==null){
                        CarryForwardDollarUSDAmount="";
                    }
                }


                try {
                    MembershipTierID = (String) memInfo.get("ms:MembershipTierID");
                    if(MembershipTierID==null) {
                        MembershipTierID ="";
                    }
                } catch (Exception e) {
                    if(MembershipTierID==null) {
                        MembershipTierID ="";
                    }
                }

                //StatusDollarUSDAmount = (String) memInfo.get("ms:StatusDollarUSDAmount");
                String startDateRaw=null;
                try {
                    startDateRaw = (String) memInfo.get("ms:StartDate").toString();
                    if(startDateRaw==null) {
                        StartDate ="";
                    } else {
                        StartDate = startDateRaw.substring(0,10).trim();
                    }
                } catch (Exception e) {
                    if(startDateRaw==null) {
                        StartDate ="";
                    }
                }


                String EndDateRaw = null;
                try {
                    EndDateRaw = (String) memInfo.get("ms:EndDate").toString();
                    if(EndDateRaw==null) {
                        EndDate ="";
                    } else {
                        EndDate = EndDateRaw.substring(0,10).trim();
                    }
                } catch (Exception e) {
                    if(EndDateRaw==null) {
                        EndDate ="";
                    }
                }


                try {
                    StatusDollarToUpgrade = (String) memInfo.get("ms:StatusDollarToUpgrade");
                    if(StatusDollarToUpgrade==null) {
                        StatusDollarToUpgrade ="";
                    }
                } catch (Exception e) {
                    if(StatusDollarToUpgrade==null) {
                        StatusDollarToUpgrade ="";
                    }
                }


                try {
                    StatusDollarToRenew = (String) memInfo.get("ms:StatusDollarToRenew");
                    if(StatusDollarToRenew==null) {
                        StatusDollarToRenew ="";
                    }
                } catch (Exception e) {
                    if(StatusDollarToRenew==null) {
                        StatusDollarToRenew ="";
                    }
                }
            //################################## End of crm:Membership block #################################


            //############################# getting values from crm:MemberCard block ###########################
            JSONArray crmMemberCard = null;
            try {
                crmMemberCard = (JSONArray) crmCRM.get("crm:MemberCard");
            } catch (Exception e) {
                System.out.println("The test was failed since the API response does not contain 'crm.MemberCard' Block. This means that expected structure of response have changed. Actual exception - "+e);
                _scenario.write("The test was failed since the API response does not contain 'crm.MemberCard' Block. This means that expected structure of response have changed. Actual exception - "+e);
                fail();
            }

            for (int i=0;i<crmMemberCard.size();i++){
                try {
                    JSONObject crmMemberCard2 = (JSONObject) crmMemberCard.get(i);
                    MemberInfoJsonObj = (JSONObject) crmMemberCard2.get("mc:CardInfo");
                } catch (Exception e) {
                    System.out.println("The test was failed since the API response does not contain 'crm.CardInfo' Block. This means that expected structure of response have changed. Actual exception - "+e);
                    _scenario.write("The test was failed since the API response does not contain 'crm.CardInfo' Block. This means that expected structure of response have changed. Actual exception - "+e);
                    fail();
                }

                try {
                    CardNo = MemberInfoJsonObj.get("mc:CardNo").toString();
                    if(CardNo==null) {
                        CardNo ="";
                    }
                } catch (Exception e) {
                    if(CardNo==null) {
                        CardNo ="";
                    }
                }

//                MembershipCardTypeCode = MemberInfoJsonObj.get("mc:MembershipCardTypeCode").toString();
//                MembershipcardStatusCode = MemberInfoJsonObj.get("mc:MembershipcardStatusCode").toString();
//                PtsHoldingDays = MemberInfoJsonObj.get("mc:PtsHoldingDays").toString();
//                TotalPointsBAL =MemberInfoJsonObj.get("mc:TotalPointsBAL").toString();
//                IssueDateKey = MemberInfoJsonObj.get("mc:IssueDateKey").toString();
//                EffectiveDateKey = MemberInfoJsonObj.get("mc:EffectiveDateKey").toString();

            }
        //############################# End of crm:MemberCard block ###########################

        } else  if (statusCode!=HttpStatus.SC_OK){
            System.out.println("POST request Error. Returned the status code "+ statusCode);
            _scenario.write("POST request Error. Returned the status code "+ statusCode);

        } else if (response.getBody().toString().isEmpty() || response.getBody().toString() ==""){
            System.out.println("The response body is null or its empty");
            _scenario.write("The response body is null or its empty");
        }
    }

    public void getMemberPOS(String requestBody) throws Exception {

        Response response = null;
        int statusCode = 0;
        String baseURL="";

        readTestData data = new readTestData();

        //reading the environment from the system properties
        if(System.getProperty("Environment").equalsIgnoreCase("Preprod")) {
            baseURL = data.readTestData("Preprod_GetMember_External");  // Preprod_GetMember_External    // Preprod_ETR_External
            System.out.println("-------Preprod ---------");
        } else if(System.getProperty("Environment").equalsIgnoreCase("QACore2")){
            baseURL = data.readTestData("QACore2_GetMember_External");  // QACore2_GetMember_External   // QACore2_ETR_External
            System.out.println("-------QaCore2 ---------");
        }

        String endpointURL = data.readTestData("POS_GetMember_Endpoint_URL");
        String authorization = data.readTestData("POS_Authorization");

        String URL = baseURL + endpointURL;
        System.out.println(URL);

        System.out.println(requestBody);

        try {
            response = given()
                    .headers("Content-Type", "application/json", "Authorization", authorization) //"Authorization", authorization
                    .body(requestBody)
                    //.auth().basic("automation","~g59RcHZy3X]MfpP")
                    .when()
                    .post(URL)
                    .then()
                    .contentType(ContentType.JSON)
                    .extract().response();

            statusCode = response.getStatusCode();

            //PRINTING THE update response IN CUCUMBER REPORT
            _scenario.write("=============== The API response body for POS get member is as follows ===================");
            _scenario.write(response.asString());
            _scenario.write("================================ End of API response body ===================================");

            System.out.println("=============== The API response body for POS get member is as follows ===================");
            System.out.println(response.asString());
            System.out.println("================================ End of API response body ===================================");

            System.out.println("Status code : "+ statusCode);
            _scenario.write("Status code : "+ statusCode);

        } catch (Exception e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful");
            _scenario.write("The test was failed since POST call to Salesforce was not successful");
            Assert.fail();
        }

        try {
            Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        } catch (AssertionError e) {
            System.out.println("The test was failed since POST call to Salesforce was not successful with status code"+ response.getStatusCode() +" The actual status is - "+e);
            _scenario.write("The test was failed since POST call to Salesforce was not successful with status code"+ response.getStatusCode() +" The actual status is - "+e);
            Assert.fail();
        }


        //IF STATUS IS 200 OK , then extract values from the response
        if(statusCode == HttpStatus.SC_OK && !response.getBody().toString().isEmpty()) {

            //Converting the response body into a String buffer
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getBody().asInputStream()));
            String inputLine;
            StringBuffer resp = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                resp.append(inputLine);
            }
            br.close();

            //parse the response into a JSON object

            JSONParser jsonParser = new JSONParser();
            Object object = jsonParser.parse(resp.toString());
            jsonObject = (JSONObject) object;

            JSONObject crmCRM = null;
            JSONArray crmCustomer = null;

            try {
                //Navigating through the json nodes
                JSONObject crm = (JSONObject) jsonObject.get("CRM");
                crmCRM = (JSONObject) crm.get("crm:CRM");

                crmCustomer = (JSONArray) crmCRM.get("crm:Customer");

            } catch (Exception e) {
                System.out.println("The test was failed since the API response does not contain 'crm.Customer' Block. This means that expected structure of response have changed. Actual exception - " + e);
                _scenario.write("The test was failed since the API response does not contain 'crm.Customer' Block. This means that expected structure of response have changed. Actual exception - " + e);
                fail();

            }


            // ############################ Start of crm:loyaltyService block #####################
            JSONObject respInfo = null;
            try {
                JSONObject crmLoyaltyService = (JSONObject) crmCRM.get("crm:loyaltyService");
                respInfo = (JSONObject) crmLoyaltyService.get("lts:response");
            } catch (Exception e) {
                System.out.println("The test was failed since the API response does not contain 'crm.loyaltyService' Block. This means that expected structure of response have changed. Actual exception - " + e);
                _scenario.write("The test was failed since the API response does not contain 'crm.loyaltyService' Block. This means that expected structure of response have changed. Actual exception - " + e);
                fail();
            }
            //getting values from lts:response block
//            for (int k = 0; k < crmLoyaltyService.size(); k++) {
//                JSONObject crmLoyalty = (JSONObject) crmLoyaltyService.get(k);
//                MemberInfoJsonObj = (JSONObject) crmLoyalty.get("lts:response");

            //getting the lts:ReturnStatus
            try {
                ReturnStatus = respInfo.get("lts:ReturnStatus").toString();
                if (ReturnStatus == null) {
                    ReturnStatus = "";
                }
            } catch (Exception e) {
                if (ReturnStatus == null) {
                    ReturnStatus = "";
                }
            }

            //getting the lts:ReturnMessage
            try {
                ReturnMessage = respInfo.get("lts:ReturnMessage").toString();
                if (ReturnMessage == null) {
                    ReturnMessage = "";
                }
            } catch (Exception e) {
                if (ReturnMessage == null) {
                    ReturnMessage = "";
                }
            }


//            }
            //############################ End of crm:loyaltyService block #########################

            //#########################  Start of crm:Customer block #######################################
            for (int k = 0; k < crmCustomer.size(); k++) {
                try {
                    JSONObject crmCustomer2 = (JSONObject) crmCustomer.get(k);
                    MemberInfoJsonObj = (JSONObject) crmCustomer2.get("cus:MemberInfo");
                } catch (Exception e) {
                    System.out.println("The test was failed since the API response does not contain 'cus:MemberInfo' Block. This means that expected structure of response have changed. Actual exception - "+e);
                    _scenario.write("The test was failed since the API response does not contain 'cus:MemberInfo' Block. This means that expected structure of response have changed. Actual exception - "+e);
                    fail();
                }


                //Setting values to string variables.
                try {
                    MemberID = MemberInfoJsonObj.get("cus:MemberID").toString();
                    if(MemberID==null) {
                        MemberID ="";
                    }
                } catch (Exception e) {
                    if(MemberID==null) {
                        MemberID ="";
                    }
                }


                try {
                    TitleCode = MemberInfoJsonObj.get("cus:TitleCode").toString();
                    if(TitleCode==null) {
                        TitleCode ="";
                    }
                } catch (Exception e) {
                    if(TitleCode==null) {
                        TitleCode ="";
                    }
                }


                try {
                    GenderCode = MemberInfoJsonObj.get("cus:GenderCode").toString();
                    if(GenderCode==null) {
                        GenderCode ="";
                    }
                } catch (Exception e) {
                    if(GenderCode==null) {
                        GenderCode ="";
                    }
                }


                try {
                    EmailAddressText = MemberInfoJsonObj.get("cus:EmailAddressText").toString();
                    if(EmailAddressText==null) {
                        EmailAddressText ="";
                    }
                } catch (Exception e) {
                    if(EmailAddressText==null) {
                        EmailAddressText ="";
                    }
                }


                //PostalCode = MemberInfoJsonObj.get("cus:PostalCode").toString();
                //CityNameText = MemberInfoJsonObj.get("cus:CityNameText").toString();
                try {
                    CountryNameText = MemberInfoJsonObj.get("cus:CountryNameText").toString();
                    if(CountryNameText==null) {
                        CountryNameText ="";
                    }
                } catch (Exception e) {
                    if(CountryNameText==null) {
                        CountryNameText ="";
                    }
                }


                try {
                    ContactNumberText = MemberInfoJsonObj.get("cus:ContactNumberText").toString();
                    if(ContactNumberText==null) {
                        ContactNumberText ="";
                    }
                } catch (Exception e) {
                    if(ContactNumberText==null) {
                        ContactNumberText ="";
                    }
                }


                try {
                    IsInvalidAddress = MemberInfoJsonObj.get("cus:IsInvalidAddress").toString();
                    if(IsInvalidAddress==null) {
                        IsInvalidAddress ="";
                    }

                } catch (Exception e) {
                    if(IsInvalidAddress==null) {
                        IsInvalidAddress ="";
                    }
                }


                try {
                    IsInvalidEmail = MemberInfoJsonObj.get("cus:IsInvalidEmail").toString();
                    if(IsInvalidEmail==null) {
                        IsInvalidEmail ="";
                    }
                } catch (Exception e) {
                    if(IsInvalidEmail==null) {
                        IsInvalidEmail ="";
                    }
                }


                try {
                    IsEmptyEmail = MemberInfoJsonObj.get("cus:IsEmptyEmail").toString();
                    if(IsEmptyEmail==null) {
                        IsEmptyEmail ="";
                    }
                } catch (Exception e) {
                    if(IsEmptyEmail==null) {
                        IsEmptyEmail ="";
                    }
                }

                //EmailUnwillingToProvide = MemberInfoJsonObj.get("cus:EmailUnwillingToProvide").toString();

                //POSNotesUpdatedOn = MemberInfoJsonObj.get("cus:POSNotesUpdatedOn").toString();
                try {
                    FirstName = MemberInfoJsonObj.get("cus:FirstName").toString();
                    if(FirstName==null) {
                        FirstName ="";
                    }
                } catch (Exception e) {
                    if(FirstName==null) {
                        FirstName ="";
                    }
                }


                try {
                    FirstNameNative = MemberInfoJsonObj.get("cus:FirstNameNative").toString();
                } catch (Exception e) {
                    if(FirstNameNative==null) {
                        FirstNameNative ="";
                    }
                }


                try {
                    LastName = MemberInfoJsonObj.get("cus:LastName").toString();
                    if(LastName==null) {
                        LastName ="";
                    }
                } catch (Exception e) {
                    if(LastName==null) {
                        LastName ="";
                    }
                }


                try {
                    LastNameNative = MemberInfoJsonObj.get("cus:LastNameNative").toString();
                    if(LastNameNative==null) {
                        LastNameNative ="";
                    }
                } catch (Exception e) {
                    if(LastNameNative==null) {
                        LastNameNative ="";
                    }
                }


                try {
                    NativeSalutation = MemberInfoJsonObj.get("cus:NativeSalutation").toString();
                    if(NativeSalutation==null) {
                        NativeSalutation ="";
                    }
                } catch (Exception e) {
                    if(NativeSalutation==null) {
                        NativeSalutation ="";
                    }
                }


                //BirthDate = MemberInfoJsonObj.get("cus:BirthDate").toString();
                //BirthMonth = MemberInfoJsonObj.get("cus:BirthMonth").toString();
                //CustomerEnrolmentSource = MemberInfoJsonObj.get("cus:CustomerEnrolmentSource").toString();
                //RegistrationDivisionCode = MemberInfoJsonObj.get("cus:RegistrationDivisionCode").toString();
                //RegistrationLocationID = MemberInfoJsonObj.get("cus:RegistrationLocationID").toString();
                //RegistrationLocationCode = MemberInfoJsonObj.get("cus:RegistrationLocationCode").toString();
                try {
                    HomeNo = MemberInfoJsonObj.get("cus:HomeNo").toString();
                    if(HomeNo==null) {
                        HomeNo ="";
                    }
                } catch (Exception e) {
                    if(HomeNo==null) {
                        HomeNo ="";
                    }
                }


                try {
                    OtherAddress1 = MemberInfoJsonObj.get("cus:OtherAddress1").toString();
                    if(OtherAddress1==null) {
                        OtherAddress1 ="";
                    }
                } catch (Exception e) {
                    if(OtherAddress1==null) {
                        OtherAddress1 ="";
                    }
                }


                try {
                    MailingAddress2 = MemberInfoJsonObj.get("cus:MailingAddress2").toString();
                    if(MailingAddress2==null) {
                        MailingAddress2 ="";
                    }
                } catch (Exception e) {
                    if(MailingAddress2==null) {
                        MailingAddress2 ="";
                    }
                }


                try {
                    ZipCode = MemberInfoJsonObj.get("cus:ZipCode").toString();
                    if(ZipCode==null) {
                        ZipCode ="";
                    }
                } catch (Exception e) {
                    if(ZipCode==null) {
                        ZipCode ="";
                    }
                }


                try {
                    CorrCityNameText = MemberInfoJsonObj.get("cus:CorrCityNameText").toString();
                    if(CorrCityNameText==null) {
                        CorrCityNameText ="";
                    }
                } catch (Exception e) {
                    if(CorrCityNameText==null) {
                        CorrCityNameText ="";
                    }
                }


                try {
                    CorrStateNameText = MemberInfoJsonObj.get("cus:CorrStateNameText").toString();
                    if(CorrStateNameText==null) {
                        CorrStateNameText ="";
                    }
                } catch (Exception e) {
                    if(CorrStateNameText==null) {
                        CorrStateNameText ="";
                    }
                }


                try {
                    MailingAddress1 = MemberInfoJsonObj.get("cus:MailingAddress1").toString();
                    if(MailingAddress1==null) {
                        MailingAddress1 ="";
                    }
                } catch (Exception e) {
                    if(MailingAddress1==null) {
                        MailingAddress1 ="";
                    }
                }


                try {
                    MailingAddress3 = MemberInfoJsonObj.get("cus:MailingAddress3").toString();
                    if(MailingAddress3==null) {
                        MailingAddress3 ="";
                    }
                } catch (Exception e) {
                    if(MailingAddress3==null) {
                        MailingAddress3 ="";
                    }
                }


                try {
                    ResCityNameText = MemberInfoJsonObj.get("cus:ResCityNameText").toString();
                    if(ResCityNameText==null) {
                        ResCityNameText ="";
                    }
                } catch (Exception e) {
                    if(ResCityNameText==null) {
                        ResCityNameText ="";
                    }
                }


                try {
                    ResStateNameText = MemberInfoJsonObj.get("cus:ResStateNameText").toString();
                    if(ResStateNameText==null) {
                        ResStateNameText ="";
                    }
                } catch (Exception e) {
                    if(ResStateNameText==null) {
                        ResStateNameText ="";
                    }
                }


                try {
                    EnglishCountry = MemberInfoJsonObj.get("cus:EnglishCountry").toString();
                    if(EnglishCountry==null) {
                        EnglishCountry ="";
                    }
                } catch (Exception e) {
                    if(EnglishCountry==null) {
                        EnglishCountry ="";
                    }
                }


                try {
                    ValidMobileNo1 = MemberInfoJsonObj.get("cus:ValidMobileNo1").toString();
                    if(ValidMobileNo1==null) {
                        ValidMobileNo1 ="";
                    }
                } catch (Exception e) {
                    if(ValidMobileNo1==null) {
                        ValidMobileNo1 ="";
                    }
                }


                try {
                    ValidHomeNo = MemberInfoJsonObj.get("cus:ValidHomeNo").toString();
                    if(ValidHomeNo==null) {
                        ValidHomeNo ="";
                    }
                } catch (Exception e) {
                    if(ValidHomeNo==null) {
                        ValidHomeNo ="";
                    }
                }


                try {
                    SpokenLanguage = MemberInfoJsonObj.get("cus:SpokenLanguage").toString();
                    if(SpokenLanguage==null) {
                        SpokenLanguage ="";
                    }
                } catch (Exception e) {
                    if(SpokenLanguage==null) {
                        SpokenLanguage ="";
                    }
                }

                //IsContactable = MemberInfoJsonObj.get("cus:IsContactable").toString();
                //ChildrenCountRange = MemberInfoJsonObj.get("cus:ChildrenCountRange").toString();
                //CustomerRegistrationDatetime = MemberInfoJsonObj.get("cus:CustomerRegistrationDatetime").toString().substring(0,10);
                //CustomerLeisureActivity = MemberInfoJsonObj.get("cus:CustomerLeisureActivity").toString();
                //CustomerShoppingPreference = MemberInfoJsonObj.get("cus:CustomerShoppingPreference").toString();
                //CustomerPreferredBrands = MemberInfoJsonObj.get("cus:CustomerPreferredBrands").toString();
                //LeisureActivitiesMultiple = MemberInfoJsonObj.get("cus:LeisureActivitiesMultiple").toString();
                //ShoppingPreferencesMultiple = MemberInfoJsonObj.get("cus:ShoppingPreferencesMultiple").toString();
                //PreferredBrandsMultiple = MemberInfoJsonObj.get("cus:PreferredBrandsMultiple").toString();
                //FirstPurchaseDate =  MemberInfoJsonObj.get("cus:FirstPurchaseDate").toString();
                //NoOfRepeatVisits =  MemberInfoJsonObj.get("cus:NoOfRepeatVisits").toString();
                //PurchaseRange = MemberInfoJsonObj.get("cus:PurchaseRange").toString();
                //LTV = MemberInfoJsonObj.get("cus:LTV").toString();
                //NoOfPurchases =  MemberInfoJsonObj.get("cus:NoOfPurchases").toString();
                //TotalUnits =  MemberInfoJsonObj.get("cus:TotalUnits").toString();
                //LastPurchaseDate = PreferredBrandsMultiple = MemberInfoJsonObj.get("cus:LastPurchaseDate").toString();
                //TotalDiscount = PreferredBrandsMultiple = MemberInfoJsonObj.get("cus:TotalDiscount").toString();
                //AverageNoOfDaysPurchase = PreferredBrandsMultiple = MemberInfoJsonObj.get("cus:AverageNoOfDaysPurchase").toString();
                //AgeRange = MemberInfoJsonObj.get("cus:AgeRange").toString();
                //MarketingSource = MemberInfoJsonObj.get("cus:MarketingSource").toString();
                //IsEmailOptOut = MemberInfoJsonObj.get("cus:IsEmailOptOut").toString();
                //IsValidMobile2 = MemberInfoJsonObj.get("cus:IsValidMobile2").toString();
                //Mobile2AreaCode = MemberInfoJsonObj.get("cus:Mobile2AreaCode").toString();
                //ZipCodeValidFlag = MemberInfoJsonObj.get("cus:ZipCodeValidFlag").toString();
                try {
                    OtherAddress2 = MemberInfoJsonObj.get("cus:OtherAddress2").toString();
                    if(OtherAddress2==null) {
                        OtherAddress2 ="";
                    }
                } catch (Exception e) {
                    if(OtherAddress2==null) {
                        OtherAddress2 ="";
                    }
                }


                try {
                    OtherAddress3 = MemberInfoJsonObj.get("cus:OtherAddress3").toString();
                    if(OtherAddress3==null) {
                        OtherAddress3 ="";
                    }

                } catch (Exception e) {
                    if(OtherAddress3==null) {
                        OtherAddress3 ="";
                    }
                }
                //IsValidAddressOthers = MemberInfoJsonObj.get("cus:IsValidAddressOthers").toString();

                String memberPointBalance = null;
                try {
                    memberPointBalance = MemberInfoJsonObj.get("cus:MemberPointBalance").toString();
                    if(memberPointBalance==null) {
                        MemberPointBalance="0";
                    } else if(memberPointBalance.contains(".")) {
                        int indexOfdecimalPoint = memberPointBalance.indexOf(".");
                        MemberPointBalance = memberPointBalance.substring(0,indexOfdecimalPoint);
                    } else {
                        MemberPointBalance = memberPointBalance.trim();
                    }
                } catch (Exception e) {
                    if(memberPointBalance==null) {
                        MemberPointBalance="0";
                    }
                }

                try {
                    LastAccrualDate = MemberInfoJsonObj.get("cus:LastAccrualDate").toString();
                    if(LastAccrualDate==null){
                        LastAccrualDate="";
                    }
                } catch (Exception e) {
                    if(LastAccrualDate==null){
                        LastAccrualDate="";
                    }
                }

                //JoinedDate = MemberInfoJsonObj.get("cus:JoinedDate").toString();

                try {
                    MemberStatus = MemberInfoJsonObj.get("cus:MemberStatus").toString();
                    if(MemberStatus==null) {
                        MemberStatus ="";
                    }
                } catch (Exception e) {
                    if(MemberStatus==null) {
                        MemberStatus ="";
                    }
                }


                try {
                    CardTier = MemberInfoJsonObj.get("cus:CardTier").toString();
                    if(CardTier==null) {
                        CardTier ="";
                    }
                } catch (Exception e) {
                    if(CardTier==null) {
                        CardTier ="";
                    }
                }
                //CardPickupMethod = MemberInfoJsonObj.get("cus:CardPickupMethod").toString();
                //Remarks = MemberInfoJsonObj.get("cus:Remarks").toString();
                //IpadID = MemberInfoJsonObj.get("cus:IpadID").toString();
                //StaffID = MemberInfoJsonObj.get("cus:StaffID").toString();
                //MarketingSourceOthers = MemberInfoJsonObj.get("cus:MarketingSourceOthers").toString();
                //Mobile1AreaCode = MemberInfoJsonObj.get("cus:Mobile1AreaCode").toString();
                //HomePhoneAreaCode = MemberInfoJsonObj.get("cus:HomePhoneAreaCode").toString();
                //CreationDate = MemberInfoJsonObj.get("cus:CreationDate").toString().substring(0,10);
                //UpdateDate = MemberInfoJsonObj.get("cus:UpdateDate").toString().substring(0,10);
                //CreationUserID = MemberInfoJsonObj.get("cus:CreationUserID").toString();
                //UpdateUserID = MemberInfoJsonObj.get("cus:UpdateUserID").toString();
                //SourceSystem = MemberInfoJsonObj.get("cus:SourceSystem").toString();
                //PortalActivationFlag = MemberInfoJsonObj.get("cus:PortalActivationFlag").toString();
                //isValidPoints = MemberInfoJsonObj.get("cus:isValidPoints").toString();
                //isActiveLoyalTMember = MemberInfoJsonObj.get("cus:isActiveLoyalTMember").toString();
                //IsVCardDownloaded = MemberInfoJsonObj.get("cus:IsVCardDownloaded").toString();



            }

            //##########################Getting values from crm:Membership block ############################

            JSONObject memInfo = null;
            try {
                JSONObject crmMembership = (JSONObject) crmCRM.get("crm:Membership");
                memInfo = (JSONObject) crmMembership.get("ms:MembershipInfo");
            } catch (Exception e) {
                if(ReturnMessage.equalsIgnoreCase(Property.API_CARDNO_NOT_FOUND_MESSAGE)) {
                    System.out.println("Test failed Intentionally !. Member Not found");
                    _scenario.write("Test failed Intentionally !. Member Not found");
                    fail("TEST FAILED INTENTIONALLY !. Member Not found");
                } else {
                    System.out.println("The API response does not contain 'crm.Membership' Block.");
                    _scenario.write("The API response does not contain 'crm.Membership' Block.");
                    //fail("TEST FAILED INTENTIONALLY !. API response does not contain 'crm.Membership' Block. This means that expected structure of response have changed.");
                    throw new InterruptedException("The API response does not contain 'crm.Membership' Block. This could be a 'CANCELLED MEMBER'");
                }

            }

            //TotalVisits = (String) memInfo.get("ms:TotalVisits");
                String carryforwardStatusDollar = null;
                try {
                    carryforwardStatusDollar = (String) memInfo.get("ms:CarryForwardDollarUSDAmount");
                    if(carryforwardStatusDollar.contains(".")) {
                        int indexOfDecimalPoint = carryforwardStatusDollar.indexOf(".");
                        CarryForwardDollarUSDAmount = carryforwardStatusDollar.substring(0,indexOfDecimalPoint);
                    } else {
                        CarryForwardDollarUSDAmount = carryforwardStatusDollar.trim();
                    }
                } catch (Exception e) {
                    CarryForwardDollarUSDAmount="";
                }


                //TotalSpending = (String) memInfo.get("ms:TotalSpending");
                try {
                    MembershipTierID = (String) memInfo.get("ms:MembershipTierID");
                    if(MembershipTierID==null) {
                        MembershipTierID="";
                    }
                } catch (Exception e) {
                    if(MembershipTierID==null) {
                        MembershipTierID="";
                    }
                }


                String statusdollarUSDamount = null;
                try {
                    statusdollarUSDamount = (String) memInfo.get("ms:StatusDollarUSDAmount");
                    if(statusdollarUSDamount==null) {
                        StatusDollarUSDAmount="0";
                    }else if (statusdollarUSDamount.equalsIgnoreCase("0.0")){
                        StatusDollarUSDAmount="0";
                    } else if (statusdollarUSDamount.contains(".")) {
                        int indexOfDecimalPointOfstatusdollar = statusdollarUSDamount.indexOf(".");
                        StatusDollarUSDAmount = statusdollarUSDamount.substring(0,indexOfDecimalPointOfstatusdollar).trim();
                    } else {
                        StatusDollarUSDAmount =statusdollarUSDamount.trim();
                    }
                } catch (Exception e) {
                    StatusDollarUSDAmount="0";
                    if(statusdollarUSDamount==null) {
                        StatusDollarUSDAmount="0";
                    }
                }


                try {
                    StartDate =  (String) memInfo.get("ms:StartDate").toString().substring(0,10);
                } catch (Exception e) {
                    if(StartDate==null) {
                        StartDate ="";
                    }
                }


                try {
                    EndDate = (String) memInfo.get("ms:EndDate").toString().substring(0,10);

                } catch (Exception e) {
                    if(EndDate==null) {
                        EndDate ="";
                    }
                }


                try {
                    StatusDollarToUpgrade = (String) memInfo.get("ms:StatusDollarToUpgrade");
                    if(StatusDollarToUpgrade==null) {
                        StatusDollarToUpgrade ="";
                    }
                } catch (Exception e) {
                    if(StatusDollarToUpgrade==null) {
                        StatusDollarToUpgrade ="";
                    }
                }


                try {
                    StatusDollarToRenew = (String) memInfo.get("ms:StatusDollarToRenew");
                    if(StatusDollarToRenew==null) {
                        StatusDollarToRenew ="";
                    }
                } catch (Exception e) {
                    if(StatusDollarToRenew==null) {
                        StatusDollarToRenew ="";
                    }
                }

            //################################## End of crm:Membership block #################################


            //############################# getting values from crm:MemberCard block ###########################
            JSONArray crmMemberCard = null;
            try {
                crmMemberCard = (JSONArray) crmCRM.get("crm:MemberCard");
            } catch (Exception e) {
                System.out.println("The test was failed since the API response does not contain 'crm:MemberCard' Block. This means that expected structure of response have changed. Actual exception - "+e);
                _scenario.write("The test was failed since the API response does not contain 'crm:MemberCard' Block. This means that expected structure of response have changed. Actual exception - "+e);
                fail();
            }

            for (int i=0;i<crmMemberCard.size();i++){
                try {
                    JSONObject crmMemberCard2 = (JSONObject) crmMemberCard.get(i);
                    MemberInfoJsonObj = (JSONObject) crmMemberCard2.get("mc:CardInfo");
                } catch (Exception e) {
                    System.out.println("The test was failed since the API response does not contain 'mc:CardInfo' Block. This means that expected structure of response have changed. Actual exception - "+e);
                    _scenario.write("The test was failed since the API response does not contain 'mc:CardInfo' Block. This means that expected structure of response have changed. Actual exception - "+e);
                    fail();
                }

                try {
                    CardNo = MemberInfoJsonObj.get("mc:CardNo").toString();
                    if(CardNo==null){
                        CardNo="";
                    }
                } catch (Exception e) {
                    if(CardNo==null){
                        CardNo="";
                    }
                }
//                MembershipCardTypeCode = MemberInfoJsonObj.get("mc:MembershipCardTypeCode").toString();
                try {
                    MembershipcardStatusCode = MemberInfoJsonObj.get("mc:MembershipcardStatusCode").toString();
                    if(MembershipcardStatusCode==null){
                        MembershipcardStatusCode="";
                    }
                } catch (Exception e) {
                    if(MembershipcardStatusCode==null){
                        MembershipcardStatusCode="";
                    }
                }

                String balancePointsCount="";
                try {

                    balancePointsCount = MemberInfoJsonObj.get("mc:BalancePointsCount").toString();

                    if(balancePointsCount==null){
                        BalancePointCount="";
                    } else if(balancePointsCount.contains(".")){
                        int indexOfDecimalPointInPointBalance = balancePointsCount.indexOf(".");
                        BalancePointCount=balancePointsCount.substring(0,indexOfDecimalPointInPointBalance).trim();
                    } else {
                        BalancePointCount = balancePointsCount.trim();
                    }
                } catch (Exception e) {
                    if(balancePointsCount==null){
                        BalancePointCount="";
                    }
                }
//                PtsHoldingDays = MemberInfoJsonObj.get("mc:PtsHoldingDays").toString();
//                TotalPointsBAL =MemberInfoJsonObj.get("mc:TotalPointsBAL").toString();
//                IssueDateKey = MemberInfoJsonObj.get("mc:IssueDateKey").toString();
//                EffectiveDateKey = MemberInfoJsonObj.get("mc:EffectiveDateKey").toString();

                // === Reading values inside mc:RewardCycleLists --> mc:RewardCycleInfo
                JSONObject mcRewardCycleLists  = (JSONObject) MemberInfoJsonObj.get("mc:RewardCycleLists");
                JSONArray mcRewardCycleInfo = (JSONArray) mcRewardCycleLists.get("mc:RewardCycleInfo");

                for(int j=0;j<mcRewardCycleInfo.size();j++){
                    JSONObject mcRewardCycleInfo2 = (JSONObject) mcRewardCycleInfo.get(j);
                    ExpiringDate =mcRewardCycleInfo2.get("@ExpiringDate").toString().substring(0,10).trim();
                }

            }
            //############################# End of crm:MemberCard block ###########################


        } else  if (statusCode!=HttpStatus.SC_OK){
            System.out.println("POST request Error. Returned the status code "+ statusCode);
            _scenario.write("POST request Error. Returned the status code "+ statusCode);

        } else if (response.getBody().toString().isEmpty() || response.getBody().toString() ==""){
            System.out.println("The response body is null or its empty");
            _scenario.write("The response body is null or its empty");
        }
    }



    public void readDSAJsonNode(JSONObject jsonObject) throws IOException {

        readTestData testData = new readTestData();

        JSONObject crm = (JSONObject)jsonObject.get("CRM");
        JSONObject crmCRM = (JSONObject) crm.get("crm:CRM");

        //Retrieve Customer:MemberInfo JSON Object
        JSONArray crmCustomer = (JSONArray)crmCRM.get("crm:Customer");


        for (int k = 0; k < crmCustomer.size(); k++) {

            JSONObject crmCustomer2 = (JSONObject) crmCustomer.get(k);
            MemberInfoJsonObj = (JSONObject) crmCustomer2.get("cus:MemberInfo");

            //Setting values to string variables.
            try {
                TitleCode = MemberInfoJsonObj.get("cus:TitleCode").toString();
            } catch (Exception e) {
                TitleCode="";
            }

            try {
                GenderCode = MemberInfoJsonObj.get("cus:GenderCode").toString();
            } catch (Exception e) {
                GenderCode ="";
            }

            try {
                EmailAddressText = MemberInfoJsonObj.get("cus:EmailAddressText").toString();
            } catch (Exception e) {
                EmailAddressText="";
            }

            try {
                PostalCode = MemberInfoJsonObj.get("cus:PostalCode").toString();
            } catch (Exception e) {
                PostalCode ="";
            }

            try {
                CityNameText = MemberInfoJsonObj.get("cus:CityNameText").toString();
            } catch (Exception e) {
                CityNameText ="";
            }

            try {
                CountryNameText = MemberInfoJsonObj.get("cus:CountryNameText").toString();
            } catch (Exception e) {
                CountryNameText="";
            }

            try {
                ContactNumberText = MemberInfoJsonObj.get("cus:ContactNumberText").toString();
            } catch (Exception e) {
                ContactNumberText ="";
            }

            try {
                IsInvalidAddress = MemberInfoJsonObj.get("cus:IsInvalidAddress").toString();
            } catch (Exception e) {
                IsInvalidAddress ="";
            }

            try {
                IsInvalidEmail = MemberInfoJsonObj.get("cus:IsInvalidEmail").toString();
            } catch (Exception e) {
                IsInvalidEmail="";
            }

            try {
                IsEmptyEmail = MemberInfoJsonObj.get("cus:IsEmptyEmail").toString();
            } catch (Exception e) {
                IsEmptyEmail="";
            }

            try {
                EmailUnwillingToProvide = MemberInfoJsonObj.get("cus:EmailUnwillingToProvide").toString();
            } catch (Exception e) {
                EmailUnwillingToProvide="";
            }

            try {
                FirstName = MemberInfoJsonObj.get("cus:FirstName").toString();
            } catch (Exception e) {
                FirstName ="";
            }

            try {
                FirstNameNative = MemberInfoJsonObj.get("cus:FirstNameNative").toString();
            } catch (Exception e) {
                FirstNameNative ="";
            }

            try {
                LastName = MemberInfoJsonObj.get("cus:LastName").toString();
            } catch (Exception e) {
                LastName ="";
            }

            try {
                LastNameNative = MemberInfoJsonObj.get("cus:LastNameNative").toString();
            } catch (Exception e) {
                LastNameNative ="";
            }

            try {
                BirthDate = MemberInfoJsonObj.get("cus:BirthDate").toString();
            } catch (Exception e) {
                BirthDate ="";
            }

            try {
                BirthMonth = MemberInfoJsonObj.get("cus:BirthMonth").toString();
            } catch (Exception e) {
                BirthMonth="";
            }

            try {
                RegistrationDivisionCode = MemberInfoJsonObj.get("cus:RegistrationDivisionCode").toString();
            } catch (Exception e) {
                RegistrationDivisionCode="";
            }

            try {
                RegistrationLocationID = MemberInfoJsonObj.get("cus:RegistrationLocationID").toString();
            } catch (Exception e) {
                RegistrationLocationID="";
            }

            try {
                MailingAddress2 = MemberInfoJsonObj.get("cus:MailingAddress2").toString();
            } catch (Exception e) {
                MailingAddress2="";
            }

            try {
                ZipCode = MemberInfoJsonObj.get("cus:ZipCode").toString();
            } catch (Exception e) {
                ZipCode ="";
            }

            try {
                CorrCityNameText = MemberInfoJsonObj.get("cus:CorrCityNameText").toString();
            } catch (Exception e) {
                CorrCityNameText ="";
            }

            try {
                CorrStateNameText = MemberInfoJsonObj.get("cus:CorrStateNameText").toString();
            } catch (Exception e) {
                CorrStateNameText="";
            }

            try {
                MailingAddress1 = MemberInfoJsonObj.get("cus:MailingAddress1").toString();
            } catch (Exception e) {
                MailingAddress1="";
            }

            try {
                MailingAddress3 = MemberInfoJsonObj.get("cus:MailingAddress3").toString();
            } catch (Exception e) {
                MailingAddress3 ="";
            }

            try {
                ResCityNameText = MemberInfoJsonObj.get("cus:ResCityNameText").toString();
            } catch (Exception e) {
                ResCityNameText ="";
            }

            try {
                ResStateNameText = MemberInfoJsonObj.get("cus:ResStateNameText").toString();
            } catch (Exception e) {
                ResStateNameText ="";
            }

            try {
                EnglishCountry = MemberInfoJsonObj.get("cus:EnglishCountry").toString();
            } catch (Exception e) {
                EnglishCountry ="";
            }

            try {
                ValidMobileNo1 = MemberInfoJsonObj.get("cus:ValidMobileNo1").toString();
            } catch (Exception e) {
                ValidMobileNo1 ="";
            }

            try {
                SpokenLanguage = MemberInfoJsonObj.get("cus:SpokenLanguage").toString();
            } catch (Exception e) {
                SpokenLanguage ="";
            }

            try {
                IsContactable = MemberInfoJsonObj.get("cus:IsContactable").toString();
            } catch (Exception e) {
                IsContactable="";
            }

            try {
                CustomerRegistrationDatetime = MemberInfoJsonObj.get("cus:CustomerRegistrationDatetime").toString().substring(0,10);
            } catch (Exception e) {
                CustomerRegistrationDatetime="";
            }

            try {
                CustomerLeisureActivity = MemberInfoJsonObj.get("cus:CustomerLeisureActivity").toString();
            } catch (Exception e) {
                CustomerLeisureActivity="";
            }

            try {
                CustomerShoppingPreference = MemberInfoJsonObj.get("cus:CustomerShoppingPreference").toString();
            } catch (Exception e) {
                CustomerShoppingPreference="";
            }

            try {
                CustomerPreferredBrands = MemberInfoJsonObj.get("cus:CustomerPreferredBrands").toString();
            } catch (Exception e) {
                CustomerPreferredBrands="";
            }

            try {
                LeisureActivitiesMultiple = MemberInfoJsonObj.get("cus:LeisureActivitiesMultiple").toString();
            } catch (Exception e) {
                LeisureActivitiesMultiple="";
            }

            try {
                ShoppingPreferencesMultiple = MemberInfoJsonObj.get("cus:ShoppingPreferencesMultiple").toString();
            } catch (Exception e) {
                ShoppingPreferencesMultiple="";
            }

            try {
                PreferredBrandsMultiple = MemberInfoJsonObj.get("cus:PreferredBrandsMultiple").toString();
            } catch (Exception e) {
                PreferredBrandsMultiple="";
            }

            try {
                AgeRange = MemberInfoJsonObj.get("cus:AgeRange").toString();
            } catch (Exception e) {
                AgeRange ="";
            }

            try {
                MarketingSource = MemberInfoJsonObj.get("cus:MarketingSource").toString();
            } catch (Exception e) {
                MarketingSource ="";
            }

            try {
                ZipCodeValidFlag = MemberInfoJsonObj.get("cus:ZipCodeValidFlag").toString();
            } catch (Exception e) {
                ZipCodeValidFlag ="";
            }

            try {
                CardTier = MemberInfoJsonObj.get("cus:CardTier").toString();
            } catch (Exception e) {
                CardTier ="";
            }

            try {
                CardPickupMethod = MemberInfoJsonObj.get("cus:CardPickupMethod").toString();
            } catch (Exception e) {
                CardPickupMethod="";
            }

            try {
                IpadID = MemberInfoJsonObj.get("cus:IpadID").toString();
            } catch (Exception e) {
                IpadID="";
            }

            try {
                StaffID = MemberInfoJsonObj.get("cus:StaffID").toString();
            } catch (Exception e) {
                StaffID ="";
            }

            try {
                MarketingSourceOthers = MemberInfoJsonObj.get("cus:MarketingSourceOthers").toString();
            } catch (Exception e) {
                MarketingSourceOthers ="";
            }

            try {
                Mobile1AreaCode = MemberInfoJsonObj.get("cus:Mobile1AreaCode").toString();
            } catch (Exception e) {
                Mobile1AreaCode="";
            }

            try {
                CreationDate = MemberInfoJsonObj.get("cus:CreationDate").toString().substring(0,10);
            } catch (Exception e) {
                CreationDate="";
            }
            try {
                UpdateDate = MemberInfoJsonObj.get("cus:UpdateDate").toString().substring(0,10);
            } catch (Exception e) {
                UpdateDate="";
            }

            try {
                CreationUserID = MemberInfoJsonObj.get("cus:CreationUserID").toString();
            } catch (Exception e) {
                CreationUserID ="";
            }

            try {
                UpdateUserID = MemberInfoJsonObj.get("cus:UpdateUserID").toString();
            } catch (Exception e) {
                UpdateUserID ="";
            }

            try {
                SourceSystem = MemberInfoJsonObj.get("cus:SourceSystem").toString();
            } catch (Exception e) {
                SourceSystem="";
            }

            try {
                Override = MemberInfoJsonObj.get("cus:Override").toString();
            } catch (Exception e) {
                Override ="";
            }

            try {
                ContactDetails = MemberInfoJsonObj.get("cus:ContactDetails").toString();
            } catch (Exception e) {
                ContactDetails ="";
            }


        }

        //Reading the inner values of contact details
        JSONArray obj = (JSONArray) MemberInfoJsonObj.get("cus:ContactDetails");
        for (int i=0;i<obj.size();i++){
            JSONObject rec = (JSONObject) obj.get(i);

            try {
                ContactType = rec.get("cus:ContactType").toString();
            } catch (Exception e) {
                ContactType ="";
            }

            try {
                ContactDetail = rec.get("cus:ContactDetail").toString();
            } catch (Exception e) {
                ContactDetail ="";
            }
        }


        //Retrieve CardInfoJsonObj JSON Object
        JSONArray crmMemberCard = (JSONArray)crmCRM.get("crm:MemberCard");
        for (int k = 0; k < crmMemberCard.size(); k++) {

            JSONObject crmMemberCard2 = (JSONObject) crmMemberCard.get(k);
            CardInfoJsonObj = (JSONObject) crmMemberCard2.get("mc:CardInfo");
            //System.out.println(CardInfoJsonObj);
            try {
                CardNo = CardInfoJsonObj.get("mc:CardNo").toString();
            } catch (Exception e) {
                CardNo ="";
            }


        }
        //Retrieve POSJsonObj JSON Object
        JSONObject crmLoyaltyService = (JSONObject)crmCRM.get("crm:loyaltyService");
        JSONObject ltscommon = (JSONObject)crmLoyaltyService.get("lts:common");
        POSJsonObj = (JSONObject)ltscommon.get("lts:POS");
        //System.out.println(POSJsonObj);
        try {
            OutletCode = POSJsonObj.get("pos:OutletCode").toString();
        } catch (Exception e) {
            OutletCode ="";
        }

        try {
            CashierID = POSJsonObj.get("pos:CashierID").toString();
        } catch (Exception e) {
            CashierID="";
        }


    }

public void readETRJsonNode(JSONObject jsonObject) throws Exception {
    readTestData testData = new readTestData();

    JSONObject crm = (JSONObject)jsonObject.get("CRM");
    JSONObject crmCRM = (JSONObject) crm.get("crm:CRM");

    //Retrieve Customer:MemberInfo JSON Object
    JSONArray crmCustomer = (JSONArray)crmCRM.get("crm:Customer");


    for (int k = 0; k < crmCustomer.size(); k++) {

        JSONObject crmCustomer2 = (JSONObject) crmCustomer.get(k);
        MemberInfoJsonObj = (JSONObject) crmCustomer2.get("cus:MemberInfo");

        //Setting values to string variables.
        try {
            TitleCode = MemberInfoJsonObj.get("cus:TitleCode").toString();
        } catch (NullPointerException e) {
            TitleCode="";
        }

        try {
            GenderCode = MemberInfoJsonObj.get("cus:GenderCode").toString();
        } catch (NullPointerException e) {
            GenderCode="";
        }

        try {
            EmailAddressText = MemberInfoJsonObj.get("cus:EmailAddressText").toString();
        } catch (NullPointerException e) {
            EmailAddressText="";
        }

        try {
            ContactNumberText = MemberInfoJsonObj.get("cus:ContactNumberText").toString();
        } catch (NullPointerException e) {
            ContactNumberText="";
        }

        try {
            IsInvalidEmail = MemberInfoJsonObj.get("cus:IsInvalidEmail").toString();
        } catch (NullPointerException e) {
            IsInvalidEmail="";
        }

        try {
            IsEmptyEmail = MemberInfoJsonObj.get("cus:IsEmptyEmail").toString();
        } catch (NullPointerException e) {
            IsEmptyEmail="";
        }

        try {
            FirstName = MemberInfoJsonObj.get("cus:FirstName").toString();
        } catch (NullPointerException e) {
            FirstName="";
        }

        try {
            LastName = MemberInfoJsonObj.get("cus:LastName").toString();
        } catch (NullPointerException e) {
            LastName="";
        }

        try {
            NativeSalutation = MemberInfoJsonObj.get("cus:NativeSalutation").toString();
        } catch (NullPointerException e) {
            NativeSalutation="";
        }

        try {
            Mobile1AreaCode = MemberInfoJsonObj.get("cus:Mobile1AreaCode").toString();
        } catch (NullPointerException e) {
            Mobile1AreaCode="";
        }

        try {
            ValidMobileNo1 = MemberInfoJsonObj.get("cus:ValidMobileNo1").toString();
        } catch (NullPointerException e) {
            ValidMobileNo1="";
        }

        try {
            EnglishCountry = MemberInfoJsonObj.get("cus:EnglishCountry").toString();
        } catch (NullPointerException e) {
            EnglishCountry ="";
        }

        try {
            SpokenLanguage = MemberInfoJsonObj.get("cus:SpokenLanguage").toString();
        } catch (NullPointerException e) {
            SpokenLanguage ="";
        }

        try {
            IsContactable = MemberInfoJsonObj.get("cus:IsContactable").toString();
        } catch (NullPointerException e) {
            IsContactable ="";
        }

        try {
            CardTier = MemberInfoJsonObj.get("cus:CardTier").toString();
        } catch (NullPointerException e) {
            CardTier ="";
        }

        try {
            CardPickupMethod = MemberInfoJsonObj.get("cus:CardPickupMethod").toString();
        } catch (NullPointerException e) {
            CardPickupMethod ="";
        }

        try {
            SourceSystem = MemberInfoJsonObj.get("cus:SourceSystem").toString();
        } catch (NullPointerException e) {
            SourceSystem ="";
        }

        try {
            Override = MemberInfoJsonObj.get("cus:Override").toString();
        } catch (NullPointerException e) {
            Override ="";
        }

        try {
            RegistrationDivisionCode = MemberInfoJsonObj.get("cus:RegistrationDivisionCode").toString();
        } catch (NullPointerException e) {
            RegistrationDivisionCode ="";
        }

        try {
            RegistrationLocationID = MemberInfoJsonObj.get("cus:RegistrationLocationID").toString();
        } catch (NullPointerException e) {
            RegistrationLocationID ="";
        }

        try {
            CustomerRegistrationDatetime = MemberInfoJsonObj.get("cus:CustomerRegistrationDatetime").toString().substring(0,10);
        } catch (NullPointerException e) {
            CustomerRegistrationDatetime ="";
        }

        try {
            AgeRange = MemberInfoJsonObj.get("cus:AgeRange").toString();
        } catch (NullPointerException e) {
            AgeRange ="";
        }

    }


    //Retrieve CardInfoJsonObj JSON Object
    JSONArray crmMemberCard = (JSONArray)crmCRM.get("crm:MemberCard");
    for (int k = 0; k < crmMemberCard.size(); k++) {

        JSONObject crmMemberCard2 = (JSONObject) crmMemberCard.get(k);
        CardInfoJsonObj = (JSONObject) crmMemberCard2.get("mc:CardInfo");
        //System.out.println(CardInfoJsonObj);
        try {
            CardNo = CardInfoJsonObj.get("mc:CardNo").toString();
        } catch (NullPointerException e) {
            CardNo ="";
        }

        try {
            MembershipCardTypeCode = CardInfoJsonObj.get("mc:MembershipCardTypeCode").toString();
        } catch (NullPointerException e) {
            MembershipCardTypeCode="";
        }


    }
    //Retrieve POSJsonObj JSON Object
    JSONObject crmLoyaltyService = (JSONObject)crmCRM.get("crm:loyaltyService");
    JSONObject ltscommon = (JSONObject)crmLoyaltyService.get("lts:common");
    POSJsonObj = (JSONObject)ltscommon.get("lts:POS");
    //System.out.println(POSJsonObj);
    try {
        OutletCode = POSJsonObj.get("pos:OutletCode").toString();
    } catch (NullPointerException e) {
        OutletCode ="";
    }

    try {
        CashierID = POSJsonObj.get("pos:CashierID").toString();
    } catch (NullPointerException e) {
        CashierID ="";
    }


}

public void readPOSJsonNode(JSONObject jsonObject) throws Exception{

    JSONObject crm = (JSONObject)jsonObject.get("CRM");
    JSONObject crmCRM = (JSONObject) crm.get("crm:CRM");

    //Retrieve Customer:MemberInfo JSON Object
    JSONArray crmCustomer = (JSONArray)crmCRM.get("crm:Customer");


    for (int k = 0; k < crmCustomer.size(); k++) {

        JSONObject crmCustomer2 = (JSONObject) crmCustomer.get(k);
        MemberInfoJsonObj = (JSONObject) crmCustomer2.get("cus:MemberInfo");

        //Setting values to string variables.
        TitleCode = MemberInfoJsonObj.get("cus:TitleCode").toString();
        GenderCode = MemberInfoJsonObj.get("cus:GenderCode").toString();
        EmailAddressText = MemberInfoJsonObj.get("cus:EmailAddressText").toString();
        ContactNumberText = MemberInfoJsonObj.get("cus:ContactNumberText").toString();
        CardPickupMethod = MemberInfoJsonObj.get("cus:CardPickupMethod").toString();
        Mobile1AreaCode = MemberInfoJsonObj.get("cus:Mobile1AreaCode").toString();
        CardTier = MemberInfoJsonObj.get("cus:CardTier").toString();
        IsContactable = MemberInfoJsonObj.get("cus:IsContactable").toString();
        FirstNameNative = MemberInfoJsonObj.get("cus:FirstNameNative").toString();
        FirstName = MemberInfoJsonObj.get("cus:FirstName").toString();
        LastName = MemberInfoJsonObj.get("cus:LastName").toString();
        CountryNameText = MemberInfoJsonObj.get("cus:CountryNameText").toString();
        RegistrationDivisionCode = MemberInfoJsonObj.get("cus:RegistrationDivisionCode").toString();
        RegistrationLocationID = MemberInfoJsonObj.get("cus:RegistrationLocationID").toString();
        SpokenLanguage = MemberInfoJsonObj.get("cus:SpokenLanguage").toString();
        SourceSystem = MemberInfoJsonObj.get("cus:SourceSystem").toString();
        AgeRange = MemberInfoJsonObj.get("cus:AgeRange").toString();
        CreationUserID = MemberInfoJsonObj.get("cus:CreationUserID").toString();
        try {
            CustomerRegistrationDatetime = MemberInfoJsonObj.get("cus:CustomerRegistrationDatetime").toString().substring(0,10);
        } catch (Exception e) {
            CustomerRegistrationDatetime="";
        }


    }


    //Retrieve CardInfoJsonObj JSON Object
    JSONArray crmMemberCard = (JSONArray)crmCRM.get("crm:MemberCard");
    for (int k = 0; k < crmMemberCard.size(); k++) {

        JSONObject crmMemberCard2 = (JSONObject) crmMemberCard.get(k);
        CardInfoJsonObj = (JSONObject) crmMemberCard2.get("mc:CardInfo");
        //System.out.println(CardInfoJsonObj);
        CardNo = CardInfoJsonObj.get("mc:CardNo").toString();



    }
    //Retrieve POSJsonObj JSON Object
    JSONObject crmLoyaltyService = (JSONObject)crmCRM.get("crm:loyaltyService");
    JSONObject ltscommon = (JSONObject)crmLoyaltyService.get("lts:common");
    POSJsonObj = (JSONObject)ltscommon.get("lts:POS");
    //System.out.println(POSJsonObj);
    OutletCode = POSJsonObj.get("pos:OutletCode").toString();
    CashierID = POSJsonObj.get("pos:CashierID").toString();

}

    public void getElementValuesFromDSAJsonFile(String env){
    try {

        readTestData testData = new readTestData();

        JSONParser jsonParser = new JSONParser();

        File file = new File(testData.readTestData(getDSAjsonPath(env)));

        Object object = jsonParser.parse(new FileReader(file));

        jsonObject = (JSONObject) object;
        readDSAJsonNode(jsonObject);
        // parseJson(jsonObject);

    } catch (Exception ex) {
        ex.printStackTrace();
    }
}

public void getElementValuesFromAnyDSAJsonFile(String testDataTag, String env) {
    try {
        readTestData testData = new readTestData();

        //getting the filepath
        if(testDataTag==null || testDataTag.isEmpty()){
            System.out.println("Provided test data tag is Null or empty");
            throw new InterruptedException ("Provided test data tag is Null or empty");
        }

        JSONParser jsonParser = new JSONParser();

        File file = new File(testData.readTestData(getDSAmemberContactDetailsUpdateJsonPath(testDataTag,env)));

        Object object = jsonParser.parse(new FileReader(file));

        jsonObject = (JSONObject) object;
        readDSAJsonNode(jsonObject);
        // parseJson(jsonObject);
} catch (Exception ex) {
        ex.printStackTrace();
    }
}

public void getElementValuesFromETRJsonFile(String env) {
    try {

        readTestData testData = new readTestData();

        JSONParser jsonParser = new JSONParser();

        File file = new File(testData.readTestData(getETRjsonPath(env)));

        Object object = jsonParser.parse(new FileReader(file));

        jsonObject = (JSONObject) object;
        readETRJsonNode(jsonObject);
        // parseJson(jsonObject);

    } catch (Exception ex) {
        ex.printStackTrace();
    }
}

public void getElementValuesFromUpdateETRJsonFile(String env) {
    try {

        readTestData testData = new readTestData();

        JSONParser jsonParser = new JSONParser();

        File file = new File(testData.readTestData(getUpdateETRjsonPath(env)));

        Object object = jsonParser.parse(new FileReader(file));

        jsonObject = (JSONObject) object;
        readETRJsonNode(jsonObject);
        // parseJson(jsonObject);

    } catch (Exception ex) {
        ex.printStackTrace();
    }
}


public void getElementValuesFromPOSJsonFile(String env) {
    try {

        readTestData testData = new readTestData();

        JSONParser jsonParser = new JSONParser();

        File file = new File(testData.readTestData(getPOSjsonPath(env)));

        Object object = jsonParser.parse(new FileReader(file));

        jsonObject = (JSONObject) object;
        readPOSJsonNode(jsonObject);
        // parseJson(jsonObject);

    } catch (Exception ex) {
        ex.printStackTrace();
    }
}

    public void verifyValuesInSF(){
        //go to salesforce and then read
    }

    public String getMemberIDFromMatrix(String environment, String cardNumber ) throws SQLException {
        String query = "select MemberID from MatrixTpReward.dbo.card where CardNo='"+cardNumber+"';";
        System.out.println("Qurry matrix : "+query);
        createDbConnection db = new createDbConnection();

        //executing the query and getting the resultset

        ResultSet resultSet = null;
        ResultSetMetaData rsmd=null;
        try {
            resultSet = db.queryDB(environment, query);
            rsmd = resultSet.getMetaData();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



        int columnCount = rsmd.getColumnCount();
        String memberID=null;

        if (resultSet!=null && columnCount>0) {
            while (resultSet.next()){
                for(int i=1; i<=columnCount;i++) {
                    if(resultSet.getString(i)!=null) {
                        try {
                            memberID = resultSet.getString(i).trim();
                        } catch (SQLException e) {
                            System.out.println("Unable to get MemberID value from the Matrix "+e.getLocalizedMessage());
                        }
                    } else {
                        System.out.println("MemberID field value is Null");
                        memberID=null;
                    }
                } //end of for loop

            } // end of while


            //Message when colleted data successfully
            System.out.println("Successfully collected data from Matrix");
            _scenario.write("Successfully collected data from Matrix");

        } else {
            System.out.println("Result set is empty");
            _scenario.write("Result set is empty");

        }
        System.out.println("#######  Member Id found in Matrix table - "+memberID);
        return memberID;
    }

    public String getReturnMessageFromCmdLogsInMatrix(String environment, String cardNo) throws SQLException {
        String query = "select TOP(1) ReturnMessage from MatrixCRM.dbo.CmdLog where CardNo in ('"+cardNo+"') and Command='DFS MEMBERSHIP REGISTRATION2' order by AddedOn desc";
        System.out.println("Qurry matrix : "+query);
        createDbConnection db = new createDbConnection();

        //executing the query and getting the resultset

        ResultSet resultSet = null;
        ResultSetMetaData rsmd=null;
        try {
            resultSet = db.queryDB(environment, query);
            rsmd = resultSet.getMetaData();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



        int columnCount = rsmd.getColumnCount();
        String ReturnMessage=null;

        if (resultSet!=null && columnCount>0) {
            while (resultSet.next()){
                for(int i=1; i<=columnCount;i++) {
                    if(resultSet.getString(i)!=null) {
                        try {
                            ReturnMessage = resultSet.getString(i).trim();
                        } catch (SQLException e) {
                            System.out.println("Unable to get Return Message from the Matrix CmdLogs "+e.getLocalizedMessage());
                        }
                    } else {
                        System.out.println("Return Message value is Null");
                        ReturnMessage="";
                    }
                } //end of for loop

            } // end of while


            //Message when colleted data successfully
            System.out.println("Successfully collected data from Matrix");
            _scenario.write("Successfully collected data from Matrix");

        } else {
            System.out.println("Result set is empty");
            _scenario.write("Result set is empty");

        }

        return ReturnMessage;
    }

    public String getReturnMessageFromCmdLogsInMatrixForCardEnquiry(String environment, String cardNo) throws SQLException {
        String query = "select TOP(1) ReturnMessage from MatrixCRM.dbo.CmdLog where CardNo in ('"+cardNo+"') and Command='DFS CARD ENQUIRY' order by AddedOn desc";
        System.out.println("Qurry matrix : "+query);
        createDbConnection db = new createDbConnection();

        //executing the query and getting the resultset

        ResultSet resultSet = null;
        ResultSetMetaData rsmd=null;
        try {
            resultSet = db.queryDB(environment, query);
            rsmd = resultSet.getMetaData();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



        int columnCount = rsmd.getColumnCount();
        String ReturnMessage=null;

        if (resultSet!=null && columnCount>0) {
            while (resultSet.next()){
                for(int i=1; i<=columnCount;i++) {
                    if(resultSet.getString(i)!=null) {
                        try {
                            ReturnMessage = resultSet.getString(i).trim();
                        } catch (SQLException e) {
                            System.out.println("Unable to get Return Message from the Matrix CmdLogs "+e.getLocalizedMessage());
                        }
                    } else {
                        System.out.println("Return Message value is Null");
                        ReturnMessage="";
                    }
                } //end of for loop

            } // end of while


            //Message when colleted data successfully
            System.out.println("Successfully collected data from Matrix");
            _scenario.write("Successfully collected data from Matrix");

        } else {
            System.out.println("Result set is empty");
            _scenario.write("Result set is empty");

        }

        return ReturnMessage;
    }

    public String getReturnMessageFromCmdLogsInMatrixAfterUpdate(String environment, String cardNo) throws SQLException {
        String query = "select TOP(1) ReturnMessage from MatrixCRM.dbo.CmdLog where CardNo in ('"+cardNo+"') and Command='DFS UPDATE PROFILE2' order by AddedOn desc";
        System.out.println("Qurry matrix : "+query);
        createDbConnection db = new createDbConnection();

        //executing the query and getting the resultset

        ResultSet resultSet = null;
        ResultSetMetaData rsmd=null;
        try {
            resultSet = db.queryDB(environment, query);
            rsmd = resultSet.getMetaData();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



        int columnCount = rsmd.getColumnCount();
        String ReturnMessage=null;

        if (resultSet!=null && columnCount>0) {
            while (resultSet.next()){
                for(int i=1; i<=columnCount;i++) {
                    if(resultSet.getString(i)!=null) {
                        try {
                            ReturnMessage = resultSet.getString(i).trim();
                        } catch (SQLException e) {
                            System.out.println("Unable to get Return Message from the Matrix CmdLogs "+e.getLocalizedMessage());
                        }
                    } else {
                        System.out.println("Return Message value is Null");
                        ReturnMessage="";
                    }
                } //end of for loop

            } // end of while


            //Message when colleted data successfully
            System.out.println("Successfully collected data from Matrix");
            _scenario.write("Successfully collected data from Matrix");

        } else {
            System.out.println("Result set is empty");
            _scenario.write("Result set is empty");

        }

        return ReturnMessage;
    }

    public HashMap<String, String> readMemberDataFromMatrix (String environment, String cardNumber) throws ClassNotFoundException, SQLException {

        String query = "select GenericStr1 AS FirstName,GenericStr6 AS LastName,S1.Name AS " +
                "Salutation,GenericStr8 AS NativeSalutation,Email,GenericStr4 AS FirstNameNative," +
                "GenericStr7 AS LastNameNative,AreaCode AS MobileAreaCode,MobileNo AS MobileNo,S4.Name " +
                "AS SpokenLanguageCode,S.Name AS Country,S7.Name AS AgeRange,'' as EnrolmentType,'' " +
                "AS ActualTier,GenericStr48 AS IssuedCardTier,GenericStr44 AS CardPickupMethod,GenericStr18 " +
                "AS EnglishAddress1,GenericStr19 AS EnglishAddress2,GenericStr24 AS EnglishAddress3,GenericStr23 " +
                "AS NativeAddress1,AddressOthers2,AddressOthers3,GenericStr26 " +
                "AS EnglishCity,GenericStr20 AS ZipCode,GenericStr39 AS IsContactable,GenericStr15 " +
                "AS RegistrationLocationID,GenericDate1 AS CustomerRegistrationDatetime,C.MembershipStatusCode " +
                "AS CardStatus,GenericStr45 AS Staff,M.AutoID AS AutoID,Gender AS Gender,MaritalStatus " +
                "AS MaritalStatus,Email AS Email,DateofBirth AS DateofBirth, PostalCode AS PostalCode, ContactNo " +
                "AS ContactNo,InvalidAddress AS InvalidAddress,IsInvalidEmail AS IsInvalidEmail, IsEmptyEmail " +
                "AS IsEmptyEmail,IsDNWTP AS IsDNWTP,GenericStr9 AS BirthDate,GenericStr10 AS BirthMonth,GenericStr11 " +
                "AS BirthYear,S2.Name AS CustomerEnrolmentSource,GenericStr13 AS WechatID,GenericStr14 " +
                "AS RegistrationDivisionCode,GenericStr16 AS RegistrationLocationCode, GenericStr17 AS HomeNo,GenericStr21 " +
                "AS NativeCity, GenericStr22 AS NativeState,GenericStr25 AS isOptOutResearch,GenericStr27 AS EnglishState,S3.Name " +
                "AS EnglishCountry, GenericStr29 AS SuspendedStatus, GenericStr30 AS GenericStr30,GenericStr31 AS GenericStr31, GenericStr32 " +
                "AS ValidMobileNo1, GenericStr33 AS ValidHomeNo, GenericStr34 AS ValidOfficeNo,GenericStr35 " +
                "AS ValidFaxNo,GenericStr36 AS MobileNo2,GenericStr38 AS CustomerIsContactableFlag,GenericStr40 " +
                "AS OpenToSurveyFlag,S5.Name AS ChildrenCountRangeCode,GenericStr42 AS HomePhoneAreaCode,GenericStr43 " +
                "AS OfficePhoneAreacode,GenericStr45 AS GenericStr45, GenericStr46 AS PortalActivationFlag,GenericStr47 " +
                "AS IpadID,GenericStr49 AS SourceSystem, GenericStr50 AS IsVCardDownloaded,GenericDate2 " +
                "AS DateforDirectMarketing,GenericDate3 AS DateforMarketingResearch,GenericDate10 AS DateforTC,GenericStrAry1 " +
                "AS Remarks,GenericStrAry5 AS CustomerLeisureActivity, GenericStrAry6 AS CustomerShoppingPreference,GenericStrAry7 " +
                "AS CustomerPreferredBrands, GenericStrAry8 AS LeisureActivitiesMultiple,GenericStrAry9 AS ShoppingPreferencesMultiple,GenericStrAry10 " +
                "AS PreferredBrandsMultiple,M.AddedBy AS AddedBy,M.AddedOn AS AddedOn,M.ModifiedBy AS ModifiedBy,M.ModifiedOn " +
                "AS ModifiedOn,M.DeletedBy AS DeletedBy, M.DeletedOn AS DeletedOn,S6.Name " +
                "AS MarketingSource,IsEmailOptOut,IsOptOutMobile1,IsValidMobile2,IsOptOutMobile2,MobileNoArea2,IsOptOutHomePhone,IsOptOutWorkPhone," +
                "IsOptOutAddressEnglish,IsValidZipCode,IsValidAddressOthers,IsOptOutAddressOthers,MarketingSourceOthers from " +
                "MatrixTpReward.dbo.Member M left join MatrixTpReward.dbo.SystemCode S on S.Code=M.Country left join MatrixTpReward.dbo.SystemCode S1 on S1.Code=M.Salutation " +
                "left join MatrixTpReward.dbo.SystemCode S2 on S2.Code=M.GenericStr12 left join MatrixTpReward.dbo.SystemCode S3 on S3.Code=M.GenericStr28 " +
                "left join MatrixTpReward.dbo.SystemCode S4 on S4.Code=M.GenericStr37 left join MatrixTpReward.dbo.SystemCode S5 on S5.Code=M.GenericStr41 " +
                "left join MatrixTpReward.DFS.DFS_Member DM on DM.Member_AutoID=M.AutoID left join MatrixTpReward.dbo.SystemCode S6 On S6.ParentCode=916 " +
                "and S6.Code=CAST(DM.MarketingSource AS VARCHAR(5)) left join MatrixTpReward.dbo.SystemCode S7 On S7.ParentCode=905 " +
                "and S7.Code=CAST(DM.AgeRange AS VARCHAR(5)) inner join MatrixTpReward.dbo.Card C on C.MemberID=M.ID  where C.CardNo IN ('"+cardNumber+"');";

        createDbConnection db = new createDbConnection();

        //executing the query and getting the resultset

        ResultSet resultSet = null;
        ResultSetMetaData rsmd=null;
        try {
            resultSet = db.queryDB(environment, query);
            rsmd = resultSet.getMetaData();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



        int columnCount = rsmd.getColumnCount();
        HashMap<String, String> dataset = new HashMap<>();

        if (resultSet!=null && columnCount>0) {
            while (resultSet.next()){
                for(int i=1; i<=columnCount;i++) {
                    //System.out.println(rsmd.getColumnName(i) + " : " + resultSet.getString(i) + "\n");
                    String nativeSalutationConvertedValue="";
                    if(resultSet.getString(i)!=null) {
                        if(rsmd.getColumnName(i).equalsIgnoreCase("NativeSalutation")) {
                            nativeSalutationConvertedValue = convertNativeSalutation(environment, resultSet.getString(i).trim());
                            //updating the value in dataset
                            dataset.put(rsmd.getColumnName(i),nativeSalutationConvertedValue);
                        } else {
                            dataset.put(rsmd.getColumnName(i), resultSet.getString(i));
                        }

                    } else {
                        dataset.put(rsmd.getColumnName(i), "");
                    }
                    // printing out the data collected from Matrix
                    System.out.println(rsmd.getColumnName(i) +" : "+dataset.get(rsmd.getColumnName(i)));
                    _scenario.write(rsmd.getColumnName(i) +" : "+dataset.get(rsmd.getColumnName(i)));

                } //end of for loop

            } // end of while


            //Message when colleted data successfully
            System.out.println("Successfully collected data from Matrix");
            _scenario.write("Successfully collected data from Matrix");

        } else {
            System.out.println("Result set is empty");
            _scenario.write("Result set is empty");

        }

        return dataset;


    }

    public HashMap<String, String> readIsProcessedFromStagingDB (String environment, String minimumValue, String maximumValue) throws ClassNotFoundException, SQLException {

        String query = "select isProcessed from dfsstaging.dbo.PreloadCards where CRR_MIN_NUM='"+ minimumValue+"' and CRR_MAX_NUM='"+maximumValue+"';";

        createDbConnection db = new createDbConnection();

        //executing the query and getting the resultset

        ResultSet resultSet = null;
        ResultSetMetaData rsmd=null;
        try {
            resultSet = db.queryDB(environment, query);
            rsmd = resultSet.getMetaData();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



        int columnCount = rsmd.getColumnCount();
        HashMap<String, String> dataset = new HashMap<>();

        if (resultSet!=null && columnCount>0) {
            while (resultSet.next()){
                for(int i=1; i<=columnCount;i++) {
                    //System.out.println(rsmd.getColumnName(i) + " : " + resultSet.getString(i) + "\n");
                    String nativeSalutationConvertedValue="";
                    if(resultSet.getString(i)!=null) {
                        if(rsmd.getColumnName(i).equalsIgnoreCase("NativeSalutation")) {
                            nativeSalutationConvertedValue = convertNativeSalutation(environment, resultSet.getString(i).trim());
                            //updating the value in dataset
                            dataset.put(rsmd.getColumnName(i),nativeSalutationConvertedValue);
                        } else {
                            dataset.put(rsmd.getColumnName(i), resultSet.getString(i));
                        }

                    } else {
                        dataset.put(rsmd.getColumnName(i), "");
                    }
                    // printing out the data collected from Matrix
                    System.out.println(rsmd.getColumnName(i) +" : "+dataset.get(rsmd.getColumnName(i)));
                    _scenario.write(rsmd.getColumnName(i) +" : "+dataset.get(rsmd.getColumnName(i)));

                } //end of for loop

            } // end of while


            //Message when colleted data successfully
            System.out.println("Successfully collected data from staging");
            _scenario.write("Successfully collected data from staging");

        } else {
            System.out.println("Result set is empty");
            _scenario.write("Result set is empty");

        }

        return dataset;


    }

    public String convertNativeSalutation(String environment, String nativeSalutationCode) throws SQLException {

        String NativeSalutationCode = nativeSalutationCode;

        //Pass the NativeSalutationCode into another query and get the actual character
        String getNativeSalutationQuery = "select description from  MatrixTpReward.dfs.GenderSalutation WITH (NOLOCK) where salutationcode IN ('" + NativeSalutationCode + "');";

        //creating new DB connection object
        createDbConnection db = new createDbConnection();

        ResultSet resultSetNative = null;
        ResultSetMetaData rsmdNative=null;
        String nativeSalutation=null;
        try {
            resultSetNative = db.queryDB(environment, getNativeSalutationQuery);
            rsmdNative = resultSetNative.getMetaData();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        assert rsmdNative != null;
        int columnCountNative = rsmdNative.getColumnCount();

        if (resultSetNative!=null && columnCountNative>0) {
            while (resultSetNative.next()){
                for(int i=1; i<=columnCountNative;i++) {
                    //System.out.println(rsmd.getColumnName(i) + " : " + resultSet.getString(i) + "\n");
                    if(resultSetNative.getString(i)!=null) {
                        nativeSalutation = resultSetNative.getString(i);
                    } else {
                        nativeSalutation="";
                    }
                    // printing out the data collected from Matrix
                    System.out.println("NativeSalutationCode : "+nativeSalutationCode + " is converted to NativeSalutation : "+nativeSalutation);


                } //end of for loop

            } // end of while

        } else {
            System.out.println("No matching result for given NativeSalutation code was found. Result set is empty");
        }
        return nativeSalutation;
    }



}
