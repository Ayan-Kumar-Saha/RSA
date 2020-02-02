import java.math.BigInteger;

public class User {

    private String userName = null;
    private BigInteger p = null;
    private BigInteger q = null;
    private RSAResponse userKeyPair;
    private String receiverName = null;
    private RSAResponse receiverPublicKey;

    public User() { }

    public User(String userName, final BigInteger p, final BigInteger q) {
        this.userName = userName;
        this.p = p;
        this.q = q;
    }

    public void setUserName(String userName) { this.userName = userName; }

    public void setPQ(final BigInteger p, final BigInteger q) { this.p = p; this.q = q; }

    public void setUserKeyPair(RSAResponse userKeyPair) { this.userKeyPair = userKeyPair; }

    public void setReceiverName(String receiverName) { this.receiverName = receiverName; }

    public void setReceiverPublicKey(RSAResponse receiverPublicKey) { this.receiverPublicKey = receiverPublicKey; }

    public String getUserName() { return userName; }

    public BigInteger getP() { return p; }

    public BigInteger getQ() { return q; }

    public RSAResponse getUserKeyPair() { return userKeyPair; }

    public String getReceiverName() { return receiverName; }

    public RSAResponse getReceiverPublicKey() { return receiverPublicKey; }

}