package server.model.prototype;

import server.model.employee.EmployeeAccount;
import server.model.employee.EmployeeImage;

import java.io.Serializable;

public class EmployeeAccountPrototype implements Serializable{

    @Override
    public Object clone() throws CloneNotSupportedException {
        EmployeeAccount copy = (EmployeeAccount) super.clone();

        return copy;
    }
}
