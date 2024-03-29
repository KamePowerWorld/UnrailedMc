package quarri6343.unredstone.impl.ui;

import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import quarri6343.unredstone.UnRedstone;
import quarri6343.unredstone.common.GlobalTeamHandler;
import quarri6343.unredstone.common.data.URData;
import quarri6343.unredstone.common.data.URTeam;
import quarri6343.unredstone.common.logic.URLogic;
import quarri6343.unredstone.utils.ItemCreator;

public class AdminMenuRow3 {

    private static URData getData() {
        return UnRedstone.getInstance().getData();
    }

    private static URLogic getLogic() {
        return UnRedstone.getInstance().getLogic();
    }

    public static void addElements(PaginatedGui gui, Player player) {
        ItemStack setMaxHoldableItemsItem = new ItemCreator(Material.OAK_LOG).setName(Component.text("プレイヤーが所持可能な最大の原木、丸石、線路の数"))
                .setLore(Component.text("現在: " + getData().maxHoldableItems.get() + "個").decoration(TextDecoration.ITALIC, false)).create();
        GuiItem setMaxHoldableItemsButton = new GuiItem(setMaxHoldableItemsItem,
                AdminMenuRow3::onSetMaxHoldableItemsButton);
        gui.setItem(18, setMaxHoldableItemsButton);

        ItemStack setCraftingCostItem = new ItemCreator(Material.COBBLESTONE).setName(Component.text("線路を一個作るのに必要な材料の数"))
                .setLore(Component.text("現在: " + getData().craftingCost.get() + "個").decoration(TextDecoration.ITALIC, false)).create();
        GuiItem setCraftingCostButton = new GuiItem(setCraftingCostItem,
                AdminMenuRow3::onSetCraftingCostButton);
        gui.setItem(19, setCraftingCostButton);

        ItemStack placeBeaconItem = new ItemCreator(Material.BEACON).setName(Component.text("チームのスタート地点と終了地点に目印としてビーコンを設置する")
                .color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)).create();
        GuiItem placeBeaconButton = new GuiItem(placeBeaconItem,
                AdminMenuRow3::onPlaceBeaconButton);
        gui.setItem(21, placeBeaconButton);

        GuiItem closeButton = new GuiItem(new ItemCreator(Material.BARRIER).setName(Component.text("閉じる")).create(),
                event -> gui.close(event.getWhoClicked()));
        gui.setItem(22, closeButton);

        GuiItem startButton = new GuiItem(new ItemCreator(Material.GREEN_WOOL).setName(Component.text("ゲームを開始")).setLore(getCanStartGameDesc()).create(),
                event -> {
                    getLogic().startGame((Player) event.getWhoClicked());
                    UIAdminMenu.openUI((Player) event.getWhoClicked());
                });
        gui.setItem(24, startButton);
        GuiItem endButton = new GuiItem(new ItemCreator(Material.RED_WOOL).setName(Component.text("ゲームを強制終了")).setLore(getCanTerminateGameDesc()).create(),
                event -> {
                    getLogic().endGame((Player) event.getWhoClicked(), null, URLogic.GameResult.FAIL, true);
                    UIAdminMenu.openUI((Player) event.getWhoClicked());
                });
        gui.setItem(26, endButton);
    }


    /**
     * @return 現在ゲームを開始できるかどうかを示した文
     */
    private static TextComponent getCanStartGameDesc() {
        return getLogic().gameStatus == URLogic.GameStatus.INACTIVE ?
                Component.text("開始可能").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)
                : Component.text("ゲームが進行中です!").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false);
    }

    /**
     * @return 現在ゲームを終了できるかどうかを示した文
     */
    private static TextComponent getCanTerminateGameDesc() {
        return getLogic().gameStatus == URLogic.GameStatus.ACTIVE ?
                Component.text("強制終了可能").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)
                : Component.text("強制終了できるゲームはありません").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false);
    }

    private static void onSetMaxHoldableItemsButton(InventoryClickEvent event) {
        UINumberConfiguration.openUI((Player) event.getWhoClicked(),getData().maxHoldableItems);
    }
    
    private static void onSetCraftingCostButton(InventoryClickEvent event){
        UINumberConfiguration.openUI((Player) event.getWhoClicked(),getData().craftingCost);
    }
    
    private static void onPlaceBeaconButton(InventoryClickEvent event){
        URTeam team = getData().teams.getTeambyName(getData().adminSelectedTeam);
        if(team == null){
            event.getWhoClicked().sendMessage(Component.text("チームが選択されていません").color(NamedTextColor.RED));
            return;
        }
        
        if(team.getStartLocation() == null){
            event.getWhoClicked().sendMessage(Component.text("チームの開始地点が存在しません").color(NamedTextColor.RED));
            return;
        }

        if(team.getEndLocation() == null){
            event.getWhoClicked().sendMessage(Component.text("チームの終了地点が存在しません").color(NamedTextColor.RED));
            return;
        }

        GlobalTeamHandler.placeBeaconBelowTeamLocations(team);
        event.getWhoClicked().sendMessage(Component.text("ビーコンを設置しました"));
    }
}
