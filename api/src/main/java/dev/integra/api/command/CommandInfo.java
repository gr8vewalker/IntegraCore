package dev.integra.api.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CommandInfo {

    String name();

    String description(); // Left for usage in help commands

    String usage(); // Left for usage in help commands

    String permission() default "";

    boolean playerOnly() default false;

    String[] argumentInfo() default {};

}
