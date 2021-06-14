package org.practical3.model.data;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.io.IOUtils;
import org.practical3.utils.Commons;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.InvalidParameterException;

public class User {
    public Integer userid;


    public String username;
    public String password;

    public java.sql.Date birthday;

    public String fullname;
    public Integer sex = 0;

    public String country ;
    public String city = null;
    public String school = null;
    public String university = null;

    public Integer[] subscriptions;
    public Integer[] followers;

    public User() {}

    public User(Integer userid)
    {
        this.userid = userid;
    }

    public User(Integer userid, String username, String password)
    {
        this.userid = userid;
        this.username = username;
        this.password = password;
    }
}