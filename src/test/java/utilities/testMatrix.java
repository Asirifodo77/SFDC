package utilities;

import cucumber.api.Scenario;
import org.testng.annotations.Test;
import page_objects.DSAcreateMember_pageObjects;
import step_definitions.Hooks;

import java.sql.SQLException;
import java.util.HashMap;

import static org.testng.Assert.assertEquals;

public class testMatrix {
    public Scenario _scenario;
    public DSAcreateMember_pageObjects matrix = new DSAcreateMember_pageObjects(_scenario);
    public HashMap<String, String> matrixData;
    public DSAcreateMember_pageObjects DSAcreateMember;

    @Test
    public void testMatrixdb() throws SQLException, ClassNotFoundException {
        _scenario = Hooks._scenario;
        DSAcreateMember = new DSAcreateMember_pageObjects(_scenario);
        matrixData = new HashMap<>();

        matrix.readMemberDataFromMatrix("Preprod","520000561281");
        //Assertions in matrix
        assertEquals(matrixData.get("Salutation"),DSAcreateMember.TitleCode);
        assertEquals(matrixData.get("Gender"),DSAcreateMember.GenderCode);
        assertEquals(matrixData.get("Email"),DSAcreateMember.EmailAddressText);
        assertEquals(matrixData.get("Country"),DSAcreateMember.CountryNameText);
        assertEquals(matrixData.get("MobileNo"),DSAcreateMember.ContactNumberText);
        assertEquals(matrixData.get("IsInvalidEmail"),DSAcreateMember.IsInvalidEmail);

        //==============Commenting out this bacuase - Salesforce checkbox is always unchecked =================
        // assertEquals(Runtime_TestData.EmailUnwillingToProvide,DSAcreateMember.EmailUnwillingToProvide);

        assertEquals(matrixData.get("IsEmptyEmail"),DSAcreateMember.IsEmptyEmail);
        assertEquals(matrixData.get("FirstName"),DSAcreateMember.FirstName);
        assertEquals(matrixData.get("FirstNameNative"),DSAcreateMember.FirstNameNative);
        assertEquals(matrixData.get("LastName"),DSAcreateMember.LastName);
        assertEquals(matrixData.get("LastNameNative"),DSAcreateMember.LastNameNative);
        assertEquals(matrixData.get("RegistrationDivisionCode"),DSAcreateMember.RegistrationDivisionCode);

        assertEquals(matrixData.get("EnglishCountry"),DSAcreateMember.EnglishCountry);

        //Igonoring RegistrationLocationID because it always sends null by SF.
        // assertEquals(Runtime_TestData.RegistrationLocationID,DSAcreateMember.RegistrationLocationID);

        assertEquals(matrixData.get("ValidMobileNo1"),DSAcreateMember.ValidMobileNo1);

        assertEquals(matrixData.get("SpokenLanguageCode"),DSAcreateMember.SpokenLanguage);


        assertEquals(matrixData.get("IsContactable"),DSAcreateMember.IsContactable);
        assertEquals(matrixData.get("CustomerRegistrationDatetime").substring(0,10),DSAcreateMember.CustomerRegistrationDatetime);
        assertEquals(matrixData.get("AgeRange"),DSAcreateMember.AgeRange);

        //assertEquals(matrixData.get("MarketingSource"),DSAcreateMember.MarketingSource);

        assertEquals(matrixData.get("IssuedCardTier"),DSAcreateMember.CardTier);
        assertEquals(matrixData.get("CardPickupMethod"),DSAcreateMember.CardPickupMethod);
        assertEquals(matrixData.get("MobileAreaCode"),DSAcreateMember.Mobile1AreaCode);

        assertEquals(matrixData.get("SourceSystem"),DSAcreateMember.SourceSystem);

        assertEquals(matrixData.get("PostalCode"),DSAcreateMember.PostalCode);
        assertEquals(matrixData.get("EnglishState"),DSAcreateMember.CityNameText);
        assertEquals(matrixData.get("Country"),DSAcreateMember.CountryNameText);
        assertEquals(matrixData.get("InvalidAddress"),DSAcreateMember.IsInvalidAddress);
        assertEquals(matrixData.get("IsDNWTP"),DSAcreateMember.EmailUnwillingToProvide);
        assertEquals(matrixData.get("BirthDate"),DSAcreateMember.BirthDate);
        assertEquals(matrixData.get("BirthMonth"),DSAcreateMember.BirthMonth);
        assertEquals(matrixData.get("EnglishAddress2"),DSAcreateMember.MailingAddress2);
        assertEquals(matrixData.get("ZipCode"),DSAcreateMember.ZipCode);
        assertEquals(matrixData.get("NativeCity"),DSAcreateMember.CorrCityNameText);
        assertEquals(matrixData.get("NativeState"),DSAcreateMember.CorrStateNameText);
        assertEquals(matrixData.get("EnglishAddress1"),DSAcreateMember.MailingAddress1);
        assertEquals(matrixData.get("EnglishAddress3"),DSAcreateMember.MailingAddress3);
        assertEquals(matrixData.get("EnglishCity"),DSAcreateMember.ResCityNameText);
        assertEquals(matrixData.get("EnglishState"),DSAcreateMember.ResStateNameText);
        assertEquals(matrixData.get("CustomerLeisureActivity"),DSAcreateMember.CustomerLeisureActivity);
        assertEquals(matrixData.get("CustomerShoppingPreference"),DSAcreateMember.CustomerShoppingPreference);
        assertEquals(matrixData.get("CustomerPreferredBrands"),DSAcreateMember.CustomerPreferredBrands);
        assertEquals(matrixData.get("LeisureActivitiesMultiple"),DSAcreateMember.LeisureActivitiesMultiple);
        assertEquals(matrixData.get("ShoppingPreferencesMultiple"),DSAcreateMember.ShoppingPreferencesMultiple);
        assertEquals(matrixData.get("PreferredBrandsMultiple"),DSAcreateMember.PreferredBrandsMultiple);
        assertEquals(matrixData.get("MarketingSource"),DSAcreateMember.MarketingSource);
        assertEquals(matrixData.get("IsValidZipCode"),DSAcreateMember.ZipCodeValidFlag);
        assertEquals(matrixData.get("IpadID"),DSAcreateMember.IpadID);
        assertEquals(matrixData.get("Staff"),DSAcreateMember.StaffID);
        assertEquals(matrixData.get("MarketingSourceOthers"),DSAcreateMember.MarketingSourceOthers);
        assertEquals(matrixData.get("AddedBy"),DSAcreateMember.CreationUserID);
    }

    @Test
    public void String (){
        String fulldate = "28/9/2018";
        String trimDate = fulldate.replaceAll("/","");
        String date = trimDate.substring(0,2);

        String month =trimDate.substring(2,3);
        if(month.toCharArray().length<2){
            month="0"+month;
        }
        String year = trimDate.substring(3,7);
        System.out.println(year+"-"+month+"-"+date);

        String json = "1268 (Antigua & Barbuda)";
        System.out.println(json.substring(0,4));
    }


}
