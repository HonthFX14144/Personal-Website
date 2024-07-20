package com.kiss.personalproject.dao.zz;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kiss.personalproject.bean.zz.AccountInfo;
import com.kiss.personalproject.bean.zz.AccountLogin;
import com.kiss.personalproject.bean.zz.AccountLoginHistory;
import com.kiss.personalproject.conn.Database;
import com.kiss.personalproject.conn.MongoDB;
import com.kiss.personalproject.conn.Table;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import com.sun.jdi.InvalidModuleException;
import jakarta.servlet.ServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class AccountInfoDAO implements Table<AccountInfo> {
    static Logger log = Logger.getLogger(AccountInfoDAO.class.getName());

    Document document;
    Database database;

    public AccountInfoDAO(ServletRequest request) {
        database = new MongoDB(request);
    }
    /**
     * @return
     */
    @Override
    public List<AccountInfo> getDocuments() {
        return List.of();
    }

    /**
     * @param col
     * @param val
     * @return
     */
    @Override
    public List<AccountInfo> getDocuments(String col, String val) {
        return List.of();
    }

    /**
     * @param col
     * @param val
     * @return
     */
    @Override
    public AccountInfo getDocument(String col, String val) {
        MongoCollection<Document> table = (MongoCollection<Document>) database.getTable(MongoDB.SCHEMA_SYS, MongoDB.TABLE_ACCOUNT_INFO);
        document = table.find(Filters.eq(col, val)).first();
        if(document != null) {
            return setFieldAndReturnBean();
        }
        return new AccountInfo();
    }

    /**
     * @param accountInfo
     */
    @Override
    public void insertDocument(AccountInfo accountInfo) {

    }

    /**
     * @param val
     */
    @Override
    public void deleteDocument(String val) {

    }

    /**
     * @param accountInfo
     * @param col
     * @param val
     * @return
     * @throws JsonProcessingException
     */
    @Override
    public AccountInfo updateDocument(AccountInfo accountInfo, String col, String val) throws JsonProcessingException {
        Document filer = new Document(col, val);
        Document data = new Document();
        if(StringUtils.isNotBlank(accountInfo.getName())) {
            data.put(AccountInfo.COL_NAME_NAME, accountInfo.getName());
        }
        data.put(AccountInfo.COL_NAME_BIRTH_DAY, accountInfo.getBirthDay());
        data.put(AccountInfo.COL_NAME_GENDER, accountInfo.getGender());
        data.put(AccountInfo.COL_NAME_NATION, accountInfo.getNation());
        data.put(AccountInfo.COL_NAME_ADDRESS, accountInfo.getAddress());
        data.put(AccountInfo.COL_NAME_UPDATE_ID, accountInfo.getUpdateId());
        data.put(AccountInfo.COL_NAME_UPDATE_TIME, new Date());

        Document update = new Document("$set", data);

        MongoCollection<Document> table = (MongoCollection<Document>) database.getTable(MongoDB.SCHEMA_SYS, MongoDB.TABLE_ACCOUNT_INFO);
        List<Document> documents = table.find(filer).into(new ArrayList<>());
        if(documents == null) {
            throw new InvalidModuleException("Không tìm thấy trong AccountInfo, " + col + "=" + val);
        }
        if(documents.size() > 1) {
            throw new InvalidModuleException("Tìm thấy nhiều hơn 1 document trong AccountInfo, " + col + "=" + val);
        }

        UpdateResult result = table.updateOne(filer, update);
        if(result.wasAcknowledged()) {
            accountInfo = getDocument(col, val);
            log.info("update AccountInfo=" + document);
            return  accountInfo;
        }
        return new AccountInfo();
    }

    /**
     * @return
     */
    @Override
    public AccountInfo setFieldAndReturnBean() {

        Document docId = (Document) document.get(AccountInfo.COL_NAME_ID);

        AccountInfo accountInfo = new AccountInfo();
        accountInfo.set_id(document.get(AccountInfo.COL_NAME_ID));
        accountInfo.setUserId(docId.getString(AccountInfo.COL_NAME_USER_ID));
        accountInfo.setName(document.getString(AccountInfo.COL_NAME_NAME));
        accountInfo.setBirthDay(document.getDate(AccountInfo.COL_NAME_BIRTH_DAY));
        accountInfo.setGender(document.getInteger(AccountInfo.COL_NAME_GENDER));
        accountInfo.setNation(document.getString(AccountInfo.COL_NAME_NATION));
        accountInfo.setAddress(document.getString(AccountInfo.COL_NAME_ADDRESS));
        accountInfo.setEmail(document.getString(AccountInfo.COL_NAME_EMAIL));
        accountInfo.setPhone(document.getString(AccountInfo.COL_NAME_PHONE));
        accountInfo.setUpdateId(document.getString(AccountInfo.COL_NAME_UPDATE_ID));
        accountInfo.setUpdateTime(document.getDate(AccountInfo.COL_NAME_UPDATE_TIME));

        return accountInfo;
    }
}
