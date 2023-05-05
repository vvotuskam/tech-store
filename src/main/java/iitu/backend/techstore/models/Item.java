package iitu.backend.techstore.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "items")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private double price;
    private String pictureUrl;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "users_items",
            joinColumns = { @JoinColumn(name = "item_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private List<User> users;
}
