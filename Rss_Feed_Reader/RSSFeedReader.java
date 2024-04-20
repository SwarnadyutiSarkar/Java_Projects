import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;
import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.filter.*;

public class RSSFeedReader extends JFrame implements ActionListener {
    private JTextField urlTextField;
    private JButton fetchButton;
    private JList<String> feedList;
    private DefaultListModel<String> feedListModel;
    private JTextArea descriptionTextArea;

    public RSSFeedReader() {
        setTitle("RSS Feed Reader");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        urlTextField = new JTextField();
        fetchButton = new JButton("Fetch");
        feedListModel = new DefaultListModel<>();
        feedList = new JList<>(feedListModel);
        descriptionTextArea = new JTextArea();
        descriptionTextArea.setEditable(false);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(urlTextField, BorderLayout.CENTER);
        topPanel.add(fetchButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(feedList), BorderLayout.WEST);
        add(new JScrollPane(descriptionTextArea), BorderLayout.CENTER);

        fetchButton.addActionListener(this);
        feedList.addListSelectionListener(e -> displayDescription());

        setVisible(true);
    }

    private void displayDescription() {
        String selectedFeed = feedList.getSelectedValue();
        if (selectedFeed != null) {
            String[] parts = selectedFeed.split(" - ");
            String url = parts[1];
            try {
                SAXBuilder saxBuilder = new SAXBuilder();
                Document document = saxBuilder.build(new URL(url));
                Element channel = document.getRootElement().getChild("channel");
                Element item = channel.getChild("item");
                String description = item.getChildText("description");
                descriptionTextArea.setText(description);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error fetching feed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fetchButton) {
            String url = urlTextField.getText();
            try {
                SAXBuilder saxBuilder = new SAXBuilder();
                Document document = saxBuilder.build(new URL(url));
                Element channel = document.getRootElement().getChild("channel");
                List<Element> items = channel.getChildren("item");

                feedListModel.clear();
                for (Element item : items) {
                    String title = item.getChildText("title");
                    String link = item.getChildText("link");
                    feedListModel.addElement(title + " - " + link);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error fetching feed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RSSFeedReader());
    }
}
