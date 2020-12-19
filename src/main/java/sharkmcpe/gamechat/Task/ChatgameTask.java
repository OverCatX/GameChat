package sharkmcpe.gamechat.Task;

import cn.nukkit.scheduler.PluginTask;
import sharkmcpe.gamechat.API.GameChatAPI;
import sharkmcpe.gamechat.Loader;
import sharkmcpe.gamechat.Math.RandomInteger;

public class ChatgameTask extends PluginTask<Loader> {
    public ChatgameTask(Loader owner) {
        super(owner);
    }

    @Override
    public void onRun(int currentTick) {
        String answer = "";
        int randomLength = RandomInteger.rand(5,10);
        String characters       = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int charactersLength = characters.length();
        for (int i = 0; i < randomLength; i++ ) {
            answer += characters.charAt((int) Math.floor(Math.random() * charactersLength));
        }
        String character = "Game"+answer;
        GameChatAPI api = new GameChatAPI(owner);
        String name = "Setting";
        String prefix = (String) api.getData().get(name+".Prefix");
        owner.getServer().broadcastMessage(prefix + api.getData().get(name+".Message1") + character +"\n"+api.getData().get(name+".Message2"));
        api.Save(character, 1);
    }
}
