package server.model.prototype;

import server.model.employee.Employee;

public abstract class EmployeePrototype implements Cloneable {

    @Override
    public Object clone() throws CloneNotSupportedException {
        Employee copy = (Employee) super.clone();

        return copy;
    }
}
