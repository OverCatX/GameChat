package sharkmcpe.gamechat.Command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import sharkmcpe.gamechat.Loader;

public class GameChatCommand extends Command {

    private Loader plugin;

    public GameChatCommand(String name, Loader plugin) {
        super("game", "เปิดหน้า GameChatUI");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (commandSender instanceof Player) {
            plugin.getGameChatForm().GameChatUI((Player) commandSender);
        }
        return false;
    }
}
