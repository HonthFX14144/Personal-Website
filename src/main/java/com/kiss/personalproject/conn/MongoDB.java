package com.kiss.personalproject.conn;

import com.kiss.personalproject.utils.AppUtils;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jakarta.servlet.*;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;

import java.util.logging.Logger;

public class MongoDB implements Database<MongoCollection<Document>> {
    static Logger log = Logger.getLogger(MongoDB.class.getName());
    private static final String USER_NAME = "kisbest01";
    private static final String PASSWORD = "Kisbest01";
    private static final String APP_NAME = "Cluster0";
    private static final String RETRY_WRITES = "true";
    public static final String SCHEMA_SYS = "sys";
    public static final String TABLE_ACCOUNT_INFO = "Account_Info";
    public static final String TABLE_ACCOUNT_LOGIN = "Account_Login";
    public static final String TABLE_ACCOUNT_LOGIN_HISTORY = "Account_Login_History";
    public static final String TABLE_ACCOUNT_ROLES = "Account_Roles";
    public static final String TABLE_ACCOUNT_SALT = "Account_Salt";
    public static final String TABLE_FUNCTION_ROLES = "Function_Roles";
    public static final String TABLE_FUNCTION = "Function";
    MongoClient mongoClient;

    public MongoDB(ServletRequest request) {
        getConnect(request);
    }

    private boolean preFlightChecks(MongoClient mongoClient) {
        Document pingCommand = new Document("ping", 1);
        Document response = mongoClient.getDatabase("admin").runCommand(pingCommand);
        log.info("=> Print result of the '{ping: 1}' command.");
        log.info(response.toJson(JsonWriterSettings.builder().indent(true).build()));
        return response.get("ok", Number.class).intValue() == 1;
    }

    /**
     * @return
     */
    @Override
    public boolean getConnect(ServletRequest request) {
        mongoClient = (MongoClient) AppUtils.getStoredDBConnection(request, AppUtils.ATT_MONGO_DB_CONNECTION);
        log.info("Mongo Client: " + mongoClient);
        if (mongoClient == null) {
            String URI = "mongodb+srv://" + USER_NAME + ":" + PASSWORD + "@cluster0.y766m6x.mongodb.net/?retryWrites=" + RETRY_WRITES + "&w=majority&appName=" + APP_NAME;
            mongoClient = MongoClients.create(URI);
            AppUtils.storeDBConnection(request, mongoClient, AppUtils.ATT_MONGO_DB_CONNECTION);
        }
        boolean testConnection = preFlightChecks(mongoClient);
        log.info("=> Connection successful: " + testConnection);
        return testConnection;
    }

    /**
     * @param schema
     * @param tableName
     * @return
     */
    @Override
    public MongoCollection<Document> getTable(String schema, String tableName) {
        MongoDatabase db = mongoClient.getDatabase(schema);
        return db.getCollection(tableName);
    }

    @Override
    public void close() {
        mongoClient.close();
    }

    @Override
    public void rollback() {
        log.info("No rollback in MongoDB!!!");
    }
}
