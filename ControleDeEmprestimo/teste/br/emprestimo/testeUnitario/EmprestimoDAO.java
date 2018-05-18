package br.emprestimo.testeUnitario;

import java.util.ArrayList;

import br.emprestimo.modelo.Emprestimo;

public class EmprestimoDAO {
	 ArrayList<Emprestimo>emprestimos = new ArrayList<Emprestimo>();
	 
   public boolean adiciona(Emprestimo umEmprestimo){
	
	   
	 emprestimos.add(umEmprestimo);
	   
	return emprestimos.add(umEmprestimo);
   }
   public Emprestimo consulta(Emprestimo umEmprestimo){
	   for(Emprestimo emprestimo:emprestimos){
		   
		   if(emprestimos.contains(umEmprestimo)){
	          return emprestimo;
		   }
		   
	   }   
	   return null;
   }
}
