import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InterfataGrafica extends JFrame {

    private DictionarManager dictionarManager;
    private JTextField cuvantTextField;
    private JButton adaugaButon;
    private JButton stergeButon;
    private JButton cautaButon;
    private JTextArea afisareTextArea;

    public InterfataGrafica(DictionarManager dictionarManager) {
        this.dictionarManager = dictionarManager;

        cuvantTextField = new JTextField(20);
        adaugaButon = new JButton("Adauga cuvant");
        stergeButon = new JButton("Sterge cuvant");
        cautaButon = new JButton("Cauta pattern");
        afisareTextArea = new JTextArea(10, 20);
        afisareTextArea.setEditable(false);
        JButton incarcaDictionarButon = new JButton("Incarca Dictionar");

        incarcaDictionarButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incarcaDictionar();
            }
        });

        adaugaButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adaugaCuvant();
            }
        });

        stergeButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stergeCuvant();
            }
        });

        cautaButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cautaCuvant();
            }
        });

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        JPanel panelInput = new JPanel(new FlowLayout());
        panelInput.add(cuvantTextField);
        panelInput.add(adaugaButon);
        panelInput.add(stergeButon);
        panelInput.add(cautaButon);
        container.add(panelInput, BorderLayout.NORTH);
        container.add(new JScrollPane(afisareTextArea), BorderLayout.CENTER);
        container.add(incarcaDictionarButon, BorderLayout.SOUTH);

        setTitle("Interfata Dictionar");;
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void incarcaDictionar() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            String numeFisier = chooser.getSelectedFile().getAbsolutePath();
            dictionarManager.incarcaDictionar(numeFisier);
            actualizeazaAfisare();
            JOptionPane.showMessageDialog(this, "Dictionarul a fost incarcat din fisier!");
        }
    }

    private void adaugaCuvant() {
        String cuvant = cuvantTextField.getText();
        dictionarManager.adaugaCuvant(cuvant);
        cuvantTextField.setText("");
        actualizeazaAfisare();
        JOptionPane.showMessageDialog(this, "Cuvantul a fost adaugat in dictionar!");
    }

    private void stergeCuvant() {
        String cuvant = cuvantTextField.getText();
        if (!dictionarManager.existaCuvant(cuvant)) {
            JOptionPane.showMessageDialog(this, "Cuvantul " + cuvant + " nu a fost gasit in dictionar!");
        } else {
            dictionarManager.stergeCuvant(cuvant);
            cuvantTextField.setText("");
            actualizeazaAfisare();
            JOptionPane.showMessageDialog(this, "Cuvantul " + cuvant + " a fost sters dn dictionar!");
        }
    }

    private void cautaCuvant() {
        String cuvant = cuvantTextField.getText();
        List<String> rezultate = dictionarManager.cautareCuvant(cuvant);

        if (!rezultate.isEmpty()) {
            CustomDialog customDialog = new CustomDialog(this, rezultate);
            customDialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Cuvantul nu a fost gasit in dictionar.", "Rezultate cautare", JOptionPane.INFORMATION_MESSAGE);
        }

        actualizeazaAfisare();
    }



    private void actualizeazaAfisare() {
        List<String> cuvinte = dictionarManager.getCuvinte();
        StringBuilder stringBuilder = new StringBuilder();
        for (String cuvant : cuvinte) {
            stringBuilder.append(cuvant).append("\n");
        }
        afisareTextArea.setText(stringBuilder.toString());

        afisareTextArea.setCaretPosition(0);
    }

}
