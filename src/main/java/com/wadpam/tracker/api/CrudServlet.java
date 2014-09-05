/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wadpam.tracker.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.mardao.core.CursorPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author osandstrom
 */
public abstract class CrudServlet<T, ID extends Serializable> extends HttpServlet {
    
    public static final String CURSOR_STRING = "cursorString";
    public static final String DELETE = "DELETE";
    public static final String LOCATION = "Location";
    public static final String METHOD = "_method";
    public static final String MIME_JSON = "application/json";
    public static final String PAGE_SIZE = "pageSize";
    public static final String PATH_PREFIX = "path.prefix";
    public static final String POST = "POST";
    
    protected static final Logger LOGGER = LoggerFactory.getLogger(CrudServlet.class);
    public static final ObjectMapper MAPPER = new ObjectMapper();
    static {
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //MAPPER.getDeserializationConfig().without(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
    
    protected final Class<ID> idClass;
    protected final Class<T> itemClass;
    
    /** example is /api/user */
    protected String pathPrefix = null;
    
    protected CrudServlet(Class<T> itemClass, Class<ID> idClass) {
        this.idClass = idClass;
        this.itemClass = itemClass;
    }

    protected CrudServlet(Class<T> itemClass, Class<ID> idClass, String pathPrefix) {
        this(itemClass, idClass);
        this.pathPrefix = pathPrefix;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        if (null == pathPrefix) {
            pathPrefix = config.getInitParameter(PATH_PREFIX);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final ID id = getId(request.getRequestURI());
        
        if (null != id) {
            delete(request, id);
            writeResponse(response, 200, null);
        }
        else {
            writeResponse(response, 400, null);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (DELETE.equals(request.getParameter(METHOD))) {
            doDelete(request, response);
            return;
        }
        if (POST.equals(request.getParameter(METHOD))) {
            doPost(request, response);
            return;
        }
        
        final ID id = getId(request.getRequestURI());
        
        if (null != id) {
            T item = get(request, id);
            writeResponse(response, null != item ? 200 : 404, item);
        }
        else {
            final String pageSizeString = request.getParameter(PAGE_SIZE);
            int pageSize = 10;
            if (null != nullIfEmpty(pageSizeString)) {
                try {
                    pageSize = Integer.parseInt(pageSizeString);
                }
                catch (NumberFormatException nfe) {
                    response.sendError(400, pageSizeString);
                    return;
                }
            }
            final String cursorString = nullIfEmpty(request.getParameter(CURSOR_STRING));
            CursorPage<T> page = getPage(pageSize, cursorString);
            page.setRequestedPageSize(pageSize);
            writeResponse(response, 200, page);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (DELETE.equals(request.getParameter(METHOD))) {
            doDelete(request, response);
            return;
        }
        
        final ID id = getId(request.getRequestURI());
        
        if (null != id) {
            
            final T item = parseBody(request, id);
            update(request, id, item);
            writeResponse(response, 200, null);
        }
        else {
            final T item = parseBody(request, null);
            final ID assignedId = create(request, item);
            if (null != assignedId) {
                response.setHeader(LOCATION, assignedId.toString());
            }
            writeResponse(response, 201, assignedId);
        }
    }
    
    protected ID create(HttpServletRequest request, T item) {
        LOGGER.debug("create({})", item);
        return null;
    }
    
    protected void delete(HttpServletRequest request, ID id) {
        LOGGER.debug("delete({})", id);
    }
    
    protected T get(HttpServletRequest request, ID id) {
        LOGGER.debug("get({})", id);
        return null;
    }
    
    protected CursorPage<T> getPage(int pageSize, String cursorString) {
        LOGGER.debug("getPage({},{})", pageSize, cursorString);
        CursorPage<T> page = new CursorPage<>();
        
        return page;
    }
    
    public static String getFileString(final String pathPrefix, String requestURI) {
        int beginIndex = requestURI.indexOf(pathPrefix);
        if (beginIndex < 0) {
            return null;
        }
        
        String tail = requestURI.substring(beginIndex + pathPrefix.length());
        
        if (pathPrefix.endsWith("/")) {
            return nullIfEmpty(tail);
        }
        
        if (tail.startsWith("/")) {
            return nullIfEmpty(tail.substring(1));
        }
        
        return nullIfEmpty(tail);
    }

    public ID getId(String requestURI) {
        final String fileString = getFileString(pathPrefix, requestURI);
        if (null == fileString) {
            return null;
        }
        if (Long.class.equals(idClass)) {
            return (ID) Long.valueOf(fileString);
        }
        return (ID) fileString;
    }
    
    public static String nullIfEmpty(String s) {
        if (null == s) {
            return null;
        }
        if (0 == s.length()) {
            return null;
        }
        return s;
    }
    
    public T parseBody(HttpServletRequest request, ID id) throws IOException {
        T item = null;
        if (POST.equals(request.getMethod())) {
            item = MAPPER.readValue(request.getInputStream(), itemClass);
        }
        else {
            final Map<String,Object> simpleMap = new HashMap<>();
            for (Iterator it = request.getParameterMap().entrySet().iterator(); it.hasNext();) {
                Entry<String,String[]> e = (Entry<String,String[]>) it.next();
                if (1 == e.getValue().length) {
                    simpleMap.put(e.getKey(), e.getValue()[0]);
                }
                else {
                    simpleMap.put(e.getKey(), e.getValue());
                }
            }
            item = MAPPER.convertValue(simpleMap, itemClass);
        }
        
        return item;
    }
    
    protected void update(HttpServletRequest request, ID id, T item) {
        LOGGER.debug("update({},{})", id, item);
        
    }
    
    protected void writeResponse(HttpServletResponse response, int statusCode, Object entity) throws IOException {
        response.setStatus(statusCode);
        if (null != entity) {
            response.setContentType(MIME_JSON);
            final PrintWriter writer = response.getWriter();
            MAPPER.writeValue(writer, entity);
            writer.flush();
            writer.close();
        }
    }
}
