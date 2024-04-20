import java.util.*;

public class KnowledgeBaseSystem {
    private Map<String, User> users;
    private Map<String, Category> categories;
    private Map<String, Article> articles;

    public KnowledgeBaseSystem() {
        this.users = new HashMap<>();
        this.categories = new HashMap<>();
        this.articles = new HashMap<>();
    }

    public void addUser(String userId, String userName, String email) {
        if (!users.containsKey(userId)) {
            User user = new User(userId, userName, email);
            users.put(userId, user);
            System.out.println("User added successfully.");
        } else {
            System.out.println("User already exists.");
        }
    }

    public void addCategory(String categoryId, String categoryName) {
        if (!categories.containsKey(categoryId)) {
            Category category = new Category(categoryId, categoryName);
            categories.put(categoryId, category);
            System.out.println("Category added successfully.");
        } else {
            System.out.println("Category already exists.");
        }
    }

    public void createArticle(String articleId, String title, String content, String categoryId, String userId) {
        if (categories.containsKey(categoryId) && users.containsKey(userId)) {
            Article article = new Article(articleId, title, content, categoryId, userId);
            articles.put(articleId, article);
            System.out.println("Article created successfully.");
        } else {
            System.out.println("Category or user not found.");
        }
    }

    public void updateArticle(String articleId, String content, String userId) {
        Article article = findArticleById(articleId);
        if (article != null && article.getUserId().equals(userId)) {
            article.setContent(content);
            System.out.println("Article updated successfully.");
        } else {
            System.out.println("Article not found or you do not have permission to update.");
        }
    }

    public Article findArticleById(String articleId) {
        return articles.get(articleId);
    }

    public List<Article> searchArticles(String query) {
        List<Article> results = new ArrayList<>();
        for (Article article : articles.values()) {
            if (article.getTitle().toLowerCase().contains(query.toLowerCase()) || 
                article.getContent().toLowerCase().contains(query.toLowerCase())) {
                results.add(article);
            }
        }
        return results;
    }

    public static void main(String[] args) {
        KnowledgeBaseSystem system = new KnowledgeBaseSystem();

        // Add users
        system.addUser("U001", "Alice", "alice@example.com");
        system.addUser("U002", "Bob", "bob@example.com");

        // Add categories
        system.addCategory("C001", "Programming");
        system.addCategory("C002", "Networking");

        // Create article
        system.createArticle("A001", "Java Basics", "Java is a high-level, object-oriented programming language.", "C001", "U001");

        // Update article
        system.updateArticle("A001", "Java is a high-level, object-oriented programming language.", "U001");

        // Search articles
        List<Article> searchResults = system.searchArticles("Java");
        System.out.println("Search Results:");
        for (Article article : searchResults) {
            System.out.println(article);
        }
    }
}

class User {
    private String userId;
    private String userName;
    private String email;

    public User(String userId, String userName, String email) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

class Category {
    private String categoryId;
    private String categoryName;

    public Category(String categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId='" + categoryId + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}

class Article {
    private String articleId;
    private String title;
    private String content;
    private String categoryId;
    private String userId;

    public Article(String articleId, String title, String content, String categoryId, String userId) {
        this.articleId = articleId;
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
        this.userId = userId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleId='" + articleId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
