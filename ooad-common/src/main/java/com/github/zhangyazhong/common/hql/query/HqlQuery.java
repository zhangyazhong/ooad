package com.github.zhangyazhong.common.hql.query;

import com.google.common.collect.Lists;

import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import org.apache.commons.lang.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhangyazhong
 * @version 1.0
 */
public class HqlQuery implements HqlQueryStatement {
    
    private List<String> selectList, fromList, asList, whereList, orderByList, groupByList;
    
    public HqlQuery() {
        selectList = Lists.newArrayList();
        fromList = Lists.newArrayList();
        asList = Lists.newArrayList();
        whereList = Lists.newArrayList();
        orderByList = Lists.newArrayList();
        groupByList = Lists.newArrayList();
    }
    public HqlQuery(String clazz) {
        this();
        fromList.add(clazz); asList.add("");
    }
    
    @Override
    public HqlQuery select(String... selects) {
        Collections.addAll(selectList, selects);
        return this;
    }
    
    @Override
    public HqlQuery from(String... classes) {
        for (String clazz: classes) {
            fromList.add(String.format("%s %s",
                    clazz, clazz.substring(0, 1).toLowerCase() + clazz.substring(1)));
            asList.add("");
        }
        return this;
    }
    
    @Override
    public HqlQuery where(String... wheres) {
        Collections.addAll(whereList, wheres);
        return this;
    }
    @Override
    public HqlQuery where(List<String> wheres) {
        if (wheres != null && !wheres.isEmpty()) {
            whereList.addAll(wheres);
        }
        return this;
    }
    @Override
    public HqlQuery where(Map<String, String> wheres) {
        if (wheres != null && !wheres.isEmpty()) {
            whereList.addAll(wheres.entrySet().stream()
                    .map(entry -> String.format("%s='%s'", entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList()));
        }
        return this;
    }
    @Override
    public HqlQuery where(Table<String, Operator, String> wheres) {
        wheres.cellSet().forEach((where) -> whereList.add(String.format("%s %s %s",
                where.getRowKey(),
                where.getColumnKey(),
                where.getValue()
        )));
        return this;
    }
    
    @Override
    public HqlQuery orderBy(Order order, String... attributes) {
        for (String attribute: attributes) {
            orderByList.add(String.format("%s %s", attribute, order));
        }
        return this;
    }
    
    @Override
    public HqlQuery groupBy(String... attributes) {
        for (String attribute: attributes) {
            groupByList.add(String.format("%s", attribute));
        }
        return this;
    }
    
    @Override
    public String createStatement() {
        StringBuilder hql = new StringBuilder();
        hql.append(!selectList.isEmpty() ? String.format("select %s ", StringUtils.join(selectList, ", ")) : "");
        hql.append(String.format("from %s ", StringUtils.join(fromList, ", ")));
        hql.append(!whereList.isEmpty() ? String.format("where %s ", StringUtils.join(whereList, " and ")) : "");
        hql.append(!groupByList.isEmpty() ? String.format("group by %s ", StringUtils.join(groupByList, ", ")) : "");
        hql.append(!orderByList.isEmpty() ? String.format("order by %s ", StringUtils.join(orderByList, ", ")) : "");
        return hql.toString();
    }
    
    @Override
    public Map<Integer, String> getQueryParameters() throws Exception {
        return Maps.newHashMap();
    }
}
