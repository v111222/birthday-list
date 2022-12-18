package ru.mefodovsky.birthdaylist.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "birthdays")
public class Birthday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User owner;

    @Column(name = "name")
    private String name;

    @Column(name = "birthday_month")
    private String month;

    @Column(name = "birthday_day")
    private int day;

    @Override
    public String toString() {
        return "Birthday{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", month='" + month + '\'' +
                ", day=" + day +
                '}';
    }
}
