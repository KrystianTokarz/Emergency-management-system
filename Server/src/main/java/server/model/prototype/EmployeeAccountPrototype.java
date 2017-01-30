package server.model.prototype;

import server.model.employee.EmployeeAccount;

import java.io.Serializable;

/**
 * Class (for Prototype Pattern) for clone employee accounts
 */
public class EmployeeAccountPrototype implements Serializable{

    @Override
    public Object clone() throws CloneNotSupportedException {
        EmployeeAccount copy = (EmployeeAccount) super.clone();

        return copy;
    }
}
