package pl.tfij.checktfijstyle;

import org.junit.jupiter.api.Test;
import pl.tfij.checktfijstyle.checks.MethodParameterLinesCheck;
import pl.tfij.checktfijstyle.fixture.TestCheckstyle;

class MethodParameterLinesCheckTest {

    private TestCheckstyle checkstyle = new TestCheckstyle(MethodParameterLinesCheck.class);

    @Test
    void noMethodIssues() {
        checkstyle.check("lines/NoMethodIssues.java");

        checkstyle.assertNoViolations();
    }

    @Test
    void noConstructorIssues() {
        checkstyle.check("lines/NoConstructorIssues.java");

        checkstyle.assertNoViolations();
    }

    @Test
    void MethodParamsNotAllOneLineOrSeparateLines() {
        checkstyle.check("lines/MethodParamsNotAllOneLineOrSeparateLines.java");

        checkstyle.assertViolationCount(1);
        checkstyle.assertViolation(4, 5, "Method parameters must be placed on a single line or on separate lines.");
    }

    @Test
    void ConstructorParamsNotAllOneLineOrSeparateLines() {
        checkstyle.check("lines/ConstructorParamsNotAllOneLineOrSeparateLines.java");

        checkstyle.assertViolationCount(1);
        checkstyle.assertViolation(4, 5, "Method parameters must be placed on a single line or on separate lines.");
    }

    @Test
    void RecordArgumentsNotAllOneLineOrSeparateLines() {
        checkstyle.check("lines/NotValidRecordClass.java");

        checkstyle.assertViolationCount(1);
        checkstyle.assertViolation(3, 1, "Method parameters must be placed on a single line or on separate lines.");
    }

    @Test
    void noRecordIssue() {
        checkstyle.check("lines/ValidRecordClass.java");

        checkstyle.assertNoViolations();
    }

    @Test
    void should_not_allow_single_line_when_configured() {
        var checkstyle = new TestCheckstyle(MethodParameterLinesCheck.class,
                c -> c.addAttribute("allowSingleLine", "false"));

        checkstyle.check("lines/NoMethodIssues.java");

        checkstyle.assertViolationCount(2);
        checkstyle.assertViolation(31,5,"Method parameters must be placed on separate lines.");
        checkstyle.assertViolation(35,5,"Method parameters must be placed on separate lines.");
    }
}