package com.kiss.personalproject.dao.zz;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kiss.personalproject.bean.zz.AccountLogin;
import com.kiss.personalproject.bean.zz.AccountLoginHistory;
import com.kiss.personalproject.conn.Database;
import com.kiss.personalproject.conn.MongoDB;
import com.kiss.personalproject.conn.Table;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import com.sun.jdi.InvalidModuleException;
import jakarta.servlet.ServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;

import java.util.*;
import java.util.logging.Logger;

public class AccountLoginDAO implements Table<AccountLogin> {
    static Logger log = Logger.getLogger(AccountLoginDAO.class.getName());

    public static final String COLUMN_USERNAME = "_id.user_id";

    Database database;
    Document document;

    public AccountLoginDAO(ServletRequest request) {
        database = new MongoDB(request);
    }

    /**
     * @return
     */
    @Override
    public List<AccountLogin> getDocuments() {
        MongoCollection<Document> table = (MongoCollection<Document>) database.getTable(MongoDB.SCHEMA_SYS, MongoDB.TABLE_ACCOUNT_LOGIN);
        List<AccountLogin> accountLogins = new ArrayList<>();
        table.find().forEach(document -> {
            this.document = document;
            accountLogins.add(setFieldAndReturnBean());
        });
        return accountLogins;
    }

    /**
     * @param col
     * @param val
     * @return
     */
    @Override
    public List<AccountLogin> getDocuments(String col, String val) {
        MongoCollection<Document> table = (MongoCollection<Document>) database.getTable(MongoDB.SCHEMA_SYS, MongoDB.TABLE_ACCOUNT_LOGIN);
        List<AccountLogin> accountLogins = new ArrayList<>();
        table.find().filter(new Document(col, val)).forEach(document -> {
            this.document = document;
            accountLogins.add(setFieldAndReturnBean());
        });
        return accountLogins;
    }

    /**
     * @param
     * @return
     */
    @Override
    public AccountLogin getDocument(String col, String val) {
        MongoCollection<Document> table = (MongoCollection<Document>) database.getTable(MongoDB.SCHEMA_SYS, MongoDB.TABLE_ACCOUNT_LOGIN);
        document = table.find().filter(new Document(col, val)).first();
        if(document != null) {
            return setFieldAndReturnBean();
        }
        return new AccountLogin();
    }

    /**
     * @param document
     */
    @Override
    public void insertDocument(AccountLogin document) {

    }

    /**
     * @param id
     */
    @Override
    public void deleteDocument(String id) {

    }

    /**
     * @param document
     */
    @Override
    public AccountLogin updateDocument(AccountLogin document, String col, String val) throws JsonProcessingException {
        Document filer = new Document(col, val);
        Document account = new Document();
        if(StringUtils.isNotBlank(document.getUser_id())) {
            account.put(AccountLogin.COL_NAME_USER_ID_IN_ID, document.getUser_id());
        }
        if(StringUtils.isNotBlank(document.getPassword())) {
            account.put(AccountLogin.COL_NAME_PASSWORD, document.getPassword());
        }
        if(StringUtils.isNotBlank(document.getSession_id())) {
            account.put(AccountLogin.COL_NAME_SESSION_ID, document.getSession_id());
        }
        if(StringUtils.isNotBlank(ObjectUtils.toString(document.getSession_time(), ""))) {
            account.put(AccountLogin.COL_NAME_SESSION_TIME, document.getSession_time());
        }
        if(StringUtils.isNotBlank(document.getUpdate_id())) {
            account.put(AccountLogin.COL_NAME_UPDATE_ID, document.getUpdate_id());
        }
        if(StringUtils.isNotBlank(ObjectUtils.toString(document.getUpdate_time(), ""))) {
            account.put(AccountLogin.COL_NAME_UPDATE_TIME, document.getUpdate_time());
        }
        Document update = new Document("$set", account);

        MongoCollection<Document> table = (MongoCollection<Document>) database.getTable(MongoDB.SCHEMA_SYS, MongoDB.TABLE_ACCOUNT_LOGIN);
        List<Document> documents = table.find().filter(filer).into(new ArrayList<>());
        if(documents == null) {
            throw new InvalidModuleException("Không tìm thấy trong AccountLogin, " + col + "=" + val);
        }
        if(documents.size() > 1) {
            throw new InvalidModuleException("Tìm thấy nhiều hơn 1 document trong AccountLogin, " + col + "=" + val);
        }

        UpdateResult result = table.updateOne(filer, update);
        if(result.wasAcknowledged()) {
            document = getDocument(col, val);
            log.info("update AccountLogin=" + document);
            return  document;
        }
        return new AccountLogin();
    }

    /**
     * @return
     * @throws JsonProcessingException
     */
    @Override
    public AccountLogin setFieldAndReturnBean() {

        Document docId = (Document) document.get(AccountLogin.COL_NAME_ID);

        AccountLogin accountLogin = new AccountLogin();
        accountLogin.set_id(document.get(AccountLogin.COL_NAME_ID));
        accountLogin.setUser_id(docId.getString(AccountLogin.COL_NAME_USER_ID));
        accountLogin.setPassword(document.getString(AccountLogin.COL_NAME_PASSWORD));
        accountLogin.setSession_id(document.getString(AccountLogin.COL_NAME_SESSION_ID));
        accountLogin.setSession_time(document.getDate(AccountLogin.COL_NAME_SESSION_TIME));
        accountLogin.setUpdate_id(document.getString(AccountLogin.COL_NAME_UPDATE_ID));
        accountLogin.setUpdate_time(document.getDate(AccountLogin.COL_NAME_UPDATE_TIME));

        return accountLogin;
    }

    public void saveHistory(AccountLogin accountLogin, String logMessages) {
        Document objectId = new Document();
        objectId.put(AccountLoginHistory.COL_NAME_USER_ID, accountLogin.getUser_id());
        objectId.put(AccountLoginHistory.COL_NAME_LOG_TIME, new Date());

        Document data = new Document();
        data.put(AccountLoginHistory.COL_NAME_LOG_MESSAGES, logMessages);
        data.put(AccountLoginHistory.COL_NAME_ID, objectId);
        data.put(AccountLoginHistory.COL_NAME_PASSWORD, accountLogin.getPassword());
        data.put(AccountLoginHistory.COL_NAME_SESSION_ID, accountLogin.getSession_id());
        data.put(AccountLoginHistory.COL_NAME_UPDATE_ID, accountLogin.getUpdate_id());
        data.put(AccountLoginHistory.COL_NAME_UPDATE_TIME, accountLogin.getUpdate_time());

        MongoCollection<Document> table = (MongoCollection<Document>) database.getTable(MongoDB.SCHEMA_SYS, MongoDB.TABLE_ACCOUNT_LOGIN_HISTORY);
        table.insertOne(data);
    }
}
