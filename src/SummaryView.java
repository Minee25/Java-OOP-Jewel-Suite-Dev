import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.text.DecimalFormat;

public class SummaryView extends JDialog {
    
 
    private int[] cellCounts;
    private double[] gasVolumes;
    private int totalCellCount;
    private boolean isDarkTheme;
    

    private DecimalFormat numberFormatter;

    public SummaryView(JFrame owner, int[] countsPerLevel, double[] volumePerLevel, int totalCells) {
        super(owner, Settings.SUMMARY_TITLE, true);
        
   
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setUndecorated(true);
        
  
        this.cellCounts = countsPerLevel;
        this.gasVolumes = volumePerLevel;
        this.totalCellCount = totalCells;
        
        
        this.isDarkTheme = isCurrentThemeDark();
        

        this.numberFormatter = new DecimalFormat("#,##0.00");

       
        createSummaryWindow();
    }
    
    private boolean isCurrentThemeDark() {
        return UIManager.getLookAndFeel().getName().contains("Monokai");
    }
    

    private void createSummaryWindow() {
        
        double totalVolume = calculateTotalVolume();
        int totalActiveCells = calculateTotalActiveCells();
        

        JPanel mainPanel = new JPanel(new BorderLayout(16, 16));
        mainPanel.setBorder(new EmptyBorder(16, 16, 16, 16));
        
  
        JLabel titleLabel = createTitleLabel();

        JPanel summaryCards = createSummaryCards(totalActiveCells, totalVolume);
        
  
        JPanel detailTable = createDetailTable();
        
       
        JPanel buttonPanel = createButtonPanel();
        
    
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(summaryCards, BorderLayout.BEFORE_FIRST_LINE);
        mainPanel.add(detailTable, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        
        setContentPane(mainPanel);
        pack();
        setMinimumSize(new Dimension(560, 360));
        setLocationRelativeTo(getOwner());
    }
    
   
    private double calculateTotalVolume() {
        double total = 0.0;
        for (double volume : gasVolumes) {
            total += volume;
        }
        return Math.max(0.000001, total); 
    }
    
 
    private int calculateTotalActiveCells() {
        int total = 0;
        for (int count : cellCounts) {
            total += count;
        }
        return total;
    }
    

    private JLabel createTitleLabel() {
        JLabel title = new JLabel(Settings.SUMMARY_TITLE);
        title.setFont(new Font(Settings.FONT_NAME, Font.BOLD, Settings.FONT_SIZE_TITLE));
        
        if (isDarkTheme) {
            title.setForeground(Settings.DARK_TEXT_PRIMARY);
        } else {
            title.setForeground(Settings.LIGHT_TEXT_PRIMARY);
        }
        
        return title;
    }
    
  
    private JPanel createSummaryCards(int totalActiveCells, double totalVolume) {
        JPanel cardPanel = new JPanel(new GridLayout(1, 2, 12, 0));
        cardPanel.setOpaque(false);

        JPanel cellCard = createInfoCard(Settings.SUMMARY_TOTAL_CELLS, String.valueOf(totalActiveCells));
        
        String volumeText = numberFormatter.format(totalVolume) + Settings.SUMMARY_VOLUME_UNIT;
        JPanel volumeCard = createInfoCard(Settings.SUMMARY_TOTAL_VOLUME, volumeText);
        
        cardPanel.add(cellCard);
        cardPanel.add(volumeCard);
        
        return cardPanel;
    }
    
 
    private JPanel createDetailTable() {
        JPanel tablePanel = new JPanel(new GridBagLayout());
        tablePanel.setOpaque(false);
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(8, 8, 8, 8);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        String[] gasTypeNames = { Settings.NO_GAS, Settings.LOW_GAS, Settings.HIGH_GAS };
        Color[] gasTypeColors = { Settings.COLOR_NO_GAS, Settings.COLOR_LOW_GAS, Settings.COLOR_HIGH_GAS };
        
    
        createTableHeader(tablePanel, constraints);
        
     
        for (int i = 0; i < 3; i++) {
            createTableDataRow(tablePanel, constraints, i + 1, gasTypeColors[i], 
                             gasTypeNames[i], cellCounts[i], gasVolumes[i]);
        }
        
        return tablePanel;
    }
    
    
    private JPanel createButtonPanel() {
        JButton closeButton = new JButton(Settings.SUMMARY_CLOSE);
        closeButton.addActionListener(event -> dispose());
        

        if (isDarkTheme) {
            closeButton.setForeground(Settings.DARK_TEXT_PRIMARY);
        }
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(closeButton);
        
        return buttonPanel;
    }

 
    private JPanel createInfoCard(String labelText, String valueText) {
        JPanel card = new JPanel(new BorderLayout(6, 4));
        card.setOpaque(true);

        Color borderColor = isDarkTheme ? new Color(80, 80, 80) : new Color(200, 200, 200);
        card.setBorder(new CompoundBorder(
            new LineBorder(borderColor, 1, true), 
            new EmptyBorder(10, 12, 10, 12)
        ));
        
  
        JLabel titleLabel = new JLabel(labelText);
        titleLabel.setFont(new Font(Settings.FONT_NAME, Font.PLAIN, Settings.FONT_SIZE_NORMAL));
        
        JLabel valueLabel = new JLabel(valueText);
        valueLabel.setFont(new Font(Settings.FONT_NAME, Font.BOLD, 18));
        

        if (isDarkTheme) {
            titleLabel.setForeground(Settings.DARK_TEXT_SECONDARY);
            valueLabel.setForeground(Settings.DARK_TEXT_PRIMARY);
            card.setBackground(new Color(45, 45, 45));
        } else {
            titleLabel.setForeground(Settings.LIGHT_TEXT_SECONDARY);
            valueLabel.setForeground(Settings.LIGHT_TEXT_PRIMARY);
            card.setBackground(Color.WHITE);
        }
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    private void createTableHeader(JPanel table, GridBagConstraints constraints) {
        constraints.gridy = 0;
        constraints.gridx = 0;
        
        String[] headerTexts = {
            Settings.SUMMARY_HEADER_EMPTY, 
            Settings.SUMMARY_HEADER_TYPE, 
            Settings.SUMMARY_HEADER_CELLS, 
            Settings.SUMMARY_HEADER_VOLUME, 
            Settings.SUMMARY_HEADER_PERCENT
        };
        
        for (int i = 0; i < headerTexts.length; i++) {
            constraints.gridx = i;
            JLabel headerLabel = createHeaderLabel(headerTexts[i]);
            table.add(headerLabel, constraints);
        }
    }

    private JLabel createHeaderLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(Settings.FONT_NAME, Font.BOLD, Settings.FONT_SIZE_NORMAL));

        if (isDarkTheme) {
            label.setForeground(Settings.DARK_TEXT_PRIMARY);
        } else {
            label.setForeground(Settings.LIGHT_TEXT_PRIMARY);
        }
        
        return label;
    }
    

    private void createTableDataRow(JPanel table, GridBagConstraints constraints, int rowNumber, 
                                   Color gasColor, String gasTypeName, int cellCount, double volume) {
        constraints.gridy = rowNumber;
        
      
        double cellPercentage = (totalCellCount == 0) ? 0.0 : (cellCount * 100.0 / totalCellCount);
        
       
        constraints.gridx = 0;
        JPanel colorDot = createColorIndicator(gasColor);
        table.add(colorDot, constraints);

        constraints.gridx = 1;
        JLabel typeLabel = createDataLabel(gasTypeName);
        table.add(typeLabel, constraints);
       
        constraints.gridx = 2;
        JLabel countLabel = createDataLabel(cellCount + Settings.SUMMARY_CELLS_UNIT);
        table.add(countLabel, constraints);

        constraints.gridx = 3;
        String volumeText = numberFormatter.format(volume) + Settings.SUMMARY_VOLUME_UNIT;
        JLabel volumeLabel = createDataLabel(volumeText);
        table.add(volumeLabel, constraints);
        

        constraints.gridx = 4;
        JProgressBar percentageBar = createPercentageBar((int) Math.round(cellPercentage), gasColor);
        table.add(percentageBar, constraints);
    }
    
 
    private JLabel createDataLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(Settings.FONT_NAME, Font.PLAIN, Settings.FONT_SIZE_NORMAL));
        

        if (isDarkTheme) {
            label.setForeground(Settings.DARK_TEXT_PRIMARY);
        } else {
            label.setForeground(Settings.LIGHT_TEXT_PRIMARY);
        }
        
        return label;
    }
    

    private JPanel createColorIndicator(Color color) {
        JPanel colorDot = new JPanel();
        colorDot.setPreferredSize(new Dimension(14, 14));
        colorDot.setBackground(color);
  
        Color borderColor = isDarkTheme ? new Color(100, 100, 100) : new Color(150, 150, 150);
        colorDot.setBorder(new LineBorder(borderColor, 1, true));
        
        return colorDot;
    }
    

    private JProgressBar createPercentageBar(int percentage, Color color) {
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(Math.max(0, Math.min(100, percentage)));
        progressBar.setStringPainted(true);
        progressBar.setForeground(color);
        
       
        if (isDarkTheme) {
            progressBar.setBackground(new Color(60, 60, 60));
        }
        
        return progressBar;
    }
}
