package backeryProject.vo;

import java.sql.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
public class BackeryVO {
	 private String backery_id;
	 private String backery_name;
	 private int price;
	 private String category;
	 
	 private String todaybackery_id;
	 private int quantity;
	 private Timestamp create_date;
}
