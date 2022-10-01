package br.com.cotiinformatica.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.factories.ConnectionFactory;

public class UsuarioRepository {

	// método para inserir um usuário no banco de dados
	public void create(Usuario usuario) throws Exception {

		// abrindo conexão com o banco de dados
		Connection connection = ConnectionFactory.createConnection();

		// executando um comando INSERT no banco de dados
		PreparedStatement statement = connection
				.prepareStatement("insert into usuario(nome, email, senha) values(?,?,md5(?))");
		statement.setString(1, usuario.getNome());
		statement.setString(2, usuario.getEmail());
		statement.setString(3, usuario.getSenha());
		statement.execute();

		// fechando a conexão com o banco de dados
		connection.close();
	}
	
	// método para atualizar a senha de um usuário
	public void update(Integer idUsuario, String novaSenha) throws Exception {
		
		// abrindo conexão com o banco de dados
		Connection connection = ConnectionFactory.createConnection();
		
		// executando um comando UPDATE no banco de dados
		PreparedStatement statement = connection
				.prepareStatement("update usuario set senha = md5(?) where idusuario = ?");
		statement.setString(1, novaSenha);
		statement.setInt(2, idUsuario);
		statement.execute();
		
		connection.close();
	}	

	// método para consultar 1 usuário no banco de dados através do email
	public Usuario findByEmail(String email) throws Exception {

		// abrindo conexão com o banco de dados
		Connection connection = ConnectionFactory.createConnection();

		// executando um comando SELECT no banco de dados
		PreparedStatement statement = connection.prepareStatement("select * from usuario where email = ?");
		statement.setString(1, email);
		ResultSet resultSet = statement.executeQuery();

		Usuario usuario = null;

		if (resultSet.next()) {

			usuario = new Usuario();

			usuario.setIdUsuario(resultSet.getInt("idusuario"));
			usuario.setNome(resultSet.getString("nome"));
			usuario.setEmail(resultSet.getString("email"));
			usuario.setSenha(resultSet.getString("senha"));
		}

		connection.close();
		return usuario;
	}

	// método para consultar 1 usuário no banco de dados através do email e da senha
	public Usuario findByEmailAndSenha(String email, String senha) throws Exception {

		// abrindo conexão com o banco de dados
		Connection connection = ConnectionFactory.createConnection();

		// executando um comando SELECT no banco de dados
		PreparedStatement statement = connection.prepareStatement("select * from usuario where email = ? and senha = md5(?)");
		statement.setString(1, email);
		statement.setString(2, senha);
		ResultSet resultSet = statement.executeQuery();

		Usuario usuario = null;

		if (resultSet.next()) {

			usuario = new Usuario();

			usuario.setIdUsuario(resultSet.getInt("idusuario"));
			usuario.setNome(resultSet.getString("nome"));
			usuario.setEmail(resultSet.getString("email"));
			usuario.setSenha(resultSet.getString("senha"));
		}

		connection.close();
		return usuario;
	}

}
