package jim.katunguka;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // a work around for static @BeforeAll or @AfterAll
public class MathUtilsTest {
    MathUtils mathUtils;
    TestInfo testInfo;
    TestReporter testReporter;

    @BeforeAll
    void beforeAllInit(){ //without the @TestInstance, this must be static.
        System.out.println("This runs before all");
    }

    @BeforeEach
    void init(TestInfo testInfo, TestReporter testReporter){
        this.testInfo =testInfo;
        this.testReporter = testReporter;
       mathUtils = new MathUtils();
   }

   @AfterEach
   void cleanup(){
       System.out.println("cleaning up.");
   }

   @Nested
   @DisplayName("Add Method")
   class AddTest{

       @Test
       @DisplayName("Testing the add Positive method")
       void testAddPositive(){
           assertEquals(2, mathUtils.add(1, 1), "The assertion method should add two numbers");
       }

       @Test
       @DisplayName("Testing the add Negative method")
       void testAddNegative(){
           assertEquals(-2, mathUtils.add(-1, -1), "The assertion method should add two numbers");
       }

   }

    @RepeatedTest(3) //repeated tests
    void testComputeCircleRadius(RepetitionInfo repetitionInfo){
        //repetitionInfo.getCurrentRepetition();
        assertEquals(314.1592653589793, mathUtils.computeCircleArea(10), "Should return the right circle area");
    }

    @Test
    @EnabledOnOs(OS.WINDOWS) //Conditional Execution, enable on various OS
    void testDivide(){
        //assumeFalse(true); //Assumptions
        assertThrows(ArithmeticException .class, () -> mathUtils.divide(1, 0), "Divide by 0 should throw");
    }

    @Test
    @DisplayName("TDD method should not run")
    @Disabled
    void testDisabled(){
        fail("This test is disabled");
    }

    @Test
    @Tag("Math")
    @DisplayName("Multiply method")
    void testMultiply(){
       // assertEquals(4, mathUtils.multiply(2, 2),"should return the right product");
        System.out.println("Running " + testInfo.getDisplayName() + " with tags " + testInfo.getTags());
        //testReporter.publishEntry();
        assertAll( //new to junit 5
                () -> assertEquals(4, mathUtils.multiply(2, 2)),
                () -> assertEquals(0, mathUtils.multiply(2, 0)),
                () -> assertEquals(-2, mathUtils.multiply(2, -1))
        );
    }
}
