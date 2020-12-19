package sharkmcpe.gamechat.EventListener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import sharkmcpe.gamechat.Loader;

public class PlayerJoin implements Listener {

    private Loader plugin;
    public PlayerJoin(Loader plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void join(PlayerJoinEvent event){
        String name = event.getPlayer().getName();
        plugin.CreateDataAPI(name);
    }
}
