package backeryProject.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
public class MemberVO {
	private String member_name;
	private String member_number;
	private String member_pw;
	
	private int order_id;
	private int todaybackery_id;
	private int order_quantity;
	
	private String backery_name;
	private int price;
}
