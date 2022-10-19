package pl.tfij.checktfijstyle.checks;

import org.junit.jupiter.api.Test;
import pl.tfij.checktfijstyle.fixture.TestCheckstyle;

class MethodCallParameterAlignmentCheckTest {
    private final TestCheckstyle checkstyle = new TestCheckstyle(MethodCallParameterAlignmentCheck.class);

    @Test
    void noMethodCallIssues() {
        checkstyle.check("MethodCallParameterAlignmentCheck/ValidAlignment.java");

        checkstyle.assertNoViolations();
    }

    @Test
    void badMethodCallParamLines() {
        checkstyle.check("MethodCallParameterAlignmentCheck/NonValidAlignment.java");

        checkstyle.assertViolationCount(8);
        checkstyle.assertViolation(5, 24, "Lines in method call parameter list must be aligned.");
        checkstyle.assertViolation(9, 12, "Lines in method call parameter list must be aligned.");
        checkstyle.assertViolation(13, 9, "Lines in method call parameter list must be aligned.");
        checkstyle.assertViolation(18, 9, "Lines in method call parameter list must be aligned.");
        checkstyle.assertViolation(23, 9, "Lines in method call parameter list must be aligned.");
        checkstyle.assertViolation(29, 9, "Lines in method call parameter list must be aligned.");
        checkstyle.assertViolation(38, 15, "Lines in method call parameter list must be aligned.");
        checkstyle.assertViolation(41, 10, "Lines in method call parameter list must be aligned.");
    }
}