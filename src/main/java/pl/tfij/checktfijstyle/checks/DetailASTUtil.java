package pl.tfij.checktfijstyle.checks;

import com.google.common.collect.Streams;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

import java.util.Optional;
import java.util.stream.Stream;

class DetailASTUtil {
    static Stream<DetailAST> stream(DetailAST start) {
        return Streams.stream(new DetailASTIterator(start));
    }

    static DetailAST getFirstChild(DetailAST ast, int type) {
        return tryGetFirstChild(ast, type).get();
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
