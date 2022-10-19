package pl.tfij.checktfijstyle.checks;

import org.junit.jupiter.api.Test;
import pl.tfij.checktfijstyle.fixture.TestCheckstyle;

class MethodCallParameterLinesCheckTest {

    private final TestCheckstyle checkstyle = new TestCheckstyle(MethodCallParameterLinesCheck.class);

    @Test
    void noMethodCallIssues() {
        checkstyle.check("MethodCallParameterLinesCheck/ValidLines.java");

        checkstyle.assertNoViolations();
    }

    @Test
    void badMethodCallParamLines() {
        checkstyle.check("MethodCallParameterLinesCheck/NonValidLines.java");

        checkstyle.assertViolationCount(9);
        checkstyle.assertViolation(5, 24, "Method parameters must be placed on a single line or on separate lines.");
        checkstyle.assertViolation(9, 12, "Method parameters must be placed on a single line or on separate lines.");
        checkstyle.assertViolation(12, 9, "Method parameters must be placed on a single line or on separate lines.");
        checkstyle.assertViolation(15, 19, "Method parameters must be placed on a single line or on separate lines.");
        checkstyle.assertViolation(20, 9, "Method parameters must be placed on a single line or on separate lines.");
        checkstyle.assertViolation(25, 9, "Method parameters must be placed on a single line or on separate lines.");
        checkstyle.assertViolation(30, 9, "Method parameters must be placed on a single line or on separate lines.");
        checkstyle.assertViolation(37, 15, "Method parameters must be placed on a single line or on separate lines.");
        checkstyle.assertViolation(40, 10, "Method parameters must be placed on a single line or on separate lines.");
    }

    @Test
    void ignoreMethodCallParamLines() {
        TestCheckstyle checkstyle = new TestCheckstyle(
                MethodCallParameterLinesCheck.class,
                c -> {
                    c.addProperty("ignoreMethods", "Map.of");
                    c.addProperty("ignoreMethods", "abc");
                    c.addProperty("ignoreMethods", "Bar");
                });

        checkstyle.check("MethodCallParameterLinesCheck/IgnoreMethodCallViolation.java");

        checkstyle.assertViolationCount(3);
        checkstyle.assertViolation(11, 16, "Method parameters must be placed on a single line or on separate lines.");
        checkstyle.assertViolation(14, 11, "Method parameters must be placed on a single line or on separate lines.");
        checkstyle.assertViolation(20, 9, "Method parameters must be placed on a single line or on separate lines.");
    }
}