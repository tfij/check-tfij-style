package pl.tfij.checktfijstyle.checks;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static pl.tfij.checktfijstyle.checks.DetailASTUtil.getFirstChild;
import static pl.tfij.checktfijstyle.checks.DetailASTUtil.streamAll;

@StatelessCheck
public class MethodParameterLinesCheck extends AbstractCheck {

    public static final String MSG_PARAMS_LINES = "method.params.lines";
    public static final String MSG_PARAMS_SEPARATE_LINES = "method.params.separate-lines";

    private boolean allowSingleLine = true;

    public void setAllowSingleLine(boolean allowSingleLine) {
        this.allowSingleLine = allowSingleLine;
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.METHOD_DEF, TokenTypes.CTOR_DEF, TokenTypes.RECORD_DEF};
    }

    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        List<Integer> lines = argumentLines(ast);
        if (lines.size() < 2) {
            return;
        }
        if (allDifferent(lines)) {
            return;
        }
        if (allowSingleLine && allSame(lines)) {
            return;
        }
        logViolationFor(ast);
    }

    private void logViolationFor(DetailAST ast) {
        if (allowSingleLine) {
            log(ast.getLineNo(), ast.getColumnNo(), MSG_PARAMS_LINES);
        } else {
            log(ast.getLineNo(), ast.getColumnNo(), MSG_PARAMS_SEPARATE_LINES);
        }
    }

    private List<Integer> argumentLines(DetailAST ast) {
        if (ast.getType() == TokenTypes.RECORD_DEF) {
            final DetailAST parameters = getFirstChild(ast, TokenTypes.RECORD_COMPONENTS);
            return streamAll(parameters.getFirstChild(), TokenTypes.RECORD_COMPONENT_DEF)
                    .map(it -> it.getLineNo())
                    .collect(Collectors.toList());
        } else {
            final DetailAST parameters = getFirstChild(ast, TokenTypes.PARAMETERS);
            return streamAll(parameters.getFirstChild(), TokenTypes.PARAMETER_DEF)
                    .map(it -> it.getLineNo())
                    .collect(Collectors.toList());
        }
    }

    private static boolean allDifferent(List<Integer> lines) {
        return new HashSet<>(lines).size() == lines.size();
    }

    private static boolean allSame(List<Integer> lines) {
        return new HashSet<>(lines).size() == 1;
    }

}
