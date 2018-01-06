public class Validation {
    static boolean loginIsValid(String login)
    {
        if (login.length() == 0) return false;
        return true;
    }
    static boolean passwordIsValid(String password)
    {
        if (password.equals("root")) return true;
        return false;
    }
}
