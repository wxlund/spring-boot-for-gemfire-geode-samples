package io.pivotal.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.gemfire.mapping.annotation.Region;

@Data
@AllArgsConstructor(staticName = "of")
@Region("Customer")
public class Customer {

	Integer id;
	String name;

}
