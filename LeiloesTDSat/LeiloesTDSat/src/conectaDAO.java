
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author elisa
 */
public class conectaDAO {
    
    public Connection conectar() {
        Connection conn = null;

        try {

            conn = DriverManager.getConnection("jdbc:mysql://localhost/casa_leiloes?user=root&password=010795");
            System.out.println("Conexão realizada com sucesso");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro na conexão." + erro.getMessage());
        }
        return conn;
    }
    

}
