package test.java.example.base;

import main.java.example.util.DBConnectionProvider;
import main.java.example.util.FileLoader;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.Map;

public class TestBase {
    private File backupFile;
    private Map<String, String> propMap;
    private IDatabaseConnection iDBConnection;
    protected Connection connection;

    protected void init(String propFilepath) throws Exception {
        propMap = FileLoader.loadProperties(propFilepath);
    }

    protected void dbConnect() throws Exception {
        DBConnectionProvider dbConnectionProvider = new DBConnectionProvider.Builder()
                                                                            .url(propMap.get("db.url"))
                                                                            .rdbName(propMap.get("db.rdbName"))
                                                                            .user(propMap.get("db.user"))
                                                                            .password(propMap.get("db.password"))
                                                                            .build();
        connection = dbConnectionProvider.getConnection();
        iDBConnection = new DatabaseConnection(connection, "test");
    }

    protected void dbBackup(String[] backupTables) throws Exception {
        backupFile = File.createTempFile("backup", ".xml");

        try (FileOutputStream out = new FileOutputStream(backupFile)) {
            QueryDataSet queryDataSet = new QueryDataSet(iDBConnection);

            for (String tableName : backupTables) {
                queryDataSet.addTable(tableName);
            }

            FlatXmlDataSet.write(queryDataSet, out);
        }
    }

    protected void dbSetup(String sourceDir) throws Exception {
        IDataSet dataSet = new CsvDataSet(new File(sourceDir));
        DatabaseOperation.CLEAN_INSERT.execute(iDBConnection, dataSet);
    }

    protected void dbRestore() throws Exception {
        if (backupFile == null) {
            return;
        }

        IDataSet dataSet = new FlatXmlDataSetBuilder().build(backupFile);
        DatabaseOperation.CLEAN_INSERT.execute(iDBConnection, dataSet);
        backupFile.delete();
        backupFile = null;
    }

    protected void dbDisConnect() throws Exception {
        iDBConnection.close();
        connection.close();
    }
}
