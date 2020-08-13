package cn.jji8.Musicstone;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;


public class main extends JavaPlugin implements CommandExecutor {
    static main main;
    @Override
    public void onEnable() {
        main = this;
        Bukkit.getPluginCommand("加载音乐").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length<1){
            return false;
        }
        io.jiazai(sender,args[0]);
        return true;
    }
}
