package com.test.employeeRecordManagement.utils;

public interface Mapper {
    <T,U> T map(U o,Class<T> typeToMap);
}
