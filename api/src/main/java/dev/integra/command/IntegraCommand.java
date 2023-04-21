package dev.integra.command;

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

public abstract class IntegraCommand extends BukkitCommand {

    private final List<IntegraCommandExecutor> subcommands = new ArrayList<>();

    protected IntegraCommand(@NotNull String name) {
        super(name);
    }

    public void load() {
        getCommandMap().ifPresent(map -> map.register(this.getName(), this));

        for (Method method : this.getClass().getMethods()) {
            if (method.isAnnotationPresent(CommandInfo.class)) {
                CommandInfo command = method.getAnnotation(CommandInfo.class);
                this.subcommands.add(new IntegraCommandExecutor(command, method));
            }
        }
    }

    private Optional<IntegraCommandExecutor> findExecutor(String[] args) {
        String key = args.length > 0 ? args[0] : "";
        for (IntegraCommandExecutor executor : this.subcommands) {
            if (executor.getCommand().name().equalsIgnoreCase(key)) {
                return Optional.of(executor);
            }
        }

        return Optional.empty();
    }

    public List<IntegraCommandExecutor> getSubcommands() {
        return subcommands;
    }

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
                CommandResult result = executor.get().execute(this, sender, args);
                if (result != CommandResult.SUCCESS)
                    sender.sendMessage(this.getMessage(result));
            } catch (Exception e) {
                e.printStackTrace();
                sender.sendMessage("Internal error! Notify the admin(s) or developer(s)!");
            }
        } else {
            sender.sendMessage(this.getMessage(CommandResult.NOT_FOUND));
        }
        return false;
    }

    protected abstract String getMessage(CommandResult result);

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
}
