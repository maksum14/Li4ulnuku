package com.softserve.edu.entity.user;

import com.softserve.edu.entity.Organization;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "PROVIDER_EMPLOYEE")
public class ProviderEmployee extends Employee {

    public enum ProviderEmployeeRole implements Role {
        PROVIDER_EMPLOYEE, PROVIDER_ADMIN;

        @Override
        public String roleName() {
            return name();
        }
    }

    public ProviderEmployee() {}

    public ProviderEmployee(String username) {
    }

    public ProviderEmployee(
            String username, String password, ProviderEmployeeRole role,
            Organization organization) {
        super(username, password, role, organization);
    }

    public ProviderEmployee(
            String username, String password, ProviderEmployeeRole role,
            Organization organization, String firstName, String lastName,
            String email, String phone) {
        super(username, password, role, organization, firstName, lastName, email, phone);
    }

    public void setRole(ProviderEmployeeRole role) {
        super.setRole(role);
    }
}
