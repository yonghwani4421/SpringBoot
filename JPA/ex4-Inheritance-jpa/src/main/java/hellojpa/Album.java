package hellojpa;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("A")
public class Album extends Item {
    private String artist;
}
