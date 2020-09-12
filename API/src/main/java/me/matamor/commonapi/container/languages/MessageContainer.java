package me.matamor.commonapi.container.languages;

import me.matamor.commonapi.container.ContainerEntry;
import me.matamor.commonapi.container.config.ConfigContainer;
import me.matamor.commonapi.utils.CastUtils;
import me.matamor.commonapi.utils.serializer.SerializationException;
import me.matamor.commonapi.utils.serializer.Serializer;

public class MessageContainer extends ConfigContainer<String> {

    public MessageContainer(Class<? extends ContainerEntry<String>> enumClass) {
        super(enumClass, new Serializer<String>() {
            @Override
            public Object serialize(String value) throws SerializationException {
                return value;
            }

            @Override
            public String deserialize(Object serialized) throws SerializationException {
                try {
                    return CastUtils.asString(serialized);
                } catch (CastUtils.FormatException e) {
                    throw new SerializationException("Invalid String: " + serialized);
                }
            }
        });
    }
}
