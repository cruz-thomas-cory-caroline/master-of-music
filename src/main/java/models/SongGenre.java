package models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="genres")
public class SongGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "genres")
    private List<Song> songs;
}
