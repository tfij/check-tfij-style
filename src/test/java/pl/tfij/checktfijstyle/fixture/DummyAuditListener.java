package pl.tfij.checktfijstyle.fixture;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;

import java.util.List;

public class DummyAuditListener implements AuditListener {
    private final List<AuditEvent> target;

    public DummyAuditListener(List<AuditEvent> target) {
        this.target = target;
    }

    @Override
    public void auditStarted(AuditEvent event) {

    }

    @Override
    public void auditFinished(AuditEvent event) {

    }

    @Override
    public void fileStarted(AuditEvent event) {

    }

    @Override
    public void fileFinished(AuditEvent event) {

    }

    @Override
    public void addError(AuditEvent event) {
        target.add(event);
    }

    @Override
    public void addException(AuditEvent event, Throwable throwable) {

    }
}
