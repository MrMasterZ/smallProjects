package reference.book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class ReferenceBookDivision {
	private static String[] codesDivisions = {"K1\\SK1", "K1\\SK2", "K1\\SK1\\SSK1", "K1\\SK1\\SSK2", "K2", "K2\\SK1\\SSK1", "K2\\SK1\\SSK2"};
	private static Set<String> codesDivisionsSet = new TreeSet<String>(); 
	private static ArrayList<String> codesDivisionsAr = new ArrayList<String>();
	private static int divisionRang = 0;    // ������������ ����������� ����������� (��� ������������ ���������� ������ � String)

	public static void main(String args[]) {
		
// ��������� �������������� Set (Set ��� ��������� ���������� ��������) (�� ����� ������� �� �������� ���������) ��� �������� ��� � ����������� ������� formDivisionAr, � ����� ��������� ������� ����������� ������������� 		
		for (String division : codesDivisions ) {
			codesDivisionsSet.add(division.split("\\\\")[0]);  
			int divLen = division.split("\\\\").length-1;
			if (divLen > divisionRang) divisionRang = divLen;
		}

		// ��� ���� ����� �������� ������� ���������� �� ��������
		codesDivisionsAr.addAll(codesDivisionsSet);  
		Collections.reverse(codesDivisionsAr);
			
// ������� � ����������� ����� formDivisionAr ������ ArrayList, ����� �� ������� ������� ����������
		for (String division : formDivisionAr(1, codesDivisionsAr) ) {
		    System.out.println(division);
		}
	}

	public static ArrayList<String> formDivisionAr(int N,  ArrayList<String> codesDivisionsAr) {
      while (N<=divisionRang) {                             // ������������ ���������� �������� ������������ �������������
		
		Set<String> divisionsBufferSet = new TreeSet<String>();
		ArrayList<String> divisionsBufferAr = new ArrayList<String>();

// ��� ���� ����� ����� ���� ��������������� ���������� � ���������� ������ ��� ���� �������� �� ���� ������ ��� �����
		divisionsBufferAr.addAll(codesDivisionsAr);          
		codesDivisionsAr.clear();
	
// �������� ����� ���������� ��������, ������� ����������� ������� ������ �� ������� � ��������������� TreeSet (������� ���������� ��� �� ��������, � ���������� �������� �������� �� ��������)
		for (String division : divisionsBufferAr ) {
		    for (String divisionElementStr : codesDivisions ) {
                String[] divSplit = divisionElementStr.split("\\\\");
                if (divSplit.length >= (N+1) && (( N==1 && divSplit[0].equals(division)) || ( N>1 && divisionElementStr.contains(division) && (division.split("\\\\").length==N))))
                    divisionsBufferSet.add(division + "\\" + divSplit[N]);    
            }
		}
		
// divisionsBufAr -- ��������� ArrayList (����� ������ ��� ���� ����� ����������� ���������� �� ���������� ���� Set (divisionsBufferSet)) 
		ArrayList<String> divisionsBufAr = new ArrayList<String>();
		divisionsBufAr.addAll(divisionsBufferSet);  
		Collections.reverse(divisionsBufAr);

// ��������� �������������� ��� ���� �������� (����� ������ �����������) ������������� ������		
		for (String division : divisionsBufferAr ) {
		    for (String divisionElementSet : divisionsBufAr) {
			    if(!codesDivisionsAr.contains(division))
				    codesDivisionsAr.add(division);
// ��������� ������� �� divisionsBufAr ������ ����� ����� ��� �������� �� �������� ���� ��� ��� ������� (����� ��� ������ �������� ����� formBaseWordDivision )
		        if((!codesDivisionsAr.contains(divisionElementSet)) && (codesDivisionsAr.contains(formBaseWordDivision(divisionElementSet))))
				    codesDivisionsAr.add(divisionElementSet);
		    }
		}

		N++;                                        
		formDivisionAr(N, codesDivisionsAr);  // ��������� �� ��������� ��������
		

      }	
		return codesDivisionsAr;
	}
	
// ����� � ����� ������������� ��������� ���� ������������� (�.�. ������� ����� ���������� �����)
	public static String formBaseWordDivision (String division) {
		String baseWordDivision = "";
		String[] baseWordSplit = division.split("\\\\");
		int partInd =  baseWordSplit.length-2;
		for (int i=0; i<partInd; i++)
			baseWordDivision=baseWordDivision +  baseWordSplit[i] + "\\";
		baseWordDivision=baseWordDivision+baseWordSplit[partInd];
		return baseWordDivision;
	}
}