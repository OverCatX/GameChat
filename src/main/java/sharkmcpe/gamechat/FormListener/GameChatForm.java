package sharkmcpe.gamechat.FormListener;

import cn.nukkit.Player;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.*;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import sharkmcpe.gamechat.API.GameChatAPI;
import sharkmcpe.gamechat.Loader;

public class GameChatForm implements Listener {

    private Loader plugin;
    static int ManageGame = 0x90543535;
    static int ManageMessage = 0x9354353;
    static int GameUI = 0x9405355;
    static int Content = 0x95035833;
    public GameChatForm(Loader plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void ResponseAd(PlayerFormRespondedEvent event) {
        if (event.wasClosed()) return;
        String titles = "SettingGameChat";
        Player player = event.getPlayer();
        if (event.getWindow() instanceof FormWindowSimple) {
            String title = ((FormWindowSimple) event.getWindow()).getTitle();
            int button = ((FormWindowSimple) event.getWindow()).getResponse().getClickedButtonId();
            if(title.equals(titles)){
                if(button == 0){
                    ManageMessage(player, "");
                }
                if(button == 1){
                    ManageUI(player);
                }
            }
        }
    }
    public void AdminFormGame(Player player) {
        FormWindowSimple form = new FormWindowSimple("SettingGameChat", "ตั้งค่าต่างๆ");
        form.addButton(new ElementButton("Manage Message&Reward\nจัดการเงินและของรางวัล"));
        form.addButton(new ElementButton("Manage GameChatUI\nจัดการหน้า UI,เพิ่มปุ่ม"));
        player.showFormWindow(form);
    }
    @EventHandler
    public void MaUI(PlayerFormRespondedEvent event) {
        if (event.wasClosed()) return;
        String titles = "ManageUI";
        Player player = event.getPlayer();
        if (event.getWindow() instanceof FormWindowSimple) {
            String title = ((FormWindowSimple) event.getWindow()).getTitle();
            int button = ((FormWindowSimple) event.getWindow()).getResponse().getClickedButtonId();
            if(title.equals(titles)){
                if(button == 0){
                    ManageGameUI(player, "");
                }
                if(button == 1){
                    ManageContent(player);
                }
                if(button == 2){
                    DeleteButton(player);
                }
            }
        }
    }
    public void ManageUI(Player player) {
        FormWindowSimple form = new FormWindowSimple("ManageUI", "จัดการหน้า UI");
        form.addButton(new ElementButton("AddButton\nเพิ่มปุ่มต่างๆ"));
        form.addButton(new ElementButton("ManageUI\nจัดการหน้า UI"));
        form.addButton(new ElementButton("DeleteButton\nลบปุ่ม"));
        player.showFormWindow(form);
    }
    @EventHandler
    public void DButton(PlayerFormRespondedEvent event) {
        if (event.wasClosed()) return;
        String titles = "DeleteButton";
        GameChatAPI gameChatAPI = new GameChatAPI(plugin);
        Player player = event.getPlayer();
        if (event.getWindow() instanceof FormWindowSimple) {
            String title = ((FormWindowSimple) event.getWindow()).getTitle();
            String button = ((FormWindowSimple) event.getWindow()).getResponse().getClickedButton().getText();
            if(title.equals(titles)){
                for (Object gameAPI: gameChatAPI.GameList()){
                    String b1 = gameAPI.toString().replace("?n?", "\n");
                    if(button.equals(b1)){
                        gameChatAPI.removeButton(gameAPI.toString(), player);
                    }
                    break;
                }
            }
        }
    }
    public void DeleteButton(Player player) {
        GameChatAPI gameChatAPI = new GameChatAPI(plugin);
        FormWindowSimple form = new FormWindowSimple("DeleteButton", "");
        for (Object gameAPI: gameChatAPI.GameList()){
            String button = gameAPI.toString().replace("?n?", "\n");
            form.addButton(new ElementButton(button));
        }
        player.showFormWindow(form);
    }
    @EventHandler
    public void Content(PlayerFormRespondedEvent event) {
        if (event.wasClosed()) return;
        String titles = "ManageContent";
        GameChatAPI gameChatAPI = new GameChatAPI(plugin);
        Player player = event.getPlayer();
        if (event.getWindow() instanceof FormWindowCustom) {
            String title = ((FormWindowCustom) event.getWindow()).getTitle();
            String Name = ((FormWindowCustom) event.getWindow()).getResponse().getInputResponse(0);
            if (event.getFormID() == Content) {
                if (title.equals(titles)) {
                    gameChatAPI.t3(Name);
                    player.sendMessage(gameChatAPI.getData().getString("Setting.Prefix")+" §aคุณได้แก้ไข Content สำเร็จ");
                                    }
            }
        }
    }
    public void ManageContent(Player player) {
        FormWindowCustom form = new FormWindowCustom("ManageContent");
        ElementInput input6 = new ElementInput("§2ข้อความอธิบาย §7[§6String§7]\n§cWARN§f: §l§eใช้ ?point? เพื่อแสดงคะแนนผู้เล่น ใช้?n?เพื่อเว้นบรรทัด","คะแนนของท่าน ?point?");
        form.addElement(input6);
        player.showFormWindow(form, Content);
    }
    public void ManageGameUI(Player player, String content) {
        FormWindowCustom formWindowCustom = new FormWindowCustom("GameChatUI");
        ElementLabel label = new ElementLabel(content);
        ElementDropdown ii = new ElementDropdown("§dชนิด§cของ§6ปุ่ม§7[§6String§7]\n§cWARN§f: §eTrade คือใช้คะแนนแลกของ §6Top คืออันดับคะแนน");
        ii.addOption("Trade");
        ii.addOption("Top");
        ElementInput input = new ElementInput("§bชื่อปุ่ม §7[§6String§7]\n§cWARN§f: §l§eหากอยากเพิ่มอีกบรรทัดให้ ?n?","Diamond x10?n?ใช้ 2 คะแนน");
        ElementInput input1 = new ElementInput("§dคะแนนที่ใช้แลกของ §7[§6Integer+§7]\n§cWARN§f: §l§eหากเลือกปุ่ม Top ให้ใส่ §f0","10");
        ElementInput input2 = new ElementInput("§aCommand §7[§6String§7] §eโปรดใส่ชื่อผู้เล่นเป็น ?player?\n§cWARN§f: §l§eหากเลือกปุ่ม Top ให้ใส่ §f-","givemoney ?player? 1000");
        ElementDropdown dropdown1 = new ElementDropdown("§dชนิด§cของ§6รูปภาพ");
        dropdown1.addOption("path");
        dropdown1.addOption("url");
        ElementInput input4 = new ElementInput("§6รูปภาพ §7[§6String§7]\n§cWARN§f: §l§eหากไม่เอาให้เว้นว่าง","textures/items/diamond_sword");
        ElementInput input5 = new ElementInput("§cจำนวนอันดับเกมส์แชท §7[§6Integer+§7]\n§cWARN§f: §l§eหากเลือกปุ่ม แลกของ ให้ใส่ §f0","10");
        formWindowCustom.addElement(label);
        formWindowCustom.addElement(ii);
        formWindowCustom.addElement(input);
        formWindowCustom.addElement(input1);
        formWindowCustom.addElement(input2);
        formWindowCustom.addElement(dropdown1);
        formWindowCustom.addElement(input4);
        formWindowCustom.addElement(input5);
        player.showFormWindow(formWindowCustom, ManageGame);
    }
    @EventHandler
    public void ResponseManageUI(PlayerFormRespondedEvent event) {
        if (event.wasClosed()) return;
        String titles = "GameChatUI";
        String name = "Setting";
        GameChatAPI gameChatAPI = new GameChatAPI(plugin);
        String prefix = (String) gameChatAPI.getData().get(name+".Prefix");
        Player player = event.getPlayer();
        if (event.getWindow() instanceof FormWindowCustom) {
            String label = ((FormWindowCustom) event.getWindow()).getResponse().getLabelResponse(0);
            String title = ((FormWindowCustom) event.getWindow()).getTitle();
            int typeb = ((FormWindowCustom) event.getWindow()).getResponse().getDropdownResponse(1).getElementID();
            String Name = ((FormWindowCustom) event.getWindow()).getResponse().getInputResponse(2);
            String Price = ((FormWindowCustom) event.getWindow()).getResponse().getInputResponse(3);
            String Command = ((FormWindowCustom) event.getWindow()).getResponse().getInputResponse(4);
            String Type = ((FormWindowCustom) event.getWindow()).getResponse().getDropdownResponse(5).getElementContent();
            String Pic = ((FormWindowCustom) event.getWindow()).getResponse().getInputResponse(6);
            String I = ((FormWindowCustom) event.getWindow()).getResponse().getInputResponse(7);
            if(event.getFormID() == ManageGame) {
                if (title.equals(titles)) {
                    try {
                        int i = Integer.parseInt(Price);
                        int ii = Integer.parseInt(I);
                        if (Name.isEmpty()) {
                            ManageGameUI(player, "§cโปรดกรอกชื่อปุ่ม");
                            return;
                        }
                        for (Object gameAPI: gameChatAPI.GameList()){
                            if(Name.equals(gameAPI.toString())){
                                ManageGameUI(player, "§cคุณมีชื่อปุ่มนี้อยู่แล้ว");
                                return;
                            }
                        }
                        if (Price.isEmpty()) {
                            ManageGameUI(player, "§cโปรดกรอกคะแนนที่ใช้แลกของ");
                            return;
                        }
                        if (i < 0) {
                            ManageGameUI(player, "§cโปรดใส่จำนวนเต็มบวก");
                            return;
                        }
                        if (Command.isEmpty()) {
                            ManageGameUI(player, "§cโปรดกรอก Command");
                            return;
                        }
                        if (I.isEmpty()) {
                            ManageGameUI(player, "§cโปรดกรอกจำนวนอันดับ");
                            return;
                        }
                        if(ii < 0){
                            ManageGameUI(player, "§cโปรดใส่จำนวนเต็มบวก");
                            return;
                        }
                        if(typeb == 0){
                            String TypeButton = "Trade";
                            gameChatAPI.SetButton(Name, i, Command, Type, Pic, TypeButton, 0);
                            player.sendMessage(prefix+" §aคุณได้สร้างปุ่ม §b"+Name+"\n§dชนิด: §fแลกของ §aสำเร็จแล้ว");
                            return;
                        }
                        if (typeb == 1) {
                            String TypeButton = "Top";
                            gameChatAPI.SetButton(Name, i, Command, Type, Pic, TypeButton, ii+1);
                            player.sendMessage(prefix+" §aคุณได้สร้างปุ่ม §b"+Name+"\n§dชนิด: §fอันดับคะแนนแชทเกมส์ §aสำเร็จแล้ว");
                            return;
                        }
                    } catch (NumberFormatException e1) {
                        ManageGameUI(player, "§cเกิดข้อผิดพลาดโปรดลองอีกครั้ง");
                    }
                }
            }
        }
    }
    
    public void ManageMessage(Player player, String content) {
        GameChatAPI api = new GameChatAPI(plugin);
        String name = "Setting";
        String prefix = (String) api.getData().get(name+".Prefix");
        int time = api.getData().getInt(name+".Time");
        int Mintimenow = time/60;
        FormWindowCustom formWindowCustom = new FormWindowCustom("ManageMessage");
        ElementLabel label = new ElementLabel(content);
        ElementLabel label1 = new ElementLabel("§cข้อ§aความ§6ปัจจุบัน§f:\n§bPrefix§f: "+prefix+
                "\n§dข้อความ1§f: "+api.getData().getString(name+".Message1")+
                "\n§aข้อความ2§f: "+api.getData().getString(name+".Message2")+
                "\n§cเมื่อตอบชนะการพิมพ์จะได้เงินรางวัล§f: "+api.getData().getString(".Eco")+
                "\n§eจำนวนเงินรางวัลน้อยสุด§f: "+api.getData().getInt(".EcoMin")+
                "\n§6จำนวนเงินรางวัลมากสุด§f: "+api.getData().getInt(".EcoMax")+
                "\n§5เวลาการขึ้น Broadcast แชทเกมส์§f: "+Mintimenow+" §6Minutes"+
                "\n§3PrefixTop§f: "+api.getData().getString(name+".PrefixTop"));
        ElementInput input = new ElementInput("§bPrefix §7[§6String§7]", prefix);
        ElementInput input1 = new ElementInput("§dข้อความ1 §7[§6String§7]",api.getData().getString(name+".Message1"));
        ElementInput input2 = new ElementInput("§aข้อความ2 §7[§6String§7]",api.getData().getString(name+".Message2"));
        ElementDropdown inputs = new ElementDropdown("§eเมื่อตอบชนะการพิมพ์จะได้เงินรางวัล\n§2True §f= §aเปิดใช้งาน §4False §f= §cปิดใช้งาน");
        inputs.addOption("true");
        inputs.addOption("false");
        ElementInput input3 = new ElementInput("§eจำนวนเงินรางวัลน้อยสุด §7[§6Integer+§7]",api.getData().getString(name+".EcoMin"));
        ElementInput input4 = new ElementInput("§6จำนวนเงินรางวัลมากสุด §7[§6Integer+§7]",api.getData().getString(name+".EcoMax"));
        ElementInput input5 = new ElementInput("§5เวลาการขึ้น Broadcast แชทเกมส์ §7(§6Minute§7) §7[§6Integer+§7]\n§eเมื่อคุณกด Submit เสร็จโปรด /reload เพื่อให้ Task ทำงานตามSetting", String.valueOf(Mintimenow));
        ElementInput input6 = new ElementInput("§3PrefixTop §7[§6String§7]",api.getData().getString(name+".PrefixTop"));
        formWindowCustom.addElement(label);
        formWindowCustom.addElement(label1);
        formWindowCustom.addElement(input);
        formWindowCustom.addElement(input1);
        formWindowCustom.addElement(input2);
        formWindowCustom.addElement(inputs);
        formWindowCustom.addElement(input3);
        formWindowCustom.addElement(input4);
        formWindowCustom.addElement(input5);
        formWindowCustom.addElement(input6);
        player.showFormWindow(formWindowCustom, ManageMessage);
    }
    @EventHandler
    public void MessageResponse(PlayerFormRespondedEvent event) {
        if (event.wasClosed()) return;
        String titles = "ManageMessage";
        String name = "Setting";
        GameChatAPI gameChatAPI = new GameChatAPI(plugin);
        String prefix = gameChatAPI.getData().getString(name+".Prefix");
        Player player = event.getPlayer();
        if (event.getWindow() instanceof FormWindowCustom) {
            String title = ((FormWindowCustom) event.getWindow()).getTitle();
            String label = ((FormWindowCustom) event.getWindow()).getResponse().getLabelResponse(0);
            String PrefixName = ((FormWindowCustom) event.getWindow()).getResponse().getInputResponse(2);
            String Message1 = ((FormWindowCustom) event.getWindow()).getResponse().getInputResponse(3);
            String Message2 = ((FormWindowCustom) event.getWindow()).getResponse().getInputResponse(4);
            String winprize = ((FormWindowCustom) event.getWindow()).getResponse().getDropdownResponse(5).getElementContent();
            String Ecomin = ((FormWindowCustom) event.getWindow()).getResponse().getInputResponse(6);
            String EcoMax = ((FormWindowCustom) event.getWindow()).getResponse().getInputResponse(7);
            String time = ((FormWindowCustom) event.getWindow()).getResponse().getInputResponse(8);
            String top = ((FormWindowCustom) event.getWindow()).getResponse().getInputResponse(9);
            if(event.getFormID() == ManageMessage) {
                if (title.equals(titles)) {
                    try {
                        int ecomin = Integer.parseInt(Ecomin);
                        int ecomax = Integer.parseInt(EcoMax);
                        int i = Integer.parseInt(time) * 60;
                        if (PrefixName.isEmpty()) {
                            ManageMessage(player, "§cโปรดอย่าเว้นว่าง §6[§fPrefix§6]");
                            return;
                        }
                        if (Message1.isEmpty()) {
                            ManageMessage(player, "§cโปรดอย่าเว้นว่าง §6[§fข้อความ1§6]");
                            return;
                        }
                        if (Message2.isEmpty()) {
                            ManageMessage(player, "§cโปรดอย่าเว้นว่าง §6[§fข้อความ2§6]");
                            return;
                        }
                        if (ecomin < 0) {
                            ManageMessage(player, "§cโปรดใส่จำนวนเต็มบวก §6[§fจำนวนเงินรางวัลน้อยสุด§6]");
                            return;
                        }
                        if (ecomax < 0) {
                            ManageMessage(player, "§cโปรดใส่จำนวนเต็มบวก §6[§fจำนวนเงินรางวัลมากสุด§6]");
                            return;
                        }
                        if (top.isEmpty()) {
                            ManageMessage(player, "§cโปรดอย่าเว้นว่าง §6[§fPrefixTop§6]");
                            return;
                        }
                        if (winprize.equals("true")) {
                            gameChatAPI.SetnewManager(name, PrefixName, Message1, Message2, "true", ecomin, ecomax, i, top);
                            player.sendMessage(prefix+" §aคุณได้แก้ไขข้อความสำเร็จแล้ว\n§cWarn§b: §eอย่าลืม §d/reload §6ด้วยนะ");
                            return;
                        }
                        if (winprize.equals("false")) {
                            gameChatAPI.SetnewManager(name, PrefixName, Message1, Message2, "false", ecomin, ecomax, i, top);
                            player.sendMessage(prefix+" §aคุณได้แก้ไขข้อความสำเร็จแล้ว\n§cWarn§b: §eอย่าลืม §d/reload §6ด้วยนะ");
                            return;
                        }
                    } catch (NumberFormatException e1) {
                        ManageMessage(player, "§cเกิดข้อผิดพลาดโปรดลองอีกครั้ง");
                    }
                }
            }
        }
    }
    @EventHandler
    public void GameResponse(PlayerFormRespondedEvent event) {
        if (event.wasClosed()) return;
        GameChatAPI gameChatAPI = new GameChatAPI(plugin);
        String name = "Setting";
        Player player = event.getPlayer();
        if (event.getWindow() instanceof FormWindowSimple) {
            String title = ((FormWindowSimple) event.getWindow()).getTitle();
            String button = ((FormWindowSimple) event.getWindow()).getResponse().getClickedButton().getText();
            if(event.getFormID() == GameUI){
                if(title.equals(gameChatAPI.getData().getString(name+".Prefix"))){
                    for (Object gameAPI: gameChatAPI.GameList()){
                        String b1 = gameAPI.toString().replace("?n?", "\n");
                        if(button.equals(b1)) {
                            if (gameChatAPI.getData1().getString(gameAPI.toString()+".TypeButton").equals("Trade")) {
                                if (gameChatAPI.getPoint(player.getName())
                                        >= gameChatAPI.getData1().getInt(gameAPI.toString()+".Point")) {
                                    gameChatAPI.RemovePoint(player.getName(), gameChatAPI.getData1().getInt(gameAPI.toString()+".Point"));
                                    String command = gameChatAPI.getData1().getString(gameAPI.toString()+".Command");
                                    String rs = command.replace("?player?", player.getName());
                                    plugin.getServer().getCommandMap().dispatch(new ConsoleCommandSender(), rs);
                                } else {
                                    player.sendMessage(gameChatAPI.getData().getString(name+".Prefix")+" §cคุณมีคะแนนเกมส์แชทไม่เพียงพอ");
                                }
                                return;
                            }
                            if(gameChatAPI.getData1().getString(gameAPI.toString()+".TypeButton").equals("Top")) {
                                player.sendMessage(gameChatAPI.getData().getString(name+".PrefixTop")+"\n"+gameChatAPI.TopGameChat(gameChatAPI.getData1().getInt(gameAPI.toString()+".TopAll")));
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
    public void GameChatUI(Player player) {
        GameChatAPI gameChatAPI = new GameChatAPI(plugin);
        String name = "Setting";
        String con = gameChatAPI.t3C().getString("Content").replace("?n?","\n").replace("?point?", String.valueOf(gameChatAPI.getPoint(player.getName())));
        FormWindowSimple form = new FormWindowSimple(gameChatAPI.getData().getString(name+".Prefix"), con);
        for (Object gameAPI: gameChatAPI.GameList()){
            String button = gameAPI.toString().replace("?n?", "\n");
            form.addButton(new ElementButton(button, new ElementButtonImageData(gameChatAPI.getData1().getString(gameAPI.toString()+".Type"), gameChatAPI.getData1().getString(gameAPI.toString()+".Pic"))));
        }
        player.showFormWindow(form, GameUI);
    }
}