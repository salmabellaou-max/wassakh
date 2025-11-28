import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

// 
// Decompiled by Procyon v0.6.0
// 

class PasswordUtil
{
    public static String hashPassword(final String password) {
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-256");
            final byte[] hashedBytes = md.digest(password.getBytes());
            final StringBuilder sb = new StringBuilder();
            for (final byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }
        catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
    
    public static boolean verifyPassword(final String inputPassword, final String storedHash) {
        final String inputHash = hashPassword(inputPassword);
        return inputHash.equals(storedHash);
    }
    
    public static String generateVerificationCode() {
        final SecureRandom random = new SecureRandom();
        final int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
