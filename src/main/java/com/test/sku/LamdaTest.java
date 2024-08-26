package com.test.sku;

interface MyInterface
{
	void printAdd(int a, int b);	//함수형 인터페이스
}

class MyInterImpl implements MyInterface	//인ㅅ터페이슬 구현한 완전한(이름을 가진)클래스
{
	@Override
	public void printAdd(int a,int b) {
		int ans = a + b;
		System.out.println("합계:"+ans);
	}
}
public class LamdaTest {
	public static void main(String[] args) {
		/*//익명클래스 선언/오버라이드/인스턴스 생성
		MyInterface mi = (a,b)->
		{			
				int ans = a + b;
				System.out.println("합계:"+ans);
				);
			
		};*/
		lambdaParam((a,b)->
		{			
			int ans = a + b;
			System.out.println("합계:"+ans);
		});
	}
	
	static void lambdaParam(MyInterface mi) {
		mi.printAdd(5, 8);
	}
	static void test1() {
		MyInterImpl mi = new MyInterImpl();
		mi.printAdd(3, 5);
	}


	public static void main1(String[] args) {
		//이름 없는 클래스 선언/오버라이드/인스턴스 생성
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				System.out.println("익명 클래스를 사용한 스레드가 실행 중입니다.");
				for(int i =0; i<5; i++) {
					System.out.println("i = "+i);
					try {
						Thread.sleep(400);
					}catch	(InterruptedException e){
						e.printStackTrace();
					}
				}
			}
			
		};
		new Thread(runnable).start();
	}
	static void lambdaExp() {
		//익명 클래스를 더 간략화한 람다(람다 표현식, Lamda Expression)
		//클래스 선언 / 메소드 오버라이드 / 인스턴스
		//람다 표현식을 적용할 수 있는 경우는 클래스 안에 메소드가 한개만 있는경우
		Runnable runnable = () ->{
			System.out.println("람다 표현식을 사용한 스레드가 실행 중입니다.");
			for(int i =0; i< 5; i++) {
				System.out.println("i = "+i);
				try {
					Thread.sleep(400);	//400a밀리초 동안 스레드 일시 중지
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		new Thread(runnable).start();
	}
	/*Functional Interface(함수형 인터페이스)
	 * 람다 표현식으로 인스턴스를 생성할 수 있다
	 */
	
	
	/**이름 있는 클래스*/
	class RunnableImpl implements Runnable
	{
		@Override
		public void run() {
		System.out.println("익명 클래스를 사용한 스레드가 실행 중입니다.");
		for(int i =0; i<5; i++) {
			System.out.println("i = "+i);
			try {
				Thread.sleep(400);
			}catch	(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
		
	}
}
