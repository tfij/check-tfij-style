package pl.tfij.checktfijstyle.checks2;

import com.google.common.collect.Streams;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

final class DetailASTUtil {
    private DetailASTUtil() {
        throw new IllegalStateException("Can't create instance of util class");
    }

    static Stream<DetailAST> stream(DetailAST start) {
        return Streams.stream(new DetailASTIterator(start));
    }

    static Stream<DetailAST> streamRecursively(DetailAST start) {
        if (start.getFirstChild() == null) {
            return stream(start);
        } else {
            return Stream.concat(
                    stream(start),
                    stream(start).filter(it -> it.getFirstChild() != null).map(it -> it.getFirstChild()).flatMap(it -> streamRecursively(it))
            );
        }
    }

    static DetailAST getFirstChild(DetailAST ast, int type) {
        return tryGetFirstChild(ast, type)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format(
                                "Can't find element of type %s ant %s[%s:%s] AST",
                                type,
                                ast.getText(),
                                ast.getLineNo(),
                                ast.getColumnNo())));
    }

    static Optional<DetailAST> tryGetFirstChild(DetailAST ast, int type) {
        return stream(ast.getFirstChild())
                .filter(it -> it.getType() == type)
                .findFirst();
    }

    static Stream<DetailAST> streamAll(DetailAST start, int type) {
        return stream(start)
                .filter(c -> c.getType() == type);
    }
}
