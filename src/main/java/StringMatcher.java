import java.util.ArrayList;
import java.util.List;

public class StringMatcher {

    private int[] computePrefixFunction(String pattern) {
        int m = pattern.length();
        int pi[] = new int[m];
        int len = 0;
        int i = 1;

        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                pi[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = pi[len -1];
                } else {
                    pi[i] = 0;
                    i++;
                }
            }
        }
        return pi;
    }

    public List<String> search(String pattern, RedBlackTree redBlackTree) {
        List<String> matches = new ArrayList<>();
        int m = pattern.length();
        int[] pi = computePrefixFunction(pattern);

        cautareRecursivaKMP(redBlackTree.getRoot(), pattern, pi, 0, matches);

        return matches;
    }

    private void cautareRecursivaKMP(
            RedBlackTree.Node x,
            String pattern,
            int[] pi,
            int j,
            List<String> matches
    ) {
        if (x == null) {
            return;
        }

        String cuvant = x.getKey();
        int m = pattern.length();
        int n = cuvant.length();
        int i = 0;

        while (i < n) {
            if (pattern.charAt(j) == cuvant.charAt(i)) {
                j++;
                i++;
            }
            if (j == m) {
                matches.add(cuvant);
                j = pi[j-1];
            } else if (i < n && pattern.charAt(j) != cuvant.charAt(i)) {
                if (j != 0) {
                    j = pi[j - 1];
                } else {
                    i++;
                }
            }
        }

        cautareRecursivaKMP(x.getLeft(), pattern, pi, j, matches);
        cautareRecursivaKMP(x.getRight(), pattern, pi, j, matches);
    }
}
