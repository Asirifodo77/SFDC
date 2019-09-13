package utilities;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import property.Property;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class readTestData {
    public JsonObject myJsonObj;

    public String readTestData(String field) throws FileNotFoundException, JsonSyntaxException, JsonIOException {
        String fieldValue="";
        try {
            JsonParser parser = new JsonParser();
            Object obj = parser.parse(new FileReader(Property.TESTDATA_FILE_PATH));
            myJsonObj = (JsonObject) obj;
            fieldValue = myJsonObj.get(field.trim()).getAsString();

        } catch (JsonIOException e) {
            System.out.println("Json Input/ output file exception occurred - "+e.getLocalizedMessage());
            throw e;
        } catch (JsonSyntaxException e) {
            System.out.println("Incorrect JsonSyntax in the Testdata file  - "+e.getLocalizedMessage());
            throw e;
        } catch (FileNotFoundException e) {
            System.out.println("Unable to find Testdata file in the Location "+Property.TESTDATA_FILE_PATH+". ---> "+e.getLocalizedMessage());
            throw e;
        }
        return fieldValue;
    }

    public String readDSABrandMapping(String field) throws FileNotFoundException {

        JsonParser parser = new JsonParser();
        Object obj = parser.parse(new FileReader(Property.BRANDMAPPING_FILE_PATH));
        myJsonObj = (JsonObject) obj;
        String fieldValue = null;
        try {
            fieldValue = myJsonObj.get(field.trim()).getAsString();
            System.out.println("Filed value passed to BrandMapping File - "+field);
        } catch (NullPointerException e) {
           return "";

        }
        return fieldValue;
    }

}
