import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class Algorithm {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int choice = 0;

        String currentUserName = null, receiverUserName = null, plaintext, ciphertext;

        User currentUser = new User();

        RSA rsa = new RSA();

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

                        rsa.generateKeyPair();

                        setCurrentUser(currentUser, currentUserName, rsa);

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

                        RSAKey receiverPublicKey = currentUser.getReceiverPublicKey();

                        ciphertext = rsa.encrypt(plaintext, receiverPublicKey);

                        createEncryptedMessageFile(currentUser, ciphertext);

                        break;

                case 3:

                        ciphertext = getEncryptedMessageFromFile(currentUser);

                        RSAKey currentUserPrivateKey = currentUser.getUserPrivateKey();

                        plaintext = rsa.decrypt(ciphertext, currentUserPrivateKey);

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

    private static void setCurrentUser(User currentUser, String currentUserName, RSA rsa) {

        currentUser.setUserName(currentUserName);

        RSAKey currentUserPublicKey = rsa.getPublicKey();

        currentUser.setUserPublicKey(currentUserPublicKey);

        RSAKey currentUserPrivateKey = rsa.getPrivateKey();

        currentUser.setUserPrivateKey(currentUserPrivateKey);

    }

    private static void createLink(User currentUser, String currentUserName, String receiverUserName) throws IOException {

        currentUser.setUserName(currentUserName);

        currentUser.setReceiverName(receiverUserName);

        RSAKey receiverPublicKey = getReceiverPublicKeyFromFile(receiverUserName);

        currentUser.setReceiverPublicKey(receiverPublicKey);

        System.out.println("\nPublic Key fetched successfully !");

    }

    private static void createPublicKeyFile(User currentUser) throws IOException {
        
        FileWriter fw = new FileWriter("pk-" + currentUser.getUserName() + ".txt");

        RSAKey userPublicKey = currentUser.getUserPublicKey();

        String publicKey = String.format("[ Public key exponent(kp) : " + userPublicKey.getExponent() + " , Modulus(n) : " + userPublicKey.getModulus() + " ]");

        fw.write(publicKey);

        System.out.println("\nPublic key is stored in pk-"+ currentUser.getUserName() + ".txt");

        fw.close();
    }

    private static RSAKey getReceiverPublicKeyFromFile(String receiverUserName) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("pk-" + receiverUserName + ".txt"));

        String line;

        String[] values = null;

        while ((line = br.readLine()) != null) 
            values = line.split(" ");

        BigInteger kp = new BigInteger(values[5]);
        BigInteger n = new BigInteger(values[9]);

        br.close();

        return new RSAKey(kp, n);
    }

    private static void createEncryptedMessageFile(User currentUser, String ciphertext) throws IOException {

        FileWriter fw = new FileWriter("msg-" + currentUser.getReceiverName() + ".txt");

        fw.write(ciphertext);

        System.out.println("\nYour encrypted message is stored in msg-" + currentUser.getReceiverName() + ".txt" );

        fw.close();
    }

    private static String getEncryptedMessageFromFile(User currentUser) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("msg-" + currentUser.getUserName() + ".txt"));

        String msg = br.readLine();

        br.close();

        return msg;
    }

}