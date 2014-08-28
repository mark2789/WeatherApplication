import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class ProgressBar extends JPanel {

    private static JProgressBar progressBar;

    public ProgressBar() {
        setLayout(new BorderLayout());
        progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setMaximum(12);
        progressBar.setStringPainted(true);
        progressBar.setBorder(null);
        add(progressBar, BorderLayout.SOUTH);
        setVisible(true);
    }

    void updateProgress(final int newValue) {
        progressBar.setValue(newValue);
    }

    public void setValue(final int j) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                updateProgress(j);
            }
        });
    }
}