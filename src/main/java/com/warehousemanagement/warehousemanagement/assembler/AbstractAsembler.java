package com.warehousemanagement.warehousemanagement.assembler;

public abstract class AbstractAsembler<T, S> {

    public abstract S toDto(T t);

    public abstract T fromDto(S s);
}
