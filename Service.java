public class Service {

    public static byte[] convertStringtoBytes(String stringValue) {

        String[] csv = stringValue.split(",");

        byte[] stringValueInBytes = new byte[csv.length];

        for (int i = 0 ; i < csv.length ; i++) stringValueInBytes[i] = (byte) Integer.parseInt(csv[i]);

        return stringValueInBytes;
    }

    public static String convertBytestoString(byte[] byteValue) {

        String stringValue = "";

        for (int value : byteValue) stringValue += Integer.toString(value) + ",";

        return stringValue;
    }
}