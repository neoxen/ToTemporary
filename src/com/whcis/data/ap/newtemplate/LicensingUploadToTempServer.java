
package com.whcis.data.ap.newtemplate;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class LicensingUploadToTempServer {

    private static String url_my =
            "jdbc:mysql://localhost:3306/db_credit_test?useSSL=false";

    private static String user_my = "root";

    private static String password_my = "kaifa001";

    private static Connection con_my;

    private static String sPath = "/Users/neo/Downloads/20161123/NL2016-11-14.xls";

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
                    LicensingWHBean.setX(j, cell.getContents());
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
            if (LicensingWHBean.XK_WSH.contains("表格说明") || LicensingWHBean.isEmpty()) {
                return;
            }
            con_my.createStatement()
                    .execute(
                            "INSERT INTO tab_permisson_wuhan_month (`XK_WSH`,`XK_XMMC`,`XK_SPLB`,`XK_NR`,`XK_XDR`,`XK_XDR_SHXYM`,`XK_XDR_ZDM`,`XK_XDR_GSDJ`,`XK_XDR_SWDJ`,`XK_XDR_SFZ`,`XK_FR`,`XK_JDRQ`,`XK_JZQ`,`XK_XZJG`,`XK_ZT`,`DFBM`,`SJC`,`BZ`) VALUES "
                                    + LicensingWHBean.toValues());

        } catch (Exception e) {
            System.out
                    .println("INSERT INTO tab_permisson_wuhan_month (`XK_WSH`,`XK_XMMC`,`XK_SPLB`,`XK_NR`,`XK_XDR`,`XK_XDR_SHXYM`,`XK_XDR_ZDM`,`XK_XDR_GSDJ`,`XK_XDR_SWDJ`,`XK_XDR_SFZ`,`XK_FR`,`XK_JDRQ`,`XK_JZQ`,`XK_XZJG`,`XK_ZT`,`DFBM`,`SJC`,`BZ`) VALUES "
                            + LicensingWHBean.toValues());
            e.printStackTrace();
        } finally {
            LicensingWHBean.clean();
        }
    }
}
