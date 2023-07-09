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
        checkstyle.assertViolation(32, 1, "Empty lines in methods are not allowed.");
        checkstyle.assertViolation(37, 1, "Empty lines in methods are not allowed.");
        checkstyle.assertViolation(41, 1, "Empty lines in methods are not allowed.");
        checkstyle.assertViolation(42, 1, "Empty lines in methods are not allowed.");
        checkstyle.assertViolation(55, 1, "Empty lines in methods are not allowed.");
        checkstyle.assertViolation(64, 1, "Empty lines in methods are not allowed.");
        checkstyle.assertViolation(73, 1, "Empty lines in methods are not allowed.");
        checkstyle.assertViolation(80, 1, "Empty lines in methods are not allowed.");
        checkstyle.assertViolation(82, 1, "Empty lines in methods are not allowed.");
        checkstyle.assertViolation(83, 1, "Empty lines in methods are not allowed.");
    }
}