package quarri6343.unredstone.impl.command;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import quarri6343.unredstone.common.PlayerEventHandler;
import quarri6343.unredstone.api.CommandBase;
import quarri6343.unredstone.utils.ItemCreator;

/**
 * 管理者に管理用メニューを配布するコマンド
 */
public class CommandUnRedstone extends CommandBase {

    private static final String commandName = "unredstone";

    public CommandUnRedstone() {
        super(commandName, 0, 0, true);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @Nullable String[] arguments) {
        ((Player) sender).getInventory().addItem(new ItemCreator(Material.STICK).setName(Component.text(PlayerEventHandler.menuItemName)).create());
        return true;
    }
}
