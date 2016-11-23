
package cn.com.zerov.www;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class LicensingToDataBase {

     private static String url_my =
     "jdbc:mysql://localhost:3306/db_credit_test?useSSL=false";
    
     private static String user_my = "root";
    
     private static String password_my = "ShanShi@1989";
    
     private static Connection con_my;

//    private static String url_ser = "jdbc:mysql://192.168.18.110:3306/upload_to_xychina?useSSL=false";
//
//    private static String user_ser = "zxdc";
//
//    private static String password_ser = "zxdc";
//
//    private static Connection con_ser;

    private static String sPath = "/Users/zerov1989/Downloads/data/data_1.xls";

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
             con_my = DriverManager.getConnection(url_my, user_my,
             password_my);
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
                    PermissonWHBean.setX(j, cell.getContents());
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
            if (PermissonWHBean.XK_WSH.contains("表格说明") || PermissonWHBean.isEmpty()) {
                return;
            }
//            // 查重
//            ResultSet rs = con_ser.createStatement().executeQuery("select count(*) as rowCount from licensing_tem where " + PermissonWHBean.toID());
//            // ResultSet rs =
//            // con_my.createStatement().executeQuery("select count(*) as rowCount from licensing_tem where "
//            // + PermissonWHBean.toID());
//            rs.next();
//            if (rs.getInt("rowCount") > 0) {
//                System.out.println(PermissonWHBean.toValues());
//                return;
//            }
//            con_ser.createStatement()
//                    .execute(
//                            "INSERT INTO licensing_tem (`XK_WSH`,`XK_XMMC`,`XK_SPLB`,`XK_NR`,`XK_XDR`,`XK_XDR_SHXYM`,`XK_XDR_ZDM`,`XK_XDR_GSDJ`,`XK_XDR_SWDJ`,`XK_XDR_SFZ`,`XK_FR`,`XK_JDRQ`,`XK_JZQ`,`XK_XZJG`,`XK_ZT`,`DFBM`,`SJC`,`BZ`) VALUES "
//                                    + PermissonWHBean.toValues());
            con_my.createStatement()
                    .execute(
                            "INSERT INTO tab_permisson_wuhan_month (`XK_WSH`,`XK_XMMC`,`XK_SPLB`,`XK_NR`,`XK_XDR`,`XK_XDR_SHXYM`,`XK_XDR_ZDM`,`XK_XDR_GSDJ`,`XK_XDR_SWDJ`,`XK_XDR_SFZ`,`XK_FR`,`XK_JDRQ`,`XK_JZQ`,`XK_XZJG`,`XK_ZT`,`DFBM`,`SJC`,`BZ`) VALUES "
                                    + PermissonWHBean.toValues());

        } catch (Exception e) {
            System.out
                    .println("INSERT INTO tab_permisson_wuhan_month (`XK_WSH`,`XK_XMMC`,`XK_SPLB`,`XK_NR`,`XK_XDR`,`XK_XDR_SHXYM`,`XK_XDR_ZDM`,`XK_XDR_GSDJ`,`XK_XDR_SWDJ`,`XK_XDR_SFZ`,`XK_FR`,`XK_JDRQ`,`XK_JZQ`,`XK_XZJG`,`XK_ZT`,`DFBM`,`SJC`,`BZ`) VALUES "
                            + PermissonWHBean.toValues());
        } finally {
            PermissonWHBean.clean();
        }
    }
}
