
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
    private ArrayList<Produto> listagemVendidos = new ArrayList<>();

    public ProdutoDAO() {
        this.conn = new conectaDAO().conectar();
    }

    public void cadastrarProduto(Produto produto) throws SQLException {
        if (produto.getNome() == null || produto.getNome().trim().isEmpty()
                || produto.getValor() == null) {
            throw new IllegalArgumentException("Preencha todos os campos.");
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

    public ArrayList<Produto> listarProdutos() throws SQLException {
        String sql = "SELECT * FROM produtos";

        try {
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            while (resultset.next()) {
                Produto produto = new Produto();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                listagem.add(produto);
            }
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + sqle.getMessage());
        }

        resultset.close();
        prep.close();
        conn.close();

        return listagem;
    }
    
        public ArrayList<Produto> listarProdutosVendidos() throws SQLException {
        String sql = "SELECT * FROM produtos WHERE status ='Vendido'";

        try {
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            while (resultset.next()) {
                Produto produto = new Produto();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));

                listagemVendidos.add(produto);
            }
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos vendidos: " + sqle.getMessage());
        }

        resultset.close();
        prep.close();
        conn.close();

        return listagemVendidos;
    }

    public void venderProduto(int id) {
        String sql = "UPDATE produtos SET status = ? WHERE id = ?";

        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, "Vendido");
            prep.setInt(2, id);

            int linhas = prep.executeUpdate();
            if (linhas > 0) {
                JOptionPane.showMessageDialog(null, "Venda do produto realizada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Insira um produto válido.");
            }
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar produto: " + sqle.getMessage());
        }
    }

}
