package pl.tfij.checktfijstyle.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pl.tfij.checktfijstyle.checks.DetailASTUtil.getFirstChild;

public class NameLengthCheck extends AbstractCheck {
    private static final Map<Integer, String> TOKEN_TYPE_TO_MSG = Map.of(
            TokenTypes.CLASS_DEF, "nameLength.classNameToLong",
            TokenTypes.METHOD_DEF, "nameLength.methodNameToLong",
            TokenTypes.CTOR_DEF, "nameLength.constructorNameToLong",
            TokenTypes.RECORD_DEF, "nameLength.recordNameToLong",
            TokenTypes.PARAMETER_DEF, "nameLength.parameterNameToLong",
            TokenTypes.VARIABLE_DEF, "nameLength.variableNameToLong"
    );
    private static final int DEFAULT_MAX_SIZE = 50;

    @Override
    public int[] getDefaultTokens() {
        //TODO INTERFACE_DEF
        //TODO PACKAGE_DEF
        //TODO ENUM_DEF
        //TODO different defaults for types
        //TODO setters to override default max length
        return new int[]{
                TokenTypes.CLASS_DEF,
                TokenTypes.METHOD_DEF,
                TokenTypes.CTOR_DEF,
                TokenTypes.RECORD_DEF,
                TokenTypes.PARAMETER_DEF,
                TokenTypes.VARIABLE_DEF};
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
        if (ast.getType() == TokenTypes.METHOD_DEF && isMethodAnnotatedByOverride(ast)) {
            return;
        }
        DetailAST firstChild = getFirstChild(ast, TokenTypes.IDENT);
        String intentName = firstChild.getText();
        if (intentName.length() > DEFAULT_MAX_SIZE) {
            log(ast.getLineNo(), ast.getColumnNo(), TOKEN_TYPE_TO_MSG.get(ast.getType()), intentName, intentName.length(), DEFAULT_MAX_SIZE);
        }
    }

    private boolean isMethodAnnotatedByOverride(DetailAST ast) {
        Stream<DetailAST> methodModifiers = DetailASTUtil.tryGetFirstChild(ast, TokenTypes.MODIFIERS)
                .map(it -> DetailASTUtil.stream(it.getFirstChild()))
                .orElse(Stream.empty());
        Stream<DetailAST> methodAnnotations = methodModifiers.filter(it -> it.getType() == TokenTypes.ANNOTATION);
        return methodAnnotations.anyMatch(this::isOverrideAnnotation);
    }

    private boolean isOverrideAnnotation(DetailAST annotationAst) {
        List<DetailAST> annotationAstElements = DetailASTUtil.stream(annotationAst.getFirstChild()).collect(Collectors.toList());
        if (annotationAstElements.size() >= 2) {
            return "@".equals(annotationAstElements.get(0).getText()) && "Override".equals(annotationAstElements.get(1).getText());
        } else {
            return false;
        }
    }
}
