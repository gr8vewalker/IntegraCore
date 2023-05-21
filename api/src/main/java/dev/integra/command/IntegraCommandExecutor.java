package dev.integra.command;

import com.google.common.collect.Lists;
import dev.integra.api.command.CommandInfo;
import dev.integra.api.command.CommandResult;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Executor class for subcommands or no-argument commands
 *
 * @author milizm
 * @since 1.0.0
 */
public class IntegraCommandExecutor {

    /**
     * The command information of the command is gotten from the method's annotation.
     */
    private final CommandInfo command;
    /**
     * The method that will be executed when the command is called
     */
    private final Method method;

    public IntegraCommandExecutor(CommandInfo command, Method method) {
        this.command = command;
        this.method = method;
    }

    public CommandInfo getCommand() {
        return command;
    }

    public Method getMethod() {
        return method;
    }

    /**
     * Executes the command method by parsing the arguments and checking the sender's permission.
     *
     * @param integraCommand The command instance
     * @param sender         The sender of the command
     * @param args           The arguments of the command to be parsed
     * @return The result of the command execution
     */
    public CommandResult execute(IntegraCommand integraCommand, CommandSender sender, String[] args) {
        if (this.command.playerOnly() && !(sender instanceof Player)) {
            return CommandResult.INVALID_SENDER;
        }

        if (!sender.hasPermission(this.command.permission())) {
            return CommandResult.NO_PERMISSION;
        }

        try {
            if (!this.command.name().isEmpty()) {
                String[] newArgs = new String[args.length - 1];
                System.arraycopy(args, 1, newArgs, 0, newArgs.length);
                args = newArgs;
            }

            Map<String, String> argsMap = new HashMap<>();
            String[] argumentInfo = this.command.argumentInfo();
            for (int i = 0; i < argumentInfo.length; i++) {
                // if arg starts with ! and is longer than 1 character, it is needed to be present.
                boolean isMandatory = argumentInfo[i].startsWith("!") && argumentInfo[i].length() > 1;
                if (i < args.length) {
                    String name = isMandatory ? argumentInfo[i].substring(1) : argumentInfo[i];
                    argsMap.put(name, args[i]);
                } else {
                    if (isMandatory) {
                        return CommandResult.MISSING_ARGUMENTS;
                    }
                    argsMap.put(argumentInfo[i], "");
                }
            }

            this.method.invoke(integraCommand, sender, argsMap);
            return CommandResult.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return CommandResult.ERROR;
        }
    }

    public List<String> tabComplete(IntegraCommand integraCommand, CommandSender sender, String[] args) {
        // TODO: implement tab completion that can changeable by the plugin
        // for now it just returns the argument information for that index

        if (this.command.playerOnly() && !(sender instanceof Player)) {
            return new ArrayList<>();
        }

        if (!sender.hasPermission(this.command.permission())) {
            return new ArrayList<>();
        }

        if (!this.command.name().isEmpty()) {
            String[] newArgs = new String[args.length - 1];
            System.arraycopy(args, 1, newArgs, 0, newArgs.length);
            args = newArgs;
        }

        if (args.length > this.command.argumentInfo().length) {
            return new ArrayList<>();
        }

        String arg = this.command.argumentInfo()[args.length - 1];
        if (arg.startsWith("!")) {
            arg = arg.substring(1);
        }
        return Lists.newArrayList(arg);
    }
}
