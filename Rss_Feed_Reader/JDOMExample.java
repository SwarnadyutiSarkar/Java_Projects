import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import java.net.URL;
import java.util.List;

public class JDOMExample {
    public static void main(String[] args) {
        try {
            // URL of the RSS feed
            String rssFeedURL = "https://example.com/rss";

            // Create a SAXBuilder object
            SAXBuilder saxBuilder = new SAXBuilder();

            // Build the XML document
            Document document = saxBuilder.build(new URL(rssFeedURL));

            // Get the root element (usually <rss>)
            Element rootElement = document.getRootElement();

            // Get the <channel> element
            Element channel = rootElement.getChild("channel");

            // Get all <item> elements
            List<Element> items = channel.getChildren("item");

            // Iterate through each <item>
            for (Element item : items) {
                // Get title, link, and description of each <item>
                String title = item.getChildText("title");
                String link = item.getChildText("link");
                String description = item.getChildText("description");

                // Print the extracted data
                System.out.println("Title: " + title);
                System.out.println("Link: " + link);
                System.out.println("Description: " + description);
                System.out.println("------------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
