import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.text.DecimalFormat;

public class SummaryView extends JDialog {

    public SummaryView(JFrame owner, int[] countsPerLevel, double[] volumePerLevel, int totalCells) {
        super(owner, Settings.SUMMARY_TITLE, true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setUndecorated(true);
        double totalVolume = Math.max(0.000001, volumePerLevel[0] + volumePerLevel[1] + volumePerLevel[2]);
        int totalCount = countsPerLevel[0] + countsPerLevel[1] + countsPerLevel[2];
        DecimalFormat volFormat = new DecimalFormat("#,##0.00");

        JPanel root = new JPanel(new BorderLayout(16, 16));
        root.setBorder(new EmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel(Settings.SUMMARY_TITLE);
        title.setFont(new Font(Settings.FONT_NAME, Font.BOLD, Settings.FONT_SIZE_TITLE));

        JPanel header = new JPanel(new GridLayout(1, 2, 12, 0));
        header.setOpaque(false);
        header.add(kpiCard("Total Cells", String.valueOf(totalCount)));
        header.add(kpiCard("Total Volume", volFormat.format(totalVolume) + " CB.M"));

        JPanel table = new JPanel(new GridBagLayout());
        table.setOpaque(false);
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8, 8, 8, 8);
        gc.fill = GridBagConstraints.HORIZONTAL;

        String[] groupNames = { Settings.NO_GAS, Settings.LOW_GAS, Settings.HIGH_GAS };
        Color[] groupColors = { Settings.COLOR_NO_GAS, Settings.COLOR_LOW_GAS, Settings.COLOR_HIGH_GAS };

        addHeaderRow(table, gc, "", "Group", "Cells", "Volume", "% Cells");

        for (int i = 0; i < 3; i++) {
            int count = countsPerLevel[i];
            double vol = volumePerLevel[i];
            double percentCells = (totalCells == 0) ? 0.0 : (count * 100.0 / totalCells);
            JProgressBar bar = createProgressBar((int) Math.round(percentCells), groupColors[i]);
            addDataRow(table, gc, i + 1, createColorDot(groupColors[i]), groupNames[i],
                    count + " cells", volFormat.format(vol) + " CB.M", bar);
        }

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dispose());
        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque(false);
        btnPanel.add(closeBtn);

        root.add(title, BorderLayout.NORTH);
        root.add(header, BorderLayout.BEFORE_FIRST_LINE);
        root.add(table, BorderLayout.CENTER);
        root.add(btnPanel, BorderLayout.SOUTH);

        setContentPane(root);
        pack();
        setMinimumSize(new Dimension(560, 360));
        setLocationRelativeTo(owner);
    }

    private JPanel kpiCard(String labelText, String valueText) {
        JPanel card = new JPanel(new BorderLayout(6, 4));
        card.setOpaque(true);

        card.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0, 30), 1, true), new EmptyBorder(10, 12, 10, 12)));
        JLabel label = new JLabel(labelText);
        label.setFont(new Font(Settings.FONT_NAME, Font.PLAIN, Settings.FONT_SIZE_NORMAL));
        JLabel value = new JLabel(valueText);
        value.setFont(new Font(Settings.FONT_NAME, Font.BOLD, 18));
        card.add(label, BorderLayout.NORTH);
        card.add(value, BorderLayout.CENTER);
        return card;
    }

    private void addHeaderRow(JPanel table, GridBagConstraints gc, String c0, String c1, String c2, String c3, String c4) {
        gc.gridy = 0;
        table.add(headerLabel(c0), gc);
        gc.gridx = 1; table.add(headerLabel(c1), gc);
        gc.gridx = 2; table.add(headerLabel(c2), gc);
        gc.gridx = 3; table.add(headerLabel(c3), gc);
        gc.gridx = 4; table.add(headerLabel(c4), gc);
    }

    private JLabel headerLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font(Settings.FONT_NAME, Font.BOLD, Settings.FONT_SIZE_NORMAL));
        return l;
    }

    private void addDataRow(JPanel table, GridBagConstraints gc, int row, Component dot, String name, String cells, String vol, JProgressBar bar) {
        gc.gridy = row; gc.gridx = 0; table.add(dot, gc);
        gc.gridx = 1; table.add(new JLabel(name), gc);
        gc.gridx = 2; table.add(new JLabel(cells), gc);
        gc.gridx = 3; table.add(new JLabel(vol), gc);
        gc.gridx = 4; table.add(bar, gc);
    }

    private Component createColorDot(Color color) {
        JPanel dot = new JPanel();
        dot.setPreferredSize(new Dimension(14, 14));
        dot.setBackground(color);
        dot.setBorder(new LineBorder(new Color(0, 0, 0, 50), 1, true));
        return dot;
    }

    private JProgressBar createProgressBar(int percent, Color color) {
        JProgressBar pb = new JProgressBar(0, 100);
        pb.setValue(Math.max(0, Math.min(100, percent)));
        pb.setStringPainted(true);
        pb.setForeground(color);

        return pb;
    }
}
