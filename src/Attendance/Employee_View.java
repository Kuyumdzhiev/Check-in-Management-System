package Attendance;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Employee_View {
    Connection con;
    JFrame frame = new JFrame();
    DefaultTableModel model = new DefaultTableModel();

    public void empView(int id) throws SQLException { // eView

        //-CLOSE-
        JLabel x = new JLabel("X");
        x.setForeground(Color.decode("#124b70"));
        x.setBounds(965, 10, 100, 20);
        x.setFont(new Font("Times New Roman", Font.BOLD, 20));
        frame.add(x);
        x.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        //-MINIMIZE-
        JLabel min = new JLabel("_");
        min.setForeground(Color.decode("#124b70"));
        min.setBounds(935, 0, 100, 20);
        frame.add(min);
        min.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.setState(JFrame.ICONIFIED);
            }
        });

        //-Panel-
        JPanel panel = new  JPanel();
        panel.setBounds(0, 0, 1000, 35);
        panel.setBackground(Color.decode("#DEE4E7"));
        frame.add(panel);

        //-Welcome-
        JLabel welcome = new JLabel("Welcome "+getUser(id)+",");
        welcome.setForeground(Color.decode("#DEE4E7"));
        welcome.setBounds(10, 50, 250, 20);
        welcome.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        frame.add(welcome);


        //-TABLE-
        JTable table=new JTable();
        model = (DefaultTableModel)table.getModel();
        model.addColumn("DATE");
        model.addColumn("STATUS");
        JScrollPane scPane=new JScrollPane(table);
        scPane.setBounds(500, 50, 480, 525);
        table.setFont(new Font("Times New Roman", Font.BOLD, 20));
        table.setRowHeight(50);
        frame.add(scPane);

        //-INFO-
        JLabel totalTeams = new JLabel("TOTAL TEAMS : "); // totalTeam; TEAMS
        totalTeams.setBounds(25, 180, 250, 20);
        totalTeams.setForeground(Color.decode("#DEE4E7"));
        totalTeams.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        frame.add(totalTeams); // totalTeam
        JLabel ttbox = new JLabel("");
        ttbox.setBounds(60, 230, 250, 20);
        ttbox.setForeground(Color.decode("#DEE4E7"));
        ttbox.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        frame.add(ttbox);
        JLabel teamAtt = new JLabel("DAYS ATTENDED : "); // teamAtt
        teamAtt.setBounds(25, 280, 250, 20);
        teamAtt.setForeground(Color.decode("#DEE4E7"));
        teamAtt.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        frame.add(teamAtt); // teamAtt
        JLabel atbox = new JLabel("");
        atbox.setBounds(60, 330, 250, 20);
        atbox.setForeground(Color.decode("#DEE4E7"));
        atbox.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        frame.add(atbox);
        JLabel teamAbs = new JLabel("DAYS MISSED : "); //team
        teamAbs.setBounds(25, 380, 250, 20);
        teamAbs.setForeground(Color.decode("#DEE4E7"));
        teamAbs.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        frame.add(teamAbs); //teamAbs
        JLabel mtbox = new JLabel("");
        mtbox.setBounds(60, 430, 250, 20);
        mtbox.setForeground(Color.decode("#DEE4E7"));
        mtbox.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        frame.add(mtbox);
        JLabel AttPer = new JLabel("ATTENDANCE PERCENTAGE : ");
        AttPer.setBounds(25, 480, 300, 20);
        AttPer.setForeground(Color.decode("#DEE4E7"));
        AttPer.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        frame.add(AttPer);
        JLabel prbox = new JLabel("");
        prbox.setBounds(60, 530, 250, 20);
        prbox.setForeground(Color.decode("#DEE4E7"));
        prbox.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        frame.add(prbox);

        //-SET_VALUES-
        int[] arr = stat(4);
        ttbox.setText(String.valueOf(arr[0]));
        atbox.setText(String.valueOf(arr[1]));
        mtbox.setText(String.valueOf(arr[2]));
        prbox.setText(String.valueOf(arr[3])+"%");

        //-frame-
        frame.setSize(1000,600);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.getContentPane().setBackground(Color.decode("#124b70"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public String getUser(int id) throws SQLException {
        //ENTER PORT, USER, PASSWORD.
        String url = "jdbc:mysql://localhost:3306/attendance";
        String user = "root";
        String pass = "hardSQLpassword_1";
        con = DriverManager.getConnection(url, user, pass);
        String str = "SELECT name FROM user WHERE id = "+id;
        Statement stm = con.createStatement();
        ResultSet rst = stm.executeQuery(str);
        rst.next();
        return rst.getString("name");
    }

    public void tblupdt(int id) {
        try {
            ResultSet res = dbSearch(id);
            for(int i=0; res.next(); i++) {
                model.addRow(new Object[0]);
                model.setValueAt(res.getString("dt"), i, 0);
                model.setValueAt(res.getString("status"), i, 1);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public int[] stat(int id) throws SQLException {
        String str = "SELECT COUNT(*) AS pre FROM attend WHERE stid = "+id+" AND status = 'Present'";
        String str2 = "SELECT COUNT(*) AS abs FROM attend WHERE stid = "+id+" AND status = 'Absent'";
        int[] x = new int[4];
        Statement stm = con.createStatement();
        ResultSet rst = stm.executeQuery(str);
        rst.next();
        x[1] = rst.getInt("pre");
        rst = stm.executeQuery(str2);
        rst.next();
        x[2] = rst.getInt("abs");
        x[0] = x[1] + x[2];
        //x[3] = (x[1]*100)/x[0];
        tblupdt(id);
        return x;
    }

    public ResultSet dbSearch(int id) throws SQLException {
        String str1 = "SELECT * from attend where stid = "+id+" ORDER BY dt desc";
        Statement stm = con.createStatement();
        ResultSet rst = stm.executeQuery(str1);
        return rst;
    }
}
