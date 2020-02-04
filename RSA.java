import java.util.Random;
import java.math.BigInteger;

public class RSA {

    private int keyBitLength = 1024;

    private RSAKey publicKey;
    private RSAKey privateKey;

    public void generateKeyPair() {

        Random rndm = new Random();

        BigInteger p = BigInteger.probablePrime(keyBitLength, rndm);
        BigInteger q = BigInteger.probablePrime(keyBitLength, rndm);

        BigInteger modulus = calculateModulus(p, q);

        BigInteger totient = calulateTotient(p, q);

        BigInteger publicKeyExponent = generatePublicKeyExponent(totient);

        BigInteger privateKeyExponent = generatePrivateKeyExponent(publicKeyExponent, totient);

        publicKey = new RSAKey(publicKeyExponent, modulus);

        privateKey = new RSAKey(privateKeyExponent, modulus);

        System.out.println("\nKey generation successful !!");
    }

    private BigInteger calculateModulus(final BigInteger p, final BigInteger q) {
        return p.multiply(q) ;
    }

    private BigInteger calulateTotient(final BigInteger p, final BigInteger q) {
        return p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
    }

    private BigInteger generatePublicKeyExponent(final BigInteger totient) {

        Random rndm = new Random();
        
        BigInteger value;

        value = BigInteger.probablePrime(keyBitLength / 2, rndm);

        while (totient.gcd(value).compareTo(BigInteger.ONE) > 0 && value.compareTo(totient) < 0)
            value.add(BigInteger.ONE);

        return value;
    }

    private BigInteger generatePrivateKeyExponent(final BigInteger publicKeyExponent, final BigInteger totient) {
        return publicKeyExponent.modInverse(totient);
    }

    public RSAKey getPublicKey() {
        return publicKey;
    }

    public RSAKey getPrivateKey() {
        return privateKey;
    }

    public String encrypt(String plaintext, RSAKey publicKey) {

        BigInteger kp = publicKey.getExponent();

        BigInteger n = publicKey.getModulus();

        byte[] plaintextInBytes = plaintext.getBytes();

        byte[] ciphertextInBytes = (new BigInteger(plaintextInBytes)).modPow(kp, n).toByteArray();

        return Service.convertBytestoString(ciphertextInBytes);
    }

    public String decrypt(String ciphertext, RSAKey privateKey) {

        BigInteger ks = privateKey.getExponent();

        BigInteger n = privateKey.getModulus();

        byte[] ciphertextInBytes = Service.convertStringtoBytes(ciphertext);

        byte[] plaintextInBytes = (new BigInteger(ciphertextInBytes)).modPow(ks, n).toByteArray();

        return new String(plaintextInBytes);
    }

}