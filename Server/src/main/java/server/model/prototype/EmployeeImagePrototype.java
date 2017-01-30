package server.model.prototype;

import server.model.employee.EmployeeImage;


/**
 * Class (for Prototype Pattern) for clone image accounts
 */
public abstract class EmployeeImagePrototype implements Cloneable {

    @Override
    public Object clone() throws CloneNotSupportedException {
        EmployeeImage copy = (EmployeeImage) super.clone();

        return copy;
    }
}
