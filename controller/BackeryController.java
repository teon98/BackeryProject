package backeryProject.controller;

import java.util.Scanner;

import backeryProject.model.BackeryService;
import backeryProject.view.BackeryView;
import backeryProject.vo.BackeryVO;
import backeryProject.vo.MemberVO;

public class BackeryController {

	public static void main(String[] args) {
		BackeryService bs = new BackeryService();
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("/////////////////--------Welcome--------/////////////////");
		System.out.println("/////////  Real Time Bread Reservation Program  /////////");
		System.out.println("/////////////////////////////////////////////////////////");
		
		while(true) {
		System.out.println(" ");
		System.out.println("Select User Type ========================================");
		System.out.println("1.관리자 | 2.이용자");
		System.out.println("=========================================================");
		System.out.print("사용자 유형을 선택하세요 >>");
		String user_type = sc.next();
		
		if(user_type.equals("1")) {
			while(true) {
				System.out.println(" ");
				System.out.println("Select Menu =============================================");
				System.out.println("[전체 빵 조회] | 1.전체 빵 목록 조회");
				System.out.println("[실시간 빵 등록] | 2. 빵 등록하기");
				System.out.println("[매출 관리] | 3.실시간 매출 확인하기");
				System.out.println("[영업 마감] | 4.영업 마감");
				System.out.println("[서비스 종료하기] | 5.서비스 종료");
				System.out.println("=========================================================");
				System.out.print("작업을 선택하세요 >>");
				String manager_job = sc.next();
				
				if(manager_job.equals("5")) break; //고객 서비스 종료
				switch(manager_job) {
				case "1":
					BackeryView.printMenuforManager(bs.selectMenuforManager());
					break;
				case "2":
					System.out.print("빵 ID를 입력하세요 >>");
					String bread_id = sc.next();
					
					if(bs.isExistBread(bread_id) < 1) {
						BackeryView.printMsg("존재하지 않은 빵 ID입니다.");
						break;
					}
					
					if(bs.alreadyExistBread(bread_id) >=1 ) {
						BackeryView.printMsg("이미 등록 완료된 빵입니다. 재 등록 하실 수 없습니다.");
						break;
					}
					
					System.out.print("수량을 입력하세요 >>");
					int quantity = sc.nextInt();
					
					BackeryVO bread = new BackeryVO();
					bread.setBackery_id(bread_id);
					bread.setQuantity(quantity);
					BackeryView.printMsg(bs.registBread(bread));
					break;
				case "3":
					System.out.println(bs.todaySales()); 
					break;
				case "4":
					System.out.print("영업을 새로 시작하시겠습니까?(Y/N)>>"); 
					String check = sc.next();
					if (check.equals("Y")) {
						BackeryView.printMsg(bs.newDaySales());
						break;
					}
					break;
				default:
					System.out.println("[알림]올바른 작업명을 입력해주세요");
					break;
				}
			}
			System.out.println("이용해 주셔서 감사합니다.");
		}
		else if(user_type.equals("2")) {
			while(true) {
				System.out.println(" ");
				System.out.println("Select Menu =============================================");
				System.out.println("[전체 빵 조회] | 1.전체 빵 목록 조회");
				System.out.println("[실시간 빵 조회] | 2.일반조회 3.카테고리별 조회 4.이름 조회 ");
				System.out.println("[빵 주문하기] | 5.회원 주문(마이페이지) 6.비회원 회원가입");
				System.out.println("[서비스 종료하기] | 7.서비스 종료");
				System.out.println("=========================================================");
				System.out.print("작업을 선택하세요 >>");
				String customer_job = sc.next();
				if(customer_job.equals("7")) break; //고객 서비스 종료
				switch(customer_job) {
				case "1":
					BackeryView.printMenu(bs.selectMenu());
					break;
				case "2":
					BackeryView.printRealList(bs.selectALL());
					break;
				case "3":
					System.out.println("<카테고리 목록>");
					System.out.println("단과자 | 소시지 | 식빵 | 페스츄리 | 케잌");
					System.out.print("검색하고 싶은 카테고리를 입력하세요 >>");
					String category = sc.next();
					if(category.equals("단과자") || category.equals("소시지") || category.equals("식빵") ||
						category.equals("페스츄리") || category.equals("케잌")) {
						BackeryView.printRealList(bs.selectCategory(category));
					}else {
						System.out.println("[알림]분류되지 않은 카테고리 명입니다.");
					}
					break;
				case "4":{
					System.out.print("검색하고 싶은 빵 이름을 입력하세요>>");
					String name = sc.next();
					
					if(bs.isExistBreadName(name) == 0) {
						System.out.println("[알림]매장에서 판매되지 않은 빵입니다.");
						break;
					}
					BackeryView.printRealList(bs.selectName(name));
					break;}
				case "5":{
					System.out.print("아이디를 입력하세요>>");
					String name = sc.next();
					
					if(bs.alreadyMember(name) == 0) {
						BackeryView.printMsg("아이디가 존재하지 않습니다. 다시 입력하세요.");
						break;
					}else {
						while(true) {
							System.out.println(" ");
							System.out.println(name + " 님 환영합니다===============================================");
							System.out.println("1.주문하기 | 2.주문확인하기 | 3.주문수정하기 | 4.주문취소하기 | 5.작업종료");
							System.out.println("===============================================================");
							System.out.print("작업을 선택하세요 >>");
							String memberJob = sc.next();
							
							if (memberJob.equals("5")) break;
							
							switch(memberJob) {
							case "1":
								System.out.print("주문하실 빵의 이름을 입력하세요>>");
								String bread_name = sc.next();
								
								if(bs.isExistBreadName(bread_name) == 0) {
									System.out.println("[알림]매장에서 판매되지 않은 빵입니다.");
									break;
								}
								
								if(bs.alreadyExistBreadName(bread_name) == 0) {
									BackeryView.printMsg("주문하신 빵은 준비중에 있습니다.");
									break;
								}
								
								System.out.print("주문하실 빵의 수량을 입력하세요>>");
								int quantity = sc.nextInt();
								
								if(quantity > bs.checkQuantity(bread_name)) {
									BackeryView.printMsg("준비된 빵의 개수보다 더 큰 수량을 입력하였습니다.");
									break;
								}
								
								if(bs.alreadyExistBreadorder(name, bs.selectBreadID(bread_name)) > 0){
									BackeryView.printMsg("이미 주문 완료된 빵입니다.");
									BackeryView.printMsg("수량 변경을 원할시 '주문수정하기' 작업을 이용해주세요");
									break;
								}
								
								MemberVO member = new MemberVO();
								
								member.setMember_name(name);
								member.setTodaybackery_id(bs.selectBreadID(bread_name));
								member.setOrder_quantity(quantity);
								BackeryView.printMsg(bs.order(member));
								break;
							case "2":
								BackeryView.printCheckMyOrder(bs.checkMyorder(name));
								break;
							case "3":{
								BackeryView.printCheckMyOrder(bs.checkMyorder(name));
								System.out.print("변경하실 주문 번호를 입력하세요>>");
								int orderID = sc.nextInt();
								System.out.print("변경하실 수량을 입력하세요>>");
								int updateQuantity = sc.nextInt();
								
								if( (updateQuantity - bs.checkOrderQuantity(orderID)) > bs.checkQuantity(orderID)) {
									BackeryView.printMsg("준비된 빵의 개수보다 더 큰 수량을 입력하였습니다.");
									break;
								}
								
								BackeryView.printMsg(bs.orderUpdate(updateQuantity, orderID));
								BackeryView.printCheckMyOrder(bs.checkMyorder(name));
								break;}
							case "4":{
								BackeryView.printCheckMyOrder(bs.checkMyorder(name));
								System.out.print("취소하실 주문 번호를 입력하세요>>");
								int orderID = sc.nextInt();
								
								BackeryView.printMsg(bs.orderDelete(orderID));
								BackeryView.printCheckMyOrder(bs.checkMyorder(name));
								}
								break;
							default: 
								System.out.println("올바른 작업명을 입력해주세요");
								break;
							}
						}
					}
					break;
					}
				case "6":{
					System.out.print("ID>> ");
					String name = sc.next();
					
					if(bs.alreadyMember(name) != 0) {
						BackeryView.printMsg("이미 존재하는 아이디입니다.");
						break;
					}
					
					System.out.print("PW>> ");
					String pw = sc.next();
					System.out.print("PHONE>> ");
					String phone = sc.next();
					
					MemberVO member = new MemberVO();
					member.setMember_name(name);
					member.setMember_pw(pw);
					member.setMember_number(phone);
					
					BackeryView.printMsg(bs.signUp(member));
					break;}
				default:
					System.out.println("올바른 작업명을 입력해주세요");
					break;
				}
			}
			System.out.println("이용해 주셔서 감사합니다.");
		}else {
			System.out.println("프로그램을 종료합니다.");
			break;
		}}
	}

}
