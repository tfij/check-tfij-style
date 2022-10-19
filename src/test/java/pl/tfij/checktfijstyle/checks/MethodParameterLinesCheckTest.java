package pl.tfij.checktfijstyle.checks;

import org.junit.jupiter.api.Test;
import pl.tfij.checktfijstyle.fixture.TestCheckstyle;

class MethodParameterLinesCheckTest {

    private final TestCheckstyle checkstyle = new TestCheckstyle(MethodParameterLinesCheck.class);

    @Test
    void noMethodIssues() {
        checkstyle.check("MethodParameterLinesCheck/NoMethodIssues.java");

        checkstyle.assertNoViolations();
    }

    @Test
    void noConstructorIssues() {
        checkstyle.check("MethodParameterLinesCheck/NoConstructorIssues.java");

        checkstyle.assertNoViolations();
    }

    @Test
    void MethodParamsNotAllOneLineOrSeparateLines() {
        checkstyle.check("MethodParameterLinesCheck/MethodParamsNotAllOneLineOrSeparateLines.java");

        checkstyle.assertViolationCount(1);
        checkstyle.assertViolation(4, 5, "Method parameters must be placed on a single line or on separate lines.");
    }

    @Test
    void ConstructorParamsNotAllOneLineOrSeparateLines() {
        checkstyle.check("MethodParameterLinesCheck/ConstructorParamsNotAllOneLineOrSeparateLines.java");

        checkstyle.assertViolationCount(1);
        checkstyle.assertViolation(4, 5, "Method parameters must be placed on a single line or on separate lines.");
    }

    @Test
    void RecordArgumentsNotAllOneLineOrSeparateLines() {
        checkstyle.check("MethodParameterLinesCheck/NotValidRecordClass.java");

        checkstyle.assertViolationCount(1);
        checkstyle.assertViolation(3, 1, "Method parameters must be placed on a single line or on separate lines.");
    }

    @Test
    void noRecordIssue() {
        checkstyle.check("MethodParameterLinesCheck/ValidRecordClass.java");

        checkstyle.assertNoViolations();
    }

    @Test
    void should_not_allow_single_line_when_configured() {
        var checkstyle = new TestCheckstyle(MethodParameterLinesCheck.class,
                c -> c.addProperty("allowSingleLine", "false"));

        checkstyle.check("MethodParameterLinesCheck/NoMethodIssues.java");

        checkstyle.assertViolationCount(2);
        checkstyle.assertViolation(31,5,"Method parameters must be placed on separate lines.");
        checkstyle.assertViolation(35,5,"Method parameters must be placed on separate lines.");
    }
}