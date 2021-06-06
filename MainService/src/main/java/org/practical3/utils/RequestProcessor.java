package org.practical3.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface RequestProcessor {
    void process(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
