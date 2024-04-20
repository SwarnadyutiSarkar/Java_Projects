import java.util.*;
import java.io.File;
import javax.sound.sampled.*;

public class MusicPlayerApp {
    private static List<Song> songs = new ArrayList<>();
    private static Playlist currentPlaylist = new Playlist();

    public static void main(String[] args) {
        initializeSongs();

        while (true) {
            System.out.println("\nMusic Player App");
            System.out.println("1. Add Song");
            System.out.println("2. Create Playlist");
            System.out.println("3. Add Song to Playlist");
            System.out.println("4. Play Song");
            System.out.println("5. Skip Song");
            System.out.println("6. Pause Song");
            System.out.println("7. Resume Song");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addSong(scanner);
                    break;
                case 2:
                    createPlaylist(scanner);
                    break;
                case 3:
                    addSongToPlaylist(scanner);
                    break;
                case 4:
                    playSong();
                    break;
                case 5:
                    skipSong();
                    break;
                case 6:
                    pauseSong();
                    break;
                case 7:
                    resumeSong();
                    break;
                case 8:
                    System.out.println("Exiting the program. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void initializeSongs() {
        songs.add(new Song("Song1", "Artist1", "Album1", "path/to/song1.mp3"));
        songs.add(new Song("Song2", "Artist2", "Album2", "path/to/song2.mp3"));
        songs.add(new Song("Song3", "Artist3", "Album3", "path/to/song3.mp3"));
    }

    private static void addSong(Scanner scanner) {
        System.out.print("Enter song title: ");
        String title = scanner.nextLine();
        System.out.print("Enter artist name: ");
        String artist = scanner.nextLine();
        System.out.print("Enter album name: ");
        String album = scanner.nextLine();
        System.out.print("Enter file path: ");
        String filePath = scanner.nextLine();
        Song song = new Song(title, artist, album, filePath);
        songs.add(song);
        System.out.println("Song added successfully!");
    }

    private static void createPlaylist(Scanner scanner) {
        System.out.print("Enter playlist name: ");
        String name = scanner.nextLine();
        Playlist playlist = new Playlist(name);
        currentPlaylist = playlist;
        System.out.println("Playlist created successfully!");
    }

    private static void addSongToPlaylist(Scanner scanner) {
        if (currentPlaylist == null) {
            System.out.println("No playlist selected!");
            return;
        }
        System.out.print("Enter song title to add to playlist: ");
        String title = scanner.nextLine();
        Song songToAdd = findSongByTitle(title);
        if (songToAdd != null) {
            currentPlaylist.addSong(songToAdd);
            System.out.println("Song added to playlist!");
        } else {
            System.out.println("Song not found!");
        }
    }

    private static void playSong() {
        if (currentPlaylist == null || currentPlaylist.isEmpty()) {
            System.out.println("No playlist or songs available!");
            return;
        }
        Song songToPlay = currentPlaylist.getCurrentSong();
        if (songToPlay != null) {
            playAudio(songToPlay.getFilePath());
            System.out.println("Playing: " + songToPlay.getTitle());
        } else {
            System.out.println("No song selected to play!");
        }
    }

    private static void skipSong() {
        if (currentPlaylist == null || currentPlaylist.isEmpty()) {
            System.out.println("No playlist or songs available!");
            return;
        }
        currentPlaylist.nextSong();
        playSong();
    }

    private static void pauseSong() {
        // Logic to pause the audio playback
        System.out.println("Song paused.");
    }

    private static void resumeSong() {
        // Logic to resume the audio playback
        System.out.println("Song resumed.");
    }

    private static Song findSongByTitle(String title) {
        for (Song song : songs) {
            if (song.getTitle().equalsIgnoreCase(title)) {
                return song;
            }
        }
        return null;
    }

    private static void playAudio(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            System.out.println("Error playing audio: " + e.getMessage());
        }
    }
}

class Song {
    private String title;
    private String artist;
    private String album;
    private String filePath;

    public Song(String title, String artist, String album, String filePath) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.filePath = filePath;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getFilePath() {
        return filePath;
    }
}

class Playlist {
    private String name;
    private List<Song> songs = new ArrayList<>();
    private int currentSongIndex = 0;

    public Playlist() {
        this.name = "Default";
    }

    public Playlist(String name) {
        this.name = name;
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public boolean isEmpty() {
        return songs.isEmpty();
    }

    public Song getCurrentSong() {
        if (currentSongIndex >= 0 && currentSongIndex < songs.size()) {
            return songs.get(currentSongIndex);
        }
        return null;
    }

    public void nextSong() {
        currentSongIndex = (currentSongIndex + 1) % songs.size();
    }
}
