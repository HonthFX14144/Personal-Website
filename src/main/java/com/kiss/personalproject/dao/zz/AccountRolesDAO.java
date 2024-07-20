package com.kiss.personalproject.dao.zz;

import com.kiss.personalproject.bean.zz.AccountRoles;
import com.kiss.personalproject.conn.Database;
import com.kiss.personalproject.conn.MongoDB;
import com.kiss.personalproject.conn.Table;
import com.mongodb.client.MongoCollection;
import jakarta.servlet.ServletRequest;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AccountRolesDAO implements Table<AccountRoles> {
    static Logger log = Logger.getLogger(AccountLoginDAO.class.getName());

    Database database;
    Document document;

    public AccountRolesDAO(ServletRequest request) {
        database = new MongoDB(request);
    }
    /**
     * @return
     */
    @Override
    public List<AccountRoles> getDocuments() {
        MongoCollection<Document> collection = (MongoCollection<Document>) database.getTable(MongoDB.SCHEMA_SYS, MongoDB.TABLE_ACCOUNT_ROLES);
        List<AccountRoles> accountRoles = new ArrayList<>();
        collection.find().forEach(document -> {
            this.document = document;
            accountRoles.add(setFieldAndReturnBean());
        });
        return accountRoles;
    }

    /**
     * @param col
     * @param val
     * @return
     */
    @Override
    public List<AccountRoles> getDocuments(String col, String val) {
        MongoCollection<Document> collection = (MongoCollection<Document>) database.getTable(MongoDB.SCHEMA_SYS, MongoDB.TABLE_ACCOUNT_ROLES);
        List<AccountRoles> accountRoles = new ArrayList<>();
        collection.find().filter(new Document(col, val)).forEach(document -> {
            this.document = document;
            accountRoles.add(setFieldAndReturnBean());
        });
        return accountRoles;
    }

    /**
     * @param col
     * @param val
     * @return
     */
    @Override
    public AccountRoles getDocument(String col, String val) {
        MongoCollection<Document> collection = (MongoCollection<Document>) database.getTable(MongoDB.SCHEMA_SYS, MongoDB.TABLE_ACCOUNT_ROLES);
        document = collection.find().filter(new Document(col, val)).first();
        if (document != null) {
            return setFieldAndReturnBean();
        }
        return new AccountRoles();
    }

    /**
     * @param accountRoles
     */
    @Override
    public void insertDocument(AccountRoles accountRoles) {

    }

    /**
     * @param id
     */
    @Override
    public void deleteDocument(String id) {

    }

    /**
     * @param accountRoles
     */
    @Override
    public AccountRoles updateDocument(AccountRoles accountRoles, String col, String val) {
        return new AccountRoles();
    }

    /**
     * @return
     */
    @Override
    public AccountRoles setFieldAndReturnBean() {
        Document docId = (Document) document.get(AccountRoles.COL_NAME_ID);

        AccountRoles accountRole = new AccountRoles();
        accountRole.set_id(document.get(AccountRoles.COL_NAME_ID));
        accountRole.setUser_id(docId.getString(AccountRoles.COL_NAME_USER_ID));
        accountRole.setRoles_id(docId.getString(AccountRoles.COL_NAME_ROLES_ID));
        accountRole.setUpdate_id(document.getString(AccountRoles.COL_NAME_UPDATE_ID));
        accountRole.setUpdate_date(document.getDate(AccountRoles.COL_NAME_UPDATE_DATE));

        return accountRole;
    }
}
