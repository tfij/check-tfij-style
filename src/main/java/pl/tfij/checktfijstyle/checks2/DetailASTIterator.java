package pl.tfij.checktfijstyle.checks2;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

import java.util.Iterator;

class DetailASTIterator implements Iterator<DetailAST> {
    private DetailAST c;

    DetailASTIterator(DetailAST start) {
        c = start;
    }

    @Override
    public boolean hasNext() {
        return c != null;
    }

    @Override
    public DetailAST next() {
        DetailAST r = c;
        c = r.getNextSibling();
        return r;
    }
}
