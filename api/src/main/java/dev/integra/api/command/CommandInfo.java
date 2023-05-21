package dev.integra.api.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Command info annotation for Integra commands
 *
 * @author milizm
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CommandInfo {

    /**
     * The name of subcommand
     *
     * @return Empty string if it's a root command or name of subcommand
     */
    String name();

    /**
     * Description of command. Usable in help commands
     *
     * @return Description of command
     */
    String description(); // Left for usage in help commands

    /**
     * Usage of command. Usable in help commands
     *
     * @return Usage of command
     */
    String usage(); // Left for usage in help commands

    /**
     * Permission required to execute command
     *
     * @return Permission required to execute command
     */
    String permission() default "";

    /**
     * Is command only for players
     *
     * @return Is command only for players
     */
    boolean playerOnly() default false;

    /**
     * Argument information for command. Needed for parsing the argument array to {@link java.util.Map}<br><br>
     * For mandatory arguments, use {@code !} prefix.<br><br>
     * P.S: While getting the argument value from the map, don't add {@code !} prefix to the argument name.
     *
     * @return Argument information for command
     */
    String[] argumentInfo() default {};

}
