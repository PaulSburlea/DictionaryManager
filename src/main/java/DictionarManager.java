import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class DictionarManager {

    private RedBlackTree redBlackTree;
    private StringMatcher stringMatcher;

    public DictionarManager() {
        this.redBlackTree = new RedBlackTree();
        this.stringMatcher = new StringMatcher();
    }

    public void adaugaCuvant(String cuvant) {
        redBlackTree.insert(cuvant);
    }

    public void stergeCuvant(String cuvant) {
        redBlackTree.delete(cuvant);
    }

    public List<String> cautareCuvant(String pattern) {
        return stringMatcher.search(pattern, redBlackTree);
    }

    public void incarcaDictionar(String numFisier) {
        try (BufferedReader br = new BufferedReader(new FileReader(numFisier))) {
            String linie;
            while ((linie = br.readLine()) != null) {
                adaugaCuvant(linie.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean existaCuvant(String cuvant) {
        RedBlackTree.Node radacina = redBlackTree.getRoot();
        return cautaCuvant(radacina, cuvant);
    }

    private boolean cautaCuvant(RedBlackTree.Node nod, String cuvant) {
        if (nod == null) {
            return false;
        }

        int cmp = cuvant.compareTo(nod.getKey());

        if (cmp == 0) {
            return true;
        } else if (cmp < 0) {
            return cautaCuvant(nod.getLeft(), cuvant);
        } else {
            return cautaCuvant(nod.getRight(), cuvant);
        }
    }

    public List<String> getCuvinte() {
        return redBlackTree.getCuvinte();
    }

    public String toString() {
        return redBlackTree.toString();
    }
}
