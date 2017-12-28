package app.services.impl;

import app.services.api.PassKeyVerificationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PassKeyVerificationServiceImplTest {

    private static final String NON_HAHSED_PASS = "1111111111";
    private static final String SECOND_NON_HAHSED_PASS = "2222222222";
    private static final int WORKLOAD = 15;
    private static final String BCRYPT_START = "$2a$";
    private static final String CORRECT_HASHED_PASS = "$2a$10$bjNBEn8NGtyUdVGW060bLeQ27TeRfWB.j6bEVVL6b9vYQbZrSE2G.";
    private static final String INCORRECT_HASHED_PASS = "$2r$12$bjNBEnsNGtyUdVGW060bLeQ27TeRfWB.j6bEVVL6b9vYQbZrSE2G.";

    @Autowired
    private PassKeyVerificationService passKeyVerificationService;

    @Before
    public void init() throws Exception {
    }

    @Test
    public void validatePassKey() throws Exception {


    }

    @Test
    public void testHashPassKeyToReturnCorrectHashType() throws Exception {

        String hashResult = this.passKeyVerificationService.hashPassKey(NON_HAHSED_PASS);
        Assert.assertTrue("HashPassKey do not return correct type",hashResult.startsWith(BCRYPT_START));
    }

    @Test
    public void testHashPassKeyToReturnCorrectWorkLoad() throws Exception {

        Field workloadField = this.passKeyVerificationService.getClass().getDeclaredField("workload");
        workloadField.setAccessible(true);
        workloadField.set(this.passKeyVerificationService, WORKLOAD);

        String hashResult = this.passKeyVerificationService.hashPassKey(NON_HAHSED_PASS);
        int result = Integer.parseInt(hashResult.replace(BCRYPT_START, "").substring(0,2));
        Assert.assertEquals("HashPassKey do not return correct value",WORKLOAD,result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkPasskeyWithNull() throws Exception {

        boolean hashResult = this.passKeyVerificationService.checkPassKey(NON_HAHSED_PASS, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkPasskeyWithIncorrectHash() throws Exception {

        boolean hashResult = this.passKeyVerificationService.checkPassKey(NON_HAHSED_PASS, INCORRECT_HASHED_PASS);
    }

    @Test
    public void checkPasskeyWithCorrectHash() throws Exception {

        boolean hashResult = this.passKeyVerificationService.checkPassKey(NON_HAHSED_PASS, CORRECT_HASHED_PASS);
        Assert.assertTrue("Passkey checking is not working properly", hashResult);
    }

    @Test
    public void checkPasskeyWithInCorrectPasskey() throws Exception {

        boolean hashResult = this.passKeyVerificationService.checkPassKey(SECOND_NON_HAHSED_PASS, CORRECT_HASHED_PASS);
        Assert.assertFalse("Passkey checking is not working properly", hashResult);
    }

}