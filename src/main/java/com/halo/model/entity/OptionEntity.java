package com.halo.model.entity;

import com.halo.model.enums.OptionType;
import jakarta.persistence.*;
import lombok.*;

/**
 * @author Deep
 * @create 2023-09-27
 */
@Data
@Entity
@Table(name = "options")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OptionEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * option type
	 */
	@Column(name = "type")
	private OptionType type;

	/**
	 * option key
	 */
	@Column(name = "option_key", length = 100, nullable = false)
	private String key;

	/**
	 * option value
	 */
	@Column(name = "option_value", nullable = false)
	@Lob    // 大型对象持久保存到数据库,默认为Blob
	private String value;

	public OptionEntity() {
	}


	@Override
	public void prePersist() {
		super.prePersist();

		if (type == null) {
			type = OptionType.INTERNAL;
		}
	}
}
