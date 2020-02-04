public class Service {

    public static String convertBytestoString(byte[] byteValue) {

        String stringValue = "";

        for (int value : byteValue) stringValue += Integer.toString(value) + ",";

        return stringValue;
    }
}