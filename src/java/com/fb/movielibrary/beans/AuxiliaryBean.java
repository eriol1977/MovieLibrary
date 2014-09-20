/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fb.movielibrary.beans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Francesco
 */
@Stateless
public class AuxiliaryBean
{

    @PersistenceContext
    private EntityManager em;

    public Long getCount(final Class clazz)
    {
        final String jpql = "select count(o.id) from " + clazz.getSimpleName() + " o";
        return (Long) em.createQuery(jpql).getSingleResult();
    }

    public List load(final String jpql, final int first, final int pageSize, final String sortField, final boolean ascending)
    {
        String query = jpql;
        if (sortField != null && !sortField.isEmpty()) {
            query += " order by o." + sortField;
            if (ascending) {
                query += " asc";
            } else {
                query += " desc";
            }
        }
        return em.createQuery(query).setFirstResult(first).setMaxResults(pageSize).getResultList();
    }
}
