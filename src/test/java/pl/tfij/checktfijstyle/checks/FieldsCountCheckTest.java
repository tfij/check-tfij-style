package pl.tfij.checktfijstyle.checks;

import org.junit.jupiter.api.Test;
import pl.tfij.checktfijstyle.fixture.TestCheckstyle;

public class FieldsCountCheckTest {

    private final TestCheckstyle checkstyle = new TestCheckstyle(FieldsCountCheck.class);

    @Test
    void shouldNotReportViolations() {
        checkstyle.check("FieldsCountCheck/SampleTestClass.java");

        checkstyle.assertViolationCount(0);
    }

    @Test
    void shouldReportViolationsWhenDefaultsAreOverride() {
        TestCheckstyle checkstyle = new TestCheckstyle(
                FieldsCountCheck.class,
                c -> {
                    c.addProperty("maxFieldsCount", "4");
                    c.addProperty("maxUninitializedOnDeclarationFieldsCount", "2");
                });

        checkstyle.check("FieldsCountCheck/SampleTestClass.java");

        checkstyle.assertViolationCount(2);
        checkstyle.assertViolation(1, 1, "Class contains `6` fields, max accepted number of fields is 4.");
        checkstyle.assertViolation(1, 1, "Class contains `3` not initialized on declaration fields, max accepted number is 2.");
    }

    @Test
    void shouldReportNoViolationsWhenDefaultsAreOverrideAndCountersAreEquals() {
        TestCheckstyle checkstyle = new TestCheckstyle(
                FieldsCountCheck.class,
                c -> {
                    c.addProperty("maxFieldsCount", "6");
                    c.addProperty("maxUninitializedOnDeclarationFieldsCount", "3");
                });

        checkstyle.check("FieldsCountCheck/SampleTestClass.java");

        checkstyle.assertNoViolations();
    }

    @Test
    void shouldReportViolationsWhenDefaultsAreNotOverrideAndCountersAreExceeded() {
        checkstyle.check("FieldsCountCheck/ViolatedTestClass.java");

        checkstyle.assertViolationCount(2);
        checkstyle.assertViolation(1, 1, "Class contains `10` not initialized on declaration fields, max accepted number is 7.");
        checkstyle.assertViolation(1, 1, "Class contains `13` fields, max accepted number of fields is 12.");
    }

    @Test
    void shouldReportViolationsWhenDefaultsAreNotOverrideAndCountersAreExceededInSubClass() {
        checkstyle.check("FieldsCountCheck/ViolatedTestSubClass.java");

        checkstyle.assertViolationCount(2);
        checkstyle.assertViolation(8, 5, "Class contains `10` not initialized on declaration fields, max accepted number is 7.");
        checkstyle.assertViolation(8, 5, "Class contains `13` fields, max accepted number of fields is 12.");
    }
}
