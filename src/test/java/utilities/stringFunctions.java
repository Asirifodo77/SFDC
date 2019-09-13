package utilities;

public class stringFunctions {

    public String trimStringBeforeOpenBracket(String originalStr) throws InterruptedException, NullPointerException {
        String returnedString;
        if(originalStr==null || originalStr.isEmpty() ) {
            System.out.println("The given string is Null or Empty");
            returnedString="";
        } else if(!originalStr.contains("(")) {
            //System.out.println("Unable to trim the given string. No open brackets found. ");
            throw new InterruptedException ("Unable to trim the given string. No open brackets found");
        } else {
            try {
                returnedString= originalStr.substring(0,originalStr.indexOf("(")).trim();
            } catch (NullPointerException e) {
                System.out.println("The trimmed string is Null");
                throw e;
            }
        }
        return returnedString;
    }

}
