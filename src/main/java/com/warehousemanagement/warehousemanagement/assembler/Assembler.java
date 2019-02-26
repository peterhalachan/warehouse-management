package com.warehousemanagement.warehousemanagement.assembler;

public interface Assembler<T, S> {

    S toDto(T t);

    T fromDto(S s);
}
