package me.matamor.commonapi.utils.serializer;

import me.matamor.commonapi.utils.CastUtils;
import me.matamor.commonapi.utils.Time;

public class TimeSerializer implements Serializer<Long> {

    @Override
    public String serialize(Long value) throws SerializationException {
        return Time.toString(value);
    }

    @Override
    public Long deserialize(Object object) throws SerializationException {
        return Time.parseString(CastUtils.asString(object)).toMilliseconds();
    }
}
