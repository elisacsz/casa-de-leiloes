
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutoDAO {

    private Connection conn;
    private PreparedStatement prep;
    private ResultSet resultset;
    private ArrayList<Produto> listagem = new ArrayList<>();

    public ProdutoDAO() {
        this.conn = new conectaDAO().conectar();
    }

    public void cadastrarProduto(Produto produto) throws SQLException {
        if (produto.getNome() == null || produto.getNome().trim().isEmpty()
                || produto.getValor() == null) {
            throw new IllegalArgumentException("Preencha todos os campos corretamente.");
        }

        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?,?,?)";

        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());

            prep.executeUpdate();

            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + sqle.getMessage());
        }
        
        prep.close();
        conn.close();
    }

}
