package edu.ub.pis.giickos.model.managers;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserManager {

    //TODO leverage useful code from DS

    /*
    private Map<String, Persona> persones;

    public enum VALIDATE_PASSWORD_RESULT {
        SECURE,
        INSECURE,
    }
    public enum VALIDATE_USERNAME_RESULT {
        VALID,
        INVALID,
    }
    public enum USER_CREATION_RESULT {
        SUCCESS,
        DUPLICATE_USER,
        INVALID_CREDENTIALS,
    }
    public enum LOGIN_RESULT {
        SUCCESS,
        WRONG_PASSWORD,
        INEXISTENT_USER,
    }
    public enum FIND_PERSON_RESULT {
        FOUND,
        NOT_FOUND,
    }

    public ClientManager(List<Persona> persones) {
        this.persones = new HashMap<>();

        addPersones(persones);
    }

    public void addPersones(List<Persona> persones) {
        for (Persona persona : persones) {
            addPersona(persona);
        }
    }

    public void addPersona(Persona persona) {
        persones.put(persona.getName(), persona);
    }

    public Persona find(String username) {
        return persones.get(username);
    }

    public FIND_PERSON_RESULT findPersona(String username)
    {
        FIND_PERSON_RESULT result = FIND_PERSON_RESULT.FOUND;
        Persona persona = find(username);

        if (persona == null) {
            result = FIND_PERSON_RESULT.NOT_FOUND;
        }

        return result;
    }

    public VALIDATE_PASSWORD_RESULT validatePassword(String password)
    {
        VALIDATE_PASSWORD_RESULT result = VALIDATE_PASSWORD_RESULT.SECURE;

        if (!isPasswordSegur(password)) {
            result = VALIDATE_PASSWORD_RESULT.INSECURE;
        }

        return result;
    }

    public VALIDATE_USERNAME_RESULT validateUsername(String username)
    {
        VALIDATE_USERNAME_RESULT result = VALIDATE_USERNAME_RESULT.VALID;

        if (!isMail(username)) {
            result = VALIDATE_USERNAME_RESULT.INVALID;
        }

        return result;
    }

    public USER_CREATION_RESULT validateRegistrePersona(String username, String password)
    {
        USER_CREATION_RESULT result = USER_CREATION_RESULT.SUCCESS;

        if (isMail(username) && isPasswordSegur(password)) {
            Persona persona = find(username);

            if (persona != null) {
                result = USER_CREATION_RESULT.DUPLICATE_USER;
            }
        }
        else {
            result = USER_CREATION_RESULT.INVALID_CREDENTIALS;
        }

        return result;
    }

    private boolean isPasswordSegur(String password)
    {
        Pattern pattern = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

    private boolean isMail(String correu)
    {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(correu);
        return matcher.find();
    }

    public LOGIN_RESULT loguejarPersona(String username, String password) {
        LOGIN_RESULT result = LOGIN_RESULT.SUCCESS;
        Persona persona = find(username);

        if (persona == null) {
            result = LOGIN_RESULT.INEXISTENT_USER;
        }
        else if(!persona.getPwd().equals(password)){
            result = LOGIN_RESULT.WRONG_PASSWORD;
        }

        return result;
    }

    public String recuperarContrassenya(String username){
        Persona persona = find(username);
        if(persona == null){
            return "Correu inexistent";
        }
        return persona.getPwd();
    }*/
}
