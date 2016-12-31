package com.github.zhangyazhong.common.hql.query;

import com.github.zhangyazhong.common.hql.HqlStatement;
import com.google.common.collect.Table;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyazhong
 * @version 1.0
 */
public interface HqlQueryStatement extends HqlStatement {
    enum Order {
        DESC("desc"), ASC("asc");
        
        private String order;
        
        Order(String order) {
            this.order = order;
        }
        @Override
        public String toString() {
            return order;
        }
    }
    enum Operator {
        GREATER(">"),
        GREATER_OR_EQUAL(">="),
        LESS("<"),
        LESS_OR_EQUAL("<="),
        EQUAL("="),
        NOT_EQUAL("<>"),
        LIKE("like");
        
        private String operator;
        
        Operator(String operator) {
            this.operator = operator;
        }
        @Override
        public String toString() {
            return operator;
        }
    }
    
    HqlQueryStatement select(String... selects);
    HqlQueryStatement from(String... classes);
    HqlQueryStatement where(String... wheres);
    HqlQueryStatement where(List<String> wheres);
    HqlQueryStatement where(Map<String, String> wheres);
    HqlQueryStatement where(Table<String, Operator, String> wheres);
    HqlQueryStatement orderBy(Order order, String... attributes);
    HqlQueryStatement groupBy(String... attributes);
    
    Map<Integer, String> getQueryParameters() throws Exception;
}