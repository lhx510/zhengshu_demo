package test;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.io.*;

public class CARead extends JPanel {
    private String CA_Name;
    private String CA_ItemData[][] = new String[9][2];
    private String[] columnNames = { "证书字段标记", "内容" };

    public CARead(String CertName) {
        CA_Name = CertName;
/* 三个Panel用来显示证书内容 */
        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel panelNormal = new JPanel();
        tabbedPane.addTab("普通信息", panelNormal);
        JPanel panelAll = new JPanel();
        panelAll.setLayout(new BorderLayout());
        tabbedPane.addTab("所有信息", panelAll);
        JPanel panelBase64 = new JPanel();
        panelBase64.setLayout(new BorderLayout());
        tabbedPane.addTab("Base64编码形式的信息", panelBase64);
/* 读取证书常规信息 */
        Read_Normal(panelNormal);
/* 读取证书文件字符串表示内容 */
        Read_Bin(panelAll);
/* 以Base64编码形式读取证书文件的信息 */
        Read_Raw(panelBase64);
        tabbedPane.setSelectedIndex(0);
        setLayout(new GridLayout(1, 1));
        add(tabbedPane);
    }
    private int Read_Normal(JPanel panel) {
        String Field;
        try {
            CertificateFactory certificate_factory = CertificateFactory
                    .getInstance("X.509");
            FileInputStream file_inputstream = new FileInputStream(CA_Name);
            X509Certificate x509certificate = (X509Certificate) certificate_factory
                    .generateCertificate(file_inputstream);
            Field = x509certificate.getType();
            CA_ItemData[0][0] = "类型";
            CA_ItemData[0][1] = Field;
            Field = Integer.toString(x509certificate.getVersion());
            CA_ItemData[1][0] = "版本";
            CA_ItemData[1][1] = Field;
            Field = x509certificate.getSubjectDN().getName();
            CA_ItemData[2][0] = "标题";
            CA_ItemData[2][1] = Field;
            Field=x509certificate.getNotBefore().toString();//得到开始有效日期
            CA_ItemData[3][0] = "开始有效日期";
            CA_ItemData[3][1] = Field;
            Field=x509certificate. getNotAfter().toString();//得到截止日期
            CA_ItemData[4][0] = "截止日期";
            CA_ItemData[4][1] = Field;
            Field=x509certificate.getSerialNumber().toString(16);//得到序列号
            CA_ItemData[5][0] = "序列号";
            CA_ItemData[5][1] = Field;
            Field=x509certificate.getIssuerDN().getName();//得到发行者名
            CA_ItemData[6][0] = "发行者名";
            CA_ItemData[6][1] = Field;
            Field=x509certificate.getSigAlgName();//得到签名算法
            CA_ItemData[7][0] = "签名算法";
            CA_ItemData[7][1] = Field;
            Field=x509certificate.getPublicKey().getAlgorithm();//得到公钥算法
            CA_ItemData[8][0] = "公钥算法";
            CA_ItemData[8][1] = Field;

            file_inputstream.close();
            final JTable table = new JTable(CA_ItemData, columnNames);
            TableColumn tc = null;
            tc = table.getColumnModel().getColumn(1);
            tc.setPreferredWidth(600);
            panel.add(table);
        } catch (Exception exception) {
            exception.printStackTrace();
            return -1;
        }
        return 0;
    }

    private int Read_Bin(JPanel panel) {
        try {
            FileInputStream file_inputstream = new FileInputStream(CA_Name);
            DataInputStream data_inputstream = new DataInputStream(
                    file_inputstream);
            CertificateFactory certificatefactory = CertificateFactory
                    .getInstance("X.509");
            byte[] bytes = new byte[data_inputstream.available()];
            data_inputstream.readFully(bytes);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            JEditorPane Cert_EditorPane;
            Cert_EditorPane = new JEditorPane();
            X509Certificate cert=null;
//遍历得到所有的证书属性
            if (bais.available() > 0)
            {
                cert = (X509Certificate) certificatefactory .generateCertificate(bais);
                Cert_EditorPane.setText(cert.toString());
            }
            Cert_EditorPane.disable();
            JScrollPane edit_scroll = new JScrollPane(Cert_EditorPane);
            panel.add(edit_scroll);
            file_inputstream.close();
            data_inputstream.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            return -1;
        }
        return 0;
    }
    private int Read_Raw(JPanel panel) {
        try {
            JEditorPane Cert_EditorPane = new JEditorPane();
            StringBuffer strBuffer =new StringBuffer();
            File inputFile = new File(CA_Name);
            FileReader in = new FileReader(inputFile);
            char[] buf = new char[2000];
            int len = in.read(buf, 0, 2000);
            for (int i = 1; i < len; i++) {
                strBuffer.append(buf[i]);
            }
            in.close();
            Cert_EditorPane.setText(strBuffer.toString());
            Cert_EditorPane.disable();
            JScrollPane edit_scroll = new JScrollPane(Cert_EditorPane);
            panel.add(edit_scroll);
        } catch (Exception exception) {
            exception.printStackTrace();
            return -1;
        }
        return 0;
    }
}
