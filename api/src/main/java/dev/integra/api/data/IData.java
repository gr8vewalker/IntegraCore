package dev.integra.api.data;

public interface IData {

    Class<?> getType();

    Object getData();

    static IData of(Class<?> type, Object data) {
        return new IData() {
            @Override
            public Class<?> getType() {
                return type;
            }

            @Override
            public Object getData() {
                return data;
            }
        };
    }

}
