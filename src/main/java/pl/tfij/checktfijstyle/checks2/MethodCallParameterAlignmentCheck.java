package pl.tfij.checktfijstyle.checks2;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;
import static pl.tfij.checktfijstyle.checks2.DetailASTUtil.tryGetFirstChild;

public class MethodCallParameterAlignmentCheck extends AbstractCheck {
    public static final String MSG_PARAMS_LINES = "methodCall.params.lines-alignment";

    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.METHOD_CALL, TokenTypes.CTOR_CALL, TokenTypes.SUPER_CTOR_CALL, TokenTypes.LITERAL_NEW};
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
        Optional<DetailAST> params = tryGetFirstChild(ast, TokenTypes.ELIST);
        params.ifPresent(p -> {
            Map<Integer, Integer> lineToFirstArg = DetailASTUtil.streamAll(p.getFirstChild(), TokenTypes.EXPR)
                    .flatMap(it -> findAstWithMinColumnNo(it).stream())
                    .collect(toMap(DetailAST::getLineNo, DetailAST::getColumnNo, Integer::min));
            HashSet<Integer> uniqueColumnNumbers = new HashSet<>(lineToFirstArg.values());
            if (uniqueColumnNumbers.size() > 1) {
                log(ast.getLineNo(), ast.getColumnNo(), MSG_PARAMS_LINES);
            }
        });
    }

    private Optional<DetailAST> findAstWithMinColumnNo(DetailAST ast) {
        return DetailASTUtil.streamRecursively(ast.getFirstChild()).min(Comparator.comparing(DetailAST::getColumnNo));
    }
}
