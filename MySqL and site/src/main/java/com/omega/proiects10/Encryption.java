package com.omega.proiects10;

public class Encryption {
    private static final String encryptKey="3fegw%$^&*Gr#$";

    public static String encrypt(String password) throws Exception{
        char[] keyToArray = encryptKey.toCharArray();
        char[] passwordArray=password.toCharArray();

        try {
            for (int i = 0; i < passwordArray.length; i++) {
                passwordArray[i] = (char) (passwordArray[i] ^ keyToArray[i % keyToArray.length]);
            }
        }
        catch (Exception e){
            System.out.println("The password cannot be encrypted!");
        }
        return new String(passwordArray);
    }
    public static String decrypt(String encryptedPassword) throws Exception{
        try {
            return encrypt(encryptedPassword); // XOR-based encryption is symmetric
        }
        catch (Exception e){
            System.out.println("The password cannot be decrypted!");
        }
        return null;
    }
}
