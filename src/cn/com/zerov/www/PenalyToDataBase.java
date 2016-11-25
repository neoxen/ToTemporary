
package cn.com.zerov.www;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class PenalyToDataBase {
//    private static String url_ser = "jdbc:mysql://192.168.18.110:3306/upload_to_xychina?useSSL=false";
//
//    private static String user_ser = "zxdc";
//
//    private static String password_ser = "zxdc";
//
//    private static Connection con_ser;
    
    private static String url_my = "jdbc:mysql://localhost:3306/db_credit_test?useSSL=false";

    private static String user_my = "root";

    private static String password_my = "kaifa001";

    private static Connection con_my;

    private static String sPath = "/Users/neo/Downloads/20161123/NP2016-11-14.xls";

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // 打开数据库
        openDatabase();
        // 写入数据
        writeToDatabase();

        System.out.println("OK!");
    }

    private static void openDatabase() {
        try {
//            con_ser = DriverManager.getConnection(url_ser, user_ser, password_ser);
            con_my = DriverManager.getConnection(url_my, user_my, password_my);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeToDatabase() {
        try {
            Workbook readWB = Workbook.getWorkbook(new File(sPath));
            Sheet readsheet = readWB.getSheet(0);
            int rsColumns = readsheet.getColumns();
            int rsRows = readsheet.getRows();
            for (int i = 1; i < rsRows; i++) {
                for (int j = 0; j < rsColumns; j++) {
                    Cell cell = readsheet.getCell(j, i);
                    PenalyWHBean.setX(j, cell.getContents());
                }
                insertINTO();
                // System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertINTO() {
        try {
            if (PenalyWHBean.CF_WSH.contains("表格说明") || PenalyWHBean.isEmpty()) {
                return;
            }
//            // 查重
//            ResultSet rs = con_ser.createStatement().executeQuery("select count(*) as rowCount from penaly_tem where " + PenalyWHBean.toID());
//            // System.out.println("select count(*) as rowCount from penaly_tem where "
//            // + PenalyWHBean.toID());
//            rs.next();
//            if (rs.getInt("rowCount") > 0) {
//                System.out.println(PenalyWHBean.toValues());
//                return;
//            }
//            con_ser.createStatement()
//                    .execute(
//                            "INSERT INTO penaly_tem (`CF_WSH`,`CF_CFMC`,`CF_CFLB1`,`CF_CFLB2`,`CF_SY`,`CF_YJ`,`CF_XDR_MC`,`CF_XDR_SHXYM`,`CF_XDR_ZDM`,`CF_XDR_GSDJ`,`CF_XDR_SWDJ`,`CF_XDR_SFZ`,`CF_FR`,`CF_JG`,`CF_JDRQ`,`CF_XZJG`,`CF_ZT`,`DFBM`,`SJC`,`BZ`) VALUES "
//                                    + PenalyWHBean.toValues());
            con_my.createStatement()
                    .execute(
                            "INSERT INTO tab_penaly_wuhan_month (`CF_WSH`,`CF_CFMC`,`CF_CFLB1`,`CF_CFLB2`,`CF_SY`,`CF_YJ`,`CF_XDR_MC`,`CF_XDR_SHXYM`,`CF_XDR_ZDM`,`CF_XDR_GSDJ`,`CF_XDR_SWDJ`,`CF_XDR_SFZ`,`CF_FR`,`CF_JG`,`CF_JDRQ`,`CF_XZJG`,`CF_ZT`,`DFBM`,`SJC`,`BZ`) VALUES "
                                    + PenalyWHBean.toValues());
        } catch (Exception e) {
            e.printStackTrace();
            System.out
                    .println("INSERT INTO tab_penaly_wuhan_month (`CF_WSH`,`CF_CFMC`,`CF_CFLB1`,`CF_CFLB2`,`CF_SY`,`CF_YJ`,`CF_XDR_MC`,`CF_XDR_SHXYM`,`CF_XDR_ZDM`,`CF_XDR_GSDJ`,`CF_XDR_SWDJ`,`CF_XDR_SFZ`,`CF_FR`,`CF_JG`,`CF_JDRQ`,`CF_XZJG`,`CF_ZT`,`DFBM`,`SJC`,`BZ`) VALUES "
                            + PenalyWHBean.toValues());
        } finally {
            PenalyWHBean.clean();
        }
    }

}
