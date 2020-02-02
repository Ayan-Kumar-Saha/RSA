import java.util.Random;
import java.math.BigInteger;

public class RSAImplementation {

    private static BigInteger modulus;
    private static BigInteger totient;

    public static int bitLength = 1024;

    public static RSAResponse prepare(final BigInteger p, final BigInteger q) {

        BigInteger n = calculateModulusTotient(p, q);

        BigInteger kp = generatePublicKeyExponent();

        BigInteger ks = generatePrivateKeyExponent(kp);

        System.out.println("\nKey generation successful !!");

        return new RSAResponse(kp, ks, n);
    }

    private static BigInteger calculateModulusTotient(BigInteger p, BigInteger q) {

        modulus = p.multiply(q) ;
        totient = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        return modulus;
    }

    private static BigInteger generatePublicKeyExponent() {

        Random rndm = new Random();
        
        BigInteger value;

        value = BigInteger.probablePrime(bitLength / 2, rndm);

        while (totient.gcd(value).compareTo(BigInteger.ONE) > 0 && value.compareTo(totient) < 0)
            value.add(BigInteger.ONE);

        return value;
    }

    private static BigInteger generatePrivateKeyExponent(final BigInteger publicKeyExponent) {

        return publicKeyExponent.modInverse(totient);
    }

    public static byte[] doEncryption(String plaintext, RSAResponse rsp) {

        BigInteger kp = rsp.getPublicKeyExponent();

        BigInteger n = rsp.getModulus();

        byte[] message = plaintext.getBytes();

        byte[] encrypted = (new BigInteger(message)).modPow(kp, n).toByteArray();

        return encrypted; 
    }

    public static String doDecryption(byte[] ciphertext, RSAResponse ursp) {

        BigInteger ks = ursp.getPrivateKeyExponent();

        BigInteger n = ursp.getModulus();

        byte[] decrypted = (new BigInteger(ciphertext)).modPow(ks, n).toByteArray();

        return new String(decrypted);
    }

}