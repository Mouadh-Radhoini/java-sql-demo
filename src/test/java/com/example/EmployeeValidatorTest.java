
package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeValidatorTest {

    @Test
    void shouldAcceptValidEmployee() {

        assertTrue(
                EmployeeValidator.isValid("Ali", "Mahdia")
        );
    }

    @Test
    void shouldRejectEmptyName() {

        assertFalse(
                EmployeeValidator.isValid("", "Mahdia")
        );
    }

    @Test
    void shouldRejectEmptyCity() {

        assertFalse(
                EmployeeValidator.isValid("Ali", "")
        );
    }

    @Test
    void shouldRejectNullName() {

        assertFalse(
                EmployeeValidator.isValid(null, "Mahdia")
        );
    }
}
