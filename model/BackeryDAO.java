package backeryProject.model;

import java.sql.*;
import java.util.*;

import com.shinhan.dbutil.OracleUtil;

import backeryProject.OracleUtilTY;
import backeryProject.vo.BackeryVO;
import backeryProject.vo.MemberVO;

public class BackeryDAO {
	Connection conn;
	Statement st;
	PreparedStatement pst;
	CallableStatement cst;
	ResultSet rs;
	int resultCount; 
	
	//[고객용] 1.모든 빵 조회
	public List<BackeryVO> selectMenu(){
		String sql = """
					select backery_name, price, category from backerys
				""";
		
		List<BackeryVO> backeryList = new ArrayList<>();
		conn = OracleUtilTY.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				BackeryVO backery = makeBackery(rs);
				backeryList.add(backery);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			OracleUtilTY.dbDisconnect(rs, st, conn);
		}
		return backeryList;
	}
	
	//[고객용] 2.실시간 빵 목록 조회
	public List<BackeryVO> selectALL(){
		String sql = """
				select backery_name, price, create_date, quantity, category from todaybackerys join backerys using(backery_id)
				""";
		List<BackeryVO> backeryList = new ArrayList<>();
		conn = OracleUtilTY.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				BackeryVO backery = makeRealBackery(rs);
				backeryList.add(backery);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			OracleUtilTY.dbDisconnect(rs, st, conn);
		}
		return backeryList;
	}
	
	//[고객용]3.실시간 빵 목록 카테고리별 조회
	public List<BackeryVO> selectCategory(String category){
		String sql = """
				select backery_name, price, create_date, quantity, category from todaybackerys join backerys using(backery_id)
				where category= ?
				""";
		List<BackeryVO> backeryList = new ArrayList<>();
		conn = OracleUtilTY.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, category);
			rs = pst.executeQuery();
			while(rs.next()) {
				BackeryVO backery = makeRealBackery(rs);
				backeryList.add(backery);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			OracleUtilTY.dbDisconnect(rs, pst, conn);
		}
		
		return backeryList;
	}
	
	//[고객용]4.빵 이름 별 목록
	public List<BackeryVO> selectName(String name){
		String sql = """
			select backery_name, price, create_date, quantity, category from todaybackerys join backerys using(backery_id)
			where backery_name= ? 
				""";
		List<BackeryVO> backeryList = new ArrayList<>();
		conn = OracleUtilTY.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, name);
			rs = pst.executeQuery();
			while(rs.next()) {
				BackeryVO backery = makeRealBackery(rs);
				backeryList.add(backery);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			OracleUtilTY.dbDisconnect(rs, pst, conn);
		}
		
		return backeryList;
	}
	
	//[고객용]4.빵 이름 별 목록 조회 시 없는 빵을 조회했는지 확인하기
	public int isExistBreadName(String bread_name) {
		int result = 0;
		String sql = """
				select count(*) existbread from backerys
				where backery_name=?
				""";
		conn = OracleUtilTY.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, bread_name);
			rs = pst.executeQuery();
			if(rs.next()) {
				result = rs.getInt("existbread");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			OracleUtilTY.dbDisconnect(rs, pst, conn);
		}
		return result;
	}
	
	//[고객용]5.회원 주문(마이페이지)
	public int order(MemberVO member) {
		String sql = """
					INSERT INTO ORDERS VALUES (ORDER_NUM.nextval, ?, ?, ?)
				""";
		conn = OracleUtilTY.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, member.getMember_name());
			pst.setInt(2, member.getTodaybackery_id());
			pst.setInt(3, member.getOrder_quantity());

			resultCount = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			OracleUtilTY.dbDisconnect(null, pst, conn);
		}
		return resultCount;
	}
	
	//[고객용]5.회원 주문 - 빵 이름 입력시 빵 ID 반환
	public int selectBreadID(String bread_name) {
		String sql = """
					select todaybackery_id from todaybackerys join backerys using(backery_id) 
					where backery_name = ?
				""";
		conn = OracleUtilTY.getConnection();
		int result = 0;
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, bread_name);
			rs = pst.executeQuery();
			if(rs.next()) {
				result = rs.getInt("todaybackery_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			OracleUtilTY.dbDisconnect(null, pst, conn);
		}
		return result;
	}
	
	//[고객용]5.회원 주문 - 고객이 같은 빵을 주문하려하면 수량만 수정하도록 안내
	public int alreadyExistBreadorder(String name, int id) {
		int result = 0;
		String sql = """
				SELECT count(*) existorder FROM ORDERS
				WHERE member_name = ? and todaybackery_id = ?
				""";
		conn = OracleUtilTY.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, name);
			pst.setInt(2, id);
			rs = pst.executeQuery();
			if(rs.next()) {
				result = rs.getInt("existorder");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			OracleUtilTY.dbDisconnect(rs, pst, conn);
		}
		return result;
	}
	
	//[고객용]5.회원 주문 - 고객이 입력한 빵이 주문가능한 빵인지 확인
		public int alreadyExistBreadName(String bread_name) {
			int result = 0;
			String sql = """
					select count(*) existorder from todaybackerys join backerys using(backery_id)
					where backery_name = ? and quantity>0
					""";
			conn = OracleUtilTY.getConnection();
			try {
				pst = conn.prepareStatement(sql);
				pst.setString(1, bread_name);
				rs = pst.executeQuery();
				if(rs.next()) {
					result = rs.getInt("existorder");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				OracleUtilTY.dbDisconnect(rs, pst, conn);
			}
			return result;
		}
	
	//[고객용]5. 고객 개인별 자신의 주문 데이터 조회하기
	public List<MemberVO> checkMyorder(String name){
		String sql = """
				SELECT order_id, backery_name, order_quantity, (order_quantity*price) price
				FROM ORDERS join todaybackerys using(todaybackery_id) join backerys using(backery_id)
				WHERE member_name = ?
				""";
		List<MemberVO> backeryList = new ArrayList<>();
		conn = OracleUtilTY.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, name);
			rs = pst.executeQuery();
			while(rs.next()) {
				MemberVO member = makeMember();
				backeryList.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			OracleUtilTY.dbDisconnect(rs, pst, conn);
		}
		
		return backeryList;
	}

	//[고객용]5. 주문 수량 수정하기(update)
	public int orderUpdate(int quantity, int orderID) {
		String sql = """
				UPDATE ORDERS SET ORDER_QUANTITY = ? WHERE ORDER_ID = ?
				""";
		conn = OracleUtilTY.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, quantity);
			pst.setInt(2, orderID);
			resultCount = pst.executeUpdate(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			OracleUtilTY.dbDisconnect(null, pst, conn);
		}
		return resultCount;
	}
	
	//[고객용]5.주문 취소
	public int orderDelete(int orderID) {
		String sql = """
			DELETE FROM ORDERS WHERE ORDER_ID = ?
		""";
		conn = OracleUtilTY.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, orderID);
			resultCount = pst.executeUpdate(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			OracleUtilTY.dbDisconnect(null, pst, conn);
		}
		return resultCount;
	}
	
	//[고객용] 수량확인
	public int checkQuantity(String bread_name) {
		int result = 0;
		String sql = """
				select quantity
				from todaybackerys join backerys using(backery_id)
				where backery_name = ?
				""";
		conn = OracleUtilTY.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, bread_name);
			rs = pst.executeQuery();
			if(rs.next()) {
				result = rs.getInt("quantity");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			OracleUtilTY.dbDisconnect(rs, pst, conn);
		}
		return result;
	}
	
	public int checkQuantity(int bread_id) {
		int result = 0;
		String sql = """
				select quantity
				from todaybackerys join orders using(todaybackery_id)
				where order_id = ?
				""";
		conn = OracleUtilTY.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, bread_id);
			rs = pst.executeQuery();
			if(rs.next()) {
				result = rs.getInt("quantity");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			OracleUtilTY.dbDisconnect(rs, pst, conn);
		}
		return result;
	}
	
	public int checkOrderQuantity(int bread_id) {
		int result = 0;
		String sql = """
				select order_quantity
				from todaybackerys join orders using(todaybackery_id)
				where order_id = ?
				""";
		conn = OracleUtilTY.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, bread_id);
			rs = pst.executeQuery();
			if(rs.next()) {
				result = rs.getInt("order_quantity");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			OracleUtilTY.dbDisconnect(rs, pst, conn);
		}
		return result;
	}
	
	//프로시저 호출
	public String newDaySales() {
		String sql = " { call newdaySales } ";
		conn = OracleUtilTY.getConnection();
		try {
			cst = conn.prepareCall(sql);
			
			cst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			OracleUtilTY.dbDisconnect(null, cst, conn);
		}
		String result = "주문테이블과 오늘만든빵 테이블을 비웁니다.";
		return result;
	}
	
	//[고객용]6.비회원 회원가입
	public int signUp(MemberVO member) {
		String sql = """
					INSERT INTO MEMBERS VALUES(?, ?, ?)
				""";
		conn = OracleUtilTY.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, member.getMember_name());
			pst.setString(2, member.getMember_number());
			pst.setString(3, member.getMember_pw());

			resultCount = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			OracleUtilTY.dbDisconnect(null, pst, conn);
		}
		return resultCount;
	}
	
	//[고객용]6-1.이미 존재하는 회원인지 확인하기
	public int alreadyMember (String name) {
		int result = 0;
		String sql = """
				select count(*) as existuser from members where member_name = ?
				""";
		conn = OracleUtilTY.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, name);
			rs = pst.executeQuery();
			if(rs.next()) {
				result = rs.getInt("existuser");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			OracleUtilTY.dbDisconnect(rs, pst, conn);
		}
		return result;
	}
	
	
	//[관리자용]1.전체 빵 목록 조회
	public List<BackeryVO> selectMenuforManager(){
		String sql = """
					select backery_Id, backery_name, nvl(quantity,0) as quantity, price, category
					from todaybackerys  right outer join backerys using(backery_id)
					order by 1
				""";
		
		List<BackeryVO> backeryList = new ArrayList<>();
		conn = OracleUtilTY.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				BackeryVO backery = makeBackeryforManger(rs);
				backeryList.add(backery);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			OracleUtilTY.dbDisconnect(rs, st, conn);
		}
		return backeryList;
	}
	
	//[관리자용]2.갓 구운 빵 등록
	public int registBread(BackeryVO bread) {
		String sql = """
					INSERT INTO  TODAYBACKERYS(todaybackery_id,backery_id,quantity) 
					VALUES (TODAY_NUM.nextval, upper(?), ?)
				""";
		conn = OracleUtilTY.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, bread.getBackery_id());
			pst.setInt(2, bread.getQuantity());

			resultCount = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			OracleUtilTY.dbDisconnect(null, pst, conn);
		}
		return resultCount;
	}
	
	//[관리자용]2-1. 갓 구운 빵 등록시 존재하는 빵 ID인지 확인
	public int isExistBread(String bread_id) {
		int result = 0;
		String sql = """
				select count(*) existbread from backerys where backery_id= UPPER(?)
				""";
		conn = OracleUtilTY.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, bread_id);
			rs = pst.executeQuery();
			if(rs.next()) {
				result = rs.getInt("existbread");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			OracleUtilTY.dbDisconnect(rs, pst, conn);
		}
		return result;
	}
	
	//[관리자용]2-2.갓 구운 빵 등록시 이미 등록한 빵인지 확인
	public int alreadyExistBread(String bread_id) {
		int result = 0;
		String sql = """
				select count(*) existbread from TODAYBACKERYS where backery_id=UPPER(?)
				""";
		conn = OracleUtilTY.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, bread_id);
			rs = pst.executeQuery();
			if(rs.next()) {
				result = rs.getInt("existbread");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			OracleUtilTY.dbDisconnect(rs, pst, conn);
		}
		return result;
	}
	
	//[관리자용]3. 오늘의 매출 확인
	public String todaySales(){
		String sql = """
					select sum(order_quantity) as soldcount, sum(order_quantity*price) amount
					from todaybackerys  join backerys using(backery_id)
					        join orders using(todaybackery_id)
				""";
		
		String result = "";
		conn = OracleUtilTY.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next()) {
				String soldcount = Integer.toString(rs.getInt("soldcount"));
				String amount = Integer.toString(rs.getInt("amount"));
				result = "현재까지 " + soldcount + "개의 빵이 팔렸습니다.\n" 
						+ "총 매출은 " + amount + "원 입니다.";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			OracleUtilTY.dbDisconnect(rs, st, conn);
		}
		return result;
	}
	
	//사람 제작소/////////////////////////////////////////////////////////////////
	private MemberVO makeMember() {
		MemberVO member = new MemberVO();
		
		try {
			member.setOrder_id(rs.getInt("Order_id"));
			member.setBackery_name(rs.getString("Backery_name"));
			member.setOrder_quantity(rs.getInt("Order_quantity"));
			member.setPrice(rs.getInt("Price"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return member;
	}
	
	//빵 제작소//////////////////////////////////////////////////////////////////
	//빵 colum - 관리자용 모든 빵 조회
	private BackeryVO makeBackeryforManger(ResultSet rs) {
		BackeryVO backery = new BackeryVO();
		
		try {
			backery.setBackery_id(rs.getString("Backery_id"));
			backery.setBackery_name(rs.getString("Backery_name"));
			backery.setPrice(rs.getInt("Price"));
			backery.setQuantity(rs.getInt("Quantity"));
			backery.setCategory(rs.getString("Category"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return backery;
	}
	
	
	//실시간 빵 column - 고객용 실시간 빵 목록 조회에 사용
	private BackeryVO makeRealBackery(ResultSet rs) {
		BackeryVO backery = new BackeryVO();
		
		try {
			backery.setBackery_name(rs.getString("Backery_name"));
			backery.setPrice(rs.getInt("Price"));
			backery.setCreate_date(rs.getTimestamp("Create_date"));
			backery.setQuantity(rs.getInt("Quantity"));
			backery.setCategory(rs.getString("Category"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return backery;
	}

	//빵 column - 고객용 전체 빵 목록 조회에 사용
	private BackeryVO makeBackery(ResultSet rs) {
		BackeryVO backery = new BackeryVO();
		try {
			backery.setBackery_name(rs.getString("Backery_name"));
			backery.setCategory(rs.getString("Category"));
			backery.setPrice(rs.getInt("Price"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return backery;
	}
}
