package server;

public class Validation {
    static boolean loginIsValid(String login)
    {
        if (login.length() == 0) return false;
        for (int i = 0; i < login.length(); i++)
        {
            if (login.charAt(i) == '_' || login.charAt(i) == ' ')
                return false;
        }
        return true;
    }
    static boolean passwordIsValid(String password)
    {
        if (password.length() < 4) return false;
        for (int i = 0; i < password.length(); i++)
        {
            if (password.charAt(i) == '_' || password.charAt(i) == ' ')
                return false;
        }
        return true;
    }
}
