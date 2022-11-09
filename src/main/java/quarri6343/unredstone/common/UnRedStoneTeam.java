package quarri6343.unredstone.common;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * UnRedStoneを共に攻略するチームのデータクラス
 */
public class UnRedStoneTeam {

    public String name;
    public String color;
    public Location startLocation;
    //    public Location relayLocation1 = null;
//    public Location relayLocation2 = null;
    public Location endLocation;
    public Location joinLocation1;
    public Location joinLocation2;
    
    public List<Player> players = new ArrayList<>();
    
    public UnRedStoneTeam(String name, String color){
        this.name = name;
        this.color = color;
    }
}
