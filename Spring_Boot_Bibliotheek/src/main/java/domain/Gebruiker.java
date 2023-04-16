package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class Gebruiker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username, password;
    private int maxAantalFavorieten;

    @Column(name = "enabled")
    private Boolean isActief;

    @Enumerated(EnumType.STRING)
    private Authorities authority;

    @ManyToMany
    @JoinTable(name = "gebruiker_boek", joinColumns = @JoinColumn(name = "gebruiker_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "boek_id", referencedColumnName = "id"))
    List<Boek> favorieten;

    public Gebruiker(String username, String password, Authorities authority, Boolean isActief,
	    int maxAantalFavorieten) {
	this.username = username;
	this.password = password;
	this.authority = authority;
	this.isActief = isActief;
	this.maxAantalFavorieten = maxAantalFavorieten;
	favorieten = new ArrayList<>();
    }

    public void addFavoriet(Boek boek) {
	if (!favorieten.contains(boek)) {
	    this.favorieten.add(boek);
	}
    }

    public void removeFavoriet(Boek boek) {
	this.favorieten.remove(boek);
    }
}
