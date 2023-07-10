package pl.tfij.checktfijstyle.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static pl.tfij.checktfijstyle.checks.DetailASTUtil.getFirstChild;
import static pl.tfij.checktfijstyle.checks.DetailASTUtil.streamRecursively;

public class MethodEmptyLinesCheck extends AbstractCheck {
    public static final String MSG_EMPTY_LINE = "methodEmptyLines.emptyLinesNotAllowed";

    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.METHOD_DEF, TokenTypes.CTOR_DEF, TokenTypes.COMPACT_CTOR_DEF};
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
        Set<Integer> notEmptyLines = getNotEmptyLines(ast);
        int firstLine = findFirstLine(notEmptyLines);
        int lastLine = findLastLine(notEmptyLines);
        List<Integer> emptyLines = findEmptyLines(notEmptyLines, firstLine, lastLine);
        emptyLines.forEach(emptyLine -> log(emptyLine, 0, MSG_EMPTY_LINE));
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    private static Set<Integer> getNotEmptyLines(DetailAST ast) {
        return streamRecursively(ast)
                .flatMap(it -> getLineNumbers(it).stream())
                .collect(Collectors.toSet());
    }

    private static List<Integer> getLineNumbers(DetailAST ast) {
        if (ast.getType() == TokenTypes.BLOCK_COMMENT_BEGIN) {
            int commentFirstLine = ast.getLineNo();
            int commentLastLine = getFirstChild(ast, TokenTypes.BLOCK_COMMENT_END).getLineNo();
            return intRange(commentFirstLine, commentLastLine);
        } else if (ast.getType() == TokenTypes.TEXT_BLOCK_LITERAL_BEGIN) {
            int stringFirstLine = ast.getLineNo();
            int stringLastLine = getFirstChild(ast, TokenTypes.TEXT_BLOCK_LITERAL_END).getLineNo();
            return intRange(stringFirstLine, stringLastLine);
        } else {
            return List.of(ast.getLineNo());
        }
    }

    private static int findFirstLine(Set<Integer> notEmptyLines) {
        return notEmptyLines.stream().mapToInt(it -> it).min().orElseThrow(() -> new IllegalStateException(""));
    }

    private static int findLastLine(Set<Integer> notEmptyLines) {
        return notEmptyLines.stream().mapToInt(it -> it).max().orElseThrow(() -> new IllegalStateException(""));
    }

    private static List<Integer> findEmptyLines(Set<Integer> notEmptyLines, int firstLine, int lastLine) {
        return IntStream.range(firstLine, lastLine)
                .filter(lineNumber -> !notEmptyLines.contains(lineNumber))
                .sorted()
                .boxed()
                .collect(Collectors.toList());
    }

    private static List<Integer> intRange(int stringFirstLine, int stringLastLine) {
        return IntStream.range(stringFirstLine, stringLastLine)
                .boxed()
                .collect(Collectors.toList());
    }
}
