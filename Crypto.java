public interface Crypto {

    public void generateKeyPair();
    public RSAKey getPublicKey();
    public RSAKey getPrivateKey();
    public String encrypt(String plaintext, RSAKey publicKey);
    public String decrypt(String ciphertext, RSAKey privateKey);
}