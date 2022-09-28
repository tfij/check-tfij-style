package pl.tfij.checktfijstyle.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.*;

import static java.util.stream.Collectors.*;
import static pl.tfij.checktfijstyle.checks.DetailASTUtil.getFirstChild;
import static pl.tfij.checktfijstyle.checks.DetailASTUtil.tryGetFirstChild;

public class MethodCallParameterLinesCheck extends AbstractCheck {
    public static final String MSG_PARAMS_LINES = "methodCall.params.lines";

    private final Set<MethodId> ignoreMethods = new HashSet<>();

    public void setIgnoreMethods(String... ignoreMethod) {
        ignoreMethods.clear();
        ignoreMethods.addAll(Arrays.stream(ignoreMethod).map(MethodId::fromString).collect(toSet()));
    }

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
        Optional<MethodId> methodId = getAstMethodId(ast);
        if (methodId.isPresent() && ignoreMethods.contains(methodId.get())) {
            return;
        }

        DetailAST params = getFirstChild(ast, TokenTypes.ELIST);
        Map<Integer, Integer> paramsInLines = DetailASTUtil.streamAll(params.getFirstChild(), TokenTypes.EXPR)
                .map(DetailAST::getLineNo)
                .collect(toMap(it -> it, it -> 1, Integer::sum));

        if (paramsInLines.size() < 2) {
            return;
        }

        Optional<Integer> invalidLine = paramsInLines.values().stream().filter(it -> it > 1).findFirst();
        invalidLine.ifPresent(line -> {
            log(ast.getLineNo(), ast.getColumnNo(), MSG_PARAMS_LINES);
        });
    }

    private Optional<MethodId> getAstMethodId(DetailAST ast) {
        Optional<DetailAST> ident = tryGetFirstChild(ast, TokenTypes.IDENT);
        List<DetailAST> staticMethodWithClass = tryGetFirstChild(ast, TokenTypes.DOT).map(it -> it.getFirstChild()).stream().flatMap(it -> DetailASTUtil.streamAll(it, TokenTypes.IDENT)).collect(toList());
        return ident.map(it -> new MethodId(null, it.getText()))
                .or(() -> {
                    if (staticMethodWithClass.size() == 2) {
                        return Optional.of(new MethodId(staticMethodWithClass.get(0).getText(), staticMethodWithClass.get(1).getText()));
                    } else {
                        return Optional.empty();
                    }
                });
    }

    private static class MethodId {
        private final String className;
        private final String methodName;

        MethodId(String className, String methodName) {
            this.className = className;
            this.methodName = methodName;
        }

        static MethodId fromString(String string) {
            String[] split = string.split("\\.");
            if (split.length == 1) {
                return new MethodId(null, split[0]);
            } else {
                return new MethodId(split[0], split[1]);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MethodId methodId = (MethodId) o;
            return Objects.equals(className, methodId.className) && Objects.equals(methodName, methodId.methodName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(className, methodName);
        }
    }
}
