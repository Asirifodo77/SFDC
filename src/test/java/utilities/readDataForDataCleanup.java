package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class readDataForDataCleanup {


    public List<String> getCardNumbersToBeDeleted() throws Exception {

        BufferedReader br = new BufferedReader(new FileReader("src//test//resources//test_data//DataCleanup.csv"));
        String line = null;

        List <String> a = new ArrayList<>();
        String[] values = new String[0];
        while ((line = br.readLine()) != null) {
            values = line.split(",");
            for (String str : values) {
                //System.out.println(str);
                a.add(str);
            }
        }
        br.close();
        return a;


    }


}
