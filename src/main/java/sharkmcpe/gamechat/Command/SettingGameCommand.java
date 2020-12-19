package sharkmcpe.gamechat.Command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import sharkmcpe.gamechat.API.GameChatAPI;
import sharkmcpe.gamechat.Loader;

public class SettingGameCommand extends Command {
    private Loader plugin;
    public SettingGameCommand(String name, Loader plugin) {
        super("sgame", "For Admin");
        this.plugin = plugin;
        this.setPermission("game.chat");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (commandSender instanceof Player) {
            if(commandSender.hasPermission("game.chat")) {
                plugin.getGameChatForm().AdminFormGame((Player) commandSender);
            }
        }
        return false;
    }
}
