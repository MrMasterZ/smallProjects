package reference.book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class ReferenceBookDivision {
	private static String[] codesDivisions = {"K1\\SK1", "K1\\SK2", "K1\\SK1\\SSK1", "K1\\SK1\\SSK2", "K2", "K2\\SK1\\SSK1", "K2\\SK1\\SSK2"};
	private static Set<String> codesDivisionsSet = new TreeSet<String>(); 
	private static ArrayList<String> codesDivisionsAr = new ArrayList<String>();
	private static int divisionRang = 0;    // максимальная вложенность подразделов (или максимальное количество слешей в String)

	public static void main(String args[]) {
		
// формируем первоначальный Set (Set для получения уникальных значений) (из самых верхних по иерархии элементов) для передачи его в рекурсивную функцию formDivisionAr, а также вычисляем степень вложенности подразделений 		
		for (String division : codesDivisions ) {
			codesDivisionsSet.add(division.split("\\\\")[0]);  
			int divLen = division.split("\\\\").length-1;
			if (divLen > divisionRang) divisionRang = divLen;
		}

		// Для того чтобы поменять порядок сортировки на обратный
		codesDivisionsAr.addAll(codesDivisionsSet);  
		Collections.reverse(codesDivisionsAr);
			
// Передаём в рекурсивный метод formDivisionAr именно ArrayList, чтобы не терялся порядок сортировки
		for (String division : formDivisionAr(1, codesDivisionsAr) ) {
		    System.out.println(division);
		}
	}

	public static ArrayList<String> formDivisionAr(int N,  ArrayList<String> codesDivisionsAr) {
      while (N<=divisionRang) {                             // ограничиваем количество итераций вложенностью подразделений
		
		Set<String> divisionsBufferSet = new TreeSet<String>();
		ArrayList<String> divisionsBufferAr = new ArrayList<String>();

// для того чтобы можно было переформировать полученный в параметрах массив при этом итерируя по нему создаём его копию
		divisionsBufferAr.addAll(codesDivisionsAr);          
		codesDivisionsAr.clear();
	
// получаем новые уникальные элементы, уровень вложенности которых больше на единицу и отсортированные TreeSet (порядок сортировки нам не подходит, в дальнейшем прийдётся изменить на обратный)
		for (String division : divisionsBufferAr ) {
		    for (String divisionElementStr : codesDivisions ) {
                String[] divSplit = divisionElementStr.split("\\\\");
                if (divSplit.length >= (N+1) && (( N==1 && divSplit[0].equals(division)) || ( N>1 && divisionElementStr.contains(division) && (division.split("\\\\").length==N))))
                    divisionsBufferSet.add(division + "\\" + divSplit[N]);    
            }
		}
		
// divisionsBufAr -- временный ArrayList (нужен только для того чтобы перевернуть полученный на предыдущем шаге Set (divisionsBufferSet)) 
		ArrayList<String> divisionsBufAr = new ArrayList<String>();
		divisionsBufAr.addAll(divisionsBufferSet);  
		Collections.reverse(divisionsBufAr);

// формируем результирующий для этой итерации (этого уровня вложенности) упорядоченный массив		
		for (String division : divisionsBufferAr ) {
		    for (String divisionElementSet : divisionsBufAr) {
			    if(!codesDivisionsAr.contains(division))
				    codesDivisionsAr.add(division);
// добавляем элемент из divisionsBufAr только тогда когда все элементы по иерархии ниже его уже созданы (чтобы это узнать вызываем метод formBaseWordDivision )
		        if((!codesDivisionsAr.contains(divisionElementSet)) && (codesDivisionsAr.contains(formBaseWordDivision(divisionElementSet))))
				    codesDivisionsAr.add(divisionElementSet);
		    }
		}

		N++;                                        
		formDivisionAr(N, codesDivisionsAr);  // переходим на следующую итерацию
		

      }	
		return codesDivisionsAr;
	}
	
// узнаём в каком подразделении находится наше подразделение (т.е. убираем после последнего слеша)
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