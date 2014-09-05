/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wadpam.tracker.api;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import net.sf.mardao.core.CursorPage;
import net.sf.mardao.core.dao.Dao;

/**
 *
 * @author osandstrom
 */
public abstract class MardaoCrudServlet<T, ID extends Serializable, D extends Dao<T, ID>> extends CrudServlet<T, ID> {

    protected D dao;

    public MardaoCrudServlet(Class<T> itemClass, Class<ID> idClass) {
        super(itemClass, idClass);
    }

    public MardaoCrudServlet(Class<T> itemClass, Class<ID> idClass, String pathPrefix) {
        super(itemClass, idClass, pathPrefix);
    }

    @Override
    protected ID create(HttpServletRequest request, T item) {
        return dao.persist(item);
    }

    @Override
    protected void delete(HttpServletRequest request, ID id) {
        dao.delete(id);
    }

    @Override
    protected T get(HttpServletRequest request, ID id) {
        return dao.findByPrimaryKey(id);
    }

    @Override
    protected CursorPage<T> getPage(int pageSize, String cursorString) {
        return dao.queryPage(pageSize, cursorString);
    }

    @Override
    protected void update(HttpServletRequest request, ID id, T item) {
        dao.update(item);
    }

    public void setDao(D dao) {
        this.dao = dao;
    }
    
}
