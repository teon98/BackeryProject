package backeryProject.view;

import java.text.SimpleDateFormat;
import java.util.*;

import backeryProject.vo.BackeryVO;
import backeryProject.vo.MemberVO;

public class BackeryView {
	public static void printMenu(List<BackeryVO> backerylist) {
		System.out.println("---------------------<빵 목록>----------------------");
		System.out.println("빵 이름\t\t|가격\t|카테고리");
		System.out.println("---------------------------------------------------");
		
		for(BackeryVO backery : backerylist) {
			System.out.printf("%-8s\t|%d\t|%s\n",
					backery.getBackery_name(),backery.getPrice(), backery.getCategory());
		}
		
		System.out.println(" ");
	}
	
	public static void printRealList(List<BackeryVO> backerylist) {
		System.out.println("--------------------<실시간 빵 목록>--------------------------------");
		System.out.println("빵 이름\t\t|가격\t|만든 시간\t\t|수량\t|카테고리");
		System.out.println("----------------------------------------------------------------");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		
		if(backerylist.size() < 1) {
			System.out.println("(조회하신 빵은 준비중에 있습니다.)");
		}else {
			for(BackeryVO backery : backerylist) {
				
				System.out.printf("%-8s\t|%-5d\t|%-10s\t|%d\t|%-5s\n",
						backery.getBackery_name(), backery.getPrice(),
						format.format(backery.getCreate_date()), backery.getQuantity(),
						backery.getCategory());
			}			
		}
		
		System.out.println(" ");
	}
	
	public static void printMenuforManager(List<BackeryVO> backerylist) {
		System.out.println("--------------------<빵 목록: 수량확인>------------------------");
		System.out.println("빵 아이디\t|빵 이름\t\t|수량\t|가격\t|카테고리");
		System.out.println("---------------------------------------------------------");
				
		for(BackeryVO backery : backerylist) {
			
			System.out.printf("%s\t|%-8s\t|%d\t|%d\t|%s\n",
					backery.getBackery_id(),backery.getBackery_name(), 
					backery.getQuantity(), backery.getPrice(),
					backery.getCategory());
		}
		
		System.out.println(" ");
	}
	
	public static void printCheckMyOrder(List<MemberVO> orderlist) {
		System.out.println("--------------------<주문 확인하기>------------------------");
		System.out.println("주문 번호\t|빵 이름\t\t|수량\t|가격\t");
		System.out.println("---------------------------------------------------------");
		
		if(orderlist.size() < 1) {
			System.out.println("(주문 내역이 없습니다.)");
		}else {
			for(MemberVO member: orderlist) {
				System.out.printf("%d\t|%-8s\t|%d\t|%d\n",
						member.getOrder_id(),member.getBackery_name(), 
						member.getOrder_quantity(), member.getPrice()
						);
			}
		}
		
		System.out.println(" ");
	}
	
	public static void printMsg(String msg) {
		System.out.println("[알림]" + msg);
	}
}
