package dev.integra.command;

import dev.integra.api.command.CommandInfo;
import dev.integra.api.command.CommandResult;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class IntegraCommandExecutor {

    private final CommandInfo command;
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
                if (i < args.length) {
                    argsMap.put(argumentInfo[i], args[i]);
                } else {
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
}
