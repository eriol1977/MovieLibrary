/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fb.movielibrary.controllers;

import com.fb.movielibrary.beans.AuxiliaryBean;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Francesco
 * @param <T>
 */
public class QueryDataModel<T> extends LazyDataModel<T>
{

    private final AuxiliaryBean bean;

    private final String jpql;

    public QueryDataModel(final AuxiliaryBean bean, final Class clazz)
    {
        this.bean = bean;
        this.jpql = "select o from " + clazz.getSimpleName() + " o";
        final Long count = bean.getCount(clazz);
        setRowCount(count.intValue());
    }

    @Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters)
    {
        return bean.load(jpql, first, pageSize, sortField, sortOrder.equals(SortOrder.ASCENDING));
    }

}
