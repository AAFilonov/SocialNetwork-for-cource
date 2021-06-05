package org.practical3.utils;

import java.io.IOException;

public interface RequestProcessor<T,U> {
    void process(T t,U u) throws Exception;
}
