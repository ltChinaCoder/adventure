package com.lt.chapter2.service;

import com.lt.chapter2.model.Consumer;
import junit.framework.TestCase;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsumerServiceTest extends TestCase {
      ConsumerService consumerService=new ConsumerService();
    public void testGetConsumerList() {
        List<Consumer> consumerList = consumerService.getConsumerList();
        for (Consumer consumer : consumerList) {
            System.out.println(consumer);
        }
        
    }

    public void testGetConsumerById() {
        Consumer consumerById = consumerService.getConsumerById(1);
        System.out.println(consumerById);

    }

    public void testDeleteConsumerById() {
        boolean b = consumerService.deleteConsumerById(1);
        System.out.println(b);
    }
    public void testAddConsumer() {
        Map<String, Object>map = new HashMap<String, Object>();
        map.put("consumerName", "lt");
        map.put("consumerContact", "jack");
        map.put("consumerTelephone", "114");
        System.out.println( consumerService.addConsumer(map));
    }

    public void testUpdateConsumer() {
      for(int i=0;i<10000;i++)
      {
          Map<String, Object>map = new HashMap<String, Object>();
          map.put("consumerName", "zy");
          System.out.println(consumerService.updateConsumer(1, map));
      }
    }
}