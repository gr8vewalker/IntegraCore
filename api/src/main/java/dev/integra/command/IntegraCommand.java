package dev.integra.command;

import dev.integra.api.IntegraAPI;
import dev.integra.api.command.CommandInfo;
import dev.integra.api.command.CommandResult;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Command API for Integra.
 *
 * @author milizm
 * @since 1.0.0
 */
public abstract class IntegraCommand extends BukkitCommand {

    private final List<IntegraCommandExecutor> subcommands = new ArrayList<>();

    protected IntegraCommand(@NotNull String name) {
        super(name);
    }

    /**
     * Gets the command map of the server using reflection.
     *
     * @return The command map of the server.
     */
    private static Optional<CommandMap> getCommandMap() {
        try {
            Class<?> craftServer = Bukkit.getServer().getClass();
            Field commandMap = craftServer.getDeclaredField("commandMap");
            commandMap.setAccessible(true);
            return Optional.of((CommandMap) commandMap.get(Bukkit.getServer()));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * Adds the command programmatically to the server.<br>
     * Loads all subcommands and registers them.
     */
    public void load() {
        try {
            for (Method method : this.getClass().getMethods()) {
                if (method.isAnnotationPresent(CommandInfo.class)) {
                    CommandInfo command = method.getAnnotation(CommandInfo.class);
                    checkInfo(command);
                    this.subcommands.add(new IntegraCommandExecutor(command, method));
                }
            }
            getCommandMap().ifPresent(map -> map.register(this.getName(), this));
        } catch (Exception e) {
            IntegraAPI.getLogger().severe("An error occurred while loading the command " + this.getName() + "!");
            e.printStackTrace();
        }
    }

    private void checkInfo(CommandInfo command) {
        if (command.name().isEmpty() && command.argumentInfo().length > 0) {
            throw new IllegalArgumentException("Command name cannot be empty if the command has arguments!");
        }

        String[] argumentInfo = command.argumentInfo();
        int mandatoryIndex = -1;
        int i = 0;
        for (String s : argumentInfo) {
            if (s.isEmpty()) {
                throw new IllegalArgumentException("Argument info cannot be empty!");
            }
            if (s.startsWith("!")) {
                if (i > 0 && mandatoryIndex == -1) {
                    throw new IllegalArgumentException("Optional arguments cannot be before mandatory arguments!");
                }
                mandatoryIndex = i;
            }
            i++;
        }
    }

    /**
     * Finds the subcommand that matches the given arguments.
     *
     * @param args The arguments to match.
     * @return The subcommand that matches the given arguments.
     */
    private Optional<IntegraCommandExecutor> findExecutor(String[] args) {
        String key = args.length > 0 ? args[0] : "";
        return this.subcommands.stream().filter(executor -> executor.getCommand().name().equalsIgnoreCase(key)).findFirst();
    }

    private List<IntegraCommandExecutor> findExecutorsContains(String[] args) {
        String key = args.length > 0 ? args[0] : "";
        return this.subcommands.stream().filter(executor -> executor.getCommand().name().toLowerCase().contains(key.toLowerCase())).collect(Collectors.toList());
    }

    public List<IntegraCommandExecutor> getSubcommands() {
        return subcommands;
    }

    /**
     * Executes the command integration.
     *
     * @param sender The sender of the command.
     * @param label  The command name. (No use if the command is registered to only one name)
     * @param args   The arguments of the command.
     * @return Whether the command was executed successfully.
     */
    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        Optional<IntegraCommandExecutor> executor = this.findExecutor(args);

        if (args.length > 0 && !executor.isPresent()) {
            executor = this.findExecutor(new String[0]);

            if (executor.isPresent() && executor.get().getCommand().argumentInfo().length == 0) {
                executor = Optional.empty();
            }
        }

        if (executor.isPresent()) {
            try {
                IntegraCommandExecutor exec = executor.get();
                CommandResult result = exec.execute(this, sender, args);

                if (result == CommandResult.SUCCESS)
                    return true;

                sender.sendMessage(this.getMessage(result, exec, args));
            } catch (Exception e) {
                e.printStackTrace();
                sender.sendMessage("Internal error! Notify the admin(s) or developer(s)!");
            }
        } else {
            sender.sendMessage(this.getMessage(CommandResult.NOT_FOUND, null, args));
        }
        return true;
    }

    @NotNull
    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        if (args.length == 1) {
            return this.findExecutorsContains(args).stream().map(executor -> executor.getCommand().name()).collect(Collectors.toList());
        } else {
            Optional<IntegraCommandExecutor> executor = this.findExecutor(args);
            if (executor.isPresent()) {
                return executor.get().tabComplete(this, sender, args);
            } else {
                return new ArrayList<>();
            }
        }
    }

    /**
     * Gets the message for the given result.<br>
     * No use for {@link CommandResult#SUCCESS}
     *
     * @param result   The result to get the message for.
     * @param executor The executor that executed the command. Null if the command was not found.
     * @param args     The arguments that were passed to the command.
     * @return The message for the given result.
     */
    protected abstract String getMessage(CommandResult result, IntegraCommandExecutor executor, String[] args);
}
