package pl.tfij.checktfijstyle.checks;

import org.junit.jupiter.api.Test;
import pl.tfij.checktfijstyle.fixture.TestCheckstyle;

class MethodEmptyLinesCheckTest {
    private final TestCheckstyle checkstyle = new TestCheckstyle(MethodEmptyLinesCheck.class);

    @Test
    void shouldNotReportViolations() {
        checkstyle.check("MethodEmptyLines/SampleTestClass.java");

        checkstyle.assertViolationCount(0);
    }

    @Test
    void methodsWithEmptyLines() {
        checkstyle.check("MethodEmptyLines/ViolatedTestClass.java");

        checkstyle.assertViolationCount(14);
        checkstyle.assertViolation(5, 1, "Empty lines in methods are not allowed.");
        checkstyle.assertViolation(19, 1, "Empty lines in methods are not allowed.");
        checkstyle.assertViolation(21, 1, "Empty lines in methods are not allowed.");
        checkstyle.assertViolation(25, 1, "Empty lines in methods are not allowed.");
        checkstyle.assertViolation(38, 1, "Empty lines in methods are not allowed.");
        checkstyle.assertViolation(43, 1, "Empty lines in methods are not allowed.");
        checkstyle.assertViolation(47, 1, "Empty lines in methods are not allowed.");
        checkstyle.assertViolation(48, 1, "Empty lines in methods are not allowed.");
        checkstyle.assertViolation(61, 1, "Empty lines in methods are not allowed.");
        checkstyle.assertViolation(70, 1, "Empty lines in methods are not allowed.");
        checkstyle.assertViolation(79, 1, "Empty lines in methods are not allowed.");
        checkstyle.assertViolation(86, 1, "Empty lines in methods are not allowed.");
        checkstyle.assertViolation(88, 1, "Empty lines in methods are not allowed.");
        checkstyle.assertViolation(89, 1, "Empty lines in methods are not allowed.");
    }
}