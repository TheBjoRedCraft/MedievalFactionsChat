package dev.thebjoredcraft.medievalfactionschat;

import com.dansplugins.factionsystem.MedievalFactions;
import com.dansplugins.factionsystem.faction.MfFaction;
import com.dansplugins.factionsystem.faction.MfFactionMember;
import com.dansplugins.factionsystem.faction.MfFactionService;
import com.dansplugins.factionsystem.player.MfPlayer;
import com.dansplugins.factionsystem.player.MfPlayerService;
import com.dansplugins.factionsystem.service.Services;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChatCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            MedievalFactions mf = (MedievalFactions) MedievalFactionsChat.getInstance().getServer().getPluginManager().getPlugin("MedievalFactions");
            if (mf == null) return false;

            Services services = mf.getServices();
            MfPlayerService playerService = services.getPlayerService();
            MfFactionService factionService = services.getFactionService();

            MedievalFactionsChat.getInstance().getServer().getScheduler().runTaskAsynchronously(MedievalFactionsChat.getInstance(), () -> {
                MfPlayer mfPlayer = playerService.getPlayerByBukkitPlayer(player);
                if (mfPlayer == null) {
                    return;
                }

                MfFaction faction = factionService.getFactionByPlayerId(mfPlayer.getId());
                if (faction == null) {
                    player.sendMessage("Du bist in keiner Faction!");
                } else {
                    if(args.length >=1) {
                        String msg = "";
                        for(int i = 0; i < args.length; i++) {
                            msg = msg + args[i] + " ";
                        }
                        for(MfFactionMember member : faction.getMembers()){
                            OfflinePlayer offlinePlayer = mfPlayer.toBukkit();
                            Player targetMember = offlinePlayer.getPlayer();
                            targetMember.sendMessage(MiniMessage.miniMessage().deserialize("<dark_gray>[<gradient:#3b92d1:#40d1db>Faction<dark_gray>]<reset> " + msg));
                        }
                    }else{
                        player.sendMessage("Bitte gib eine Nachricht ein.");
                    }
                }
            });
        }
        return false;
    }
}
