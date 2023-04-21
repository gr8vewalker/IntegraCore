package dev.integra.listener;

import dev.integra.TestPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;

public class TestListener implements Listener {

    @EventHandler
    public void onCommand(ServerCommandEvent event) {
        TestPlugin.getInstance().getLogger().info("Command Issued: " + event.getCommand());
    }

}
