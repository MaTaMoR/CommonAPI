package me.matamor.commonapi.particles;

import lombok.Getter;
import me.matamor.commonapi.commands.CommandArgs;
import me.matamor.commonapi.commands.ICommand;
import me.matamor.commonapi.commands.ICommandException;
import me.matamor.commonapi.modules.Module;
import me.matamor.commonapi.modules.java.JavaModule;
import me.matamor.commonapi.nms.NMSVersion;
import me.matamor.commonapi.particles.v1_12_R1_Down.ParticleControllerDown;
import me.matamor.commonapi.particles.v1_13_R1_Up.ParticleControllerUp;
import me.matamor.commonapi.utils.container.SimpleDataContainer;
import org.bukkit.entity.Player;

import java.util.Collections;

public class ParticleManager extends JavaModule {

    @Getter
    private static ParticleManager instance;

    @Getter
    private ParticleController particleController;

    @Override
    public void onEnable() {
        instance = this;

        if (NMSVersion.isGreaterEqualThan(NMSVersion.v1_12_R1)) {
            this.particleController = new ParticleControllerUp();
        } else {
            this.particleController = new ParticleControllerDown();
        }

        //Set the particle controller
        ParticleEffect.setParticleController(this.particleController);

        ICommand<ParticleManager> testCommand = new ICommand<ParticleManager>(this, "testparticle", new String[0]) {
            @Override
            public void onCommand(CommandArgs commandArgs) throws ICommandException {
                try {
                    ParticleEffect particleEffect = ParticleEffect.valueOf(commandArgs.getString(0));

                    Player player = commandArgs.getPlayer();

                    particleEffect.playParticle(player.getLocation(), new SimpleDataContainer(), Collections.singletonList(player));
                } catch (IllegalArgumentException e) {
                    commandArgs.sendMessage("Invalid particle %s", commandArgs.getString(0));
                    e.printStackTrace();
                }
            }
        };

        testCommand.register();
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
