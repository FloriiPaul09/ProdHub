package com.project.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "repository")
public class Reposi {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String name;
	
	
	private String description;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "repository_option",
			joinColumns = @JoinColumn(name = "repository_id"),
			inverseJoinColumns = @JoinColumn(name = "option_id"))
	private Set<RepoOption> options = new HashSet<>();
	
	
}
