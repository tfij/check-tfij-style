package pl.tfij.checktfijstyle.checks;

import org.junit.jupiter.api.Test;
import pl.tfij.checktfijstyle.checks.MethodParameterAlignmentCheck;
import pl.tfij.checktfijstyle.fixture.TestCheckstyle;

class MethodParameterAlignmentCheckTest {

    private final TestCheckstyle checkstyle = new TestCheckstyle(MethodParameterAlignmentCheck.class);

    @Test
    void noMethodIssues() {
        checkstyle.check("paramsAlignmentInDeclaration/NoMethodIssues.java");

        checkstyle.assertNoViolations();
    }

    @Test
    void badMethodParamAlignments() {
        checkstyle.check("paramsAlignmentInDeclaration/BadMethodParamAlignments.java");

        checkstyle.assertViolationCount(5);
        checkstyle.assertViolation(4, 5, "Lines in method parameter declaration list must be aligned.");
        checkstyle.assertViolation(8, 5, "Lines in method parameter declaration list must be aligned.");
        checkstyle.assertViolation(12, 5, "Lines in method parameter declaration list must be aligned.");
        checkstyle.assertViolation(16, 5, "Lines in method parameter declaration list must be aligned.");
        checkstyle.assertViolation(19, 5, "Lines in method parameter declaration list must be aligned.");
    }

    @Test
    void badRecordParamAlignments() {
        checkstyle.check("paramsAlignmentInDeclaration/BadRecordParamAlignments.java");

        checkstyle.assertViolationCount(1);
        checkstyle.assertViolation(4, 1, "Lines in method parameter declaration list must be aligned.");
    }
}