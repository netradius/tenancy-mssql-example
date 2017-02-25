package com.mastercontrol.data.model;

import com.mastercontrol.data.lifecycle.UUIDIdentified;
import com.mastercontrol.data.lifecycle.UUIDListener;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author Kevin Hawkins
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "user")
@EntityListeners({UUIDListener.class})
public class User implements UUIDIdentified<User> {

	@Id
	@Column(name = "id", columnDefinition = "uniqueidentifier", nullable = false, unique = true)
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	private UUID id;

	@Column(name = "name", length = 30, nullable = false)
	private String name;
}
