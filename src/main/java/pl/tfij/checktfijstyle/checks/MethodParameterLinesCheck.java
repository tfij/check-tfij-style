package pl.tfij.checktfijstyle.checks;

import com.google.common.collect.Streams;
import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@StatelessCheck
public class MethodParameterLinesCheck extends AbstractCheck {

    private static DetailAST getFirstChild(DetailAST ast, int type) {
        return stream(ast.getFirstChild())
                .filter(it -> it.getType() == type)
                .findFirst()
                .get();
    }

    public static Stream<DetailAST> stream(DetailAST start) {
        return Streams.stream(iterate(start));
    }

    private static Stream<DetailAST> streamAll(DetailAST start, int type) {
        return stream(start)
                .filter(c -> c.getType() == type);
    }

    private static Iterator<DetailAST> iterate(DetailAST start) {
        return new Iterator<DetailAST>() {
            private DetailAST c = start;

            @Override
            public boolean hasNext() {
                return c != null;
            }

            @Override
            public DetailAST next() {
                DetailAST r = c;
                c = r.getNextSibling();
                return r;
            }
        };
    }

    private static boolean allDifferent(List<Integer> lines) {
        return new HashSet<>(lines).size() == lines.size();
    }

    private static boolean allSame(List<Integer> lines) {
        return new HashSet<>(lines).size() == 1;
    }

    public static final String MSG_PARAMS_LINES = "method.params.lines";
    public static final String MSG_PARAMS_SEPARATE_LINES = "method.params.separate-lines";

    private boolean allowSingleLine = true;

    public void setAllowSingleLine(boolean allowSingleLine) {
        this.allowSingleLine = allowSingleLine;
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.METHOD_DEF, TokenTypes.CTOR_DEF};
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
        final DetailAST parameters = getFirstChild(ast, TokenTypes.PARAMETERS);
        List<Integer> lines = streamAll(parameters.getFirstChild(), TokenTypes.PARAMETER_DEF)
                .map(it -> it.getLineNo())
                .collect(Collectors.toList());

        if (lines.size() < 2) {
            return;
        }

        if (allDifferent(lines)) {
            return;
        }

        if (allowSingleLine && allSame(lines)) {
            return;
        }

        if (allowSingleLine)
            log(ast.getLineNo(), ast.getColumnNo(), MSG_PARAMS_LINES);
        else
            log(ast.getLineNo(), ast.getColumnNo(), MSG_PARAMS_SEPARATE_LINES);
    }
}
