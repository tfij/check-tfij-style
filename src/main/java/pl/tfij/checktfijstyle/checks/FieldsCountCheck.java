package pl.tfij.checktfijstyle.checks;

import com.puppycrawl.tools.checkstyle.AstTreeStringPrinter;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;
import java.util.stream.Collectors;

import static pl.tfij.checktfijstyle.checks.DetailASTUtil.tryGetFirstChild;

public class FieldsCountCheck extends AbstractCheck {
    public static final String MSG_FIELD_COUNT = "class.fields.count";
    public static final String MSG_UNINITIALIZED_ON_DECLARATION_FIELD_COUNT = "class.fields.uninitializedOnDeclarationCount";
    private static final int DEFAULT_MAX_FIELD_COUNT = 12;
    private static final int DEFAULT_MAX_UNINITIALIZED_ON_DECLARATION_FIELD_COUNT = 7;
    private int maxFieldsCount = DEFAULT_MAX_FIELD_COUNT;
    private int maxUninitializedOnDeclarationFieldsCount = DEFAULT_MAX_UNINITIALIZED_ON_DECLARATION_FIELD_COUNT;

    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.CLASS_DEF};
    }

    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    public void setMaxFieldsCount(int maxFieldsCount) {
        this.maxFieldsCount = maxFieldsCount;
    }

    public void setMaxUninitializedOnDeclarationFieldsCount(int maxUninitializedOnDeclarationFieldsCount) {
        this.maxUninitializedOnDeclarationFieldsCount = maxUninitializedOnDeclarationFieldsCount;
    }

    @Override
    public void visitToken(DetailAST ast) {
        List<DetailAST> classFields = tryGetFirstChild(ast, TokenTypes.OBJBLOCK).stream()
                .flatMap(it -> tryGetFirstChild(it, TokenTypes.VARIABLE_DEF).stream())
                .flatMap(DetailASTUtil::stream)
                .filter(it -> it.getType() == TokenTypes.VARIABLE_DEF)
                .collect(Collectors.toList());
        int fieldsCount = classFields.size();
        long uninitializedOnDeclarationFieldsCount = classFields.stream().filter(it -> !isInitialized(it)).count();
        if (fieldsCount > maxFieldsCount) {
            log(ast.getLineNo(), ast.getColumnNo(), MSG_FIELD_COUNT, fieldsCount, maxFieldsCount);
        }
        if (uninitializedOnDeclarationFieldsCount > maxUninitializedOnDeclarationFieldsCount) {
            log(
                    ast.getLineNo(),
                    ast.getColumnNo(),
                    MSG_UNINITIALIZED_ON_DECLARATION_FIELD_COUNT,
                    uninitializedOnDeclarationFieldsCount,
                    maxUninitializedOnDeclarationFieldsCount);
        }
    }

    private boolean isInitialized(DetailAST ast) {
        if (ast.getType() != TokenTypes.VARIABLE_DEF) {
            throw new IllegalArgumentException(
                    String.format("Required VARIABLE_DEF type but given `%s`. AST: %s", ast.getType(), AstTreeStringPrinter.printBranch(ast)));
        }
        return DetailASTUtil.streamRecursively(ast).anyMatch(it -> it.getType() == TokenTypes.ASSIGN);
    }
}
