package br.com.digitalinnovationone.whiskey_api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import br.com.digitalinnovationone.whiskey_api.enums.WhiskeyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "whiskey")
@NoArgsConstructor
@AllArgsConstructor
public class Whiskey {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false, unique = true)
	    private String name;

	    @Column(nullable = false)
	    private String brand;

	    @Column(nullable = false)
	    private int max;

	    @Column(nullable = false)
	    private int quantity;

	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false)
	    private WhiskeyType type;
}
