package com.isaac.collegeapp.constants;

import org.springframework.stereotype.Component;

@Component
public class StaticRoles {


    protected String WRITE = "WRITE,";
    protected String READ = "READ,";

    public String getWRITE() {
        return WRITE;
    }

    public String getREAD() {
        return READ;
    }

    public String getDELETE() {
        return DELETE;
    }

    public String getVIP() {
        return VIP;
    }

    public String getADMIN() {
        return ADMIN;
    }

    protected String DELETE = "DELETE,";

    protected String VIP = "VIP,";

    protected String ADMIN = "ADMIN,";


}
