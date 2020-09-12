package me.matamor.commonapi.messages;

import lombok.Getter;
import me.matamor.commonapi.container.Container;
import me.matamor.commonapi.container.ContainerEntry;
import me.matamor.commonapi.container.config.ConfigContainer;
import me.matamor.commonapi.container.languages.MessageContainer;
import me.matamor.commonapi.utils.StringUtils;
import me.matamor.commonapi.utils.annotation.FilePath;

/*

NOT IN USE DONT CALL IT WONT WORK

 */


//@PluginClass(CommonAPI.class)
@FilePath("messages.yml")
public enum Messages implements ContainerEntry<String> {

    NO_REQUIREMENTS("&cNo tienes permisos para hacer eso!"),
    ONLINE("&2Online&f"),
    OFFLINE("&4Offline&f"),
    TIME_SECOND("segundo"),
    TIME_MINUTE("minuto"),
    TIME_HOUR("hora"),
    TIME_DAY("dia"),
    TIME_YEAR("año");

    private static ConfigContainer<String> container = new MessageContainer(Messages.class);

    @Getter
    private final String path;

    @Getter
    private final String defaultValue;

    Messages(String defaultValue) {
        this.path = "Messages." + StringUtils.normalizeEnum(name());
        this.defaultValue = defaultValue;
    }

    @Override
    public String getDefault() {
        return this.defaultValue;
    }

    @Override
    public Container<String> getContainer() {
        return container;
    }

    @Override
    public String get() {
        return StringUtils.color((getContainer().hasEntry(this) ? getContainer().get(this) : getDefault()));
    }
}
