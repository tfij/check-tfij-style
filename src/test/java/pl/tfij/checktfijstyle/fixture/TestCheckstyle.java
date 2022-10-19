package pl.tfij.checktfijstyle.fixture;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static java.util.stream.Collectors.toList;

public class TestCheckstyle {

    private static Checker createChecker(Class<? extends AbstractCheck> check, List<AuditEvent> errors, Consumer<? super DefaultConfiguration> cfg) {
        try {
            var checkConfig = new DefaultConfiguration(check.getName());
            cfg.accept(checkConfig);

            var checker = new Checker();
            checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
            checker.configure(config(checkConfig));
            checker.addListener(new DummyAuditListener(errors));
            return checker;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static File checkedFile(String name) {
        return new File("src/test/resources/sample/" + name);
    }

    private static DefaultConfiguration config(Configuration checkConfig) {
        var tw = new DefaultConfiguration(TreeWalker.class.getName());
        tw.addChild(checkConfig);

        var cfg = new DefaultConfiguration("configuration");
        // make sure that the tests always run with this charset
        cfg.addAttribute("charset", StandardCharsets.UTF_8.name());
        cfg.addChild(tw);

        return cfg;
    }

    private final List<AuditEvent> errors;
    private final Checker checker;

    public TestCheckstyle(Class<? extends AbstractCheck> check) {
        this(check, __ -> {});
    }

    public TestCheckstyle(Class<? extends AbstractCheck> check, Consumer<? super DefaultConfiguration> cfg) {
        errors = new ArrayList<>();
        checker = createChecker(check, errors, cfg);
    }

    public void check(String checkedFile) {
        try {
            checker.process(List.of(checkedFile(checkedFile)));
        } catch (CheckstyleException e) {
            throw new RuntimeException(e);
        }
    }

    public void assertViolationCount(int expectedNumberOfViolations) {
        Assertions.assertEquals(
                expectedNumberOfViolations,
                errors.size(),
                String.format(
                        "Expect %s violations but found %s: %s",
                        expectedNumberOfViolations,
                        errors.size(),
                        errorsInFile()
                ));
    }

    public void assertViolation(int line, int col, String expectedMessage) {
        var found = errors.stream()
                .filter(e -> e.getLine() == line)
                .filter(e -> e.getColumn() == col)
                .filter(e -> e.getMessage().equals(expectedMessage))
                .findFirst();
        Assertions.assertTrue(
                found.isPresent(),
                assertionErrorMessage(line, col, expectedMessage));
    }

    private String assertionErrorMessage(int line, int col, String message) {
        List<String> errorsInLine = errors.stream().filter(e -> e.getLine() == line).filter(e -> e.getColumn() == col).map(AuditEvent::getMessage).collect(toList());
        if (!errorsInLine.isEmpty()) {
            return String.format(
                    "Expect `%s` message for %s line and %s col but found %s",
                    message,
                    line,
                    col,
                    errorsInLine);
        } else {
            return String.format(
                    "Expect `%s` message for %s line and %s col but no violation found. Violations for file: %s",
                    message,
                    line,
                    col,
                    errorsInFile());
        }
    }

    private List<String> errorsInFile() {
        List<String> errorsInFile = errors.stream()
                .map(auditEvent -> String.format("%s:%s %s", auditEvent.getLine(), auditEvent.getColumn(), auditEvent.getMessage()))
                .collect(toList());
        return errorsInFile;
    }

    public void assertNoViolations() {
        Assertions.assertEquals(List.of(), errorsInFile());
    }

}
