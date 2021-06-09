
package org.practical3.model.data;


public class User  {
    public Integer userid;

    public String username;
    public String password;

    public Integer[] subscriptions;
    public Integer[] followers;

    public java.sql.Date birthday;

    public String fullname;
    public Integer sex = 0;

    public String country ;
    public String city = null;
    public String school = null;
    public String university = null;

    public User() {}

    public User(Integer userid)
    {
        this.userid = userid;
    }
}