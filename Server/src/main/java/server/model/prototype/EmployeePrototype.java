package server.model.prototype;

import server.model.employee.Employee;
/**
 * Class (for Prototype Pattern) for clone Employee Entity
 */
public abstract class EmployeePrototype implements Cloneable {

    @Override
    public Object clone() throws CloneNotSupportedException {
        Employee copy = (Employee) super.clone();

        return copy;
    }
}
