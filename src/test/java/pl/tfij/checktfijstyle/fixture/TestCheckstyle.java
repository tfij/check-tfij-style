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
        return new File("src/test/java/pl/tfij/checktfijstyle/sample/" + name);
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

    public void assertViolation(int line, int col, String message) {
        var found = errors.stream()
                .filter(e -> e.getLine() == line)
                .filter(e -> e.getColumn() == col)
                .filter(e -> e.getMessage().equals(message))
                .findFirst();
        Assertions.assertTrue(
                found.isPresent(),
                String.format(
                        "Expect `%s` message for %s line and %s col but found %s",
                        message,
                        line,
                        col,
                        errors.stream().filter(e -> e.getLine() == line).filter(e -> e.getColumn() == col).map(AuditEvent::getMessage).collect(toList())));
    }

    public void assertNoViolations() {
        Assertions.assertEquals(List.of(), errors);
    }

}
