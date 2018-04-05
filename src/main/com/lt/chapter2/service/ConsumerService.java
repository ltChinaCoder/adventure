package com.lt.chapter2.service;

import com.lt.chapter2.model.Consumer;
import com.lt.chapter2.utils.DatabaseUtils;

import java.util.List;
import java.util.Map;

public class ConsumerService {

    public List<Consumer> getConsumerList() {
        String sql = "select * from consumer";
        return DatabaseUtils.getList(Consumer.class, sql);
    }

    public Consumer getConsumerById(long id) {
        return DatabaseUtils.getBeanById(Consumer.class, id);
    }

    public boolean deleteConsumerById(long id) {
        return DatabaseUtils.deleteEntityById(Consumer.class, id);
    }

    public boolean addConsumer(Map<String, Object> map) {
        return DatabaseUtils.insertEntity(Consumer.class, map);

    }
    public boolean updateConsumer(long id, Map<String, Object> map) {
        //todo
        if(id<0)
            return false;
        return DatabaseUtils.updateEntity(Consumer.class,id, map);
    }
}
