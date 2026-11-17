
package com.example;

public class EmployeeValidator {

    public static boolean isValid(String name, String city) {

        return name != null
                && !name.isBlank()
                && city != null
                && !city.isBlank();
    }
}
