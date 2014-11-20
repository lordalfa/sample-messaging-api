/*
 */
package it.dv.samples.techallenge.model;

import java.io.Serializable;

/**
 * An User token item
 *
 * @author davidvotino
 */
public class UserToken implements Serializable {

    private String username;
    private String apiToken;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.username != null ? this.username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserToken other = (UserToken) obj;
        if ((this.username == null) ? (other.username != null) : !this.username.equals(other.username)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserToken{" + "username=" + username + ", apiToken=" + apiToken + '}';
    }

}
