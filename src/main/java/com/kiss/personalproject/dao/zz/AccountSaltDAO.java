package com.kiss.personalproject.dao.zz;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kiss.personalproject.bean.zz.AccountLogin;
import com.kiss.personalproject.bean.zz.AccountSalt;
import com.kiss.personalproject.conn.Database;
import com.kiss.personalproject.conn.MongoDB;
import com.kiss.personalproject.conn.Table;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import com.sun.jdi.InvalidModuleException;
import jakarta.servlet.ServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AccountSaltDAO implements Table<AccountSalt> {
    static Logger log = Logger.getLogger(AccountSaltDAO.class.getName());

    Database database;
    Document document;

    public AccountSaltDAO(ServletRequest request) {
        database = new MongoDB(request);
    }
    /**
     * @return
     */
    @Override
    public List<AccountSalt> getDocuments() {
        return List.of();
    }

    /**
     * @param col
     * @param val
     * @return
     */
    @Override
    public List<AccountSalt> getDocuments(String col, String val) {
        return List.of();
    }

    /**
     * @param col
     * @param val
     * @return
     */
    @Override
    public AccountSalt getDocument(String col, String val) {
        MongoCollection<Document> table = (MongoCollection<Document>) database.getTable(MongoDB.SCHEMA_SYS, MongoDB.TABLE_ACCOUNT_SALT);
        document = table.find().filter(new Document(col, val)).first();
        if(document != null) {
            return setFieldAndReturnBean();
        }
        return new AccountSalt();
    }

    /**
     * @param accountSalt
     */
    @Override
    public void insertDocument(AccountSalt accountSalt) {

    }

    /**
     * @param val
     */
    @Override
    public void deleteDocument(String val) {

    }

    /**
     * @param
     * @param col
     * @param val
     * @return
     */
    @Override
    public AccountSalt updateDocument(AccountSalt document, String col, String val) {
        Bson filer = Filters.eq(col, val);

        List<Bson> setList = new ArrayList<>();
        if(StringUtils.isNotBlank(document.getUser_id())) {
            setList.add(Updates.set(AccountLogin.COL_NAME_USER_ID_IN_ID, document.getUser_id()));
        }
        if(StringUtils.isNotBlank(document.getSalt_id())) {
            setList.add(Updates.set(AccountSalt.COL_NAME_SALT_ID, document.getSalt_id()));
        }
        if(StringUtils.isNotBlank(document.getUpdate_id())) {
            setList.add(Updates.set(AccountLogin.COL_NAME_UPDATE_ID, document.getUpdate_id()));
        }
        if(StringUtils.isNotBlank(ObjectUtils.toString(document.getUpdate_time(), ""))) {
            setList.add(Updates.set(AccountLogin.COL_NAME_UPDATE_TIME, document.getUpdate_time()));
        }
        Bson update = Updates.combine(setList);

        MongoCollection<Document> table = (MongoCollection<Document>) database.getTable(MongoDB.SCHEMA_SYS, MongoDB.TABLE_ACCOUNT_SALT);
        List<Document> documents = table.find().filter(filer).into(new ArrayList<>());
        if(documents == null) {
            throw new InvalidModuleException("Không tìm thấy trong AccountSalt, " + col + "=" + val);
        }
        if(documents.size() > 1) {
            throw new InvalidModuleException("Tìm thấy nhiều hơn 1 document trong AccountSalt, " + col + "=" + val);
        }
        UpdateResult result = table.updateOne(filer, update);
        if(result.wasAcknowledged()) {
            document = getDocument(col, val);
            log.info("update AccountSalt=" + document);
            return  document;
        }
        return new AccountSalt();
    }

    /**
     * @return
     * @throws JsonProcessingException
     */
    @Override
    public AccountSalt setFieldAndReturnBean() {

        Document doc = (Document) document.get(AccountSalt.COL_NAME_ID);

        AccountSalt accountSalt = new AccountSalt();
        accountSalt.set_id(document.get(AccountSalt.COL_NAME_ID));
        accountSalt.setUser_id(doc.getString(AccountSalt.COL_NAME_USER_ID));
        accountSalt.setSalt_id(document.getString(AccountSalt.COL_NAME_SALT_ID));
        accountSalt.setUpdate_id(document.getString(AccountSalt.COL_NAME_UPDATE_ID));
        accountSalt.setUpdate_time(document.getDate(AccountSalt.COL_NAME_UPDATE_TIME));

        return accountSalt;
    }
}
