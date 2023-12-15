package courier;

import java.util.Random;

public class CourierTestData {
    private final String existingLogin;
    private final String nonExistLogin;
    private final String existingPassword;
    private final String nonExistPassword;
    private final String firstName;

    public CourierTestData () {
        this.existingLogin = randomLoginOrPass(9);
        this.nonExistLogin = randomLoginOrPass(9)+"foo";
        this.existingPassword = "SomePass";
        this.nonExistPassword = randomLoginOrPass(8);
        this.firstName = "SomeName";
    }


    public static String randomLoginOrPass(int length) {
        int leftLimit = 97; // от 'a'
        int rightLimit = 122; // до 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public String getExistingLogin() {
        return existingLogin;
    }

    public String getExistingPassword() {
        return existingPassword;
    }

    public String getNonExistLogin() {
        return nonExistLogin;
    }

    public String getNonExistPassword() {
        return nonExistPassword;
    }

    public String getFirstName() {
        return firstName;
    }

}
