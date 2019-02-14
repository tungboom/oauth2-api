package com.authentication.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

/**
 * @author TungBoom
 *
 */
public class SQLBuilder {

    public static final Logger LOG = Logger.getLogger(SQLBuilder.class);

    public final static String SQL_MODULE_COMMON = "common";
    
    public final static String SQL_MODULE_EMPLOYEES = "employees";
    public final static String SQL_MODULE_HISTORYS = "historys";
    public final static String SQL_MODULE_LOCATIONS = "locations";
    public final static String SQL_MODULE_ROLES = "roles";
    
    public static String getSqlQueryById(String module, String queryId) {
        File folder = null;
        try {
            folder = new ClassPathResource(
                    "sql" + File.separator + module + File.separator + queryId + ".sql").getFile();
            // Read file
            if (folder.isFile()) {
                return new String(Files.readAllBytes(Paths.get(folder.getAbsolutePath())));
            }
        } catch (IOException e) {
            LOG.error(e);
            return null;
        }
        return null;
    }

}
