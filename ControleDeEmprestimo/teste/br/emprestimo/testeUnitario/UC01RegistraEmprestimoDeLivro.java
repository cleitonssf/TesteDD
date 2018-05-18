package br.emprestimo.testeUnitario;
import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.emprestimo.modelo.Emprestimo;
import br.emprestimo.modelo.Livro;
import br.emprestimo.modelo.Usuario;
import br.emprestimo.servico.ServicoEmprestimo;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class UC01RegistraEmprestimoDeLivro {
	static private Livro livro;
	static private Usuario usuario;
	static private ServicoEmprestimo servico;
	static private Emprestimo emprestimo;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//cenario
		livro = ObtemLivro.comDadosValidos();
		
		usuario = new Usuario();
		usuario.setRa("11111");
		usuario.setNome("Jose da Silva");
		servico = new ServicoEmprestimo();
		emprestimo = new Emprestimo();
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	@Test
	public void CT01UC01FB_registrar_emprestimo_com_sucesso() {
		assertNotNull(servico.empresta(livro, usuario));
	}
	@Test(expected=RuntimeException.class)
	public void CT02UC01FB_registrar_emprestimo_com_dados_invalidos() {
		servico.empresta(null, usuario);
	}
	@Test
	public void CT03UC01FB_registrar_emprestimo_com_dados_invalidos(){
		try{
			servico.empresta(null, usuario);
			fail ("deveria lançar uma exceção");
		}catch(RuntimeException e){
			assertEquals("Dados inválidos.", e.getMessage());
		}
	}
	@Test
	public void CT04UC01FB_registrar_emprestimo_com_sucesso_validacao_da_data() {
		//cenario
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/YYYY");
		String dataEsperada = new DateTime().plusDays(8).toString(fmt);
		//acao
		emprestimo = servico.empresta(livro, usuario);
		String dataObtida = emprestimo.getDataDevolucao();
		//verificacao
		assertTrue(dataEsperada.equals(dataObtida));
	}
	@Test
	public void CT05UC01FB_registrar_emprestimo_com_data_invalida() {
		assertTrue(emprestimo.validaData("29/03/2000"));
	}
	
	@Test
	public void CT06UC01FB_registrar_emprestimo_com_data_invalida() {
		Emprestimo emprestimo2 = new Emprestimo();
		try{
		emprestimo2.setDataEmprestimo("30/02/2000");
		fail ("deveria lançar uma exceção");
		} catch (Exception e){
			assertEquals("Data invalida", e.getMessage());
		}
	}
	@Test(expected = RuntimeException.class)
	public void CT07UC01RegistrarEmprestimo_com_data_invalida() {
		emprestimo.setDataEmprestimo("30/02/2000");
	}
	@Test
	public void CT08UC01RegistrarEmprestimo_obtem_data_corrente(){
		//cenario
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/YYYY");
		String dataAtual = new DateTime().toString(fmt);
		//acao
		String dataEmprestimo = emprestimo.setDataEmprestimo();
		assertTrue(dataAtual.equals(dataEmprestimo));
	}
	
	@Test
	public void CT09QuandoDataDevolucaoForDomingoErro(){
		//cenário
		String data = "06/05/2018";
		Emprestimo umEmprestimo = new Emprestimo();
		//ação
		boolean ehDomingo = umEmprestimo.verificaDiaSemana(data);
		//verificação
		assertFalse(ehDomingo);
	}
	
	@Test
	public void CT06QuandoInserirUmEmprestimoRetorna1(){
		//cenário
		Emprestimo umEmprestimo = new Emprestimo();
		Usuario umUsuario = ObtemUsuario.comDadosValidos();
		Livro umLivro = ObtemLivro.comDadosValidos();
		ServicoEmprestimo servico = new ServicoEmprestimo();
		umEmprestimo = servico.empresta(umLivro, umUsuario);
		
		EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
		//acao
		boolean resultadoEsperado=emprestimoDAO.adiciona(umEmprestimo);
		//verificacao
		assertTrue(resultadoEsperado);
	}
	
	@Test
	public void CT07ConsultaRegistroComSucesso(){
		//cenário
		Emprestimo umEmprestimo = new Emprestimo();
		Usuario umUsuario = ObtemUsuario.comDadosValidos();
		Livro umLivro = ObtemLivro.comDadosValidos();
		ServicoEmprestimo servico = new ServicoEmprestimo();
		umEmprestimo = servico.empresta(umLivro, umUsuario);	
		EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
		emprestimoDAO.adiciona(umEmprestimo);
		
		//insere 1 registro
		umUsuario = ObtemUsuario.ListacomDadosValidos.get(1);
		umEmprestimo = servico.empresta(umLivro, umUsuario);	
		EmprestimoDAO emprestimoDAO1 = new EmprestimoDAO();
		emprestimoDAO.adiciona(umEmprestimo);
		
		//insere 2 registro
		umUsuario = ObtemUsuario.ListacomDadosValidos.get(2);
		umEmprestimo = servico.empresta(umLivro, umUsuario);	
		EmprestimoDAO emprestimoDAO2 = new EmprestimoDAO();
		emprestimoDAO.adiciona(umEmprestimo);
		
		//insere 3 registro
		umUsuario = ObtemUsuario.ListacomDadosValidos.get(3);
		umEmprestimo = servico.empresta(umLivro, umUsuario);	
		EmprestimoDAO emprestimoDAO3 = new EmprestimoDAO();
		emprestimoDAO1.adiciona(umEmprestimo);
		
		//insere 4 registro
		umUsuario = ObtemUsuario.ListacomDadosValidos.get(4);
		umEmprestimo = servico.empresta(umLivro, umUsuario);	
		EmprestimoDAO emprestimoDAO4 = new EmprestimoDAO();
		emprestimoDAO4.adiciona(umEmprestimo);
				
		//acao
		Emprestimo resultadoObtido = emprestimoDAO.consulta(umEmprestimo.getUsuario().getRa());
		assertFalse(resultadoObtido.equals(umEmprestimo));
	}
}









