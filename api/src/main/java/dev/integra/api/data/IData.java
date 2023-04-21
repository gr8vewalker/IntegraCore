package dev.integra.api.data;

public interface IData {

    Class<?> getType();

    Object getData();

    static IData of(Object data) {
        return new IData() {
            @Override
            public Class<?> getType() {
                return data.getClass();
            }

            @Override
            public Object getData() {
                return data;
            }
        };
    }

}
