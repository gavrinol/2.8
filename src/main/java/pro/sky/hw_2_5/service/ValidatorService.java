package pro.sky.hw_2_5.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pro.sky.hw_2_5.exceptions.IncorrectNameException;
import pro.sky.hw_2_5.exceptions.IncorrectSurnameException;

@Service
public class ValidatorService {

    public String validateName(String name) {
        if (!StringUtils.isAlpha(name)) {
            throw new IncorrectNameException();
        }
        return StringUtils.capitalize(name.toLowerCase());
    }

    public String validateSurname(String surname) {
        String[] surnames = surname.split("-");
        for (int i = 0; i < surname.length(); i++) {
            if(!StringUtils.isAlpha(surnames[i])){
                throw new IncorrectSurnameException();
            }
            surnames[i] = StringUtils.capitalize(surnames[i].toLowerCase());
        }
        return String.join("-", surnames);
    }
}
