package dev.integra.api.command;

/**
 * Represents the result of a command execution.
 *
 * @author milizm
 * @see dev.integra.command.IntegraCommand#getMessage(CommandResult, dev.integra.command.IntegraCommandExecutor, String...)
 * @since 1.0.0
 */
public enum CommandResult {

    /**
     * Command was successful
     * It is no use in {@link dev.integra.command.IntegraCommand#getMessage(CommandResult, dev.integra.command.IntegraCommandExecutor, String...)}
     */
    SUCCESS,
    /**
     * Sender has no permission to execute the command
     */
    NO_PERMISSION,
    /**
     * Sender is not a player
     */
    INVALID_SENDER,
    /**
     * An exception thrown while executing the command
     */
    ERROR,
    /**
     * No subcommands found for arguments
     */
    NOT_FOUND,
    /**
     * Mandatory arguments are missing
     */
    MISSING_ARGUMENTS

}
