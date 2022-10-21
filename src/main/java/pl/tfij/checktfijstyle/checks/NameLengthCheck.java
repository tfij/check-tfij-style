package pl.tfij.checktfijstyle.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pl.tfij.checktfijstyle.checks.DetailASTUtil.getFirstChild;

public class NameLengthCheck extends AbstractCheck {
    private static final Map<Integer, String> TOKEN_TYPE_TO_MSG = Map.of(
            TokenTypes.CLASS_DEF, "nameLength.classNameToLong",
            TokenTypes.METHOD_DEF, "nameLength.methodNameToLong",
            TokenTypes.RECORD_DEF, "nameLength.recordNameToLong",
            TokenTypes.PARAMETER_DEF, "nameLength.parameterNameToLong",
            TokenTypes.VARIABLE_DEF, "nameLength.variableNameToLong",
            TokenTypes.INTERFACE_DEF, "nameLength.interfaceNameToLong",
            TokenTypes.PACKAGE_DEF, "nameLength.packageNameToLong",
            TokenTypes.ENUM_DEF, "nameLength.enumClassNameToLong",
            TokenTypes.ENUM_CONSTANT_DEF, "nameLength.enumConstantNameToLong"
    );
    private static final Map<Integer, Integer> TOKEN_TYPE_TO_DEFAULT_MAX_LENGTH = Map.of(
            TokenTypes.CLASS_DEF, 50,
            TokenTypes.METHOD_DEF, 50,
            TokenTypes.RECORD_DEF, 50,
            TokenTypes.PARAMETER_DEF, 30,
            TokenTypes.VARIABLE_DEF, 30,
            TokenTypes.INTERFACE_DEF, 50,
            TokenTypes.PACKAGE_DEF, 20,
            TokenTypes.ENUM_DEF, 50,
            TokenTypes.ENUM_CONSTANT_DEF, 50
    );
    private final Map<Integer, Integer> customMaxLength = new HashMap<>();

    public void setMaxClassNameLength(int maxLength) {
        customMaxLength.put(TokenTypes.CLASS_DEF, maxLength);
    }

    public void setMaxMethodNameLength(int maxLength) {
        customMaxLength.put(TokenTypes.METHOD_DEF, maxLength);
    }

    public void setMaxRecordNameLength(int maxLength) {
        customMaxLength.put(TokenTypes.RECORD_DEF, maxLength);
    }

    public void setMaxParameterNameLength(int maxLength) {
        customMaxLength.put(TokenTypes.PARAMETER_DEF, maxLength);
    }

    public void setMaxVariableNameLength(int maxLength) {
        customMaxLength.put(TokenTypes.VARIABLE_DEF, maxLength);
    }

    public void setMaxInterfaceNameLength(int maxLength) {
        customMaxLength.put(TokenTypes.INTERFACE_DEF, maxLength);
    }

    public void setMaxPackageNameLength(int maxLength) {
        customMaxLength.put(TokenTypes.PACKAGE_DEF, maxLength);
    }

    public void setMaxEnumClassNameLength(int maxLength) {
        customMaxLength.put(TokenTypes.ENUM_DEF, maxLength);
    }

    public void setMaxEnumConstNameLength(int maxLength) {
        customMaxLength.put(TokenTypes.ENUM_CONSTANT_DEF, maxLength);
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[]{
                TokenTypes.CLASS_DEF,
                TokenTypes.METHOD_DEF,
                TokenTypes.RECORD_DEF,
                TokenTypes.PARAMETER_DEF,
                TokenTypes.VARIABLE_DEF,
                TokenTypes.INTERFACE_DEF,
                TokenTypes.PACKAGE_DEF,
                TokenTypes.ENUM_DEF,
                TokenTypes.ENUM_CONSTANT_DEF,
        };
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
        if (ast.getType() == TokenTypes.METHOD_DEF) {
            methodNameVerification(ast);
        } else if (ast.getType() == TokenTypes.PACKAGE_DEF) {
            packageNameVerification(ast);
        } else {
            regularIdentifierVerification(ast);
        }
    }

    private void methodNameVerification(DetailAST ast) {
        if (isMethodAnnotatedByOverride(ast)) {
            return;
        }
        regularIdentifierVerification(ast);
    }

    private void packageNameVerification(DetailAST ast) {
        Optional<String> toLongPackageSegmentName = DetailASTUtil.streamRecursively(ast)
                .filter(it -> it.getType() == TokenTypes.IDENT)
                .map(it -> it.getText())
                .filter(it -> it.length() > maxLengthFor(ast))
                .findFirst();
        toLongPackageSegmentName
                .ifPresent(it -> log(ast.getLineNo(), ast.getColumnNo(), TOKEN_TYPE_TO_MSG.get(ast.getType()), it, it.length(), maxLengthFor(ast)));
    }

    private void regularIdentifierVerification(DetailAST ast) {
        DetailAST firstChild = getFirstChild(ast, TokenTypes.IDENT);
        String intentName = firstChild.getText();
        if (intentName.length() > maxLengthFor(ast)) {
            log(ast.getLineNo(), ast.getColumnNo(), TOKEN_TYPE_TO_MSG.get(ast.getType()), intentName, intentName.length(), maxLengthFor(ast));
        }
    }

    private Integer maxLengthFor(DetailAST ast) {
        return customMaxLength.getOrDefault(ast.getType(), TOKEN_TYPE_TO_DEFAULT_MAX_LENGTH.get(ast.getType()));
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
