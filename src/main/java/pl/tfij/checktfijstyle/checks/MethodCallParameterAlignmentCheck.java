package pl.tfij.checktfijstyle.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.HashSet;
import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static pl.tfij.checktfijstyle.checks.DetailASTUtil.getFirstChild;

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
        DetailAST params = getFirstChild(ast, TokenTypes.ELIST);
        Map<Integer, Integer> lineToFirstArg = DetailASTUtil.streamAll(params.getFirstChild(), TokenTypes.EXPR)
                .collect(toMap(DetailAST::getLineNo, DetailAST::getColumnNo, Integer::min));
        HashSet<Integer> uniqueColumnNumbers = new HashSet<>(lineToFirstArg.values());
        if (uniqueColumnNumbers.size() > 1) {
            log(ast.getLineNo(), ast.getColumnNo(), MSG_PARAMS_LINES);
        }
    }
}
