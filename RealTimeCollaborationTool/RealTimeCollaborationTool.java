import java.util.*;
import java.io.*;

public class RealTimeCollaborationTool {
    private Map<String, User> users;
    private Map<String, Document> documents;

    public RealTimeCollaborationTool() {
        this.users = new HashMap<>();
        this.documents = new HashMap<>();
    }

    public void registerUser(String userId, String username) {
        if (!users.containsKey(userId)) {
            User user = new User(userId, username);
            users.put(userId, user);
            System.out.println("User registered successfully.");
        } else {
            System.out.println("User already exists.");
        }
    }

    public void createDocument(String documentId, String documentName, String ownerId) {
        if (!documents.containsKey(documentId)) {
            Document document = new Document(documentId, documentName, ownerId);
            documents.put(documentId, document);
            System.out.println("Document created successfully.");
        } else {
            System.out.println("Document already exists.");
        }
    }

    public void editDocument(String documentId, String userId, String content) {
        Document document = documents.get(documentId);
        if (document != null && document.getOwnerId().equals(userId)) {
            document.setContent(content);
            System.out.println("Document edited successfully.");
        } else {
            System.out.println("Document not found or you do not have permission to edit.");
        }
    }

    public void displayDocument(String documentId) {
        Document document = documents.get(documentId);
        if (document != null) {
            System.out.println("Document: " + document.getName());
            System.out.println("Content: " + document.getContent());
        } else {
            System.out.println("Document not found.");
        }
    }

    public static void main(String[] args) {
        RealTimeCollaborationTool tool = new RealTimeCollaborationTool();

        // Register users
        tool.registerUser("U001", "Alice");
        tool.registerUser("U002", "Bob");

        // Create document
        tool.createDocument("D001", "Project Plan", "U001");

        // Edit document
        tool.editDocument("D001", "U001", "1. Define scope\n2. Assign tasks");

        // Display document
        tool.displayDocument("D001");
    }
}

class User {
    private String userId;
    private String username;

    public User(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}

class Document {
    private String documentId;
    private String name;
    private String ownerId;
    private String content;

    public Document(String documentId, String name, String ownerId) {
        this.documentId = documentId;
        this.name = name;
        this.ownerId = ownerId;
        this.content = "";
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getName() {
        return name;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
