package backeryProject.model;

import java.util.List;

import backeryProject.vo.BackeryVO;
import backeryProject.vo.MemberVO;

public class BackeryService {
	BackeryDAO backeryDao = new BackeryDAO();
	
	//[고객용] 1.모든 빵 조회
	public List<BackeryVO> selectMenu(){
		return backeryDao.selectMenu();
	}
	
	//[고객용] 2.실시간 빵 목록 조회
	public List<BackeryVO> selectALL(){
		return backeryDao.selectALL();
	}

	//[고객용]3.실시간 빵 목록 카테고리별 조회
	public List<BackeryVO> selectCategory(String category){
		return backeryDao.selectCategory(category);
	}
	
	//[고객용]4.빵 이름 별 목록
	public List<BackeryVO> selectName(String name){
		return backeryDao.selectName(name);
	}
	
	//[고객용]4-1.빵 이름 별 목록 조회 시 없는 빵을 조회했는지 확인하기
	public int isExistBreadName(String bread_name) {
		return backeryDao.isExistBreadName(bread_name);
	}
	
	//[고객용]5.회원 주문(마이페이지)
	public String order(MemberVO member) {
		int result = backeryDao.order(member);
		return result > 0 ? "주문 완료되었습니다." : "주문에 실패하였습니다.";
	}
	
	//[고객용]5.회원 주문 - 빵 이름 입력시 빵 ID 반환
	public int selectBreadID(String bread_name) {
		return backeryDao.selectBreadID(bread_name);
	}
	
	//[고객용]5.회원 주문 - 고객이 같은 빵을 주문하려하면 수량만 수정하도록 안내
	public int alreadyExistBreadorder(String name, int id) {
		return backeryDao.alreadyExistBreadorder(name, id);
	}
	
	//[고객용]5. 고객 개인별 자신의 주문 데이터 조회하기
	public List<MemberVO> checkMyorder(String name){
		return backeryDao.checkMyorder(name);
	}
	
	//[고객용]5.회원 주문 - 고객이 입력한 빵이 주문가능한 빵인지 확인
	public int alreadyExistBreadName(String bread_name) {
		return backeryDao.alreadyExistBreadName(bread_name);
	}
	
	//[고객용]5. 주문 수량 수정하기(update)
	public String orderUpdate(int quantity, int orderID) {
		int result = backeryDao.orderUpdate(quantity, orderID);
		return result > 0 ? "수량 변경에 성공하였습니다." : "수량 변경에 실패하였습니다.";
	}
	
	//[고객용]5.주문 취소
	public String orderDelete(int orderID) {
		int result = backeryDao.orderDelete(orderID);
		return result > 0 ? "주문 취소에 성공하였습니다." : "주문 취소에 실패하였습니다.";
	}
	
	//[고객용] 수량확인
	public int checkQuantity(String bread_name) {
		return backeryDao.checkQuantity(bread_name);
	}
	
	public int checkQuantity(int bread_id) {
		return backeryDao.checkQuantity(bread_id);
	}
	
	public int checkOrderQuantity(int bread_id) {
		return backeryDao.checkOrderQuantity(bread_id);
	}
	
	//프로시저
	public String newDaySales() {
		return backeryDao.newDaySales();
	}
	
	//[고객용]6.비회원 회원가입
	public String signUp(MemberVO member) {
		int result = backeryDao.signUp(member);
		return result > 0 ? "회원가입에 성공하였습니다." : "회원가입에 실패하였습니다.";
	}
	
	//[고객용]6-1.이미 존재하는 회원인지 확인하기
	public int alreadyMember (String name) {
		return backeryDao.alreadyMember(name);
	}
	
	//[관리자용]1.전체 빵 목록 조회
	public List<BackeryVO> selectMenuforManager(){
		return backeryDao.selectMenuforManager();
	}
	
	//[관리자용]2.갓 구운 빵 등록
	public String registBread(BackeryVO bread) {
		int result = backeryDao.registBread(bread);
		return result > 0 ? "빵 등록에 성공하였습니다" : "빵 등록에 실패하였습니다.";
	}
	
	//[관리자용]2-1. 갓 구운 빵 등록시 존재하는 빵 ID인지 확인
	public int isExistBread(String bread_id) {
		return backeryDao.isExistBread(bread_id);
	}
	
	//[관리자용]2-2.갓 구운 빵 등록시 이미 등록한 빵인지 확인
	public int alreadyExistBread(String bread_id) {
		return backeryDao.alreadyExistBread(bread_id);
	}
	
	//[관리자용]3. 오늘의 매출 확인
	public String todaySales(){
		return backeryDao.todaySales();
	}
}
