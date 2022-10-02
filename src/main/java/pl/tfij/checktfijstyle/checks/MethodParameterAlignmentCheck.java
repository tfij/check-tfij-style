package pl.tfij.checktfijstyle.checks;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;
import static pl.tfij.checktfijstyle.checks.DetailASTUtil.getFirstChild;
import static pl.tfij.checktfijstyle.checks.DetailASTUtil.streamRecursively;

@StatelessCheck
public class MethodParameterAlignmentCheck extends AbstractCheck {

    public static final String MSG_PARAM_ALIGNMENT = "method.params.lines-alignment";

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
        Set<Integer> uniqueFirstColNumbersInLines = getParameters(ast)
                .filter(Objects::nonNull)
                .collect(toMap(
                        it -> it.getLineNo(),
                        it -> it.getColumnNo(),
                        (c1, c2) -> Math.min(c1, c2)
                ))
                .values().stream().collect(Collectors.toSet());

        if (uniqueFirstColNumbersInLines.size() > 1) {
            log(ast.getLineNo(), ast.getColumnNo(), MSG_PARAM_ALIGNMENT);
        }
    }

    private Stream<DetailAST> getParameters(DetailAST ast) {
        if (ast.getType() == TokenTypes.RECORD_DEF) {
            final DetailAST parameters = getFirstChild(ast, TokenTypes.RECORD_COMPONENTS);
            return streamRecursively(parameters.getFirstChild());
        } else {
            final DetailAST parameters = getFirstChild(ast, TokenTypes.PARAMETERS);
            return streamRecursively(parameters.getFirstChild());
        }
    }
}
