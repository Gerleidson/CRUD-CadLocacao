import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Calendar;

public class cadastro {
    private JPanel Main;
    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtTel;
    private JTextField txtAno;
    private JTextField txtNomeQuadra;
    private JTextField txtTipoQuadra;
    private JTextField txtValorMinuto;
    private JTextField txtTempoMinutos;
    private JTextField txtNecessita;
    private JTextField txtValorCalc;
    private JButton salvarButton;
    private JButton alterarButton;
    private JButton apagarButton;
    private JButton pesquisarButton;
    private JTextField txtId;
    private JTable table1;


    public static void main(String[] args) {
        JFrame frame = new JFrame("Cadastro de Locação");
        frame.setContentPane(new cadastro().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;

    public void conexao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/cadlocacao", "root","root");
            System.out.println("Conexão Ok");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    void table_load(){
        try
        {
            pst = con.prepareStatement("select * from cadastro");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public cadastro() {
        conexao();
        table_load();

        salvarButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String Nome, CPF, Telefone, NomeQuadra, TipoQuadra, Ano, ValorMinuto, TempoMinuto,NecessitaEquipamento,ValorCalculado,calc;

                Nome = txtNome.getText();
                CPF = txtCpf.getText();
                Telefone = txtTel.getText();
                Ano = txtAno.getText();
                NomeQuadra = txtNomeQuadra.getText();
                TipoQuadra = txtTipoQuadra.getText();
                ValorMinuto = txtValorMinuto.getText();
                TempoMinuto = txtTempoMinutos.getText();
                NecessitaEquipamento = txtNecessita.getText();
                ValorCalculado = Double.toString(Double.parseDouble(ValorMinuto) * Double.parseDouble(TempoMinuto));

                // Verifica se NecessitaEquipamento é igual a "Sim"
                if (NecessitaEquipamento.equalsIgnoreCase("Sim")) {
                    ValorCalculado = Double.toString(Double.parseDouble(ValorCalculado) + 50.00);
                }

                // Aplica desconto de 10% para locações com mais de 2 horas (120 minutos)
               /* if (TempoMinuto > 120) {
                    ValorCalculado *= 0.9; // Aplica desconto de 10%
                }
*/
                // Após inserir os dados no banco de dados
                TelaInformacoes tela = new TelaInformacoes(Nome, CPF, Telefone, Ano, NomeQuadra, TipoQuadra, ValorMinuto, TempoMinuto, NecessitaEquipamento, ValorCalculado);
                tela.setVisible(true);


                try {
                    int idade = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(Ano);
                    if (idade >= 18) {
                        pst = con.prepareStatement("insert into cadastro(Nome,CPF,Telefone,Ano,NomeQuadra,TipoQuadra,ValorMinuto,TempoMinuto,NecessitaEquipamento,ValorCalculado)values(?,?,?,?,?,?,?,?,?,?)");
                        pst.setString(1, Nome);
                        pst.setString(2, CPF);
                        pst.setString(3, Telefone);
                        pst.setString(4, Ano);
                        pst.setString(5, NomeQuadra);
                        pst.setString(6, TipoQuadra);
                        pst.setString(7, ValorMinuto);
                        pst.setString(8, TempoMinuto);
                        pst.setString(9, NecessitaEquipamento);
                        pst.setString(10, ValorCalculado);
                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Salvar as informações?");
                        table_load();
                        txtNome.setText("");
                        txtCpf.setText("");
                        txtTel.setText("");
                        txtAno.setText("");
                        txtNomeQuadra.setText("");
                        txtTipoQuadra.setText("");
                        txtValorMinuto.setText("");
                        txtTempoMinutos.setText("");
                        txtNecessita.setText("");
                        txtValorCalc.setText(ValorCalculado);
                        txtNome.requestFocus();
                    } else {
                        JOptionPane.showMessageDialog(null, "Apenas maiores de 18 anos podem se cadastrar!");
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }


            class TelaInformacoes extends JFrame {
                public TelaInformacoes(String nome, String cpf, String telefone, String ano, String nomeQuadra, String tipoQuadra, String valorMinuto, String tempoMinuto, String necessitaEquipamento, String valorCalculado) {
                    // Configurações da janela
                    setTitle("Informações cadastradas");
                    setSize(500, 200);
                    setLocationRelativeTo(null);
                    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                   // Criação dos componentes para exibir as informações
                    JLabel labelNome = new JLabel("Nome: " + nome);
                    JLabel labelCpf = new JLabel("| CPF: " + cpf);
                    JLabel labelTelefone = new JLabel("| Telefone: " + telefone);
                    JLabel labelAno = new JLabel("| Ano de nascimento: " + ano);
                    JLabel labelNomeQuadra = new JLabel("| Nome da quadra: " + nomeQuadra);
                    JLabel labelTipoQuadra = new JLabel("| Tipo da quadra: " + tipoQuadra);
                    JLabel labelValorMinuto = new JLabel("| Valor por minuto: R$ " + valorMinuto);
                    JLabel labelTempoMinuto = new JLabel("|  Tempo de locação (minutos): " + tempoMinuto);
                    JLabel labelNecessitaEquipamento = new JLabel("| Necessita equipamento: " + necessitaEquipamento);
                    JLabel labelValorCalculado = new JLabel("| Valor calculado: R$ " + valorCalculado);

                    // Adiciona os componentes na janela
                    JPanel panel = new JPanel();
                    panel.add(labelNome);
                    panel.add(labelCpf);
                    panel.add(labelTelefone);
                    panel.add(labelAno);
                    panel.add(labelNomeQuadra);
                    panel.add(labelTipoQuadra);
                    panel.add(labelValorMinuto);
                    panel.add(labelTempoMinuto);
                    panel.add(labelNecessitaEquipamento);
                    panel.add(labelValorCalculado);
                    add(panel);
                }
            }


        });

        pesquisarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String id = txtId.getText();

                    pst = con.prepareStatement("select Nome,CPF,Telefone,NomeQuadra,TipoQuadra,Ano,ValorMinuto,TempoMinuto,NecessitaEquipamento,ValorCalculado from cadastro where id = ?");
                    pst.setString(1, id);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next() == true) {
                        String Nome = rs.getString(1);
                        String CPF = rs.getString(2);
                        String Telefone = rs.getString(3);
                        String Ano = rs.getString(4);
                        String NomeQuadra = rs.getString(5);
                        String TipoQuadra = rs.getString(6);
                        String ValorMinuto = rs.getString(7);
                        String TempoMinutos = rs.getString(8);
                        String NecessitaEquipamento = rs.getString(9);
                        String ValorCalculado = rs.getString(10);

                        txtNome.setText(Nome);
                        txtCpf.setText(CPF);
                        txtTel.setText(Telefone);
                        txtAno.setText(Ano);
                        txtNomeQuadra.setText(NomeQuadra);
                        txtTipoQuadra.setText(TipoQuadra);
                        txtValorMinuto.setText(ValorMinuto);
                        txtTempoMinutos.setText(TempoMinutos);
                        txtNecessita.setText(NecessitaEquipamento);
                        txtValorCalc.setText(ValorCalculado);

                    } else {
                        txtNome.setText("");
                        txtCpf.setText("");
                        txtTel.setText("");
                        txtAno.setText("");
                        txtNomeQuadra.setText("");
                        txtTipoQuadra.setText("");
                        txtValorMinuto.setText("");
                        txtTempoMinutos.setText("");
                        txtNecessita.setText("");
                        txtValorCalc.setText("");
                        JOptionPane.showMessageDialog(null, "Id nao encontrado!");
                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });


        alterarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String Nome, CPF, Telefone, NomeQuadra, TipoQuadra, Ano, ValorMinuto, TempoMinuto,NecessitaEquipamento,ValorCalculado,id;

                Nome = txtNome.getText();
                CPF = txtCpf.getText();
                Telefone = txtTel.getText();
                Ano = txtAno.getText();
                NomeQuadra = txtNomeQuadra.getText();
                TipoQuadra = txtTipoQuadra.getText();
                ValorMinuto = txtValorMinuto.getText();
                TempoMinuto = txtTempoMinutos.getText();
                NecessitaEquipamento = txtNecessita.getText();
                ValorCalculado = txtValorCalc.getText();
                id = txtId.getText();


                try {
                    pst = con.prepareStatement("update cadastro set Nome = ?,CPF = ?,Telefone = ?,Ano = ?,NomeQuadra = ?,TipoQuadra = ?,ValorMinuto = ?,TempoMinuto = ?,NecessitaEquipamento = ?,ValorCalculado = ? where id = ?");
                    pst.setString(11, id);
                    pst.setString(1, Nome);
                    pst.setString(2, CPF);
                    pst.setString(3, Telefone);
                    pst.setString(4, Ano);
                    pst.setString(5, NomeQuadra);
                    pst.setString(6, TipoQuadra);
                    pst.setString(7, ValorMinuto);
                    pst.setString(8, TempoMinuto);
                    pst.setString(9, NecessitaEquipamento);
                    pst.setString(10, ValorCalculado);
                    pst.setString(11, id);


                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Atualização realizada com sucesso!");
                    table_load();
                    txtNome.setText("");
                    txtCpf.setText("");
                    txtTel.setText("");
                    txtAno.setText("");
                    txtNomeQuadra.setText("");
                    txtTipoQuadra.setText("");
                    txtValorMinuto.setText("");
                    txtTempoMinutos.setText("");
                    txtNecessita.setText("");
                    txtValorCalc.setText("");
                    txtId.setText("");
                    txtNome.requestFocus();
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });


        apagarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id;
                id = txtId.getText();

                try {
                    pst = con.prepareStatement("delete from cadastro  where id = ?");

                    pst.setString(1, id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Apagado!");
                    table_load();
                    txtNome.setText("");
                    txtCpf.setText("");
                    txtTel.setText("");
                    txtAno.setText("");
                    txtNomeQuadra.setText("");
                    txtTipoQuadra.setText("");
                    txtValorMinuto.setText("");
                    txtTempoMinutos.setText("");
                    txtNecessita.setText("");
                    txtValorCalc.setText("");
                    txtNome.requestFocus();
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });


    }
}