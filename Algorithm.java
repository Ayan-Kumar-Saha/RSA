import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Random;

public class Algorithm {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int choice = 0;

        String currentUserName = null , receiverUserName = null;

        String plaintext;

        byte[] ciphertext;

        User currentUser = new User();

        do {
            System.out.println("\n\n--MAIN MENU--");
            System.out.println("1. Generate your Key pair");
            System.out.println("2. Send encrypted message to an user");
            System.out.println("3. Decrypt received message from an user");
            System.out.println("0. Quit");
            System.out.print("\nEnter your choice: ");

            choice = Integer.parseInt(br.readLine());

            switch (choice) {

                case 1:

                        currentUserName = inputData("\nEnter your username: ");

                        Random r = new Random();

                        BigInteger p = BigInteger.probablePrime(RSAImplementation.bitLength, r);

                        BigInteger q = BigInteger.probablePrime(RSAImplementation.bitLength, r);

                        setCurrentUser(currentUser, currentUserName, p, q);

                        createPublicKeyFile(currentUser);

                        break;

                case 2: 

                        if ( currentUser.getUserName() == null ) 
                            currentUserName = inputData("\nEnter your username: ");
                        
                        if ( currentUser.getReceiverName() == null ) 
                            receiverUserName = inputData("\nEnter receiver's username: ");

                        if (  currentUser.getReceiverName() == null )
                            createLink(currentUser, currentUserName, receiverUserName);

                        plaintext = inputData("\nEnter the message to encrypt: ");

                        RSAResponse rsp = currentUser.getReceiverPublicKey();

                        ciphertext = RSAImplementation.doEncryption(plaintext, rsp);

                        createEncryptedMessageFile(currentUser, ciphertext);

                        break;

                case 3:

                        ciphertext = getEncryptedMessageFromFile(currentUser);

                        RSAResponse ursp = currentUser.getUserKeyPair();

                        plaintext = RSAImplementation.doDecryption(ciphertext, ursp);

                        System.out.print("\nYour decrypted message is: " + plaintext);

                        break;

                case 0:

                        break;

                default: 

                        System.out.print("\nInvalid option choosen! Enter correctly: ");
                        break;
            }

        } while (choice != 0);


    }

    private static String inputData(String name) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print(name);

        return br.readLine();
    }

    private static void setCurrentUser(User currentUser, String currentUserName, final BigInteger p, final BigInteger q) {

        currentUser.setUserName(currentUserName);
        currentUser.setPQ(p, q);

        RSAResponse currentUserKeyPair = RSAImplementation.prepare(p, q);

        currentUser.setUserKeyPair(currentUserKeyPair);
    }

    private static void createLink(User currentUser, String currentUserName, String receiverUserName) throws IOException {

        currentUser.setUserName(currentUserName);

        currentUser.setReceiverName(receiverUserName);

        RSAResponse rsp = getReceiverPublicKeyFromFile(receiverUserName);

        currentUser.setReceiverPublicKey(rsp);

        System.out.println("\nPublic Key fetched successfully !");

    }

    private static void createPublicKeyFile(User currentUser) throws IOException {
        
        FileWriter fw = new FileWriter("pk-" + currentUser.getUserName() + ".txt");

        RSAResponse rsp = currentUser.getUserKeyPair();

        String output = String.format("[ Public key exponent(kp) : " + rsp.getPublicKeyExponent() + " , Modulus(n) : " + rsp.getModulus()) + " ]";

        System.out.println("\nPublic key is stored in pk-"+ currentUser.getUserName() + ".txt");

        fw.write(output);

        fw.close();
    }

    private static RSAResponse getReceiverPublicKeyFromFile(String receiverUserName) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("pk-" + receiverUserName + ".txt"));

        String line;

        String[] values = null;

        while ((line = br.readLine()) != null) 
            values = line.split(" ");

        BigInteger kp = new BigInteger(values[5]);
        BigInteger n = new BigInteger(values[9]);

        br.close();

        return new RSAResponse(kp, n);
    }

    private static void createEncryptedMessageFile(User currentUser, byte[] ciphertext) throws IOException {

        FileWriter fw = new FileWriter("msg-" + currentUser.getReceiverName() + ".txt");

        String csv = "";

        for (int value : ciphertext) csv += Integer.toString(value) + ",";

        fw.write(csv);

        System.out.println("\nYour encrypted message is stored in msg-" + currentUser.getReceiverName() + ".txt" );

        fw.close();
    }

    private static byte[] getEncryptedMessageFromFile(User currentUser) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("msg-" + currentUser.getUserName() + ".txt"));

        String line;

        String[] csv = null;

        while ((line = br.readLine()) != null) 
            csv = line.split(",");

        byte[] msg = new byte[csv.length];

        for (int i = 0 ; i < csv.length ; i++) msg[i] = (byte) Integer.parseInt(csv[i]);
        
        br.close();

        return msg;
    }

}