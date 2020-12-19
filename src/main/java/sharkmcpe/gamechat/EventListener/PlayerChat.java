package sharkmcpe.gamechat.EventListener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import me.onebone.economyapi.EconomyAPI;
import sharkmcpe.gamechat.API.GameChatAPI;
import sharkmcpe.gamechat.Loader;
import sharkmcpe.gamechat.Math.RandomInteger;

public class PlayerChat implements Listener {

    private Loader plugin;

    public PlayerChat(Loader plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void Chat(PlayerChatEvent event){
        Player player = event.getPlayer();
        GameChatAPI api = new GameChatAPI(plugin);
        String message = event.getMessage();
        String names = "Setting";
        String prefix = (String) api.getData().get(names+".Prefix");
        String name = player.getName();
        int winprize = RandomInteger.rand(api.getData().getInt(names+".EcoMin"),api.getData().getInt(names+".EcoMax"));
        if(api.inprocess() == 1) {
            if (message.contains(api.getCharactor())){
                if(api.getData().getString(names + ".Eco").equals("true")){
                    EconomyAPI.getInstance().addMoney(player, winprize);
                    api.AddPoint(player.getName(), 1);
                    plugin.getServer().broadcastMessage(prefix + "§d: §fผู้เล่น §b" + name + " §aได้ชนะไปในครั้งนี้");
                    player.sendMessage(prefix+" §7คุณได้ชนะการพิมพ์เร็ว §aคุณ§dได้§bรับ§4เงิน§5ไป§6 "+winprize);
                    player.sendTitle("§7คุณได้ชนะการพิมพ์เร็ว\n§aคะแนน§cสะ§dสม"+api.getPoint(player.getName()));
                    api.Save("null???0?)?*()?", 0);
                    event.setCancelled(true);
                }
                if(api.getData().getString(names + ".Eco").equals("false")){
                    api.AddPoint(player.getName(), 1);
                    plugin.getServer().broadcastMessage(prefix + "§d: §fผู้เล่น §b" + name + " §aได้ชนะไปในครั้งนี้");
                    player.sendMessage(prefix+" §7คุณได้ชนะการพิมพ์เร็ว");
                    player.sendTitle("§7คุณได้ชนะการพิมพ์เร็ว\n§aคะแนน§cสะ§dสม"+api.getPoint(player.getName()));
                    api.Save("null???0?)?*()?", 0);
                    event.setCancelled(true);
                }
            }
        }
    }
}
