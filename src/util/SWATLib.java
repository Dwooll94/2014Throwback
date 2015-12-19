package util;

public class SWATLib {

	private static double m_angle1;
	private static double m_angle2;
	
	public static double convertTo2DVector(double angle1, double angle2){
		m_angle1 = angle1;
		m_angle2 = angle2;
		
		return Math.sqrt(Math.pow(m_angle1, 2)) + (Math.pow(m_angle2, 2));
	}
	
	public static double average(double num1, double num2){
		return (num1 + num2)/2;
	}
	
}
