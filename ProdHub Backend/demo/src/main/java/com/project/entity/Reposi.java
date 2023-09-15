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
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String type;
	
	
	private String description;
	
	@Lob
	private byte[] data;
	

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "repository_option",
			joinColumns = @JoinColumn(name = "repository_id"),
			inverseJoinColumns = @JoinColumn(name = "option_id"))
	private Set<RepoPostOption> postOptions = new HashSet<>();
	
	public Reposi() {}
	
	public Reposi(String name, String type, byte[] data) {
		super();
		this.name = name;
		this.type = type;
		this.data = data;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public Set<RepoPostOption> getPostOptions() {
		return postOptions;
	}

	public void setPostOptions(Set<RepoPostOption> postoptions) {
		postOptions = postoptions;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
