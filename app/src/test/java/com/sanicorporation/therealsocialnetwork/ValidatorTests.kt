package com.sanicorporation.therealsocialnetwork

import com.sanicorporation.therealsocialnetwork.utils.ValidationUtil
import junit.framework.Assert.assertEquals
import org.junit.Test


class ValidatorTests {



        @Test
        fun emailValidator_correct() {
            assertEquals(ValidationUtil.emailIsValid("miltonputallaz@gmail.com"), true);
        }

        @Test
        fun passwordValidator_correct() {
            assertEquals(ValidationUtil.passwordIsValid("1234123"), true);
        }

        @Test
        fun verifyPassword_correct() {
            assertEquals(ValidationUtil.passwordAreValid("1234123","1234123"), true);
        }



}