package com.kiss.personalproject.dao.zz;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kiss.personalproject.bean.zz.Function;
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

public class FunctionDAO implements Table<Function> {
    static Logger log = Logger.getLogger(FunctionRolesDAO.class.getName());

    Database database;
    Document document;

    public FunctionDAO(ServletRequest request) {
        database = new MongoDB(request);
    }

    /**
     * @return
     */
    @Override
    public List<Function> getDocuments() {
        return List.of();
    }

    /**
     * @param col
     * @param val
     * @return
     */
    @Override
    public List<Function> getDocuments(String col, String val) {
        MongoCollection<Document> table = (MongoCollection<Document>) database.getTable(MongoDB.SCHEMA_SYS, MongoDB.TABLE_FUNCTION);
        List<Function> function = new ArrayList<>();
        table.find(Filters.eq(col, val)).forEach(doc -> {
            this.document = doc;
            function.add(this.setFieldAndReturnBean());
        });
        return function;
    }

    /**
     * @param col
     * @param val
     * @return
     */
    @Override
    public Function getDocument(String col, String val) {
        MongoCollection<Document> table = (MongoCollection<Document>) database.getTable(MongoDB.SCHEMA_SYS, MongoDB.TABLE_FUNCTION);
        document = table.find(Filters.eq(col, val)).first();
        return this.setFieldAndReturnBean();
    }

    /**
     * @param function
     */
    @Override
    public void insertDocument(Function function) {

    }

    /**
     * @param val
     */
    @Override
    public void deleteDocument(String val) {

    }

    /**
     * @param function
     * @param col
     * @param val
     * @return
     * @throws JsonProcessingException
     */
    @Override
    public Function updateDocument(Function function, String col, String val) throws JsonProcessingException {
        return null;
    }

    /**
     * @return
     * @throws JsonProcessingException
     */
    @Override
    public Function setFieldAndReturnBean() {
        Document docId = (Document) document.get(FunctionRoles.COL_NAME_ID);

        Function function = new Function();
        function.set_id(document.get(Function.COL_NAME_ID));
        function.setFunctionId(docId.getString(Function.COL_NAME_FUNCTION_ID));
        function.setFunctionName(document.getString(Function.COL_NAME_FUNCTION_NAME));
        function.setFunctionUrl(document.getString(Function.COL_NAME_FUNCTION_URL));
        function.setFunctionDesc(document.getString(Function.COL_NAME_FUNCTION_DESC));
        function.setUpdateId(document.getString(Function.COL_NAME_UPDATE_ID));
        function.setUpdateTime(document.getDate(Function.COL_NAME_UPDATE_TIME));

        return function;
    }
}
