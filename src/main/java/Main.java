import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DictionarManager dictionarManager = new DictionarManager();
                InterfataGrafica interfataGrafica = new InterfataGrafica(dictionarManager);
            }
        });
    }
}
