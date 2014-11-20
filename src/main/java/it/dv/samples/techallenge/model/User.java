/*
 */
package it.dv.samples.techallenge.model;

import java.io.Serializable;

/**
 * User domain object
 *
 * @author davidvotino
 */
public class User extends UserToken implements Serializable {

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
