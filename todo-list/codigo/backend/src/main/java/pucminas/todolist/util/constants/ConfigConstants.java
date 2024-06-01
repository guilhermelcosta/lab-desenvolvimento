package pucminas.todolist.util.constants;

import lombok.experimental.UtilityClass;

import java.util.List;

import static java.util.Arrays.asList;

@UtilityClass
public class ConfigConstants {

    public static final List<String> ALLOWED_METHODS = asList("GET", "POST", "PUT", "PATCH", "HEAD", "OPTIONS");

    public static final String CORS_CONFIG = "/**";
}
