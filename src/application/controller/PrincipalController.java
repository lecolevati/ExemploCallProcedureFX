package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.sql.SQLException;

import application.model.Cliente;
import application.persistence.ClienteDao;
import application.persistence.IClienteDao;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.Tab;

public class PrincipalController {
	@FXML
	private Tab tabCliente;
	@FXML
	private TextField tfCpf;
	@FXML
	private Label lblCpf;
	@FXML
	private TextField tfNome;
	@FXML
	private TextField tfLogradouro;
	@FXML
	private TextField tfNumero;
	@FXML
	private Label lblNome;
	@FXML
	private Label lblNumero;
	@FXML
	private Button btnBuscar;
	@FXML
	private Button btnCadastrar;
	@FXML
	private Button btnAtualizar;
	@FXML
	private Button btnExcluir;
	@FXML
	private Tab newTab;

	// Event Listener on Button[#btnBuscar].onAction
	@FXML
	public void acaoCliente(ActionEvent event) {
		Cliente cli = new Cliente();
		cli.setCpf(tfCpf.getText());
		cli.setNome(tfNome.getText());
		cli.setLogradouro(tfLogradouro.getText());
		if (!tfNumero.getText().trim().isEmpty()) {
			cli.setNumero(Integer.parseInt(tfNumero.getText()));
		}
		
		IClienteDao cDao = null;
		try {
			cDao = new ClienteDao();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		String cmd = event.getSource().toString();
		if (cmd.contains("Cadastrar")) {
			boolean valido = validaCampos(cli, cmd);
			if (valido) {
				try {
					String saida = cDao.insereCliente(cli);
					
					Alert alert = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
					alert.setHeaderText(saida);
					alert.setTitle("Sucesso");
					alert.showAndWait();
					
					limpaCampos();
				} catch (SQLException e) {
					Alert alert = new Alert(AlertType.ERROR, "", ButtonType.OK);
					alert.setHeaderText(e.getMessage());
					alert.setTitle("Erro");
					alert.showAndWait();
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR, "", ButtonType.OK);
				alert.setHeaderText("Preencha os campos");
				alert.setTitle("Erro");
				alert.showAndWait();
			}
		}
		if (cmd.contains("Atualizar")) {
			boolean valido = validaCampos(cli, cmd);
			if (valido) {
				try {
					String saida = cDao.atualizaCliente(cli);
					
					Alert alert = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
					alert.setHeaderText(saida);
					alert.setTitle("Sucesso");
					alert.showAndWait();
					
					limpaCampos();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR, "", ButtonType.OK);
				alert.setHeaderText("Preencha os campos");
				alert.setTitle("Erro");
				alert.showAndWait();
			}
		}
		if (cmd.contains("Excluir")) {
			boolean valido = validaCampos(cli, cmd);
			if (valido) {
				try {
					String saida = cDao.excluiCliente(cli);
					
					Alert alert = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
					alert.setHeaderText(saida);
					alert.setTitle("Sucesso");
					alert.showAndWait();
					limpaCampos();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR, "", ButtonType.OK);
				alert.setHeaderText("Preencha o CPF");
				alert.setTitle("Erro");
				alert.showAndWait();
			}
		}
		if (cmd.contains("Buscar")) {
			boolean valido = validaCampos(cli, cmd);
			if (valido) {
				try {
					cli = cDao.consultaCliente(cli);
					tfNome.setText(cli.getNome());
					tfLogradouro.setText(cli.getLogradouro());
					tfNumero.setText(String.valueOf(cli.getNumero()));
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR, "", ButtonType.OK);
				alert.setHeaderText("Preencha o CPF");
				alert.setTitle("Erro");
				alert.showAndWait();
			}
		}
	}

	private void limpaCampos() {
		tfCpf.setText("");
		tfNome.setText("");
		tfLogradouro.setText("");
		tfNumero.setText("");
	}
	
	private boolean validaCampos(Cliente cli, String cmd) {
		if (cmd.contains("Cadastrar") || cmd.contains("Atualizar")) {
			if (cli.getCpf().trim().isEmpty() ||
				cli.getNome().trim().isEmpty() ||
				cli.getLogradouro().trim().isEmpty()
				) {
				return false;
			} else {
				return true;
			}
		}
		if (cmd.contains("Excluir") || cmd.contains("Buscar")) {
			if (cli.getCpf().trim().isEmpty()) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
}
