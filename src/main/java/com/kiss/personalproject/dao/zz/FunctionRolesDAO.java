package com.kiss.personalproject.dao.zz;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kiss.personalproject.bean.zz.FunctionRoles;
import com.kiss.personalproject.conn.Database;
import com.kiss.personalproject.conn.MongoDB;
import com.kiss.personalproject.conn.Table;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import jakarta.servlet.ServletRequest;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FunctionRolesDAO implements Table<FunctionRoles> {
    static Logger log = Logger.getLogger(FunctionRolesDAO.class.getName());

    Database database;
    Document document;

    public FunctionRolesDAO(ServletRequest request) {
        database = new MongoDB(request);
    }

    /**
     * @return
     */
    @Override
    public List<FunctionRoles> getDocuments() {
        return List.of();
    }

    /**
     * @param col
     * @param val
     * @return
     */
    @Override
    public List<FunctionRoles> getDocuments(String col, String val) {
        MongoCollection<Document> table = (MongoCollection<Document>) database.getTable(MongoDB.SCHEMA_SYS, MongoDB.TABLE_FUNCTION_ROLES);
        List<FunctionRoles> functionRoles = new ArrayList<>();
        table.find(Filters.eq(col, val)).forEach(doc -> {
            this.document = doc;
            functionRoles.add(this.setFieldAndReturnBean());
        });
        return functionRoles;
    }

    /**
     * @param col
     * @param val
     * @return
     */
    @Override
    public FunctionRoles getDocument(String col, String val) {
        return null;
    }

    /**
     * @param functionRoles
     */
    @Override
    public void insertDocument(FunctionRoles functionRoles) {

    }

    /**
     * @param val
     */
    @Override
    public void deleteDocument(String val) {

    }

    /**
     * @param functionRoles
     * @param col
     * @param val
     * @return
     * @throws JsonProcessingException
     */
    @Override
    public FunctionRoles updateDocument(FunctionRoles functionRoles, String col, String val) throws JsonProcessingException {
        return null;
    }

    /**
     * @return
     * @throws JsonProcessingException
     */
    @Override
    public FunctionRoles setFieldAndReturnBean() {
        Document docId = (Document) document.get(FunctionRoles.COL_NAME_ID);

        FunctionRoles functionRoles = new FunctionRoles();
        functionRoles.set_id(document.get(FunctionRoles.COL_NAME_ID));
        functionRoles.setFunctionId(docId.getString(FunctionRoles.COL_NAME_FUNCTION_ID));
        functionRoles.setRoleId(docId.getString(FunctionRoles.COL_NAME_ROLE_ID));
        functionRoles.setUpdateId(document.getString(FunctionRoles.COL_NAME_UPDATE_ID));
        functionRoles.setUpdateTime(document.getDate(FunctionRoles.COL_NAME_UPDATE_TIME));

        return functionRoles;
    }
}
