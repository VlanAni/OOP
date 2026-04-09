package ru.vladimirdev.snake.commonservices;

import ru.vladimirdev.snake.exceptions.CannotGetIntegerPropException;
import ru.vladimirdev.snake.exceptions.CannotLoadPropsException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GameConfig {

    private final Properties gameprops;

    public GameConfig() throws CannotLoadPropsException {
        gameprops = new Properties();

        try (InputStream is = getClass().getResourceAsStream("/config.properties")) {
            gameprops.load(is);
        } catch (IOException | NullPointerException e) {
            throw new CannotLoadPropsException("some problems with loading properties occurred", e);
        }
    }

    public Integer get(String key) throws CannotGetIntegerPropException {
        try {
            return Integer.parseInt(gameprops.getProperty(key));
        } catch (NumberFormatException e) {
            throw new CannotGetIntegerPropException("cannot parse string property", e);
        }
    }
}
