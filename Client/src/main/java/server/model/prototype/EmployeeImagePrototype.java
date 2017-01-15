package server.model.prototype;

import server.model.employee.Employee;
import server.model.employee.EmployeeImage;

public abstract class EmployeeImagePrototype implements Cloneable {

    @Override
    public Object clone() throws CloneNotSupportedException {
        EmployeeImage copy = (EmployeeImage) super.clone();

        return copy;
    }
}
