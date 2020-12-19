package sharkmcpe.gamechat.API;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import sharkmcpe.gamechat.Loader;

import java.util.*;
import java.util.stream.Collectors;

public class GameChatAPI {

    public Loader plugin;
    public String[] array;
    public GameChatAPI(Loader plugin){
        this.plugin = plugin;
    }

    public void CreateGameAPI(String p){
        Config data2 = new Config(plugin.getDataFolder() + "/savegamechat.yml", Config.YAML);
        Config data3 = new Config(plugin.getDataFolder() + "/player.yml", Config.YAML);
        if(!data2.exists("Content")) {
            data2.set("Content", "");
            data2.save();
        }
        if(!data2.exists("Charactor")) {
            data2.set("Charactor", array);
            data2.save();
        }
        if(!data2.exists("Process")) {
            data2.set("Process", 0);
            data2.save();
        }
        if(!data3.exists(p)) {
            data3.set(p, 0);
            data3.save();
        }
    }

    public void Save(String Charactor, Integer process){
        Config data2 = new Config(plugin.getDataFolder() + "/savegamechat.yml", Config.YAML);
        data2.set("Charactor", Charactor);
        data2.set("Process", process);
        data2.save();
    }
    public int inprocess(){
        Config data2 = new Config(plugin.getDataFolder() + "/savegamechat.yml", Config.YAML);
        return data2.getInt("Process");
    }
    public String getCharactor(){
        Config data2 = new Config(plugin.getDataFolder() + "/savegamechat.yml", Config.YAML);
        return data2.getString("Charactor");
    }
    public void SetnewManager(String name,String Prefix, String M1, String M2, String tf, int ecomin, int ecomax, int time, String top){
        Config data = new Config(plugin.getDataFolder() + "/manager.yml", Config.YAML);
        data.set(name+".Prefix", Prefix);
        data.set(name+".Message1", M1);
        data.set(name+".Message2", M2);
        data.set(name+".Eco", tf);
        data.set(name+".EcoMin", ecomin);
        data.set(name+".EcoMax", ecomax);
        data.set(name+".Time", time);
        data.set(name+".PrefixTop", top);
        data.save();
    }
    public void SetButton(String name, int Point, String Command, String Type, String Pic, String TypeButton, int iAll){
        Config data = new Config(plugin.getDataFolder() + "/gamechat.yml", Config.YAML);
        data.set(name+".Point", Point);
        data.set(name+".Command",Command);
        data.set(name+".Type",Type);
        data.set(name+".Pic",Pic);
        data.set(name+".TypeButton", ""+TypeButton);
        data.set(name+".TopAll",iAll);
        data.save();
    }
    public Config getData(){
        return new Config(plugin.getDataFolder() + "/manager.yml", Config.YAML);
    }
    public Config getData1(){
        return new Config(plugin.getDataFolder() + "/gamechat.yml", Config.YAML);
    }
    public void t3(String string){
        Config data = new Config(plugin.getDataFolder() + "/savegamechat.yml", Config.YAML);
        data.set("Content", string);
        data.save();
    }
    public Config t3C(){
        return new Config(plugin.getDataFolder() + "/savegamechat.yml", Config.YAML);
    }
    public Object[] GameList() {
        ArrayList<String> games = new ArrayList<String>();
        Config config = new Config(plugin.getDataFolder() + "/gamechat.yml", Config.YAML);
        config.getAll().forEach((name, location) -> {
            games.add(name);
        });
        return games.toArray();
    }
    public void AddPoint(String p, int amount){
        Config data2 = new Config(plugin.getDataFolder() + "/player.yml", Config.YAML);
        data2.set(p, data2.getInt(p) + amount);
        data2.save();
    }
    public void RemovePoint(String p, int amount){
        Config data2 = new Config(plugin.getDataFolder() + "/player.yml", Config.YAML);
        data2.set(p, data2.getInt(p) - amount);
        data2.save();
    }
    public int getPoint(String p){
        Config data2 = new Config(plugin.getDataFolder() + "/player.yml", Config.YAML);
        return data2.getInt(p);
    }
    public Config getPointData(){
        return new Config(plugin.getDataFolder() + "/player.yml", Config.YAML);
    }
    public String TopGameChat(int i) {
        Map<String, Integer> counter = arsorteieieiei();
        int viewN = 1;
        String MessageTop = "";
        for (Map.Entry<String, Integer> entry : counter.entrySet()) {
            String nameee = entry.getKey();
            Integer money = entry.getValue();
            MessageTop = "§f " + viewN + ". §7" + nameee + "    " + money + " §f" + " Point\n";
            viewN++;
            if (viewN == i) {
                return MessageTop;
            }
        }
        return MessageTop;
    }

    private Map<String, Integer> arsorteieieiei() {
        HashMap<String, Integer> map = new HashMap<>();
        GameChatAPI gameChatAPI = new GameChatAPI(plugin);
        for (Map.Entry<String, Object> entry : gameChatAPI.getPointData().getAll().entrySet()) {
            map.put(entry.getKey(), (Integer) entry.getValue());
        }
        Map<String, Integer> sorted = map;
        sorted = map.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        return sorted;
    }
    public void removeButton(String game, Player p) {
        ArrayList<String> games = new ArrayList<String>();
        Config config = new Config(plugin.getDataFolder() + "/gamechat.yml", Config.YAML);
        config.getAll().forEach((name, location) -> {
            games.add(name);
        });
        if (games.contains(game)) {
            config.remove(game);
            config.save();
            config.reload();
            p.sendMessage(this.getData().getString("Setting.Prefix")+
                    "§aคุณได้ลบปุ่ม §b"
                    +game+
                    " §aสำเร็จ");
        } else {
            p.sendMessage("ไม่พบ data");
        }
    }
}
