import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CustomDialog extends JDialog {

    public CustomDialog(JFrame parent, List<String> results) {
        super(parent, "Rezultate cautare", true);

        JTextArea textArea = new JTextArea(20, 40);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        for (String result : results) {
            textArea.append(result + "\n");
        }

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        textArea.setCaretPosition(0);

        setContentPane(panel);
        pack();
        setLocationRelativeTo(parent);
    }
}
