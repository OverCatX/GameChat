package sharkmcpe.gamechat;

import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import sharkmcpe.gamechat.API.GameChatAPI;
import sharkmcpe.gamechat.Command.GameChatCommand;
import sharkmcpe.gamechat.Command.SettingGameCommand;
import sharkmcpe.gamechat.EventListener.PlayerChat;
import sharkmcpe.gamechat.EventListener.PlayerJoin;
import sharkmcpe.gamechat.FormListener.GameChatForm;
import sharkmcpe.gamechat.Task.ChatgameTask;

import java.io.File;

public class Loader extends PluginBase implements Listener{

    private GameChatForm gameChatForm;
    private GameChatAPI gameChatAPI;
    Config data;
    @Override
    public void onEnable() {
        getLogger().info("§aระบบทำงานแล้วจ้าา");
        getLogger().info("สร้างโดย SharkMCPE นะครับบ");
        registerCommands();
        getDataFolder().mkdirs();
        CreateDatabase();
        RunTask();
        registerEvents();
        this.gameChatAPI = new GameChatAPI(this);
        this.gameChatForm = new GameChatForm(this);
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(this.gameChatForm, this);
    }

    private void registerCommands() {
        getServer().getCommandMap().register("sgame", new SettingGameCommand("sgame", this));
        getServer().getCommandMap().register("game", new GameChatCommand("game", this));
    }
    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerJoin(this),this);
        getServer().getPluginManager().registerEvents(new PlayerChat(this),this);
    }
    private void RunTask() {
        getServer().getScheduler().scheduleRepeatingTask(new ChatgameTask(this), 20 * time());
    }
    public GameChatForm getGameChatForm(){
        return this.gameChatForm;
    }

    public void CreateDatabase() {
        File gamefile = new File(this.getDataFolder() + "/gamechat.yml");
        if(!this.getDataFolder().exists()) {
            this.getDataFolder().mkdirs();
        }
        if(!gamefile.exists()) {
            data = new Config(this.getDataFolder() + "/gamechat.yml", Config.YAML);
            data.save();
            data.reload();
        }
        File manage = new File(this.getDataFolder() + "/manager.yml");
        if(!this.getDataFolder().exists()) {
            this.getDataFolder().mkdirs();
        }
        if(!manage.exists()) {
            String name = "Setting";
            data = new Config(this.getDataFolder() + "/manager.yml", Config.YAML);
            data.set(name+".Prefix", "§cG§ea§6m§5e§dC§4h§7a§3t");
            data.set(name+".Message1", "§f: §7จงพิมพ์คำว่า §b");
            data.set(name+".Message2", "§aใ§bค§cร§dพิ§eม§fพ์§3เ§5ร็§6ว§4ก่§7อ§bน§dจ§aะ§2ไ§5ด้§eรั§6บ§7เ§3งิ§4น§cร§bา§dง§aวั§eล§dน้§7า");
            data.set(name+".Eco", true);
            data.set(name+".EcoMin", 500);
            data.set(name+".EcoMax", 1000);
            data.set(name+".Time", 10);
            data.set(name+".PrefixTop", "Top 10 GameChat");
            data.save();
            data.reload();
        }
    }
    public int time(){
        String name = "Setting";
        data = new Config(this.getDataFolder() + "/manager.yml", Config.YAML);
        return data.getInt(name+".Time");
    }
    public void CreateDataAPI(String name) {
        gameChatAPI.CreateGameAPI(name);
    }
}