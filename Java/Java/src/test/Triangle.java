package test;

import java.io.IOException;

public class Triangle {

	private Point v1;
	private Point v2;
	private Point v3;

	public double getArea() { // 넓이
		double area = 0.0, s = 0.0;
		s = getPerimeter() / 2;
		area = Math.sqrt(s * (s - getLength(v1, v2)) * (s - getLength(v2, v3)) * (s - getLength(v1, v3)));

		return area;
	}

	public double getPerimeter() { // 둘레의 길이
		double pm = 0;

		pm = getLength(v1, v2) + getLength(v2, v3) + getLength(v1, v3);

		return pm;
	}

	public String getType() {
		String type = "";

		if (isRightTriangle())
			type = "It is a right angle triangle..";
		else if (isEquilatralTriangle() == true)
			type = "It is a Equilatral Triangle..";
		else if (isAcuteTriangle() == true)
			type = "It is a Acute Triangle..";

		// else

		return type;
	}

	// private로 getType에서 쓸 함수를 구현
	private boolean isRightTriangle() { // 직각삼각형인지 확인
		boolean RT = false;

		if ((getAngle(v1, v2, v3) == 90) || (getAngle(v2, v3, v1) == 90) || (getAngle(v3, v1, v2) == 90))
			RT = true;
		else
			RT = false;

		return RT;

	}

	private boolean isEquilatralTriangle() { // 정삼각형인지 확인
		boolean ET = false;
		if (getLength(v1, v2) == getLength(v2, v3) || getLength(v2, v3) == getLength(v3, v1)
				|| getLength(v3, v1) == getLength(v1, v2))
			ET = true;

		return ET;
	}

	public boolean isAcuteTriangle() { // 예각삼각형인지 확인
		boolean AT = false;
		if (getAngle(v1, v2, v3) < 90 && getAngle(v2, v3, v1) < 90 && getAngle(v3, v1, v2) < 90)
			AT = true;
		return AT;
	}

	// point 4개를 넣었을 때 앞의 두 점으로 연결된 선과 뒤의 두점으로 연결된 선이 평행인지 확인
	// 결국, 두 선분의 기울기가 같은지 확인하는 것
	private boolean isParallel(Point p1, Point p2, Point p3, Point p4) {
		if ((p1.y - p2.y) / (p1.x - p2.x) == (p3.y - p4.y) / (p3.x - p4.x))
			return true;
		else
			return false;
	}

	// point3개를 넣어서 가운데 point의 각을 구하는 함수 구현
	public static double getAngle(Point p1, Point p2, Point p3) {
		double angle = 0.0;
		double a = 0.0, b = 0.0;
		
		a = Math.atan((p1.y - p2.y) / (p1.x - p2.x));
		b = Math.atan((p3.y - p2.y) / (p3.x - p2.x));

		angle = Math.abs((a - b) * 180 / Math.PI);

		return angle;
	}

	public double getLength(Point p1, Point p2) {
		double len = 0;

		len = Math.sqrt(Math.pow((p1.x - p2.x), 2) + Math.pow((p1.y - p2.y), 2));

		return len;
	}

	public Triangle(Point v1, Point v2, Point v3) {
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
	}

	public Point getV1() {
		return v1;
	}

	public void setV1(Point v1) {
		this.v1 = v1;
	}

	public Point getV2() {
		return v2;
	}

	public void setV2(Point v2) {
		this.v2 = v2;
	}

	public Point getV3() {
		return v3;
	}

	public void setV3(Point v3) {
		this.v3 = v3;
	}

	@Override
	public String toString() {
		return "Triangle [v1=" + v1 + ", v2=" + v2 + ", v3=" + v3 + "]";
	}

	public static void main(String[] args) throws IOException {
		Point p1 = new Point(4,1);
		Point p2 = new Point(1,1);
		Point p3 = new Point(4,5);
		Triangle t1 = new Triangle(p1, p2, p3);
		
		Triangle t = new Triangle(new Point(4, 1), new Point(1, 1), new Point(4, 5));
		

		System.out.println(getAngle(t.v1, t.v2, t.v3));
		System.out.println("2");
		System.out.println(t.getType());
		System.out.println("3");
		System.out.println(t.getPerimeter());
		System.out.println("4");
		System.out.println(t.getArea());

	}
	
	public static void testTriangle() {
	      Triangle t = new Triangle(new Point(4,1), new Point(1,1),new Point(4,5));
	      
	      System.out.println(t.getAngle(new Point(6,9), new Point(0,0),new Point(7,5)));
	      System.out.println(t.getType());
	      System.out.println(t.getPerimeter());
	      System.out.println(t.getArea());
	      
	      
	   }

}